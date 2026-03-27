package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;

/**
 * 離着陸場予約要素
 */
@Schema(description = "離着陸場予約要素")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class PortElement   {
  @JsonProperty("portId")
  private String portId;

  @JsonProperty("reservationId")
  private String reservationId;

  @JsonProperty("name")
  private String name;

  /**
   * 利用形態: 1=離陸, 2=着陸, 3=その他
   */
  public enum UsageTypeEnum {
    NUMBER_1(1),
    
    NUMBER_2(2),
    
    NUMBER_3(3);

    private Integer value;

    UsageTypeEnum(Integer value) {
      this.value = value;
    }

    @JsonValue
    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static UsageTypeEnum fromValue(Integer value) {
      for (UsageTypeEnum b : UsageTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("usageType")
  private UsageTypeEnum usageType;

  @JsonProperty("startAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String startAt;

  @JsonProperty("endAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String endAt;

  @JsonProperty("amount")
  private Integer amount;

  public PortElement portId(String portId) {
    this.portId = portId;
    return this;
  }

  /**
   * 離着陸場ID
   * @return portId
  */
  @Schema(description = "離着陸場ID")


  public String getPortId() {
    return portId;
  }

  public void setPortId(String portId) {
    this.portId = portId;
  }

  public PortElement reservationId(String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  /**
   * 離着陸場予約ID
   * @return reservationId
  */
  @Schema(description = "離着陸場予約ID")

  @Valid

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public PortElement name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 離着陸場の表示名
   * @return name
  */
  @Schema(description = "離着陸場の表示名")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PortElement usageType(UsageTypeEnum usageType) {
    this.usageType = usageType;
    return this;
  }

  /**
   * 利用形態: 1=離陸, 2=着陸, 3=その他
   * @return usageType
  */
  @Schema(description = "利用形態: 1=離陸, 2=着陸, 3=その他")


  public UsageTypeEnum getUsageType() {
    return usageType;
  }

  public void setUsageType(UsageTypeEnum usageType) {
    this.usageType = usageType;
  }

  public PortElement startAt(String startAt) {
    this.startAt = startAt;
    return this;
  }

  /**
   * 予約開始時間（RFC3339形式）
   * @return startAt
  */
  @Schema(description = "予約開始時間（RFC3339形式）")

  @Valid

  public String getStartAt() {
    return startAt;
  }

  public void setStartAt(String startAt) {
    this.startAt = startAt;
  }

  public PortElement endAt(String endAt) {
    this.endAt = endAt;
    return this;
  }

  /**
   * 予約終了時間（RFC3339形式）
   * @return endAt
  */
  @Schema(description = "予約終了時間（RFC3339形式）")

  @Valid

  public String getEndAt() {
    return endAt;
  }

  public void setEndAt(String endAt) {
    this.endAt = endAt;
  }

  public PortElement amount(Integer amount) {
    this.amount = amount;
    return this;
  }

  /**
   * 離着陸場の利用料金(税込)
   * @return amount
  */
  @Schema(description = "離着陸場の利用料金(税込)")


  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortElement portElement = (PortElement) o;
    return Objects.equals(this.portId, portElement.portId) &&
        Objects.equals(this.reservationId, portElement.reservationId) &&
        Objects.equals(this.name, portElement.name) &&
        Objects.equals(this.usageType, portElement.usageType) &&
        Objects.equals(this.startAt, portElement.startAt) &&
        Objects.equals(this.endAt, portElement.endAt) &&
        Objects.equals(this.amount, portElement.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portId, reservationId, name, usageType, startAt, endAt, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PortElement {\n");
    
    sb.append("    portId: ").append(toIndentedString(portId)).append("\n");
    sb.append("    reservationId: ").append(toIndentedString(reservationId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    usageType: ").append(toIndentedString(usageType)).append("\n");
    sb.append("    startAt: ").append(toIndentedString(startAt)).append("\n");
    sb.append("    endAt: ").append(toIndentedString(endAt)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

