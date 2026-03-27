package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.Valid;

/**
 * 機体に関する情報
 */
@Schema(description = "機体に関する情報")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ConformityAircraftInfo   {
  @JsonProperty("maker")
  private String maker;

  @JsonProperty("modelNumber")
  private String modelNumber;

  @JsonProperty("name")
  private String name;

  @JsonProperty("type")
  private String type;

  @JsonProperty("length")
  private BigDecimal length;

  public ConformityAircraftInfo maker(String maker) {
    this.maker = maker;
    return this;
  }

  /**
   * 製造者名
   * @return maker
  */
  @Schema(description = "製造者名")


  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  public ConformityAircraftInfo modelNumber(String modelNumber) {
    this.modelNumber = modelNumber;
    return this;
  }

  /**
   * 機体型式
   * @return modelNumber
  */
  @Schema(description = "機体型式")


  public String getModelNumber() {
    return modelNumber;
  }

  public void setModelNumber(String modelNumber) {
    this.modelNumber = modelNumber;
  }

  public ConformityAircraftInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 機体名称
   * @return name
  */
  @Schema(description = "機体名称")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ConformityAircraftInfo type(String type) {
    this.type = type;
    return this;
  }

  /**
   * 機体種別
   * @return type
  */
  @Schema(description = "機体種別")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ConformityAircraftInfo length(BigDecimal length) {
    this.length = length;
    return this;
  }

  /**
   * 機体全長
   * @return length
  */
  @Schema(description = "機体全長")

  @Valid

  public BigDecimal getLength() {
    return length;
  }

  public void setLength(BigDecimal length) {
    this.length = length;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConformityAircraftInfo conformityAircraftInfo = (ConformityAircraftInfo) o;
    return Objects.equals(this.maker, conformityAircraftInfo.maker) &&
        Objects.equals(this.modelNumber, conformityAircraftInfo.modelNumber) &&
        Objects.equals(this.name, conformityAircraftInfo.name) &&
        Objects.equals(this.type, conformityAircraftInfo.type) &&
        Objects.equals(this.length, conformityAircraftInfo.length);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maker, modelNumber, name, type, length);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConformityAircraftInfo {\n");
    
    sb.append("    maker: ").append(toIndentedString(maker)).append("\n");
    sb.append("    modelNumber: ").append(toIndentedString(modelNumber)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

