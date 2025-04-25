package jp.go.meti.drone.com.common.mqtt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * MqttSSLSocketFactoryのテストクラス
 */
@DisplayName("MqttSSLSocketFactoryのテストクラス")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MqttSSLSocketFactoryTest {

    /**
     * getSSLSocketFactoryメソッドのテスト
     * <p>
     * 正常系1：SSLSocketFactoryの取得<br>
     * 設定条件1：src/test/resources/keystore配下にトラストストアファイルが配置されていること<br>
     * 設定条件2：トラストストアファイルのパスワードが判明していること<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト：なし
     * <li>検証：SSLSocketFactoryが取得できること
     * </ul>
     * 
     * @throws MqttException MQTT例外
     */
    @Test
    @DisplayName("正常系1：SSLSocketFactoryの取得")
    void testGetSSLSocketFactory_normal1() throws MqttException {
        // メソッドの呼び出し
        SSLSocketFactory sslSocketFactory = MqttSSLSocketFactory.getSSLSocketFactory(
            "password",
            "src/test/resources/keystore/brokerTest.ts");
        assertNotNull(sslSocketFactory);
    }

    /**
     * コンストラクタのテスト
     * <p>
     * 正常系2：インスタンス化によるコンストラクタの実行
     * <p>
     * <ul>
     * <li>モックオブジェクト：なし
     * <li>検証：MqttSSLSocketFactoryが取得できること
     * </ul>
     * 
     * @throws MqttException MQTT例外
     */
    @Test
    @DisplayName("正常系2：インスタンス化によるコンストラクタの実行")
    void testGetSSLSocketFactory_normal2() throws MqttException {
        // コンストラクタの呼び出し
        MqttSSLSocketFactory sslSocketFactory = new MqttSSLSocketFactory();
        assertNotNull(sslSocketFactory);
    }

    /**
     * initSSLメソッドのテスト
     * <p>
     * 正常系1：SSLの設定<br>
     * 設定条件1：src/test/resources/keystore配下にトラストストアファイルが配置されていること<br>
     * 設定条件2：トラストストアファイルのパスワードが判明していること<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト：なし
     * <li>検証：SSLの設定でエラーが発生しないこと
     * </ul>
     * 
     * @throws MqttException MQTT例外
     */
    @Test
    @DisplayName("正常系1：SSL設定")
    void testInitSSL_normal1() throws MqttException {

        // メソッドの呼び出し
        try {
            MqttSSLSocketFactory.getSSLSocketFactory("password", "src/test/resources/keystore/brokerTest.ts");
        } catch (MqttException e) {
            fail();
        }
    }

    /**
     * initSSLメソッドのテスト
     * <p>
     * 異常系1：CertificateException<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：keyStore - loadメソッドがCertificateExceptionをスロー
     * <li>検証1：トラストストアのロードに失敗すること
     * <li>検証2：MqttExceptionをキャッチしてREASON_CODE_SSL_CONFIG_ERRORの理由コードが取得できること
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("異常系1：CertificateException")
    void testInitSSL_error1() throws Exception {
        // モックの作成
        KeyStore mockKeyStore = mock(KeyStore.class);

        doThrow(new CertificateException()).when(mockKeyStore).load(any(InputStream.class), any(char[].class));

        try (MockedStatic<KeyStore> mockKeyStoreStaticMock = mockStatic(KeyStore.class);) {
            mockKeyStoreStaticMock.when(() -> mockKeyStore.getInstance("PKCS12")).thenReturn(mockKeyStore);

            try {
                // メソッドの呼び出し
                MqttSSLSocketFactory.getSSLSocketFactory("password", "src/test/resources/keystore/brokerTest.ts");
                fail();
            } catch (MqttException e) {
                // 結果の検証
                assertEquals(MqttException.REASON_CODE_SSL_CONFIG_ERROR, e.getReasonCode());
                verify(mockKeyStore, times(1)).load(any(InputStream.class), any(char[].class));
            }
        }
    }

    /**
     * initSSLメソッドのテスト
     * <p>
     * 異常系2：NoSuchAlgorithmException<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：keyStore - loadメソッドがNoSuchAlgorithmExceptionをスロー
     * <li>検証1：トラストストアのロードに失敗すること
     * <li>検証2：MqttExceptionをキャッチしてREASON_CODE_SSL_CONFIG_ERRORの理由コードが取得できること
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("異常系2：NoSuchAlgorithmException")
    void testInitSSL_error2() throws Exception {
        // モックの作成
        KeyStore mockKeyStore = mock(KeyStore.class);

        doThrow(new NoSuchAlgorithmException()).when(mockKeyStore).load(any(InputStream.class), any(char[].class));

        try (MockedStatic<KeyStore> mockKeyStoreStaticMock = mockStatic(KeyStore.class);) {
            mockKeyStoreStaticMock.when(() -> mockKeyStore.getInstance("PKCS12")).thenReturn(mockKeyStore);

            try {
                // メソッドの呼び出し
                MqttSSLSocketFactory.getSSLSocketFactory("password", "src/test/resources/keystore/brokerTest.ts");
                fail();
            } catch (MqttException e) {
                // 結果の検証
                assertEquals(MqttException.REASON_CODE_SSL_CONFIG_ERROR, e.getReasonCode());
                verify(mockKeyStore, times(1)).load(any(InputStream.class), any(char[].class));
            }
        }
    }

    /**
     * initSSLメソッドのテスト
     * <p>
     * 異常系3：KeyStoreException<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockTrustManagerFactory - initメソッドがKeyStoreExceptionをスロー
     * <li>モックオブジェクト2：trustManagerFactoryStaticMock - trustManagerFactoryStaticMockメソッドがmockTrustManagerFactoryを返却
     * <li>検証1：MqttExceptionをキャッチしてREASON_CODE_SSL_CONFIG_ERRORの理由コードが取得できること
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("異常系3：KeyStoreException")
    void testInitSSL_error3() throws Exception {
        // モックの作成
        ArgumentCaptor<KeyStore> mockKeyStore = ArgumentCaptor.forClass(KeyStore.class);

        TrustManagerFactory mockTrustManagerFactory = mock(TrustManagerFactory.class);
        doThrow(new KeyStoreException()).when(mockTrustManagerFactory).init(mockKeyStore.capture());

        try (MockedStatic<TrustManagerFactory> trustManagerFactoryStaticMock = mockStatic(TrustManagerFactory.class);) {
            trustManagerFactoryStaticMock.when(
                () -> TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()))
                .thenReturn(mockTrustManagerFactory);

            try {
                // メソッドの呼び出し
                MqttSSLSocketFactory.getSSLSocketFactory("password", "src/test/resources/keystore/brokerTest.ts");
                fail();
            } catch (MqttException e) {
                // 結果の検証
                assertEquals(MqttException.REASON_CODE_SSL_CONFIG_ERROR, e.getReasonCode());
                verify(mockTrustManagerFactory, times(1)).init(mockKeyStore.capture());
            }
        }
    }

    /**
     * initSSLメソッドのテスト
     * <p>
     * 異常系4：KeyManagementException<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockSSLContext - initメソッドがKeyManagementExceptionをスロー
     * <li>モックオブジェクト2：sslContextStaticMock - getInstanceメソッドがmockSSLContextを返却
     * <li>検証1：MqttExceptionをキャッチしてREASON_CODE_SSL_CONFIG_ERRORの理由コードが取得できること
     * </ul>
     * 
     * @throws KeyManagementException 例外
     */
    @Test
    @DisplayName("異常系4：KeyManagementException")
    void testInitSSL_error4() throws KeyManagementException {
        // モックの作成

        try (MockedStatic<SSLContext> sslContextStaticMock = mockStatic(SSLContext.class);) {
            SSLContext mockSSLContext = mock(SSLContext.class);
            sslContextStaticMock.when(() -> SSLContext.getInstance("TLS")).thenReturn(mockSSLContext);
            doThrow(new KeyManagementException()).when(mockSSLContext)
                .init(isNull(KeyManager[].class), any(TrustManager[].class), any(SecureRandom.class));

            try {
                // メソッドの呼び出し
                MqttSSLSocketFactory.getSSLSocketFactory("password", "src/test/resources/keystore/brokerTest.ts");
                fail();
            } catch (MqttException e) {
                // 結果の検証
                assertEquals(MqttException.REASON_CODE_SSL_CONFIG_ERROR, e.getReasonCode());
                verify(mockSSLContext, times(1)).init(
                    isNull(KeyManager[].class),
                    any(TrustManager[].class),
                    any(SecureRandom.class));
            }
        }
    }

    /**
     * initSSLメソッドのテスト
     * <p>
     * 異常系5：IOException<br>
     * 設定条件1：存在しないトラストストアファイルを指定すること<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：なし
     * <li>検証1：MqttExceptionをキャッチしてREASON_CODE_SSL_CONFIG_ERRORの理由コードが取得できること
     * </ul>
     * 
     * @throws IOException
     */
    @Test
    @DisplayName("異常系5：IOException")
    void testInitSSL_error5() throws IOException {

        try {
            // メソッドの呼び出し
            MqttSSLSocketFactory.getSSLSocketFactory("password", "src/test/resources/keystore/hogehoge.ts");
        } catch (MqttException e) {
            // 結果の検証
            assertEquals(MqttException.REASON_CODE_SSL_CONFIG_ERROR, e.getReasonCode());
        }

    }
}
