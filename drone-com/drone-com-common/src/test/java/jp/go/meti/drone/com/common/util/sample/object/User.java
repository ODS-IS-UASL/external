package jp.go.meti.drone.com.common.util.sample.object;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * オブジェクトマッピングユーティリティ実装クラスのテストクラスで使用するサンプルのオブジェクト
 */
@Data
@Builder
public class User implements Serializable {
    // シリアルバージョンUID
    private static final long serialVersionUID = 1L;

    // ユーザ名
    private String name;

    // ユーザ情報
    private Information information;

    // 好きな料理リスト
    private List<FavoriteFood> favoriteFoods;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

    // 論理削除フラグ
    private boolean isDeleted;

}
