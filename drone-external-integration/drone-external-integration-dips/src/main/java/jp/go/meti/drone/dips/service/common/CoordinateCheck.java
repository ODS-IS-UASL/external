package jp.go.meti.drone.dips.service.common;

import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 経度、緯度は範囲値チェック サービス共通
 * 
 * @version 1.0 2024/10/31
 */
@Slf4j
public class CoordinateCheck {

    private CoordinateCheck() {
        throw new IllegalStateException("CoordinateCheck class");
    }

    /**
     * 経度の範囲値チェック
     * 
     * @param longitudeTmp チェック必要の経度値
     * @return flag
     */
    public static boolean longitudeCheck(Double longitudeTmp) {
        if (ObjectUtils.isEmpty(longitudeTmp)) {
            return false;
        }
        // 経度、緯度は範囲値と比較
        boolean flag = true;
        if (longitudeTmp > FlightProhibtedConst.MAX_LONGITUDE || longitudeTmp < FlightProhibtedConst.MIN_LONGITUDE) {
            flag = false;
        }
        log.info("経度の範囲値チェック:" + longitudeTmp + "--結果：" + flag);
        return flag;
    }

    /**
     * 緯度の範囲値チェック
     * 
     * @param latitudeTmp チェック必要の緯度値
     * @return flag
     */
    public static boolean latitudeCheck(Double latitudeTmp) {
        if (ObjectUtils.isEmpty(latitudeTmp)) {
            return false;
        }
        // 経度、緯度は範囲値と比較
        boolean flag = true;
        if (latitudeTmp > FlightProhibtedConst.MAX_LATITUDE || latitudeTmp < FlightProhibtedConst.MIN_LATITUDE) {
            flag = false;
        }
        log.info("緯度の範囲値チェック:" + latitudeTmp + "--結果：" + flag);
        return flag;
    }

}
