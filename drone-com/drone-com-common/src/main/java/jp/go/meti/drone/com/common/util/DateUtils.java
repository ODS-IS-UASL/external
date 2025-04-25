package jp.go.meti.drone.com.common.util;

import static java.util.Optional.ofNullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日付操作用のユーティリティクラス。
 */
public class DateUtils {
    // プライベートコンストラクタを追加
    private DateUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * {@link LocalDateTime}を{@link Date}に変換する。
     * 
     * @param localDateTime 変換元の{@link LocalDateTime}
     * @return 変換した{@link Date}
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return ofNullable(localDateTime).map(lDateTime -> ZonedDateTime.of(lDateTime, ZoneId.systemDefault()))
            .map(ZonedDateTime::toInstant)
            .map(Date::from)
            .orElse(null);
    }

    /**
     * {@link Date}を{@link LocalDateTime}に変換する。
     * 
     * @param date 変換元の{@link Date}
     * @return 変換した{@link LocalDateTime}
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return ofNullable(date).map(arg -> LocalDateTime.ofInstant(arg.toInstant(), ZoneId.systemDefault()))
            .orElse(null);
    }

    /**
     * {@link LocalDateTime}の年度情報を示す数値を取得する。
     * 
     * @param localDateTime 取得元の{@link LocalDateTime}
     * @return 取得した年度情報、{@code null}が指定された場合は{@link IllegalArgumentException}をthrowする
     */
    public static int getFiscalYear(LocalDateTime localDateTime) {
        return ofNullable(localDateTime).map(lDateTime -> lDateTime.minusMonths(3))
            .map(LocalDateTime::getYear)
            .orElseThrow(() -> new IllegalArgumentException(String.format("localDateTime: %s", localDateTime)));
    }

    /**
     * 現在時刻を指定のフォーマットの文字列で取得する。
     * 
     * @param pattern パターン
     * @return 現在時刻
     */
    public static String currentDateTimeToStr(String pattern) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return now.format(formatter);
    }
}
