package jp.go.meti.drone.swim.repository.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * SWIM関連テーブルselect結果（複数テーブルJOIN用）
 */
@Data
public class SelectEntity {

    /** 事業者番号(航路画定) */
    private String businessNumber;

    /** 最大落下範囲ID(航路画定) */
    private String maxFallRangeId;

    /** 履歴番号 **/
    private int historyIndex;

    /** OperatorID(PIP) */
    private String operatorId;

    /** 事業者ID3桁 */
    private String identifier;

    /** 事業者内ID3桁 */
    private String internalIdentifier;

    /** designator8桁 */
    private String swimId;

    /** 有効 */
    private Boolean enable;

    /** 更新日 */
    private LocalDateTime updateAt;

    /** 作成日 */
    private LocalDateTime createAt;

    /** 履歴レコード: NOTE 使用時に別select前提 **/
    private SwimMaxFallRangeHistoryEntity history;

    /** SWIMフィードバック内容: NOTE 使用時に別select前提 **/
    private SwimFeedbackEntity swimFeedback;

}
