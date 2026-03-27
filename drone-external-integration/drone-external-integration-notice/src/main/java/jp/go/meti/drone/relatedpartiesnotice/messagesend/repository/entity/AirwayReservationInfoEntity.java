package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路予約情報取得
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirwayReservationInfoEntity {

    // 航路予約関係者
    // 航路予約ID
    private String airwayReserveId;
    
    // 事業者ID
    private String relatedOperatorId;
    
    // 事業者名
    private String operatorName;
    
    // 周知先メールアドレス
    private String notificationEmail;

    // 航路予約情報
    // 運航事業者(予約者)ID
    private String reservationOperatorId;
    
    // 飛行目的
    private String flightPurpose;
    
    // 処理区分
    private String status;
    
    // 予約状態更新日時
    private String updatedAt;

    // 航路情報
    // 航路名称
    private String airwayName;
}
