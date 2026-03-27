package jp.go.meti.drone.swim.service.feedback;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.feedback.RequestSwimFeedback;
import jp.go.meti.drone.swim.model.feedback.ResponseSwimFeedback;

/**
 * フィードバック情報処理インタフェイス
 */
public interface SwimFeedbackService {

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
     * @throws CommonResponseBadRequestError パラメーターに誤りがある場合に返却する
     */
    public ResponseSwimFeedback feedback(String businessNumber, String maxFallRangeId, Integer historyNum,
        RequestSwimFeedback request, boolean force) throws CommonResponseInternalServerError,
        CommonResponseNotFoundError, CommonResponseBadRequestError;
}
