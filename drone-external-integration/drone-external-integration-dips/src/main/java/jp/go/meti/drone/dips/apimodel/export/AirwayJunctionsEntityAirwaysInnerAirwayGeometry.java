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
 * GeoJsonにおけるgeometryオブジェクト
 */

@Schema(name = "airwayJunctionsEntity_airways_inner_airway_geometry", description = "GeoJsonにおけるgeometryオブジェクト")
@JsonTypeName("airwayJunctionsEntity_airways_inner_airway_geometry")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-24T17:06:21.206821500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AirwayJunctionsEntityAirwaysInnerAirwayGeometry {

  private String type;

  @Valid
  private List<List<BigDecimal>> coordinates = new ArrayList<>();

  public AirwayJunctionsEntityAirwaysInnerAirwayGeometry type(String type) {
    this.type = type;
    return this;
  }

  /**
   * GeoJsonにおけるgeometryのtype。Polygon固定。
   * @return type
   */
  
  @Schema(name = "type", example = "Polygon", description = "GeoJsonにおけるgeometryのtype。Polygon固定。", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public AirwayJunctionsEntityAirwaysInnerAirwayGeometry coordinates(List<List<BigDecimal>> coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  public AirwayJunctionsEntityAirwaysInnerAirwayGeometry addCoordinatesItem(List<BigDecimal> coordinatesItem) {
    if (this.coordinates == null) {
      this.coordinates = new ArrayList<>();
    }
    this.coordinates.add(coordinatesItem);
    return this;
  }

  /**
   * Get coordinates
   * @return coordinates
   */
  @Valid 
  @Schema(name = "coordinates", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    AirwayJunctionsEntityAirwaysInnerAirwayGeometry airwayJunctionsEntityAirwaysInnerAirwayGeometry = (AirwayJunctionsEntityAirwaysInnerAirwayGeometry) o;
    return Objects.equals(this.type, airwayJunctionsEntityAirwaysInnerAirwayGeometry.type) &&
        Objects.equals(this.coordinates, airwayJunctionsEntityAirwaysInnerAirwayGeometry.coordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, coordinates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirwayJunctionsEntityAirwaysInnerAirwayGeometry {\n");
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

