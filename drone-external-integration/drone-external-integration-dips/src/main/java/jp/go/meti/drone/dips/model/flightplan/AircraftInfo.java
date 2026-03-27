package jp.go.meti.drone.dips.model.flightplan;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * 機体情報
 */

@SuppressWarnings("javadoc")
@Schema(name = "AircraftInfo", description = "機体情報")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-16T11:33:36.290655300+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class AircraftInfo {

  private Integer aircraftId;

  private String type;

  private String certificationNum;

  private String symbol;

  private String model;

  private String maker;

  private String certification1;

  private String certification2;

  private BigDecimal maxWeight;

  public AircraftInfo aircraftId(Integer aircraftId) {
    this.aircraftId = aircraftId;
    return this;
  }

  /**
   * 機体ID
   * @return aircraftId
   */
  
  @Schema(name = "aircraftId", example = "1132709", description = "機体ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("aircraftId")
  public Integer getAircraftId() {
    return aircraftId;
  }

  public void setAircraftId(Integer aircraftId) {
    this.aircraftId = aircraftId;
  }

  public AircraftInfo type(String type) {
    this.type = type;
    return this;
  }

  /**
   * 機体の種類: 1: 飛行機, 2: 回転翼航空機（ヘリコプター）, 3: 回転翼航空機（マルチローター）, 4: 回転翼航空機（その他）, 5: 滑空機, 6: 飛行船 
   * @return type
   */
  
  @Schema(name = "type", example = "2", description = "機体の種類: 1: 飛行機, 2: 回転翼航空機（ヘリコプター）, 3: 回転翼航空機（マルチローター）, 4: 回転翼航空機（その他）, 5: 滑空機, 6: 飛行船 ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public AircraftInfo certificationNum(String certificationNum) {
    this.certificationNum = certificationNum;
    return this;
  }

  /**
   * 機体認証書番号
   * @return certificationNum
   */
  
  @Schema(name = "certificationNum", example = "12345678901", description = "機体認証書番号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("certificationNum")
  public String getCertificationNum() {
    return certificationNum;
  }

  public void setCertificationNum(String certificationNum) {
    this.certificationNum = certificationNum;
  }

  public AircraftInfo symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  /**
   * 登録記号
   * @return symbol
   */
  
  @Schema(name = "symbol", example = "JU1234567890", description = "登録記号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("symbol")
  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public AircraftInfo model(String model) {
    this.model = model;
    return this;
  }

  /**
   * 型式／名称
   * @return model
   */
  
  @Schema(name = "model", example = "model001", description = "型式／名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("model")
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public AircraftInfo maker(String maker) {
    this.maker = maker;
    return this;
  }

  /**
   * 製造者名
   * @return maker
   */
  
  @Schema(name = "maker", example = "maker001", description = "製造者名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maker")
  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  public AircraftInfo certification1(String certification1) {
    this.certification1 = certification1;
    return this;
  }

  /**
   * 機体認証（第一種）（\"1\": 有り, \"0\": 無し） 
   * @return certification1
   */
  
  @Schema(name = "certification1", example = "1", description = "機体認証（第一種）（\"1\": 有り, \"0\": 無し） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("certification1")
  public String getCertification1() {
    return certification1;
  }

  public void setCertification1(String certification1) {
    this.certification1 = certification1;
  }

  public AircraftInfo certification2(String certification2) {
    this.certification2 = certification2;
    return this;
  }

  /**
   * 機体認証（第二種）（\"1\": 有り, \"0\": 無し） 
   * @return certification2
   */
  
  @Schema(name = "certification2", example = "0", description = "機体認証（第二種）（\"1\": 有り, \"0\": 無し） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("certification2")
  public String getCertification2() {
    return certification2;
  }

  public void setCertification2(String certification2) {
    this.certification2 = certification2;
  }

  public AircraftInfo maxWeight(BigDecimal maxWeight) {
    this.maxWeight = maxWeight;
    return this;
  }

  /**
   * 最大離陸重量（kg）
   * @return maxWeight
   */
  @Valid 
  @Schema(name = "maxWeight", example = "20.5", description = "最大離陸重量（kg）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxWeight")
  public BigDecimal getMaxWeight() {
    return maxWeight;
  }

  public void setMaxWeight(BigDecimal maxWeight) {
    this.maxWeight = maxWeight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AircraftInfo aircraftInfo = (AircraftInfo) o;
    return Objects.equals(this.aircraftId, aircraftInfo.aircraftId) &&
        Objects.equals(this.type, aircraftInfo.type) &&
        Objects.equals(this.certificationNum, aircraftInfo.certificationNum) &&
        Objects.equals(this.symbol, aircraftInfo.symbol) &&
        Objects.equals(this.model, aircraftInfo.model) &&
        Objects.equals(this.maker, aircraftInfo.maker) &&
        Objects.equals(this.certification1, aircraftInfo.certification1) &&
        Objects.equals(this.certification2, aircraftInfo.certification2) &&
        Objects.equals(this.maxWeight, aircraftInfo.maxWeight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(aircraftId, type, certificationNum, symbol, model, maker, certification1, certification2, maxWeight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AircraftInfo {\n");
    sb.append("    aircraftId: ").append(toIndentedString(aircraftId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    certificationNum: ").append(toIndentedString(certificationNum)).append("\n");
    sb.append("    symbol: ").append(toIndentedString(symbol)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    maker: ").append(toIndentedString(maker)).append("\n");
    sb.append("    certification1: ").append(toIndentedString(certification1)).append("\n");
    sb.append("    certification2: ").append(toIndentedString(certification2)).append("\n");
    sb.append("    maxWeight: ").append(toIndentedString(maxWeight)).append("\n");
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

