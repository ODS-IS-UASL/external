package jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.dto;


import jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.UaslTopEntity;
import lombok.Data;

/**
 * 航路画定機能からの航路情報取得結果DTO。
 * 
 * @version 1.0 2025/12/11
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
