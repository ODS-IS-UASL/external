package jp.go.meti.drone.swim.com;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.go.meti.drone.swim.model.excel.dto.Feature;
import jp.go.meti.drone.swim.model.excel.dto.Object10;
import jp.go.meti.drone.swim.model.excel.dto.Object13;
import jp.go.meti.drone.swim.model.excel.dto.Object24;
import jp.go.meti.drone.swim.model.excel.dto.Object29;
import jp.go.meti.drone.swim.model.excel.dto.Object38;

/**
 * Excelテンプレートを読み込み値を差し込む処理のインターフェース
 */
public interface ExcelCreator {

    /**
     * templatePathからExcelテンプレートを読み込む
     * 
     * @param templatePath テンプレートのパス
     * @return 読み込んだXSSFWorkbook
     * @throws IOException テンプレートの読み込みで失敗時発生
     */
    public XSSFWorkbook loadTemplate(String templatePath) throws IOException;

    /**
     * 受け取ったExcelをbyte配列に変換
     * 
     * @param workbook 変換するExcelテンプレート
     * @return byte配列にしたworkbook
     * @throws IOException 変換処理中に失敗した場合発生
     */
    public byte[] workbookToBytes(XSSFWorkbook workbook) throws IOException;

    /**
     * Featureシートのプレースホルダを値に置換
     * 
     * @param workbook featureシート部分
     * @param features featureシートの値一覧
     * @return workbook featureシートの値が埋まったworkbook
     * @throws IntrospectionException Beansの情報を取得または走査する際の不正な定義やエラーが発生した場合
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    public XSSFWorkbook setFeature(XSSFWorkbook workbook, List<Feature> features) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException;

    /**
     * object10sシートのプレースホルダを値に置換
     * 
     * @param workbook object10sシート部分
     * @param object10s object10sシートの値一覧
     * @return workbook object10sシートの値が埋まったworkbook
     * @throws IntrospectionException Beansの情報を取得または走査する際、不正な定義やエラーが発生した場合
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    public XSSFWorkbook setObject10(XSSFWorkbook workbook, List<Object10> object10s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException;

    /**
     * Object13sシートのプレースホルダを値に置換
     * 
     * @param workbook object13sシート部分
     * @param object13s object13sシートの値
     * @return workbook object13sシートの値が埋まったworkbook
     * @throws IntrospectionException Beansの情報を取得または走査する際、不正な定義やエラーが発生した場合
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    public XSSFWorkbook setObject13(XSSFWorkbook workbook, List<Object13> object13s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException;

    /**
     * object24sシートのプレースホルダを値に置換
     * 
     * @param workbook object24sシート部分
     * @param object24s object24sシートの値一覧
     * @return workbook object24sシートの値が埋まったworkbook
     * @throws IntrospectionException Beansの情報を取得または走査する際、不正な定義やエラーが発生した場合
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    public XSSFWorkbook setObject24(XSSFWorkbook workbook, List<Object24> object24s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException;

    /**
     * object29sシートのプレースホルダを値に置換
     * 
     * @param workbook object29sシート部分
     * @param object29s object29sシートの値一覧
     * @return workbook object29sシートの値が埋まったworkbook
     * @throws IntrospectionException Beansの情報を取得または走査する際、不正な定義やエラーが発生した場合
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    public XSSFWorkbook setObject29(XSSFWorkbook workbook, List<Object29> object29s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException;

    /**
     * object38sシートのプレースホルダを値に置換
     * 
     * @param workbook object38sシート部分
     * @param object38s object38sシートの値一覧
     * @return workbook object38sシートの値が埋まったworkbook
     * @throws IntrospectionException Beansの情報を取得または走査する際、不正な定義やエラーが発生した場合
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    public XSSFWorkbook setObject38(XSSFWorkbook workbook, List<Object38> object38s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException;
}
