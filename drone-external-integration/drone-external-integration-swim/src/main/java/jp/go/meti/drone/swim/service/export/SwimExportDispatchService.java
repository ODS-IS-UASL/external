package jp.go.meti.drone.swim.service.export;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.export.ResponseSuccessSwim;
import jp.go.meti.drone.swim.service.export.SwimProcessorType.ProcessorNames;
import lombok.extern.slf4j.Slf4j;

/**
 * SWIM向けExcel出力サービス
 */
@Slf4j
@Service
public class SwimExportDispatchService {

    /** 新規/更新判定クラス */
    private final SwimDecisionService decisionService;

    /** SwimExportProcessorマップ */
    private final Map<String, SwimExportProcessor> processorMap;

    /**
     * コンストラクタ
     * 
     * @param decisionService 新規/更新判定クラス
     * @param processorMap processorのMap
     */
    public SwimExportDispatchService(SwimDecisionService decisionService,
        Map<String, SwimExportProcessor> processorMap) {
        this.decisionService = decisionService;
        this.processorMap = processorMap;
    }

    /**
     * 更新・新規Excel出力
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @param operatorId 事業者ID(uuid)
     * @param force 強制
     * @return Excel出力した結果
     * @throws CommonResponseNotFoundError 指定したID不正
     * @throws CommonResponseBadRequestError フィードバック前の再出力時（forceフラグ指定により返却可能
     * @throws CommonResponseInternalServerError DB不整合など
     */
    @Transactional
    public ResponseSuccessSwim createNewUpdateExcel(String maxFallRangeId, String businessNumber, String operatorId,
        boolean force) throws CommonResponseNotFoundError, CommonResponseBadRequestError,
        CommonResponseInternalServerError {

        String type = this.decisionService.decisionNewUpdate(businessNumber, maxFallRangeId, force);

        log.debug(type);
        SwimExportProcessor ins = this.processorMap.get(type);

        return ins.getMaximumFallRange(maxFallRangeId, businessNumber, operatorId, force);
    }

    /**
     * 取消Excel出力
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @param force 未使用
     * @throws CommonResponseNotFoundError 指定したID不正
     * @throws CommonResponseInternalServerError DB不整合など
     * @return Excel出力した結果
     */
    @Transactional
    public ResponseSuccessSwim createCancellationExcel(String maxFallRangeId, String businessNumber, boolean force)
        throws CommonResponseNotFoundError, CommonResponseInternalServerError {

        SwimExportProcessor ins = this.processorMap.get(ProcessorNames.CANCELLATION_EXCEL);
        return ins.getMaximumFallRangeCancel(maxFallRangeId, businessNumber, force);
    }

}
