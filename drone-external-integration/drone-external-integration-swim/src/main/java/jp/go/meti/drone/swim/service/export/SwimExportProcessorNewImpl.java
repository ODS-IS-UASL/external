package jp.go.meti.drone.swim.service.export;

import java.time.LocalDateTime;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.apimodel.export.MaxFallRange;
import jp.go.meti.drone.swim.com.ExcelCreator;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.excel.dto.Airspace;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeEntity;
import jp.go.meti.drone.swim.repository.mapper.SelectMapper;
import jp.go.meti.drone.swim.repository.mapper.SwimFeedbackRepository;
import jp.go.meti.drone.swim.repository.mapper.SwimMaxFallRangeHistoryRepository;
import jp.go.meti.drone.swim.repository.mapper.SwimMaxFallRangeRepository;
import jp.go.meti.drone.swim.service.convert.DesignatorAllocator;
import jp.go.meti.drone.swim.service.convert.DesignatorConverter;
import jp.go.meti.drone.swim.service.convert.FeatureHelper;
import jp.go.meti.drone.swim.service.convert.Object10Helper;
import jp.go.meti.drone.swim.service.convert.Object13Helper;
import jp.go.meti.drone.swim.service.convert.Object24Helper;
import jp.go.meti.drone.swim.service.convert.Object29Helper;
import jp.go.meti.drone.swim.service.convert.Object38Helper;
import jp.go.meti.drone.swim.service.export.SwimProcessorType.ProcessType;
import lombok.extern.slf4j.Slf4j;

/**
 * SWIMに連携する情報を、新規Excelテンプレートに出力処理するサービス Note: force flag
 */
@Slf4j
@Service(SwimProcessorType.ProcessorNames.NEW_EXCEL)
public class SwimExportProcessorNewImpl extends AbstractSwimExportProcessor {

    /**
     * Excelテンプレート
     */
    @Value("${excel.template.path.regist}")
    private String excelTemplatePath;

    /** 事業者番号をもとに事業者内ID生成クラス */
    private final DesignatorAllocator designatorAllocator;

    /** 航路ID変換クラス(事業者内IDも発番) */
    protected final DesignatorConverter designatorConverter;

    /**
     * コンストラクタ
     * 
     * @param swimDecisionService 新規/更新判定サービス
     * @param maximumFallRangeService 航路画定からの最大落下範囲取得サービス
     * @param uaslMaxAltitudeService 最大高度取得サービス（最大落下範囲に含まれる全航路から抽出）
     * @param pipUserAttributeService ユーザー情報取得サービス
     * @param excelCreator Excel生成
     * @param validator バリデーター
     * @param featureHelper featureヘルパー
     * @param object10Helper object10ヘルパー
     * @param object13Helper object13ヘルパー
     * @param object24Helper object24ヘルパー
     * @param object29Helper object29ヘルパー
     * @param object38Helper object38ヘルパー
     * @param swimMaxFallRangeRepository リポジトリ: 最大落下範囲TBL
     * @param swimMaxFallRangeHistoryRepository リポジトリ： 最大落下範囲履歴TBL
     * @param swimFeedbackRepository リポジトリ： SWIMフィードバックTBL
     * @param selectMapper リポジトリ： 検索用クエリ
     * @param designatorAllocator 事業者番号をもとに事業者内ID生成サービス
     * @param designatorConverter 航路ID変換サービス
     */
    public SwimExportProcessorNewImpl(SwimDecisionService swimDecisionService,
        MaximumFallRangeService maximumFallRangeService, UaslMaxAltitudeService uaslMaxAltitudeService,
        PipUserAttributeService pipUserAttributeService, ExcelCreator excelCreator, MaximumFallRangeValidator validator,
        FeatureHelper featureHelper, Object10Helper object10Helper, Object13Helper object13Helper,
        Object24Helper object24Helper, Object29Helper object29Helper, Object38Helper object38Helper,
        SwimMaxFallRangeRepository swimMaxFallRangeRepository,
        SwimMaxFallRangeHistoryRepository swimMaxFallRangeHistoryRepository,
        SwimFeedbackRepository swimFeedbackRepository, SelectMapper selectMapper,
        DesignatorAllocator designatorAllocator, DesignatorConverter designatorConverter) {

        super(swimDecisionService, maximumFallRangeService, uaslMaxAltitudeService, pipUserAttributeService,
            excelCreator, validator, featureHelper, object10Helper, object13Helper, object24Helper, object29Helper,
            object38Helper, swimMaxFallRangeRepository, swimMaxFallRangeHistoryRepository, swimFeedbackRepository,
            selectMapper);

        this.designatorAllocator = designatorAllocator;
        this.designatorConverter = designatorConverter;
    }

