package jp.go.meti.drone.swim.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.go.meti.drone.swim.repository.entity.SelectEntity;

/**
 * テーブル内での登録/検索時の条件を記載したインターフェース
 */
@Mapper
public interface SelectMapper {

    /**
     * A：フィードバック済みの全レコードを取得 NOTE 子テーブルは別select前提のため history, swim_feedback は "INNER JOIN" のみ
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @return 更新・新規Excel判定用（フィードバック済みの全レコード）
     */
    public List<SelectEntity> selectAllHistory(@Param("businessNumber") String businessNumber,
        @Param("maxFallRangeId") String maxFallRangeId);

    /**
     * B：最大履歴レコードのみを取得
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @return 1レコード：フィードバック判定時-履歴番号特定用
     */
    public SelectEntity selectMax(@Param("businessNumber") String businessNumber,
        @Param("maxFallRangeId") String maxFallRangeId);

    /**
     * C：フィードバック済みの指定した履歴番号レコードを取得
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @param historyIndex 履歴番号
     * @return 1レコード：フィードバック判定時update用
     */
    public SelectEntity selectFeedback(@Param("businessNumber") String businessNumber,
        @Param("maxFallRangeId") String maxFallRangeId, @Param("historyIndex") int historyIndex);
}
