package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * 乗り入れ先航路の予約情報
 */
@Schema(description = "乗り入れ先航路の予約情報")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DestinationReservationEntity   {
  @JsonProperty("reservationId")
  private String reservationId;

  @JsonProperty("uaslId")
  private String uaslId;

  @JsonProperty("administratorId")
  private String administratorId;

  @JsonProperty("subTotalAmount")
  private Integer subTotalAmount;

  @JsonProperty("uaslSections")
  @Valid
  private List<DestinationUaslSectionEntity> uaslSections = null;

  @JsonProperty("ports")
  @Valid
  private List<DestinationPortElement> ports = null;

  @JsonProperty("conflictedFlightPlanIds")
  @Valid
  private List<String> conflictedFlightPlanIds = null;

  @JsonProperty("conformityAssessmentResults")
  @Valid
  private List<DestinationConformityAssessmentResult> conformityAssessmentResults = null;

  public DestinationReservationEntity reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  /**
   * 乗り入れ先航路の航路予約ID
   * @return reservationId
  */
  @Schema(description = "乗り入れ先航路の航路予約ID")

  @Valid

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public DestinationReservationEntity uaslId(String uaslId) {
    this.uaslId = uaslId;
    return this;
  }

  /**
   * 乗り入れ先航路の航路ID
   * @return uaslId
  */
  @Schema(description = "乗り入れ先航路の航路ID")


  public String getUaslId() {
    return uaslId;
  }

  public void setUaslId(String uaslId) {
    this.uaslId = uaslId;
  }

  public DestinationReservationEntity administratorId(String administratorId) {
    this.administratorId = administratorId;
    return this;
  }

  /**
   * 乗り入れ先航路の航路運営者ID
   * @return administratorId
  */
  @Schema(description = "乗り入れ先航路の航路運営者ID")


  public String getAdministratorId() {
    return administratorId;
  }

  public void setAdministratorId(String administratorId) {
    this.administratorId = administratorId;
  }

  public DestinationReservationEntity subTotalAmount(Integer subTotalAmount) {
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

  public DestinationReservationEntity uaslSections(List<DestinationUaslSectionEntity> uaslSections) {
    this.uaslSections = uaslSections;
    return this;
  }

  public DestinationReservationEntity addUaslSectionsItem(DestinationUaslSectionEntity uaslSectionsItem) {
    if (this.uaslSections == null) {
      this.uaslSections = new ArrayList<>();
    }
    this.uaslSections.add(uaslSectionsItem);
    return this;
  }

  /**
   * 乗り入れ先航路の航路区画毎の予約内容
   * @return uaslSections
  */
  @Schema(description = "乗り入れ先航路の航路区画毎の予約内容")

  @Valid

  public List<DestinationUaslSectionEntity> getUaslSections() {
    return uaslSections;
  }

  public void setUaslSections(List<DestinationUaslSectionEntity> uaslSections) {
    this.uaslSections = uaslSections;
  }

  public DestinationReservationEntity ports(List<DestinationPortElement> ports) {
    this.ports = ports;
    return this;
  }

  public DestinationReservationEntity addPortsItem(DestinationPortElement portsItem) {
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

  public DestinationReservationEntity conflictedFlightPlanIds(List<String> conflictedFlightPlanIds) {
    this.conflictedFlightPlanIds = conflictedFlightPlanIds;
    return this;
  }

  public DestinationReservationEntity addConflictedFlightPlanIdsItem(String conflictedFlightPlanIdsItem) {
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

  public DestinationReservationEntity conformityAssessmentResults(List<DestinationConformityAssessmentResult> conformityAssessmentResults) {
    this.conformityAssessmentResults = conformityAssessmentResults;
    return this;
  }

  public DestinationReservationEntity addConformityAssessmentResultsItem(DestinationConformityAssessmentResult conformityAssessmentResultsItem) {
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
    DestinationReservationEntity destinationReservationEntity = (DestinationReservationEntity) o;
    return Objects.equals(this.reservationId, destinationReservationEntity.reservationId) &&
        Objects.equals(this.uaslId, destinationReservationEntity.uaslId) &&
        Objects.equals(this.administratorId, destinationReservationEntity.administratorId) &&
        Objects.equals(this.subTotalAmount, destinationReservationEntity.subTotalAmount) &&
        Objects.equals(this.uaslSections, destinationReservationEntity.uaslSections) &&
        Objects.equals(this.ports, destinationReservationEntity.ports) &&
        Objects.equals(this.conflictedFlightPlanIds, destinationReservationEntity.conflictedFlightPlanIds) &&
        Objects.equals(this.conformityAssessmentResults, destinationReservationEntity.conformityAssessmentResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationId, uaslId, administratorId, subTotalAmount, uaslSections, ports, conflictedFlightPlanIds, conformityAssessmentResults);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DestinationReservationEntity {\n");
    
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    uaslId: ").append(toIndentedString(uaslId)).append("\n");
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
    sb.append("    subTotalAmount: ").append(toIndentedString(subTotalAmount)).append("\n");
    sb.append("    uaslSections: ").append(toIndentedString(uaslSections)).append("\n");
    sb.append("    ports: ").append(toIndentedString(ports)).append("\n");
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

