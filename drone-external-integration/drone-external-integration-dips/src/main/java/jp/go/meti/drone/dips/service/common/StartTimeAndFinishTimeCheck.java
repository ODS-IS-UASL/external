package jp.go.meti.drone.dips.service.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.com.DipsDateUtils;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;

/**
 * 検索期間（FROM、TO）チェック
 * 
 * @version 1.0 2024/10/30
 */
public class StartTimeAndFinishTimeCheck {

    private StartTimeAndFinishTimeCheck() {
        throw new IllegalStateException("StartTimeAndFinishTimeCheck class");
    }

    /**
     * 有効期限（FROM）チェック
     * <p>
     * リクエストパラメータ「有効期限（FROM）」が設定されない場合、このメソッドでデフォルト値を設定する<br/>
     * 例：20240910 0000
     * <p/>
     * 
     * @param startTime 有効期限（FROM）
     * @return 日付
     */
    public static String startTimeCheck(String startTime) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(FlightProhibtedConst.DATE_PARTTERN_yyyyMMdd);
        LocalDate localDate = LocalDate.now();

        if (startTime == null || startTime.isBlank()) {
            // 形式： yyyyMMdd HHmm (20240910 0000)
            startTime = localDate.format(dateFormat) + " " + FlightProhibtedConst.START_TIME
                + FlightProhibtedConst.START_TIME;
            return startTime;
        } else {
            if (!DipsDateUtils.isValidDate(startTime, FlightProhibtedConst.DATE_PARTTERN_yyyyMMdd_HHmm)) {
                String messageTmp = MessageUtils.getMessage("DR001E005");
                throw new CommonResponseBadRequestError(400, messageTmp);
            }
        }
        return startTime;
    }

    /**
     * 有効期限（TO）チェック
     * <p>
     * リクエストパラメータ「有効期限（TO）」が設定されない場合、このメソッドでデフォルト値を設定する<br/>
     * 例：20240910 2359
     * <p/>
     * 
     * @param finishTime 有効期限（TO）
     * @return 日付
     */
    public static String finishTimeCheck(String finishTime) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(FlightProhibtedConst.DATE_PARTTERN_yyyyMMdd);
        LocalDate localDate = LocalDate.now();

        if (finishTime == null || finishTime.isBlank()) {
            // 形式： yyyyMMdd HHmm (20240910 2359)
            finishTime = localDate.format(dateFormat) + " " + FlightProhibtedConst.END_HOUR
                + FlightProhibtedConst.END_MIN;
            return finishTime;
        } else {
            if (!DipsDateUtils.isValidDate(finishTime, FlightProhibtedConst.DATE_PARTTERN_yyyyMMdd_HHmm)) {
                String messageTmp = MessageUtils.getMessage("DR001E005");
                throw new CommonResponseBadRequestError(400, messageTmp);
            }
        }
        return finishTime;
    }

}
