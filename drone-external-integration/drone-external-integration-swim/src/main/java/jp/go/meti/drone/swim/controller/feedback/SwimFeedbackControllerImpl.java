package jp.go.meti.drone.swim.controller.feedback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.feedback.RequestSwimFeedback;
import jp.go.meti.drone.swim.model.feedback.ResponseSwimFeedback;
import jp.go.meti.drone.swim.service.feedback.SwimFeedbackService;
import lombok.extern.slf4j.Slf4j;

/**
 * フィードバック情報のコントローラー
 */
@Slf4j
@RestController
@RequestMapping("${drone-route-base-path}")
public class SwimFeedbackControllerImpl {

    /**
     * フィードバック情報処理のサービス
     */
    private final SwimFeedbackService swimFeedbackService;

    /**
     * コンストラクタ
     * 
     * @param swimFeedbackService フィードバック情報処理のサービス
     */
    public SwimFeedbackControllerImpl(SwimFeedbackService swimFeedbackService) {
        this.swimFeedbackService = swimFeedbackService;
    }

    /**
     * 「maxFallRangeId」 と 「businessNumber」 と 「historyNum」を使用し、SWIMからフィードバック情報を登録
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者番号
     * @param historyNum 履歴番号
     * @param force 強制更新フラグ
     * @param request フィードバック情報
     * @return HTTPレスポンス
     */
    @PutMapping("/swimExport/{maxFallRangeId}/{businessNumber}/{historyNum}")
    public ResponseEntity<?> getSwimExport(@PathVariable String maxFallRangeId, @PathVariable String businessNumber,
        @PathVariable int historyNum, @RequestParam(defaultValue = "false") Boolean force,
        @RequestBody RequestSwimFeedback request) {
        log.debug(
            "SwimExportControllerImpl::getSwimExport() called with maxFallRangeId = " + maxFallRangeId
                + " businessNumber = " + businessNumber);
        try {
            ResponseSwimFeedback response = swimFeedbackService.feedback(
                businessNumber,
                maxFallRangeId,
                historyNum,
                request,
                force != null && force);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (CommonResponseInternalServerError e) {
            log.debug(e.getMessage());
            return e.errorResponse();
        } catch (CommonResponseNotFoundError e) {
            log.debug(e.getMessage());
            return e.errorResponse();
        } catch (CommonResponseBadRequestError e) {
            log.debug(e.getMessage());
            return e.errorResponse();
        } catch (Exception e) {
            log.error("予期しないシステムエラーが発生しました。", e);
            CommonResponseInternalServerError serverError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "予期しないシステムエラーが発生しました。");
            return serverError.errorResponse();
        }
    }

}
