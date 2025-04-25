package jp.go.meti.drone.com.common.mqtt;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * MqttClientFactoryのテストクラス
 */
@DisplayName("MqttClientFactoryのテストクラス")
class MqttClientFactoryTest {

    /**
     * getMqttClienｔメソッドのテスト
     * <p>
     * 正常系1：MqttClientの取得
     * <p>
     * <ul>
     * <li>モックオブジェクト：なし
     * <li>検証：MqttClientが取得できること
     * </ul>
     */
    @Test
    @DisplayName("正常系1：MqttClientの取得")
    void testGetMqttClient_normal1() {

        try {

            // メソッドの呼び出し
            MqttClient retMqttClient = MqttClientFactory.getMqttClient("tcp://localhost:1883", "test");

            // 結果の検証
            assertNotNull(retMqttClient);

        } catch (Exception e) {
            fail();
        }
    }

    /**
     * コンストラクタのテスト
     * <p>
     * 正常系2：インスタンス化によるコンストラクタの実行
     * <p>
     * <ul>
     * <li>モックオブジェクト：なし
     * <li>検証：MqttClientが取得できること
     * </ul>
     */
    @Test
    @DisplayName("正常系2：インスタンス化によるコンストラクタの実行")
    void testGetMqttClient_normal2() {

        // コンストラクタの呼び出し
        MqttClientFactory mqttClientFactory = new MqttClientFactory();
        assertNotNull(mqttClientFactory);
    }

    /**
     * getMqttClienｔメソッドのテスト
     * <p>
     * 正常系3：MqttCallbackの実行<br>
     * 取得したMqttClientからMqttAsyncClient、MqttCallbackを取得<br>
     * MqttCallbackのconnectionLost、messageArrived、deliveryCompleteからログが出力されること
     * <p>
     * <ul>
     * <li>モックオブジェクト：mockMqttDeliveryToken - getTopicsが呼ばれた際にString配列を返却
     * <li>検証1：MqttCallbackに設定したメソッドがログを出力すること
     * <li>検証2：getTopicsメソッドが1回だけ呼び出されること
     * </ul>
     */
    @Test
    @DisplayName("正常系3：MqttCallbackの実行")
    void testGetMqttClient_normal3() {

        try {

            // モックの作成
            MqttDeliveryToken mockMqttDeliveryToken = mock(MqttDeliveryToken.class);
            String[] retStrArray = { "TopicName" };
            when(mockMqttDeliveryToken.getTopics()).thenReturn(retStrArray);

            // メソッドの呼び出し
            MqttClient retMqttClient = MqttClientFactory.getMqttClient("tcp://localhost:1883", "test");

            // MqttAsyncClient取得
            Field aClientField = MqttClient.class.getDeclaredField("aClient");
            aClientField.setAccessible(true);
            MqttAsyncClient aClient = (MqttAsyncClient) aClientField.get(retMqttClient);

            // MqttCallback取得
            Field mqttCallbackField = MqttAsyncClient.class.getDeclaredField("mqttCallback");
            mqttCallbackField.setAccessible(true);
            MqttCallback mqttCallback = (MqttCallback) mqttCallbackField.get(aClient);

            // connectionLostの実行
            mqttCallback.connectionLost(null);

            // messageArrivedの実行
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload("message".getBytes());
            mqttCallback.messageArrived("test", mqttMessage);

            // deliveryCompleteの実行
            mqttCallback.deliveryComplete(mockMqttDeliveryToken);

            // 結果の検証
            verify(mockMqttDeliveryToken, times(1)).getTopics();

        } catch (Exception e) {
            fail();
        }
    }
}
