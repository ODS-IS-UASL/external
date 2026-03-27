package jp.go.meti.drone.dips.model.flightplan;

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
 * DroneRouteFlightPlanInfoResponse
 */
@SuppressWarnings("javadoc")
@JsonTypeName("droneRoute.FlightPlanInfoResponse")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class DroneRouteFlightPlanInfoResponse {

  @Valid
  private List<@Valid FlightPlan> flightPlanInfo = new ArrayList<>();

  private Integer totalCount;

public DroneRouteFlightPlanInfoResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DroneRouteFlightPlanInfoResponse(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public DroneRouteFlightPlanInfoResponse flightPlanInfo(List<@Valid FlightPlan> flightPlanInfo) {
    this.flightPlanInfo = flightPlanInfo;
    return this;
  }

  public DroneRouteFlightPlanInfoResponse addFlightPlanInfoItem(FlightPlan flightPlanInfoItem) {
    if (this.flightPlanInfo == null) {
      this.flightPlanInfo = new ArrayList<>();
    }
    this.flightPlanInfo.add(flightPlanInfoItem);
    return this;
  }

  /**
   * 飛行計画情報一覧
   * @return flightPlanInfo
   */
  @Valid 
  @Schema(name = "flightPlanInfo", description = "飛行計画情報一覧", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flightPlanInfo")
  public List<@Valid FlightPlan> getFlightPlanInfo() {
    return flightPlanInfo;
  }

  public void setFlightPlanInfo(List<@Valid FlightPlan> flightPlanInfo) {
    this.flightPlanInfo = flightPlanInfo;
  }

  public DroneRouteFlightPlanInfoResponse totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * 総件数
   * @return totalCount
   */
  @NotNull 
  @Schema(name = "totalCount", example = "1", description = "総件数", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalCount")
  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DroneRouteFlightPlanInfoResponse droneRouteFlightPlanInfoResponse = (DroneRouteFlightPlanInfoResponse) o;
    return Objects.equals(this.flightPlanInfo, droneRouteFlightPlanInfoResponse.flightPlanInfo) &&
        Objects.equals(this.totalCount, droneRouteFlightPlanInfoResponse.totalCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flightPlanInfo, totalCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DroneRouteFlightPlanInfoResponse {\n");
    sb.append("    flightPlanInfo: ").append(toIndentedString(flightPlanInfo)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
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