    @Override
    protected byte[] createWorkbook(Airspace airspace) throws CommonResponseInternalServerError {
        try {
            XSSFWorkbook workbook = excelCreator.loadTemplate(excelTemplatePath);
            excelCreator.setFeature(workbook, airspace.getFeatures());
            excelCreator.setObject10(workbook, airspace.getObject10());
            excelCreator.setObject13(workbook, airspace.getObject13());
            excelCreator.setObject24(workbook, airspace.getObject24());
            excelCreator.setObject29(workbook, airspace.getObject29());
            excelCreator.setObject38(workbook, airspace.getObject38());
            return excelCreator.workbookToBytes(workbook);
        } catch (Exception e) {
            log.error("Excel出力時にエラーが発生しました。", e);
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Excel出力時にエラーが発生しました。");
        }
    }

    @Override
    protected Airspace execute(String maxFallRangeId, String businessNumber, String operatorId, boolean force)
        throws CommonResponseInternalServerError {
        // 航路画定通信
        MaxFallRange maxFallRange = maximumFallRangeService.getMaximumFallRange(maxFallRangeId, businessNumber);

        // バリデーションチェック
        validator.isPolygonWithinJapan(maxFallRange.getGeometry().getCoordinates());
        validator.isPolygonClosed(maxFallRange.getGeometry().getCoordinates());

        // 高度取得
        Double upper = uaslMaxAltitudeService.getMaxHeight(maxFallRangeId);

        // 航路ID
        String designator = null;

        // 新規/更新判定
        SwimProcessorType.ProcessType decision = swimDecisionService.decisionRegist(
            businessNumber,
            maxFallRangeId,
            force);
        if (ProcessType.TYPE_REGIST_RETRY.equals(decision)) {
            // リトライ時、１回目の履歴削除
            SwimMaxFallRangeEntity entity = swimMaxFallRangeRepository.selectByPrimaryKey(
                businessNumber,
                maxFallRangeId);
            entity.setUpdateAt(LocalDateTime.now());
            swimMaxFallRangeRepository.updateByPrimaryKeySelective(entity);

            // 強制発行時、最初に付与した識別子を出力
            designator = entity.getSwimId();

            Integer affectetRows = swimMaxFallRangeHistoryRepository.deleteByPrimaryKey(
                businessNumber,
                maxFallRangeId,
                1);
            log.info("dleted history : " + maxFallRangeId + " - " + businessNumber + " [" + affectetRows + "]");
        } else { // TYPE_REGIST_NORMAL

            // PIP通信
            String swimOperatorId = pipUserAttributeService.getSwimId(operatorId);
            log.debug(swimOperatorId);
            if (swimOperatorId == null) {
                // PIPからswimOperatorId取得失敗時、500エラー
                throw new CommonResponseInternalServerError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "get OperatorId failed.");
            }

            try {
                String internalOperatorId = this.designatorAllocator.generateInternalIdentifier(businessNumber);
                designator = this.designatorConverter.convertId(
                    swimOperatorId,
                    internalOperatorId,
                    maxFallRange.getTypeId(),
                    maxFallRange.getRegionId());

                SwimMaxFallRangeEntity swimMaxfallRangeEntity = SwimMaxFallRangeEntity.builder()
                    .businessNumber(businessNumber)
                    .maxFallRangeId(maxFallRangeId)
                    .operatorId(operatorId)
                    .identifier(swimOperatorId)
                    .internalIdentifier(internalOperatorId)
                    .swimId(designator)
                    .build();
                swimMaxFallRangeRepository.insertSelective(swimMaxfallRangeEntity);

            } catch (DuplicateKeyException e) {
                log.error(e.getMessage(), e);
                throw new CommonResponseInternalServerError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            }
        }

        String featureId = "FeatureID_" + getTemporaryId("00");
        String timeSliceId = "TimeSliceID_" + getTemporaryId("00");

        Airspace airspace = convertAirSpace(
            featureId,
            timeSliceId,
            designator,
            maxFallRange.getGeometry().getCoordinates(),
            upper);
        saveHistory(maxFallRangeId, businessNumber, airspace);

        return airspace;
    }
}
