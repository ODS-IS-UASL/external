package jp.go.meti.drone.relatedpartiesnotice.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.relatedpartiesnotice.tenant.model.DroneRouteResponseOperatorList;
import jp.go.meti.drone.relatedpartiesnotice.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 事業者情報取得 コントローラー。
 * <p>
 * DBより事業者関連の周知先、航路ID情報を取得する
 * </p>
 * 
 * @version 1.0 2024/11/14
 */
@Slf4j
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
public class TenantController {

	/** 事業者情報取得サービス */
	@Autowired
	private TenantService tenantService;

	/**
	 * 事業者情報を取得
	 * <p>
	 * 事業者IDが設定した場合、事業者IDより事業者情報を取得<br>
	 * 航路IDが設定した場合、航路IDより事業者情報を取得<br>
	 * 上記以外の場合、一覧情報を取得
	 * </p>
	 * 
	 * @param operatorId 事業者ID
	 * @param airwayId 航路ID
	 * @return 返答
	 */
	@GetMapping("/operator")
	public ResponseEntity<?> operatorInfo(String operatorId, String airwayId) {

		log.info("TenantController-事業者ID:" + operatorId);
		log.info("TenantController-航路ID:" + airwayId);
		DroneRouteResponseOperatorList responseTenantList = new DroneRouteResponseOperatorList();
		try {

			// 事業者ID、航路IDが空の場合、事業者一覧取得
			if (ObjectUtils.isEmpty(operatorId) && ObjectUtils.isEmpty(airwayId)) {
				responseTenantList = tenantService.getOperatorByOperatorId(operatorId);
				return new ResponseEntity<>(responseTenantList, HttpStatus.OK);
			}
			// 事業者ID、航路IDが空ではない場合
			else if (!ObjectUtils.isEmpty(operatorId) && !ObjectUtils.isEmpty(airwayId)) {
				// 検索条件の業務エラー
				log.error("クエリパラメータが複数設定されています。");
				CommonResponseBadRequestError err = new CommonResponseBadRequestError(400,
						MessageUtils.getMessage("DR000E005"));
				return err.errorResponse();
			}
			// 事業者IDが指定される場合
			else if (!ObjectUtils.isEmpty(operatorId)) {
				if (operatorId.length() == 36) {
					responseTenantList = tenantService.getOperatorByOperatorId(operatorId);
					return new ResponseEntity<>(responseTenantList, HttpStatus.OK);
				} else {
					// 検索条件の業務エラー
					log.error("事業者IDの文字数が不正です。");
					Object[] args = {"operatorId","36"};
					CommonResponseBadRequestError err = new CommonResponseBadRequestError(400,
							MessageUtils.getMessage("DR002E002",args));
					return err.errorResponse();
				}
			}
			// 航路IDが指定される場合
			else{
				responseTenantList = tenantService.getOperatorByAirwayId(airwayId);
				return new ResponseEntity<>(responseTenantList, HttpStatus.OK);
			}
		} catch(CommonResponseInternalServerError e) {
		    return e.errorResponse();
		}catch (Exception e) {
			// 予期せぬエラー
			CommonResponseInternalServerError err = new CommonResponseInternalServerError(500,
					MessageUtils.getMessage("DRC01E001"));
			log.error("Unexpected error" + e);
			return err.errorResponse();
		}
	}
}