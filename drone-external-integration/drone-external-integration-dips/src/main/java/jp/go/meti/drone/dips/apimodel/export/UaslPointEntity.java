/*
* 開発システム： ドローン航路基盤システム
* ファイル名： UaslPointEntity.java
* 著作権： Copyright (C) 202X-20XX,  経済産業省
* 会社名： NTT DATA Corporation
* 更新日： $Date$
*
*/
package jp.go.meti.drone.dips.apimodel.export;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * UaslPointEntity
 */

@JsonTypeName("uaslPointEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-13T10:34:08.568609+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UaslPointEntity {

  private String uaslPointId;

  private String uaslPointName;

  private UaslPointEntityGeometry geometry;

  private UaslPointEntityGeometry deviationGeometry;

  public UaslPointEntity uaslPointId(String uaslPointId) {
    this.uaslPointId = uaslPointId;
    return this;
  }

  /**
   * 航路点ID
   * @return uaslPointId
   */
  
  @Schema(name = "uaslPointId", description = "航路点ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslPointId")
  public String getUaslPointId() {
    return uaslPointId;
  }

  public void setUaslPointId(String uaslPointId) {
    this.uaslPointId = uaslPointId;
  }

  public UaslPointEntity uaslPointName(String uaslPointName) {
    this.uaslPointName = uaslPointName;
    return this;
  }

  /**
   * 航路点名
   * @return uaslPointName
   */
  
  @Schema(name = "uaslPointName", description = "航路点名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslPointName")
  public String getUaslPointName() {
    return uaslPointName;
  }

  public void setUaslPointName(String uaslPointName) {
    this.uaslPointName = uaslPointName;
  }

  public UaslPointEntity geometry(UaslPointEntityGeometry geometry) {
    this.geometry = geometry;
    return this;
  }

  /**
   * 航路点ジオメトリー
   * Get geometry
   * @return geometry
   */
  @Valid 
  @Schema(name = "geometry", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("geometry")
  public UaslPointEntityGeometry getGeometry() {
    return geometry;
  }

  public void setGeometry(UaslPointEntityGeometry geometry) {
    this.geometry = geometry;
  }

  public UaslPointEntity deviationGeometry(UaslPointEntityGeometry deviationGeometry) {
    this.deviationGeometry = deviationGeometry;
    return this;
  }

  /**
   * 航路逸脱領域ジオメトリー
   * Get deviationGeometry
   * @return deviationGeometry
   */
  @Valid 
  @Schema(name = "deviationGeometry", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deviationGeometry")
  public UaslPointEntityGeometry getDeviationGeometry() {
    return deviationGeometry;
  }

  public void setDeviationGeometry(UaslPointEntityGeometry deviationGeometry) {
    this.deviationGeometry = deviationGeometry;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UaslPointEntity uaslPointEntity = (UaslPointEntity) o;
    return Objects.equals(this.uaslPointId, uaslPointEntity.uaslPointId) &&
        Objects.equals(this.uaslPointName, uaslPointEntity.uaslPointName) &&
        Objects.equals(this.geometry, uaslPointEntity.geometry) &&
        Objects.equals(this.deviationGeometry, uaslPointEntity.deviationGeometry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uaslPointId, uaslPointName, geometry, deviationGeometry);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslPointEntity {\n");
    sb.append("    uaslPointId: ").append(toIndentedString(uaslPointId)).append("\n");
    sb.append("    uaslPointName: ").append(toIndentedString(uaslPointName)).append("\n");
    sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
    sb.append("    deviationGeometry: ").append(toIndentedString(deviationGeometry)).append("\n");
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

