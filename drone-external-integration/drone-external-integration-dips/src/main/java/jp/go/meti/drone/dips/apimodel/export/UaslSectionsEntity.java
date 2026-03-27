/*
* 開発システム： ドローン航路基盤システム
* ファイル名： AirwayJunctionsEntityAirwaysInnerAirwayGeometry.java
* 著作権： Copyright (C) 202X-20XX,  経済産業省
* 会社名： NTT DATA Corporation
* 更新日： $Date$
*
*/
package jp.go.meti.drone.dips.apimodel.export;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * 航路設定可能空間の断面に描かれる航路/航路逸脱領域の位置座標
 */

@Schema(name = "uaslSectionsEntity", description = "航路設定可能空間の断面に描かれる航路/航路逸脱領域の位置座標")
@JsonTypeName("uaslSectionsEntity")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-13T10:34:08.568609+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class UaslSectionsEntity {

  private String uaslSectionId;

  private String uaslSectionName;

  @Valid
  private List<String> uaslPointIds = new ArrayList<>();

  @Valid
  private List<String> droneportIds = new ArrayList<>();

  public UaslSectionsEntity uaslSectionId(String uaslSectionId) {
    this.uaslSectionId = uaslSectionId;
    return this;
  }

  /**
   * 航路区画ID
   * @return uaslSectionId
   */
  
  @Schema(name = "uaslSectionId", description = "航路区画ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslSectionId")
  public String getUaslSectionId() {
    return uaslSectionId;
  }

  public void setUaslSectionId(String uaslSectionId) {
    this.uaslSectionId = uaslSectionId;
  }

  public UaslSectionsEntity uaslSectionName(String uaslSectionName) {
    this.uaslSectionName = uaslSectionName;
    return this;
  }

  /**
   * 航路区画名
   * @return uaslSectionName
   */
  
  @Schema(name = "uaslSectionName", description = "航路区画名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslSectionName")
  public String getUaslSectionName() {
    return uaslSectionName;
  }

  public void setUaslSectionName(String uaslSectionName) {
    this.uaslSectionName = uaslSectionName;
  }

  public UaslSectionsEntity uaslPointIds(List<String> uaslPointIds) {
    this.uaslPointIds = uaslPointIds;
    return this;
  }

  public UaslSectionsEntity addUaslPointIdsItem(String uaslPointIdsItem) {
    if (this.uaslPointIds == null) {
      this.uaslPointIds = new ArrayList<>();
    }
    this.uaslPointIds.add(uaslPointIdsItem);
    return this;
  }

  /**
   * 航路区画に接している航路点ID
   * @return uaslPointIds
   */
  
  @Schema(name = "uaslPointIds", example = "[\"uasl_point_1\",\"uasl_point_2\"]", description = "航路区画に接している航路点ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uaslPointIds")
  public List<String> getUaslPointIds() {
    return uaslPointIds;
  }

  public void setUaslPointIds(List<String> uaslPointIds) {
    this.uaslPointIds = uaslPointIds;
  }

  public UaslSectionsEntity droneportIds(List<String> droneportIds) {
    this.droneportIds = droneportIds;
    return this;
  }

  public UaslSectionsEntity addDroneportIdsItem(String droneportIdsItem) {
    if (this.droneportIds == null) {
      this.droneportIds = new ArrayList<>();
    }
    this.droneportIds.add(droneportIdsItem);
    return this;
  }

  /**
   * 航路区画に紐づいているドローンポートID
   * @return droneportIds
   */
  
  @Schema(name = "droneportIds", example = "[\"droneport_1\",\"droneport_2\"]", description = "航路区画に紐づいているドローンポートID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("droneportIds")
  public List<String> getDroneportIds() {
    return droneportIds;
  }

  public void setDroneportIds(List<String> droneportIds) {
    this.droneportIds = droneportIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UaslSectionsEntity uaslSectionsEntity = (UaslSectionsEntity) o;
    return Objects.equals(this.uaslSectionId, uaslSectionsEntity.uaslSectionId) &&
        Objects.equals(this.uaslSectionName, uaslSectionsEntity.uaslSectionName) &&
        Objects.equals(this.uaslPointIds, uaslSectionsEntity.uaslPointIds) &&
        Objects.equals(this.droneportIds, uaslSectionsEntity.droneportIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uaslSectionId, uaslSectionName, uaslPointIds, droneportIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslSectionsEntity {\n");
    sb.append("    uaslSectionId: ").append(toIndentedString(uaslSectionId)).append("\n");
    sb.append("    uaslSectionName: ").append(toIndentedString(uaslSectionName)).append("\n");
    sb.append("    uaslPointIds: ").append(toIndentedString(uaslPointIds)).append("\n");
    sb.append("    droneportIds: ").append(toIndentedString(droneportIds)).append("\n");
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

