package org.eurofurence.connavigator.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.widget.TextView
import com.pawegio.kandroid.textWatcher
import org.eurofurence.connavigator.BuildConfig
import org.eurofurence.connavigator.R
import org.eurofurence.connavigator.pref.AnalyticsPreferences
import org.eurofurence.connavigator.pref.AppPreferences
import org.eurofurence.connavigator.pref.BackgroundPreferences
import org.eurofurence.connavigator.pref.DebugPreferences
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

class ActivitySettings : AppCompatActivity(), AnkoLogger {
    val ui by lazy { SettingsUi() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)
    }
}

class SettingsUi : AnkoComponent<ActivitySettings> {
    override fun createView(ui: AnkoContext<ActivitySettings>) = with(ui) {
        scrollView {
            verticalLayout {
                toolbar {
                    title = "Settings"
                    setTitleTextColor(ContextCompat.getColor(ctx, R.color.textWhite))
                    backgroundResource = R.color.primary
                }
                verticalLayout {
                    padding = dip(10)
                    textView("UI settings") {
                        padding = dip(15)
                    }

                    checkBox {
                        text = "Show expired announcements"
                        isChecked = AppPreferences.showOldAnnouncements
                        setOnCheckedChangeListener { _, b -> AppPreferences.showOldAnnouncements = b }
                    }

                    checkBox {
                        text = "Use days of the week instead of actual dates."
                        isChecked = AppPreferences.shortenDates
                        setOnCheckedChangeListener { _, b -> AppPreferences.shortenDates = b }
                    }

                    checkBox {
                        text = "Switch the short press and long press behaviour for events."
                        isChecked = AppPreferences.dialogOnEventPress
                        setOnCheckedChangeListener { _, b -> AppPreferences.dialogOnEventPress = b }
                    }

                    checkBox("Immediately close app on back button press") {
                        isChecked = BackgroundPreferences.closeAppImmediately
                        setOnCheckedChangeListener { _, b -> BackgroundPreferences.closeAppImmediately = b }
                    }

                    checkBox("Send a notification when a new announcement is published") {
                        isChecked = AppPreferences.notificationsEnabled
                        setOnCheckedChangeListener { _, b -> AppPreferences.notificationsEnabled = b }
                    }

                    linearLayout {
                        weightSum = 10F

                        editText {
                            hint = "Get notified this many minutes before the start of a favorited event."
                            setText(AppPreferences.notificationMinutesBefore.toString(), TextView.BufferType.EDITABLE)
                            textWatcher {
                                afterTextChanged { text ->
                                    if (text!!.isNotEmpty()) AppPreferences.notificationMinutesBefore = text.toString().toInt()
                                }
                            }
                            inputType = InputType.TYPE_CLASS_NUMBER
                        }.lparams(dip(0), wrapContent) {
                            weight = 2F
                        }

                        textView("Amount of minutes to get an alert for a favorited event.") {
                            textColor = ContextCompat.getColor(ctx, R.color.textBlack)
                        }.lparams(dip(0), wrapContent) {
                            weight = 8F
                        }
                    }



                    textView("Analytics settings") {
                        padding = dip(15)
                    }

                    checkBox {
                        text = "Track usage with analytics"
                        isChecked = AnalyticsPreferences.enabled
                        setOnCheckedChangeListener { _, value -> AnalyticsPreferences.enabled = value }
                    }

                    checkBox {
                        text = "Track performance statistics"
                        isChecked = AnalyticsPreferences.performanceTracking
                        setOnCheckedChangeListener { _, value -> AnalyticsPreferences.performanceTracking = value }
                    }
                }

                verticalLayout {
                    padding = dip(10)
                    visibility = if (BuildConfig.DEBUG) View.VISIBLE else View.GONE

                    view {
                        lparams(matchParent, dip(1)) {
                            verticalMargin = dip(5)
                        }
                        backgroundResource = R.color.primary
                    }

                    textView("Debug settings") {
                        padding = dip(15)
                    }

                    checkBox {
                        text = "Tweak event days so it seems like it's the current date"
                        isChecked = DebugPreferences.debugDates
                        setOnClickListener { DebugPreferences.debugDates = !DebugPreferences.debugDates }
                    }

                    linearLayout {
                        weightSum = 10F

                        editText {
                            hint = "The amount of days to offset by. Must be integer"
                            setText(DebugPreferences.eventDateOffset.toString(), TextView.BufferType.EDITABLE)
                            textWatcher {
                                afterTextChanged { text ->
                                    if (text!!.isNotEmpty()) DebugPreferences.eventDateOffset = text.toString().toInt()
                                }
                            }
                            inputType = InputType.TYPE_CLASS_NUMBER
                        }.lparams(dip(0), wrapContent) {
                            weight = 2F
                        }

                        textView("Amount of days to offset the event schedule by") {
                            textColor = ContextCompat.getColor(ctx, R.color.textBlack)
                        }.lparams(dip(0), wrapContent) {
                            weight = 8F
                        }
                    }

                    checkBox {
                        text = "Schedule notifications in 5 minutes instead of event time"
                        isChecked = DebugPreferences.scheduleNotificationsForTest
                        setOnClickListener { DebugPreferences.scheduleNotificationsForTest = !DebugPreferences.scheduleNotificationsForTest }
                    }
                }

                lparams(matchParent, matchParent)
            }
        }
    }
}