package jp.go.meti.drone.swim.service.convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.excel.dto.Object10;
import lombok.extern.slf4j.Slf4j;

/**
 * Object10ヘルパー
 */
@Slf4j
@Service
public class Object10Helper {

    // fixed values
    @Value("${fixedValues.object10.activity}")
    private String columnActivity;

    @Value("${fixedValues.object10.status}")
    private String columnStatus;

    /**
     * シートObject10: Airspace/activation
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param objectId 本シート用仮ID(ObjectID_10000X)
     * @param timeIntervalIdx Object13のオブジェクト数
     * @return Object10変換後オブジェクト
     */
    public Object10 convert(String featureId, String timeSliceId, String objectId, Integer timeIntervalIdx) {

        Object10 object10 = new Object10();

        object10.setColumnFeatureID(featureId);
        object10.setColumnTimeSliceID(timeSliceId);
        object10.setColumnObjectID(objectId);
        object10.setColumnActivity(columnActivity);
        object10.setColumnStatus(columnStatus);
        object10.setColumnOAircraft(null);
        object10.setColumnOLevels(null);
        object10.setColumnOAnnotation(null);
        object10.setColumnOTimeInterval(timeIntervalIdx);
        object10.setColumnFUser(null);
        object10.setColumnFSpecialDateAuthority(null);

        return object10;
    }
}
