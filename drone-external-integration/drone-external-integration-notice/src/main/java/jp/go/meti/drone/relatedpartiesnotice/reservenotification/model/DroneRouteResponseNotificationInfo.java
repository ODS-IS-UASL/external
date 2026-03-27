package jp.go.meti.drone.relatedpartiesnotice.reservenotification.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 航路予約関係者情報
 */

@Schema(name = "droneRoute.ResponseNotificationInfo", description = "航路予約関係者情報")
@JsonTypeName("droneRoute.ResponseNotificationInfo")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-08T10:30:34.120311+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
@SuppressWarnings("javadoc")
public class DroneRouteResponseNotificationInfo {

  private String operatorId;

  private String operatorName;

  private String relatedCategory;

  private String notificationEmail;

  private String notificationPhone;

  
public DroneRouteResponseNotificationInfo() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DroneRouteResponseNotificationInfo(String operatorId, String operatorName, String relatedCategory, String notificationEmail, String notificationPhone) {
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.relatedCategory = relatedCategory;
    this.notificationEmail = notificationEmail;
    this.notificationPhone = notificationPhone;
  }

  public DroneRouteResponseNotificationInfo operatorId(String operatorId) {
    this.operatorId = operatorId;
    return this;
  }

  /**
   * 事業者ID（UUID）
   * @return operatorId
   */
  @NotNull @Size(max = 36) 
  @Schema(name = "operatorId", example = "123e4567-e89b-12d3-a456-426614174000", description = "事業者ID（UUID）", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operatorId")
  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  public DroneRouteResponseNotificationInfo operatorName(String operatorName) {
    this.operatorName = operatorName;
    return this;
  }

  /**
   * 事業者名
   * @return operatorName
   */
  @NotNull @Size(max = 100) 
  @Schema(name = "operatorName", example = "関係機関A", description = "事業者名", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operatorName")
  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public DroneRouteResponseNotificationInfo relatedCategory(String relatedCategory) {
    this.relatedCategory = relatedCategory;
    return this;
  }

  /**
   * 関係者のカテゴリー
   * @return relatedCategory
   */
  @NotNull @Size(max = 1) 
  @Schema(name = "relatedCategory", example = "1:警察 2:消防 3:ドクターヘリ", description = "関係者のカテゴリー", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("relatedCategory")
  public String getRelatedCategory() {
    return relatedCategory;
  }

  public void setRelatedCategory(String relatedCategory) {
    this.relatedCategory = relatedCategory;
  }

  public DroneRouteResponseNotificationInfo notificationEmail(String notificationEmail) {
    this.notificationEmail = notificationEmail;
    return this;
  }

  /**
   * 周知先メールアドレス
   * @return notificationEmail
   */
  @NotNull @Size(max = 254) 
  @Schema(name = "notificationEmail", example = "related@example.co.jp", description = "周知先メールアドレス", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("notificationEmail")
  public String getNotificationEmail() {
    return notificationEmail;
  }

  public void setNotificationEmail(String notificationEmail) {
    this.notificationEmail = notificationEmail;
  }

  public DroneRouteResponseNotificationInfo notificationPhone(String notificationPhone) {
    this.notificationPhone = notificationPhone;
    return this;
  }

  /**
   * 電話番号
   * @return notificationPhone
   */
  @NotNull @Size(max = 20) 
  @Schema(name = "notificationPhone", example = "080-8888-8888", description = "電話番号", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("notificationPhone")
  public String getNotificationPhone() {
    return notificationPhone;
  }

  public void setNotificationPhone(String notificationPhone) {
    this.notificationPhone = notificationPhone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DroneRouteResponseNotificationInfo droneRouteResponseNotificationInfo = (DroneRouteResponseNotificationInfo) o;
    return Objects.equals(this.operatorId, droneRouteResponseNotificationInfo.operatorId) &&
        Objects.equals(this.operatorName, droneRouteResponseNotificationInfo.operatorName) &&
        Objects.equals(this.relatedCategory, droneRouteResponseNotificationInfo.relatedCategory) &&
        Objects.equals(this.notificationEmail, droneRouteResponseNotificationInfo.notificationEmail) &&
        Objects.equals(this.notificationPhone, droneRouteResponseNotificationInfo.notificationPhone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operatorId, operatorName, relatedCategory, notificationEmail, notificationPhone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DroneRouteResponseNotificationInfo {\n");
    sb.append("    operatorId: ").append(toIndentedString(operatorId)).append("\n");
    sb.append("    operatorName: ").append(toIndentedString(operatorName)).append("\n");
    sb.append("    relatedCategory: ").append(toIndentedString(relatedCategory)).append("\n");
    sb.append("    notificationEmail: ").append(toIndentedString(notificationEmail)).append("\n");
    sb.append("    notificationPhone: ").append(toIndentedString(notificationPhone)).append("\n");
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

