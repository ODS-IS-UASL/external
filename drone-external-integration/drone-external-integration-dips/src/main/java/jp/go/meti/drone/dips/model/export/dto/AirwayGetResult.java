package jp.go.meti.drone.dips.model.export.dto;

import jp.go.meti.drone.dips.apimodel.export.AirwayEntity;
import lombok.Data;

/**
 * 航路画定機能からの航路情報取得結果DTO。
 * 
 * @version 1.0 2024/11/08
 */
@Data
public class AirwayGetResult {

    /**
     * HTTPステータスコード
     */
    private int statusCode;
    
    /**
     * 航路情報
     */
    private AirwayEntity airwayEntity;
}
