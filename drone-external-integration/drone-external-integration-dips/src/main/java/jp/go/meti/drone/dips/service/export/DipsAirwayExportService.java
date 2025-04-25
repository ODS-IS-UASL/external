package jp.go.meti.drone.dips.service.export;

import org.springframework.http.ResponseEntity;
import jp.go.meti.drone.dips.model.export.RequestAirwayIdList;

/**
 * DIPS登録用航路情報エクスポートサービスインターフェース
 * 
 * @version 1.0 2024/11/01
 */
public interface DipsAirwayExportService {

    /**
     * DIPS登録用航路情報エクスポート
     * 
     * @param requestAirwayIdList 航路IDリスト
     * @return ZIPストリーム
     */
    public ResponseEntity<?> dipsAirwayExport(RequestAirwayIdList requestAirwayIdList);
}
