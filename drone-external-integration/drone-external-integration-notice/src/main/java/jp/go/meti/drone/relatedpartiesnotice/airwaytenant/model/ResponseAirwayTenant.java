package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model;

import org.springframework.http.ResponseEntity;

/**
 * 成功のレスポンスクラス
 */
public class ResponseAirwayTenant {

	/**
	 * 成功のレスポンス
	 * 
	 * @return
	 */
	public static ResponseEntity<String> successResponse() {
		return ResponseEntity.ok(null);
	}

}
