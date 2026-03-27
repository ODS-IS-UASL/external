package jp.go.meti.drone.swim.model.excel.dto;

import jp.go.meti.drone.swim.annotation.ExcelColumn;
import lombok.Data;

/**
 * Object10シートに出力するデータ
 */
@Data
public class Object10 {

    /** FeatureID */
    @ExcelColumn(placeholder = "columnFeatureID")
    private String columnFeatureID;

    /** TimeSliceID */
    @ExcelColumn(placeholder = "columnTimeSliceID")
    private String columnTimeSliceID;

    /** ObjectID */
    @ExcelColumn(placeholder = "columnObjectID")
    private String columnObjectID;

    /** activity */
    @ExcelColumn(placeholder = "columnActivity")
    private String columnActivity;

    /** status */
    @ExcelColumn(placeholder = "columnStatus")
    private String columnStatus;

    /** O:aircraft */
    @ExcelColumn(placeholder = "columnOAircraft")
    private Integer columnOAircraft;

    /** O:levels */
    @ExcelColumn(placeholder = "columnOLevels")
    private Integer columnOLevels;

    /** O:Annotation */
    @ExcelColumn(placeholder = "columnOAnnotation")
    private Integer columnOAnnotation;

    /** O:timeInterval */
    @ExcelColumn(placeholder = "columnOTimeInterval")
    private Integer columnOTimeInterval = 1;

    /** F:user */
    @ExcelColumn(placeholder = "columnFUser")
    private String columnFUser;

    /** F:specialDateAuthority */
    @ExcelColumn(placeholder = "columnFSpecialDateAuthority")
    private String columnFSpecialDateAuthority;
}
