package jp.go.meti.drone.swim.service.convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.excel.dto.Feature;
import lombok.extern.slf4j.Slf4j;

/**
 * Featureヘルパー
 */
@Slf4j
@Service
public class FeatureHelper {

    // fixed values
    @Value("${fixedValues.feature.type}")
    private String columnType;

    @Value("${fixedValues.feature.localType}")
    private String columnLocalType;

    @Value("${fixedValues.feature.name}")
    private String columnName;

    /**
     * シートFeature: Airspace
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param beginPosition 有効開始日時
     * @param endPosition 終了予定日時
     * @param designator 航路ID
     * @param activationIdx Object10のオブジェクト数
     * @param geometryComponentIdx Object24のオブジェクト数
     * @return feature変換後オブジェクト
     */
    public Feature convert(String featureId, String timeSliceId, String beginPosition, String endPosition,
        String designator, Integer activationIdx, Integer geometryComponentIdx) {

        Feature feature = new Feature();
        feature.setColumnFeatureID(featureId);
        feature.setColumnTimeSliceID(timeSliceId);
        feature.setColumnBeginPosition(beginPosition);
        feature.setColumnEndPosition(endPosition);
        feature.setColumnType(columnType);
        feature.setColumnDesignator(designator);
        feature.setColumnLocalType(columnLocalType);
        feature.setColumnName(columnName);
        feature.setColumnDesignatorICAO(null);
        feature.setColumnControlType(null);
        feature.setColumnUpperLowerSeparation(null);
        feature.setColumnOClass(null);
        feature.setColumnOActivation(activationIdx); // Object10
        feature.setColumnOAnnotation(null);
        feature.setColumnOGeometryComponent(geometryComponentIdx); // Object24
        feature.setColumnOProtectedRoute(null);

        return feature;
    }

}
