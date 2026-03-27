package jp.go.meti.drone.swim.service.export;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.apimodel.export.MaxFallRange;
import jp.go.meti.drone.swim.com.ExcelCreator;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.excel.dto.Airspace;
import jp.go.meti.drone.swim.model.excel.dto.Feature;
import jp.go.meti.drone.swim.model.excel.dto.Object10;
import jp.go.meti.drone.swim.model.excel.dto.Object13;
import jp.go.meti.drone.swim.model.excel.dto.Object24;
import jp.go.meti.drone.swim.model.excel.dto.Object29;
import jp.go.meti.drone.swim.model.excel.dto.Object38;
import jp.go.meti.drone.swim.repository.entity.SelectEntity;
import jp.go.meti.drone.swim.repository.entity.SwimFeedbackEntity;
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
import jp.go.meti.drone.swim.service.export.SwimProcessorType.ProcessType;
import lombok.extern.slf4j.Slf4j;

/**
 * SWIMに連携する情報を、更新Excelテンプレートに出力処理するサービス
 */
@Slf4j
@Service(SwimProcessorType.ProcessorNames.UPDATE_EXCEL)
public class SwimExportProcessorUpdateImpl extends AbstractSwimExportProcessor {

    /**
     * Excelテンプレート
     */
    @Value("${excel.template.path.update}")
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
    public SwimExportProcessorUpdateImpl(SwimDecisionService swimDecisionService,
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
    protected byte[] createWorkbook(Airspace airspace) throws CommonResponseInternalServerError {
        // 更新時用helper呼び出し
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

    private Airspace convertAirSpaceHistory(List<SelectEntity> histories) {
        try {
            Airspace airspace = new Airspace();

            List<Feature> features = new ArrayList<>();
            List<Object10> object10s = new ArrayList<>();
            List<Object13> object13s = new ArrayList<>();
            List<Object24> object24s = new ArrayList<>();
            List<Object29> object29s = new ArrayList<>();
            List<Object38> object38s = new ArrayList<>();

            for (SelectEntity history : histories) {
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
                
                // Object10
                object10s.add(
                    object10Helper.convert(
                        featureId,
                        feedbackEntity.getFeatureTimesliceid(),
                        feedbackEntity.getObject10Objid(),
                        historyEntity.getObject10TimeintervalIndex()));
                // Object13
                object13s.add(
                    object13Helper.convert(
                        featureId,
                        feedbackEntity.getFeatureTimesliceid(),
                        feedbackEntity.getObject13ParObjid(),
                        feedbackEntity.getObject13Objid()));

                // Object24
                object24s.add(
                    object24Helper.convert(
                        featureId,
                        feedbackEntity.getFeatureTimesliceid(),
                        feedbackEntity.getObject24Objid(),
                        historyEntity.getObject24AirSpaceVolumeIndex()));
                // Object29
                object29s.add(
                    object29Helper.convertFromHistory(
                        featureId,
                        feedbackEntity.getFeatureTimesliceid(),
                        feedbackEntity.getObject29ParObjid(),
                        feedbackEntity.getObject29Objid(),
                        historyEntity.getObject29Gml()));
                // Object28
                object38s.add(
                    object38Helper.convertFromHistory(
                        featureId,
                        feedbackEntity.getFeatureTimesliceid(),
                        feedbackEntity.getObject38ParObjid(),
                        feedbackEntity.getObject38Objid(),
                        historyEntity.getObject38Upperlimit(),
                        historyEntity.getObject38HorizontalProjectionIndex()));
            }

            airspace.setFeatures(features);
            airspace.setObject10(object10s);
            airspace.setObject13(object13s);
            airspace.setObject24(object24s);
            airspace.setObject29(object29s);
            airspace.setObject38(object38s);

            return airspace;
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected Airspace execute(String maxFallRangeId, String businessNumber, String operatorId, boolean force)
        throws CommonResponseInternalServerError {

        // 航路画定通信
        MaxFallRange maxFallRange = maximumFallRangeService.getMaximumFallRange(maxFallRangeId, businessNumber);

        validator.isPolygonWithinJapan(maxFallRange.getGeometry().getCoordinates());
        validator.isPolygonClosed(maxFallRange.getGeometry().getCoordinates());

        Double upper = uaslMaxAltitudeService.getMaxHeight(maxFallRangeId);

        List<SelectEntity> histories = super.selectAndMergeHistory(businessNumber, maxFallRangeId);
        String designator = histories.get(0).getSwimId();

        // 最初に発行されたfeatureId
        String featureId = histories.getFirst().getSwimFeedback().getFeatureId();

        // リトライ判定：リトライ時は最後の履歴レコードを削除
        SwimProcessorType.ProcessType decision = swimDecisionService.decisionUpdate(
            businessNumber,
            maxFallRangeId,
            force,
            histories);
        if (ProcessType.TYPE_UPDATE_RETRY.equals(decision)) {
            SelectEntity lastHistoy = selectMapper.selectMax(businessNumber, maxFallRangeId);
            Integer lastHistoryIndex = lastHistoy.getHistoryIndex();
            log.debug("delete history : " + maxFallRangeId + " - " + businessNumber + " [" + lastHistoryIndex + "]");
            Integer affectetRows = swimMaxFallRangeHistoryRepository.deleteByPrimaryKey(
                businessNumber,
                maxFallRangeId,
                lastHistoryIndex);
            log.info("deleted history : " + maxFallRangeId + " - " + businessNumber + " [" + affectetRows + "]");
        }

        String timeSliceId = "TimeSliceID_" + getTemporaryId("00");
        // 今回分
        Airspace newAirspace = convertAirSpace(
            featureId,
            timeSliceId,
            designator,
            maxFallRange.getGeometry().getCoordinates(),
            upper);

        // Feature.endPosition更新
        histories.getLast()
            .getSwimFeedback()
            .setFeatureEndPosition(newAirspace.getFeatures().get(0).getColumnBeginPosition());

        // 前回まで
        Airspace airspace = convertAirSpaceHistory(histories);
        airspace.getFeatures().add(newAirspace.getFeatures().get(0));
        airspace.getObject10().add(newAirspace.getObject10().get(0));
        airspace.getObject13().add(newAirspace.getObject13().get(0));
        airspace.getObject24().add(newAirspace.getObject24().get(0));
        airspace.getObject29().add(newAirspace.getObject29().get(0));
        airspace.getObject38().add(newAirspace.getObject38().get(0));

        saveHistory(maxFallRangeId, businessNumber, newAirspace);

        return airspace;
    }
}
