package jp.go.meti.drone.swim.model.excel.dto;

import jp.go.meti.drone.swim.annotation.ExcelColumn;
import lombok.Data;

/**
 * Object38シートに出力するデータ
 */
@Data
public class Object38 {

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

    /** upperLimit */
    @ExcelColumn(placeholder = "columnUpperLimit")
    private String columnUpperLimit;

    /** upperLimitReference */
    @ExcelColumn(placeholder = "columnUpperLimitReference")
    private String columnUpperLimitReference;

    /** MaximumLimit */
    @ExcelColumn(placeholder = "columnMaximumLimit")
    private String columnMaximumLimit;

    /** maximumLimitReference */
    @ExcelColumn(placeholder = "columnMaximumLimitReference")
    private String columnMaximumLimitReference;

    /** lowerLimit */
    @ExcelColumn(placeholder = "columnLowerLimit")
    private String columnLowerLimit;

    /** lowerLimitReference */
    @ExcelColumn(placeholder = "columnLowerLimitReference")
    private String columnLowerLimitReference;

    /** minimumLimit */
    @ExcelColumn(placeholder = "columnMinimumLimit")
    private String columnMinimumLimit;

    /** minimumLimitReference */
    @ExcelColumn(placeholder = "columnMinimumLimitReference")
    private String columnMinimumLimitReference;

    /** width */
    @ExcelColumn(placeholder = "columnWidth")
    private String columnWidth;

    /** O:contributorAirspace */
    @ExcelColumn(placeholder = "columnOContributorAirspace")
    private Integer columnOContributorAirspace;

    /** O:horizontalProjection */
    @ExcelColumn(placeholder = "columnOHorizontalProjection")
    private Integer columnOHorizontalProjection = 1;

    /** O:Centreline */
    @ExcelColumn(placeholder = "columnOCentreline")
    private Integer columnOCentreline;

    /** O:annotation */
    @ExcelColumn(placeholder = "columnOAnnotation")
    private Integer columnOAnnotation;
}
