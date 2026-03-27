package jp.go.meti.drone.dips.apimodel.export;


import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;



import javax.annotation.Generated;

/**
 * UaslEntity
 */

@JsonTypeName("uaslEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-13T10:34:08.568609+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class UaslEntity {

  private String uaslId;

  private String uaslName;

  private String flightPurpose;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  @Valid
  private List<BigDecimal> droneList = new ArrayList<>();

  @Valid
  private List<@Valid UaslPointEntity> uaslPoints = new ArrayList<>();

  @Valid
  private List<@Valid UaslSectionsEntity> uaslSections = new ArrayList<>();

  public UaslEntity uaslId(String uaslId) {
    this.uaslId = uaslId;
    return this;
  }

  /**
   * 航路ID
   * @return uaslId
   */
  
  @Schema(name = "uaslId", description = "航路ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslId")
  public String getUaslId() {
    return uaslId;
  }

  public void setUaslId(String uaslId) {
    this.uaslId = uaslId;
  }

  public UaslEntity uaslName(String uaslName) {
    this.uaslName = uaslName;
    return this;
  }

  /**
   * 航路名
   * @return uaslName
   */
  
  @Schema(name = "uaslName", description = "航路名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslName")
  public String getUaslName() {
    return uaslName;
  }

  public void setUaslName(String uaslName) {
    this.uaslName = uaslName;
  }

  public UaslEntity flightPurpose(String flightPurpose) {
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

  public UaslEntity createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * 登録日時
   * @return createdAt
   */
  @Valid 
  @Schema(name = "createdAt", example = "2025-01-24T05:36:26.021Z", description = "登録日時", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UaslEntity updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * 更新日時
   * @return updatedAt
   */
  @Valid 
  @Schema(name = "updatedAt", example = "2025-01-24T05:36:26.021Z", description = "更新日時", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public UaslEntity droneList(List<BigDecimal> droneList) {
    this.droneList = droneList;
    return this;
  }

  public UaslEntity addDroneListItem(BigDecimal droneListItem) {
    if (this.droneList == null) {
      this.droneList = new ArrayList<>();
    }
    this.droneList.add(droneListItem);
    return this;
  }

  /**
   * 航路を利用可能なドローンの機体情報IDのリスト
   * @return droneList
   */
  @Valid 
  @Schema(name = "droneList", example = "[0,1]", description = "航路を利用可能なドローンの機体情報IDのリスト", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("droneList")
  public List<BigDecimal> getDroneList() {
    return droneList;
  }

  public void setDroneList(List<BigDecimal> droneList) {
    this.droneList = droneList;
  }

  public UaslEntity uaslPoints(List<@Valid UaslPointEntity> uaslPoints) {
    this.uaslPoints = uaslPoints;
    return this;
  }

  public UaslEntity addUaslPointsItem(UaslPointEntity uaslPointsItem) {
    if (this.uaslPoints == null) {
      this.uaslPoints = new ArrayList<>();
    }
    this.uaslPoints.add(uaslPointsItem);
    return this;
  }

  /**
   * 航路点
   * @return uaslPoints
   */
  @Valid 
  @Schema(name = "uaslPoints", description = "航路点", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslPoints")
  public List<@Valid UaslPointEntity> getUaslPoints() {
    return uaslPoints;
  }

  public void setUaslPoints(List<@Valid UaslPointEntity> uaslPoints) {
    this.uaslPoints = uaslPoints;
  }

  public UaslEntity uaslSections(List<@Valid UaslSectionsEntity> uaslSections) {
    this.uaslSections = uaslSections;
    return this;
  }

  public UaslEntity addUaslSectionsItem(UaslSectionsEntity uaslSectionsItem) {
    if (this.uaslSections == null) {
      this.uaslSections = new ArrayList<>();
    }
    this.uaslSections.add(uaslSectionsItem);
    return this;
  }

  /**
   * Get uaslSections
   * @return uaslSections
   */
  @Valid 
  @Schema(name = "uaslSections", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslSections")
  public List<@Valid UaslSectionsEntity> getUaslSections() {
    return uaslSections;
  }

  public void setUaslSections(List<@Valid UaslSectionsEntity> uaslSections) {
    this.uaslSections = uaslSections;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UaslEntity uaslEntity = (UaslEntity) o;
    return Objects.equals(this.uaslId, uaslEntity.uaslId) &&
        Objects.equals(this.uaslName, uaslEntity.uaslName) &&
        Objects.equals(this.flightPurpose, uaslEntity.flightPurpose) &&
        Objects.equals(this.createdAt, uaslEntity.createdAt) &&
        Objects.equals(this.updatedAt, uaslEntity.updatedAt) &&
        Objects.equals(this.droneList, uaslEntity.droneList) &&
        Objects.equals(this.uaslPoints, uaslEntity.uaslPoints) &&
        Objects.equals(this.uaslSections, uaslEntity.uaslSections);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uaslId, uaslName, flightPurpose, createdAt, updatedAt, droneList, uaslPoints, uaslSections);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslEntity {\n");
    sb.append("    uaslId: ").append(toIndentedString(uaslId)).append("\n");
    sb.append("    uaslName: ").append(toIndentedString(uaslName)).append("\n");
    sb.append("    flightPurpose: ").append(toIndentedString(flightPurpose)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    droneList: ").append(toIndentedString(droneList)).append("\n");
    sb.append("    uaslPoints: ").append(toIndentedString(uaslPoints)).append("\n");
    sb.append("    uaslSections: ").append(toIndentedString(uaslSections)).append("\n");
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

