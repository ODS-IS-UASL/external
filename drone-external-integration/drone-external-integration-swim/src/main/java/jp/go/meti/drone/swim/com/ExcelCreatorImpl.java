package jp.go.meti.drone.swim.com;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.annotation.ExcelColumn;
import jp.go.meti.drone.swim.model.excel.dto.Feature;
import jp.go.meti.drone.swim.model.excel.dto.Object10;
import jp.go.meti.drone.swim.model.excel.dto.Object13;
import jp.go.meti.drone.swim.model.excel.dto.Object24;
import jp.go.meti.drone.swim.model.excel.dto.Object29;
import jp.go.meti.drone.swim.model.excel.dto.Object38;
import lombok.extern.slf4j.Slf4j;

/**
 * Excelテンプレートを読み込み値を差し込む処理の実装
 */
@Slf4j
@Service
public class ExcelCreatorImpl implements ExcelCreator {

    /**
     * Excelのセルを表示形式どおりに文字列へ変換するための共通のフォーマッター
     */
    public static final DataFormatter FORMATTER = new DataFormatter();

    /** Excelテンプレートのオープンタグ */
    @Value("${excel.tag-open}")
    private String tagOpen;

    /** Excelテンプレートのクローズタグ */
    @Value("${excel.tag-close}")
    private String tagClose;

