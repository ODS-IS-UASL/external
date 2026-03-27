package jp.go.meti.drone.dips.model.flightplan;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 保険情報
 */

@SuppressWarnings("javadoc")
@Schema(name = "InsuranceInformation", description = "保険情報")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class InsuranceInformation {

  private String insuranceCompany;

  private String insuranceProduct;

  private Integer interPerson;

  private Integer interObject;

  private String insuranceAbility;

  private String insuranceSupplement;

  public InsuranceInformation insuranceCompany(String insuranceCompany) {
    this.insuranceCompany = insuranceCompany;
    return this;
  }

  /**
   * 保険会社名
   * @return insuranceCompany
   */
  
  @Schema(name = "insuranceCompany", example = "〇×損保", description = "保険会社名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("insuranceCompany")
  public String getInsuranceCompany() {
    return insuranceCompany;
  }

  public void setInsuranceCompany(String insuranceCompany) {
    this.insuranceCompany = insuranceCompany;
  }

  public InsuranceInformation insuranceProduct(String insuranceProduct) {
    this.insuranceProduct = insuranceProduct;
    return this;
  }

  /**
   * 商品名
   * @return insuranceProduct
   */
  
  @Schema(name = "insuranceProduct", example = "〇×保険", description = "商品名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("insuranceProduct")
  public String getInsuranceProduct() {
    return insuranceProduct;
  }

  public void setInsuranceProduct(String insuranceProduct) {
    this.insuranceProduct = insuranceProduct;
  }

  public InsuranceInformation interPerson(Integer interPerson) {
    this.interPerson = interPerson;
    return this;
  }

  /**
   * 補償金額（対人）
   * @return interPerson
   */
  
  @Schema(name = "interPerson", example = "-1", description = "補償金額（対人）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("interPerson")
  public Integer getInterPerson() {
    return interPerson;
  }

  public void setInterPerson(Integer interPerson) {
    this.interPerson = interPerson;
  }

  public InsuranceInformation interObject(Integer interObject) {
    this.interObject = interObject;
    return this;
  }

  /**
   * 補償金額（対物）
   * @return interObject
   */
  
  @Schema(name = "interObject", example = "20000000", description = "補償金額（対物）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("interObject")
  public Integer getInterObject() {
    return interObject;
  }

  public void setInterObject(Integer interObject) {
    this.interObject = interObject;
  }

  public InsuranceInformation insuranceAbility(String insuranceAbility) {
    this.insuranceAbility = insuranceAbility;
    return this;
  }

  /**
   * 賠償能力（\"1\": あり, \"0\": なし） 
   * @return insuranceAbility
   */
  
  @Schema(name = "insuranceAbility", example = "1", description = "賠償能力（\"1\": あり, \"0\": なし） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("insuranceAbility")
  public String getInsuranceAbility() {
    return insuranceAbility;
  }

  public void setInsuranceAbility(String insuranceAbility) {
    this.insuranceAbility = insuranceAbility;
  }

  public InsuranceInformation insuranceSupplement(String insuranceSupplement) {
    this.insuranceSupplement = insuranceSupplement;
    return this;
  }

  /**
   * 補足情報
   * @return insuranceSupplement
   */
  
  @Schema(name = "insuranceSupplement", example = "補足情報なし", description = "補足情報", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("insuranceSupplement")
  public String getInsuranceSupplement() {
    return insuranceSupplement;
  }

  public void setInsuranceSupplement(String insuranceSupplement) {
    this.insuranceSupplement = insuranceSupplement;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InsuranceInformation insuranceInformation = (InsuranceInformation) o;
    return Objects.equals(this.insuranceCompany, insuranceInformation.insuranceCompany) &&
        Objects.equals(this.insuranceProduct, insuranceInformation.insuranceProduct) &&
        Objects.equals(this.interPerson, insuranceInformation.interPerson) &&
        Objects.equals(this.interObject, insuranceInformation.interObject) &&
        Objects.equals(this.insuranceAbility, insuranceInformation.insuranceAbility) &&
        Objects.equals(this.insuranceSupplement, insuranceInformation.insuranceSupplement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(insuranceCompany, insuranceProduct, interPerson, interObject, insuranceAbility, insuranceSupplement);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InsuranceInformation {\n");
    sb.append("    insuranceCompany: ").append(toIndentedString(insuranceCompany)).append("\n");
    sb.append("    insuranceProduct: ").append(toIndentedString(insuranceProduct)).append("\n");
    sb.append("    interPerson: ").append(toIndentedString(interPerson)).append("\n");
    sb.append("    interObject: ").append(toIndentedString(interObject)).append("\n");
    sb.append("    insuranceAbility: ").append(toIndentedString(insuranceAbility)).append("\n");
    sb.append("    insuranceSupplement: ").append(toIndentedString(insuranceSupplement)).append("\n");
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

