package jp.go.meti.drone.relatedpartiesnotice.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.relatedpartiesnotice.tenant.model.ResponseTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.tenant.service.TenantInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 権限情報取得コントローラー。
 * 
 * @version 1.0 2024/12/12
 */
@Slf4j
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
public class TenantInfoControllerImpl  implements TenantInfoController {

	@Autowired
	private TenantInfoService tenantInfoService;

	/**
	 * 認証情報を取得する
	 * </p>
	 * 指定したoperator_idに紐づく権限情報を取得するAPIです。
	 * </p>
	 * @param operatorId 事業者ID
	 * @return 取得成功 (status code 200) or Not Found (status code 404) or Internal Server Error
	 *         サーバーエラー (status code 500)
	 */
	@GetMapping("/operatorRole")
	public ResponseEntity<?> getTenantInfo(String operatorId) {

		try {
			// operatorIdチェック
			if (operatorId == null || operatorId.isEmpty()) {
				log.error("operatorIdは必須項目です。");
				Object[] args = {"operatorId"};
				CommonResponseBadRequestError err = new CommonResponseBadRequestError(400,
						MessageUtils.getMessage("DRC01E003",args));
				return err.errorResponse();
			}
			ResponseTenantInfo tenantInfoResponse = tenantInfoService.getTenantEntityInfo(operatorId);
			return new ResponseEntity<>(tenantInfoResponse, HttpStatus.OK);	

		} catch (CommonResponseNotFoundError e) {
			log.error("CommonResponseNotFoundError" + e);
			return e.errorResponse();
		} catch (CommonResponseInternalServerError e) {
			log.error("CommonResponseInternalServerError" + e);
			return e.errorResponse();
		} catch (Exception e) {
			// 予期せぬエラー
			CommonResponseInternalServerError err = new CommonResponseInternalServerError(500,
					MessageUtils.getMessage("DRC01E001"));
			log.error("Unexpected error" + e);
			return err.errorResponse();
		}
	}
}
