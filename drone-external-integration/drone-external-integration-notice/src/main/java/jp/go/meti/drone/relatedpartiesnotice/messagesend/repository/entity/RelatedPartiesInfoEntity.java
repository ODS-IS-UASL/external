package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル：関係者情報 RELATED_PARTIES_INFO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedPartiesInfoEntity {
    // 事業者ID (主キー)
    private String operatorId;

    // 関係者のカテゴリー
    private String relatedCategory;

    // 飛行エリアの都道府県
    private String flightPrefecture;

    // 抽出条件(1：全域,2：市区町村指定,3：上記以外)
    private String extractionConditions;

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
