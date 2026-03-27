package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * 乗り入れ元航路の予約情報
 */
@Schema(description = "乗り入れ元航路の予約情報")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReservationEntity   {
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
  private List<UaslSectionEntity> uaslSections = null;

  @JsonProperty("ports")
  @Valid
  private List<PortElement> ports = null;

  @JsonProperty("vehicles")
  @Valid
  private List<VehicleElement> vehicles = null;

  @JsonProperty("conflictedFlightPlanIds")
  @Valid
  private List<String> conflictedFlightPlanIds = null;

  @JsonProperty("conformityAssessmentResults")
  @Valid
  private List<ConformityAssessmentResult> conformityAssessmentResults = null;

  public ReservationEntity reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  /**
   * 航路予約ID
   * @return reservationId
  */
  @Schema(description = "航路予約ID")

  @Valid

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public ReservationEntity uaslId(String uaslId) {
    this.uaslId = uaslId;
    return this;
  }

  /**
   * 当該予約が属する航路のID
   * @return uaslId
  */
  @Schema(description = "当該予約が属する航路のID")


  public String getUaslId() {
    return uaslId;
  }

  public void setUaslId(String uaslId) {
    this.uaslId = uaslId;
  }

  public ReservationEntity administratorId(String administratorId) {
    this.administratorId = administratorId;
    return this;
  }

  /**
   * 当該予約が属する航路の運営者ID
   * @return administratorId
  */
  @Schema(description = "当該予約が属する航路の運営者ID")


  public String getAdministratorId() {
    return administratorId;
  }

  public void setAdministratorId(String administratorId) {
    this.administratorId = administratorId;
  }

  public ReservationEntity subTotalAmount(Integer subTotalAmount) {
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

  public ReservationEntity uaslSections(List<UaslSectionEntity> uaslSections) {
    this.uaslSections = uaslSections;
    return this;
  }

  public ReservationEntity addUaslSectionsItem(UaslSectionEntity uaslSectionsItem) {
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

  public List<UaslSectionEntity> getUaslSections() {
    return uaslSections;
  }

  public void setUaslSections(List<UaslSectionEntity> uaslSections) {
    this.uaslSections = uaslSections;
  }

  public ReservationEntity ports(List<PortElement> ports) {
    this.ports = ports;
    return this;
  }

  public ReservationEntity addPortsItem(PortElement portsItem) {
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

  public List<PortElement> getPorts() {
    return ports;
  }

  public void setPorts(List<PortElement> ports) {
    this.ports = ports;
  }

  public ReservationEntity vehicles(List<VehicleElement> vehicles) {
    this.vehicles = vehicles;
    return this;
  }

  public ReservationEntity addVehiclesItem(VehicleElement vehiclesItem) {
    if (this.vehicles == null) {
      this.vehicles = new ArrayList<>();
    }
    this.vehicles.add(vehiclesItem);
    return this;
  }

  /**
   * 機体予約情報
   * @return vehicles
  */
  @Schema(description = "機体予約情報")

  @Valid

  public List<VehicleElement> getVehicles() {
    return vehicles;
  }

  public void setVehicles(List<VehicleElement> vehicles) {
    this.vehicles = vehicles;
  }

  public ReservationEntity conflictedFlightPlanIds(List<String> conflictedFlightPlanIds) {
    this.conflictedFlightPlanIds = conflictedFlightPlanIds;
    return this;
  }

  public ReservationEntity addConflictedFlightPlanIdsItem(String conflictedFlightPlanIdsItem) {
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

  public ReservationEntity conformityAssessmentResults(List<ConformityAssessmentResult> conformityAssessmentResults) {
    this.conformityAssessmentResults = conformityAssessmentResults;
    return this;
  }

  public ReservationEntity addConformityAssessmentResultsItem(ConformityAssessmentResult conformityAssessmentResultsItem) {
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

  public List<ConformityAssessmentResult> getConformityAssessmentResults() {
    return conformityAssessmentResults;
  }

  public void setConformityAssessmentResults(List<ConformityAssessmentResult> conformityAssessmentResults) {
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
    ReservationEntity reservationEntity = (ReservationEntity) o;
    return Objects.equals(this.reservationId, reservationEntity.reservationId) &&
        Objects.equals(this.uaslId, reservationEntity.uaslId) &&
        Objects.equals(this.administratorId, reservationEntity.administratorId) &&
        Objects.equals(this.subTotalAmount, reservationEntity.subTotalAmount) &&
        Objects.equals(this.uaslSections, reservationEntity.uaslSections) &&
        Objects.equals(this.ports, reservationEntity.ports) &&
        Objects.equals(this.vehicles, reservationEntity.vehicles) &&
        Objects.equals(this.conflictedFlightPlanIds, reservationEntity.conflictedFlightPlanIds) &&
        Objects.equals(this.conformityAssessmentResults, reservationEntity.conformityAssessmentResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reservationId, uaslId, administratorId, subTotalAmount, uaslSections, ports, vehicles, conflictedFlightPlanIds, conformityAssessmentResults);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationEntity {\n");
    
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    uaslId: ").append(toIndentedString(uaslId)).append("\n");
    sb.append("    administratorId: ").append(toIndentedString(administratorId)).append("\n");
    sb.append("    subTotalAmount: ").append(toIndentedString(subTotalAmount)).append("\n");
    sb.append("    uaslSections: ").append(toIndentedString(uaslSections)).append("\n");
    sb.append("    ports: ").append(toIndentedString(ports)).append("\n");
    sb.append("    vehicles: ").append(toIndentedString(vehicles)).append("\n");
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

