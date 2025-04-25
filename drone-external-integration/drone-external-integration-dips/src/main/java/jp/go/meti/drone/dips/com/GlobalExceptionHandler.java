package jp.go.meti.drone.dips.com;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import lombok.extern.slf4j.Slf4j;

/**
 * フレームワークで単項目チェック処理クラス
 * 
 * @version 1.0 2024/11/01
 */
@Slf4j
@ControllerAdvice
@Component("globalExceptionHandlerForDips")
public class GlobalExceptionHandler {

	/** メッセージのパラメータ値(航路ID)設定 */
	private static String airwayId = "航路ID";

	/** メッセージのパラメータ値(航路IDレングス)設定 */
	private static String airwayIdLength = "300";
	
    /** メッセージのパラメータ値(事業者IDリスト)設定 */
    private static String operatorIdList = "事業者IDリスト";

    /** メッセージのパラメータ値(事業者ID)設定 */
    private static String operatorId = "事業者ID";

    /** メッセージのパラメータ値(事業者IDレングス)設定 */
    private static int operatorIdLength = 36;

    /** メッセージのパラメータ値(更新種別)設定 */
    private static String updateType = "更新種別";

	/**
	 * パラメーターに誤りがある場合に返却する。
	 * 
	 * @param ex
	 * @return エラーレスポンス
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {

		StringBuilder errMsg = new StringBuilder();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
		    String errorMessage = error.getDefaultMessage();
		    if (error.getObjectName().equals("requestAirwayIdList")) {
		        if (error.getCode().equals("Size")) {
		            Object[] args = {airwayId, airwayIdLength};
		            errorMessage = MessageUtils.getMessage(errorMessage,args);
		        }
		        else {
		            Object[] args = {airwayId};
		            errorMessage = MessageUtils.getMessage(errorMessage,args);
		        }
		        errMsg.append("airwayId=").append(error.getRejectedValue()).append(" : ").append(errorMessage).append("\n ");
		    }
		    else if (error.getObjectName().equals("requestRouteTenantInfo") || error.getObjectName().equals("requestUpdateRouteTenantInfo")) {
		        if (error.getField().equals("airwayId")) {
		            Object[] args = {airwayId};
		            errorMessage = MessageUtils.getMessage(errorMessage,args);
		            errMsg.append(error.getRejectedValue()).append(" : ").append(errorMessage).append("\n ");
		        } else if (error.getField().indexOf("relatedPartiesIdList") >= 0) {
		            if (error.getCode().equals("NotNull")) {
		                Object[] args = {operatorIdList};
		                errorMessage = MessageUtils.getMessage(errorMessage,args);
		            } else if (error.getCode().equals("NotBlank")) {
		                Object[] args = {operatorId};
		                errorMessage = MessageUtils.getMessage(errorMessage,args);
		            } else if (error.getCode().equals("Size")) {
		                Object[] args = {operatorId, operatorIdLength};
		                errorMessage = MessageUtils.getMessage(errorMessage,args);
		            }
		            errMsg.append(error.getRejectedValue()).append(" : ").append(errorMessage).append("\n");
		        } else if (error.getField().equals("updateType")) {
		            Object[] args = {updateType};
		            errorMessage = MessageUtils.getMessage(errorMessage,args);
		            errMsg.append(error.getRejectedValue()).append(" : ").append(errorMessage).append("\n");
		        }
		    }
		});

		CommonResponseBadRequestError badRequestError = new CommonResponseBadRequestError(
				HttpStatus.BAD_REQUEST.value(), errMsg.toString());
		return badRequestError.errorResponse();
	}

}
