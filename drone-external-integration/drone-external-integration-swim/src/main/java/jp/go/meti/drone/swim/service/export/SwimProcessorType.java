package jp.go.meti.drone.swim.service.export;

/**
 * 処理タイプのenum
 */
public enum SwimProcessorType {

    /** 新規Excel作成 */
    TYPE_NEW(ProcessorNames.NEW_EXCEL),

    /** 更新Excel作成 */
    TYPE_UPDATE(ProcessorNames.UPDATE_EXCEL),

    /** 取消Excel作成 */
    TYPE_CANCELLATION(ProcessorNames.CANCELLATION_EXCEL);

    private final String beanName;

    SwimProcessorType(String beanName) {
        this.beanName = beanName;
    }

    /**
     * BeanName取得
     * 
     * @return beanName
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * Excel出力処理で使用するBean名の定義
     */
    public static class ProcessorNames {

        /** 新規Excel作成のBean名 */
        public static final String NEW_EXCEL = "newExcelProcessor";

        /** 更新Excel作成のBean名 */
        public static final String UPDATE_EXCEL = "updateExcelProcessor";

        /** 取消Excel作成のBean名 */
        public static final String CANCELLATION_EXCEL = "cancellationExcelProcessor";
    }

    /**
     * 処理種別の列挙型
     */
    public static enum ProcessType {

        /** 新規登録 */
        TYPE_REGIST_NORMAL,

        /** 再登録 */
        TYPE_REGIST_RETRY,

        /** 更新 */
        TYPE_UPDATE_NORMAL,

        /** 再更新 */
        TYPE_UPDATE_RETRY;
    }
}
