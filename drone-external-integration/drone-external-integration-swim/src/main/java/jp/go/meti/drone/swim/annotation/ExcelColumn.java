package jp.go.meti.drone.swim.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ExcelColumnアノテーションのインターフェース
 */
@Retention(RetentionPolicy.RUNTIME) // 実行時まで残る
@Target(ElementType.FIELD) // フィールドだけ使用できる
public @interface ExcelColumn {

    /**
     * Excelテンプレート内で置き換え対象となるプレースホルダの文字列を指定
     * 
     * @return Excelテンプレートのプレースホルダ文字列
     */
    String placeholder();
}
