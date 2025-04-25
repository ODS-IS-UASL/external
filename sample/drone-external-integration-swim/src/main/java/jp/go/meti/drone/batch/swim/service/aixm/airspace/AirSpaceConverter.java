package jp.go.meti.drone.batch.swim.service.aixm.airspace;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import aero.aixm.AbstractAIXMFeatureType;
import aero.aixm.AirspaceActivationPropertyType;
import aero.aixm.AirspaceActivationType;
import aero.aixm.AirspaceGeometryComponentPropertyType;
import aero.aixm.AirspaceGeometryComponentType;
import aero.aixm.AirspaceTimeSlicePropertyType;
import aero.aixm.AirspaceTimeSliceType;
import aero.aixm.AirspaceType;
import aero.aixm.AirspaceVolumePropertyType;
import aero.aixm.AirspaceVolumeType;
import aero.aixm.CodeAirspaceActivityType;
import aero.aixm.CodeAirspaceDesignatorType;
import aero.aixm.CodeAirspaceType;
import aero.aixm.CodeDayType;
import aero.aixm.CodeStatusAirspaceType;
import aero.aixm.CodeVerticalReferenceType;
import aero.aixm.SurfacePropertyType;
import aero.aixm.SurfaceType;
import aero.aixm.TextNameType;
import aero.aixm.TimeType;
import aero.aixm.TimesheetPropertyType;
import aero.aixm.TimesheetType;
import aero.aixm.ValDistanceVerticalType;
import jp.go.meti.drone.batch.swim.entity.inner.GeometryInfo;
import jp.go.meti.drone.batch.swim.service.aixm.AixmUtil;
import net.opengis.gml.AbstractRingPropertyType;
import net.opengis.gml.CodeWithAuthorityType;
import net.opengis.gml.CurvePropertyType;
import net.opengis.gml.CurveSegmentArrayPropertyType;
import net.opengis.gml.CurveType;
import net.opengis.gml.DirectPositionListType;
import net.opengis.gml.GeodesicStringType;
import net.opengis.gml.PolygonPatchType;
import net.opengis.gml.RingType;
import net.opengis.gml.SurfacePatchArrayPropertyType;
import net.opengis.gml.TimeIndeterminateValueType;
import net.opengis.gml.TimePositionType;
import net.opengis.gml.TimePrimitivePropertyType;

/**
 * Airspaceタグを使用したAIXMデータのコンバートクラス
 *
 * @version 1.0 2024/09/13
 */
public class AirSpaceConverter { // NOSONAR

    /**
     * AirSpaceActivationタグのActivity指定（無人航空機）
     */
    private static final String CODE_AIRSPACE_ACTIVE_TYPE = "UAV";

    /**
     * AirSpaceタグのLocalType指定（空域の種別 無人航空システムの運用エリアを指定）
     */
    private static final String CODE_AIRSPACE_LOCAL_TYPE = "UAS_OPERATING_AREA";

    /**
     * AirSpaceタグのType指定（空域の種別 危険な性質の活動(危険区域を除く)を指定）
     */
    private static final String CODE_AIRSPACE_TYPE = "D_OTHER";

    /**
     * LowerLimitReference/UpperLimitReferenceのデフォルト値
     */
    private static final String CODE_VERTICAL_REFERENCE_TYPE = "MSL";

    /**
     * 座標参照系（CRS: Coordinate Reference System）：WGS84
     */
    private static final String CURVE_TYPE_SRC_NAME = "urn:ogc:def:crs:EPSG::4326";

    /**
     * データの解釈方法の指定のデフォルト値
     */
    private static final String INTERPRETATION_DEFAULT = "BASELINE";

    /**
     *
     */
    private static final String TIME_SLICE_01 = "TimeSlice_01_";

    /**
     * TimesheetタグのDay指定(ANY)
     */
    private static final String TIMESHEET_DAY = "ANY";

    /**
     * TimesheetタグのendTime指定(00:00)
     */
    private static final String TIMESHEET_END_TIME = "00:00";

    /**
     * TimesheetタグのstartTime指定(00:00)
     */
    private static final String TIMESHEET_START_TIME = "00:00";

    /**
     * UUID用CodeSpace指定
     */
    private static final String URN_UUID = "urn:uuid:";

