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
 * 事業者情報リスト
 */

@Schema(name = "droneRoute.ResponseOperatorList", description = "事業者情報リスト")
@JsonTypeName("droneRoute.ResponseOperatorList")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-12-04T11:19:34.085772500+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class DroneRouteResponseOperatorList {

  @Valid
  private List<@Valid DroneRouteResponseOperatorInfo> operatorList = new ArrayList<>();

  public DroneRouteResponseOperatorList() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DroneRouteResponseOperatorList(List<@Valid DroneRouteResponseOperatorInfo> operatorList) {
    this.operatorList = operatorList;
  }

  public DroneRouteResponseOperatorList operatorList(List<@Valid DroneRouteResponseOperatorInfo> operatorList) {
    this.operatorList = operatorList;
    return this;
  }

  public DroneRouteResponseOperatorList addOperatorListItem(DroneRouteResponseOperatorInfo operatorListItem) {
    if (this.operatorList == null) {
      this.operatorList = new ArrayList<>();
    }
    this.operatorList.add(operatorListItem);
    return this;
  }

  /**
   * Get operatorList
   * @return operatorList
   */
  @NotNull @Valid 
  @Schema(name = "operatorList", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operatorList")
  public List<@Valid DroneRouteResponseOperatorInfo> getOperatorList() {
    return operatorList;
  }

  public void setOperatorList(List<@Valid DroneRouteResponseOperatorInfo> operatorList) {
    this.operatorList = operatorList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DroneRouteResponseOperatorList droneRouteResponseOperatorList = (DroneRouteResponseOperatorList) o;
    return Objects.equals(this.operatorList, droneRouteResponseOperatorList.operatorList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operatorList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DroneRouteResponseOperatorList {\n");
    sb.append("    operatorList: ").append(toIndentedString(operatorList)).append("\n");
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

