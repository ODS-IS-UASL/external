package jp.go.meti.drone.dips.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * 日付書式チェック
 * 
 * @version 1.0
 */
@Slf4j
public final class DipsDateUtils {

    private DipsDateUtils() {
        throw new IllegalStateException("DateUtils class");
    }

    /**
     * @param datePattern yyyyMMdd HHmm
     * @param dateTime 検索期間（FROM TO）
     * @return チェック結果
     */
    public static boolean isValidDate(String dateTime, String datePattern) {

        log.info("check date:" + dateTime);
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        df.setLenient(false);
        Date parsedDate;
        try {
            parsedDate = df.parse(dateTime);
        } catch (ParseException e) {
            log.error(e.getMessage() + e);
            return false;
        }
        log.info("isValidDate result:" + df.format(parsedDate).equals(dateTime));
        return df.format(parsedDate).equals(dateTime);

    }
}
