package jp.go.meti.drone.dips.controller.flightplan;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import jp.go.meti.drone.api.auth.exception.DipsAccessTokenException;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseDipsAccessTokenError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.flightplan.DroneRouteFlightPlanInfoRequest;
import jp.go.meti.drone.dips.model.flightplan.DroneRouteFlightPlanInfoResponse;
import jp.go.meti.drone.dips.service.flightplan.FlightPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DIPS飛行プラン取得コントローラー
 * 
 * @version 1.0 2025/09/16
 */
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
@Slf4j
public class FlightPlanControllerImpl implements FlightPlanController{

    @Qualifier("FlightPlanService")
    private final FlightPlanService FlightPlanService;

    /**
     * DIPS飛行プラン情報取得する
     * 
     * @param droneRouteFlightPlanInfoRequest 要求
     * @return 応答
     */
    @PostMapping("/flightPlanInfoReceiver")
    public ResponseEntity<?> flightPlanInfoReceiver(
        @RequestBody @Validated DroneRouteFlightPlanInfoRequest droneRouteFlightPlanInfoRequest) {
        log.info("DIPS飛行プラン取得コントローラー開始します。");
        log.info("DroneRouteFlightPlanInfoRequestチェック前:" + HtmlUtils.htmlEscape(droneRouteFlightPlanInfoRequest.toString()));
        try {
            DroneRouteFlightPlanInfoResponse response = FlightPlanService.flightPlanInfoReceiver(
                droneRouteFlightPlanInfoRequest);
            log.info("DIPS飛行プラン取得コントローラー終了します。");
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            // 400 Bad Request
            CommonResponseBadRequestError badRequestError = new CommonResponseBadRequestError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return badRequestError.errorResponse();
        } catch (DipsAccessTokenException e) {
            // 401 処理失敗(DIPSアクセストークン例外) 
            CommonResponseDipsAccessTokenError dipsAccessTokenError = new CommonResponseDipsAccessTokenError(
                HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return dipsAccessTokenError.errorResponse();
        } catch (Exception e) {
            // 500 Internal Server Error サーバーエラー
            CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return internalServerError.errorResponse();
        }
    }
}
