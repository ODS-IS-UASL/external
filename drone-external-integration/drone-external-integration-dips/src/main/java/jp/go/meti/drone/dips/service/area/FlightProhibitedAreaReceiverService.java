package jp.go.meti.drone.dips.service.area;

import jp.go.meti.drone.dips.apimodel.dips.DroneRouteFlightProhibitedAreaResponse;
import jp.go.meti.drone.dips.model.flightprohibited.FlightProhibitedAreaInfoRequest;

/**
 * 飛行禁止エリア取得のサービスインターフェース
 *
 * @version 1.0 2024/10/30
 */
public interface FlightProhibitedAreaReceiverService {

    /**
     * 飛行禁止エリア取得
     * 
     * @param searchModelApi リクエスト
     * @return 返答
     */
    public DroneRouteFlightProhibitedAreaResponse flightProhibitedAreaReceiverInfo(FlightProhibitedAreaInfoRequest searchModelApi);
}
