package jp.go.meti.drone.swim.service.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import jp.go.meti.drone.swim.com.ExcelCreator;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.swim.model.excel.dto.Airspace;
import jp.go.meti.drone.swim.model.excel.dto.Feature;
import jp.go.meti.drone.swim.model.excel.dto.Object10;
import jp.go.meti.drone.swim.model.excel.dto.Object13;
import jp.go.meti.drone.swim.model.excel.dto.Object24;
import jp.go.meti.drone.swim.model.excel.dto.Object29;
import jp.go.meti.drone.swim.model.excel.dto.Object38;
import jp.go.meti.drone.swim.model.export.ResponseSuccessSwim;
import jp.go.meti.drone.swim.repository.entity.SelectEntity;
import jp.go.meti.drone.swim.repository.entity.SwimFeedbackEntity;
import jp.go.meti.drone.swim.repository.entity.SwimFeedbackEntityExample;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeHistoryEntity;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeHistoryEntityExample;
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
 * SWIM連携処理
 */
@Slf4j
public abstract class AbstractSwimExportProcessor implements SwimExportProcessor {

    /** 新規/更新を判定するサービス */
    protected final SwimDecisionService swimDecisionService;

    /** 航路画定から最大落下範囲を取得するサービス */
    protected final MaximumFallRangeService maximumFallRangeService;

    /** 最大高度を取得するサービス（最大落下範囲に含まれる全航路から抽出） */
    protected final UaslMaxAltitudeService uaslMaxAltitudeService;

    /** ユーザー情報取得サービス */
    protected final PipUserAttributeService pipUserAttributeService;

    /** バリデーションの実装 */
    protected final MaximumFallRangeValidator validator;

    /** Excelテンプレートを読み込み値を差し込む処理の実装 */
    protected final ExcelCreator excelCreator;

    /** ZIPヘッダ情報のサイズ */
    private static final int SIZE = 22;

    /** Excelファイル名：prefix */
    private String downloadFilePrefix = "Airspace_";

    /** Excelファイル名：suffix日付フォーマット */
    private String downloadFileNameFormat = "yyyyMMddHHmmss";

    @Value("${export.common.dateformat}")
    private String dateFormat;

    private String fixedTemporaryId = "0001";

    // ヘルパー群
    /** Featureヘルパー */
    protected final FeatureHelper featureHelper;

    /** Object10ヘルパー */
    protected final Object10Helper object10Helper;

    /** Object13ヘルパー */
    protected final Object13Helper object13Helper;

    /** Object24ヘルパー */
    protected final Object24Helper object24Helper;

    /** Object29ヘルパー */
    protected final Object29Helper object29Helper;

    /** Object38ヘルパー */
    protected final Object38Helper object38Helper;

    // Repository群
    /** SWIM向け最大落下範囲テーブル */
    protected SwimMaxFallRangeRepository swimMaxFallRangeRepository;

    /** SWIM向け履歴テーブル */
    protected SwimMaxFallRangeHistoryRepository swimMaxFallRangeHistoryRepository;

    /** SWIMフィードバックテーブル */
    protected SwimFeedbackRepository swimFeedbackRepository;

