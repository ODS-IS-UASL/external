package jp.go.meti.drone.swim.apimodel.export.uasl;

import java.util.List;

import lombok.Data;

/**
 * 航路運営者IDに紐づく、航路情報のレスポンスDTO
 */
@Data
public class Uasl {

    /** 航路ID */
    private String uaslId;

    /** 航路名 */
    private String uaslName;

    /** 飛行目的 */
    private String flightPurpose;

    /** 登録日時 */
    private String createdAt;

    /** 更新日時 */
    private String updatedAt;

    /** 航路を利用可能なドローンの機体情報IDのリスト */
    private List<Integer> droneList;

    /** 航路点情報リスト */
    private List<UaslPoint> uaslPoints;

    /** 航路区画情報リスト */
    private List<UaslSection> uaslSections;

}
