package jp.go.meti.drone.swim.service.feedback;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.feedback.RequestSwimFeedback;
import jp.go.meti.drone.swim.model.feedback.ResponseSwimFeedback;
import jp.go.meti.drone.swim.repository.entity.SelectEntity;
import jp.go.meti.drone.swim.repository.entity.SwimFeedbackEntity;
import jp.go.meti.drone.swim.repository.mapper.SelectMapper;
import jp.go.meti.drone.swim.repository.mapper.SwimFeedbackRepository;

/**
 * フィードバック情報処理実装
 */
@Service
public class SwimFeedbackServiceImpl implements SwimFeedbackService {

    /** テーブル内での登録や検索時の条件を記載したインターフェース */
    private SelectMapper mapper;

    /** DBのswim_feedbackテーブルを操作するためのリポジトリ */
    private SwimFeedbackRepository swimFeedbackRepository;

    /**
     * コンストラクタ
     * 
     * @param mapper テーブル内での登録や検索時の条件を記載したインターフェース
     * @param swimFeedbackRepository DBのswim_feedbackテーブルを操作するためのリポジトリ
     */
    public SwimFeedbackServiceImpl(SelectMapper mapper, SwimFeedbackRepository swimFeedbackRepository) {
        this.mapper = mapper;
        this.swimFeedbackRepository = swimFeedbackRepository;
    }

    /**
     * フィードバック情報の登録/更新
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @param historyNum 履歴番号
     * @param request リクエスト
     * @param force 強制更新フラグ(任意履歴番号の更新用途)
     * @return フィードバック情報
     * @throws CommonResponseInternalServerError 予測しないシステムエラーが発生した場合
     * @throws CommonResponseNotFoundError 指定した情報が取得できない場合
     * @throws CommonResponseBadRequestError パラメーターに誤りがある場合に返却
     */
    @Override
    public ResponseSwimFeedback feedback(String businessNumber, String maxFallRangeId, Integer historyNum,
        RequestSwimFeedback request, boolean force) throws CommonResponseInternalServerError,
        CommonResponseNotFoundError, CommonResponseBadRequestError {

        SelectEntity maxHistory = mapper.selectMax(businessNumber, maxFallRangeId); // クエリB：最大履歴番号を確認
        if (maxHistory == null) {
            // 最大落下範囲・事業者番号不正
            throw new CommonResponseNotFoundError().code(HttpStatus.NOT_FOUND.value())
                .errorMessage("事業者番号,最大落下範囲ID:[" + businessNumber + "," + maxFallRangeId + "]");
        }
        SelectEntity feedEntity = mapper.selectFeedback(businessNumber, maxFallRangeId, historyNum); // クエリC:指定された履歴番号がフィードバック済みか（≒履歴番号不正チェック）
        if (feedEntity != null) {
            if (force || maxHistory.getHistoryIndex() == feedEntity.getHistoryIndex()) {
                // 履歴あり && フィードバック済み
                // UPDATE：指定履歴番号
                int updateNum = this.updateFeedback(request, historyNum, feedEntity);
                return new ResponseSwimFeedback(
                    HttpStatus.OK.value(), "反映済み(UPDATE)[" + updateNum + "]： " + maxFallRangeId + "[" + historyNum
                        + "]");

            } else {
                // NOTE 前の番号の更新→指定された履歴番号が不正
                throw new CommonResponseBadRequestError().code(HttpStatus.BAD_REQUEST.value())
                    .errorMessage(
                        "履歴番号不正：SWIM登録済み,または存在しない履歴番号指定[" + historyNum + "]" + " 最大値" + maxHistory.getHistoryIndex()
                            + " 事業者番号,最大落下範囲ID:[" + businessNumber + "," + maxFallRangeId + "]");
            }
        } else {
            if ((force && maxHistory.getHistoryIndex() > historyNum) || maxHistory.getHistoryIndex() == historyNum) {
                // 履歴あり && フィードバックなし
                // INSERT：指定履歴番号
                this.insertFeedback(request, historyNum, businessNumber, maxFallRangeId);
                return new ResponseSwimFeedback(
                    HttpStatus.OK.value(), "反映済み(INSERT)：" + maxFallRangeId + "[" + historyNum + "]");

            } else {
                /*
                 * 履歴１： max1, size()1 --- feedあり：deadcode 履歴１： max1, size()1 --- feedなし１指定：正常 履歴１： max1, size()1 ---
                 * feedなし２指定：★（履歴番号!=max 履歴２： max2, size()2 --- feedあり：deadcode 履歴２： max2, size()2 --- feedなし１指定：★（履歴番号!=max
                 * 履歴２： max2, size()2 --- feedなし２指定:正常
                 */
                // 履歴番号指定値不正として400返却
                throw new CommonResponseBadRequestError().code(HttpStatus.BAD_REQUEST.value())
                    .errorMessage(
                        "履歴番号不正[" + historyNum + "]" + " 最大値" + maxHistory.getHistoryIndex() + " 事業者番号,最大落下範囲ID:["
                            + businessNumber + "," + maxFallRangeId + "]");
            }
        }

    }

    /**
     * model作成：update/insert兼用
     * 
     * @param request リクエスト
     * @param historyIndex 履歴番号
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @return update/insert用model
     */
    private SwimFeedbackEntity createModel(RequestSwimFeedback request, int historyIndex, String businessNumber,
        String maxFallRangeId) {
        SwimFeedbackEntity target = new SwimFeedbackEntity();
        // PK
        target.setBusinessNumber(businessNumber);
        target.setMaxFallRangeId(maxFallRangeId);
        target.setHistoryIndex(historyIndex);

        // data:feature
        target.setFeatureId(request.getFeature().getFeatureId());
        target.setFeatureTimesliceid(request.getFeature().getTimesliceId());
        target.setFeatureBeginPosition(request.getFeature().getBeginPosition());
        target.setFeatureEndPosition(request.getFeature().getEndPosition());
        // data:Obj
        target.setObject10Objid(request.getObject10().getObjectId());
        target.setObject13ParObjid(request.getObject13().getParObjectId());
        target.setObject13Objid(request.getObject13().getObjectId());
        target.setObject24Objid(request.getObject24().getObjectId());
        target.setObject29ParObjid(request.getObject29().getParObjectId());
        target.setObject29Objid(request.getObject29().getObjectId());
        target.setObject38ParObjid(request.getObject38().getParObjectId());
        target.setObject38Objid(request.getObject38().getObjectId());
        // other
        target.setEnable(true);
        // target.setUpdateAt(null); NOTE insert時：未指定＝実行日時
        // target.setCreateAt(null); NOTE insert時：未指定＝実行日時

        return target;
    }

    // UPDATE発行： NOTE 更新日時のみ現在時刻を設定
    private int updateFeedback(RequestSwimFeedback request, int historyIndex, SelectEntity base) {
        SwimFeedbackEntity target = this.createModel(
            request,
            historyIndex,
            base.getBusinessNumber(),
            base.getMaxFallRangeId());
        target.setUpdateAt(LocalDateTime.now());
        return this.swimFeedbackRepository.updateByPrimaryKeySelective(target);
    }

    // INSERT発行： NOTE 更新・作成日時は未指定（デフォルト設定でINSERT）
    private void insertFeedback(RequestSwimFeedback request, int historyIndex, String businessNumber,
        String maxFallRangeId) {
        SwimFeedbackEntity target = this.createModel(request, historyIndex, businessNumber, maxFallRangeId);
        this.swimFeedbackRepository.insertSelective(target);
    }
}
