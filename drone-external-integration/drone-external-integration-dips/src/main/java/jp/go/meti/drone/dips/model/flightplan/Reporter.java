package jp.go.meti.drone.dips.model.flightplan;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 通報者情報
 */

@SuppressWarnings("javadoc")
@Schema(name = "Reporter", description = "通報者情報")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class Reporter {

  private String contactReporterFlag;

  private Contact contactReporter;

  public Reporter contactReporterFlag(String contactReporterFlag) {
    this.contactReporterFlag = contactReporterFlag;
    return this;
  }

  /**
   * 連絡先フラグ（ 通報者を連絡先とする場合\"1\",しない場合\"０\"  通報者、操縦者、許可・承認情報のいずれか一つの連絡先フラグが”１”に設定されている  
   * @return contactReporterFlag
   */
  
  @Schema(name = "contactReporterFlag", example = "1", description = "連絡先フラグ（ 通報者を連絡先とする場合\"1\",しない場合\"０\"  通報者、操縦者、許可・承認情報のいずれか一つの連絡先フラグが”１”に設定されている  ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactReporterFlag")
  public String getContactReporterFlag() {
    return contactReporterFlag;
  }

  public void setContactReporterFlag(String contactReporterFlag) {
    this.contactReporterFlag = contactReporterFlag;
  }

  public Reporter contactReporter(Contact contactReporter) {
    this.contactReporter = contactReporter;
    return this;
  }

  /**
   * Get contactReporter
   * @return contactReporter
   */
  @Valid 
  @Schema(name = "contactReporter", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contactReporter")
  public Contact getContactReporter() {
    return contactReporter;
  }

  public void setContactReporter(Contact contactReporter) {
    this.contactReporter = contactReporter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reporter reporter = (Reporter) o;
    return Objects.equals(this.contactReporterFlag, reporter.contactReporterFlag) &&
        Objects.equals(this.contactReporter, reporter.contactReporter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contactReporterFlag, contactReporter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Reporter {\n");
    sb.append("    contactReporterFlag: ").append(toIndentedString(contactReporterFlag)).append("\n");
    sb.append("    contactReporter: ").append(toIndentedString(contactReporter)).append("\n");
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

