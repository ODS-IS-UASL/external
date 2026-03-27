/*
* 開発システム： ドローン航路基盤システム
* ファイル名： AirwayGetResult.java
* 著作権： Copyright (C) 202X-20XX,  経済産業省
* 会社名： NTT DATA Corporation
* 更新日： $Date$
*
*/
package jp.go.meti.drone.dips.model.export.dto;

import jp.go.meti.drone.dips.apimodel.export.UaslTopEntity;
import lombok.Data;

/**
 * 航路画定機能(A-2)からの航路情報取得結果DTO。
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
    private UaslTopEntity uaslTopEntity;
}
