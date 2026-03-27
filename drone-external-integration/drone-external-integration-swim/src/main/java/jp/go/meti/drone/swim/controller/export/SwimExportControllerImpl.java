package jp.go.meti.drone.swim.controller.export;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.export.ResponseSuccessSwim;
import jp.go.meti.drone.swim.service.export.SwimExportDispatchService;
import lombok.extern.slf4j.Slf4j;

/**
 * SWIMに連携するための情報を取得し、Excelテンプレートに変換／ZIP出力したものを返却するコントローラー
 */
@Slf4j
@RestController
@RequestMapping("${drone-route-base-path}")
public class SwimExportControllerImpl implements SwimExportController {

    /**
     * SWIM向けExcel出力サービス
     */
    private final SwimExportDispatchService swimExportDispatchService;

    /**
     * コンストラクタ
     * 
     * @param swimExportDispatchService SWIM向けExcel出力Serviceクラス
     */
    public SwimExportControllerImpl(SwimExportDispatchService swimExportDispatchService) {
        this.swimExportDispatchService = swimExportDispatchService;
    }

    /**
     * 「maxFallRangeId」 と 「businessNumber」 を使用し、SWIM出力用の最大落下範囲と航路の最大高度を取得
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者番号
     * @param operatorId 事業者ID
     * @param force 強制出力フラグ
     * @return 登録/更新データの情報、もしくはエラー結果
     */
    @GetMapping("/swimExport/{maxFallRangeId}/{businessNumber}")
    public ResponseEntity<?> getSwimExport(@PathVariable String maxFallRangeId, @PathVariable String businessNumber,
        @RequestParam(required = true) String operatorId, @RequestParam(defaultValue = "false") boolean force) {
        log.debug(
            "SwimExportControllerImpl::getSwimExport() called with maxFallRangeId = " + maxFallRangeId
                + " businessNumber = " + businessNumber);
        try {
            ResponseSuccessSwim response = swimExportDispatchService.createNewUpdateExcel(
                maxFallRangeId,
                businessNumber,
                operatorId,
                force);

            return response.successResponse();
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
            log.error(e.getMessage(), e);
            CommonResponseInternalServerError serverError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "予期しないシステムエラーが発生しました。");
            return serverError.errorResponse();
        }
    }

    /**
     * 「maxFallRangeId」 と 「businessNumber」 を使用し、取消Excelを取得
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者番号
     * @param force 強制出力フラグ
     * @return 登録/更新データの情報、もしくはエラー結果
     */
    @DeleteMapping("/swimExport/{maxFallRangeId}/{businessNumber}")
    public ResponseEntity<?> cancelSwimExport(@PathVariable String maxFallRangeId, @PathVariable String businessNumber,
        @RequestParam(defaultValue = "false") boolean force) {
        log.debug(
            "SwimExportControllerImpl::getSwimExport() called with maxFallRangeId = " + maxFallRangeId
                + " businessNumber = " + businessNumber);
        try {
            ResponseSuccessSwim response = swimExportDispatchService.createCancellationExcel(
                maxFallRangeId,
                businessNumber,
                force);

            return response.successResponse();
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
            log.error(e.getMessage(), e);
            CommonResponseInternalServerError serverError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "予期しないシステムエラーが発生しました。");
            return serverError.errorResponse();
        }
    }
}
