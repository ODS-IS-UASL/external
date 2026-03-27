package jp.go.meti.drone.relatedpartiesnotice.reservenotification.controller;

import java.util.List;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.com.BadRequestException;
import jp.go.meti.drone.relatedpartiesnotice.reservenotification.model.DroneRouteResponseNotificationInfo;
import jp.go.meti.drone.relatedpartiesnotice.reservenotification.service.ReserveNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路予約関係者情報取得コントローラー。
 * 
 * @version 1.0 2025/12/05
 */
@Slf4j
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
public class ReserveNotificationControllerImpl  implements ReserveNotificationController {

	@Autowired
	private ReserveNotificationService reserveNotificationService;

    /**
     * 航路予約関係者情報取得する
     * 
     * @param airwayReserveId 航路予約ID
     * @return　航路予約関係者情報リスト
     */
	@GetMapping("/reserveNotification")
	public ResponseEntity<?> getReserveNotificationInfo(String airwayReserveId) {

        log.info("航路予約関係者情報取得コントローラー開始します。");
        log.info("リクエストパラメータ：" + airwayReserveId);
        try {
            List<DroneRouteResponseNotificationInfo> response = reserveNotificationService.getReserveNotificationInfo(airwayReserveId);
            log.info("航路予約関係者情報取得コントローラー終了します。");
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            // 400 Bad Request
            CommonResponseBadRequestError badRequestError = new CommonResponseBadRequestError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return badRequestError.errorResponse();
        } catch (NotFoundException e) {
            // 404 Not Found
            CommonResponseNotFoundError notFoundError = new CommonResponseNotFoundError(
                HttpStatus.NOT_FOUND.value(), e.getMessage());
            return notFoundError.errorResponse();
        } catch (Exception e) {
            // 500 Internal Server Error サーバーエラー
            CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return internalServerError.errorResponse();
        }
	}
}