    /**
     * templatePathからExcelテンプレートを読み込む
     * 
     * @param templatePath テンプレートのパス
     * @return 読み込んだXSSFWorkbook
     * @throws IOException テンプレートの読み込みで失敗した場合に発生
     */
    public XSSFWorkbook loadTemplate(String templatePath) throws IOException {
        try (InputStream is = new ClassPathResource(templatePath).getInputStream()) {
            return new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 受け取ったExcelをbyte配列に変換
     * 
     * @param workbook 変換するExcelテンプレート
     * @return byte配列にしたworkbook
     * @throws IOException 変換処理中に失敗した場合発生
     */
    public byte[] workbookToBytes(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        workbook.close();
        return bos.toByteArray();
    }

    /**
     * Featureシートのプレースホルダを値に置換
     * 
     * @param workbook featureシート部分
     * @param features featureシートの値一覧
     * @return workbook featureシートの値が埋まったworkbook
     * @throws IntrospectionException Beansの情報を取得または走査する際、不正な定義やエラーが発生した場合
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    @Override
    public XSSFWorkbook setFeature(XSSFWorkbook workbook, List<Feature> features) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException {
        List<List<Map<String, Object>>> valueMapList = getValueMapList(features);
        XSSFSheet sheet = workbook.getSheet("Feature");
        setObject(sheet, valueMapList);
        return workbook;
    }

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
    @Override
    public XSSFWorkbook setObject10(XSSFWorkbook workbook, List<Object10> object10s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException {
        List<List<Map<String, Object>>> valueMapList = getValueMapList(object10s);
        XSSFSheet sheet = workbook.getSheet("Object10");
        setObject(sheet, valueMapList);
        return workbook;
    }

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
    @Override
    public XSSFWorkbook setObject13(XSSFWorkbook workbook, List<Object13> object13s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException {
        List<List<Map<String, Object>>> valueMapList = getValueMapList(object13s);
        XSSFSheet sheet = workbook.getSheet("Object13");
        setObject(sheet, valueMapList);
        return workbook;
    }

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
    @Override
    public XSSFWorkbook setObject24(XSSFWorkbook workbook, List<Object24> object24s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException {
        List<List<Map<String, Object>>> valueMapList = getValueMapList(object24s);
        XSSFSheet sheet = workbook.getSheet("Object24");
        setObject(sheet, valueMapList);
        return workbook;
    }

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
    @Override
    public XSSFWorkbook setObject29(XSSFWorkbook workbook, List<Object29> object29s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException {
        List<List<Map<String, Object>>> valueMapList = getValueMapList(object29s);
        XSSFSheet sheet = workbook.getSheet("Object29");
        setObject(sheet, valueMapList);
        return workbook;
    }

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
    @Override
    public XSSFWorkbook setObject38(XSSFWorkbook workbook, List<Object38> object38s) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException {
        List<List<Map<String, Object>>> valueMapList = getValueMapList(object38s);
        XSSFSheet sheet = workbook.getSheet("Object38");
        setObject(sheet, valueMapList);
        return workbook;
    }

    /**
     * 指定されたExcelのシート内のプレースホルダを、実際の値に置換処理を実行
     * 
     * @param sheet 処理する対象のExcelシート
     * @param valueMapList プレースホルダと置換する値のマップのリスト
     * @return sheet 処理した後のExcelシート
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     */
    private XSSFSheet setObject(XSSFSheet sheet, List<List<Map<String, Object>>> valueMapList)
        throws IllegalAccessException {
        int templateRowIndex = searchTemplateRow(sheet, valueMapList.get(0).get(0));
        prepareRows(sheet, templateRowIndex, valueMapList.size());
        for (List<Map<String, Object>> valueMaps : valueMapList) {
            Row row = sheet.getRow(templateRowIndex++);
            for (Map<String, Object> valueMap : valueMaps) {
                replaceRowPlaceholders(row, valueMap);
            }
        }
        return sheet;
    }

    /**
     * 1行分の全セルに対してプレースホルダ置換を実行
     * 
     * @param row プレースホルダ置換対象の行
     * @param valueMap プレースホルダと値のマップ
     */
    private void replaceRowPlaceholders(Row row, Map<String, Object> valueMap) {

        int lastCellNum = row.getLastCellNum();

        for (int cellIndex = 0; cellIndex <= lastCellNum; cellIndex++) {
            Cell cell = row.getCell(cellIndex);
            if (cell == null) {
                continue;
            }
            replaceCellPlaceholders(cell, valueMap);
        }
    }

    /**
     * 1セル分の文字列を取り出して、プレースホルダを置換してセルに書き戻す
     * 
     * @param cell プレースホルダ置換対象のセル
     * @param valueMap プレースホルダと値のマップ
     */
    private void replaceCellPlaceholders(Cell cell, Map<String, Object> valueMap) {

        // FORMATTER を使って、数値や日付も含めて「文字列」として取得
        String originalText = FORMATTER.formatCellValue(cell);

        // もともと空文字 or nullならばなにもしない
        if (cell == null || originalText == null || originalText.isEmpty()) {
            return;
        }

        // 置換処理（結果の文字列を受け取る）
        String replacedText = replaceTextPlaceholders(originalText, valueMap);

        // 置換後の文字列が同じなら、セルは書き換えない
        if (!originalText.equals(replacedText)) {
            cell.setCellValue(replacedText);
        }
    }

    /**
     * 文字列中のプレースホルダを valueMap の値で置き換えた結果返却
     * 
     * @param text セルから取り出した文字列
     * @param valueMap プレースホルダと値のマップ
     * @return 置換後の文字列（置換がなければ元の text と同じ）
     */
    private String replaceTextPlaceholders(String text, Map<String, Object> valueMap) {

        String result = text;

        // mapのプレースホルダを順番にチェック
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value == null) {
                value = "";
            }

            if (result.contains(key)) {
                // プレースホルダ部分を value の文字列表現に置き換え
                result = result.replace(key, String.valueOf(value));
            }
        }
        return result;
    }

    /**
     * モデルクラスに書かれた@ExcelColumnアノテーションを読み取り、Excelテンプレートのプレースホルダと出力する値のMapを作成
     * 
     * @param objects
     * @return List<List<Map<String, Object>>> valueMapList
     * @throws IllegalAccessException アクセス権限を持っていないフィールドなどにアクセスした場合に発生
     * @throws InvocationTargetException 呼び出されたメソッド側で例外がthrowされた場合に発生
     * @throws IntrospectionException 情報の取得に失敗した場合に発生
     */
    private List<List<Map<String, Object>>> getValueMapList(List<?> objects) throws IllegalAccessException,
        InvocationTargetException, IntrospectionException {
        try {
            List<List<Map<String, Object>>> valueMapList = new ArrayList<>();
            for (Object object : objects) {
                List<Map<String, Object>> valueMaps = new ArrayList<>();
                Class<?> clazz = object.getClass();
                for (Field field : clazz.getDeclaredFields()) {
                    Map<String, Object> valueMap = new HashMap<>();
                    if (field.isAnnotationPresent(ExcelColumn.class)) {
                        ExcelColumn ann = field.getAnnotation(ExcelColumn.class);

                        String placeholder = tagOpen + ann.placeholder().trim() + tagClose;
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                        Method getter = pd.getReadMethod();
                        if (getter != null) {
                            Object value = getter.invoke(object);
                            valueMap.put(placeholder, value);
                            valueMaps.add(valueMap);
                        }
                    }
                }
                valueMapList.add(valueMaps);
            }
            return valueMapList;
        } catch (IllegalAccessException e) {
            log.debug(e.getMessage());
            throw e;
        } catch (InvocationTargetException e) {
            log.debug(e.getMessage());
            throw e;
        } catch (IntrospectionException e) {
            log.debug(e.getMessage());
            throw e;
        }
    }

