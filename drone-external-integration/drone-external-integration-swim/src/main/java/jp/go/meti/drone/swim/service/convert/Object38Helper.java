package jp.go.meti.drone.swim.service.convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.excel.dto.Object38;
import lombok.extern.slf4j.Slf4j;

/**
 * Object38ヘルパー
 */
@Slf4j
@Service
public class Object38Helper {

    // fixed values
    @Value("${fixedValues.object38.upperLimitReference}")
    private String columnUpperLimitReference;

    @Value("${fixedValues.object38.lowerLimit}")
    private String columnLowerLimit;

    @Value("${fixedValues.object38.lowerLimitReference}")
    private String columnLowerLimitReference;

    @Value("${fixedValues.object38.upperLimitUnit}")
    private String upperLimitUnit;

    /**
     * シートObject38: Airspace/geometryComponent/theAirspaceVolume
     * 
     * @param featureID 全シート共通(FeatureID_00000X)
     * @param timeSliceID 全シート共通(TimeSliceID_00000X)
     * @param parObjectID Object24で設定した仮ID(ObjectID_24000X)
     * @param objectID 本シート用仮ID(ObjectID_38000X)
     * @param upper 上限高度[M]
     * @param horizontalProjectionIdx Object29シートオブジェクト数
     * @return Object38変換後オブジェクト
     */
    public Object38 convert(String featureID, String timeSliceID, String parObjectID, String objectID, double upper,
        Integer horizontalProjectionIdx) {

        Object38 obj = new Object38();
        obj.setColumnFeatureID(featureID);
        obj.setColumnTimeSliceID(timeSliceID);
        obj.setColumnParObjectID(parObjectID);
        obj.setColumnObjectID(objectID);
        obj.setColumnUpperLimit(upper + upperLimitUnit);
        obj.setColumnUpperLimitReference(columnUpperLimitReference);
        obj.setColumnMaximumLimit(null);
        obj.setColumnMaximumLimitReference(null);
        obj.setColumnLowerLimit(columnLowerLimit);
        obj.setColumnLowerLimitReference(null);
        obj.setColumnMinimumLimit(null);
        obj.setColumnMinimumLimitReference(null);
        obj.setColumnWidth(null);
        obj.setColumnOHorizontalProjection(horizontalProjectionIdx);
        obj.setColumnOCentreline(null);
        obj.setColumnOAnnotation(null);

        return obj;
    }

    /**
     * シートObject38を生成し返却
     * 
     * @param featureID 全シート共通(FeatureID_00000X)
     * @param timeSliceID 全シート共通(TimeSliceID_00000X)
     * @param parObjectID Object24で設定した仮ID(ObjectID_24000X)
     * @param objectID 本シート用仮ID(ObjectID_38000X)
     * @param upper 上限高度[M]（DB格納値）
     * @param horizontalProjectionIdx Object29シートオブジェクト数
     * @return Object38変換後オブジェクト
     */
    public Object38 convertFromHistory(String featureID, String timeSliceID, String parObjectID, String objectID,
        String upper, Integer horizontalProjectionIdx) {

        Object38 obj = new Object38();
        obj.setColumnFeatureID(featureID);
        obj.setColumnTimeSliceID(timeSliceID);
        obj.setColumnParObjectID(parObjectID);
        obj.setColumnObjectID(objectID);
        obj.setColumnUpperLimit(upper);
        obj.setColumnUpperLimitReference(columnUpperLimitReference);
        obj.setColumnMaximumLimit(null);
        obj.setColumnMaximumLimitReference(null);
        obj.setColumnLowerLimit(columnLowerLimit);
        obj.setColumnLowerLimitReference(null);
        obj.setColumnMinimumLimit(null);
        obj.setColumnMinimumLimitReference(null);
        obj.setColumnWidth(null);
        obj.setColumnOHorizontalProjection(horizontalProjectionIdx);
        obj.setColumnOCentreline(null);
        obj.setColumnOAnnotation(null);

        return obj;
    }
}
