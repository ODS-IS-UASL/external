package jp.go.meti.drone.dips.com;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import io.micrometer.common.util.StringUtils;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.model.flightplan.PilotInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 戻り値チェッククラス
 * 
 * @version 1.0 2025/9/22
 */
@Slf4j
@SuppressWarnings("javadoc")
public class ResponseFlightPlanValidator {
    // 経度最小値
    private static final BigDecimal MIN_LON = new BigDecimal("-180.000000000000");

    // 経度最大値
    private static final BigDecimal MAX_LON = new BigDecimal("180.000000000000");

    // 緯度最小値
    private static final BigDecimal MIN_LAT = new BigDecimal("-90.000000000000");

    // 緯度最大値
    private static final BigDecimal MAX_LAT = new BigDecimal("90.000000000000");
     
    /**
     * 必須項目チェック
     * @param value
     * @param fieldName
     * @throws Exception
     */
    public static void validateRequired(Object value, String fieldName){
        if (value == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DRC01E003"), fieldName);
            throw new ResponseValidatorException(message);
        }

        if (value instanceof String str && str.trim().isEmpty()) {
            String message = MessageFormat.format(MessageUtils.getMessage("DRC01E003"), fieldName);
            throw new ResponseValidatorException(message);
        }

    }
    
    /**
     * 配列（数値）の範囲値チェック
     * @param list
     * @param min
     * @param max
     * @param fieldName
     */
    public static void validateIntegerListValueRange(List<Integer> list, int min, int max, String fieldName) {
        if (list != null && !list.isEmpty()) {
            for (Integer value : list) {
                if (value == null || value < min || value > max) {
                    String message = MessageFormat.format(MessageUtils.getMessage("DR005E014"), fieldName, String.valueOf(value), min + "～" + max);
                    throw new ResponseValidatorException(message);
                }
            }
        }          
    }
    
    /**
     * 数値の範囲 チェック（例: 5～1440の5分単位）
     * @param value
     * @param min
     * @param max
     * @param step
     * @param fieldName
     */
    public static void validateNumberWithStep(int value, int min, int max, int step, String fieldName) {
        if (value < min || value > max || value % step != 0) {
            String message = MessageFormat.format(
                MessageUtils.getMessage("DR005E012"),
                fieldName,
                String.valueOf(value),
                min + "～" + max + "（" + step + "単位）");
            throw new ResponseValidatorException(message);
        }
    }
    
