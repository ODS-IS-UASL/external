package jp.go.meti.drone.com.common.mail.control.delivery;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * {@link MailDeliveryControl}のテストクラス。
 * <p>
 * MailDeliveryControlの各メソッドをテストします。
 * </p>
 */
@DisplayName("MailDeliveryControlのテストクラス")
@EnableAutoConfiguration
@SpringBootTest(classes = { MailDeliveryControlTest.Config.class})
public class MailDeliveryControlTest {

    /** 送信対象のメールアドレスその1 */
    static final String INCLUDE_ADDRESS_1 = "include01@mail.address";

    /** 送信対象のメールアドレスその2 */
    static final String INCLUDE_ADDRESS_2 = "include02@mail.address";

    /** 送信対象のメールアドレスその3 */
    static final String INCLUDE_ADDRESS_3 = "include03@mail.address";

    /** 除外対象のメールアドレスその1 */
    static final String EXCLUDE_ADDRESS_1 = "exclude01@mail.address";

    /** 除外対象のメールアドレスその2 */
    static final String EXCLUDE_ADDRESS_2 = "exclude02@mail.address";

    /** 除外対象のメールアドレスその3 */
    static final String EXCLUDE_ADDRESS_3 = "exclude03@mail.address";

    /** 除外処理を行わないタイトルその1（可変部分なし） */
    static final Pattern SEND_ALL_TITLE_1 = Pattern.compile(
        "【ドローン航路基盤システム】手数料の納付のお知らせ \\[DRONE-REG\\] Notification of fee payment");

    /** 除外処理を行わないタイトルその2（可変部分あり） */
    static final Pattern SEND_ALL_TITLE_2 = Pattern.compile("可変部ありの件名.*");

    /** デフォルトメールアドレスリスト */
    static final List<String> DEFAULT_EXCLUDE_ADDRESS_LIST = Arrays.asList(
        EXCLUDE_ADDRESS_1,
        EXCLUDE_ADDRESS_2,
        EXCLUDE_ADDRESS_3);

    /** デフォルトメールアドレスリスト */
    static final List<String> DEFAULT_TITLE_KEY_LIST = Arrays.asList("mailFixedSubject", "mailVariableSubject");

    /** デフォルトタイトルリスト */
    static final List<Pattern> DEFAULT_TITLE_LIST = Arrays.asList(SEND_ALL_TITLE_1, SEND_ALL_TITLE_2);

    /**
     * テスト対象
     */
    @Autowired
    MailDeliveryControl target;


