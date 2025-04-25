package jp.go.meti.drone.dips.controller.export;

import org.springframework.http.ResponseEntity;
import jp.go.meti.drone.dips.model.export.RequestAirwayIdList;

/**
 * DIPS登録用航路情報エクスポートインターフェース
 * 
 * @version 1.0 2024/11/01
 */
public interface DipsAirwayExportController {

	/**
	 * DIPS登録用航路情報エクスポートする
	 * 
	 * @param requestBody 要求
	 * @return 応答
	 */
	public ResponseEntity<?> dipsAirwayExport(RequestAirwayIdList requestAirwayIdList);

}
