/**
 * Eurofurence API for Mobile Apps
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.model;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;

@ApiModel(description = "")
public class RegSysAlternativePinResponse {
  
  @SerializedName("RegNo")
  private Integer regNo = null;
  @SerializedName("NameOnBadge")
  private String nameOnBadge = null;
  @SerializedName("Pin")
  private String pin = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getRegNo() {
    return regNo;
  }
  public void setRegNo(Integer regNo) {
    this.regNo = regNo;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getNameOnBadge() {
    return nameOnBadge;
  }
  public void setNameOnBadge(String nameOnBadge) {
    this.nameOnBadge = nameOnBadge;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getPin() {
    return pin;
  }
  public void setPin(String pin) {
    this.pin = pin;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegSysAlternativePinResponse regSysAlternativePinResponse = (RegSysAlternativePinResponse) o;
    return (this.regNo == null ? regSysAlternativePinResponse.regNo == null : this.regNo.equals(regSysAlternativePinResponse.regNo)) &&
        (this.nameOnBadge == null ? regSysAlternativePinResponse.nameOnBadge == null : this.nameOnBadge.equals(regSysAlternativePinResponse.nameOnBadge)) &&
        (this.pin == null ? regSysAlternativePinResponse.pin == null : this.pin.equals(regSysAlternativePinResponse.pin));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.regNo == null ? 0: this.regNo.hashCode());
    result = 31 * result + (this.nameOnBadge == null ? 0: this.nameOnBadge.hashCode());
    result = 31 * result + (this.pin == null ? 0: this.pin.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegSysAlternativePinResponse {\n");
    
    sb.append("  regNo: ").append(regNo).append("\n");
    sb.append("  nameOnBadge: ").append(nameOnBadge).append("\n");
    sb.append("  pin: ").append(pin).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
