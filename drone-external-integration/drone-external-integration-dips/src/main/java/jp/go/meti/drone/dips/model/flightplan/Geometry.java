package jp.go.meti.drone.dips.model.flightplan;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 検索範囲 指定範囲に飛行範囲の一部または全てが含まれる飛行計画の情報を返却 
 */

@SuppressWarnings("javadoc")
@Schema(name = "Geometry", description = "検索範囲 指定範囲に飛行範囲の一部または全てが含まれる飛行計画の情報を返却 ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class Geometry {

  private String type;

  @Valid
  private List<BigDecimal> center = new ArrayList<>();

  private BigDecimal radius;

  @Valid
  private List<List<BigDecimal>> coordinates = new ArrayList<>();

  public Geometry() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Geometry(String type) {
    this.type = type;
  }

  public Geometry type(String type) {
    this.type = type;
    return this;
  }

  /**
   * ジオメトリタイプ 検索範囲に使用する図形種別（Circle:円,Polygon:多角形） 
   * @return type
   */
  @NotNull 
  @Schema(name = "type", example = "Circle", description = "ジオメトリタイプ 検索範囲に使用する図形種別（Circle:円,Polygon:多角形） ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Geometry center(List<BigDecimal> center) {
    this.center = center;
    return this;
  }

  public Geometry addCenterItem(BigDecimal centerItem) {
    if (this.center == null) {
      this.center = new ArrayList<>();
    }
    this.center.add(centerItem);
    return this;
  }

  /**
   * ジオメトリ（中心点）  ジオメトリタイプで「Circle」を指定した場合必須  
   * @return center
   */
  @Valid 
  @Schema(name = "center", example = "[139.4677,35.6476]", description = "ジオメトリ（中心点）  ジオメトリタイプで「Circle」を指定した場合必須  ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("center")
  public List<BigDecimal> getCenter() {
    return center;
  }

  public void setCenter(List<BigDecimal> center) {
    this.center = center;
  }

  public Geometry radius(BigDecimal radius) {
    this.radius = radius;
    return this;
  }

  /**
   * 半径 ジオメトリタイプで「Circle」を指定した場合必須  
   * @return radius
   */
  @Valid 
  @Schema(name = "radius", example = "10000", description = "半径 ジオメトリタイプで「Circle」を指定した場合必須  ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("radius")
  public BigDecimal getRadius() {
    return radius;
  }

  public void setRadius(BigDecimal radius) {
    this.radius = radius;
  }

  public Geometry coordinates(List<List<BigDecimal>> coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  public Geometry addCoordinatesItem(List<BigDecimal> coordinatesItem) {
    if (this.coordinates == null) {
      this.coordinates = new ArrayList<>();
    }
    this.coordinates.add(coordinatesItem);
    return this;
  }

  /**
   * ジオメトリ（構成点）  ジオメトリタイプで「Polygon:多角形」を指定した場合設定が必須  ジオメトリタイプで「Polygon:多角形」を指定した場合 3 点以上の 構成点を設定する（終点は始点と同じ座標をサーバー側で生成）  
   * @return coordinates
   */
  @Valid 
  @Schema(name = "coordinates", example = "[[139.779031,35.569748],[139.782776,35.574085],[139.77099,35.56901],[139.779031,35.569748]]", description = "ジオメトリ（構成点）  ジオメトリタイプで「Polygon:多角形」を指定した場合設定が必須  ジオメトリタイプで「Polygon:多角形」を指定した場合 3 点以上の 構成点を設定する（終点は始点と同じ座標をサーバー側で生成）  ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coordinates")
  public List<List<BigDecimal>> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(List<List<BigDecimal>> coordinates) {
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
    Geometry geometry = (Geometry) o;
    return Objects.equals(this.type, geometry.type) &&
        Objects.equals(this.center, geometry.center) &&
        Objects.equals(this.radius, geometry.radius) &&
        Objects.equals(this.coordinates, geometry.coordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, center, radius, coordinates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Geometry {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    center: ").append(toIndentedString(center)).append("\n");
    sb.append("    radius: ").append(toIndentedString(radius)).append("\n");
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

