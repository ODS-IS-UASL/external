package jp.go.meti.drone.com.common.util.sample.object;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * オブジェクトマッピングユーティリティ実装クラスのテストクラスで使用するサンプルのオブジェクト
 */
@Data
@Builder
public class Information implements Serializable {
    // シリアルバージョンUID
    private static final long serialVersionUID = 1L;

    // 年齢
    private int age;

    // 住所
    private String address;

    // 職業
    private String work;

    /*
     * 秘密
     * シリアライズ(Java->JSON)対象から除外する例
     * e.g. DB管理用のフィールド等、シリアライズ不要なフィールドを除外するケースを想定
     *    DB管理用のフィールドとは、createdAt、updatedAt、isDeleted等のこと
     */
    @JsonIgnore
    private String secret;

    /*
     * 作成日時
     * フィールド値がnullの場合のみ、シリアライズ(Java->JSON)対象から除外する例
     * e.g. 業務要件等により、null以外のフィールドのみシリアライズしたいケースを想定
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdAt;

    /*
     * 更新日時
     * フィールド値がnullの場合のみ、シリアライズ(Java->JSON)対象から除外する例
     * e.g. 業務要件等により、null以外のフィールドのみシリアライズしたいケースを想定
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    // 論理削除フラグ
    private boolean isDeleted;

}
