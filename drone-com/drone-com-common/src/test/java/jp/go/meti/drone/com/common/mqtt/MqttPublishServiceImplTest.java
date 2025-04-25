package jp.go.meti.drone.com.common.mqtt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * MqttPublishServiceImplのテストクラス
 */
@DisplayName("MqttPublishServiceImplのテストクラス")
class MqttPublishServiceImplTest {

    /**
     * publishMessageメソッドのテスト
     * <p>
     * 正常系1：送信<br>
     * 設定条件1：リトライ回数0回<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - publishメソッドが正常処理を実行
     * <li>検証：publishメソッドが1回だけ呼び出されること
     * </ul>
     */
    @Test
    @DisplayName("正常系1：送信")
    void testPublishMessage_normal1() {

        try {
            // モックの作成
            Map<String, MqttClient> mqttClients = new HashMap<>();
            MqttClient mockMqttClient = mock(MqttClient.class);
            doNothing().when(mockMqttClient).publish(anyString(), any(MqttMessage.class));
            mqttClients.put("mqttSampleClient", mockMqttClient);
            MqttPublishService mqttPublishService = new MqttPublishServiceImpl(mqttClients);

            // sendClientIdを設定
            Field sendClientId = MqttPublishServiceImpl.class.getDeclaredField("sendClientId");
            sendClientId.setAccessible(true);
            sendClientId.set(mqttPublishService, "mqttSampleClient");
            
            // retryCntMaxを設定
            Field retryCntMax = MqttPublishServiceImpl.class.getDeclaredField("retryCntMax");
            retryCntMax.setAccessible(true);
            retryCntMax.setInt(mqttPublishService, 0);

            // メソッドの呼び出し
            mqttPublishService.publishMessage("Topic", "Message");

            // 結果の検証
            verify(mockMqttClient, times(1)).publish(anyString(), any(MqttMessage.class));
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * publishMessageメソッドのテスト
     * <p>
     * 異常系1：送信失敗<br>
     * 設定条件1：リトライ回数0回<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - publishメソッドがMqttExceptionをスロー
     * <li>検証1：publishメソッドが1回だけ呼び出されること
     * <li>検証2：MqttExceptionをキャッチしてREASON_CODE_SERVER_CONNECT_ERRORの理由コードが取得できること
     * </ul>
     * 
     * @throws MqttException MQTT例外
     */
    @Test
    @DisplayName("異常系1：送信失敗")
    void testPublishMessage_error1() throws MqttException {

        // モックの作成
        Map<String, MqttClient> mqttClients = new HashMap<>();
        MqttClient mockMqttClient = mock(MqttClient.class);
        try {
            doThrow(new MqttException(MqttException.REASON_CODE_SERVER_CONNECT_ERROR)).when(mockMqttClient)
                .publish(anyString(), any(MqttMessage.class));
            mqttClients.put("mqttSampleClient", mockMqttClient);
            MqttPublishService mqttPublishService = new MqttPublishServiceImpl(mqttClients);

            // sendClientIdを設定
            Field sendClientId = MqttPublishServiceImpl.class.getDeclaredField("sendClientId");
            sendClientId.setAccessible(true);
            sendClientId.set(mqttPublishService, "mqttSampleClient");
            
            // retryCntMaxを設定
            Field retryCntMax = MqttPublishServiceImpl.class.getDeclaredField("retryCntMax");
            retryCntMax.setAccessible(true);
            retryCntMax.setInt(mqttPublishService, 0);
            // メソッドの呼び出し
            mqttPublishService.publishMessage("Topic", "Message");
            fail();
        } catch (Exception e) {
            // 結果の検証
            MqttException mqttException = (MqttException) e;
            assertEquals(MqttException.REASON_CODE_SERVER_CONNECT_ERROR, mqttException.getReasonCode());
            verify(mockMqttClient, times(1)).publish(anyString(), any(MqttMessage.class));
        }
    }

    /**
     * publishMessageメソッドのテスト
     * <p>
     * 異常系1：送信失敗<br>
     * 設定条件1：リトライ回数1回<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：mockMqttClient - publishメソッドがMqttExceptionをスロー
     * <li>検証1：publishメソッドが2回呼び出されること
     * <li>検証2：MqttExceptionをキャッチしてREASON_CODE_SERVER_CONNECT_ERRORの理由コードが取得できること
     * </ul>
     * 
     * @throws MqttException MQTT例外
     */
    @Test
    @DisplayName("異常系2：送信失敗")
    void testPublishMessage_error2() throws MqttException {

        // モックの作成
        Map<String, MqttClient> mqttClients = new HashMap<>();
        MqttClient mockMqttClient = mock(MqttClient.class);
        try {
            doThrow(new MqttException(MqttException.REASON_CODE_SERVER_CONNECT_ERROR)).when(mockMqttClient)
                .publish(anyString(), any(MqttMessage.class));
            mqttClients.put("mqttSampleClient", mockMqttClient);
            MqttPublishService mqttPublishService = new MqttPublishServiceImpl(mqttClients);
            
            // sendClientIdを設定
            Field sendClientId = MqttPublishServiceImpl.class.getDeclaredField("sendClientId");
            sendClientId.setAccessible(true);
            sendClientId.set(mqttPublishService, "mqttSampleClient");

            // retryCntMaxを設定
            Field retryCntMax = MqttPublishServiceImpl.class.getDeclaredField("retryCntMax");
            retryCntMax.setAccessible(true);
            retryCntMax.setInt(mqttPublishService, 1);
            
            // メソッドの呼び出し
            mqttPublishService.publishMessage("Topic", "Message");
            fail();
        } catch (Exception e) {
            // 結果の検証
            MqttException mqttException = (MqttException) e;
            assertEquals(MqttException.REASON_CODE_SERVER_CONNECT_ERROR, mqttException.getReasonCode());
            verify(mockMqttClient, times(2)).publish(anyString(), any(MqttMessage.class));
        }
    }

    /**
     * publishMessageメソッドのテスト
     * <p>
     * 異常系3：ブローカー未接続<br>
     * 設定条件1：インスタンス時のMQTTクライアントの引数をnullに設定<br>
     * <p>
     * <ul>
     * <li>検証：MqttExceptionをキャッチしてREASON_CODE_CLIENT_NOT_CONNECTEDの理由コードが取得できること
     * </ul>
     * 
     * @throws MqttException MQTT例外
     */
    @Test
    @DisplayName("異常系3：ブローカー未接続")
    void testPublishMessage_error3() {

        try {

            // メソッドの呼び出し
            Map<String, MqttClient> mqttClients = new HashMap<>();
            MqttPublishService mqttPublishService = new MqttPublishServiceImpl(mqttClients);
            mqttPublishService.publishMessage("Topic", "Message");
            fail();
        } catch (Exception e) {
            // 結果の検証
            MqttException mqttException = (MqttException) e;
            assertEquals(MqttException.REASON_CODE_CLIENT_NOT_CONNECTED, mqttException.getReasonCode());

        }
    }

}
