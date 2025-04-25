package jp.go.meti.drone.dips.apimodel.export;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 航路
 */
@Schema(name = "airwayEntity", description = "航路")
@JsonTypeName("airwayEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-24T17:06:21.206821500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AirwayEntity {

  private AirwayEntityAirway airway;

  public AirwayEntity airway(AirwayEntityAirway airway) {
    this.airway = airway;
    return this;
  }

  /**
   * Get airway
   * @return airway
   */
  @Valid 
  @Schema(name = "airway", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airway")
  public AirwayEntityAirway getAirway() {
    return airway;
  }

  public void setAirway(AirwayEntityAirway airway) {
    this.airway = airway;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AirwayEntity airwayEntity = (AirwayEntity) o;
    return Objects.equals(this.airway, airwayEntity.airway);
  }

  @Override
  public int hashCode() {
    return Objects.hash(airway);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirwayEntity {\n");
    sb.append("    airway: ").append(toIndentedString(airway)).append("\n");
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

