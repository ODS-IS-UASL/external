package jp.go.meti.drone.swim.model.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SWIM発行情報フィードバックレスポンス
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSwimFeedback {

    /**
     * 正常時のcode（200）
     */
    private Integer code;

    /**
     * 補足情報
     */
    private String message;

}
