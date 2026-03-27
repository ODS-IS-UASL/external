package jp.go.meti.drone.dips.model.commonmodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.constraints.*;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Generated;

/**
 * エラーオブジェクト
 */

@Schema(name = "common.ResponseDipsAccessTokenError", description = "エラーオブジェクト")
@JsonTypeName("common.ResponseDipsAccessTokenError")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-09-24T15:47:47.380274600+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class CommonResponseDipsAccessTokenError {

  private Integer code;

  private String errorMessage;

  public CommonResponseDipsAccessTokenError() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CommonResponseDipsAccessTokenError(Integer code, String errorMessage) {
    this.code = code;
    this.errorMessage = errorMessage;
  }

  public CommonResponseDipsAccessTokenError code(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * エラーの種類を示すエラーコード
   * @return code
   */
  @NotNull 
  @Schema(name = "code", example = "401", description = "エラーの種類を示すエラーコード", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public CommonResponseDipsAccessTokenError errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * エラーの説明
   * @return errorMessage
   */
  @NotNull 
  @Schema(name = "errorMessage", example = "DIPSアクセストークン例外。", description = "エラーの説明", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommonResponseDipsAccessTokenError commonResponseDipsAccessTokenError = (CommonResponseDipsAccessTokenError) o;
    return Objects.equals(this.code, commonResponseDipsAccessTokenError.code) &&
        Objects.equals(this.errorMessage, commonResponseDipsAccessTokenError.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommonResponseDipsAccessTokenError {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
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
  
  private String createErrorResponse() {
      StringBuilder sb = new StringBuilder();
      sb.append("{\n");
      sb.append("    \"code\": ").append(toIndentedString(code)).append(",\n");
      sb.append("    \"errorMessage\": \"").append(toIndentedString(errorMessage)).append("\"\n");
      sb.append("}");
      return sb.toString();
  }

  /**
   * パラメーターに誤りがある場合
   * 
   * @return ResponseEntity<Resource>
   */
  public ResponseEntity<String> errorResponse() {
      String resource = createErrorResponse();
      return ResponseEntity.status(code).body(resource);
  }
}