    /**
     * UUID用プレフィックス
     */
    private static final String UUID_DOT = "uuid.";

    /**
     * 日時フォーマット指定
     */
    private static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * AIXM-JAXBライブラリを扱うためのユーティリティクラス
     */
    static AixmUtil aixmUtil = new AixmUtil();

    /**
     * 引数で受け取った要素リストからAIXMを生成して返す
     *
     * @param elements AIXMの要素リスト
     * @return 生成したAIXMのボディー
     * @throws JAXBException XMLの生成に失敗した場合のエラー
     */
    public static String convertAixmElementToString(List<JAXBElement<? extends AbstractAIXMFeatureType>> elements)
        throws JAXBException {
        return aixmUtil.createAixmData(elements);
    }

    /**
     * AirspaceタグのJAXBElementを生成する
     *
     * @param id AirspaceタグのID
     * @param sequenceNo シーケンス番号
     * @param beginDate ドローン航路の有効期間の開始日を指定する
     * @param endDate ドローン航路の有効期間の終了日を指定する（nullの場合unknown）
     * @param designator 空域短縮名（英数字からなる一位の識別名を指定）
     * @param interpretation データの解釈方法の指定（nullの場合、BASELINE：基本的な情報提供の指定になる。）
     * @param geometryInfos 空域を表現する凸多角形ポリゴン座標情報
     * @return 生成されたAirspaceタグのElement
     */
    public static JAXBElement<AirspaceType> convertElementForGeo(String id, Long sequenceNo, Date beginDate,        // NOSONAR
        Date endDate, // NOSONAR
        String designator, String interpretation, List<GeometryInfo> geometryInfos) {
        // AirspaceTypeを生成
        String idStr = UUID_DOT + id;
        AirspaceType airspace = aixmUtil.createAirspaceType();
        airspace.setId(idStr);

        // 識別用のタグを生成
        CodeWithAuthorityType idValue = new CodeWithAuthorityType();
        idValue.setCodeSpace(URN_UUID);
        idValue.setValue(id);
        airspace.setIdentifier(idValue);

        // AirspaceのTimeSliceeを生成
        AirspaceTimeSlicePropertyType asTimeSliceP = aixmUtil.createAirspaceTimeSlicePropertyType();
        AirspaceTimeSliceType asTimeSlice = aixmUtil.createAirspaceTimeSliceType();
        asTimeSlice.setId(TIME_SLICE_01 + id);

        // ValidTimeとfeatureLifeTimeに有効期間を設定
        if (Objects.nonNull(beginDate) || Objects.nonNull(endDate)) {
            TimePrimitivePropertyType validTimeP = createValidTime(beginDate, endDate);
            TimePrimitivePropertyType featureLifetimeP = createValidTime(beginDate, endDate);
            asTimeSlice.setValidTime(validTimeP);
            asTimeSlice.setFeatureLifetime(featureLifetimeP);
        }

        // Interpretation データの登録なのでBASELINEを指定
        asTimeSlice.setInterpretation(Objects.isNull(interpretation) ? INTERPRETATION_DEFAULT : interpretation);
        asTimeSlice.setSequenceNumber(sequenceNo);

        // Typeに「危険な性質の活動(危険区域以外)」を指定
        CodeAirspaceType type = aixmUtil.getElementFactory().createCodeAirspaceType();
        type.setValue(CODE_AIRSPACE_TYPE);
        asTimeSlice.setType(aixmUtil.getElementFactory().createAirspaceTimeSliceTypeType(type));

        // LocalTypeに「無人航空システムの運用エリア」を指定
        TextNameType localType = aixmUtil.getElementFactory().createTextNameType();
        localType.setValue(CODE_AIRSPACE_LOCAL_TYPE);
        asTimeSlice.setLocalType(aixmUtil.getElementFactory().createAirspaceTimeSliceTypeLocalType(localType));

        // designatorに空域短縮名を設定
        CodeAirspaceDesignatorType designatorType = aixmUtil.getElementFactory().createCodeAirspaceDesignatorType();
        designatorType.setValue(designator);
        asTimeSlice.setDesignator(aixmUtil.getElementFactory().createAirspaceTimeSliceTypeDesignator(designatorType));

        // AirspaceのGeometryComponentを生成
        for (GeometryInfo geometryinfo : geometryInfos) {
            AirspaceGeometryComponentPropertyType geoP = createGeometryComponent(
                geometryinfo.getLowerLimit(),
                geometryinfo.getUpperLimit(),
                geometryinfo.getPosCount(),
                geometryinfo.getPosList());
            // XMLの各要素を設定
            asTimeSlice.getGeometryComponent().add(geoP);
        }

        asTimeSliceP.setAirspaceTimeSlice(asTimeSlice);
        airspace.getTimeSlice().add(asTimeSliceP);

        return aixmUtil.createAirspace(airspace);
    }

