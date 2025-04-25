package jp.go.meti.drone.dips.controller.area;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.apimodel.dips.DroneRouteFlightProhibitedAreaResponse;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.flightprohibited.FlightProhibitedAreaInfoRequest;
import jp.go.meti.drone.dips.service.area.FlightProhibitedAreaReceiverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 飛行禁止エリア取得コントローラー。
 * <p>
 * このクラスはdips飛行禁止エリア情報取得連携機能です。<br>
 * </p>
 * 
 * @version 1.0 2024/10/30
 */
@Slf4j
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
public class FlightProhibitedAreaReceiverController {

    @Autowired
    @Qualifier("AreaReceiverService")
    private FlightProhibitedAreaReceiverService flightProhibitedAreaReceiverService;

    /**
     * 飛行禁止エリア取得
     * <p>
     * 飛行禁止エリア取得 リクエスト項目を受け取り、飛行禁止エリア取得情報をDIPSから取得して、取得結果を返却する。<br>
     * サービス層で DIPSへアクセスし、情報を取得する。<br>
     * </p>
     * 
     * @param request 要求
     * @return 応答
     */
    @PostMapping("/flightProhibitedAreaReceiver")
    public ResponseEntity<?> flightProhibitedAreaReceiver(
        @Valid @RequestBody FlightProhibitedAreaInfoRequest request) {

    	log.info("FlightProhibitedAreaInfoRequest:" + HtmlUtils.htmlEscape(request.toString()));

        DroneRouteFlightProhibitedAreaResponse responseResult = new DroneRouteFlightProhibitedAreaResponse();
        try {
            responseResult = flightProhibitedAreaReceiverService.flightProhibitedAreaReceiverInfo(request);

            log.info("ResponseFlightProhibitedArea:" + HtmlUtils.htmlEscape(responseResult.toString()));
            return new ResponseEntity<>(responseResult, HttpStatus.OK);
        } catch (CommonResponseBadRequestError e) {
            // 業務処理で失敗した
            log.info("CommonResponseBadRequestError:" + e.getErrorMessage());
            return e.errorResponse();
        } catch (CommonResponseInternalServerError e) {
            // 業務処理で失敗した
            log.info("CommonResponseInternalServerError:" + e.getErrorMessage());
            return e.errorResponse();
        }  catch (Exception e) {
            // 予期せぬエラー
            String messageTmp = MessageUtils.getMessage("DRC01E001");
            CommonResponseInternalServerError err = new CommonResponseInternalServerError(500, messageTmp);
            log.error(messageTmp, e);
            return err.errorResponse();
        }
    }
}
