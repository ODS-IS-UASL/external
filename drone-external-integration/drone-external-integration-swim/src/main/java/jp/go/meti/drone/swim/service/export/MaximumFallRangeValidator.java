package jp.go.meti.drone.swim.service.export;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.util.CoordinateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 座標系バリデーター
 */
@Slf4j
@Service
public class MaximumFallRangeValidator {

    /**
     * 日本最南端の緯度
     */
    @Value("${export.common.min_latitude}")
    private double minLatitude;

    /**
     * 日本最北端の緯度
     */
    @Value("${export.common.max_latitude}")
    private double maxLatitude;

    /**
     * 日本最西端の経度
     */
    @Value("${export.common.min_longitude}")
    private double minLongitude;

    /**
     * 日本最東端の経度
     */
    @Value("${export.common.max_longitude}")
    private double maxLongitude;

    private CoordinateUtil coordinateUtil;

    /**
     * コンストラクタ
     * 
     * @param coordinateUtil APIから取得したポリゴン座標のUtil
     */
    public MaximumFallRangeValidator(CoordinateUtil coordinateUtil) {
        this.coordinateUtil = coordinateUtil;
    }

    /**
     * 日本国内(private doubleで指定した緯度経度の範囲内)であるか
     * 
     * @param coordinates APIから取得したポリゴンの座標
     * @return true すべての座標が日本国内である false coordinatesがnullもしくは空欄である場合
     * @throws CommonResponseInternalServerError 日本国外の座標が1つ以上ある場合
     */
    public boolean isPolygonWithinJapan(List<List<List<Double>>> coordinates) throws CommonResponseInternalServerError {
        if (coordinates == null || coordinates.isEmpty())
            return false;

        for (List<List<Double>> ring : coordinates) {
            for (List<Double> point : ring) {
                Map<String, Double> latLng = coordinateUtil.getLatLng(point);
                // 範囲外の点が一つでもあれば即座にfalse
                if (latLng.get("lat") < minLatitude || latLng.get("lat") > maxLatitude || latLng.get(
                    "lon") < minLongitude || latLng.get("lon") > maxLongitude) {
                    // coordinates is NOT in Japan.
                    throw new CommonResponseInternalServerError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "日本国外の座標です。");
                }
            }
        }
        return true;
    }

    /**
     * ポリゴンの始点と終点が一致しているか
     * 
     * @param coordinates APIから取得したポリゴンの座標
     * @return true ポリゴンの始点と終点が一致している false coordinatesがnullもしくは空欄である場合
     * @throws CommonResponseInternalServerError ポリゴンを形成する点が4点より少ない場合/ポリゴンの始点と終点が不一致の場合
     */
    public boolean isPolygonClosed(List<List<List<Double>>> coordinates) throws CommonResponseInternalServerError {
        if (coordinates == null || coordinates.isEmpty()) {
            return false;
        }

        for (List<List<Double>> ring : coordinates) {
            if (ring == null || ring.size() < 4) {
                // 最低4点
                throw new CommonResponseInternalServerError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "不正な座標です。");
            }

            List<Double> firstPoint = ring.get(0);
            List<Double> lastPoint = ring.get(ring.size() - 1);

            // 始点と終点が違う場合
            if (!firstPoint.get(0).equals(lastPoint.get(0)) || !firstPoint.get(1).equals(lastPoint.get(1))) {
                // coordinates is NOT closed.
                throw new CommonResponseInternalServerError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "不正な座標です。");

            }
        }
        return true;
    }
}