    /**
     * AIP用のAirspaceタグのJAXBElementを生成する
     *
     * @param id AirspaceタグのID
     * @param sequenceNo シーケンス番号
     * @param beginDate ドローン航路の有効期間の開始日を指定する
     * @param endDate ドローン航路の有効期間の終了日を指定する（nullの場合unknown）
     * @param designator 空域短縮名（英数字からなる一位の識別名を指定）
     * @param interpretation データの解釈方法の指定（nullの場合、BASELINE：基本的な情報提供の指定になる。）
     * @param geometryInfos 空域を表現する凸多角形ポリゴン座標情報
     * @return 生成されたAirspaceタグのElement
     */
    public static JAXBElement<AirspaceType> createAipAirSpaceElementWithActivation(String id, Long sequenceNo,
        Date beginDate, Date endDate, String designator, String interpretation, List<GeometryInfo> geometryInfos) {
        JAXBElement<AirspaceType> airSpaceElement = convertElementForGeo(
            id,
            sequenceNo,
            beginDate,
            endDate,
            designator,
            interpretation,
            geometryInfos);

        // Activationタグの設定
        AirspaceActivationPropertyType activationp = aixmUtil.getElementFactory()
            .createAirspaceActivationPropertyType();
        AirspaceActivationType activation = aixmUtil.getElementFactory().createAirspaceActivationType();
        UUID activationId = UUID.randomUUID();
        activation.setId(UUID_DOT + activationId.toString());

        // DELETE以外の時、activityタグ、timeIntervalタグを設定
        if (Objects.isNull(endDate)) {
            // activityタグの設定
            CodeAirspaceActivityType actValue = aixmUtil.getElementFactory().createCodeAirspaceActivityType();
            actValue.setValue(CODE_AIRSPACE_ACTIVE_TYPE);
            activation.setActivity(aixmUtil.getElementFactory().createAirspaceActivationTypeActivity(actValue));

            // timeIntervalタグの設定
            TimesheetPropertyType timesheetPropertyType = aixmUtil.getElementFactory().createTimesheetPropertyType();
            TimesheetType timesheetType = aixmUtil.getElementFactory().createTimesheetType();
            UUID timesheetId = UUID.randomUUID();
            timesheetType.setId(UUID_DOT + timesheetId.toString());
            CodeDayType dayType = aixmUtil.getElementFactory().createCodeDayType();
            TimeType startTimeType = aixmUtil.getElementFactory().createTimeType();
            TimeType endTimeType = aixmUtil.getElementFactory().createTimeType();
            dayType.setValue(TIMESHEET_DAY);
            startTimeType.setValue(TIMESHEET_START_TIME);
            endTimeType.setValue(TIMESHEET_END_TIME);
            timesheetType.setDay(aixmUtil.getElementFactory().createTimesheetTypeDay(dayType));
            timesheetType.setStartTime(aixmUtil.getElementFactory().createTimesheetTypeStartTime(startTimeType));
            timesheetType.setEndTime(aixmUtil.getElementFactory().createTimesheetTypeEndTime(endTimeType));
            timesheetPropertyType.setTimesheet(timesheetType);
            // XMLの各要素を設定
            activation.getTimeInterval().add(timesheetPropertyType);
        }

        // statusタグの設定
        CodeStatusAirspaceType status = aixmUtil.getElementFactory().createCodeStatusAirspaceType();
        status.setValue(Objects.isNull(endDate) ? "ACTIVE" : "INACTIVE");
        activation.setStatus(aixmUtil.getElementFactory().createAirspaceActivationTypeStatus(status));

        activationp.setAirspaceActivation(activation);
        airSpaceElement.getValue().getTimeSlice().getFirst().getAirspaceTimeSlice().getActivation().add(activationp);

        return airSpaceElement;
    }

