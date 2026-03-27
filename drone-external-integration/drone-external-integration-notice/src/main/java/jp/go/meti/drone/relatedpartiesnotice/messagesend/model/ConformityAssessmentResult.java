package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;

/**
 * 航路区画ごとの適合性評価結果
 */
@Schema(description = "航路区画ごとの適合性評価結果")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-02-25T13:26:04.084+09:00[Asia/Tokyo]")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ConformityAssessmentResult   {
  @JsonProperty("uaslSectionId")
  private String uaslSectionId;

  @JsonProperty("aircraftInfo")
  private ConformityAircraftInfo aircraftInfo;

  @JsonProperty("evaluationResults")
  private String evaluationResults;

  /**
   * 適合性評価結果がNGの場合の種別 \"weather\": 天候・風速条件によるNG \"event\": 規制／イベントによるNG \"railway\": 鉄道運行によるNG \"intrusion\": 第三者立入によるNG ※evaluationResultsがfalseの場合に参照 
   */
  public enum TypeEnum {
    WEATHER("weather"),
    
    EVENT("event"),
    
    RAILWAY("railway"),
    
    INTRUSION("intrusion"),
    
    NULL("null");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("type")
  private TypeEnum type;

  @JsonProperty("reasons")
  private String reasons;

  public ConformityAssessmentResult uaslSectionId(String uaslSectionId) {
    this.uaslSectionId = uaslSectionId;
    return this;
  }

  /**
   * 評価対象の航路区画ID
   * @return uaslSectionId
  */
  @Schema(description = "評価対象の航路区画ID")

  @Valid

  public String getUaslSectionId() {
    return uaslSectionId;
  }

  public void setUaslSectionId(String uaslSectionId) {
    this.uaslSectionId = uaslSectionId;
  }

  public ConformityAssessmentResult aircraftInfo(ConformityAircraftInfo aircraftInfo) {
    this.aircraftInfo = aircraftInfo;
    return this;
  }

  /**
   * Get aircraftInfo
   * @return aircraftInfo
  */
  @Schema(description = "")

  @Valid

  public ConformityAircraftInfo getAircraftInfo() {
    return aircraftInfo;
  }

  public void setAircraftInfo(ConformityAircraftInfo aircraftInfo) {
    this.aircraftInfo = aircraftInfo;
  }

  public ConformityAssessmentResult evaluationResults(String evaluationResults) {
    this.evaluationResults = evaluationResults;
    return this;
  }

  /**
   * 適合性評価結果 true : 適合性評価結果OK false: 適合性評価結果NG 
   * @return evaluationResults
  */
  @Schema(description = "適合性評価結果 true : 適合性評価結果OK false: 適合性評価結果NG ")


  public String getEvaluationResults() {
    return evaluationResults;
  }

  public void setEvaluationResults(String evaluationResults) {
    this.evaluationResults = evaluationResults;
  }

  public ConformityAssessmentResult type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * 適合性評価結果がNGの場合の種別 \"weather\": 天候・風速条件によるNG \"event\": 規制／イベントによるNG \"railway\": 鉄道運行によるNG \"intrusion\": 第三者立入によるNG ※evaluationResultsがfalseの場合に参照 
   * @return type
  */
  @Schema(description = "適合性評価結果がNGの場合の種別 \"weather\": 天候・風速条件によるNG \"event\": 規制／イベントによるNG \"railway\": 鉄道運行によるNG \"intrusion\": 第三者立入によるNG ※evaluationResultsがfalseの場合に参照 ")


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public ConformityAssessmentResult reasons(String reasons) {
    this.reasons = reasons;
    return this;
  }

  /**
   * 適合性評価結果がNGの場合の詳細 ※evaluationResultsがfalseの場合に参照 
   * @return reasons
  */
  @Schema(description = "適合性評価結果がNGの場合の詳細 ※evaluationResultsがfalseの場合に参照 ")


  public String getReasons() {
    return reasons;
  }

  public void setReasons(String reasons) {
    this.reasons = reasons;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConformityAssessmentResult conformityAssessmentResult = (ConformityAssessmentResult) o;
    return Objects.equals(this.uaslSectionId, conformityAssessmentResult.uaslSectionId) &&
        Objects.equals(this.aircraftInfo, conformityAssessmentResult.aircraftInfo) &&
        Objects.equals(this.evaluationResults, conformityAssessmentResult.evaluationResults) &&
        Objects.equals(this.type, conformityAssessmentResult.type) &&
        Objects.equals(this.reasons, conformityAssessmentResult.reasons);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uaslSectionId, aircraftInfo, evaluationResults, type, reasons);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConformityAssessmentResult {\n");
    
    sb.append("    uaslSectionId: ").append(toIndentedString(uaslSectionId)).append("\n");
    sb.append("    aircraftInfo: ").append(toIndentedString(aircraftInfo)).append("\n");
    sb.append("    evaluationResults: ").append(toIndentedString(evaluationResults)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    reasons: ").append(toIndentedString(reasons)).append("\n");
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

