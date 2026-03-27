package jp.go.meti.drone.dips.com;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import jp.go.meti.drone.com.common.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * 入力値チェッククラス
 * 
 * @version 1.0 2025/9/22
 */
@Slf4j
@SuppressWarnings("javadoc")
public class RequestFlightPlanValidator {
    
    // 経度最小値
    private static final BigDecimal MIN_LON = new BigDecimal("-180.000000000000");

    // 経度最大値
    private static final BigDecimal MAX_LON = new BigDecimal("180.000000000000");

    // 緯度最小値
    private static final BigDecimal MIN_LAT = new BigDecimal("-90.000000000000");

    // 緯度最大値
    private static final BigDecimal MAX_LAT = new BigDecimal("90.000000000000");

    // 経度
    private static final String LON = "経度";
    
    // 緯度
    private static final String LAT = "緯度";
    
    /**
     * 必須項目チェック
     * @param value
     * @param fieldName
     * @throws BadRequestException
     */
    public static void validateRequired(Object value, String fieldName) throws BadRequestException {
        if (value == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DRC01E003"), fieldName);
            throw new BadRequestException(message);
        }
    }

    /**
     * ジオメトリタイプチェック
     * @param geometryType
     * @throws BadRequestException
     */
    public static void validateGeometryType(String geometryType) throws BadRequestException {
        if (!"Circle".equalsIgnoreCase(geometryType) && !"Polygon".equalsIgnoreCase(geometryType)) {
            String message = MessageUtils.getMessage("DR005E006");
            throw new BadRequestException(message);
        }
    }
    
    /**
     * ジオメトリタイプがCircleの相関チェック
     * @param geometryType
     * @param center
     * @param radius
     * @param fieldName1
     * @param fieldName2
     * @throws BadRequestException
     */
    public static void validateCircleGeometry(String geometryType, List<BigDecimal> center, BigDecimal radius,
        String fieldName1,String fieldName2) throws BadRequestException {
        if (!"Circle".equalsIgnoreCase(geometryType))
            return;

        if (center == null || center.size() != 2) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E001"), geometryType,fieldName1);
            throw new BadRequestException(message);
        }

        BigDecimal lon = center.get(0);
        BigDecimal lat = center.get(1);
        validateCoordinate(geometryType, fieldName1, lon, lat);

        if (radius == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E001"), geometryType, fieldName2);
            throw new BadRequestException(message);
        }
    }

    /**
     * ジオメトリタイプがPolygonの相関チェック
     * @param geometryType
     * @param geometry
     * @param fieldName
     * @throws BadRequestException
     */
    public static void validatePolygonGeometry(String geometryType, List<List<BigDecimal>> geometry, String fieldName)
        throws BadRequestException {
        if (!"Polygon".equalsIgnoreCase(geometryType))
            return;

        if (geometry == null) {
            String message = MessageUtils.getMessage("DR005E001", geometryType,fieldName);
            throw new BadRequestException(message);
        }
        if (geometry.size() < 3) {
            String message = MessageUtils.getMessage("DR005E004");
            throw new BadRequestException(message);
        }

        for (int i = 0; i < geometry.size(); i++) {
            List<BigDecimal> point = geometry.get(i);

            if (point != null && point.size() == 2) {
                BigDecimal lon = point.get(0);
                BigDecimal lat = point.get(1);

                validateCoordinate(geometryType, fieldName, lon, lat);
                
            } else {
                String message = MessageFormat.format(MessageUtils.getMessage("DR005E001"),geometryType,fieldName + LON + LAT);
                throw new BadRequestException(message);
            }
        }
    }

    private static void validateCoordinate(String geometryType, String fieldName, BigDecimal lon, BigDecimal lat)
        throws BadRequestException {
        if (lon == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E001"), geometryType,fieldName + LON);
            throw new BadRequestException(message);
        }
        if (lon.compareTo(MIN_LON) < 0 || lon.compareTo(MAX_LON) > 0) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E003"), fieldName + LON, MIN_LON.toPlainString(),MAX_LON.toPlainString());
            throw new BadRequestException(message);
        }
   
        if (lat == null) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E001"), geometryType,fieldName + LAT);
            throw new BadRequestException(message);
        }
        if (lat.compareTo(MIN_LAT) < 0 || lat.compareTo(MAX_LAT) > 0) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR005E003"), fieldName + LAT, MIN_LAT.toPlainString(),MAX_LAT.toPlainString());
            throw new BadRequestException(message);
        }
    }

    /**
     * 日時形式「yyyyMMdd HHmm」チェック
     * @param datetime
     * @param fieldName
     * @throws BadRequestException
     */
    public static void validateDateTimeFormat(String datetime, String fieldName) throws BadRequestException {
        try {
            if(StringUtils.isNotEmpty(datetime)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
                LocalDateTime.parse(datetime, formatter);
            }
        } catch (DateTimeParseException e) { 
            String message = MessageUtils.getMessage("DR005E005",fieldName);
            log.error(message, e);
            throw new BadRequestException(message);
        }
    }
    
    /**
     * 検索期間(FROM)は昨日よりも後の日時チェック
     * @param datetime
     * @param fieldName
     * @throws BadRequestException
     */
    public static void validateMinusDays(String datetime, String fieldName) throws BadRequestException {
        if (StringUtils.isNotEmpty(datetime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
            LocalDateTime startTime = LocalDateTime.parse(datetime, formatter);
            LocalDateTime yesterday = LocalDate.now().minusDays(1).atStartOfDay();
            if (startTime.isBefore(yesterday)) {
                String message = MessageUtils.getMessage("DR005E019", fieldName);
                throw new BadRequestException(message);
            }
        }
    }
    
    /**
     * 検索開始時刻 と 検索終了時刻　チェック
     * @param start
     * @param end
     * @throws BadRequestException
     */
    public static void validateStartBeforeEnd(String start, String end) throws BadRequestException {
        if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
            LocalDateTime startTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endTime = LocalDateTime.parse(end, formatter);
            
            // 終了時刻は開始時刻より後である必要があります
            if (!startTime.isBefore(endTime)) {
                String message = MessageUtils.getMessage("DR005E007");
                throw new BadRequestException(message);
            }
            // 終了時刻が開始時刻＋24時間を超えていたらエラー
            if (endTime.isAfter(startTime.plusHours(24))) {
                String message = MessageUtils.getMessage("DR005E008");
                throw new BadRequestException(message);
            }
        }
    }

    
    /**
     * 更新時刻相関チェック
     * @param datetime
     * @param fieldName
     * @throws BadRequestException
     */
    public static void validateUpdateTime(String datetime, String fieldName) throws BadRequestException {
        try {
            if (StringUtils.isNotEmpty(datetime)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
                LocalDateTime updateTime = LocalDateTime.parse(datetime, formatter);
                LocalDateTime now = LocalDateTime.now();

                if (updateTime.isAfter(now)) {
                    String message = MessageUtils.getMessage("DR005E018", fieldName);
                    throw new BadRequestException(message);
                }
            }
        } catch (DateTimeParseException e) {
            String message = MessageUtils.getMessage("DR005E005",fieldName);
            log.error(message, e);
            throw new BadRequestException(message);
        }
    }

    /**
     * 検索対象利用者チェック
     * @param value
     * @throws BadRequestException
     */
    public static void validateUserTarget(String value) throws BadRequestException {
        if (!"0".equals(value) && !"1".equals(value) && !StringUtils.isEmpty(value)) {
            String message = MessageUtils.getMessage("DR005E009");
            throw new BadRequestException(message);
        }
    }
}
