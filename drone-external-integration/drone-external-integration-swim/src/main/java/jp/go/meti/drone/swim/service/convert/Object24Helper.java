package jp.go.meti.drone.swim.service.convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.excel.dto.Object24;
import lombok.extern.slf4j.Slf4j;

/**
 * Object24ヘルパー
 */
@Slf4j
@Service
public class Object24Helper {

    // fixed values
    @Value("${fixedValues.object24.operation}")
    private String columnOperation;

    @Value("${fixedValues.object24.operationSequence}")
    private String columnOperationSequence;

    /**
     * シートObject24: Airspace/geometryComponent
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param objectId 本シート用仮ID（ObjectID_24000X)
     * @param airSpaceVolumeIdx Object38に入力したオブジェクト数
     * @return Object24変換後オブジェクト
     */
    public Object24 convert(String featureId, String timeSliceId, String objectId, Integer airSpaceVolumeIdx) {
        Object24 object24 = new Object24();

        object24.setColumnFeatureID(featureId);
        object24.setColumnTimeSliceID(timeSliceId);
        object24.setColumnObjectID(objectId);
        object24.setColumnOperation(columnOperation);
        object24.setColumnOperationSequence(columnOperationSequence);
        object24.setColumnOAnnotation(null);
        object24.setColumnOTheAirspaceVolume(airSpaceVolumeIdx);

        return object24;
    }
}
