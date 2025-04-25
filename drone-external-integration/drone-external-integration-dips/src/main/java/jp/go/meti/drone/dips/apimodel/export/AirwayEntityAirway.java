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
 * AirwayEntityAirway
 */

@JsonTypeName("airwayEntity_airway")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-24T17:06:21.206821500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AirwayEntityAirway {

  private String airwayAdministratorId;

  private String businessNumber;

  @Valid
  private List<@Valid AirwaysEntity> airways = new ArrayList<>();

  public AirwayEntityAirway airwayAdministratorId(String airwayAdministratorId) {
    this.airwayAdministratorId = airwayAdministratorId;
    return this;
  }

  /**
   * 航路運営者ID
   * @return airwayAdministratorId
   */
  
  @Schema(name = "airwayAdministratorId", description = "航路運営者ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airwayAdministratorId")
  public String getAirwayAdministratorId() {
    return airwayAdministratorId;
  }

  public void setAirwayAdministratorId(String airwayAdministratorId) {
    this.airwayAdministratorId = airwayAdministratorId;
  }

  public AirwayEntityAirway businessNumber(String businessNumber) {
    this.businessNumber = businessNumber;
    return this;
  }

  /**
   * 事業者番号
   * @return businessNumber
   */
  
  @Schema(name = "businessNumber", description = "事業者番号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("businessNumber")
  public String getBusinessNumber() {
    return businessNumber;
  }

  public void setBusinessNumber(String businessNumber) {
    this.businessNumber = businessNumber;
  }

  public AirwayEntityAirway airways(List<@Valid AirwaysEntity> airways) {
    this.airways = airways;
    return this;
  }

  public AirwayEntityAirway addAirwaysItem(AirwaysEntity airwaysItem) {
    if (this.airways == null) {
      this.airways = new ArrayList<>();
    }
    this.airways.add(airwaysItem);
    return this;
  }

  /**
   * Get airways
   * @return airways
   */
  @Valid 
  @Schema(name = "airways", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airways")
  public List<@Valid AirwaysEntity> getAirways() {
    return airways;
  }

  public void setAirways(List<@Valid AirwaysEntity> airways) {
    this.airways = airways;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AirwayEntityAirway airwayEntityAirway = (AirwayEntityAirway) o;
    return Objects.equals(this.airwayAdministratorId, airwayEntityAirway.airwayAdministratorId) &&
        Objects.equals(this.businessNumber, airwayEntityAirway.businessNumber) &&
        Objects.equals(this.airways, airwayEntityAirway.airways);
  }

  @Override
  public int hashCode() {
    return Objects.hash(airwayAdministratorId, businessNumber, airways);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirwayEntityAirway {\n");
    sb.append("    airwayAdministratorId: ").append(toIndentedString(airwayAdministratorId)).append("\n");
    sb.append("    businessNumber: ").append(toIndentedString(businessNumber)).append("\n");
    sb.append("    airways: ").append(toIndentedString(airways)).append("\n");
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

