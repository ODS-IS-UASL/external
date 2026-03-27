package jp.go.meti.drone.swim.apimodel.export.uasl;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 航路情報のレスポンスのDTO
 */
@Data
@AllArgsConstructor
public class ResponseUaslInfo {

    /** 航路運営者ID */
    private String uaslAdministratorId;

    /** 事業者番号 */
    private String businessNumber;

    /** 航路運営者IDに紐づく、航路の情報 */
    private Uasl uasl;
}
