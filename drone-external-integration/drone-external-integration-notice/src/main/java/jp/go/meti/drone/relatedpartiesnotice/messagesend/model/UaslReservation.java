package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * 乗り入れ元航路（主航路）の予約通知ペイロード。originReservation配下に主航路の予約情報、destinationReservations配下に乗り入れ先航路の予約情報を含む。
 */
@Schema(description = "乗り入れ元航路（主航路）の予約通知ペイロード。originReservation配下に主航路の予約情報、destinationReservations配下に乗り入れ先航路の予約情報を含む。")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UaslReservation   {
  @JsonProperty("eventId")
  private String eventId;

  @JsonProperty("requestId")
  private String requestId;

  @JsonProperty("operatorId")
  private String operatorId;

  @JsonProperty("flightPurpose")
  private String flightPurpose;

  /**
   * 予約状態（RESERVED: 予約済み / CANCELED: （運航者から）取消済み / RESCINDED: （航路運営者から）撤回済み）
   */
  public enum StatusEnum {
    RESERVED("RESERVED"),
    
    CANCELED("CANCELED"),
    
    RESCINDED("RESCINDED");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("status")
  private StatusEnum status;

  @JsonProperty("totalAmount")
  private Integer totalAmount;

  @JsonProperty("reservedAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String reservedAt;

  @JsonProperty("estimatedAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String estimatedAt;

  @JsonProperty("updatedAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String updatedAt;

  @JsonProperty("originReservation")
  private ReservationEntity originReservation;

  @JsonProperty("destinationReservations")
  @Valid
  private List<DestinationReservationEntity> destinationReservations = null;

  public UaslReservation eventId(String eventId) {
    this.eventId = eventId;
    return this;
  }

  /**
   * PublishイベントID（MQTTメッセージごとに採番されるユニークID）
   * @return eventId
  */
  @Schema(description = "PublishイベントID（MQTTメッセージごとに採番されるユニークID）")

  @Valid

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public UaslReservation requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * 親予約ID ※REST側のrequestIdと同一
   * @return requestId
  */
  @Schema(description = "親予約ID ※REST側のrequestIdと同一")

  @Valid

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public UaslReservation operatorId(String operatorId) {
    this.operatorId = operatorId;
    return this;
  }

  /**
   * 運航事業者（予約者）ID
   * @return operatorId
  */
  @Schema(description = "運航事業者（予約者）ID")


  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  public UaslReservation flightPurpose(String flightPurpose) {
    this.flightPurpose = flightPurpose;
    return this;
  }

  /**
   * 飛行目的
   * @return flightPurpose
  */
  @Schema(description = "飛行目的")


  public String getFlightPurpose() {
    return flightPurpose;
  }

  public void setFlightPurpose(String flightPurpose) {
    this.flightPurpose = flightPurpose;
  }

  public UaslReservation status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * 予約状態（RESERVED: 予約済み / CANCELED: （運航者から）取消済み / RESCINDED: （航路運営者から）撤回済み）
   * @return status
  */
  @Schema(description = "予約状態（RESERVED: 予約済み / CANCELED: （運航者から）取消済み / RESCINDED: （航路運営者から）撤回済み）")


  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public UaslReservation totalAmount(Integer totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  /**
   * 合計金額(税込)
   * @return totalAmount
  */
  @Schema(description = "合計金額(税込)")


  public Integer getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Integer totalAmount) {
    this.totalAmount = totalAmount;
  }

  public UaslReservation reservedAt(String reservedAt) {
    this.reservedAt = reservedAt;
    return this;
  }

  /**
   * 予約完了日時
   * @return reservedAt
  */
  @Schema(description = "予約完了日時")

  @Valid

  public String getReservedAt() {
    return reservedAt;
  }

  public void setReservedAt(String reservedAt) {
    this.reservedAt = reservedAt;
  }

  public UaslReservation estimatedAt(String estimatedAt) {
    this.estimatedAt = estimatedAt;
    return this;
  }

  /**
   * 予約完了日時
   * @return estimatedAt
  */
  @Schema(description = "予約完了日時")

  @Valid

  public String getEstimatedAt() {
    return estimatedAt;
  }

  public void setEstimatedAt(String estimatedAt) {
    this.estimatedAt = estimatedAt;
  }

  public UaslReservation updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * 予約状態更新日時（登録時も更新）
   * @return updatedAt
  */
  @Schema(description = "予約状態更新日時（登録時も更新）")

  @Valid

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public UaslReservation originReservation(ReservationEntity originReservation) {
    this.originReservation = originReservation;
    return this;
  }

  /**
   * Get originReservation
   * @return originReservation
  */
  @Schema(description = "")

  @Valid

  public ReservationEntity getOriginReservation() {
    return originReservation;
  }

  public void setOriginReservation(ReservationEntity originReservation) {
    this.originReservation = originReservation;
  }

  public UaslReservation destinationReservations(List<DestinationReservationEntity> destinationReservations) {
    this.destinationReservations = destinationReservations;
    return this;
  }

  public UaslReservation addDestinationReservationsItem(DestinationReservationEntity destinationReservationsItem) {
    if (this.destinationReservations == null) {
      this.destinationReservations = new ArrayList<>();
    }
    this.destinationReservations.add(destinationReservationsItem);
    return this;
  }

  /**
   * 相互乗り入れ先の航路予約情報（単一航路の場合は空配列）
   * @return destinationReservations
  */
  @Schema(description = "相互乗り入れ先の航路予約情報（単一航路の場合は空配列）")

  @Valid

  public List<DestinationReservationEntity> getDestinationReservations() {
    return destinationReservations;
  }

  public void setDestinationReservations(List<DestinationReservationEntity> destinationReservations) {
    this.destinationReservations = destinationReservations;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UaslReservation uaslReservation = (UaslReservation) o;
    return Objects.equals(this.eventId, uaslReservation.eventId) &&
        Objects.equals(this.requestId, uaslReservation.requestId) &&
        Objects.equals(this.operatorId, uaslReservation.operatorId) &&
        Objects.equals(this.flightPurpose, uaslReservation.flightPurpose) &&
        Objects.equals(this.status, uaslReservation.status) &&
        Objects.equals(this.totalAmount, uaslReservation.totalAmount) &&
        Objects.equals(this.reservedAt, uaslReservation.reservedAt) &&
        Objects.equals(this.estimatedAt, uaslReservation.estimatedAt) &&
        Objects.equals(this.updatedAt, uaslReservation.updatedAt) &&
        Objects.equals(this.originReservation, uaslReservation.originReservation) &&
        Objects.equals(this.destinationReservations, uaslReservation.destinationReservations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, requestId, operatorId, flightPurpose, status, totalAmount, reservedAt, estimatedAt, updatedAt, originReservation, destinationReservations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslReservation {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    operatorId: ").append(toIndentedString(operatorId)).append("\n");
    sb.append("    flightPurpose: ").append(toIndentedString(flightPurpose)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    reservedAt: ").append(toIndentedString(reservedAt)).append("\n");
    sb.append("    estimatedAt: ").append(toIndentedString(estimatedAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    originReservation: ").append(toIndentedString(originReservation)).append("\n");
    sb.append("    destinationReservations: ").append(toIndentedString(destinationReservations)).append("\n");
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

