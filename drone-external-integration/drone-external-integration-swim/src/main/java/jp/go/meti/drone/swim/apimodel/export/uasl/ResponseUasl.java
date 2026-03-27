package jp.go.meti.drone.swim.apimodel.export.uasl;

import java.util.List;

import lombok.Data;

/**
 * 航路情報一覧のレスポンスのDTO
 */
@Data
public class ResponseUasl {

    /** 航路情報一覧情報のリスト */
    private List<ResponseUaslInfo> uasl;
}
