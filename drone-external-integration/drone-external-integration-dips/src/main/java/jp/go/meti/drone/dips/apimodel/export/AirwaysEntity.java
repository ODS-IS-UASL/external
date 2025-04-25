package jp.go.meti.drone.dips.apimodel.export;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * AirwaysEntity
 */

@JsonTypeName("airwaysEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-24T17:06:21.206821500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AirwaysEntity {

  private String airwayId;

  private String airwayName;

  private String flightPurpose;

  private String createdAt;

  private String updatedAt;

  @Valid
  private List<Integer> droneList = new ArrayList<>();

  @Valid
  private List<@Valid AirwayJunctionsEntity> airwayJunctions = new ArrayList<>();

  @Valid
  private List<@Valid AirwaySectionsEntity> airwaySections = new ArrayList<>();

  public AirwaysEntity airwayId(String airwayId) {
    this.airwayId = airwayId;
    return this;
  }

  /**
   * 航路ID
   * @return airwayId
   */
  
  @Schema(name = "airwayId", description = "航路ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airwayId")
  public String getAirwayId() {
    return airwayId;
  }

  public void setAirwayId(String airwayId) {
    this.airwayId = airwayId;
  }

  public AirwaysEntity airwayName(String airwayName) {
    this.airwayName = airwayName;
    return this;
  }

  /**
   * 航路名
   * @return airwayName
   */
  
  @Schema(name = "airwayName", description = "航路名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airwayName")
  public String getAirwayName() {
    return airwayName;
  }

  public void setAirwayName(String airwayName) {
    this.airwayName = airwayName;
  }

  public AirwaysEntity flightPurpose(String flightPurpose) {
    this.flightPurpose = flightPurpose;
    return this;
  }

  /**
   * 飛行目的
   * @return flightPurpose
   */
  
  @Schema(name = "flightPurpose", description = "飛行目的", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flightPurpose")
  public String getFlightPurpose() {
    return flightPurpose;
  }

  public void setFlightPurpose(String flightPurpose) {
    this.flightPurpose = flightPurpose;
  }

  public AirwaysEntity createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * 作成日時
   * @return createdAt
   */
  
  @Schema(name = "createdAt", description = "作成日時", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public AirwaysEntity updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * 更新日時
   * @return updatedAt
   */
  
  @Schema(name = "updatedAt", description = "更新日時", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public AirwaysEntity droneList(List<Integer> droneList) {
    this.droneList = droneList;
    return this;
  }

  public AirwaysEntity addDroneListItem(Integer droneListItem) {
    if (this.droneList == null) {
      this.droneList = new ArrayList<>();
    }
    this.droneList.add(droneListItem);
    return this;
  }

  /**
   * 航路を利用可能なドローンの機体種別IDのリスト
   * @return droneList
   */
  
  @Schema(name = "droneList", description = "航路を利用可能なドローンの機体種別IDのリスト", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("droneList")
  public List<Integer> getDroneList() {
    return droneList;
  }

  public void setDroneList(List<Integer> droneList) {
    this.droneList = droneList;
  }

  public AirwaysEntity airwayJunctions(List<@Valid AirwayJunctionsEntity> airwayJunctions) {
    this.airwayJunctions = airwayJunctions;
    return this;
  }

  public AirwaysEntity addAirwayJunctionsItem(AirwayJunctionsEntity airwayJunctionsItem) {
    if (this.airwayJunctions == null) {
      this.airwayJunctions = new ArrayList<>();
    }
    this.airwayJunctions.add(airwayJunctionsItem);
    return this;
  }

  /**
   * ジャンクション
   * @return airwayJunctions
   */
  @Valid 
  @Schema(name = "airwayJunctions", description = "ジャンクション", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airwayJunctions")
  public List<@Valid AirwayJunctionsEntity> getAirwayJunctions() {
    return airwayJunctions;
  }

  public void setAirwayJunctions(List<@Valid AirwayJunctionsEntity> airwayJunctions) {
    this.airwayJunctions = airwayJunctions;
  }

  public AirwaysEntity airwaySections(List<@Valid AirwaySectionsEntity> airwaySections) {
    this.airwaySections = airwaySections;
    return this;
  }

  public AirwaysEntity addAirwaySectionsItem(AirwaySectionsEntity airwaySectionsItem) {
    if (this.airwaySections == null) {
      this.airwaySections = new ArrayList<>();
    }
    this.airwaySections.add(airwaySectionsItem);
    return this;
  }

  /**
   * Get airwaySections
   * @return airwaySections
   */
  @Valid 
  @Schema(name = "airwaySections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airwaySections")
  public List<@Valid AirwaySectionsEntity> getAirwaySections() {
    return airwaySections;
  }

  public void setAirwaySections(List<@Valid AirwaySectionsEntity> airwaySections) {
    this.airwaySections = airwaySections;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AirwaysEntity airwaysEntity = (AirwaysEntity) o;
    return Objects.equals(this.airwayId, airwaysEntity.airwayId) &&
        Objects.equals(this.airwayName, airwaysEntity.airwayName) &&
        Objects.equals(this.flightPurpose, airwaysEntity.flightPurpose) &&
        Objects.equals(this.createdAt, airwaysEntity.createdAt) &&
        Objects.equals(this.updatedAt, airwaysEntity.updatedAt) &&
        Objects.equals(this.droneList, airwaysEntity.droneList) &&
        Objects.equals(this.airwayJunctions, airwaysEntity.airwayJunctions) &&
        Objects.equals(this.airwaySections, airwaysEntity.airwaySections);
  }

  @Override
  public int hashCode() {
    return Objects.hash(airwayId, airwayName, flightPurpose, createdAt, updatedAt, droneList, airwayJunctions, airwaySections);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirwaysEntity {\n");
    sb.append("    airwayId: ").append(toIndentedString(airwayId)).append("\n");
    sb.append("    airwayName: ").append(toIndentedString(airwayName)).append("\n");
    sb.append("    flightPurpose: ").append(toIndentedString(flightPurpose)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    droneList: ").append(toIndentedString(droneList)).append("\n");
    sb.append("    airwayJunctions: ").append(toIndentedString(airwayJunctions)).append("\n");
    sb.append("    airwaySections: ").append(toIndentedString(airwaySections)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}