    /**
     * AIXMのXMLを生成するためのリストを生成する
     *
     * @return AIXMの要素を格納できるJAXBElementリスト
     */
    public static List<JAXBElement<? extends AbstractAIXMFeatureType>> createElementList() { // NOSONAR
        return new ArrayList<>();
    }

    /**
     * 空域の座標を定義したGeometryComponentのタグを生成する
     *
     * @param lowerLimit 空域の下限を指定（nullの場合、該当のタグ指定が省かれる。）
     * @param upperLimit 空域の上限を指定（nullの場合、該当のタグ指定が省かれる。）
     * @param posCount 航路の座標（緯度経度）の件数 NOTE: 未使用
     * @param posList 航路の座標リスト
     * @return 生成されたGeometryComponentのタグ
     */
    private static AirspaceGeometryComponentPropertyType createGeometryComponent(String lowerLimit, String upperLimit,
        BigInteger posCount, List<Double> posList) {
        // AirspaceのGeometryComponentを生成
        AirspaceGeometryComponentPropertyType geoP = aixmUtil.createAirspaceGeometryComponentPropertyType();
        AirspaceGeometryComponentType geo = aixmUtil.createAirspaceGeometryComponentType();
        UUID geometryid = UUID.randomUUID();
        geo.setId(UUID_DOT + geometryid.toString());

        // AirspaceVolumeTypeを生成
        AirspaceVolumePropertyType airSpaceVolumeP = aixmUtil.createAirspaceVolumePropertyType();
        AirspaceVolumeType airSpaceVolume = aixmUtil.createAirspaceVolumeType();
        UUID volumeid = UUID.randomUUID();
        airSpaceVolume.setId(UUID_DOT + volumeid.toString());

        // SurfaceTypeを生成
        SurfacePropertyType horizontalProjection = aixmUtil.getElementFactory().createSurfacePropertyType();
        SurfaceType surfaceType = aixmUtil.getElementFactory().createSurfaceType();
        SurfacePatchArrayPropertyType patch = aixmUtil.getGmlFactory().createSurfacePatchArrayPropertyType();

        // PolygonPatchを生成
        PolygonPatchType polygon = aixmUtil.getGmlFactory().createPolygonPatchType();
        AbstractRingPropertyType exterior = aixmUtil.getGmlFactory().createAbstractRingPropertyType();

        RingType ringType = aixmUtil.getGmlFactory().createRingType();
        CurvePropertyType curveMember = aixmUtil.getGmlFactory().createCurvePropertyType();
        CurveType curveType = aixmUtil.getGmlFactory().createCurveType();
        UUID curveid = UUID.randomUUID();
        curveType.setId(UUID_DOT + curveid.toString());
        CurveSegmentArrayPropertyType segments = aixmUtil.getGmlFactory().createCurveSegmentArrayPropertyType();
        GeodesicStringType geodesic = aixmUtil.getGmlFactory().createGeodesicStringType();

        // 座標を設定
        DirectPositionListType posListType = aixmUtil.getGmlFactory().createDirectPositionListType();
        posList.forEach(pos -> posListType.getValue().add(pos));
        // 一旦、posListType.setCountは無しにして置く

        geodesic.setPosList(posListType);

        // 生成したタグを親のタグに設定
        segments.getAbstractCurveSegment().add(aixmUtil.getGmlFactory().createGeodesicString(geodesic));
        curveType.setSegments(segments);
        curveMember.setAbstractCurve(aixmUtil.getGmlFactory().createCurve(curveType));
        ringType.getCurveMember().add(curveMember);
        exterior.setAbstractRing(aixmUtil.getGmlFactory().createRing(ringType));

        polygon.setExterior(exterior);
        patch.getAbstractSurfacePatch().add(aixmUtil.getGmlFactory().createPolygonPatch(polygon));
        surfaceType.setPatches(aixmUtil.getGmlFactory().createPatches(patch));

        // srcNameとIDを設定
        surfaceType.setSrsName(CURVE_TYPE_SRC_NAME);
        UUID surfaceid = UUID.randomUUID();
        surfaceType.setId(UUID_DOT + surfaceid.toString());
        horizontalProjection.setSurface(aixmUtil.getElementFactory().createSurface(surfaceType));

        if (Objects.nonNull(lowerLimit)) {
            // lowerLimitを設定
            ValDistanceVerticalType lowerLimitType = aixmUtil.getElementFactory().createValDistanceVerticalType();
            lowerLimitType.setUom("M");
            lowerLimitType.setValue(lowerLimit);
            airSpaceVolume.setLowerLimit(
                aixmUtil.getElementFactory().createAirspaceVolumeTypeLowerLimit(lowerLimitType));
            CodeVerticalReferenceType lowerLimitRefType = aixmUtil.getElementFactory()
                .createCodeVerticalReferenceType();
            lowerLimitRefType.setValue(CODE_VERTICAL_REFERENCE_TYPE);
            airSpaceVolume.setLowerLimitReference(
                aixmUtil.getElementFactory().createAirspaceLayerTypeLowerLimitReference(lowerLimitRefType));
        }

        if (Objects.nonNull(upperLimit)) {
            // upperLimitを設定
            ValDistanceVerticalType upperLimitType = aixmUtil.getElementFactory().createValDistanceVerticalType();
            upperLimitType.setUom("M");
            upperLimitType.setValue(upperLimit);
            airSpaceVolume.setUpperLimit(
                aixmUtil.getElementFactory().createAirspaceVolumeTypeUpperLimit(upperLimitType));
            CodeVerticalReferenceType upperLimitRefType = aixmUtil.getElementFactory()
                .createCodeVerticalReferenceType();
            upperLimitRefType.setValue(CODE_VERTICAL_REFERENCE_TYPE);
            airSpaceVolume.setUpperLimitReference(
                aixmUtil.getElementFactory().createAirspaceLayerTypeUpperLimitReference(upperLimitRefType));
        }

        // AirSpaceタグに各要素を設定
        airSpaceVolume.setHorizontalProjection(
            aixmUtil.getElementFactory().createAirspaceVolumeTypeHorizontalProjection(horizontalProjection));
        airSpaceVolumeP.setAirspaceVolume(airSpaceVolume);
        geo.setTheAirspaceVolume(
            aixmUtil.getElementFactory().createAirspaceGeometryComponentTypeTheAirspaceVolume(airSpaceVolumeP));

        // XMLの各要素を設定
        geoP.setAirspaceGeometryComponent(geo);

        return geoP;
    }

