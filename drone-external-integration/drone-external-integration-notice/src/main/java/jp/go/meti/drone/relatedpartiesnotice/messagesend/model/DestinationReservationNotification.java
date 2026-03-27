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
 * 乗り入れ先航路の予約通知ペイロード。当該航路に関する予約情報のみをフラットに含む。
 */
@Schema(description = "乗り入れ先航路の予約通知ペイロード。当該航路に関する予約情報のみをフラットに含む。")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T14:02:58.812+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DestinationReservationNotification   {
  @JsonProperty("eventId")
  private String eventId;

  @JsonProperty("requestId")
  private String requestId;

  @JsonProperty("reservationId")
  private String reservationId;

  @JsonProperty("operatorId")
  private String operatorId;

  @JsonProperty("uaslId")
  private String uaslId;

  @JsonProperty("administratorId")
  private String administratorId;

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

  @JsonProperty("subTotalAmount")
  private Integer subTotalAmount;

  @JsonProperty("reservedAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String reservedAt;

  @JsonProperty("estimatedAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String estimatedAt;

  @JsonProperty("updatedAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String updatedAt;

  @JsonProperty("uaslSections")
  @Valid
  private List<DestinationUaslSectionEntity> uaslSections = null;

  @JsonProperty("ports")
  @Valid
  private List<DestinationPortElement> ports = null;

  @JsonProperty("operatingAircrafts")
  @Valid
  private List<AircraftInfo> operatingAircrafts = null;

  @JsonProperty("conflictedFlightPlanIds")
  @Valid
  private List<String> conflictedFlightPlanIds = null;

  @JsonProperty("conformityAssessmentResults")
  @Valid
  private List<DestinationConformityAssessmentResult> conformityAssessmentResults = null;

  public DestinationReservationNotification eventId(String eventId) {
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

  public DestinationReservationNotification requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * 親予約ID（乗り入れ先航路の親予約ID）※REST側のrequestIdと同一
   * @return requestId
  */
  @Schema(description = "親予約ID（乗り入れ先航路の親予約ID）※REST側のrequestIdと同一")

  @Valid

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public DestinationReservationNotification reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  /**
   * 当該航路の予約ID
   * @return reservationId
  */
  @Schema(description = "当該航路の予約ID")

  @Valid

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public DestinationReservationNotification operatorId(String operatorId) {
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

  public DestinationReservationNotification uaslId(String uaslId) {
    this.uaslId = uaslId;
    return this;
  }

  /**
   * 当該航路のID
   * @return uaslId
  */
  @Schema(description = "当該航路のID")


  public String getUaslId() {
    return uaslId;
  }

  public void setUaslId(String uaslId) {
    this.uaslId = uaslId;
  }

  public DestinationReservationNotification administratorId(String administratorId) {
    this.administratorId = administratorId;
    return this;
  }

  /**
   * 当該航路の運営者ID
   * @return administratorId
  */
  @Schema(description = "当該航路の運営者ID")


  public String getAdministratorId() {
    return administratorId;
  }

  public void setAdministratorId(String administratorId) {
    this.administratorId = administratorId;
  }

  public DestinationReservationNotification flightPurpose(String flightPurpose) {
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

  public DestinationReservationNotification status(StatusEnum status) {
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

  public DestinationReservationNotification subTotalAmount(Integer subTotalAmount) {
    this.subTotalAmount = subTotalAmount;
    return this;
  }

  /**
   * 当該航路の利用料金小計(税込)
   * @return subTotalAmount
  */
  @Schema(description = "当該航路の利用料金小計(税込)")


  public Integer getSubTotalAmount() {
    return subTotalAmount;
  }

  public void setSubTotalAmount(Integer subTotalAmount) {
    this.subTotalAmount = subTotalAmount;
  }

  public DestinationReservationNotification reservedAt(String reservedAt) {
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

  public DestinationReservationNotification estimatedAt(String estimatedAt) {
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

  public DestinationReservationNotification updatedAt(String updatedAt) {
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

  public DestinationReservationNotification uaslSections(List<DestinationUaslSectionEntity> uaslSections) {
    this.uaslSections = uaslSections;
    return this;
  }

  public DestinationReservationNotification addUaslSectionsItem(DestinationUaslSectionEntity uaslSectionsItem) {
    if (this.uaslSections == null) {
      this.uaslSections = new ArrayList<>();
    }
    this.uaslSections.add(uaslSectionsItem);
    return this;
  }

  /**
   * 航路区画毎の予約内容
   * @return uaslSections
  */
  @Schema(description = "航路区画毎の予約内容")

  @Valid

  public List<DestinationUaslSectionEntity> getUaslSections() {
    return uaslSections;
  }

  public void setUaslSections(List<DestinationUaslSectionEntity> uaslSections) {
    this.uaslSections = uaslSections;
  }

  public DestinationReservationNotification ports(List<DestinationPortElement> ports) {
    this.ports = ports;
    return this;
  }

  public DestinationReservationNotification addPortsItem(DestinationPortElement portsItem) {
    if (this.ports == null) {
      this.ports = new ArrayList<>();
    }
    this.ports.add(portsItem);
    return this;
  }

  /**
   * 離着陸場予約情報
   * @return ports
  */
  @Schema(description = "離着陸場予約情報")

  @Valid

  public List<DestinationPortElement> getPorts() {
    return ports;
  }

  public void setPorts(List<DestinationPortElement> ports) {
    this.ports = ports;
  }

  public DestinationReservationNotification operatingAircrafts(List<AircraftInfo> operatingAircrafts) {
    this.operatingAircrafts = operatingAircrafts;
    return this;
  }

  public DestinationReservationNotification addOperatingAircraftsItem(AircraftInfo operatingAircraftsItem) {
    if (this.operatingAircrafts == null) {
      this.operatingAircrafts = new ArrayList<>();
    }
    this.operatingAircrafts.add(operatingAircraftsItem);
    return this;
  }

  /**
   * 乗り入れ元で予約された機体情報
   * @return operatingAircrafts
  */
  @Schema(description = "乗り入れ元で予約された機体情報")

  @Valid

  public List<AircraftInfo> getOperatingAircrafts() {
    return operatingAircrafts;
  }

  public void setOperatingAircrafts(List<AircraftInfo> operatingAircrafts) {
    this.operatingAircrafts = operatingAircrafts;
  }

  public DestinationReservationNotification conflictedFlightPlanIds(List<String> conflictedFlightPlanIds) {
    this.conflictedFlightPlanIds = conflictedFlightPlanIds;
    return this;
  }

  public DestinationReservationNotification addConflictedFlightPlanIdsItem(String conflictedFlightPlanIdsItem) {
    if (this.conflictedFlightPlanIds == null) {
      this.conflictedFlightPlanIds = new ArrayList<>();
    }
    this.conflictedFlightPlanIds.add(conflictedFlightPlanIdsItem);
    return this;
  }

  /**
   * 空域干渉している飛行計画IDリスト（空配列の場合は干渉なし） <br />※基本的に干渉した場合は予約自体がなされないためMQTT Publish処理まで到達しない。 
   * @return conflictedFlightPlanIds
  */
  @Schema(description = "空域干渉している飛行計画IDリスト（空配列の場合は干渉なし） <br />※基本的に干渉した場合は予約自体がなされないためMQTT Publish処理まで到達しない。 ")


  public List<String> getConflictedFlightPlanIds() {
    return conflictedFlightPlanIds;
  }

  public void setConflictedFlightPlanIds(List<String> conflictedFlightPlanIds) {
    this.conflictedFlightPlanIds = conflictedFlightPlanIds;
  }

  public DestinationReservationNotification conformityAssessmentResults(List<DestinationConformityAssessmentResult> conformityAssessmentResults) {
    this.conformityAssessmentResults = conformityAssessmentResults;
    return this;
  }

  public DestinationReservationNotification addConformityAssessmentResultsItem(DestinationConformityAssessmentResult conformityAssessmentResultsItem) {
    if (this.conformityAssessmentResults == null) {
      this.conformityAssessmentResults = new ArrayList<>();
    }
    this.conformityAssessmentResults.add(conformityAssessmentResultsItem);
    return this;
  }

  /**
   * 適合性評価結果リスト（航路区画ごとの評価結果）
   * @return conformityAssessmentResults
  */
  @Schema(description = "適合性評価結果リスト（航路区画ごとの評価結果）")

  @Valid

  public List<DestinationConformityAssessmentResult> getConformityAssessmentResults() {
    return conformityAssessmentResults;
  }

  public void setConformityAssessmentResults(List<DestinationConformityAssessmentResult> conformityAssessmentResults) {
    this.conformityAssessmentResults = conformityAssessmentResults;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DestinationReservationNotification destinationReservationNotification = (DestinationReservationNotification) o;
    return Objects.equals(this.eventId, destinationReservationNotification.eventId) &&
        Objects.equals(this.requestId, destinationReservationNotification.requestId) &&
        Objects.equals(this.reservationId, destinationReservationNotification.reservationId) &&
        Objects.equals(this.operatorId, destinationReservationNotification.operatorId) &&
        Objects.equals(this.uaslId, destinationReservationNotification.uaslId) &&
        Objects.equals(this.administratorId, destinationReservationNotification.administratorId) &&
        Objects.equals(this.flightPurpose, destinationReservationNotification.flightPurpose) &&
        Objects.equals(this.status, destinationReservationNotification.status) &&
        Objects.equals(this.subTotalAmount, destinationReservationNotification.subTotalAmount) &&
        Objects.equals(this.reservedAt, destinationReservationNotification.reservedAt) &&
        Objects.equals(this.estimatedAt, destinationReservationNotification.estimatedAt) &&
        Objects.equals(this.updatedAt, destinationReservationNotification.updatedAt) &&
        Objects.equals(this.uaslSections, destinationReservationNotification.uaslSections) &&
        Objects.equals(this.ports, destinationReservationNotification.ports) &&
        Objects.equals(this.operatingAircrafts, destinationReservationNotification.operatingAircrafts) &&
        Objects.equals(this.conflictedFlightPlanIds, destinationReservationNotification.conflictedFlightPlanIds) &&
        Objects.equals(this.conformityAssessmentResults, destinationReservationNotification.conformityAssessmentResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, requestId, reservationId, operatorId, uaslId, administratorId, flightPurpose, status, subTotalAmount, reservedAt, estimatedAt, updatedAt, uaslSections, ports, operatingAircrafts, conflictedFlightPlanIds, conformityAssessmentResults);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DestinationReservationNotification {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    operatorId: ").append(toIndentedString(operatorId)).append("\n");
    sb.append("    uaslId: ").append(toIndentedString(uaslId)).append("\n");
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
    sb.append("    flightPurpose: ").append(toIndentedString(flightPurpose)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    subTotalAmount: ").append(toIndentedString(subTotalAmount)).append("\n");
    sb.append("    reservedAt: ").append(toIndentedString(reservedAt)).append("\n");
    sb.append("    estimatedAt: ").append(toIndentedString(estimatedAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    uaslSections: ").append(toIndentedString(uaslSections)).append("\n");
    sb.append("    ports: ").append(toIndentedString(ports)).append("\n");
    sb.append("    operatingAircrafts: ").append(toIndentedString(operatingAircrafts)).append("\n");
    sb.append("    conflictedFlightPlanIds: ").append(toIndentedString(conflictedFlightPlanIds)).append("\n");
    sb.append("    conformityAssessmentResults: ").append(toIndentedString(conformityAssessmentResults)).append("\n");
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

