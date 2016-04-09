package org.eurofurence.connavigator.util

import io.swagger.client.model.EntityBase
import org.eurofurence.connavigator.db.DB
import org.eurofurence.connavigator.db.IDB
import org.eurofurence.connavigator.db.SyncIDB
import org.eurofurence.connavigator.db.cached

/**
 * Uses the [indexer] function to index the database receiver.
 */
fun <T> DB<T>.indexBy(indexer: (T) -> Any) = object : IDB<T>() {
    override fun id(item: T): Any = indexer(item)

    override var elements: List<T>
        get() = this@indexBy.elements
        set(values) {
            this@indexBy.elements = values
        }

    override var keyValues: Map<Any, T>
        get() = this@indexBy.elements.associateBy { id(it) }
        set(values) {
            this@indexBy.elements = values.values.toList()
        }

    override val exists: Boolean
        get() = this@indexBy.exists
}

/**
 * Pairs the left indexed database with the right by using shared keys.
 */
infix fun <T, U> IDB<T>.pairWith(second: IDB<U>) =
        object : IDB<Pair<T?, U?>>() {
            override val exists: Boolean
                get() = this@pairWith.exists && second.exists

            override fun id(item: Pair<T?, U?>): Any =
                    if (item.first != null)
                        this@pairWith.id(item.first!!)
                    else if (item.second != null)
                        second.id(item.second!!)
                    else throw IllegalArgumentException()

            override var keyValues: Map<Any, Pair<T?, U?>>
                get() {
                    // First where key not in second
                    val f = this@pairWith.keyValues
                            .filterKeys { it !in second.keyValues }
                            .mapValues { it.value to null }

                    // Second where key not in first
                    val s = second.keyValues
                            .filterKeys { it !in this@pairWith.keyValues }
                            .mapValues { null to it.value }

                    // Both where keys join
                    val c = this@pairWith.keyValues
                            .filterKeys { it in second.keyValues }
                            .mapValues { it.value to second.keyValues[it.key]!! }

                    return f + c + s
                }
                set(value) {
                    // Assign to first where the first field is assigned
                    this@pairWith.keyValues = value.filterValues { it.first == null }.mapValues { it.value.first!! }

                    // Assign to second where the second field is assigned
                    second.keyValues = value.filterValues { it.second == null }.mapValues { it.value.second!! }
                }

        }

/**
 * Indexes the receiver by [EntityBase]s identifier, caches it returns a [SyncIDB] marking by the deleted flag.
 */
fun <T : EntityBase> DB<T>.cachedApiDB() = SyncIDB(EntityBase::deleted, indexBy(EntityBase::getId).cached())