package jp.go.meti.drone.swim.service.export;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.repository.entity.SelectEntity;
import jp.go.meti.drone.swim.repository.mapper.SelectMapper;
import jp.go.meti.drone.swim.service.export.SwimProcessorType.ProcessType;
import lombok.extern.slf4j.Slf4j;

/**
 * 新規/更新を判定するサービス
 */
@Slf4j
@Service
public class SwimDecisionService {

    private final SelectMapper selectMapper;

    /**
     * コンストラクタ
     * 
     * @param selectMapper テーブル内での登録/検索時の条件を記載したインターフェース
     */
    public SwimDecisionService(SelectMapper selectMapper) {
        this.selectMapper = selectMapper;
    }

    /**
     * 新規/更新判定し、対応するインスタンスのキーを返却
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @param force 強制フラグ
     * @return 新規({@link SwimProcessorType#TYPE_NEW})/更新({@link SwimProcessorType#TYPE_UPDATE})
     * @throws CommonResponseBadRequestError フィードバック前の再出力時（forceフラグ指定により新規返却可能
     * @throws CommonResponseInternalServerError DB不整合
     */
    public String decisionNewUpdate(String businessNumber, String maxFallRangeId, boolean force)
        throws CommonResponseBadRequestError, CommonResponseInternalServerError {

        List<SelectEntity> history = selectMapper.selectAllHistory(businessNumber, maxFallRangeId);
        SelectEntity max = selectMapper.selectMax(businessNumber, maxFallRangeId);

        if (max == null) {
            return SwimProcessorType.TYPE_NEW.getBeanName();
        }

        if (!history.isEmpty()) {
            if (max.getHistoryIndex() == history.getLast().getHistoryIndex()) {
                return SwimProcessorType.TYPE_UPDATE.getBeanName();
            } else {
                if (force) {
                    return SwimProcessorType.TYPE_UPDATE.getBeanName();
                }
                throw new CommonResponseBadRequestError(
                    HttpStatus.BAD_REQUEST.value(), "フィードバック前の再出力(更新Excel) フィードバック済み[" + history.size() + "]" + ", 履歴["
                        + max.getHistoryIndex() + "]");
            }
        }

        if (max.getHistoryIndex() == 1) {
            if (force) {
                return SwimProcessorType.TYPE_NEW.getBeanName();
            }
            throw new CommonResponseBadRequestError(
                HttpStatus.BAD_REQUEST.value(), "フィードバック前の再出力(更新Excel) フィードバック済み[" + 0 + "]" + ", 履歴[" + max
                    .getHistoryIndex() + "]");
        }
        throw new CommonResponseInternalServerError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), "DB不整合 フィードバック済み[" + 0 + "]" + ", 履歴[" + max.getHistoryIndex()
                + "]");
    }

    /**
     * 事業者番号と最大落下範囲IDを使用して、新規登録/再登録を実行
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @param force 強制フラグ
     * @return 新規登録、TRUEの時は再登録
     */
    public SwimProcessorType.ProcessType decisionRegist(String businessNumber, String maxFallRangeId, boolean force) {

        SelectEntity max = selectMapper.selectMax(businessNumber, maxFallRangeId);
        if (max == null) {
            return ProcessType.TYPE_REGIST_NORMAL;
        }

        if (max.getHistoryIndex() == 1) {
            if (force) { // NOSONAR
                return ProcessType.TYPE_REGIST_RETRY;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * 事業者番号と最大落下範囲IDを使用して、更新/再更新を実行
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @param force 強制
     * @param history 履歴
     * @return 更新、TRUEの時は再更新
     */
    public SwimProcessorType.ProcessType decisionUpdate(String businessNumber, String maxFallRangeId, boolean force,
        List<SelectEntity> history) {

        SelectEntity max = selectMapper.selectMax(businessNumber, maxFallRangeId);
        if (max == null) {
            throw new IllegalArgumentException();
        }

        if (!history.isEmpty()) {
            if (max.getHistoryIndex() == history.getLast().getHistoryIndex()) {
                return ProcessType.TYPE_UPDATE_NORMAL;
            } else {
                if (force) {
                    return ProcessType.TYPE_UPDATE_RETRY;
                }
            }
        }
        throw new IllegalArgumentException();
    }
}
