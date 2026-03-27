package jp.go.meti.drone.swim.model.excel.dto;

import jp.go.meti.drone.swim.annotation.ExcelColumn;
import lombok.Data;

/**
 * Object24シートに出力するデータ
 */
@Data
public class Object24 {

    /** FeatureID */
    @ExcelColumn(placeholder = "columnFeatureID")
    private String columnFeatureID;

    /** TimeSliceID */
    @ExcelColumn(placeholder = "columnTimeSliceID")
    private String columnTimeSliceID;

    /** ObjectID */
    @ExcelColumn(placeholder = "columnObjectID")
    private String columnObjectID;

    /** operation */
    @ExcelColumn(placeholder = "columnOperation")
    private String columnOperation;

    /** operationSequence */
    @ExcelColumn(placeholder = "columnOperationSequence")
    private String columnOperationSequence;

    /** O:annotation */
    @ExcelColumn(placeholder = "columnOAnnotation")
    private Integer columnOAnnotation;

    /** O:theAirspaceVolume */
    @ExcelColumn(placeholder = "columnOTheAirspaceVolume")
    private Integer columnOTheAirspaceVolume = 1;
}
