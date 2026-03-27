package jp.go.meti.drone.dips.model.flightplan;


import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 許可・承認情報
 */

@SuppressWarnings("javadoc")
@Schema(name = "FlightPermitApplicationInfo", description = "許可・承認情報")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class FlightPermitApplicationInfo {

  private String flightPermitApplicationNumber;

  private String permitDate;

  private String startDate;

  private String finishDate;

  private String contactPermitFlag;

  private Contact contactPermit;

  public FlightPermitApplicationInfo flightPermitApplicationNumber(String flightPermitApplicationNumber) {
    this.flightPermitApplicationNumber = flightPermitApplicationNumber;
    return this;
  }

  /**
   * 許可・承認番号
   * @return flightPermitApplicationNumber
   */
  
  @Schema(name = "flightPermitApplicationNumber", example = "dddddddddd", description = "許可・承認番号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flightPermitApplicationNumber")
  public String getFlightPermitApplicationNumber() {
    return flightPermitApplicationNumber;
  }

  public void setFlightPermitApplicationNumber(String flightPermitApplicationNumber) {
    this.flightPermitApplicationNumber = flightPermitApplicationNumber;
  }

  public FlightPermitApplicationInfo permitDate(String permitDate) {
    this.permitDate = permitDate;
    return this;
  }

  /**
   * 許可書発行日（YYYYMMDD形式）
   * @return permitDate
   */
  
  @Schema(name = "permitDate", example = "20221020", description = "許可書発行日（YYYYMMDD形式）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("permitDate")
  public String getPermitDate() {
    return permitDate;
  }

  public void setPermitDate(String permitDate) {
    this.permitDate = permitDate;
  }

  public FlightPermitApplicationInfo startDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * 許可期間（自）開始日（YYYYMMDD形式）
   * @return startDate
   */
  
  @Schema(name = "startDate", example = "20221020", description = "許可期間（自）開始日（YYYYMMDD形式）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startDate")
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public FlightPermitApplicationInfo finishDate(String finishDate) {
    this.finishDate = finishDate;
    return this;
  }

  /**
   * 許可期間（至）終了日（YYYYMMDD形式）
   * @return finishDate
   */
  
  @Schema(name = "finishDate", example = "20231020", description = "許可期間（至）終了日（YYYYMMDD形式）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("finishDate")
  public String getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(String finishDate) {
    this.finishDate = finishDate;
  }

  public FlightPermitApplicationInfo contactPermitFlag(String contactPermitFlag) {
    this.contactPermitFlag = contactPermitFlag;
    return this;
  }

  /**
   * 連絡先フラグ  許可・承認情報登録者を連絡先とするか（\"1\": する, \"0\": しない） 
   * @return contactPermitFlag
   */
  
  @Schema(name = "contactPermitFlag", example = "0", description = "連絡先フラグ  許可・承認情報登録者を連絡先とするか（\"1\": する, \"0\": しない） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactPermitFlag")
  public String getContactPermitFlag() {
    return contactPermitFlag;
  }

  public void setContactPermitFlag(String contactPermitFlag) {
    this.contactPermitFlag = contactPermitFlag;
  }

  public FlightPermitApplicationInfo contactPermit(Contact contactPermit) {
    this.contactPermit = contactPermit;
    return this;
  }

  /**
   * Get contactPermit
   * @return contactPermit
   */
  @Valid 
  @Schema(name = "contactPermit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactPermit")
  public Contact getContactPermit() {
    return contactPermit;
  }

  public void setContactPermit(Contact contactPermit) {
    this.contactPermit = contactPermit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FlightPermitApplicationInfo flightPermitApplicationInfo = (FlightPermitApplicationInfo) o;
    return Objects.equals(this.flightPermitApplicationNumber, flightPermitApplicationInfo.flightPermitApplicationNumber) &&
        Objects.equals(this.permitDate, flightPermitApplicationInfo.permitDate) &&
        Objects.equals(this.startDate, flightPermitApplicationInfo.startDate) &&
        Objects.equals(this.finishDate, flightPermitApplicationInfo.finishDate) &&
        Objects.equals(this.contactPermitFlag, flightPermitApplicationInfo.contactPermitFlag) &&
        Objects.equals(this.contactPermit, flightPermitApplicationInfo.contactPermit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flightPermitApplicationNumber, permitDate, startDate, finishDate, contactPermitFlag, contactPermit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlightPermitApplicationInfo {\n");
    sb.append("    flightPermitApplicationNumber: ").append(toIndentedString(flightPermitApplicationNumber)).append("\n");
    sb.append("    permitDate: ").append(toIndentedString(permitDate)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    finishDate: ").append(toIndentedString(finishDate)).append("\n");
    sb.append("    contactPermitFlag: ").append(toIndentedString(contactPermitFlag)).append("\n");
    sb.append("    contactPermit: ").append(toIndentedString(contactPermit)).append("\n");
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

