package jp.go.meti.drone.swim.model.excel.dto;

import jp.go.meti.drone.swim.annotation.ExcelColumn;
import lombok.Data;

/**
 * Object13シートに出力するデータ
 */
@Data
public class Object13 {

    /** FeatureID */
    @ExcelColumn(placeholder = "columnFeatureID")
    private String columnFeatureID;

    /** TimeSliceID */
    @ExcelColumn(placeholder = "columnTimeSliceID")
    private String columnTimeSliceID;

    /** parObjectID */
    @ExcelColumn(placeholder = "columnParObjectID")
    private String columnParObjectID;

    /** ObjectID */
    @ExcelColumn(placeholder = "columnObjectID")
    private String columnObjectID;

    /** timeReference */
    @ExcelColumn(placeholder = "columnTimeReference")
    private String columnTimeReference;

    /** startDate */
    @ExcelColumn(placeholder = "columnStartDate")
    private String columnStartDate;

    /** endDate */
    @ExcelColumn(placeholder = "columnEndDate")
    private String columnEndDate;

    /** day */
    @ExcelColumn(placeholder = "columnDay")
    private String columnDay;

    /** dayTil */
    @ExcelColumn(placeholder = "columnDayTil")
    private String columnDayTil;

    /** startTime */
    @ExcelColumn(placeholder = "columnStartTime")
    private String columnStartTime;

    /** startEvent */
    @ExcelColumn(placeholder = "columnStartEvent")
    private String columnStartEvent;

    /** startTimeRelativeEvent */
    @ExcelColumn(placeholder = "columnStartTimeRelativeEvent")
    private String columnStartTimeRelativeEvent;

    /** startEventInterpretation */
    @ExcelColumn(placeholder = "columnStartEventInterpretation")
    private String columnStartEventInterpretation;

    /** endTime */
    @ExcelColumn(placeholder = "columnEndTime")
    private String columnEndTime;

    /** endEvent */
    @ExcelColumn(placeholder = "columnEndEvent")
    private String columnEndEvent;

    /** endTimeRelativeEvent */
    @ExcelColumn(placeholder = "columnEndTimeRelativeEvent")
    private String columnEndTimeRelativeEvent;

    /** endEventInterpretation */
    @ExcelColumn(placeholder = "columnEndEventInterpretation")
    private String columnEndEventInterpretation;

    /** daylightSavingAdjust */
    @ExcelColumn(placeholder = "columnDaylightSavingAdjust")
    private String columnDaylightSavingAdjust;

    /** excluded */
    @ExcelColumn(placeholder = "columnExcluded")
    private String columnExcluded;

    /** O:annotation */
    @ExcelColumn(placeholder = "columnOAnnotation")
    private String columnOAnnotation;
}
