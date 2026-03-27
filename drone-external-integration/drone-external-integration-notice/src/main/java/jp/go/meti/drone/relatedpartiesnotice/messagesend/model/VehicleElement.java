package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;

/**
 * 機体予約要素
 */
@Schema(description = "機体予約要素")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class VehicleElement   {
  @JsonProperty("reservationId")
  private String reservationId;

  @JsonProperty("vehicleId")
  private String vehicleId;

  @JsonProperty("aircraftInfo")
  private AircraftInfo aircraftInfo;

  @JsonProperty("startAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String startAt;

  @JsonProperty("endAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String endAt;

  @JsonProperty("amount")
  private Integer amount;

  public VehicleElement reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  /**
   * 機体予約ID
   * @return reservationId
  */
  @Schema(description = "機体予約ID")

  @Valid

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public VehicleElement vehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
    return this;
  }

  /**
   * 機体ID
   * @return vehicleId
  */
  @Schema(description = "機体ID")


  public String getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(String vehicleId) {
    this.vehicleId = vehicleId;
  }

  public VehicleElement aircraftInfo(AircraftInfo aircraftInfo) {
    this.aircraftInfo = aircraftInfo;
    return this;
  }

  /**
   * Get aircraftInfo
   * @return aircraftInfo
  */
  @Schema(description = "")

  @Valid

  public AircraftInfo getAircraftInfo() {
    return aircraftInfo;
  }

  public void setAircraftInfo(AircraftInfo aircraftInfo) {
    this.aircraftInfo = aircraftInfo;
  }

  public VehicleElement startAt(String startAt) {
    this.startAt = startAt;
    return this;
  }

  /**
   * 予約開始時間（RFC3339形式）
   * @return startAt
  */
  @Schema(description = "予約開始時間（RFC3339形式）")

  @Valid

  public String getStartAt() {
    return startAt;
  }

  public void setStartAt(String startAt) {
    this.startAt = startAt;
  }

  public VehicleElement endAt(String endAt) {
    this.endAt = endAt;
    return this;
  }

  /**
   * 予約終了時間（RFC3339形式）
   * @return endAt
  */
  @Schema(description = "予約終了時間（RFC3339形式）")

  @Valid

  public String getEndAt() {
    return endAt;
  }

  public void setEndAt(String endAt) {
    this.endAt = endAt;
  }

  public VehicleElement amount(Integer amount) {
    this.amount = amount;
    return this;
  }

  /**
   * 機体利用料金(税込)
   * @return amount
  */
  @Schema(description = "機体利用料金(税込)")


  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleElement vehicleElement = (VehicleElement) o;
    return Objects.equals(this.reservationId, vehicleElement.reservationId) &&
        Objects.equals(this.vehicleId, vehicleElement.vehicleId) &&
        Objects.equals(this.aircraftInfo, vehicleElement.aircraftInfo) &&
        Objects.equals(this.startAt, vehicleElement.startAt) &&
        Objects.equals(this.endAt, vehicleElement.endAt) &&
        Objects.equals(this.amount, vehicleElement.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationId, vehicleId, aircraftInfo, startAt, endAt, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleElement {\n");
    
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    aircraftInfo: ").append(toIndentedString(aircraftInfo)).append("\n");
    sb.append("    startAt: ").append(toIndentedString(startAt)).append("\n");
    sb.append("    endAt: ").append(toIndentedString(endAt)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

