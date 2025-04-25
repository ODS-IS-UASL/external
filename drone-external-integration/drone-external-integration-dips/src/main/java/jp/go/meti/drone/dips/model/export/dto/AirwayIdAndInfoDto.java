package jp.go.meti.drone.dips.model.export.dto;

import jp.go.meti.drone.dips.apimodel.export.AirwayEntity;
import lombok.Data;

/**
 * 航路IDと取得した航路情報を保持するためのオブジェクト
 */
@Data
public class AirwayIdAndInfoDto {
    
    /**
     * 航路ID
     */
    private String airwayId;
    
    /**
     * 航路情報
     */
    private AirwayEntity airwayEntity;

}
