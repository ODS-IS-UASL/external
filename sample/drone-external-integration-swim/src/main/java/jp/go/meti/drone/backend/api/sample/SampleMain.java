package jp.go.meti.drone.backend.api.sample;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.ConvexHull2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.ConvexHullGenerator2D;
import org.apache.commons.math3.geometry.euclidean.twod.hull.MonotoneChain;

import aero.aixm.AbstractAIXMFeatureType;
import aero.aixm.AirspaceType;
import jp.go.meti.drone.batch.swim.entity.inner.GeometryInfo;
import jp.go.meti.drone.batch.swim.service.aixm.AixmUtil;
import jp.go.meti.drone.batch.swim.service.aixm.airspace.AirSpaceConverter;

/**
 * SpringBootアプリケーション起動メインクラス。
 */

public class SampleMain {
    /**
     * UUIDで後述のairspaceIdは設定
     */
    private static final UUID airSpaceId_uuid = UUID.fromString("ba0e233c-fcfa-40ca-be80-566079d8ac33");

    /**
     * AIXM-JAXBライブラリを扱うためのユーティリティクラス
     */
    static AixmUtil aixmUtil = new AixmUtil();

    /**
     * データ変換処理サンプルメイン
     *
     * @param args ※未使用
     */
    public static void main(String[] args) {
        List<GeometryInfo> geometryInfos = new ArrayList<>();

        // ********************************
        // データ変換に際して元となるデータを指定
        // ********************************
        /** 航路全体のUUID（航路ごとに一意） */
        String airSpaceId = airSpaceId_uuid.toString();
        /** 空域短縮名（半角英数最大10文字：航路ごとに一意） */
        String designator = "XXXXXXXXXX";
        /** AirSpaceタグのシーケンス番号:1固定 */
        Long sequenceNo = 1L;

        /**
         * 航路区画を構成する（緯度、経度、高度）×8点 .<br>
         * NOTE: 緯度経度の順序はAIXM側仕様に合わせているためデータ準備の際に留意
         */
        List<List<Double>> startCoordinates = new ArrayList<>();
        startCoordinates.add(new ArrayList<>(Arrays.asList(35.95812879850878, 139.0433246931407, 340.21)));
        startCoordinates.add(new ArrayList<>(Arrays.asList(35.96032650000503, 139.04334121705364, 340.21)));
        startCoordinates.add(new ArrayList<>(Arrays.asList(35.96034248003291, 139.0468646931407, 350.21)));
        startCoordinates.add(new ArrayList<>(Arrays.asList(35.95812992764329, 139.0468887832604, 350.21)));
        List<List<Double>> endCoordinates = new ArrayList<>();
        endCoordinates.add(new ArrayList<>(Arrays.asList(35.95865718362831, 139.04662431676804, 322.21)));
        endCoordinates.add(new ArrayList<>(Arrays.asList(35.95977546103831, 139.04667763396415, 322.21)));
        endCoordinates.add(new ArrayList<>(Arrays.asList(35.95977633283442, 139.04991766542219, 360.00)));
        endCoordinates.add(new ArrayList<>(Arrays.asList(35.95878719992218, 139.04992431676804, 360.00)));

        // 天頂方向からみた凸多角形ポリゴンへ変換
        geometryInfos = extractCoordinateToEntity(startCoordinates, endCoordinates, airSpaceId, designator);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
        /** AIPデータの有効期間開始日 */
        Date sDate = null;
        try {
            sDate = sdf.parse("2025/02/17 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /** AIPデータの有効期間終了日 */
        Date eDate = null;
        try {
            eDate = sdf.parse("2025/03/17 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // AIXMオブジェクトに変換後、文字列化
        List<JAXBElement<? extends AbstractAIXMFeatureType>> result = AirSpaceConverter.createElementList();
        JAXBElement<AirspaceType> convResult = null;
        convResult = AirSpaceConverter.createAipAirSpaceElementWithActivation(
            airSpaceId, // id AirspaceタグのUUID
            sequenceNo, // sequenceNo シーケンス番号
            sDate, // beginDate ドローン航路の有効期間の開始日を指定する
            eDate, // endDate ドローン航路の有効期間の終了日を指定する（nullの場合unknown）
            designator, // designator 空域短縮名
            "BASELINE", // interpretation データの解釈方法の指定
                        // BASELINE：基本的な情報提供の指定になる。
                        // PERMDELTA
            geometryInfos // geometryInfos 航路の座標情報
        );
        result.add(convResult);

        // AIXM文字列を出力
        try {
            String aixm = AirSpaceConverter.convertAixmElementToString(result);
            System.out.println(aixm);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 航路区画を構成する2座標（緯度経度8点）を天頂方向からみた凸多角形ポリゴンへ変換
     * 
     * @param startCoordinates 航路区画始点情報(緯度、経度、高度)のリスト4点
     * @param endCoordinates 航路区画終点情報(緯度、経度、高度)のリスト4点
     * @param airSpaceId 航路全体のUUID
     * @param designator 空域短縮名
     * @return リスト<凸多角形ポリゴン座標情報を保持するgeometryInfoクラス>
     */
    private static List<GeometryInfo> extractCoordinateToEntity(List<List<Double>> startCoordinates,
        List<List<Double>> endCoordinates, String airSpaceId, String designator) {
        /**
         * 引数(航路区画の始点、終点)から航路の下限高度と上限高度を抽出
         */
        Double lowerLimit = startCoordinates.get(0).get(2);
        Double upperLimit = startCoordinates.get(0).get(2);

        for (List<Double> point : startCoordinates) {
            double checkPoint = point.get(2);
            lowerLimit = Math.min(lowerLimit, checkPoint);
            upperLimit = Math.max(upperLimit, checkPoint);
        }
        for (List<Double> point : endCoordinates) {
            if (point.size() >= 3) {
                double checkPoint = point.get(2);
                lowerLimit = Math.min(lowerLimit, checkPoint);
                upperLimit = Math.max(upperLimit, checkPoint);
            }
        }

        /**
         * 引数(航路区画の始点、終点)から航路区画を構成する緯度経度8点を抽出
         */
        Set<List<Double>> pointSet = new LinkedHashSet<>();
        for (List<Double> point : startCoordinates) {
            List<Double> p = new ArrayList<>();
            p.add(point.get(0));
            p.add(point.get(1));
            pointSet.add(p);
        }
        for (List<Double> point : endCoordinates) {
            List<Double> p = new ArrayList<>();
            p.add(point.get(0));
            p.add(point.get(1));
            pointSet.add(p);
        }

        // 抽出した緯度経度8点から凸多角形ポリゴンへ変換
        GeometryInfo geometryInfo = null;
        try {
            geometryInfo = createGeometryInfo(
                pointSet,
                airSpaceId, // airSpaceId
                designator, // designator
                lowerLimit,
                upperLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<GeometryInfo> result = new ArrayList<>();
        result.add(geometryInfo);
        return result;
    }

    /**
     * 緯度経度N点(3点以上)を天頂方向から見た凸多角形ポリゴンへ変換
     * 
     * @param pointSet 緯度経度N点（3点以上：緯度、経度の順）
     * @param airSpaceId 航路全体のUUID
     * @param lowerLimit 航路の下限
     * @param upperLimit 航路の上限
     * @return 座標情報を保持するgeometryInfoクラス
     */
    private static GeometryInfo createGeometryInfo(Set<List<Double>> pointSet, String airSpaceId,
        String designator, Double lowerLimit, Double upperLimit) {

        // pointSetに設定された座標セットから対象となる空間の頂点を算出
        Collection<Vector2D> pointsIn = pointSet.stream().map(p -> new Vector2D(p.get(0), p.get(1))).toList();
        ConvexHullGenerator2D gen = new MonotoneChain();
        ConvexHull2D hull = gen.generate(pointsIn);
        Vector2D[] pointsOut = hull.getVertices();

        // 座標数を設定(ポリゴン終端点を含めるため+1)
        Long posCount = pointsOut.length + 1L;

        // posListに座標の緯度、経度を設定
        List<Double> posList = new ArrayList<>();
        for (Vector2D vector : pointsOut) {
            posList.add(vector.getX());
            posList.add(vector.getY());
        }
        posList.add(pointsOut[0].getX());
        posList.add(pointsOut[0].getY());

        // GeometryInfoを生成
        GeometryInfo geometryInfo = new GeometryInfo();
        geometryInfo.setAirSpaceId(airSpaceId);
        geometryInfo.setDesignator(designator);
        if (Objects.nonNull(lowerLimit)) {
            geometryInfo.setLowerLimit(lowerLimit.toString());
        }
        if (Objects.nonNull(upperLimit)) {
            geometryInfo.setUpperLimit(upperLimit.toString());
        }
        geometryInfo.setPosCount(new BigInteger(posCount.toString()));
        geometryInfo.setPosList(posList);

        return geometryInfo;
    }
}
