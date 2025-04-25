package jp.go.meti.drone.dips.service.area;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ObjectUtils;
import jp.go.meti.drone.dips.apimodel.dips.DroneRouteFlightProhibitedAreaResponse;
import jp.go.meti.drone.dips.model.commonmodel.DroneRouteFlightProhibitedAreaInfo;
import jp.go.meti.drone.dips.model.commonmodel.DroneRouteRange;
import jp.go.meti.drone.dips.service.common.CoordinateCheck;
import jp.go.meti.drone.dips.service.common.DipsDateFomat;
import jp.go.meti.drone.dips.service.common.FlightProhibtedConst;
import lombok.extern.slf4j.Slf4j;

/**
 * DIPS飛行禁止エリア情報チェック
 *
 * @version 1.0 2024/10/31
 */
@Slf4j
public class DipsResponseCheck {

    /**
     * 取得したDIPS飛行禁止エリア情報をチェック
     * <p>
     * DIPS連携情報を取得し、戻り値をチェックする。
     * </p>
     * 
     * @param getDipsResult DIPS飛行禁止エリア情報
     * @return 返答
     */
    public static DroneRouteFlightProhibitedAreaResponse check(DroneRouteFlightProhibitedAreaResponse getDipsResult) {

        boolean flag = true;
        DroneRouteFlightProhibitedAreaResponse droneRouteFlightProhibitedAreaResponse = new DroneRouteFlightProhibitedAreaResponse();
        droneRouteFlightProhibitedAreaResponse.setTotalCount(FlightProhibtedConst.ZERO_INTEGER);
        List<DroneRouteFlightProhibitedAreaInfo> droneRouteFlightProhibitedAreaInfos = new ArrayList<>();

        if (getDipsResult.getTotalCount() > 0) {

            DroneRouteFlightProhibitedAreaInfo droneRouteFlightProhibitedAreaInfo = null;

            for (int i = 0; i < getDipsResult.getTotalCount(); i++) {
                flag = true;
                try {
                    droneRouteFlightProhibitedAreaInfo = getDipsResult.getFlightProhibitedAreaInfo().get(i);

                    String flightProhibitedAreaId = droneRouteFlightProhibitedAreaInfo.getFlightProhibitedAreaId();
                    DroneRouteRange range = droneRouteFlightProhibitedAreaInfo.getRange();
                    String type = droneRouteFlightProhibitedAreaInfo.getRange().getType();
                    Integer typeId = droneRouteFlightProhibitedAreaInfo.getFlightProhibitedAreaTypeId();

                    // 飛行禁止エリアID、飛行禁止範囲、ジオメトリタイプ、飛行禁止エリア種別が空の場合
                    if (ObjectUtils.isEmpty(flightProhibitedAreaId) || ObjectUtils.isEmpty(range) || ObjectUtils
                        .isEmpty(type) || ObjectUtils.isEmpty(typeId)) {
                        continue;
                    }

                    // 日付フォーマット
                    String tmpStart = droneRouteFlightProhibitedAreaInfo.getStartTime();
                    String tmpFinish = droneRouteFlightProhibitedAreaInfo.getFinishTime();
                    getDipsResult.getFlightProhibitedAreaInfo()
                        .get(i)
                        .setStartTime(DipsDateFomat.dipsDateFomat(tmpStart));
                    getDipsResult.getFlightProhibitedAreaInfo()
                        .get(i)
                        .setFinishTime(DipsDateFomat.dipsDateFomat(tmpFinish));

                    // 経度、緯度は範囲値チェック
                    flag = longitudeAndLatitudeCheck(droneRouteFlightProhibitedAreaInfo, flag);
                    if (flag) {
                        // flagはtrueの場合、チェック済みの飛行禁止エリア情報をリストに追加する
                        droneRouteFlightProhibitedAreaInfos.add(droneRouteFlightProhibitedAreaInfo);
                    }
                } catch (Exception e) {
                    log.info(droneRouteFlightProhibitedAreaInfo.getFlightProhibitedAreaId() + ":日付不正です。" + e);
                }

            }
            droneRouteFlightProhibitedAreaResponse.setFlightProhibitedAreaInfo(droneRouteFlightProhibitedAreaInfos);
            droneRouteFlightProhibitedAreaResponse.setTotalCount(droneRouteFlightProhibitedAreaInfos.size());
        }
        return droneRouteFlightProhibitedAreaResponse;

    }

    /**
     * 経度、緯度チェック
     * 
     * @param droneRouteFlightProhibitedAreaInfo 飛行禁止エリア情報
     * @param flag falseの場合除外
     * @return 返答
     */
    private static boolean longitudeAndLatitudeCheck(
        DroneRouteFlightProhibitedAreaInfo droneRouteFlightProhibitedAreaInfo, Boolean flag) {

        String type = droneRouteFlightProhibitedAreaInfo.getRange().getType();
        List<List<Double>> coordinates = droneRouteFlightProhibitedAreaInfo.getRange().getCoordinates();

        if (type.equals(FlightProhibtedConst.TYPE)) {
            // ジオメトリ（構成点）の場合
            for (int j = 0; j < coordinates.size(); j++) {
                List<Double> coordinate = coordinates.get(j);
                Double longitudeTmp = coordinate.get(FlightProhibtedConst.ZERO_INTEGER);
                Double latitudeTmp = coordinate.get(FlightProhibtedConst.ONE_INTEGER);

                if (!CoordinateCheck.longitudeCheck(longitudeTmp) || !CoordinateCheck.latitudeCheck(latitudeTmp)) {
                    flag = false;
                }
            }
            // ジオメトリ（中心点）の場合
        } else if (type.equals(FlightProhibtedConst.TYPE_CIRCLE)) {
            // 半径チェック
            if (ObjectUtils.isEmpty(droneRouteFlightProhibitedAreaInfo.getRange().getRadius())) {
                flag = false;
                return flag;
            }
            Double longitudeTmp = droneRouteFlightProhibitedAreaInfo.getRange()
                .getCenter()
                .get(FlightProhibtedConst.ZERO_INTEGER);
            Double latitudeTmp = droneRouteFlightProhibitedAreaInfo.getRange()
                .getCenter()
                .get(FlightProhibtedConst.ONE_INTEGER);
            if (!CoordinateCheck.longitudeCheck(longitudeTmp) || !CoordinateCheck.latitudeCheck(latitudeTmp)) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

}
