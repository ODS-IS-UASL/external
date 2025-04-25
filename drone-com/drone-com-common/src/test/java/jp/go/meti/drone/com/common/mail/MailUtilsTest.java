package jp.go.meti.drone.com.common.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jp.go.meti.drone.com.common.date.SystemDate;
import jp.go.meti.drone.com.common.exception.DroneRuntimeException;
import jp.go.meti.drone.com.common.mail.control.delivery.MailDeliveryControl;

/**
 * {@link MailUtils}のテストクラス。
 * <p>
 * MailUtilsの各メソッドをテストします。
 * </p>
 */
@DisplayName("MailUtilsのテストクラス")
@ActiveProfiles("mail-test")
@EnableAutoConfiguration
@SpringBootTest(classes = { MailUtilsConfig.class, MailDeliveryControl.class })
class MailUtilsTest {

    final GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP);

    @BeforeEach
    void before() {
        doReturn(LocalDateTime.now(Clock.systemDefaultZone())).when(systemDate).now();
        greenMail.setUser("username", "secret");
        greenMail.start();
    }

    @AfterEach
    void after() {
        greenMail.stop();
    }

    /**
     * テスト対象
     */
    @Autowired
    MailUtils target;

    /**
     * FreeMarker用の設定情報
     */
    @SpyBean
    Configuration freemarkerConfiguration;

    @SpyBean
    JavaMailSender mailSender;

    /**
     * システム日時のMock
     */
    @MockBean
    SystemDate systemDate;

    /**
     * 送信元メールアドレス
     */
    @Value("${mail.template.fromAddress}")
    private String fromAddress;

    /**
     * 送信元表示名
     */
    @Value("${mail.template.fromName}")
    private String fromName;

    @Test
    void test_targetBean() {
        assertThat(target).isExactlyInstanceOf(MailUtilsImpl.class);
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * 正常系1：テンプレートなしのメール送信 メールが送信できることを確認する。
     * </p>
     * <ul>
     * <li>検証：メールが1通送信されること</li>
     * <li>fromにtemplateMessageで初期設定されているaddressが設定されていること</li>
     * <li>toに入力したaddressが設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系1：テンプレートなしのメール送信　メールが送信できることを確認する。")
    void testSendMail_normal01() throws Exception {
        // 入力値の準備
        String to = "foo@example.com";
        String subject = "日本語の件名が正しく送信されているか.";
        String text = "こんにちは、 " + to + "本文も日本語を含めて確認" + System.lineSeparator() + "改行コードの後の文章。";

        // メソッドの呼び出し
        target.sendMail(subject, text, to);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] tos = Arrays.array(new InternetAddress(to));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1).extracting(Message::getFrom).containsExactly(froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(tos);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly(subject);
        assertThat(receivedMessages).extracting(Message::getContent).containsExactly(text);
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * 正常系2：テンプレートなしのメール送信 メールが送信できることを確認する（toが3件）。
     * </p>
     * <ul>
     * <li>検証：メールが3通送信されること</li>
     * <li>fromにtemplateMessageで初期設定されているaddressが設定されていること</li>
     * <li>toに入力したaddressが3件全て設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系2：テンプレートなしのメール送信　メールが送信できることを確認する（toが3件）。")
    void testSendMail_normal02() throws Exception {
        // 入力値の準備
        String to1 = "foo@example.com";
        String to2 = "bar@example.com";
        String to3 = "baz@example.com";
        String subject = "日本語の件名が正しく送信されているか.";
        String text = "こんにちは、 " + to1 + "本文も日本語を含めて確認" + System.lineSeparator() + "改行コードの後の文章。";

        // メソッドの呼び出し
        target.sendMail(subject, text, to1, to2, to3);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] to123 = Arrays.array(new InternetAddress(to1), new InternetAddress(to2), new InternetAddress(to3));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(3).extracting(Message::getFrom).containsExactly(froms, froms, froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(to123, to123, to123);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly(subject, subject, subject);
        assertThat(receivedMessages).extracting(Message::getContent).containsExactly(text, text, text);
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * 異常系1：テンプレートなしのメール送信 MessagingException発生時に例外スローする。
     * </p>
     * <ul>
     * <li>検証：MessagingExceptionが発生する</li>
     * <li>メッセージに解析用の入力情報（subject、text、to）の情報を設定している</li>
     * <li>Causeに発生元の例外を設定している</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("異常系1：テンプレートなしのメール送信　MessagingException発生時に例外スローする。")
    void testSendMail_error01() throws Exception {
        // 入力値の準備
        String to1 = "foo@example.com";
        String to2 = "bar@example.com";
        String to3 = "baz@example.com";
        String subject = "日本語の件名が正しく送信されているか.";
        String text = "こんにちは、 " + to1 + "本文も日本語を含めて確認" + System.lineSeparator() + "改行コードの後の文章。";

        // モックの作成と例外検証用の期待値設定
        Exception cause = new MessagingException();
        InternetAddress internetAddress = new InternetAddress(fromAddress, fromName);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        doReturn(mimeMessage).when(mailSender).createMimeMessage();
        doThrow(cause).when(mimeMessage).setFrom(internetAddress);

        // メソッドの呼び出し
        RuntimeException thrown = assertThrows(
            RuntimeException.class,
            () -> target.sendMail(subject, text, to1, to2, to3));

        // 結果の検証
        assertThat(thrown).hasCause(cause)
            .hasMessage(
                "subject: [" + subject + "], text: [" + text + "], to: [" + to1 + ", " + to2 + ", " + to3 + "]");
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション1：テンプレートなしのメール送信 件名がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション1：テンプレートなしのメール送信　件名がnull")
    void testSendMail_valid01() throws Exception {
        // 入力値の準備
        String to = "foo@example.com";
        String subject = null;
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subject, text, to));

        // 結果の検証
        assertThat(thrown).hasMessage("subject: [null], text: [テスト本文], to: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション2：テンプレートなしのメール送信 件名が空
     * </p>
     * <ul>
     * <li>検証：メールが1通送信されること</li>
     * <li>fromにtemplateMessageで初期設定されているaddressが設定されていること</li>
     * <li>toに入力したaddressが設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション2：テンプレートなしのメール送信 件名が空")
    void testSendMail_valid02() throws Exception {
        // 入力値の準備
        String to = "foo@example.com";
        String subject = "";
        String text = "テスト本文";

        // メソッドの呼び出し
        target.sendMail(subject, text, to);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] tos = Arrays.array(new InternetAddress(to));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1).extracting(Message::getFrom).containsExactly(froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(tos);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly(subject);
        assertThat(receivedMessages).extracting(Message::getContent).containsExactly(text);
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション3：テンプレートなしのメール送信 本文がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション3：テンプレートなしのメール送信　本文がnull")
    void testSendMail_valid03() throws Exception {
        // 入力値の準備
        String to = "foo@example.com";
        String subject = "テスト件名";
        String text = null;

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subject, text, to));

        // 結果の検証
        assertThat(thrown).hasMessage("subject: [テスト件名], text: [null], to: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション4：テンプレートなしのメール送信 本文が空
     * </p>
     * <ul>
     * <li>検証：メールが1通送信されること</li>
     * <li>fromにtemplateMessageで初期設定されているaddressが設定されていること</li>
     * <li>toに入力したaddressが設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション4：テンプレートなしのメール送信 本文が空")
    void testSendMail_valid04() throws Exception {
        // 入力値の準備
        String to = "foo@example.com";
        String subject = "テスト件名";
        String text = "";

        // メソッドの呼び出し
        target.sendMail(subject, text, to);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] tos = Arrays.array(new InternetAddress(to));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1).extracting(Message::getFrom).containsExactly(froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(tos);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly(subject);
        assertThat(receivedMessages).extracting(Message::getContent).containsExactly(text);
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション5：テンプレートなしのメール送信 宛先の配列がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション5：テンプレートなしのメール送信　宛先の配列がnull")
    void testSendMail_valid05() throws Exception {
        // 入力値の準備
        String to = null;
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subject, text, to));

        // 結果の検証
        assertThat(thrown).hasMessage("subject: [テスト件名], text: [テスト本文], to: [null]");
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション6：テンプレートなしのメール送信 宛先の配列が空
     * </p>
     * <ul>
     * <li>検証：例外が発生せず正常終了する</li>
     * </ul>
     */
    @Test
    @DisplayName("バリデーション6：テンプレートなしのメール送信　宛先の配列が空")
    void testSendMail_valid06() throws Exception {
        // 入力値の準備
        String[] to = new String[0];
        String subject = "テスト件名";
        String text = "テスト本文";

        assertDoesNotThrow(() -> {
            // メソッドの呼び出し
            target.sendMail(subject, text, to);
        });
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション7：テンプレートなしのメール送信 宛先の1件目がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション7：テンプレートなしのメール送信　宛先の1件目がnull")
    void testSendMail_valid07() throws Exception {
        // 入力値の準備
        String to1 = null;
        String to2 = "bar@example.com";
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subject, text, to1, to2));

        // 結果の検証
        assertThat(thrown).hasMessage("subject: [テスト件名], text: [テスト本文], to: [null, bar@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション8：テンプレートなしのメール送信 宛先の1件目が空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション8：テンプレートなしのメール送信　宛先の1件目が空")
    void testSendMail_valid08() throws Exception {
        // 入力値の準備
        String to1 = "";
        String to2 = "bar@example.com";
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subject, text, to1, to2));

        // 結果の検証
        assertThat(thrown).hasMessage("subject: [テスト件名], text: [テスト本文], to: [, bar@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション9：テンプレートなしのメール送信 宛先の2件目がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション9：テンプレートなしのメール送信　宛先の2件目がnull")
    void testSendMail_valid09() throws Exception {
        // 入力値の準備
        String to1 = "foo@example.com";
        String to2 = null;
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subject, text, to1, to2));

        // 結果の検証
        assertThat(thrown).hasMessage("subject: [テスト件名], text: [テスト本文], to: [foo@example.com, null]");
    }

    /**
     * sendMailメソッド（テンプレートなし）のテスト
     * <p>
     * バリデーション10：テンプレートなしのメール送信 宛先の2件目が空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション10：テンプレートなしのメール送信　宛先の2件目が空")
    void testSendMail_valid10() throws Exception {
        // 入力値の準備
        String to1 = "foo@example.com";
        String to2 = "";
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subject, text, to1, to2));

        // 結果の検証
        assertThat(thrown).hasMessage("subject: [テスト件名], text: [テスト本文], to: [foo@example.com, ]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * 正常系1：テンプレートありのメール送信 メールが送信できることを確認する。
     * </p>
     * <ul>
     * <li>検証：メールが1通送信されること</li>
     * <li>fromにtemplateMessageで初期設定されているaddressが設定されていること</li>
     * <li>toに入力したaddressが設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系1：テンプレートありのメール送信　メールが送信できることを確認する。")
    void testSendMail_withTemplate_normal01() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        target.sendMail(subjectId, templateFileName, model, toList);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] tos = Arrays.array(new InternetAddress(to));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1).extracting(Message::getFrom).containsExactly(froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(tos);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly("テスト件名");
        assertThat(receivedMessages).extracting(Message::getContent)
            .containsExactly("メール部品テスト用のフォーマット。\r\n" + "foo： ふー\r\n" + "bar： ばー\r\n" + "baz： ばず");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * 正常系2：テンプレートありのメール送信 メールが送信できることを確認する（toが3件）。
     * </p>
     * <ul>
     * <li>検証：メールが3通送信されること</li>
     * <li>fromにtemplateMessageで初期設定されているaddressが設定されていること</li>
     * <li>toに入力したaddressが3件全て設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系2：テンプレートありのメール送信　メールが送信できることを確認する（toが3件）。")
    void testSendMail_withTemplate_normal02() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        String to1 = "foo@example.com";
        String to2 = "bar@example.com";
        String to3 = "baz@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to1);
        toList.add(to2);
        toList.add(to3);

        // メソッドの呼び出し
        target.sendMail(subjectId, templateFileName, model, toList);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] to123 = Arrays.array(new InternetAddress(to1), new InternetAddress(to2), new InternetAddress(to3));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(3).extracting(Message::getFrom).containsExactly(froms, froms, froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(to123, to123, to123);
        String subject = "テスト件名";
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly(subject, subject, subject);
        String text = "メール部品テスト用のフォーマット。\r\nfoo： ふー\r\nbar： ばー\r\nbaz： ばず";
        assertThat(receivedMessages).extracting(Message::getContent).containsExactly(text, text, text);
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション1：テンプレートありのメール送信 件名IDがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション1：テンプレートありのメール送信　件名IDがnull")
    void testSendMail_withTemplate_valid01() throws Exception {
        // 入力値の準備
        String subjectId = null;

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [null], templateFileName: [test-MailUtilsTest.ftl], model: {foo=ふー, bar=ばー, baz=ばず}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション2：テンプレートありのメール送信 件名IDが空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション2：テンプレートありのメール送信　件名IDが空")
    void testSendMail_withTemplate_valid02() throws Exception {
        // 入力値の準備
        String subjectId = "";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [], templateFileName: [test-MailUtilsTest.ftl], model: {foo=ふー, bar=ばー, baz=ばず}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション3：テンプレートありのメール送信 テンプレートファイル名がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション3：テンプレートありのメール送信　テンプレートファイル名がnull")
    void testSendMail_withTemplate_valid03() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = null;
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [null], model: {foo=ふー, bar=ばー, baz=ばず}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション4：テンプレートありのメール送信 テンプレートファイル名が空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション4：テンプレートありのメール送信　テンプレートファイル名が空")
    void testSendMail_withTemplate_valid04() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [], model: {foo=ふー, bar=ばー, baz=ばず}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション5：テンプレートありのメール送信 モデルがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション5：テンプレートありのメール送信　モデルがnull")
    void testSendMail_withTemplate_valid05() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = null;

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [test-MailUtilsTest.ftl], model: null, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション6：テンプレートありのメール送信 モデルが空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション6：テンプレートありのメール送信　モデルが空")
    void testSendMail_withTemplate_valid06() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [test-MailUtilsTest.ftl], model: {}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション7：テンプレートありのメール送信 モデルのkeyがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション7：テンプレートありのメール送信　モデルのkeyがnull")
    void testSendMail_withTemplate_valid07() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put(null, "ばー");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [test-MailUtilsTest.ftl], model: {foo=ふー, null=ばー, baz=ばず}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション8：テンプレートありのメール送信 モデルのkeyが空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション8：テンプレートありのメール送信　モデルのkeyが空")
    void testSendMail_withTemplate_valid08() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("", "ばー");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [test-MailUtilsTest.ftl], model: {foo=ふー, =ばー, baz=ばず}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション9：テンプレートありのメール送信 モデルのvalueがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション9：テンプレートありのメール送信　モデルのvalueがnull")
    void testSendMail_withTemplate_valid09() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", null);
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [test-MailUtilsTest.ftl], model: {foo=ふー, bar=null, baz=ばず}, toList: [foo@example.com]");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション10：テンプレートありのメール送信 モデルのvalueが空
     * </p>
     * <ul>
     * <li>検証：メールが1通送信されること</li>
     * <li>fromにtemplateMessageで初期設定されているaddressが設定されていること</li>
     * <li>toに入力したaddressが設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     */
    @Test
    @DisplayName("バリデーション10：テンプレートありのメール送信　モデルのvalueが空")
    void testSendMail_withTemplate_valid10() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "");
        model.put("baz", "ばず");

        String to = "foo@example.com";
        List<String> toList = new ArrayList<>();
        toList.add(to);

        // メソッドの呼び出し
        target.sendMail(subjectId, templateFileName, model, toList);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] tos = Arrays.array(new InternetAddress(to));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1).extracting(Message::getFrom).containsExactly(froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(tos);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly("テスト件名");
        assertThat(receivedMessages).extracting(Message::getContent)
            .containsExactly("メール部品テスト用のフォーマット。\r\n" + "foo： ふー\r\n" + "bar： \r\n" + "baz： ばず");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション11：テンプレートありのメール送信 宛先リストがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション11：テンプレートありのメール送信　宛先リストがnull")
    void testSendMail_withTemplate_valid11() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        List<String> toList = null;

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMail(subjectId, templateFileName, model, toList));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "subjectId: [mailSubject], templateFileName: [test-MailUtilsTest.ftl], model: {foo=ふー, bar=ばー, baz=ばず}, toList: null");
    }

    /**
     * sendMailメソッド（テンプレートあり）のテスト
     * <p>
     * バリデーション12：テンプレートありのメール送信 宛先リストが空
     * </p>
     * <ul>
     * <li>検証：例外が発生せず正常終了する</li>
     * </ul>
     */
    @Test
    @DisplayName("バリデーション12：テンプレートありのメール送信 宛先リストが空")
    void testSendMail_withTemplate_valid12() throws Exception {
        // 入力値の準備
        String subjectId = "mailSubject";

        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        List<String> toList = new ArrayList<>();

        assertDoesNotThrow(() -> {
            // メソッドの呼び出し
            target.sendMail(subjectId, templateFileName, model, toList);
        });
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * 正常系1：テンプレート取得 指定したテンプレートにモデルの情報を埋め込んだ文字列を取得できる。
     * </p>
     * <ul>
     * <li>検証：templateFileNameに入力した名称を使用してテンプレートを取得している</li>
     * <li>テンプレートにmodelとして入力した内容を埋め込んだ文字列が取得できる</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系1：テンプレート取得　指定したテンプレートにモデルの情報を埋め込んだ文字列を取得できる。")
    void testGetTextFromTemplate_normal01() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        // メソッドの呼び出し
        String resultText = target.getTextFromTemplate(templateFileName, model);

        // 結果の検証
        verify(freemarkerConfiguration).getTemplate(templateFileName);
        assertThat(resultText).isEqualTo("メール部品テスト用のフォーマット。\r\n" + "foo： ふー\r\n" + "bar： ばー\r\n" + "baz： ばず");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * 異常系1：テンプレート取得 IOException発生時に非検査例外に置き換えて例外スローする。
     * </p>
     * <ul>
     * <li>検証：UncheckedIOExceptionが発生する</li>
     * <li>メッセージに解析用の入力情報（templateFilaName、model）の情報を設定している</li>
     * <li>Causeに発生元の例外を設定している</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("異常系1：テンプレート取得　IOException発生時に非検査例外に置き換えて例外スローする。")
    void testGetTextFromTemplate_error01() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();// toString()の出力順固定
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        // モックの作成と例外検証用の期待値設定
        Exception cause = new IOException();
        doThrow(cause).when(freemarkerConfiguration).getTemplate(templateFileName);

        // メソッドの呼び出し
        UncheckedIOException thrown = assertThrows(
            UncheckedIOException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasCause(cause)
            .hasMessage("templateFileName: [test-MailUtilsTest.ftl], model: [{foo=ふー, bar=ばー, baz=ばず}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * 異常系2：テンプレート取得 TemplateException発生時に非検査例外に置き換えて例外スローする。
     * </p>
     * <ul>
     * <li>検証：RuntimeExceptionが発生する</li>
     * <li>メッセージに解析用の入力情報（templateFilaName、model）の情報を設定している</li>
     * <li>Causeに発生元の例外を設定している</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("異常系2：テンプレート取得　TemplateException発生時に非検査例外に置き換えて例外スローする。")
    void testGetTextFromTemplate_error02() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();// toString()の出力順固定
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        // モックの作成と例外検証用の期待値設定
        Exception cause = new TemplateException(null);
        Template template = mock(Template.class);
        doThrow(cause).when(template).process(any(), any());
        doReturn(template).when(freemarkerConfiguration).getTemplate(templateFileName);

        // メソッドの呼び出し
        RuntimeException thrown = assertThrows(
            RuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasCause(cause)
            .hasMessage("templateFileName: [test-MailUtilsTest.ftl], model: [{foo=ふー, bar=ばー, baz=ばず}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション1：テンプレート取得 テンプレートファイル名がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション1：テンプレート取得 テンプレートファイル名がnull")
    void testGetTextFromTemplate_valid01() throws Exception {
        // 入力値の準備
        String templateFileName = null;
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasMessage("templateFileName: [null], model: [{foo=ふー, bar=ばー, baz=ばず}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション2：テンプレート取得 テンプレートファイル名が空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション2：テンプレート取得 テンプレートファイル名が空")
    void testGetTextFromTemplate_valid02() throws Exception {
        // 入力値の準備
        String templateFileName = "";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "ばー");
        model.put("baz", "ばず");

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasMessage("templateFileName: [], model: [{foo=ふー, bar=ばー, baz=ばず}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション3：テンプレート取得 モデルがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション3：テンプレート取得 モデルがnull")
    void testGetTextFromTemplate_valid03() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = null;

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasMessage("templateFileName: [test-MailUtilsTest.ftl], model: [null]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション4：テンプレート取得 モデルが空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション4：テンプレート取得 モデルが空")
    void testGetTextFromTemplate_valid04() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasMessage("templateFileName: [test-MailUtilsTest.ftl], model: [{}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション5：テンプレート取得 モデルのkeyがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション5：テンプレート取得 モデルのkeyがnull")
    void testGetTextFromTemplate_valid05() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put(null, "ばー");
        model.put("baz", "ばず");

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasMessage("templateFileName: [test-MailUtilsTest.ftl], model: [{foo=ふー, null=ばー, baz=ばず}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション6：テンプレート取得 モデルのkeyが空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション6：テンプレート取得 モデルのkeyが空")
    void testGetTextFromTemplate_valid06() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("", "ばー");
        model.put("baz", "ばず");

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasMessage("templateFileName: [test-MailUtilsTest.ftl], model: [{foo=ふー, =ばー, baz=ばず}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション7：テンプレート取得 モデルのvalueがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション7：テンプレート取得 モデルのvalueがnull")
    void testGetTextFromTemplate_valid07() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", null);
        model.put("baz", "ばず");

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.getTextFromTemplate(templateFileName, model));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "templateFileName: [test-MailUtilsTest.ftl], model: [{foo=ふー, bar=null, baz=ばず}]");
    }

    /**
     * getTextFromTemplateメソッドのテスト
     * <p>
     * バリデーション8：テンプレート取得 モデルのvalueが空
     * </p>
     * <ul>
     * <li>検証：templateFileNameに入力した名称を使用してテンプレートを取得している</li>
     * <li>テンプレートにmodelとして入力した内容を埋め込んだ文字列が取得できる</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション8：テンプレート取得 モデルのvalueが空")
    void testGetTextFromTemplate_valid08() throws Exception {
        // 入力値の準備
        String templateFileName = "test-MailUtilsTest.ftl";
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("foo", "ふー");
        model.put("bar", "");
        model.put("baz", "ばず");

        // メソッドの呼び出し
        String resultText = target.getTextFromTemplate(templateFileName, model);

        // 結果の検証
        verify(freemarkerConfiguration).getTemplate(templateFileName);
        assertThat(resultText).isEqualTo("メール部品テスト用のフォーマット。\r\n" + "foo： ふー\r\n" + "bar： \r\n" + "baz： ばず");
    }

    /**
     * sendMailWithFromメソッドのテスト
     * <p>
     * 正常系1：送信元を指定してメール送信 メールが送信できることを確認する。
     * </p>
     * <ul>
     * <li>検証：メールが1通送信されること</li>
     * <li>fromに入力したaddressが設定されていること</li>
     * <li>toに入力したaddressが設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("正常系1：送信元を指定してメール送信　メールが送信できることを確認する。")
    void testSendMailWithFrom_normal01() throws Exception {
        // 入力値の準備
        String fromAddress = "with.from@example.com";
        String fromName = "送信元指定テスト";
        String to = "foo@example.com";
        String subject = "日本語の件名が正しく送信されているか.";
        String text = "こんにちは、 " + to + "本文も日本語を含めて確認" + System.lineSeparator() + "改行コードの後の文章。";

        // メソッドの呼び出し
        target.sendMailWithFrom(fromAddress, fromName, subject, text, to);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] tos = Arrays.array(new InternetAddress(to));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1).extracting(Message::getFrom).containsExactly(froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(tos);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly(subject);
        assertThat(receivedMessages).extracting(Message::getContent).containsExactly(text);
    }

    /**
     * sendMailWithFromメソッドのテスト
     * <p>
     * バリデーション1：送信元を指定してメール送信 送信元アドレスがnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション1：送信元を指定してメール送信 送信元アドレスがnull")
    void testSendMailWithFrom_valid01() throws Exception {
        // 入力値の準備
        String fromAddress = null;
        String fromName = "送信元指定テスト";
        String to = "foo@example.com";
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMailWithFrom(fromAddress, fromName, subject, text, to));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "fromAddress: [null], fromName: [送信元指定テスト], subject: [テスト件名], text: [テスト本文], to: [foo@example.com]");
    }

    /**
     * sendMailWithFromメソッドのテスト
     * <p>
     * バリデーション2：送信元を指定してメール送信 送信元アドレスが空
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション2：送信元を指定してメール送信 送信元アドレスが空")
    void testSendMailWithFrom_valid02() throws Exception {
        // 入力値の準備
        String fromAddress = "";
        String fromName = "送信元指定テスト";
        String to = "foo@example.com";
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMailWithFrom(fromAddress, fromName, subject, text, to));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "fromAddress: [], fromName: [送信元指定テスト], subject: [テスト件名], text: [テスト本文], to: [foo@example.com]");
    }

    /**
     * sendMailWithFromメソッドのテスト
     * <p>
     * バリデーション3：送信元を指定してメール送信 送信元の名前がnull
     * </p>
     * <ul>
     * <li>検証：DroneRuntimeExceptionが発生する</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション3：送信元を指定してメール送信 送信元の名前がnull")
    void testSendMailWithFrom_valid03() throws Exception {
        // 入力値の準備
        String fromAddress = "with.from@example.com";
        String fromName = null;
        String to = "foo@example.com";
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        DroneRuntimeException thrown = assertThrows(
            DroneRuntimeException.class,
            () -> target.sendMailWithFrom(fromAddress, fromName, subject, text, to));

        // 結果の検証
        assertThat(thrown).hasMessage(
            "fromAddress: [with.from@example.com], fromName: [null], subject: [テスト件名], text: [テスト本文], to: [foo@example.com]");
    }

    /**
     * sendMailWithFromメソッドのテスト
     * <p>
     * バリデーション4：送信元を指定してメール送信 送信元の名前が空
     * </p>
     * <ul>
     * <li>検証：メールが1通送信されること</li>
     * <li>fromに入力したaddressが設定されていること</li>
     * <li>toに入力したaddressが設定されていること</li>
     * <li>件名に入力したsubjectが設定されていること</li>
     * <li>本文に入力したtextが設定されていること</li>
     * </ul>
     *
     * @throws Exception 発生した例外
     */
    @Test
    @DisplayName("バリデーション4：送信元を指定してメール送信 送信元の名前が空")
    void testSendMailWithFrom_valid04() throws Exception {
        // 入力値の準備
        String fromAddress = "with.from@example.com";
        String fromName = "";
        String to = "foo@example.com";
        String subject = "テスト件名";
        String text = "テスト本文";

        // メソッドの呼び出し
        target.sendMailWithFrom(fromAddress, fromName, subject, text, to);
        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        // 結果の検証
        Address[] froms = Arrays.array(new InternetAddress(fromAddress, fromName));
        Address[] tos = Arrays.array(new InternetAddress(to));

        Message[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1).extracting(Message::getFrom).containsExactly(froms);
        assertThat(receivedMessages).extracting(Message::getAllRecipients).containsExactly(tos);
        assertThat(receivedMessages).extracting(Message::getSubject).containsExactly(subject);
        assertThat(receivedMessages).extracting(Message::getContent).containsExactly(text);
    }
}
