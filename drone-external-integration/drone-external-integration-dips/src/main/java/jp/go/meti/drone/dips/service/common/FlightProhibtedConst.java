package jp.go.meti.drone.dips.service.common;

/**
 * 飛行禁止エリア用定数
 *
 * @version 1.0 2024/10/30
 */
public class FlightProhibtedConst {

    private FlightProhibtedConst() {
        throw new IllegalStateException("FlightProhibtedConst class");
    }

    /** ジオメトリタイプ 多角形 */
    public static final String TYPE = "Polygon";

    /** ジオメトリタイプ 中心 */
    public static final String TYPE_CIRCLE = "Circle";

    /** 経度 最小値 -180 */
    public static final Double MIN_LONGITUDE = -180.0;

    /** 経度 最大値 180 */
    public static final Double MAX_LONGITUDE = 180.0;

    /** 緯度 最小値 -90 */
    public static final Double MIN_LATITUDE = -90.0;

    /** 緯度 最大値 90 */
    public static final Double MAX_LATITUDE = 90.0;

    /** 検索期間(FROM) 時間設定用 */
    public static final String START_TIME = "00";

    /** 検索期間(TO) 時間設定用（時） */
    public static final String END_HOUR = "23";

    /** 検索期間(TO) 時間設定用（分） */
    public static final String END_MIN = "59";

    /** 値域チェック用内部定数 0 */
    public static final Integer ZERO_INTEGER = 0;

    /** 値域チェック用内部定数 1 */
    public static final Integer ONE_INTEGER = 1;

    /** 値域チェック用内部定数 2 */
    public static final Integer TWO_INTEGER = 2;

    /** 値域チェック用内部定数 3 */
    public static final Integer THREE_INTEGER = 3;

    /** 日付書式 yyyyMMdd */
    public static final String DATE_PARTTERN_yyyyMMdd = "yyyyMMdd";

    /** 日付書式 yyyyMMdd HHmm */
    public static final String DATE_PARTTERN_yyyyMMdd_HHmm = "yyyyMMdd HHmm";

    /** 日付書式 yyyy-MM-dd HH:mm:ss */
    public static final String DATE_PARTTERN_yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";

}
