package jp.go.meti.drone.dips.service.flightplan;

import jp.go.meti.drone.dips.model.flightplan.DroneRouteFlightPlanInfoRequest;
import jp.go.meti.drone.dips.model.flightplan.DroneRouteFlightPlanInfoResponse;

/**
 * DIPS飛行プラン取得サービスインターフェース
 * 
 * @version 1.0 2025/9/16
 */
@SuppressWarnings("javadoc")
public interface FlightPlanService {
    
    /**
     * DIPS飛行プラン取得する
     * 
     * @param droneRouteFlightPlanInfoRequest
     * @return
     * @throws Exception
     */
    public DroneRouteFlightPlanInfoResponse flightPlanInfoReceiver(DroneRouteFlightPlanInfoRequest droneRouteFlightPlanInfoRequest) throws Exception;
}
