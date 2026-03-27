package jp.go.meti.drone.swim.model.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SWIM発行情報フィードバックリクエスト
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestSwimFeedback {

    /**
     * メンバ変数の設定
     */
    private Feature feature;

    private Object10 object10;

    private Object13 object13;

    private Object24 object24;

    private Object29 object29;

    private Object38 object38;

    /**
     * Featureシート
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Feature {
        private String featureId;

        private String timesliceId;

        private String beginPosition;

        private String endPosition;
    }

    /**
     * Object10シート
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Object10 {
        private String objectId;
    }

    /**
     * Object13シート
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Object13 {
        private String parObjectId;

        private String objectId;
    }

    /**
     * Object24シート
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Object24 {
        private String objectId;
    }

    /**
     * Object29シート
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Object29 {
        private String parObjectId;

        private String objectId;
    }

    /**
     * Object38シート
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Object38 {
        private String parObjectId;

        private String objectId;
    }
}
