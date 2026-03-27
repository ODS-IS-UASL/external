package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;

/**
 * 航路区画予約情報
 */
@Schema(description = "航路区画予約情報")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UaslSectionEntity   {
  @JsonProperty("uaslSectionId")
  private String uaslSectionId;

  @JsonProperty("startAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String startAt;

  @JsonProperty("endAt")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private String endAt;

  @JsonProperty("sequence")
  private Integer sequence;

  @JsonProperty("amount")
  private Integer amount;

  public UaslSectionEntity uaslSectionId(String uaslSectionId) {
    this.uaslSectionId = uaslSectionId;
    return this;
  }

  /**
   * 航路区画ID。当該航路区画が属する航路の航路画定サービスが発行する。
   * @return uaslSectionId
  */
  @Schema(description = "航路区画ID。当該航路区画が属する航路の航路画定サービスが発行する。")

  @Valid

  public String getUaslSectionId() {
    return uaslSectionId;
  }

  public void setUaslSectionId(String uaslSectionId) {
    this.uaslSectionId = uaslSectionId;
  }

  public UaslSectionEntity startAt(String startAt) {
    this.startAt = startAt;
    return this;
  }

  /**
   * 航路区画の予約開始日時（RFC3339形式）
   * @return startAt
  */
  @Schema(description = "航路区画の予約開始日時（RFC3339形式）")

  @Valid

  public String getStartAt() {
    return startAt;
  }

  public void setStartAt(String startAt) {
    this.startAt = startAt;
  }

  public UaslSectionEntity endAt(String endAt) {
    this.endAt = endAt;
    return this;
  }

  /**
   * 航路区画の予約終了日時（RFC3339形式）
   * @return endAt
  */
  @Schema(description = "航路区画の予約終了日時（RFC3339形式）")

  @Valid

  public String getEndAt() {
    return endAt;
  }

  public void setEndAt(String endAt) {
    this.endAt = endAt;
  }

  public UaslSectionEntity sequence(Integer sequence) {
    this.sequence = sequence;
    return this;
  }

  /**
   * 通過順（航路区画を通過する順序を示す）
   * @return sequence
  */
  @Schema(description = "通過順（航路区画を通過する順序を示す）")


  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public UaslSectionEntity amount(Integer amount) {
    this.amount = amount;
    return this;
  }

  /**
   * 航路区画の利用料金
   * @return amount
  */
  @Schema(description = "航路区画の利用料金")


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
    UaslSectionEntity uaslSectionEntity = (UaslSectionEntity) o;
    return Objects.equals(this.uaslSectionId, uaslSectionEntity.uaslSectionId) &&
        Objects.equals(this.startAt, uaslSectionEntity.startAt) &&
        Objects.equals(this.endAt, uaslSectionEntity.endAt) &&
        Objects.equals(this.sequence, uaslSectionEntity.sequence) &&
        Objects.equals(this.amount, uaslSectionEntity.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uaslSectionId, startAt, endAt, sequence, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UaslSectionEntity {\n");
    
    sb.append("    uaslSectionId: ").append(toIndentedString(uaslSectionId)).append("\n");
    sb.append("    startAt: ").append(toIndentedString(startAt)).append("\n");
    sb.append("    endAt: ").append(toIndentedString(endAt)).append("\n");
    sb.append("    sequence: ").append(toIndentedString(sequence)).append("\n");
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

