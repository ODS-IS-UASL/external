package jp.go.meti.drone.com.common.mqtt;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.MqttException;

import lombok.extern.slf4j.Slf4j;

/**
 * MQTT SSL作成クラス
 */
@Slf4j
public class MqttSSLSocketFactory {

    /**
     * SSLSocketFactory生成
     * 
     * @param tsPassWord トラストストアパスワード
     * @param tsPath トラストストアファイルパス
     * @return SSLSocketFactory
     * @throws MqttException MQTT例外
     */
    public static SSLSocketFactory getSSLSocketFactory(String tsPassWord, String tsPath) throws MqttException {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            initSSL(sslContext, tsPassWord, tsPath);
            return sslContext.getSocketFactory();
        } catch (Exception exception) {
            log.error("SSLSocketFactory Error", exception);
            throw new MqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR, exception);
        }

    }

    /**
     * SSL設定
     * 
     * @param sslContext SSLContext
     * @param tsPassWord トラストストアパスワード
     * @param tsPath トラストストアファイルパス
     * @throws MqttException MQTT例外
     */
    private static void initSSL(SSLContext sslContext, String tsPassWord, String tsPath) throws MqttException {

        try (FileInputStream trustStroreFile = new FileInputStream(tsPath)) {

            // トラストストアをロード
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(trustStroreFile, tsPassWord.toCharArray());

            // トラストマネージャを初期化
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // SSLContext の初期化
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

        } catch (CertificateException exception) {
            log.error("SSL Error CertificateException", exception);
            throw new MqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR, exception);
        } catch (NoSuchAlgorithmException exception) {
            log.error("SSL Error NoSuchAlgorithmException", exception);
            throw new MqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR, exception);
        } catch (KeyStoreException exception) {
            log.error("SSL Error KeyStoreException", exception);
            throw new MqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR, exception);
        } catch (KeyManagementException exception) {
            log.error("SSL Error KeyManagementException", exception);
            throw new MqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR, exception);
        } catch (IOException exception) {
            log.error("SSL Error IOException", exception);
            throw new MqttException(MqttException.REASON_CODE_SSL_CONFIG_ERROR, exception);
        }

    }

}
