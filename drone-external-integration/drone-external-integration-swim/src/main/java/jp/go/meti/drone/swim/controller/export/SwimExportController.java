package jp.go.meti.drone.swim.controller.export;

import org.springframework.http.ResponseEntity;

/**
 * SWIMに連携するための情報を取得し、Excelテンプレートに変換／ZIP出力したものを返却するコントローラーインターフェース
 */
public interface SwimExportController {

    /**
     * 「maxFallRangeId」 と 「businessNumber」 を使用し、SWIM出力用の最大落下範囲と航路の最大高度を取得
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者番号
     * @param operatorId 事業者ID
     * @param force 強制出力フラグ
     * @return 登録/更新データの情報、もしくはエラー結果
     */
    public ResponseEntity<?> getSwimExport(String maxFallRangeId, String businessNumber, String operatorId,
        boolean force);
}