    /** 検索時リポジトリ */
    protected SelectMapper selectMapper;

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
    public AbstractSwimExportProcessor( // NOSONAR
        SwimDecisionService swimDecisionService, MaximumFallRangeService maximumFallRangeService,
        UaslMaxAltitudeService uaslMaxAltitudeService, PipUserAttributeService pipUserAttributeService,
        ExcelCreator excelCreator, MaximumFallRangeValidator validator, FeatureHelper featureHelper,
        Object10Helper object10Helper, Object13Helper object13Helper, Object24Helper object24Helper,
        Object29Helper object29Helper, Object38Helper object38Helper,
        SwimMaxFallRangeRepository swimMaxFallRangeRepository,
        SwimMaxFallRangeHistoryRepository swimMaxFallRangeHistoryRepository,
        SwimFeedbackRepository swimFeedbackRepository, SelectMapper selectMapper) {
        this.swimDecisionService = swimDecisionService;
        this.maximumFallRangeService = maximumFallRangeService;
        this.uaslMaxAltitudeService = uaslMaxAltitudeService;
        this.pipUserAttributeService = pipUserAttributeService;
        this.excelCreator = excelCreator;
        this.validator = validator;
        this.featureHelper = featureHelper;
        this.object10Helper = object10Helper;
        this.object13Helper = object13Helper;
        this.object24Helper = object24Helper;
        this.object29Helper = object29Helper;
        this.object38Helper = object38Helper;
        this.swimMaxFallRangeRepository = swimMaxFallRangeRepository;
        this.swimMaxFallRangeHistoryRepository = swimMaxFallRangeHistoryRepository;
        this.swimFeedbackRepository = swimFeedbackRepository;
        this.selectMapper = selectMapper;
    }

    /**
     * 最大落下範囲の情報を取得して処理を実行
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @param operatorId 事業者ID(uuid)
     * @param force 強制フラグ
     * @throws CommonResponseNotFoundError 指定データなし
     * @throws CommonResponseBadRequestError リクエスト不正
     * @throws CommonResponseInternalServerError 予期せぬエラー発生
     */
    @Override
    @Transactional
    public ResponseSuccessSwim getMaximumFallRange(String maxFallRangeId, String businessNumber, String operatorId,
        boolean force) throws CommonResponseNotFoundError, CommonResponseBadRequestError,
        CommonResponseInternalServerError {

        try {
            log.debug(
                "SwimExportServiceImpl::getMaximumFallRange(): maxFallRangeId = " + maxFallRangeId
                    + " businessNumber = " + businessNumber);

            Airspace airspace = this.execute(maxFallRangeId, businessNumber, operatorId, force);
            byte[] wb = createWorkbook(airspace);
            return buildRespose(wb, maxFallRangeId, businessNumber);

        } catch (CommonResponseBadRequestError e) {
            log.debug(e.getMessage());
            throw e;
        } catch (CommonResponseNotFoundError e) {
            log.debug(e.getMessage());
            throw e;
        } catch (CommonResponseInternalServerError e) {
            log.debug(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("予期しないシステムエラーが発生しました。", e);
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "予期しないシステムエラーが発生しました。");
        }
    }

    /**
     * 最大落下範囲情報の取消処理を実行
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @param force 強制フラグ
     * @throws CommonResponseNotFoundError 指定データなし
     * @throws CommonResponseInternalServerError 予期せぬエラー発生
     * @return 処理結果のレスポンス
     */
    @Override
    @Transactional
    public ResponseSuccessSwim getMaximumFallRangeCancel(String maxFallRangeId, String businessNumber, boolean force)
        throws CommonResponseNotFoundError, CommonResponseInternalServerError {

        try {
            log.debug(
                "SwimExportServiceImpl::getMaximumFallRangeCancel(): maxFallRangeId = " + maxFallRangeId
                    + " businessNumber = " + businessNumber);

            Airspace airspace = this.executeForCancel(maxFallRangeId, businessNumber, force);
            byte[] wb = createWorkbook(airspace);
            return buildRespose(wb, maxFallRangeId, businessNumber);

        } catch (CommonResponseNotFoundError e) {
            log.debug(e.getMessage());
            throw e;
        } catch (CommonResponseInternalServerError e) {
            log.debug(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "予期しないシステムエラーが発生しました。");
        }
    }

