package jp.go.meti.drone.relatedpartiesnotice.reservenotification.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路予約関係者
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveNotificationEntity {

    // 航路予約ID
    private String airwayReserveId;
    
    // 親予約ID
    private String requestId;
    
    // 事業者ID
    private String operatorId;

    // 事業者名
    private String operatorName;
    
    // 関係者のカテゴリー
    private String relatedCategory;

    // 周知先メールアドレス
    private String notificationEmail;

    // 電話番号
    private String notificationPhone;

    // 備考
    private String notificationRemark;

    // 作成者ID
    private Integer creationId;

    // 作成日時
    private LocalDateTime creationDatetime;

    // 更新者ID
    private Integer updateId;

    // 更新日時
    private LocalDateTime updateDatetime;
    
}
