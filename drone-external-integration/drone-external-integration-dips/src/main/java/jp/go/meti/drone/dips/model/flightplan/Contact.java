package jp.go.meti.drone.dips.model.flightplan;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * Contact
 */

@SuppressWarnings("javadoc")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class Contact {

  private String name;

  private String country;

  private String prefectures;

  private String municipality;

  private String telephoneCountry;

  private String telephone;

  private String email;

  public Contact name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 氏名
   * @return name
   */
  
  @Schema(name = "name", example = "操縦太郎", description = "氏名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Contact country(String country) {
    this.country = country;
    return this;
  }

  /**
   * 国コード
   * @return country
   */
  
  @Schema(name = "country", example = "001", description = "国コード", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Contact prefectures(String prefectures) {
    this.prefectures = prefectures;
    return this;
  }

  /**
   * 都道府県コード
   * @return prefectures
   */
  
  @Schema(name = "prefectures", example = "13", description = "都道府県コード", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prefectures")
  public String getPrefectures() {
    return prefectures;
  }

  public void setPrefectures(String prefectures) {
    this.prefectures = prefectures;
  }

  public Contact municipality(String municipality) {
    this.municipality = municipality;
    return this;
  }

  /**
   * 住所（市町村）
   * @return municipality
   */
  
  @Schema(name = "municipality", example = "中央区銀座 1-1", description = "住所（市町村）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("municipality")
  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public Contact telephoneCountry(String telephoneCountry) {
    this.telephoneCountry = telephoneCountry;
    return this;
  }

  /**
   * 電話番号(国コード）
   * @return telephoneCountry
   */
  
  @Schema(name = "telephoneCountry", example = "001", description = "電話番号(国コード）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("telephoneCountry")
  public String getTelephoneCountry() {
    return telephoneCountry;
  }

  public void setTelephoneCountry(String telephoneCountry) {
    this.telephoneCountry = telephoneCountry;
  }

  public Contact telephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

  /**
   * 電話番号
   * @return telephone
   */
  
  @Schema(name = "telephone", example = "09011112222", description = "電話番号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("telephone")
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Contact email(String email) {
    this.email = email;
    return this;
  }

  /**
   * メールアドレス
   * @return email
   */
  
  @Schema(name = "email", example = "shinsei@email.com", description = "メールアドレス", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Contact contact = (Contact) o;
    return Objects.equals(this.name, contact.name) &&
        Objects.equals(this.country, contact.country) &&
        Objects.equals(this.prefectures, contact.prefectures) &&
        Objects.equals(this.municipality, contact.municipality) &&
        Objects.equals(this.telephoneCountry, contact.telephoneCountry) &&
        Objects.equals(this.telephone, contact.telephone) &&
        Objects.equals(this.email, contact.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, country, prefectures, municipality, telephoneCountry, telephone, email);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Contact {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    prefectures: ").append(toIndentedString(prefectures)).append("\n");
    sb.append("    municipality: ").append(toIndentedString(municipality)).append("\n");
    sb.append("    telephoneCountry: ").append(toIndentedString(telephoneCountry)).append("\n");
    sb.append("    telephone: ").append(toIndentedString(telephone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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

