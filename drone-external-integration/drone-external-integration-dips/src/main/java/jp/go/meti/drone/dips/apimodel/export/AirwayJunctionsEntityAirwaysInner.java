package jp.go.meti.drone.dips.apimodel.export;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * AirwayJunctionsEntityAirwaysInner
 */

@JsonTypeName("airwayJunctionsEntity_airways_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-24T17:06:21.206821500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AirwayJunctionsEntityAirwaysInner {

  private AirwayJunctionsEntityAirwaysInnerAirway airway;

  private AirwayJunctionsEntityAirwaysInnerDeviation deviation;

  public AirwayJunctionsEntityAirwaysInner airway(AirwayJunctionsEntityAirwaysInnerAirway airway) {
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
  public AirwayJunctionsEntityAirwaysInnerAirway getAirway() {
    return airway;
  }

  public void setAirway(AirwayJunctionsEntityAirwaysInnerAirway airway) {
    this.airway = airway;
  }

  public AirwayJunctionsEntityAirwaysInner deviation(AirwayJunctionsEntityAirwaysInnerDeviation deviation) {
    this.deviation = deviation;
    return this;
  }

  /**
   * Get deviation
   * @return deviation
   */
  @Valid 
  @Schema(name = "deviation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deviation")
  public AirwayJunctionsEntityAirwaysInnerDeviation getDeviation() {
    return deviation;
  }

  public void setDeviation(AirwayJunctionsEntityAirwaysInnerDeviation deviation) {
    this.deviation = deviation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AirwayJunctionsEntityAirwaysInner airwayJunctionsEntityAirwaysInner = (AirwayJunctionsEntityAirwaysInner) o;
    return Objects.equals(this.airway, airwayJunctionsEntityAirwaysInner.airway) &&
        Objects.equals(this.deviation, airwayJunctionsEntityAirwaysInner.deviation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(airway, deviation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirwayJunctionsEntityAirwaysInner {\n");
    sb.append("    airway: ").append(toIndentedString(airway)).append("\n");
    sb.append("    deviation: ").append(toIndentedString(deviation)).append("\n");
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

