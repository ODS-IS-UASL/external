package jp.go.meti.drone.swim.model.excel.dto;

import jp.go.meti.drone.swim.annotation.ExcelColumn;
import lombok.Data;

/**
 * Object29シートに出力するデータ
 */
@Data
public class Object29 {

    /** FeatureID */
    @ExcelColumn(placeholder = "columnFeatureID")
    private String columnFeatureID;

    /** TimeSliceID */
    @ExcelColumn(placeholder = "columnTimeSliceID")
    private String columnTimeSliceID;

    /** ParObjectID */
    @ExcelColumn(placeholder = "columnParObjectID")
    private String columnParObjectID;

    /** ObjectID */
    @ExcelColumn(placeholder = "columnObjectID")
    private String columnObjectID;

    /** horizontalAccuracy */
    @ExcelColumn(placeholder = "columnHorizontalAccuracy")
    private String columnHorizontalAccuracy;

    /** O:annotation */
    @ExcelColumn(placeholder = "columnOAnnotation")
    private Integer columnOAnnotation;

    /** GML */
    @ExcelColumn(placeholder = "columnGML")
    private String columnGML;
}
