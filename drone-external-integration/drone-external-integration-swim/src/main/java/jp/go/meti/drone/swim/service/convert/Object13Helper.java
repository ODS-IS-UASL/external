package jp.go.meti.drone.swim.service.convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.excel.dto.Object13;
import lombok.extern.slf4j.Slf4j;

/**
 * Object13ヘルパー
 */
@Slf4j
@Service
public class Object13Helper {

    // fixed values
    @Value("${fixedValues.object13.timeReference}")
    private String columnTimeReference;

    @Value("${fixedValues.object13.day}")
    private String columnDay;

    @Value("${fixedValues.object13.dayTil}")
    private String columnDayTil;

    @Value("${fixedValues.object13.startTime}")
    private String columnStartTime;

    @Value("${fixedValues.object13.endTime}")
    private String columnEndTime;

    @Value("${fixedValues.object13.excluded}")
    private String columnExcluded;

    /**
     * シートObject13: Airspace/activation/timeInterval
     * 
     * @param featureId 全シート共通(FeatureID_00000X)
     * @param timeSliceId 全シート共通(TimeSliceID_00000X)
     * @param perObjectId Object10で設定したObjectID
     * @param objectId 本シート用仮ID(ObjectID_13000X)
     * @return object13変換後オブジェクト
     */
    public Object13 convert(String featureId, String timeSliceId, String perObjectId, String objectId) {

        Object13 object13 = new Object13();

        object13.setColumnFeatureID(featureId);
        object13.setColumnTimeSliceID(timeSliceId);
        object13.setColumnParObjectID(perObjectId);
        object13.setColumnObjectID(objectId);
        object13.setColumnTimeReference(columnTimeReference);
        object13.setColumnStartDate(null);
        object13.setColumnEndDate(null);
        object13.setColumnDay(columnDay);
        object13.setColumnDayTil(columnDayTil);
        object13.setColumnStartTime(columnStartTime);
        object13.setColumnStartTimeRelativeEvent(null);
        object13.setColumnStartEventInterpretation(null);
        object13.setColumnEndTime(columnEndTime);
        object13.setColumnEndEvent(null);
        object13.setColumnEndTimeRelativeEvent(null);
        object13.setColumnDaylightSavingAdjust(null);
        object13.setColumnExcluded(columnExcluded);
        object13.setColumnOAnnotation(null);

        return object13;
    }
}