    /**
     * テスト用コンフィギュレーションクラス
     */
    @Configuration
    @ComponentScan(basePackages = "jp.go.meti.drone.com.common.mail.control.delivery")
    static class Config {
    };


    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系1：送信先アドレスリストから除外対象アドレスが除外されることを確認する。
     * </p>
     * <ul>
     * <li>検証：送信先アドレスから正しく抑止対象のアドレスが除外されること。(除外されないアドレスあり)</li>
     * <li>除外対象のメールアドレスが除外されていること</li>
     * <li>除外対象ではないメールアドレスが除外されていないこと</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系1：送信先アドレスリストから除外対象アドレスが除外されることを確認する。")
    void testcreateActualToAddressArray01() throws Exception {
        // 引数を準備
        String subject = "title";
        String[] to = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3, EXCLUDE_ADDRESS_1,
                EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // 期待値を準備
        String[] expected = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3 };

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject, to);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();

    }

    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系2：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（全て除外対象の場合）
     * </p>
     * <ul>
     * <li>検証：送信先アドレスから正しく抑止対象のアドレスが除外されること。(除外されないアドレスなし)</li>
     * <li>空配列が返却されること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系2：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（全て除外対象の場合）")
    void testcreateActualToAddressArray02() throws Exception {
        // 引数を準備
        String subject = "title";
        String[] to = new String[] { EXCLUDE_ADDRESS_1, EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };
        // 期待値を準備
        String[] expected = new String[] {};

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject, to);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();

    }

    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系3：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（除外対象が含まれない場合）
     * </p>
     * <ul>
     * <li>検証：送信先アドレスから正しく抑止対象のアドレスが除外されること。(除外されるアドレスなし)</li>
     * <li>除外対象ではないメールアドレスが除外されていないこと</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系3：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（除外対象が含まれない場合）")
    void testcreateActualToAddressArray03() throws Exception {
        // 引数を準備
        String subject = "title";
        String[] to = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3 };

        // 期待値を準備
        String[] expected = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3 };

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject, to);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();
    }

    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系4：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（タイトルが送信制御対象外の場合）
     * </p>
     * <ul>
     * <li>検証：制御対象外のタイトルの場合抑止対象のアドレスが除外されないこと。(可変部分なしのタイトル)</li>
     * <li>除外対象のメールアドレスが除外されていないこと</li>
     * <li>除外対象ではないメールアドレスが除外されていないこと</li>
     * </ul>
     * </p>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系4：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（タイトルが送信制御対象外の場合）")
    void testcreateActualToAddressArray04() throws Exception {
        // 引数を準備
        String subject = "可変部なしの件名";
        String[] to = new String[] { EXCLUDE_ADDRESS_1, EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // 期待値を準備
        String[] expected = new String[] { EXCLUDE_ADDRESS_1, EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject, to);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();
    }

    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系5：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（タイトルが送信制御対象外の場合）
     * </p>
     * <ul>
     * <li>検証：制御対象外のタイトルの場合抑止対象のアドレスが除外されないこと。(可変部分ありのタイトル)</li>
     * <li>除外対象のメールアドレスが除外されていないこと</li>
     * <li>除外対象ではないメールアドレスが除外されていないこと</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系5：送信先アドレスリストから除外対象アドレスが除外されることを確認する。（タイトルが送信制御対象外の場合）")
    void testcreateActualToAddressArray05() throws Exception {
        // 引数を準備
        String subject = "可変部ありの件名any";
        String[] to = new String[] { EXCLUDE_ADDRESS_1, EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // 期待値を準備
        String[] expected = new String[] { EXCLUDE_ADDRESS_1, EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject, to);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();
    }

    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系6：送信先アドレスリストを渡さなかったときにもエラーにならないことの確認
     * </p>
     * <ul>
     * <li>検証：アドレスにnullを渡した場合に問題なく動作すること</li>
     * <li>空の配列が返却されること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系6：送信先アドレスリストを渡さなかったときにもエラーにならないことの確認")
    void testcreateActualToAddressArray06() throws Exception {
        // 引数を準備
        String subject = "title";

        // 期待値を準備
        String[] expected = new String[] {};

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();
    }

    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系7：抑止対象アドレスリストが未設定（空リスト）の場合にエラーにならないことの確認
     * </p>
     * <ul>
     * <li>検証：抑止対象アドレスが設定されていない場合に問題なく動作すること</li>
     * <li>エラーにならないこと</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系7：抑止対象アドレスリストが未設定（空リスト）の場合にエラーにならないことの確認")
    void testcreateActualToAddressArray07() throws Exception {
        // 試験対象を作り直す
        MailDeliveryControlProperties props = new MailDeliveryControlProperties();
        props.setExcludeAddresses(Collections.emptyList());
        props.setDeliveryAllSubjectKeys(DEFAULT_TITLE_KEY_LIST);

        target = new MailDeliveryControl(props);
        target.initialize();

        // 引数を準備
        String subject = "title";
        String[] to = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3, EXCLUDE_ADDRESS_1,
                EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // 期待値を準備
        String[] expected = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3, EXCLUDE_ADDRESS_1,
                EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject, to);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();
    }

    /**
     * createActualToAddressArrayメソッドのテスト
     * <p>
     * 正常系8：送信制御対象外タイトルリストが未設定（空リスト）の場合にエラーにならないこと
     * </p>
     * <ul>
     * <li>検証：制御対象外タイトルリストが設定されていない場合に問題なく動作すること</li>
     * <li>エラーにならないこと</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系8：送信制御対象外タイトルリストが未設定（空リスト）の場合にエラーにならないこと")
    void testcreateActualToAddressArray08() throws Exception {
        // 試験対象を作り直す
        MailDeliveryControlProperties props = new MailDeliveryControlProperties();
        props.setExcludeAddresses(DEFAULT_EXCLUDE_ADDRESS_LIST);
        props.setDeliveryAllSubjectKeys(Collections.emptyList());

        target = new MailDeliveryControl(props);
        target.initialize();

        // 引数を準備
        String subject = "title";
        String[] to = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3, EXCLUDE_ADDRESS_1,
                EXCLUDE_ADDRESS_2, EXCLUDE_ADDRESS_3 };

        // 期待値を準備
        String[] expected = new String[] { INCLUDE_ADDRESS_1, INCLUDE_ADDRESS_2, INCLUDE_ADDRESS_3 };

        // メソッドの呼び出し
        String[] actual = target.createActualToAddressArray(subject, to);

        // 結果の検証
        assertThat(Objects.deepEquals(expected, actual)).isTrue();
    }

    /**
     * initializeメソッドのテスト
     * <p>
     * 正常系9：プロパティが未設定（null）の場合にエラーにならないこと
     * </p>
     * <ul>
     * <li>検証：メール送信制御関連の設定値が定義されていない場合に問題なく動作すること</li>
     * <li>エラーにならないこと</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系9：プロパティが未設定（null）の場合にエラーにならないこと")
    void testInitialize01() throws Exception {
        // 試験対象を作り直す
        MailDeliveryControlProperties props = new MailDeliveryControlProperties();
        props.setExcludeAddresses(null);
        props.setDeliveryAllSubjectKeys(null);

        target = new MailDeliveryControl(props);

        // メソッドの呼び出し
        target.initialize();

        // 結果の検証
        assertThat(target.getExcludeAddresses()).isNotNull();
        assertThat(target.getExcludeAddresses()).isEmpty();
        assertThat(target.getDeliveryAllSubjectPatterns()).isNotNull();
        assertThat(target.getDeliveryAllSubjectPatterns()).isEmpty();
    }

    /**
     * initializeメソッドのテスト
     * <p>
     * 正常系10：除外処理を行わないタイトルとして指定されたキーが誤っていた場合にエラーにならないこと
     * </p>
     * <ul>
     * <li>検証：タイトルキーに誤ったキーが含まれていた   場合に問題なく動作すること</li>
     * <li>エラーにならないこと</li>
     * <li>誤ったキーがタイトルリストに含まれないこと</li>
     * <li>正しいキーがタイトルリストに含まれること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系10：除外処理を行わないタイトルとして指定されたキーが誤っていた場合にエラーにならないこと")
    void testInitialize02() throws Exception {
        // 試験対象を作り直す
        MailDeliveryControlProperties props = new MailDeliveryControlProperties();
        props.setExcludeAddresses(Collections.emptyList());
        props.setDeliveryAllSubjectKeys(Arrays.asList("mailVariableSubject", "illegal.key"));

        target = new MailDeliveryControl(props);

        // メソッドの呼び出し
        target.initialize();

        // 結果の検証
        assertThat(target.getExcludeAddresses()).isEmpty();
        assertThat(target.getDeliveryAllSubjectPatterns()).isNotEmpty();
        assertThat(target.getDeliveryAllSubjectPatterns()).size().isEqualTo(1);
        assertThat(target.getDeliveryAllSubjectPatterns().get(0).pattern()).isEqualTo(SEND_ALL_TITLE_2.pattern());
    }
}
