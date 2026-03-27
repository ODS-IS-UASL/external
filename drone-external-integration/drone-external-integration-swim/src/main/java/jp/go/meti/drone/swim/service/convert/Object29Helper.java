package jp.go.meti.drone.swim.service.convert;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.excel.dto.Object29;
import jp.go.meti.drone.swim.util.CoordinateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Object29ヘルパー
 */
@Slf4j
@Service
public class Object29Helper {

    private CoordinateUtil coordinateUtil;

    /**
     * コンストラクタ
     * 
     * @param coordinateUtil APIから取得したポリゴン座標のUtilクラス
     */
    public Object29Helper(CoordinateUtil coordinateUtil) {
        this.coordinateUtil = coordinateUtil;
    }

    /**
     * シートObject29: Airspace/geometryComponent/theAirspaceVolume/horizontalProjection
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param parObjectID Object38に設定したObjectID
     * @param objectId 本シート用仮ID(ObjectID_29000X)
     * @param coordinates ポリゴン座標リスト
     * @return Object29変換後オブジェクト
     * @throws IllegalArgumentException 渡された値が不正だった場合
     */
    public Object29 convert(String featureId, String timeSliceId, String parObjectID, String objectId,
        List<List<List<Double>>> coordinates) throws IllegalArgumentException {
        Object29 object29 = new Object29();
        object29.setColumnFeatureID(featureId);
        object29.setColumnTimeSliceID(timeSliceId);
        object29.setColumnParObjectID(parObjectID);
        object29.setColumnObjectID(objectId);
        object29.setColumnHorizontalAccuracy(null);
        object29.setColumnOAnnotation(null);
        object29.setColumnGML(coordinatesToString(coordinates));

        return object29;
    }

    /**
     * シートObject29を生成し返却
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param parObjectID Object38に設定したObjectID
     * @param objectId 本シート用仮ID(ObjectID_29000X)
     * @param coordinates ポリゴン座標（DB格納値）
     * @return Object29変換後オブジェクト
     */
    public Object29 convertFromHistory(String featureId, String timeSliceId, String parObjectID, String objectId,
        String coordinates) {

        Object29 object29 = new Object29();
        object29.setColumnFeatureID(featureId);
        object29.setColumnTimeSliceID(timeSliceId);
        object29.setColumnParObjectID(parObjectID);
        object29.setColumnObjectID(objectId);
        object29.setColumnHorizontalAccuracy(null);
        object29.setColumnOAnnotation(null);
        object29.setColumnGML(coordinates);

        return object29;
    }

    /**
     * 座標リストを緯度経度の順番で文字列に変換
     * 
     * @param coordinates ポリゴン座標リスト
     * @return 空白を削除され「緯度」「経度」の順番で文字列に変換された座標
     * @throw IllegalArgumentException 引数が不正な場合
     */
    private String coordinatesToString(List<List<List<Double>>> coordinates) throws IllegalArgumentException {

        StringBuilder builder = new StringBuilder();
        for (List<List<Double>> ring : coordinates) {
            for (List<Double> point : ring) {
                Map<String, Double> latLng = coordinateUtil.getLatLng(point);
                builder.append(latLng.get("lat").toString() + " ");
                builder.append(latLng.get("lon").toString() + " ");
            }
        }
        return builder.toString().trim();
    }
}
