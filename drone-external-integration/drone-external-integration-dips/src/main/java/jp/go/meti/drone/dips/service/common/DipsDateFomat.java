package jp.go.meti.drone.dips.service.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;

/**
 * DIPSより取得した有効期間のフォーマット
 *
 * @version 1.0 2024/10/30
 */
@Slf4j
public class DipsDateFomat {

    private DipsDateFomat() {
        throw new IllegalStateException("DipsDateFomat class");
    }

    /**
     * 戻り値日付フォーマット
     * <p>
     * yyyy-MM-ddTHH:mm:ss → yyyyMMdd HHmm に変換 例：2022-10-01T09:00:00 → 20221001 0900
     * </p>
     * 
     * @param dateTime 有効期限（FROM、TO）
     * @return 日付
     */
    public static String dipsDateFomat(String dateTime) {

        log.info("戻り値日付:" + dateTime);
        String resTime = "";
        if (dateTime != null) {

            String dateTimeTmp = dateTime.replace("T", " ");
            DateTimeFormatter ifa = DateTimeFormatter.ofPattern(FlightProhibtedConst.DATE_PARTTERN_yyyyMMdd_HHmmss);
            DateTimeFormatter ofa = DateTimeFormatter.ofPattern(FlightProhibtedConst.DATE_PARTTERN_yyyyMMdd_HHmm);
            LocalDateTime date = LocalDateTime.parse(dateTimeTmp, ifa);
            resTime = ofa.format(date);
        }
        log.info("日付:" + dateTime + "-フォーマット結果:" + resTime);
        return resTime;

    }

}
