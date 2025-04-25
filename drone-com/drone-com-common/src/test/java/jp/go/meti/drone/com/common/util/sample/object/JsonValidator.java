package jp.go.meti.drone.com.common.util.sample.object;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * オブジェクトマッピングユーティリティ実装クラスのテストクラスで
 * JSONの判定を行うためのサンプルクラス
 */
public class JsonValidator {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
