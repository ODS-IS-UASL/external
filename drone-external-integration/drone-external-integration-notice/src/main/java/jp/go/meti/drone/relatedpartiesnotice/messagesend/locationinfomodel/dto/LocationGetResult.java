package jp.go.meti.drone.relatedpartiesnotice.messagesend.locationinfomodel.dto;



import jp.go.meti.drone.relatedpartiesnotice.messagesend.locationinfomodel.LocationEntity;
import lombok.Data;

/**
 * 都道府県情報取得結果DTO。
 */
@Data
public class LocationGetResult {
    /**
     * HTTPステータスコード
     */
    private int statusCode;
    
    /**
     * 都道府県情報
     */
    private LocationEntity locationEntity;
}
