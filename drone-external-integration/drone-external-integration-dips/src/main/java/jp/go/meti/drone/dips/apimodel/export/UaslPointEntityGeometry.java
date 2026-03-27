/*
* 開発システム： ドローン航路基盤システム
* ファイル名： UaslPointEntityGeometry.java
* 著作権： Copyright (C) 202X-20XX,  経済産業省
* 会社名： NTT DATA Corporation
* 更新日： $Date$
*
*/
package jp.go.meti.drone.dips.apimodel.export;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * ジオメトリ geomjson形式のオブジェクト
 */

@Schema(name = "uaslPointEntity_geometry", description = "ジオメトリ geomjson形式のオブジェクト")
@JsonTypeName("uaslPointEntity_geometry")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-13T10:34:08.568609+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class UaslPointEntityGeometry {

  private String type;

  @Valid
  private List<List<List<BigDecimal>>> coordinates = new ArrayList<>();

  public UaslPointEntityGeometry type(String type) {
    this.type = type;
    return this;
  }

  /**
   * 種別 固定値「Polygon」を設定
   * @return type
   */
  
  @Schema(name = "type", example = "Polygon", description = "種別 固定値「Polygon」を設定", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public UaslPointEntityGeometry coordinates(List<List<List<BigDecimal>>> coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  public UaslPointEntityGeometry addCoordinatesItem(List<List<BigDecimal>> coordinatesItem) {
    if (this.coordinates == null) {
      this.coordinates = new ArrayList<>();
    }
    this.coordinates.add(coordinatesItem);
    return this;
  }

  /**
   * 座標 配列内は経度,緯度,高度順で記述
   * @return coordinates
   */
  @Valid 
  @Schema(name = "coordinates", description = "座標 配列内は経度,緯度,高度順で記述", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coordinates")
  public List<List<List<BigDecimal>>> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(List<List<List<BigDecimal>>> coordinates) {
    this.coordinates = coordinates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UaslPointEntityGeometry uaslPointEntityGeometry = (UaslPointEntityGeometry) o;
    return Objects.equals(this.type, uaslPointEntityGeometry.type) &&
        Objects.equals(this.coordinates, uaslPointEntityGeometry.coordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, coordinates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslPointEntityGeometry {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    coordinates: ").append(toIndentedString(coordinates)).append("\n");
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

