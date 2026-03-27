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
 * 飛行経路情報 飛行計画に登録されている飛行経路、飛行エリア  
 */

@SuppressWarnings("javadoc")
@Schema(name = "FlyRoute", description = "飛行経路情報 飛行計画に登録されている飛行経路、飛行エリア  ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class FlyRoute {

  private String type;

  @Valid
  private List<BigDecimal> center = new ArrayList<>();

  private BigDecimal radius;

  @Valid
  private List<List<BigDecimal>> coordinates = new ArrayList<>();

  public FlyRoute() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FlyRoute(String type, List<BigDecimal> center, BigDecimal radius, List<List<BigDecimal>> coordinates) {
    this.type = type;
    this.center = center;
    this.radius = radius;
    this.coordinates = coordinates;
  }

  public FlyRoute type(String type) {
    this.type = type;
    return this;
  }

  /**
   * ジオメトリタイプ  Circle: 円、Polygon:多角形 
   * @return type
   */
  @NotNull 
  @Schema(name = "type", example = "Circle", description = "ジオメトリタイプ  Circle: 円、Polygon:多角形 ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public FlyRoute center(List<BigDecimal> center) {
    this.center = center;
    return this;
  }

  public FlyRoute addCenterItem(BigDecimal centerItem) {
    if (this.center == null) {
      this.center = new ArrayList<>();
    }
    this.center.add(centerItem);
    return this;
  }

  /**
   * ジオメトリ（中心点）  ジオメトリタイプが「Circle: 円」の場合、指定される 
   * @return center
   */
  @NotNull @Valid 
  @Schema(name = "center", example = "[139.4677,35.6476]", description = "ジオメトリ（中心点）  ジオメトリタイプが「Circle: 円」の場合、指定される ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("center")
  public List<BigDecimal> getCenter() {
    return center;
  }

  public void setCenter(List<BigDecimal> center) {
    this.center = center;
  }

  public FlyRoute radius(BigDecimal radius) {
    this.radius = radius;
    return this;
  }

  /**
   * 半径 ジオメトリタイプが「Circle: 円」の場合、指定される（単位:メートル） 
   * @return radius
   */
  @NotNull @Valid 
  @Schema(name = "radius", example = "10000", description = "半径 ジオメトリタイプが「Circle: 円」の場合、指定される（単位:メートル） ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("radius")
  public BigDecimal getRadius() {
    return radius;
  }

  public void setRadius(BigDecimal radius) {
    this.radius = radius;
  }

  public FlyRoute coordinates(List<List<BigDecimal>> coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  public FlyRoute addCoordinatesItem(List<BigDecimal> coordinatesItem) {
    if (this.coordinates == null) {
      this.coordinates = new ArrayList<>();
    }
    this.coordinates.add(coordinatesItem);
    return this;
  }

  /**
   * ジオメトリ（構成点）  ジオメトリタイプが「Polygon:多角形」の場合、指定される 
   * @return coordinates
   */
  @NotNull @Valid 
  @Schema(name = "coordinates", example = "[[139.779031,35.569748],[139.782776,35.574085],[139.77099,35.56901],[139.779031,35.569748]]", description = "ジオメトリ（構成点）  ジオメトリタイプが「Polygon:多角形」の場合、指定される ", requiredMode = Schema.RequiredMode.REQUIRED)
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
    FlyRoute flyRoute = (FlyRoute) o;
    return Objects.equals(this.type, flyRoute.type) &&
        Objects.equals(this.center, flyRoute.center) &&
        Objects.equals(this.radius, flyRoute.radius) &&
        Objects.equals(this.coordinates, flyRoute.coordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, center, radius, coordinates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlyRoute {\n");
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

