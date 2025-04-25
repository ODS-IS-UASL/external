package jp.go.meti.drone.com.common.mail;

import java.util.List;
import java.util.Map;

/**
 * メール送信用のユーティリティクラス。
 * <p>
 * メール送信やメール本文を作成するための機能を提供します。
 * </p>
 * 
 * @version $Revision$
 */
public interface MailUtils {

    /**
     * 起動時に設定した送信元アドレスと名前を使用して、指定された件名、本文、宛先を設定して非同期でメールを送信します。<br>
     * 「メールの宛先リスト」に設定されたメールの宛先をメールヘッダの「To」に列挙します。<br>
     * メール受信者側では「メールの宛先リスト」に設定されたすべての宛先が表示されます。
     * 
     * @param subject メールの件名
     * @param text メール本文
     * @param to メールの宛先リスト
     */
    void sendMail(String subject, String text, String... to);

    /**
     * メール送信処理。<br>
     * 「メールの宛先リスト」に設定されたメールの宛先をメールヘッダの「To」に列挙します。<br>
     * メール受信者側では「メールの宛先リスト」に設定されたすべての宛先が表示されます。
     * 
     * @param subjectId メール件名のメッセージID<br>
     *        現在の言語設定に応じたメッセージを取得する。<br>
     *        なお、APIやバッチでは基本的に言語設定しないため、本メソッドを使用する場合は、メッセージプロパティのファイルは多言語分用意しないこと。<br>
     *        用意した場合は、Spring framework の設定次第だが、未設定の場合は、システムロケールに応じたメッセージを取得する。
     * @param templateFileName テンプレートファイル名
     * @param model テンプレートに埋め込むモデル情報
     * @param toList メールの宛先リスト
     */
    void sendMail(String subjectId, String templateFileName, Map<String, Object> model, List<String> toList);

    /**
     * 指定されたファイル名のテンプレートに、指定されたモデル情報を埋め込んだ文字列を返却します。
     * 
     * @param templateFileName テンプレートファイル名
     * @param model テンプレートに埋め込むモデル情報
     * @return 埋め込み後の文字列
     */
    String getTextFromTemplate(String templateFileName, Map<String, Object> model);

    /**
     * 引数に設定した送信元アドレスと名前を使用して、指定された件名、本文、宛先を設定して非同期でメールを送信します。<br>
     * 「メールの宛先リスト」に設定されたメールの宛先をメールヘッダの「To」に列挙します。<br>
     * メール受信者側では「メールの宛先リスト」に設定されたすべての宛先が表示されます。
     *
     * @param fromAddress 送信元アドレス
     * @param fromName 送信元として表示する名前
     * @param subject メールの件名
     * @param text メール本文
     * @param to メールの宛先リスト
     */
    void sendMailWithFrom(String fromAddress, String fromName, String subject, String text, String... to);
}
