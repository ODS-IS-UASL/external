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
 * AirwayJunctionsEntity
 */

@JsonTypeName("airwayJunctionsEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-24T17:06:21.206821500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AirwayJunctionsEntity {

  private String airwayJunctionId;

  private String airwayJunctionName;

  @Valid
  private List<@Valid AirwayJunctionsEntityAirwaysInner> airways = new ArrayList<>();

  public AirwayJunctionsEntity airwayJunctionId(String airwayJunctionId) {
    this.airwayJunctionId = airwayJunctionId;
    return this;
  }

  /**
   * ジャンクションID
   * @return airwayJunctionId
   */
  
  @Schema(name = "airwayJunctionId", description = "ジャンクションID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airwayJunctionId")
  public String getAirwayJunctionId() {
    return airwayJunctionId;
  }

  public void setAirwayJunctionId(String airwayJunctionId) {
    this.airwayJunctionId = airwayJunctionId;
  }

  public AirwayJunctionsEntity airwayJunctionName(String airwayJunctionName) {
    this.airwayJunctionName = airwayJunctionName;
    return this;
  }

  /**
   * ジャンクション名
   * @return airwayJunctionName
   */
  
  @Schema(name = "airwayJunctionName", description = "ジャンクション名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airwayJunctionName")
  public String getAirwayJunctionName() {
	  return airwayJunctionName;
  }

  public void setAirwayJunctionName(String airwayJunctionName) {
	  this.airwayJunctionName = airwayJunctionName;
  }

  public AirwayJunctionsEntity airways(List<@Valid AirwayJunctionsEntityAirwaysInner> airways) {
    this.airways = airways;
    return this;
  }

  public AirwayJunctionsEntity addAirwaysItem(AirwayJunctionsEntityAirwaysInner airwaysItem) {
    if (this.airways == null) {
      this.airways = new ArrayList<>();
    }
    this.airways.add(airwaysItem);
    return this;
  }

  /**
   * 落下空間の断面に描かれる航路/航路逸脱領域の位置座標
   * @return airways
   */
  @Valid 
  @Schema(name = "airways", description = "落下空間の断面に描かれる航路/航路逸脱領域の位置座標", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("airways")
  public List<@Valid AirwayJunctionsEntityAirwaysInner> getAirways() {
    return airways;
  }

  public void setAirways(List<@Valid AirwayJunctionsEntityAirwaysInner> airways) {
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
    AirwayJunctionsEntity airwayJunctionsEntity = (AirwayJunctionsEntity) o;
    return Objects.equals(this.airwayJunctionId, airwayJunctionsEntity.airwayJunctionId) &&
        Objects.equals(this.airwayJunctionName, airwayJunctionsEntity.airwayJunctionName) &&
        Objects.equals(this.airways, airwayJunctionsEntity.airways);
  }

  @Override
  public int hashCode() {
    return Objects.hash(airwayJunctionId, airwayJunctionName, airways);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AirwayJunctionsEntity {\n");
    sb.append("    airwayJunctionId: ").append(toIndentedString(airwayJunctionId)).append("\n");
    sb.append("    airwayJunctionName: ").append(toIndentedString(airwayJunctionName)).append("\n");
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

