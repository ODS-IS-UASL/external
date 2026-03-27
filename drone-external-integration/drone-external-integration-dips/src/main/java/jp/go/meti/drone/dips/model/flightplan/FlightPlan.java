package jp.go.meti.drone.dips.model.flightplan;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * FlightPlan
 */

@SuppressWarnings("javadoc")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class FlightPlan {

  private String flightPlanId;

  private String name;

  @Valid
  private List<Integer> flightPurpose = new ArrayList<>();

  private String othergyomutext;

  private String othergyomugaitext;

  @Valid
  private List<Integer> flightAirspace = new ArrayList<>();

  @Valid
  private List<Integer> flightType = new ArrayList<>();

  private Integer assistantsNumber;

  private String departurePoint;

  private String startTime;

  private String finishTime;

  private Integer plannedMaxTime;

  private Integer plannedFlightTime;

  private Integer flightSpeed;

  private Integer flightAltitude;

  private FlyRoute flyRoute;

  private String destinationPoint;

  private String riskMitigationOnsiteControl;

  private String riskMitigationOnsiteControlL3;

  private String riskMitigationOnsiteControl2;

  private String exceptionalConditionsMooring;

  private InsuranceInformation insuranceInformation;

  private Reporter reporter;

  private String otherInformation;

  @Valid
  private List<@Valid PilotInfo> pilotInfo = new ArrayList<>();

  @Valid
  private List<@Valid AircraftInfo> aircraftInfo = new ArrayList<>();

  private FlightPermitApplicationInfo flightPermitApplicationInfo;

  public FlightPlan() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FlightPlan(String flightPlanId, String startTime, String finishTime, Integer plannedMaxTime, Integer plannedFlightTime, Integer flightSpeed, Integer flightAltitude, FlyRoute flyRoute) {
    this.flightPlanId = flightPlanId;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.plannedMaxTime = plannedMaxTime;
    this.plannedFlightTime = plannedFlightTime;
    this.flightSpeed = flightSpeed;
    this.flightAltitude = flightAltitude;
    this.flyRoute = flyRoute;
  }

  public FlightPlan flightPlanId(String flightPlanId) {
    this.flightPlanId = flightPlanId;
    return this;
  }

  /**
   * 飛行計画 ID 飛行計画登録時に採番された番号 
   * @return flightPlanId
   */
  @NotNull 
  @Schema(name = "flightPlanId", example = "AAAAAAAAAAAAAAAAAAA.FP20221125042709013.001", description = "飛行計画 ID 飛行計画登録時に採番された番号 ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flightPlanId")
  public String getFlightPlanId() {
    return flightPlanId;
  }

  public void setFlightPlanId(String flightPlanId) {
    this.flightPlanId = flightPlanId;
  }

  public FlightPlan name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 飛行計画名称
   * @return name
   */
  
  @Schema(name = "name", example = "ID2", description = "飛行計画名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FlightPlan flightPurpose(List<Integer> flightPurpose) {
    this.flightPurpose = flightPurpose;
    return this;
  }

  public FlightPlan addFlightPurposeItem(Integer flightPurposeItem) {
    if (this.flightPurpose == null) {
      this.flightPurpose = new ArrayList<>();
    }
    this.flightPurpose.add(flightPurposeItem);
    return this;
  }

  /**
   * Get flightPurpose
   * @return flightPurpose
   */
  
  @Schema(name = "flightPurpose", example = "[1,5]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flightPurpose")
  public List<Integer> getFlightPurpose() {
    return flightPurpose;
  }

  public void setFlightPurpose(List<Integer> flightPurpose) {
    this.flightPurpose = flightPurpose;
  }

  public FlightPlan othergyomutext(String othergyomutext) {
    this.othergyomutext = othergyomutext;
    return this;
  }

  /**
   * その他 1 理由入力
   * @return othergyomutext
   */
  
  @Schema(name = "othergyomutext", example = "", description = "その他 1 理由入力", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("othergyomutext")
  public String getOthergyomutext() {
    return othergyomutext;
  }

  public void setOthergyomutext(String othergyomutext) {
    this.othergyomutext = othergyomutext;
  }

  public FlightPlan othergyomugaitext(String othergyomugaitext) {
    this.othergyomugaitext = othergyomugaitext;
    return this;
  }

  /**
   * その他 2 理由入力
   * @return othergyomugaitext
   */
  
  @Schema(name = "othergyomugaitext", example = "", description = "その他 2 理由入力", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("othergyomugaitext")
  public String getOthergyomugaitext() {
    return othergyomugaitext;
  }

  public void setOthergyomugaitext(String othergyomugaitext) {
    this.othergyomugaitext = othergyomugaitext;
  }

  public FlightPlan flightAirspace(List<Integer> flightAirspace) {
    this.flightAirspace = flightAirspace;
    return this;
  }

  public FlightPlan addFlightAirspaceItem(Integer flightAirspaceItem) {
    if (this.flightAirspace == null) {
      this.flightAirspace = new ArrayList<>();
    }
    this.flightAirspace.add(flightAirspaceItem);
    return this;
  }

  /**
   * Get flightAirspace
   * @return flightAirspace
   */
  
  @Schema(name = "flightAirspace", example = "[1,3]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flightAirspace")
  public List<Integer> getFlightAirspace() {
    return flightAirspace;
  }

  public void setFlightAirspace(List<Integer> flightAirspace) {
    this.flightAirspace = flightAirspace;
  }

  public FlightPlan flightType(List<Integer> flightType) {
    this.flightType = flightType;
    return this;
  }

  public FlightPlan addFlightTypeItem(Integer flightTypeItem) {
    if (this.flightType == null) {
      this.flightType = new ArrayList<>();
    }
    this.flightType.add(flightTypeItem);
    return this;
  }

  /**
   * Get flightType
   * @return flightType
   */
  
  @Schema(name = "flightType", example = "[2,4]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flightType")
  public List<Integer> getFlightType() {
    return flightType;
  }

  public void setFlightType(List<Integer> flightType) {
    this.flightType = flightType;
  }

  public FlightPlan assistantsNumber(Integer assistantsNumber) {
    this.assistantsNumber = assistantsNumber;
    return this;
  }

  /**
   * 補助者数
   * @return assistantsNumber
   */
  
  @Schema(name = "assistantsNumber", example = "5", description = "補助者数", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assistantsNumber")
  public Integer getAssistantsNumber() {
    return assistantsNumber;
  }

  public void setAssistantsNumber(Integer assistantsNumber) {
    this.assistantsNumber = assistantsNumber;
  }

  public FlightPlan departurePoint(String departurePoint) {
    this.departurePoint = departurePoint;
    return this;
  }

  /**
   * 出発地
   * @return departurePoint
   */
  
  @Schema(name = "departurePoint", example = "泉岳寺", description = "出発地", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("departurePoint")
  public String getDeparturePoint() {
    return departurePoint;
  }

  public void setDeparturePoint(String departurePoint) {
    this.departurePoint = departurePoint;
  }

  public FlightPlan startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * 飛行開始日時（yyyyMMdd HHmm）
   * @return startTime
   */
  @NotNull 
  @Schema(name = "startTime", example = "20221125 1130", description = "飛行開始日時（yyyyMMdd HHmm）", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("startTime")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public FlightPlan finishTime(String finishTime) {
    this.finishTime = finishTime;
    return this;
  }

  /**
   * 飛行終了日時（yyyyMMdd HHmm）
   * @return finishTime
   */
  @NotNull 
  @Schema(name = "finishTime", example = "20221125 1230", description = "飛行終了日時（yyyyMMdd HHmm）", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("finishTime")
  public String getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(String finishTime) {
    this.finishTime = finishTime;
  }

  public FlightPlan plannedMaxTime(Integer plannedMaxTime) {
    this.plannedMaxTime = plannedMaxTime;
    return this;
  }

  /**
   * 航続可能時間 飛行させる機体の航続可能時間（分）のうち最大値 （５分単位 5～1440） 
   * @return plannedMaxTime
   */
  @NotNull 
  @Schema(name = "plannedMaxTime", example = "120", description = "航続可能時間 飛行させる機体の航続可能時間（分）のうち最大値 （５分単位 5～1440） ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("plannedMaxTime")
  public Integer getPlannedMaxTime() {
    return plannedMaxTime;
  }

  public void setPlannedMaxTime(Integer plannedMaxTime) {
    this.plannedMaxTime = plannedMaxTime;
  }

  public FlightPlan plannedFlightTime(Integer plannedFlightTime) {
    this.plannedFlightTime = plannedFlightTime;
    return this;
  }

  /**
   * 所要時間 飛行開始時刻から飛行終了時刻までの時間（分） （５分単位 5～1440） 
   * @return plannedFlightTime
   */
  @NotNull 
  @Schema(name = "plannedFlightTime", example = "60", description = "所要時間 飛行開始時刻から飛行終了時刻までの時間（分） （５分単位 5～1440） ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("plannedFlightTime")
  public Integer getPlannedFlightTime() {
    return plannedFlightTime;
  }

  public void setPlannedFlightTime(Integer plannedFlightTime) {
    this.plannedFlightTime = plannedFlightTime;
  }

  public FlightPlan flightSpeed(Integer flightSpeed) {
    this.flightSpeed = flightSpeed;
    return this;
  }

  /**
   * 飛行速度 当該飛行で多用する速度、又は最大速度（GS）単位:km/h 
   * @return flightSpeed
   */
  @NotNull 
  @Schema(name = "flightSpeed", example = "100", description = "飛行速度 当該飛行で多用する速度、又は最大速度（GS）単位:km/h ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flightSpeed")
  public Integer getFlightSpeed() {
    return flightSpeed;
  }

  public void setFlightSpeed(Integer flightSpeed) {
    this.flightSpeed = flightSpeed;
  }

  public FlightPlan flightAltitude(Integer flightAltitude) {
    this.flightAltitude = flightAltitude;
    return this;
  }

  /**
   * 飛行する高度 当該飛行で多用する高度、又は最大高度（AGL）単位:メートル(地上からの高度) 
   * @return flightAltitude
   */
  @NotNull 
  @Schema(name = "flightAltitude", example = "120", description = "飛行する高度 当該飛行で多用する高度、又は最大高度（AGL）単位:メートル(地上からの高度) ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flightAltitude")
  public Integer getFlightAltitude() {
    return flightAltitude;
  }

  public void setFlightAltitude(Integer flightAltitude) {
    this.flightAltitude = flightAltitude;
  }

  public FlightPlan flyRoute(FlyRoute flyRoute) {
    this.flyRoute = flyRoute;
    return this;
  }

  /**
   * Get flyRoute
   * @return flyRoute
   */
  @NotNull @Valid 
  @Schema(name = "flyRoute", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flyRoute")
  public FlyRoute getFlyRoute() {
    return flyRoute;
  }

  public void setFlyRoute(FlyRoute flyRoute) {
    this.flyRoute = flyRoute;
  }

  public FlightPlan destinationPoint(String destinationPoint) {
    this.destinationPoint = destinationPoint;
    return this;
  }

  /**
   * 目的地
   * @return destinationPoint
   */
  
  @Schema(name = "destinationPoint", example = "京急泉岳寺駅", description = "目的地", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("destinationPoint")
  public String getDestinationPoint() {
    return destinationPoint;
  }

  public void setDestinationPoint(String destinationPoint) {
    this.destinationPoint = destinationPoint;
  }

  public FlightPlan riskMitigationOnsiteControl(String riskMitigationOnsiteControl) {
    this.riskMitigationOnsiteControl = riskMitigationOnsiteControl;
    return this;
  }

  /**
   * 立入管理措置 立入管理措置を講じる場合\"1\",立入管理措置を講じない場合\"0\" 
   * @return riskMitigationOnsiteControl
   */
  
  @Schema(name = "riskMitigationOnsiteControl", example = "1", description = "立入管理措置 立入管理措置を講じる場合\"1\",立入管理措置を講じない場合\"0\" ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("riskMitigationOnsiteControl")
  public String getRiskMitigationOnsiteControl() {
    return riskMitigationOnsiteControl;
  }

  public void setRiskMitigationOnsiteControl(String riskMitigationOnsiteControl) {
    this.riskMitigationOnsiteControl = riskMitigationOnsiteControl;
  }

  public FlightPlan riskMitigationOnsiteControlL3(String riskMitigationOnsiteControlL3) {
    this.riskMitigationOnsiteControlL3 = riskMitigationOnsiteControlL3;
    return this;
  }

  /**
   * 立入管理措置（レベル3飛行） 立入管理措置（レベル 3 飛行）を講じる場合\"1\",立入管理措置を講じない場合\"0\" 
   * @return riskMitigationOnsiteControlL3
   */
  
  @Schema(name = "riskMitigationOnsiteControlL3", example = "0", description = "立入管理措置（レベル3飛行） 立入管理措置（レベル 3 飛行）を講じる場合\"1\",立入管理措置を講じない場合\"0\" ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("riskMitigationOnsiteControlL3")
  public String getRiskMitigationOnsiteControlL3() {
    return riskMitigationOnsiteControlL3;
  }

  public void setRiskMitigationOnsiteControlL3(String riskMitigationOnsiteControlL3) {
    this.riskMitigationOnsiteControlL3 = riskMitigationOnsiteControlL3;
  }

  public FlightPlan riskMitigationOnsiteControl2(String riskMitigationOnsiteControl2) {
    this.riskMitigationOnsiteControl2 = riskMitigationOnsiteControl2;
    return this;
  }

  /**
   * 立入禁止措置 立入禁止措置を講じる場合\"1\",立入管理措置を講じない場合\"0\" 
   * @return riskMitigationOnsiteControl2
   */
  
  @Schema(name = "riskMitigationOnsiteControl2", example = "0", description = "立入禁止措置 立入禁止措置を講じる場合\"1\",立入管理措置を講じない場合\"0\" ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("riskMitigationOnsiteControl2")
  public String getRiskMitigationOnsiteControl2() {
    return riskMitigationOnsiteControl2;
  }

  public void setRiskMitigationOnsiteControl2(String riskMitigationOnsiteControl2) {
    this.riskMitigationOnsiteControl2 = riskMitigationOnsiteControl2;
  }

  public FlightPlan exceptionalConditionsMooring(String exceptionalConditionsMooring) {
    this.exceptionalConditionsMooring = exceptionalConditionsMooring;
    return this;
  }

  /**
   * 係留飛行 係留飛行を行う場合\"1\",係留飛行を行ない場合\"0\" 
   * @return exceptionalConditionsMooring
   */
  
  @Schema(name = "exceptionalConditionsMooring", example = "1", description = "係留飛行 係留飛行を行う場合\"1\",係留飛行を行ない場合\"0\" ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("exceptionalConditionsMooring")
  public String getExceptionalConditionsMooring() {
    return exceptionalConditionsMooring;
  }

  public void setExceptionalConditionsMooring(String exceptionalConditionsMooring) {
    this.exceptionalConditionsMooring = exceptionalConditionsMooring;
  }

  public FlightPlan insuranceInformation(InsuranceInformation insuranceInformation) {
    this.insuranceInformation = insuranceInformation;
    return this;
  }

  /**
   * Get insuranceInformation
   * @return insuranceInformation
   */
  @Valid 
  @Schema(name = "insuranceInformation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("insuranceInformation")
  public InsuranceInformation getInsuranceInformation() {
    return insuranceInformation;
  }

  public void setInsuranceInformation(InsuranceInformation insuranceInformation) {
    this.insuranceInformation = insuranceInformation;
  }

  public FlightPlan reporter(Reporter reporter) {
    this.reporter = reporter;
    return this;
  }

  /**
   * Get reporter
   * @return reporter
   */
  @Valid 
  @Schema(name = "reporter", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reporter")
  public Reporter getReporter() {
    return reporter;
  }

  public void setReporter(Reporter reporter) {
    this.reporter = reporter;
  }

  public FlightPlan otherInformation(String otherInformation) {
    this.otherInformation = otherInformation;
    return this;
  }

  /**
   * その他特記事項
   * @return otherInformation
   */
  
  @Schema(name = "otherInformation", example = "その他特記事項なし", description = "その他特記事項", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("otherInformation")
  public String getOtherInformation() {
    return otherInformation;
  }

  public void setOtherInformation(String otherInformation) {
    this.otherInformation = otherInformation;
  }

  public FlightPlan pilotInfo(List<@Valid PilotInfo> pilotInfo) {
    this.pilotInfo = pilotInfo;
    return this;
  }

  public FlightPlan addPilotInfoItem(PilotInfo pilotInfoItem) {
    if (this.pilotInfo == null) {
      this.pilotInfo = new ArrayList<>();
    }
    this.pilotInfo.add(pilotInfoItem);
    return this;
  }

  /**
   * 操縦者情報
   * @return pilotInfo
   */
  @Valid 
  @Schema(name = "pilotInfo", description = "操縦者情報", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pilotInfo")
  public List<@Valid PilotInfo> getPilotInfo() {
    return pilotInfo;
  }

  public void setPilotInfo(List<@Valid PilotInfo> pilotInfo) {
    this.pilotInfo = pilotInfo;
  }

  public FlightPlan aircraftInfo(List<@Valid AircraftInfo> aircraftInfo) {
    this.aircraftInfo = aircraftInfo;
    return this;
  }

  public FlightPlan addAircraftInfoItem(AircraftInfo aircraftInfoItem) {
    if (this.aircraftInfo == null) {
      this.aircraftInfo = new ArrayList<>();
    }
    this.aircraftInfo.add(aircraftInfoItem);
    return this;
  }

  /**
   * 機体情報
   * @return aircraftInfo
   */
  @Valid 
  @Schema(name = "aircraftInfo", description = "機体情報", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("aircraftInfo")
  public List<@Valid AircraftInfo> getAircraftInfo() {
    return aircraftInfo;
  }

  public void setAircraftInfo(List<@Valid AircraftInfo> aircraftInfo) {
    this.aircraftInfo = aircraftInfo;
  }

  public FlightPlan flightPermitApplicationInfo(FlightPermitApplicationInfo flightPermitApplicationInfo) {
    this.flightPermitApplicationInfo = flightPermitApplicationInfo;
    return this;
  }

  /**
   * Get flightPermitApplicationInfo
   * @return flightPermitApplicationInfo
   */
  @Valid 
  @Schema(name = "flightPermitApplicationInfo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flightPermitApplicationInfo")
  public FlightPermitApplicationInfo getFlightPermitApplicationInfo() {
    return flightPermitApplicationInfo;
  }

  public void setFlightPermitApplicationInfo(FlightPermitApplicationInfo flightPermitApplicationInfo) {
    this.flightPermitApplicationInfo = flightPermitApplicationInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FlightPlan flightPlan = (FlightPlan) o;
    return Objects.equals(this.flightPlanId, flightPlan.flightPlanId) &&
        Objects.equals(this.name, flightPlan.name) &&
        Objects.equals(this.flightPurpose, flightPlan.flightPurpose) &&
        Objects.equals(this.othergyomutext, flightPlan.othergyomutext) &&
        Objects.equals(this.othergyomugaitext, flightPlan.othergyomugaitext) &&
        Objects.equals(this.flightAirspace, flightPlan.flightAirspace) &&
        Objects.equals(this.flightType, flightPlan.flightType) &&
        Objects.equals(this.assistantsNumber, flightPlan.assistantsNumber) &&
        Objects.equals(this.departurePoint, flightPlan.departurePoint) &&
        Objects.equals(this.startTime, flightPlan.startTime) &&
        Objects.equals(this.finishTime, flightPlan.finishTime) &&
        Objects.equals(this.plannedMaxTime, flightPlan.plannedMaxTime) &&
        Objects.equals(this.plannedFlightTime, flightPlan.plannedFlightTime) &&
        Objects.equals(this.flightSpeed, flightPlan.flightSpeed) &&
        Objects.equals(this.flightAltitude, flightPlan.flightAltitude) &&
        Objects.equals(this.flyRoute, flightPlan.flyRoute) &&
        Objects.equals(this.destinationPoint, flightPlan.destinationPoint) &&
        Objects.equals(this.riskMitigationOnsiteControl, flightPlan.riskMitigationOnsiteControl) &&
        Objects.equals(this.riskMitigationOnsiteControlL3, flightPlan.riskMitigationOnsiteControlL3) &&
        Objects.equals(this.riskMitigationOnsiteControl2, flightPlan.riskMitigationOnsiteControl2) &&
        Objects.equals(this.exceptionalConditionsMooring, flightPlan.exceptionalConditionsMooring) &&
        Objects.equals(this.insuranceInformation, flightPlan.insuranceInformation) &&
        Objects.equals(this.reporter, flightPlan.reporter) &&
        Objects.equals(this.otherInformation, flightPlan.otherInformation) &&
        Objects.equals(this.pilotInfo, flightPlan.pilotInfo) &&
        Objects.equals(this.aircraftInfo, flightPlan.aircraftInfo) &&
        Objects.equals(this.flightPermitApplicationInfo, flightPlan.flightPermitApplicationInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flightPlanId, name, flightPurpose, othergyomutext, othergyomugaitext, flightAirspace, flightType, assistantsNumber, departurePoint, startTime, finishTime, plannedMaxTime, plannedFlightTime, flightSpeed, flightAltitude, flyRoute, destinationPoint, riskMitigationOnsiteControl, riskMitigationOnsiteControlL3, riskMitigationOnsiteControl2, exceptionalConditionsMooring, insuranceInformation, reporter, otherInformation, pilotInfo, aircraftInfo, flightPermitApplicationInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlightPlan {\n");
    sb.append("    flightPlanId: ").append(toIndentedString(flightPlanId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    flightPurpose: ").append(toIndentedString(flightPurpose)).append("\n");
    sb.append("    othergyomutext: ").append(toIndentedString(othergyomutext)).append("\n");
    sb.append("    othergyomugaitext: ").append(toIndentedString(othergyomugaitext)).append("\n");
    sb.append("    flightAirspace: ").append(toIndentedString(flightAirspace)).append("\n");
    sb.append("    flightType: ").append(toIndentedString(flightType)).append("\n");
    sb.append("    assistantsNumber: ").append(toIndentedString(assistantsNumber)).append("\n");
    sb.append("    departurePoint: ").append(toIndentedString(departurePoint)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    finishTime: ").append(toIndentedString(finishTime)).append("\n");
    sb.append("    plannedMaxTime: ").append(toIndentedString(plannedMaxTime)).append("\n");
    sb.append("    plannedFlightTime: ").append(toIndentedString(plannedFlightTime)).append("\n");
    sb.append("    flightSpeed: ").append(toIndentedString(flightSpeed)).append("\n");
    sb.append("    flightAltitude: ").append(toIndentedString(flightAltitude)).append("\n");
    sb.append("    flyRoute: ").append(toIndentedString(flyRoute)).append("\n");
    sb.append("    destinationPoint: ").append(toIndentedString(destinationPoint)).append("\n");
    sb.append("    riskMitigationOnsiteControl: ").append(toIndentedString(riskMitigationOnsiteControl)).append("\n");
    sb.append("    riskMitigationOnsiteControlL3: ").append(toIndentedString(riskMitigationOnsiteControlL3)).append("\n");
    sb.append("    riskMitigationOnsiteControl2: ").append(toIndentedString(riskMitigationOnsiteControl2)).append("\n");
    sb.append("    exceptionalConditionsMooring: ").append(toIndentedString(exceptionalConditionsMooring)).append("\n");
    sb.append("    insuranceInformation: ").append(toIndentedString(insuranceInformation)).append("\n");
    sb.append("    reporter: ").append(toIndentedString(reporter)).append("\n");
    sb.append("    otherInformation: ").append(toIndentedString(otherInformation)).append("\n");
    sb.append("    pilotInfo: ").append(toIndentedString(pilotInfo)).append("\n");
    sb.append("    aircraftInfo: ").append(toIndentedString(aircraftInfo)).append("\n");
    sb.append("    flightPermitApplicationInfo: ").append(toIndentedString(flightPermitApplicationInfo)).append("\n");
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

