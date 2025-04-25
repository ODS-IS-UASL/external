package jp.go.meti.drone.com.common.mqtt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;

import jp.go.meti.drone.com.common.mqtt.MqttClientTemplateProperties.Client;

/**
 * MqttConfigurationのテストクラス
 */
@DisplayName("MqttConfigurationのテストクラス")
class MqttConfigurationTest {

    /**
     * mqttClientメソッドのテスト
     * <p>
     * 正常系1：MqttClientの取得<br>
     * 設定条件1：SSLなし<br>
     * 設定条件2：サブスクライバーなし 設定条件3：MQTT接続
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - connectメソッドが正常処理を実行
     * <li>モックオブジェクト2:mockMqttClientFactory - getMqttClientがmockMqttClientを返却
     * <li>モックオブジェクト3:mockApplicationContext - getBeanがBeanExceptionをスロー(正常)
     * <li>検証：MqttClientが取得できること
     * </ul>
     */
    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("正常系1：MqttClientの取得")
    void testMqttClient_normal1() {

        try {
            // モックの作成
            MqttClient mockMqttClient = mock(MqttClient.class);
            doNothing().when(mockMqttClient).connect(any(MqttConnectOptions.class));
            MockedStatic<MqttClientFactory> mockMqttClientFactory = mockStatic(MqttClientFactory.class);
            mockMqttClientFactory.when(() -> MqttClientFactory.getMqttClient(anyString(), anyString()))
                .thenReturn(mockMqttClient);

            ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
            when(mockApplicationContext.getBean(anyString(), any(Class.class))).thenThrow(
                new BeanCreationException("msg"));

            // Propertiesの設定
            MqttClientTemplateProperties properties = new MqttClientTemplateProperties();
            Client client = new Client();
            client.setBaseUrl("tcp://localhost:1883");
            client.setUsername("admin");
            client.setPassword("admin");
            properties.getClient().put("mqttSampleClient", client);

            MqttConfiguration mqttConfiguration = new MqttConfiguration(properties, mockApplicationContext);

            // メソッドの呼び出し
            Map<String, MqttClient> retMqttClient = mqttConfiguration.mqttClient();
            mockMqttClientFactory.close();

            // 結果の検証
            assertNotNull(retMqttClient);
            verify(mockMqttClient, times(1)).connect(any(MqttConnectOptions.class));
            verify(mockApplicationContext, times(1)).getBean(anyString(), any(Class.class));
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * mqttClientメソッドのテスト
     * <p>
     * 正常系2：MqttClientの取得<br>
     * 設定条件1：SSLあり<br>
     * 設定条件2：サブスクライバーなし<br>
     * 設定条件3：MQTTS接続<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - connectメソッドが正常処理を実行
     * <li>モックオブジェクト2:mockMqttClientFactory - getMqttClientがmockMqttClientを返却
     * <li>モックオブジェクト3:mockApplicationContext - getBeanがBeanExceptionをスロー(正常)
     * <li>モックオブジェクト4:sslContextStaticMock - getInstanceがmockSSLContextを返却
     * <li>検証：MqttClientが取得できること
     * </ul>
     */
    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("正常系2：MqttClientの取得")
    void testMqttClient_normal2() {

        try {
            // モックの作成
            MqttClient mockMqttClient = mock(MqttClient.class);
            doNothing().when(mockMqttClient).connect(any(MqttConnectOptions.class));
            MockedStatic<MqttClientFactory> mockMqttClientFactory = mockStatic(MqttClientFactory.class);
            mockMqttClientFactory.when(() -> MqttClientFactory.getMqttClient(anyString(), anyString()))
                .thenReturn(mockMqttClient);

            ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
            when(mockApplicationContext.getBean(anyString(), any(Class.class))).thenThrow(
                new BeanCreationException("msg"));

            // SSLContextのモック
            SSLContext mockSSLContext = mock(SSLContext.class);
            MockedStatic<SSLContext> sslContextStaticMock = mockStatic(SSLContext.class);
            sslContextStaticMock.when(() -> SSLContext.getInstance(eq("TLS"))).thenReturn(mockSSLContext);

            // Propertiesの設定
            MqttClientTemplateProperties properties = new MqttClientTemplateProperties();
            Client client = new Client();
            client.setBaseUrl("ssl://localhost:8883");
            client.setUsername("admin");
            client.setPassword("admin");
            properties.getClient().put("mqttSampleClient", client);

            MqttConfiguration mqttConfiguration = new MqttConfiguration(properties, mockApplicationContext);

            // sslを設定
            Field sslFlg = MqttConfiguration.class.getDeclaredField("sslFlg");
            sslFlg.setAccessible(true);
            sslFlg.setBoolean(mqttConfiguration, true);

            Field tsPassWord = MqttConfiguration.class.getDeclaredField("tsPassWord");
            tsPassWord.setAccessible(true);
            tsPassWord.set(mqttConfiguration, "password");

            Field tsPath = MqttConfiguration.class.getDeclaredField("tsPath");
            tsPath.setAccessible(true);
            tsPath.set(mqttConfiguration, "src/test/resources/keystore/brokerTest.ts");

            // メソッドの呼び出し
            Map<String, MqttClient> retMqttClient = mqttConfiguration.mqttClient();
            sslContextStaticMock.close();
            mockMqttClientFactory.close();

            // 結果の検証
            assertNotNull(retMqttClient);
            verify(mockMqttClient, times(1)).connect(any(MqttConnectOptions.class));
            verify(mockApplicationContext, times(1)).getBean(anyString(), any(Class.class));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * mqttClientメソッドのテスト
     * <p>
     * 正常系3：MqttClientの取得<br>
     * 設定条件1：SSLなし<br>
     * 設定条件2：サブスクライバーあり
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - connectメソッドが正常処理を実行
     * <li>モックオブジェクト2:mockMqttClientFactory - getMqttClientがmockMqttClientを返却
     * <li>モックオブジェクト3:mockApplicationContext - getBeanがMqttSubscriberHandlerを返却
     * <li>検証：MqttClientが取得できること
     * </ul>
     */
    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("正常系3：MqttClientの取得")
    void testMqttClient_normal3() {

        try {

            // モックの作成
            MqttClient mockMqttClient = mock(MqttClient.class);
            doNothing().when(mockMqttClient).connect(any(MqttConnectOptions.class));
            MockedStatic<MqttClientFactory> mockMqttClientFactory = mockStatic(MqttClientFactory.class);
            mockMqttClientFactory.when(() -> MqttClientFactory.getMqttClient(anyString(), anyString()))
                .thenReturn(mockMqttClient);

            MqttSubscriberHandler mqttSubscriberHandler = new MqttSubscriberHandler() {

                @Override
                public void subscribe(MqttClient mqttClient) throws MqttException {
                    //
                }
            };
            ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
            when(mockApplicationContext.getBean(anyString(), any(Class.class))).thenReturn(mqttSubscriberHandler);

            // Propertiesの設定
            MqttClientTemplateProperties properties = new MqttClientTemplateProperties();
            Client client = new Client();
            client.setBaseUrl("tcp://localhost:1883");
            client.setUsername("admin");
            client.setPassword("admin");
            properties.getClient().put("mqttSampleClient", client);

            MqttConfiguration mqttConfiguration = new MqttConfiguration(properties, mockApplicationContext);

            // sslを設定
            Field sslFlg = MqttConfiguration.class.getDeclaredField("sslFlg");
            sslFlg.setAccessible(true);
            sslFlg.setBoolean(mqttConfiguration, true);

            Field tsPassWord = MqttConfiguration.class.getDeclaredField("tsPassWord");
            tsPassWord.setAccessible(true);
            tsPassWord.set(mqttConfiguration, "password");

            Field tsPath = MqttConfiguration.class.getDeclaredField("tsPath");
            tsPath.setAccessible(true);
            tsPath.set(mqttConfiguration, "src/test/resources/keystore/brokerTest.ts");

            // メソッドの呼び出し
            Map<String, MqttClient> retMqttClient = mqttConfiguration.mqttClient();
            mockMqttClientFactory.close();

            // 結果の検証
            assertNotNull(retMqttClient);
            verify(mockMqttClient, times(1)).connect(any(MqttConnectOptions.class));
            verify(mockApplicationContext, times(1)).getBean(anyString(), any(Class.class));
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * mqttClientメソッドのテスト
     * <p>
     * 正常系4：MqttClientの取得<br>
     * 設定条件1：SSLあり<br>
     * 設定条件2：サブスクライバーあり
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - connectメソッドが正常処理を実行
     * <li>モックオブジェクト2:mockMqttClientFactory - getMqttClientがmockMqttClientを返却
     * <li>モックオブジェクト3:mockApplicationContext - getBeanがMqttSubscriberHandlerを返却
     * <li>検証：MqttClientが取得できること
     * </ul>
     */
    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("正常系4：MqttClientの取得")
    void testMqttClient_normal4() throws Exception {

        try {
            // モックの作成
            MqttClient mockMqttClient = mock(MqttClient.class);
            doNothing().when(mockMqttClient).connect(any(MqttConnectOptions.class));
            MockedStatic<MqttClientFactory> mockMqttClientFactory = mockStatic(MqttClientFactory.class);
            mockMqttClientFactory.when(() -> MqttClientFactory.getMqttClient(anyString(), anyString()))
                .thenReturn(mockMqttClient);

            MqttSubscriberHandler handler = new MqttSubscriberHandler() {
                @Override
                public void subscribe(MqttClient mqttClient) throws MqttException {
                    // 空
                }
            };
            ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
            when(mockApplicationContext.getBean(anyString(), any(Class.class))).thenReturn(handler);

            // Propertiesの設定
            MqttClientTemplateProperties properties = new MqttClientTemplateProperties();
            Client client = new Client();
            client.setBaseUrl("ssl://localhost:8883");
            client.setUsername("admin");
            client.setPassword("admin");
            properties.getClient().put("mqttSampleClient", client);

            MqttConfiguration mqttConfiguration = new MqttConfiguration(properties, mockApplicationContext);

            // sslを設定
            Field sslFlg = MqttConfiguration.class.getDeclaredField("sslFlg");
            sslFlg.setAccessible(true);
            sslFlg.setBoolean(mqttConfiguration, true);

            Field tsPassWord = MqttConfiguration.class.getDeclaredField("tsPassWord");
            tsPassWord.setAccessible(true);
            tsPassWord.set(mqttConfiguration, "password");

            Field tsPath = MqttConfiguration.class.getDeclaredField("tsPath");
            tsPath.setAccessible(true);
            tsPath.set(mqttConfiguration, "src/test/resources/keystore/brokerTest.ts");

            // メソッドの呼び出し
            Map<String, MqttClient> retMqttClient = mqttConfiguration.mqttClient();

            mockMqttClientFactory.close();

            // 結果の検証
            assertNotNull(retMqttClient);
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * MqttPublishServiceImpl のmqttClient<br>
     * 異常系：接続失敗
     * 
     * @throws MqttException
     */
    /**
     * mqttClientメソッドのテスト
     * <p>
     * 異常系：接続失敗<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - connectメソッドが異常処理を実行
     * <li>モックオブジェクト2:mockMqttClientFactory - getMqttClientがmockMqttClientを返却
     * <li>検証：取得したMqttClientがnullであること
     * </ul>
     */
    @Test
    @DisplayName("異常系:接続失敗")
    void testMqttClient_error1() {

        try {
            // モックの作成
            MqttClient mockMqttClient = mock(MqttClient.class);
            doThrow(new MqttException(MqttException.REASON_CODE_SERVER_CONNECT_ERROR)).when(mockMqttClient)
                .connect(any(MqttConnectOptions.class));
            MockedStatic<MqttClientFactory> mockMqttClientFactory = mockStatic(MqttClientFactory.class);
            mockMqttClientFactory.when(() -> MqttClientFactory.getMqttClient(anyString(), anyString()))
                .thenReturn(mockMqttClient);

            // Propertiesの設定
            MqttClientTemplateProperties properties = new MqttClientTemplateProperties();
            Client client = new Client();
            client.setBaseUrl("tcp://localhost:1883");
            client.setUsername("admin");
            client.setPassword("admin");
            properties.getClient().put("mqttSampleClient", client);

            MqttConfiguration mqttConfiguration = new MqttConfiguration(properties, null);

            // メソッドの呼び出し
            Map<String, MqttClient> retMqttClient = mqttConfiguration.mqttClient();
            mockMqttClientFactory.close();

            // 結果の検証
            assertEquals(0, retMqttClient.size());
            verify(mockMqttClient, times(1)).connect(any(MqttConnectOptions.class));
        } catch (Exception e) {
            fail();
        }
    }

}
