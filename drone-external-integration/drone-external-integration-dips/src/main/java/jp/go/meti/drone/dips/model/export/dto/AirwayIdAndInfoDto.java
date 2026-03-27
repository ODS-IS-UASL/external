/*
* 開発システム： ドローン航路基盤システム
* ファイル名： AirwayIdAndInfoDto.java
* 著作権： Copyright (C) 202X-20XX,  経済産業省
* 会社名： NTT DATA Corporation
* 更新日： $Date$
*
*/
package jp.go.meti.drone.dips.model.export.dto;

import jp.go.meti.drone.dips.apimodel.export.UaslTopEntity;
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
    private UaslTopEntity uaslTopEntity;

}
