package jp.go.meti.drone.relatedpartiesnotice.tenant.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * DroneRouteResponseOperatorInfo
 */

@JsonTypeName("droneRoute.ResponseOperatorInfo")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-04T11:19:34.085772500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class DroneRouteResponseOperatorInfo {

  private String operatorId;

  private String operatorName;

  private String notificationType;

  @Valid
  private List<@Size(max = 1)String> roleList = new ArrayList<>();

  @Valid
  private List<@Size(max = 254)String> notificationTargetList = new ArrayList<>();

  @Valid
  private List<@Size(max = 40)String> linkAirwayList = new ArrayList<>();

  public DroneRouteResponseOperatorInfo() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DroneRouteResponseOperatorInfo(String operatorId, String operatorName, String notificationType, List<@Size(max = 1)String> roleList, List<@Size(max = 254)String> notificationTargetList) {
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.notificationType = notificationType;
    this.roleList = roleList;
    this.notificationTargetList = notificationTargetList;
  }

  public DroneRouteResponseOperatorInfo operatorId(String operatorId) {
    this.operatorId = operatorId;
    return this;
  }

  /**
   * 事業者ID（UUID）
   * @return operatorId
   */
  @NotNull @Size(max = 36) 
  @Schema(name = "operatorId", description = "事業者ID（UUID）", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operatorId")
  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  public DroneRouteResponseOperatorInfo operatorName(String operatorName) {
    this.operatorName = operatorName;
    return this;
  }

  /**
   * 事業者名
   * @return operatorName
   */
  @NotNull @Size(max = 100) 
  @Schema(name = "operatorName", description = "事業者名", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operatorName")
  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public DroneRouteResponseOperatorInfo notificationType(String notificationType) {
    this.notificationType = notificationType;
    return this;
  }

  /**
   * 周知方法(1：メール、2：API)
   * @return notificationType
   */
  @NotNull @Size(max = 1) 
  @Schema(name = "notificationType", description = "周知方法(1：メール、2：API)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("notificationType")
  public String getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public DroneRouteResponseOperatorInfo roleList(List<@Size(max = 1)String> roleList) {
    this.roleList = roleList;
    return this;
  }

  public DroneRouteResponseOperatorInfo addRoleListItem(String roleListItem) {
    if (this.roleList == null) {
      this.roleList = new ArrayList<>();
    }
    this.roleList.add(roleListItem);
    return this;
  }

  /**
   * 権限情報リスト
   * @return roleList
   */
  @NotNull 
  @Schema(name = "roleList", description = "権限情報リスト", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("roleList")
  public List<@Size(max = 1)String> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<@Size(max = 1)String> roleList) {
    this.roleList = roleList;
  }

  public DroneRouteResponseOperatorInfo notificationTargetList(List<@Size(max = 254)String> notificationTargetList) {
    this.notificationTargetList = notificationTargetList;
    return this;
  }

  public DroneRouteResponseOperatorInfo addNotificationTargetListItem(String notificationTargetListItem) {
    if (this.notificationTargetList == null) {
      this.notificationTargetList = new ArrayList<>();
    }
    this.notificationTargetList.add(notificationTargetListItem);
    return this;
  }

  /**
   * 連絡先情報リスト
   * @return notificationTargetList
   */
  @NotNull 
  @Schema(name = "notificationTargetList", description = "連絡先情報リスト", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("notificationTargetList")
  public List<@Size(max = 254)String> getNotificationTargetList() {
    return notificationTargetList;
  }

  public void setNotificationTargetList(List<@Size(max = 254)String> notificationTargetList) {
    this.notificationTargetList = notificationTargetList;
  }

  public DroneRouteResponseOperatorInfo linkAirwayList(List<@Size(max = 40)String> linkAirwayList) {
    this.linkAirwayList = linkAirwayList;
    return this;
  }

  public DroneRouteResponseOperatorInfo addLinkAirwayListItem(String linkAirwayListItem) {
    if (this.linkAirwayList == null) {
      this.linkAirwayList = new ArrayList<>();
    }
    this.linkAirwayList.add(linkAirwayListItem);
    return this;
  }

  /**
   * 紐づき航路情報リスト
   * @return linkAirwayList
   */
  
  @Schema(name = "linkAirwayList", description = "紐づき航路情報リスト", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("linkAirwayList")
  public List<@Size(max = 40)String> getLinkAirwayList() {
    return linkAirwayList;
  }

  public void setLinkAirwayList(List<@Size(max = 40)String> linkAirwayList) {
    this.linkAirwayList = linkAirwayList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DroneRouteResponseOperatorInfo droneRouteResponseOperatorInfo = (DroneRouteResponseOperatorInfo) o;
    return Objects.equals(this.operatorId, droneRouteResponseOperatorInfo.operatorId) &&
        Objects.equals(this.operatorName, droneRouteResponseOperatorInfo.operatorName) &&
        Objects.equals(this.notificationType, droneRouteResponseOperatorInfo.notificationType) &&
        Objects.equals(this.roleList, droneRouteResponseOperatorInfo.roleList) &&
        Objects.equals(this.notificationTargetList, droneRouteResponseOperatorInfo.notificationTargetList) &&
        Objects.equals(this.linkAirwayList, droneRouteResponseOperatorInfo.linkAirwayList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operatorId, operatorName, notificationType, roleList, notificationTargetList, linkAirwayList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DroneRouteResponseOperatorInfo {\n");
    sb.append("    operatorId: ").append(toIndentedString(operatorId)).append("\n");
    sb.append("    operatorName: ").append(toIndentedString(operatorName)).append("\n");
    sb.append("    notificationType: ").append(toIndentedString(notificationType)).append("\n");
    sb.append("    roleList: ").append(toIndentedString(roleList)).append("\n");
    sb.append("    notificationTargetList: ").append(toIndentedString(notificationTargetList)).append("\n");
    sb.append("    linkAirwayList: ").append(toIndentedString(linkAirwayList)).append("\n");
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

