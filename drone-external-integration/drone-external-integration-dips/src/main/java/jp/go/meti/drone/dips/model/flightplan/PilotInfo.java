package jp.go.meti.drone.dips.model.flightplan;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 操縦者情報
 */

@SuppressWarnings("javadoc")
@Schema(name = "PilotInfo", description = "操縦者情報")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class PilotInfo {

  private Integer pilotId;

  private String contactPilotFlag;

  private Contact contactPilot;

  private String skillCertificationNumber;

  private String firstClass;

  private String secondClass;

  private String privateLicense;

  private String maker;

  private String model;

  public PilotInfo pilotId(Integer pilotId) {
    this.pilotId = pilotId;
    return this;
  }

  /**
   * 操縦者ID
   * @return pilotId
   */
  
  @Schema(name = "pilotId", example = "1132709", description = "操縦者ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pilotId")
  public Integer getPilotId() {
    return pilotId;
  }

  public void setPilotId(Integer pilotId) {
    this.pilotId = pilotId;
  }

  public PilotInfo contactPilotFlag(String contactPilotFlag) {
    this.contactPilotFlag = contactPilotFlag;
    return this;
  }

  /**
   * 連絡先フラグ 操縦者を連絡先とする場合\"1\",しない場合\"０\"  
   * @return contactPilotFlag
   */
  
  @Schema(name = "contactPilotFlag", example = "0", description = "連絡先フラグ 操縦者を連絡先とする場合\"1\",しない場合\"０\"  ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactPilotFlag")
  public String getContactPilotFlag() {
    return contactPilotFlag;
  }

  public void setContactPilotFlag(String contactPilotFlag) {
    this.contactPilotFlag = contactPilotFlag;
  }

  public PilotInfo contactPilot(Contact contactPilot) {
    this.contactPilot = contactPilot;
    return this;
  }

  /**
   * Get contactPilot
   * @return contactPilot
   */
  @Valid 
  @Schema(name = "contactPilot", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactPilot")
  public Contact getContactPilot() {
    return contactPilot;
  }

  public void setContactPilot(Contact contactPilot) {
    this.contactPilot = contactPilot;
  }

  public PilotInfo skillCertificationNumber(String skillCertificationNumber) {
    this.skillCertificationNumber = skillCertificationNumber;
    return this;
  }

  /**
   * 技能証明書番号
   * @return skillCertificationNumber
   */
  
  @Schema(name = "skillCertificationNumber", example = "12345678901", description = "技能証明書番号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("skillCertificationNumber")
  public String getSkillCertificationNumber() {
    return skillCertificationNumber;
  }

  public void setSkillCertificationNumber(String skillCertificationNumber) {
    this.skillCertificationNumber = skillCertificationNumber;
  }

  public PilotInfo firstClass(String firstClass) {
    this.firstClass = firstClass;
    return this;
  }

  /**
   * 技能証明（一等）（\"1\": 有り, \"0\": 無し） 
   * @return firstClass
   */
  
  @Schema(name = "firstClass", example = "1", description = "技能証明（一等）（\"1\": 有り, \"0\": 無し） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstClass")
  public String getFirstClass() {
    return firstClass;
  }

  public void setFirstClass(String firstClass) {
    this.firstClass = firstClass;
  }

  public PilotInfo secondClass(String secondClass) {
    this.secondClass = secondClass;
    return this;
  }

  /**
   * 技能証明（二等）（\"1\": 有り, \"0\": 無し） 
   * @return secondClass
   */
  
  @Schema(name = "secondClass", example = "1", description = "技能証明（二等）（\"1\": 有り, \"0\": 無し） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("secondClass")
  public String getSecondClass() {
    return secondClass;
  }

  public void setSecondClass(String secondClass) {
    this.secondClass = secondClass;
  }

  public PilotInfo privateLicense(String privateLicense) {
    this.privateLicense = privateLicense;
    return this;
  }

  /**
   * 技能認証保有状況（\"1\": 有り, \"0\": 無し） 
   * @return privateLicense
   */
  
  @Schema(name = "privateLicense", example = "1", description = "技能認証保有状況（\"1\": 有り, \"0\": 無し） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("privateLicense")
  public String getPrivateLicense() {
    return privateLicense;
  }

  public void setPrivateLicense(String privateLicense) {
    this.privateLicense = privateLicense;
  }

  public PilotInfo maker(String maker) {
    this.maker = maker;
    return this;
  }

  /**
   * 使用機体製造者名
   * @return maker
   */
  
  @Schema(name = "maker", example = "maker001", description = "使用機体製造者名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maker")
  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  public PilotInfo model(String model) {
    this.model = model;
    return this;
  }

  /**
   * 使用機体型式名
   * @return model
   */
  
  @Schema(name = "model", example = "model001", description = "使用機体型式名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("model")
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PilotInfo pilotInfo = (PilotInfo) o;
    return Objects.equals(this.pilotId, pilotInfo.pilotId) &&
        Objects.equals(this.contactPilotFlag, pilotInfo.contactPilotFlag) &&
        Objects.equals(this.contactPilot, pilotInfo.contactPilot) &&
        Objects.equals(this.skillCertificationNumber, pilotInfo.skillCertificationNumber) &&
        Objects.equals(this.firstClass, pilotInfo.firstClass) &&
        Objects.equals(this.secondClass, pilotInfo.secondClass) &&
        Objects.equals(this.privateLicense, pilotInfo.privateLicense) &&
        Objects.equals(this.maker, pilotInfo.maker) &&
        Objects.equals(this.model, pilotInfo.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pilotId, contactPilotFlag, contactPilot, skillCertificationNumber, firstClass, secondClass, privateLicense, maker, model);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PilotInfo {\n");
    sb.append("    pilotId: ").append(toIndentedString(pilotId)).append("\n");
    sb.append("    contactPilotFlag: ").append(toIndentedString(contactPilotFlag)).append("\n");
    sb.append("    contactPilot: ").append(toIndentedString(contactPilot)).append("\n");
    sb.append("    skillCertificationNumber: ").append(toIndentedString(skillCertificationNumber)).append("\n");
    sb.append("    firstClass: ").append(toIndentedString(firstClass)).append("\n");
    sb.append("    secondClass: ").append(toIndentedString(secondClass)).append("\n");
    sb.append("    privateLicense: ").append(toIndentedString(privateLicense)).append("\n");
    sb.append("    maker: ").append(toIndentedString(maker)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
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

