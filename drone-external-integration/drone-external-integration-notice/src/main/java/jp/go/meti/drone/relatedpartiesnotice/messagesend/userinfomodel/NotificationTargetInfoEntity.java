package jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTargetInfoEntity {
    
    // 航路予約ID
    private String airwayReserveId;
    
    // 親予約ID
    private String requestId;

    // 事業者名
    private String operatorName;
    
    // 事業者ID
    private String operatorId;

    // 関係者のカテゴリー
    private String relatedCategory;

    // 飛行エリアの都道府県
    private String flightPrefecture;

    // 飛行エリアの市区町村
    private String flightMunicipality;

    // 周知先メールアドレス
    private String notificationEmail;

    // 電話番号
    private String notificationPhone;

    // 備考
    private String notificationRemark;
}
