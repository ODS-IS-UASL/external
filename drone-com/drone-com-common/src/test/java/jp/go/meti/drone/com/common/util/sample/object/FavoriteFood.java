package jp.go.meti.drone.com.common.util.sample.object;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * オブジェクトマッピングユーティリティ実装クラスのテストクラスで使用するサンプルのオブジェクト
 */
@Data
@Builder
public class FavoriteFood implements Serializable {
    // シリアルバージョンUID
    private static final long serialVersionUID = 1L;

    /*
     * 日本料理名
     * JSONキー項目名を指定する例
     * e.g. 業務要件等により、名称を変更したいケースを想定
     */
    @JsonProperty("japaneseFood")
    private String japanese;

    /*
     * イタリア料理名
     * JSONキー項目名を指定する例
     * e.g. 業務要件等により、名称を変更したいケースを想定
     */
    @JsonProperty("italianFood")
    private String italian;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

    /*
     * 論理削除フラグ
     * JSONキー項目名を指定する例
     * e.g. Jacksonデフォルト動作起因で名称を指定したいケースを想定
     *    Jacksonデフォルト動作では、真偽値型のフィールドisXxxxYyyyは、
     *    キー項目名xxxxYyyyとしてシリアライズされるため、それを防ぎたいケースを想定
     */
    @JsonProperty("isDeleted")
    private boolean isDeleted;

}