    /**
     * 文字列の範囲チェック
     * @param value
     * @param allowed
     * @param fieldName
     */
    public static void validateAllowedValues(String value, Set<String> allowed, String fieldName) {
        if (StringUtils.isNotEmpty(value) && !allowed.contains(value)) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E012"),fieldName,value,String.join("、", allowed));
            throw new ResponseValidatorException(message);
        }
    }
    
    /**
     * 日時形式「yyyyMMdd HHmm」チェック
     * @param datetime
     * @param fieldName
     */
    public static void validateDateTimeFormat(String datetime, String fieldName) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
            LocalDateTime.parse(datetime, formatter);
        } catch (Exception e) {
            String message = MessageUtils.getMessage("DR005E013",fieldName,datetime);
            throw new ResponseValidatorException(message);
        }
    }
     
    /**
     * 相関必須項目チェック
     * @param list
     * @param purposeValue
     * @param value
     * @param fieldName1
     * @param fieldName2
     */
    public static void validatePurpose(List<Integer> list,int purposeValue,String value,String fieldName1,String fieldName2) {
        if (list != null && list.contains(purposeValue) && StringUtils.isEmpty(value)) {
                String message = MessageFormat.format(MessageUtils.getMessage("DR005E016"),fieldName1,fieldName2,value);
                throw new ResponseValidatorException(message);
            
        }
    }
    
    /**
     * ジオメトリタイプがCircleの相関チェック
     * @param geometryType
     * @param center
     * @param radius
     * @param fieldName1
     * @param fieldName2
     */
    public static void validateCircleGeometry(String geometryType, List<BigDecimal> center, BigDecimal radius,
        String fieldName1,String fieldName2){
        if (!"Circle".equalsIgnoreCase(geometryType))
            return;

        if (center == null || center.size() != 2) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E016"), geometryType,fieldName1,center);
            throw new ResponseValidatorException(message);
        }
        
        BigDecimal lon = center.get(0);
        BigDecimal lat = center.get(1);
        validateCoordinate(geometryType, fieldName1, lon, lat);
        
        if (radius == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E016"), geometryType ,fieldName2,radius);
            throw new ResponseValidatorException(message);
        }
    }

    /**
     * ジオメトリタイプがPolygonの相関チェック
     * @param geometryType
     * @param geometry
     * @param fieldName
     */
    public static void validatePolygonGeometry(String geometryType, List<List<BigDecimal>> geometry, String fieldName){
        if (!"Polygon".equalsIgnoreCase(geometryType))
            return;

        if (geometry == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E016"), geometryType ,fieldName,"null");
            throw new ResponseValidatorException(message);
        }
        
        if (geometry.size() < 3) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E015"), fieldName ,3,geometry.size());
            throw new ResponseValidatorException(message);
        }
        
        for (int i = 0; i < geometry.size(); i++) {
            List<BigDecimal> point = geometry.get(i);
            if (point != null && point.size() == 2) {
                BigDecimal lon = point.get(0);
                BigDecimal lat = point.get(1); 
                validateCoordinate(geometryType, fieldName, lon, lat);
            } else {
                String message = MessageFormat.format(MessageUtils.getMessage("DR005E016"),geometryType,fieldName + ".lon and lat");
                throw new ResponseValidatorException(message);
            }
        }
    }

    private static void validateCoordinate(String geometryType, String fieldName, BigDecimal lon, BigDecimal lat) {
        if (lon == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E016"), geometryType ,fieldName+".lon","null");
            throw new ResponseValidatorException(message);
        }
        if (lon.compareTo(MIN_LON) < 0 || lon.compareTo(MAX_LON) > 0) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E014"), fieldName+ ".lon",lon, MIN_LON.toPlainString()+"~"+ MAX_LON.toPlainString());
            throw new ResponseValidatorException(message);
        }
   
        if (lat == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E016"), geometryType ,fieldName+".lat","null");
            throw new ResponseValidatorException(message);
        }
        if (lat.compareTo(MIN_LAT) < 0 || lat.compareTo(MAX_LAT) > 0) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E014"), fieldName + ".lat",lat, MIN_LAT.toPlainString()+"~"+ MAX_LAT.toPlainString());
            throw new ResponseValidatorException(message);
        }
    }
    
    /**
     * 連番範囲＋特別コード許容チェックの共通ロジック
     *
     * @param value チェック対象文字列
     * @param min 最小値（数値）
     * @param max 最大値（数値）
     * @param extraAllowedValues 特別に許容する文字列セット
     * @param fieldName 項目名（エラーメッセージ用）
     */
    public static void validateCodeInRangeWithExtras(String value, int min, int max, Set<String> extraAllowedValues,
        String fieldName, String formats){
        if (StringUtils.isNotEmpty(value)) {

            // 特別許容値のチェック
            if (extraAllowedValues != null && extraAllowedValues.contains(value)) {
                return;
            }

            // 数値としてチェック
            try {
                int intValue = Integer.parseInt(value);
                if (intValue < min || intValue > max) {
                    String message = MessageFormat.format(MessageUtils.getMessage("DR005E012"),fieldName,value,
                        formatRange(min, max, extraAllowedValues, formats));
                    throw new ResponseValidatorException(message);
                }
            } catch (NumberFormatException e) {
                String message = MessageFormat.format(MessageUtils.getMessage("DR005E012"),fieldName,value,
                    formatRange(min, max, extraAllowedValues, formats));
                log.error(message, e);
                throw new ResponseValidatorException(message);
            }
        }
    }

    private static String formatRange(int min, int max, Set<String> extras,String format) {
        String range = String.format(format, min, max);
        if (extras != null && !extras.isEmpty()) {
            return range + ", " + String.join(", ", extras);
        } else {
            return range;
        }
    }

    
    /**
     * 連絡先フラグ チェック
     * @param reporterFlag
     * @param permitFlag
     * @param PilotInfos
     * @throws Exception
     */  
    public static void checkContactFlags(String reporterFlag, String permitFlag, List<PilotInfo> pilotInfos){
        boolean isReporterFlagSet = "1".equals(reporterFlag);
        boolean isPermitFlagSet = "1".equals(permitFlag);
        boolean isPilotFlagSet = pilotInfos != null && pilotInfos.stream()
            .filter(Objects::nonNull)
            .anyMatch(pilotInfo -> "1".equals(pilotInfo.getContactPilotFlag()));

        if (!isReporterFlagSet && !isPermitFlagSet && !isPilotFlagSet) {
            String message = MessageUtils.getMessage("DR005E017");
            throw new ResponseValidatorException(message);
        }
    }
}
