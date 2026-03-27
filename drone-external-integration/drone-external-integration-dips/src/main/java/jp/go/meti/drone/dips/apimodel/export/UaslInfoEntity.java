/*
* 開発システム： ドローン航路基盤システム
* ファイル名： UaslInfoEntity.java
* 著作権： Copyright (C) 202X-20XX,  経済産業省
* 会社名： NTT DATA Corporation
* 更新日： $Date$
*
*/
package jp.go.meti.drone.dips.apimodel.export;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * UaslInfoEntity
 */

@JsonTypeName("uaslInfoEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-03-03T19:09:19.070177300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class UaslInfoEntity {

  private String uaslAdministratorId;

  private String businessNumber;

  private UaslEntity uasl;

  public UaslInfoEntity uaslAdministratorId(String uaslAdministratorId) {
    this.uaslAdministratorId = uaslAdministratorId;
    return this;
  }

  /**
   * 航路運営者ID
   * @return uaslAdministratorId
   */
  
  @Schema(name = "uaslAdministratorId", description = "航路運営者ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslAdministratorId")
  public String getUaslAdministratorId() {
    return uaslAdministratorId;
  }

  public void setUaslAdministratorId(String uaslAdministratorId) {
    this.uaslAdministratorId = uaslAdministratorId;
  }

  public UaslInfoEntity businessNumber(String businessNumber) {
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

  public UaslInfoEntity uasl(UaslEntity uasl) {
    this.uasl = uasl;
    return this;
  }

  /**
   * Get uasl
   * @return uasl
   */
  @Valid 
  @Schema(name = "uasl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uasl")
  public UaslEntity getUasl() {
    return uasl;
  }

  public void setUasl(UaslEntity uasl) {
    this.uasl = uasl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UaslInfoEntity uaslInfoEntity = (UaslInfoEntity) o;
    return Objects.equals(this.uaslAdministratorId, uaslInfoEntity.uaslAdministratorId) &&
        Objects.equals(this.businessNumber, uaslInfoEntity.businessNumber) &&
        Objects.equals(this.uasl, uaslInfoEntity.uasl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uaslAdministratorId, businessNumber, uasl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslInfoEntity {\n");
    sb.append("    uaslAdministratorId: ").append(toIndentedString(uaslAdministratorId)).append("\n");
    sb.append("    businessNumber: ").append(toIndentedString(businessNumber)).append("\n");
    sb.append("    uasl: ").append(toIndentedString(uasl)).append("\n");
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
