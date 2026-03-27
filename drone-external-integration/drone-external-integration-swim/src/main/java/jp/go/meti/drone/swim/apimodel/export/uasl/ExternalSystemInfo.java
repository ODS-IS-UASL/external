package jp.go.meti.drone.swim.apimodel.export.uasl;

import lombok.Data;

/**
 * 外部システム情報のDTO
 */
@Data
public class ExternalSystemInfo {

    /** 外部システムのID */
    private String systemId;

    /** 外部システムの接続先の航路ID */
    private String uaslId;

    /** 外部システムの接続先の航路点ID */
    private String uaslPointId;
}
