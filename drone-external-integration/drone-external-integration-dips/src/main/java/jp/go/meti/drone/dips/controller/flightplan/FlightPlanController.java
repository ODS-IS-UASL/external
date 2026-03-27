package jp.go.meti.drone.dips.controller.flightplan;

import org.springframework.http.ResponseEntity;
import jp.go.meti.drone.dips.model.flightplan.DroneRouteFlightPlanInfoRequest;

/**
 * DIPSDIPS飛行プラン取得インターフェース
 * 
 * @version 1.0 2025/09/16
 */
@SuppressWarnings("javadoc")
public interface FlightPlanController{

    /**
     * DIPS飛行プラン取得する
     * 
     * @param requestBody 要求
     * @return 応答
     */
    public ResponseEntity<?> flightPlanInfoReceiver(DroneRouteFlightPlanInfoRequest droneRouteFlightPlanInfoRequest);

}
