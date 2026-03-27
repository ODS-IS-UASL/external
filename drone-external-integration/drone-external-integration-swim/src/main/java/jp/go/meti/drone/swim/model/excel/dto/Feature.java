package jp.go.meti.drone.swim.model.excel.dto;

import jp.go.meti.drone.swim.annotation.ExcelColumn;
import lombok.Data;

/**
 * Featureシートに出力するデータ
 */
@Data
public class Feature {

    /** FeatureID */
    @ExcelColumn(placeholder = "columnFeatureID")
    private String columnFeatureID;

    /** TimeSliceID */
    @ExcelColumn(placeholder = "columnTimeSliceID")
    private String columnTimeSliceID;

    /** beginPosition */
    @ExcelColumn(placeholder = "columnBeginPosition")
    private String columnBeginPosition;

    /** endPosition */
    @ExcelColumn(placeholder = "columnEndPosition")
    private String columnEndPosition;

    /** type */
    @ExcelColumn(placeholder = "columnType")
    private String columnType;

    /** designator */
    @ExcelColumn(placeholder = "columnDesignator")
    private String columnDesignator;

    /** localType */
    @ExcelColumn(placeholder = "columnLocalType")
    private String columnLocalType;

    /** name */
    @ExcelColumn(placeholder = "columnName")
    private String columnName;

    /** designatorICAO */
    @ExcelColumn(placeholder = "columnDesignatorICAO")
    private String columnDesignatorICAO;

    /** controlType */
    @ExcelColumn(placeholder = "columnControlType")
    private String columnControlType;

    /** upperLowerSeparation */
    @ExcelColumn(placeholder = "columnUpperLowerSeparation")
    private String columnUpperLowerSeparation;

    /** O:class */
    @ExcelColumn(placeholder = "columnOClass")
    private Integer columnOClass;

    /** O:activation */
    @ExcelColumn(placeholder = "columnOActivation")
    private Integer columnOActivation = 1;

    /** O:annotation */
    @ExcelColumn(placeholder = "columnOAnnotation")
    private Integer columnOAnnotation;

    /** O:geometryComponent */
    @ExcelColumn(placeholder = "columnOGeometryComponent")
    private Integer columnOGeometryComponent = 1;

    /** F:rotectedRoute */
    @ExcelColumn(placeholder = "columnOProtectedRoute")
    private Integer columnOProtectedRoute;

}
