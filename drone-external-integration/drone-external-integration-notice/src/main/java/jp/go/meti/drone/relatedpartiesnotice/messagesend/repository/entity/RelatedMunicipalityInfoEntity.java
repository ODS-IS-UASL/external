package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル：関係者市区町村 RELATED_MUNICIPALITY_INFO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedMunicipalityInfoEntity {
 // 事業者ID (主キー)
    private String operatorId;

    // 飛行エリアの市区町村
    private String flightMunicipality;

    // 作成者ID
    private Integer creationId;

    // 作成日時
    private LocalDateTime creationDatetime;

    // 更新者ID
    private Integer updateId;

    // 更新日時
    private LocalDateTime updateDatetime;
}