    /**
     * Excelテンプレートの中からプレースホルダがある対象の行を探して返却
     * 
     * @param sheet 処理対象のシート
     * @param valueMap プレースホルダと値のマップ
     * @return rowIndex 探してきた行のインデックス
     * @throws IllegalAccessException シート内に検索対象の行を発見できなかった場合に発生
     */
    private int searchTemplateRow(Sheet sheet, Map<String, Object> valueMap) throws IllegalAccessException {
        int lastRowNum = sheet.getLastRowNum();
        int rowIndex = 0;
        for (rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (rowContainsPlaceholder(row, valueMap.keySet())) {
                log.debug("found temlate row : rowIndex=" + rowIndex);
                return rowIndex;
            }
        }
        throw new IllegalAccessException("template row not found.");
    }

    /**
     * 指定された行に、プレースホルダが含まれているか確認
     * 
     * @param row 確認対象のExcel行
     * @param placeholders プレースホルダ
     * @return 行の中にプレースホルダが含まれているかどうか（含まれている：true 含まれていないfalse）
     */
    private boolean rowContainsPlaceholder(Row row, Set<String> placeholders) {
        for (Cell cell : row) {
            String text = FORMATTER.formatCellValue(cell);
            if (text == null || text.isEmpty()) {
                continue;
            }
            for (String key : placeholders) {
                if (text.contains(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * テンプレートとして使用する行をコピーし、指定した行数になるよう行を増加
     * 
     * @param sheet 処理対象のExcelシート
     * @param firstRowIndex テンプレートとして使用する最初の行のインデックス
     * @param rowCount 作成したい行数
     */
    private void prepareRows(Sheet sheet, int firstRowIndex, int rowCount) {
        for (int index = 1; index < rowCount; index++) {
            copyRow(sheet, firstRowIndex, firstRowIndex + index);
        }
    }

    /**
     * 指定した行をコピーし、新しい行を作成
     * 
     * @param sheet 処理対象のExcelシート
     * @param srcRowNum コピー元の行番号
     * @param destRowNum コピー先の行番号
     * @return destRow 作成したコピー先の行
     */
    private Row copyRow(Sheet sheet, int srcRowNum, int destRowNum) {
        // コピー元の行を取得
        Row srcRow = sheet.getRow(srcRowNum);
        // コピー先の新しい行を作成
        Row destRow = sheet.createRow(destRowNum);
        // 行の高さをコピー
        destRow.setHeight(srcRow.getHeight());
        // 1セルごとにコピーする
        for (Cell srcCell : srcRow) {
            // 同じ列番号にセルを作成する
            Cell destCell = destRow.createCell(srcCell.getColumnIndex());
            // セルの中身をコピーする
            copyCell(srcCell, destCell);
        }
        // 作成した行を返す
        return destRow;
    }

    /**
     * コピー元のセルの設定を、コピー先のセルへ設定
     * 
     * @param src コピー元のセル
     * @param dest コピー先のセル
     */
    private void copyCell(Cell src, Cell dest) {
        dest.setCellStyle(src.getCellStyle());
        switch (src.getCellType()) {

        // 文字列のセル
        case STRING:
            dest.setCellValue(src.getStringCellValue());
            break;

        // 数値のセル
        case NUMERIC:
            dest.setCellValue(src.getNumericCellValue());
            break;

        // 真偽値のセル
        case BOOLEAN:
            dest.setCellValue(src.getBooleanCellValue());
            break;

        // 数式のセル
        case FORMULA:
            dest.setCellFormula(src.getCellFormula());
            break;

        // 空白のセル
        case BLANK:
            dest.setBlank();
            break;

        // そのほかのセルの場合は何もしない
        default:
            break;
        }
    }
}
