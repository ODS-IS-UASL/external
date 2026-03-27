package jp.go.meti.drone.dips.model.flightplan;


import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * DroneRouteFlightPlanInfoRequest
 */

@SuppressWarnings("javadoc")
@JsonTypeName("droneRoute.FlightPlanInfoRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class DroneRouteFlightPlanInfoRequest {

  private Geometry features;

  private String allFlightPlan;

  private String startTime;

  private String finishTime;

  private String updateTime;

  public DroneRouteFlightPlanInfoRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DroneRouteFlightPlanInfoRequest(Geometry features) {
    this.features = features;
  }

  public DroneRouteFlightPlanInfoRequest features(Geometry features) {
    this.features = features;
    return this;
  }

  /**
   * Get features
   * @return features
   */
  @NotNull @Valid 
  @Schema(name = "features", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("features")
  public Geometry getFeatures() {
    return features;
  }

  public void setFeatures(Geometry features) {
    this.features = features;
  }

  public DroneRouteFlightPlanInfoRequest allFlightPlan(String allFlightPlan) {
    this.allFlightPlan = allFlightPlan;
    return this;
  }

  /**
   * 検索対象利用者 \"0\": 全ユーザー, \"1\": 自アカウントのみ 指定がない場合は全ユーザー 
   * @return allFlightPlan
   */
  
  @Schema(name = "allFlightPlan", example = "0", description = "検索対象利用者 \"0\": 全ユーザー, \"1\": 自アカウントのみ 指定がない場合は全ユーザー ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("allFlightPlan")
  public String getAllFlightPlan() {
    return allFlightPlan;
  }

  
public void setAllFlightPlan(String allFlightPlan) {
    this.allFlightPlan = allFlightPlan;
  }

  public DroneRouteFlightPlanInfoRequest startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * 検索開始時刻 検索時間に含まれる飛行計画が検索される yyyyMMdd□HHmm 形式（□は半角スペース) 指定がない場合当日0 時 0 分 
   * @return startTime
   */
  
  @Schema(name = "startTime", example = "20221118 1200", description = "検索開始時刻 検索時間に含まれる飛行計画が検索される yyyyMMdd□HHmm 形式（□は半角スペース) 指定がない場合当日0 時 0 分 ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startTime")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public DroneRouteFlightPlanInfoRequest finishTime(String finishTime) {
    this.finishTime = finishTime;
    return this;
  }

  /**
   * 検索終了時刻 検索時間に含まれる飛行計画が検索される  yyyyMMdd□HHmm 形式（□は半角スペース） 指定がない場合当日 23 時 59 分 開始時刻より 24 時間以内 
   * @return finishTime
   */
  @Valid 
  @Schema(name = "finishTime", example = "20221118 2359", description = "検索終了時刻 検索時間に含まれる飛行計画が検索される  yyyyMMdd□HHmm 形式（□は半角スペース） 指定がない場合当日 23 時 59 分 開始時刻より 24 時間以内 ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("finishTime")
  public String getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(String finishTime) {
    this.finishTime = finishTime;
  }

  public DroneRouteFlightPlanInfoRequest updateTime(String updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  /**
   * 更新時刻 指定時刻以降に更新されたものを検索対象とする  yyyyMMdd□HHmm 形式（□は半角スペース) 指定がない場合は全て  
   * @return updateTime
   */
  
  @Schema(name = "updateTime", example = "20221118 0000", description = "更新時刻 指定時刻以降に更新されたものを検索対象とする  yyyyMMdd□HHmm 形式（□は半角スペース) 指定がない場合は全て  ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updateTime")
  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DroneRouteFlightPlanInfoRequest droneRouteFlightPlanInfoRequest = (DroneRouteFlightPlanInfoRequest) o;
    return Objects.equals(this.features, droneRouteFlightPlanInfoRequest.features) &&
        Objects.equals(this.allFlightPlan, droneRouteFlightPlanInfoRequest.allFlightPlan) &&
        Objects.equals(this.startTime, droneRouteFlightPlanInfoRequest.startTime) &&
        Objects.equals(this.finishTime, droneRouteFlightPlanInfoRequest.finishTime) &&
        Objects.equals(this.updateTime, droneRouteFlightPlanInfoRequest.updateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(features, allFlightPlan, startTime, finishTime, updateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DroneRouteFlightPlanInfoRequest {\n");
    sb.append("    features: ").append(toIndentedString(features)).append("\n");
    sb.append("    allFlightPlan: ").append(toIndentedString(allFlightPlan)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    finishTime: ").append(toIndentedString(finishTime)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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

