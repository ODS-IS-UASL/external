/*
* 開発システム： ドローン航路基盤システム
* ファイル名： UaslTopEntity.java
* 著作権： Copyright (C) 202X-20XX,  経済産業省
* 会社名： NTT DATA Corporation
* 更新日： $Date$
*
*/
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
 * 航路
 */

@Schema(name = "uaslTopEntity", description = "航路")
@JsonTypeName("uaslTopEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-13T10:34:08.568609+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class UaslTopEntity {

  @Valid
  private List<@Valid UaslInfoEntity> uasl = new ArrayList<>();

  public UaslTopEntity uasl(List<@Valid UaslInfoEntity> uasl) {
    this.uasl = uasl;
    return this;
  }

  public UaslTopEntity addUaslItem(UaslInfoEntity uaslItem) {
    if (this.uasl == null) {
      this.uasl = new ArrayList<>();
    }
    this.uasl.add(uaslItem);
    return this;
  }

  /**
   * Get uasl
   * @return uasl
   */
  @Valid 
  @Schema(name = "uasl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uasl")
  public List<@Valid UaslInfoEntity> getUasl() {
    return uasl;
  }

  public void setUasl(List<@Valid UaslInfoEntity> uasl) {
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
    UaslTopEntity uaslTopEntity = (UaslTopEntity) o;
    return Objects.equals(this.uasl, uaslTopEntity.uasl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uasl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslTopEntity {\n");
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

