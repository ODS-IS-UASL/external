package jp.go.meti.drone.dips.controller.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.go.meti.drone.dips.model.export.RequestAirwayIdList;
import jp.go.meti.drone.dips.service.export.DipsAirwayExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DIPS登録用航路情報エクスポートコントローラー
 * 
 * @version 1.0 2024/11/01
 */
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
@Slf4j
public class DipsAirwayExportControllerImpl implements DipsAirwayExportController {

    @Autowired
    @Qualifier("AirwayExportService")
	private DipsAirwayExportService flightRouteExportService;

	/**
	 * DIPS登録用航路情報エクスポートする
	 * 
	 * @param requestAirwayIdList 要求
	 * @return 応答
	 */
	@PostMapping("/dipsAirwayExport")
	public ResponseEntity<?> dipsAirwayExport(
			@RequestBody @Validated RequestAirwayIdList requestAirwayIdList) {
		log.info("DIPS登録用航路情報エクスポートコントローラー開始します。");
		ResponseEntity<?> response = flightRouteExportService.dipsAirwayExport(requestAirwayIdList);
		log.info("DIPS登録用航路情報エクスポートコントローラー終了します。");
		return response;
	}
}
