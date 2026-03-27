package jp.go.meti.drone.swim.service.export;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.com.ExcelCreator;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.excel.dto.Airspace;
import jp.go.meti.drone.swim.model.excel.dto.Feature;
import jp.go.meti.drone.swim.repository.entity.SelectEntity;
import jp.go.meti.drone.swim.repository.entity.SwimFeedbackEntity;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeEntity;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeHistoryEntity;
import jp.go.meti.drone.swim.repository.mapper.SelectMapper;
import jp.go.meti.drone.swim.repository.mapper.SwimFeedbackRepository;
import jp.go.meti.drone.swim.repository.mapper.SwimMaxFallRangeHistoryRepository;
import jp.go.meti.drone.swim.repository.mapper.SwimMaxFallRangeRepository;
import jp.go.meti.drone.swim.service.convert.FeatureHelper;
import jp.go.meti.drone.swim.service.convert.Object10Helper;
import jp.go.meti.drone.swim.service.convert.Object13Helper;
import jp.go.meti.drone.swim.service.convert.Object24Helper;
import jp.go.meti.drone.swim.service.convert.Object29Helper;
import jp.go.meti.drone.swim.service.convert.Object38Helper;
import lombok.extern.slf4j.Slf4j;

/**
 * SWIMに連携する情報を、取消Excelテンプレートに出力処理するサービス
 */
@Slf4j
@Service(SwimProcessorType.ProcessorNames.CANCELLATION_EXCEL)
public class SwimExportProcessorCancelImpl extends AbstractSwimExportProcessor {

    /**
     * Excelテンプレート
     */
    @Value("${excel.template.path.cancel}")
    private String excelTemplatePath;

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
     */
    public SwimExportProcessorCancelImpl(SwimDecisionService swimDecisionService,
        MaximumFallRangeService maximumFallRangeService, UaslMaxAltitudeService uaslMaxAltitudeService,
        PipUserAttributeService pipUserAttributeService, ExcelCreator excelCreator, MaximumFallRangeValidator validator,
        FeatureHelper featureHelper, Object10Helper object10Helper, Object13Helper object13Helper,
        Object24Helper object24Helper, Object29Helper object29Helper, Object38Helper object38Helper,
        SwimMaxFallRangeRepository swimMaxFallRangeRepository,
        SwimMaxFallRangeHistoryRepository swimMaxFallRangeHistoryRepository,
        SwimFeedbackRepository swimFeedbackRepository, SelectMapper selectMapper) {

        super(swimDecisionService, maximumFallRangeService, uaslMaxAltitudeService, pipUserAttributeService,
            excelCreator, validator, featureHelper, object10Helper, object13Helper, object24Helper, object29Helper,
            object38Helper, swimMaxFallRangeRepository, swimMaxFallRangeHistoryRepository, swimFeedbackRepository,
            selectMapper);
    }

    @Override
    protected Airspace executeForCancel(String maxFallRangeId, String businessNumber, boolean force)
        throws CommonResponseNotFoundError, CommonResponseInternalServerError {

        List<SelectEntity> histories = super.selectAndMergeHistory(businessNumber, maxFallRangeId);
        if (histories == null || histories.isEmpty()) {
            throw new CommonResponseNotFoundError(
                HttpStatus.NOT_FOUND.value(), "対象情報なし[" + businessNumber + "," + maxFallRangeId + "]");
        }

        Airspace airspace = convertAirSpaceHistory(histories);
        Feature f = airspace.getFeatures().getLast();

        String endPosition = super.createUTCString(1);
        f.setColumnEndPosition(endPosition);

        SelectEntity lastHistoy = selectMapper.selectMax(businessNumber, maxFallRangeId);
        Integer lastHistoryIndex = lastHistoy.getHistoryIndex();

        SwimMaxFallRangeHistoryEntity historyEntity = SwimMaxFallRangeHistoryEntity.builder()
            .businessNumber(businessNumber)
            .maxFallRangeId(maxFallRangeId)
            .historyIndex(lastHistoryIndex)
            .featureEndPosition(endPosition)
            .updateAt(LocalDateTime.now())
            .build();
        swimMaxFallRangeHistoryRepository.updateByPrimaryKeySelective(historyEntity);

        SwimMaxFallRangeEntity maxfallRangeEntity = SwimMaxFallRangeEntity.builder()
            .businessNumber(businessNumber)
            .maxFallRangeId(maxFallRangeId)
            .updateAt(LocalDateTime.now())
            .build();
        swimMaxFallRangeRepository.updateByPrimaryKeySelective(maxfallRangeEntity);

        return airspace;
    }

    private Airspace convertAirSpaceHistory(List<SelectEntity> histories) {
        try {
            Airspace airspace = new Airspace();
            List<Feature> features = new ArrayList<>();

            SelectEntity history = histories.getLast();

            SwimFeedbackEntity feedbackEntity = history.getSwimFeedback();
            SwimMaxFallRangeHistoryEntity historyEntity = history.getHistory();

            String featureId = feedbackEntity.getFeatureId();
            String designator = history.getSwimId();
            // Feature
            features.add(
                featureHelper.convert(
                    featureId,
                    feedbackEntity.getFeatureTimesliceid(),
                    feedbackEntity.getFeatureBeginPosition(),
                    feedbackEntity.getFeatureEndPosition(),
                    designator,
                    historyEntity.getFeatureActivationIndex(),
                    historyEntity.getFeatureGeometryComponentIndex()));

            airspace.setFeatures(features);
            return airspace;
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected byte[] createWorkbook(Airspace airspace) throws CommonResponseInternalServerError {
        try {
            XSSFWorkbook workbook = excelCreator.loadTemplate(excelTemplatePath);
            excelCreator.setFeature(workbook, airspace.getFeatures());
            return excelCreator.workbookToBytes(workbook);
        } catch (Exception e) {
            log.error("Excel出力時にエラーが発生しました。", e);
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Excel出力時にエラーが発生しました。");
        }
    }

}