    /**
     * Excelバイト列をZIPストリーム変換後、レスポンスオブジェクトを返却
     * 
     * @param workbook エクセルworkbookのバイト配列
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @return レスポンスオブジェクト
     * @throws CommonResponseInternalServerError Excelファイル生成時のエラー
     */
    protected ResponseSuccessSwim buildRespose(byte[] workbook, String maxFallRangeId, String businessNumber)
        throws CommonResponseInternalServerError {
        String fileName = getFileName(maxFallRangeId, businessNumber);
        try {
            // エクスポート用OutputStream作成
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream, StandardCharsets.UTF_8)) {
                ZipEntry entry = new ZipEntry(fileName + ".xlsx");
                zipOutputStream.putNextEntry(entry);
                zipOutputStream.write(workbook);
                zipOutputStream.closeEntry();
            }
            // ZIPストリームのチェック
            if (byteArrayOutputStream.size() <= SIZE) {
                throw new CommonResponseInternalServerError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "ZIPサイズ不正" + byteArrayOutputStream.size());
            }
            ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
            return new ResponseSuccessSwim(fileName + ".zip", resource);
        } catch (CommonResponseInternalServerError e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("Excel出力失敗", e);
            throw new CommonResponseInternalServerError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Excel出力失敗");
        }
    }

    /**
     * 現在時刻(UTC)を含めたExcelファイル名を生成
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @return Excelファイル名
     */
    private String getFileName(String maxFallRangeId, String businessNumber) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(downloadFileNameFormat);
        // UTC
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        String timestamp = now.format(formatter);
        return downloadFilePrefix + maxFallRangeId + "_" + businessNumber + "_" + timestamp;
    }

    /**
     * 新規・更新別Excel出力 - 航路ID8桁の生成 - 追加出力情報(空域)のDB保存 - 更新時は履歴取得
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @param operatorId 事業者ID(uuid)
     * @param force 強制フラグ
     * @return Excel出力用モデル
     * @throws CommonResponseInternalServerError バックエンド通信失敗時/Excel出力失敗時
     */
    protected Airspace execute(String maxFallRangeId, String businessNumber, String operatorId, boolean force)
        throws CommonResponseInternalServerError {
        throw new IllegalCallerException();
    }

    /**
     * 取消用Excel出力 - DB値のみ使用
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者ID
     * @param force 強制フラグ
     * @return Excel出力用モデル
     * @throws CommonResponseNotFoundError 対象レコードなし
     * @throws CommonResponseInternalServerError 予期せぬエラー/Excel出力失敗時
     */
    protected Airspace executeForCancel(String maxFallRangeId, String businessNumber, boolean force)
        throws CommonResponseNotFoundError, CommonResponseInternalServerError {
        throw new IllegalCallerException();
    }

    /**
     * 最大落下範囲の情報をもとにExcelファイルを作成してbyte[]で返却
     * 
     * @param airspace バリデーション済の空域情報
     * @return Excelオブジェクトbyte[]
     * @throws CommonResponseInternalServerError 予期せぬエラー/Excel出力失敗時
     */
    protected abstract byte[] createWorkbook(Airspace airspace) throws CommonResponseInternalServerError;

    /**
     * 履歴とフィードバックを取得して保存
     * 
     * @param businessNumber 事業者番号
     * @param maxFallRangeId 最大落下範囲ID
     * @return 履歴データ
     */
    protected List<SelectEntity> selectAndMergeHistory(String businessNumber, String maxFallRangeId) {
        List<SelectEntity> histories = selectMapper.selectAllHistory(businessNumber, maxFallRangeId);

        // 履歴取得（N件）
        SwimMaxFallRangeHistoryEntityExample example = new SwimMaxFallRangeHistoryEntityExample();
        example.createCriteria().andBusinessNumberEqualTo(businessNumber).andMaxFallRangeIdEqualTo(maxFallRangeId);
        List<SwimMaxFallRangeHistoryEntity> historyList = swimMaxFallRangeHistoryRepository.selectByExample(example);

        // フィードバック取得（M件）
        SwimFeedbackEntityExample exFeed = new SwimFeedbackEntityExample();
        exFeed.createCriteria().andBusinessNumberEqualTo(businessNumber).andMaxFallRangeIdEqualTo(maxFallRangeId);
        List<SwimFeedbackEntity> feedbackList = swimFeedbackRepository.selectByExample(exFeed);

        // フィードバック結果とマージ
        for (SelectEntity e : histories) {
            int idx = e.getHistoryIndex();

            SwimMaxFallRangeHistoryEntity history = historyList.stream()
                .filter(k -> k.getHistoryIndex() == idx)
                .findFirst()
                .orElseThrow();
            e.setHistory(history);

            SwimFeedbackEntity feed = feedbackList.stream()
                .filter(k -> k.getHistoryIndex() == idx)
                .findFirst()
                .orElseThrow();
            e.setSwimFeedback(feed);
        }

        return histories;
    }

    /**
     * 仮ID生成
     * 
     * @param prefix
     * @return 仮ID
     */
    protected String getTemporaryId(String prefix) {
        return prefix + fixedTemporaryId;
    }

    /**
     * シートごとに出力する値をAirspaceオブジェクト(空域情報）に変換
     * 
     * @param featureId FeatureID(仮ID
     * @param timeSliceId TimeSliceID(仮ID
     * @param designator 航路ID
     * @param coordinates ポリゴン情報
     * @param upper 上限高度[M]
     * @return airspace 空域情報
     */
    protected Airspace convertAirSpace(String featureId, String timeSliceId, String designator,
        List<List<List<Double>>> coordinates, Double upper) {

        Airspace airspace = new Airspace();

        String objectIdPrefix = "ObjectID_";

        String objectId10 = objectIdPrefix + getTemporaryId("10"); // Object10 -> Object13.parObjectID
        String objectId13 = objectIdPrefix + getTemporaryId("13"); // Object13
        String objectId24 = objectIdPrefix + getTemporaryId("24"); // Object24 -> Object38.parObjectID
        String objectId29 = objectIdPrefix + getTemporaryId("29"); // Object29
        String objectId38 = objectIdPrefix + getTemporaryId("38"); // Object38 -> Object29.parObjectID

        // UTC
        String beginPosition = this.createUTCString(0);

        airspace.setObject13(getObject13(featureId, timeSliceId, objectId10, objectId13));

        airspace.setObject29(getObject29(featureId, timeSliceId, objectId38, objectId29, coordinates));

        Integer horizontalProjectionIdx = airspace.getObject29().size(); // Object29シートオブジェクト数
        airspace.setObject38(
            getObject38(featureId, timeSliceId, objectId24, objectId38, upper, horizontalProjectionIdx));

        Integer timeIntervalIdx = airspace.getObject13().size(); // Object13のオブジェクト数
        airspace.setObject10(getObject10(featureId, timeSliceId, objectId10, timeIntervalIdx));

        Integer airSpaceVolumeIdx = airspace.getObject38().size(); // Object38に入力したオブジェクト数
        airspace.setObject24(getObject24(featureId, timeSliceId, objectId24, airSpaceVolumeIdx));

        Integer activationIdx = airspace.getObject10().size(); // Object10のオブジェクト数
        Integer geometryComponentIdx = airspace.getObject24().size(); // Object24のオブジェクト数
        airspace.setFeatures(
            getFeatures(featureId, timeSliceId, beginPosition, null, designator, activationIdx, geometryComponentIdx));

        return airspace;
    }

    /**
     * 現在日時(UTC）に指定された日数を加算した文字列を返却
     * 
     * @param addDay 加算日数
     * @return 日時文字列(UTC）
     */
    protected String createUTCString(int addDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        // UTC
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime ret = now.plusDays(addDay);
        return ret.format(formatter);
    }

    /**
     * DBのSWIM_MAXFALL_RANGE_HISTORY（履歴）テーブルに格納し、格納した履歴番号を返却
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @param businessNumber 事業者番号
     * @param airspace 航路情報
     * @return DB格納した履歴番号
     */
    // ==== 履歴テーブルに保存
    protected Integer saveHistory(String maxFallRangeId, String businessNumber, Airspace airspace) {
        // 新規の時は原理的に1行
        Integer nextIndex = getNextHistoryIndex(maxFallRangeId, businessNumber);
        log.debug("==== history nextIndex: " + nextIndex);
        SwimMaxFallRangeHistoryEntity.SwimMaxFallRangeHistoryEntityBuilder builder = SwimMaxFallRangeHistoryEntity
            .builder();

        builder.businessNumber(businessNumber).maxFallRangeId(maxFallRangeId).historyIndex(nextIndex);

        applyFeature(builder, airspace.getFeatures().get(0));
        applyObject10(builder, airspace.getObject10().get(0));
        applyObject13(builder, airspace.getObject13().get(0));
        applyObject24(builder, airspace.getObject24().get(0));
        applyObject29(builder, airspace.getObject29().get(0));
        applyObject38(builder, airspace.getObject38().get(0));

        SwimMaxFallRangeHistoryEntity entity = builder.build();

        swimMaxFallRangeHistoryRepository.insertSelective(entity);

        return nextIndex;
    }

    private Integer getNextHistoryIndex(String maxFallRangeId, String businessNumber) {

        SelectEntity entity = selectMapper.selectMax(businessNumber, maxFallRangeId);

        Integer currentMax = entity == null ? null : entity.getHistoryIndex();

        return (currentMax == null) ? 1 : currentMax + 1;
    }

    private void applyFeature(SwimMaxFallRangeHistoryEntity.SwimMaxFallRangeHistoryEntityBuilder builder,
        Feature feature) {
        builder.featureId(feature.getColumnFeatureID())
            .featureTimesliceid(feature.getColumnTimeSliceID())
            .featureBeginPosition(feature.getColumnBeginPosition())
            .featureEndPosition(feature.getColumnEndPosition())
            .featureDesignator(feature.getColumnDesignator())
            .featureActivationIndex(feature.getColumnOActivation())
            .featureGeometryComponentIndex(feature.getColumnOGeometryComponent());
    }

    private void applyObject10(SwimMaxFallRangeHistoryEntity.SwimMaxFallRangeHistoryEntityBuilder builder,
        Object10 obj10) {
        builder.object10Objid(obj10.getColumnObjectID()).object10TimeintervalIndex(obj10.getColumnOTimeInterval());
    }

    private void applyObject13(SwimMaxFallRangeHistoryEntity.SwimMaxFallRangeHistoryEntityBuilder builder,
        Object13 obj13) {
        builder.object13ParObjid(obj13.getColumnParObjectID()).object13Objid(obj13.getColumnObjectID());
    }

    private void applyObject24(SwimMaxFallRangeHistoryEntity.SwimMaxFallRangeHistoryEntityBuilder builder,
        Object24 obj24) {
        builder.object24Objid(obj24.getColumnObjectID())
            .object24AirSpaceVolumeIndex(obj24.getColumnOTheAirspaceVolume());
    }

    private void applyObject29(SwimMaxFallRangeHistoryEntity.SwimMaxFallRangeHistoryEntityBuilder builder,
        Object29 obj29) {
        builder.object29ParObjid(obj29.getColumnParObjectID())
            .object29Objid(obj29.getColumnObjectID())
            .object29Gml(obj29.getColumnGML());
    }

    private void applyObject38(SwimMaxFallRangeHistoryEntity.SwimMaxFallRangeHistoryEntityBuilder builder,
        Object38 obj38) {
        builder.object38ParObjid(obj38.getColumnParObjectID())
            .object38Objid(obj38.getColumnObjectID())
            .object38Upperlimit(obj38.getColumnUpperLimit())
            .object38HorizontalProjectionIndex(obj38.getColumnOHorizontalProjection());
    }

    // ==== ExcelCreatorに渡すPOJOに変換
    /**
     * シートFeature: Airspace
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param beginPosition 有効開始日時
     * @param endPosition 終了予定日時
     * @param designator 航路ID
     * @param activationIdx Object10のオブジェクト数
     * @param geometryComponentIdx Object24のオブジェクト数
     * @return feature 変換後オブジェクト
     */
    private List<Feature> getFeatures(String featureId, String timeSliceId, String beginPosition, String endPosition,
        String designator, Integer activationIdx, Integer geometryComponentIdx) {
        List<Feature> objects = new ArrayList<>();
        Feature feature = featureHelper.convert(
            featureId,
            timeSliceId,
            beginPosition,
            endPosition,
            designator,
            activationIdx,
            geometryComponentIdx);
        objects.add(feature);
        return objects;
    }

    /**
     * シートObject10: Airspace/activation
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param objectId 本シート用仮ID(ObjectID_10000X)
     * @param timeIntervalIdx Object13のオブジェクト数
     * @return Object10 変換後オブジェクト
     */
    private List<Object10> getObject10(String featureId, String timeSliceId, String objectId, Integer timeIntervalIdx) {
        List<Object10> objects = new ArrayList<>();
        Object10 object10 = object10Helper.convert(featureId, timeSliceId, objectId, timeIntervalIdx);
        objects.add(object10);
        return objects;
    }

    /**
     * シートObject13: Airspace/activation/timeInterval
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param perObjectId Object10で設定したObjectID
     * @param objectId 本シート用仮ID(ObjectID_13000X)
     * @return Object13 変換後オブジェクト
     */
    private List<Object13> getObject13(String featureId, String timeSliceId, String perObjectId, String objectId) {
        List<Object13> objects = new ArrayList<>();
        Object13 object13 = object13Helper.convert(featureId, timeSliceId, perObjectId, objectId);
        objects.add(object13);
        return objects;
    }

    /**
     * シートObject24: Airspace/geometryComponent
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param objectId 本シート用仮ID（ObjectID_24000X)
     * @param airSpaceVolumeIdx Object38に入力したオブジェクト数
     * @return Object24 変換後オブジェクト
     */
    private List<Object24> getObject24(String featureId, String timeSliceId, String objectId,
        Integer airSpaceVolumeIdx) {
        List<Object24> objects = new ArrayList<>();
        Object24 object24 = object24Helper.convert(featureId, timeSliceId, objectId, airSpaceVolumeIdx);
        objects.add(object24);
        return objects;
    }

    /**
     * シートObject29: Airspace/geometryComponent/theAirspaceVolume/horizontalProjection
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param parObjectID Object38に設定したObjectID
     * @param objectId 本シート用仮ID(ObjectID_29000X)
     * @param coordinates ポリゴン座標リスト
     * @return Object29 変換後オブジェクト
     */
    private List<Object29> getObject29(String featureId, String timeSliceId, String parObjectID, String objectId,
        List<List<List<Double>>> coordinates) {
        List<Object29> objects = new ArrayList<>();
        Object29 object29 = object29Helper.convert(featureId, timeSliceId, parObjectID, objectId, coordinates);
        objects.add(object29);
        return objects;
    }

    /**
     * シートObject38: Airspace/geometryComponent/theAirspaceVolume
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param parObjectID Object24で設定した仮ID(ObjectID_24000X)
     * @param objectId 本シート用仮ID(ObjectID_38000X)
     * @param upper 上限高度[M]
     * @param horizontalProjectionIdx Object29シートオブジェクト数
     * @return Object38 変換後オブジェクト
     */
    private List<Object38> getObject38(String featureId, String timeSliceId, String parObjectID, String objectId,
        double upper, Integer horizontalProjectionIdx) {
        List<Object38> objects = new ArrayList<>();
        Object38 object38 = object38Helper.convert(
            featureId,
            timeSliceId,
            parObjectID,
            objectId,
            upper,
            horizontalProjectionIdx);
        objects.add(object38);
        return objects;
    }
}
