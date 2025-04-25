package jp.go.meti.drone.dips.apimodel.export;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 落下空間の断面に描かれる航路の位置座標
 */

@Schema(name = "airwayJunctionsEntity_airways_inner_airway", description = "落下空間の断面に描かれる航路の位置座標")
@JsonTypeName("airwayJunctionsEntity_airways_inner_airway")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-24T17:06:21.206821500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AirwayJunctionsEntityAirwaysInnerAirway {

  private AirwayJunctionsEntityAirwaysInnerAirwayGeometry geometry;

  public AirwayJunctionsEntityAirwaysInnerAirway geometry(AirwayJunctionsEntityAirwaysInnerAirwayGeometry geometry) {
    this.geometry = geometry;
    return this;
  }

  /**
   * Get geometry
   * @return geometry
   */
  @Valid 
  @Schema(name = "geometry", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("geometry")
  public AirwayJunctionsEntityAirwaysInnerAirwayGeometry getGeometry() {
    return geometry;
  }

  public void setGeometry(AirwayJunctionsEntityAirwaysInnerAirwayGeometry geometry) {
    this.geometry = geometry;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AirwayJunctionsEntityAirwaysInnerAirway airwayJunctionsEntityAirwaysInnerAirway = (AirwayJunctionsEntityAirwaysInnerAirway) o;
    return Objects.equals(this.geometry, airwayJunctionsEntityAirwaysInnerAirway.geometry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geometry);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirwayJunctionsEntityAirwaysInnerAirway {\n");
    sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
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