    /**
     * 空域の有効期間指定のタグを生成する
     *
     * @param beginDate ドローン航路の有効期間の開始日を指定する（nullの場合unknown）
     * @param endDate ドローン航路の有効期間の終了日を指定する（nullの場合unknown）
     * @return 生成された有効期間指定のタグ
     */
    private static TimePrimitivePropertyType createValidTime(Date beginDate, Date endDate) {
        // ValidTimeとfeatureLifeTimeに有効期間を設定
        TimePrimitivePropertyType validTimeP = aixmUtil.getGmlFactory().createTimePrimitivePropertyType();
        net.opengis.gml.TimePeriodType timePeriod = aixmUtil.getGmlFactory().createTimePeriodType();
        UUID timePeriodid = UUID.randomUUID();
        timePeriod.setId(UUID_DOT + timePeriodid.toString());

        TimePositionType begin = aixmUtil.getGmlFactory().createTimePositionType();
        TimePositionType end = aixmUtil.getGmlFactory().createTimePositionType();
        if (Objects.nonNull(beginDate)) {
            // 有効期間の開始日を設定
            SimpleDateFormat bformat = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS_Z);
            bformat.setTimeZone(TimeZone.getTimeZone("UTC"));
            begin.getValue().add(bformat.format(beginDate));
        } else {
            begin.setIndeterminatePosition(TimeIndeterminateValueType.UNKNOWN);
        }
        if (Objects.nonNull(endDate)) {
            // 有効期間の終了日を設定
            SimpleDateFormat eformat = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS_Z);
            eformat.setTimeZone(TimeZone.getTimeZone("UTC"));
            end.getValue().add(eformat.format(endDate));
        } else {
            end.setIndeterminatePosition(TimeIndeterminateValueType.UNKNOWN);
        }
        timePeriod.setBeginPosition(begin);
        timePeriod.setEndPosition(end);

        validTimeP.setAbstractTimePrimitive(aixmUtil.getGmlFactory().createTimePeriod(timePeriod));
        return validTimeP;
    }
}
