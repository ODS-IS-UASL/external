package jp.go.meti.drone.swim.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 座標系ユーティリティ
 */
@Slf4j
@Service
public class CoordinateUtil {

    /**
     * 緯度(longitude)経度(latitude)を正しい順番に変更
     * 
     * @param point 座標の中の１つの緯度経度
     * @return latLng
     */
    public Map<String, Double> getLatLng(List<Double> point) {
        Map<String, Double> latLng = new HashMap<>();
        latLng.put("lon", point.get(0));
        latLng.put("lat", point.get(1));
        return latLng;
    }
}
