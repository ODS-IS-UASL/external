package jp.go.meti.drone.swim.service.export;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.export.ResponseSuccessSwim;

/**
 * SWIM連携インターフェース
 */
public interface SwimExportProcessor {

    /**
     * maxFallRangeId と businessNumberを使用して、SWIM連携用Excelファイルを生成
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者ID
     * @param operatorId 事業者ID(uuid)
     * @param force 強制フラグ
     * @return ZIP化されたExcelファイル
     * @throws CommonResponseNotFoundError 対象レコードなし
     * @throws CommonResponseBadRequestError リクエスト不正
     * @throws CommonResponseInternalServerError 予期せぬエラー
     */
    public ResponseSuccessSwim getMaximumFallRange(String maxFallRangeId, String businessNumber, String operatorId,
        boolean force) throws CommonResponseNotFoundError, CommonResponseBadRequestError,
        CommonResponseInternalServerError;

    /**
     * 取消用SWIM連携用Excelファイルを生成（外部通信なし
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者ID
     * @param force 強制フラグ
     * @return ZIP化されたExcelファイル
     * @throws CommonResponseNotFoundError 対象レコードなし
     * @throws CommonResponseInternalServerError 予期せぬエラー
     */
    public ResponseSuccessSwim getMaximumFallRangeCancel(String maxFallRangeId, String businessNumber, boolean force)
        throws CommonResponseNotFoundError, CommonResponseInternalServerError;
}
