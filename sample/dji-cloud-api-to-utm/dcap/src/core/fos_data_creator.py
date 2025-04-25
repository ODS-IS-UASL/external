# -*- coding: utf-8 -*-
"""
CommunicationControlBoxにおいて、FOSに通知するテレメトリ情報を定期的に作成する処理を担当するモジュール
"""

import time
import json
from com.base_class import SubProcessClass
from com.common_utils import CommonUtils
from com.interprocess_queue_accessor import InterProcessQueueAccessor
from com.protbuf_message_builder import ProtobufMessageBuilder
from com.topic_generator import TopicGenerator
import decimal
from fc.telemetry.dji.cloud_api.telemetry_manager import TelemetryManagerImpl


class FosDataCreator(SubProcessClass):
    """
    CommunicationControlBoxにおいて、FOSに通知するテレメトリ情報を定期的に作成するプロセスの実体となるクラス
    """
    mqtt_pub_queue = None
    """MQTTパブリッシャ連携用キュー"""
    telemetry_queue = None
    """テレメトリ取得キュー"""
    subordinate_share = None
    """プロセス間で情報共有するディクショナリ"""
    manager = None
    """テレメトリマネージャクライアント"""
    topic_generator = None
    """トピックジェネレータ"""
    pb_message_builder = None
    """メッセージビルダ"""
    loop_wait = None
    """設定ファイルより取得する処理待ち時間"""
    telemetry_manager = None
    """テレメトリマネージャー"""
    telemetry_key = None
    """テレメトリRedisキー"""

    def __init__(self):
        """
        コンストラクタ
        """
        super(FosDataCreator, self).__init__(__file__)

    def start(self, subordinate_share):
        """
        処理実行時の起点となるメソッド

        本メソッドより、ready()およびrun()を実行する。

        :param subordinate_share: プロセス間で情報共有するディクショナリ
        """
        self.subordinate_share = subordinate_share
        super(FosDataCreator, self).start()

    def ready(self):
        """
        FOSテレメトリ情報作成処理の事前準備となる処理を実行するメソッド

        主に以下を実施する。

            キュー生成
                MQTTパブリッシャ連携用キューを生成する。

            各種インスタンス生成
                トピックジェネレータ等のインスタンスを生成する。

            設定ファイル読込
                FOSテレメトリ情報作成処理で使用する設定ファイル項目を読み込み、クラスフィールドに格納する。

            テレメトリマネージャクライアント準備
               テレメトリマネージャクライアントのインスタンスを生成して、テレメトリマネージャへ接続する。

            有効桁数設定
                Decimalの有効桁数を16桁に設定する。（17桁目切り捨て）

        """
        self.telemetry_manager = TelemetryManagerImpl()
        self._setup_queue()
        self._setup_telemetry_queue()
        self.telemetry_key = self.resource_accessor.config_dict['redis_for_receive']['receive_key_osd']
        self.topic_generator = TopicGenerator(self.resource_accessor)
        self.pb_message_builder = ProtobufMessageBuilder(self.resource_accessor)
        self.loop_wait = self.resource_accessor.config_dict['settings']['loop_wait']
        # Decimalの有効桁数16桁設定(17桁目切り捨て)
        decimal.getcontext().prec = 16

    def run(self):
        """
        FOSテレメトリ情報作成処理として繰り返し実行するメソッド

        主に以下を実施する。

            テレメトリ情報取得（通常参照）
                ドローンのテレメトリ情報を取得（通常参照）する。
                テレメトリ情報を取得できない場合はFOSテレメトリ情報作成処理をスキップする。

            FOSテレメトリ通知
                取得したテレメトリ情報を元にFOS向けテレメトリ情報を作成する。
                そして、作成したテレメトリ情報をMQTTパブリッシャ連携用キューに格納する。

        :return: なし（処理スキップのため使用）
        """
        start_time = time.time()
        try:
            data = self.telemetry_queue.get_str_value(self.telemetry_key)
            if data is not None:
                telemetry_dic = json.loads(data)
                if 'mission_id' in self.subordinate_share and self.subordinate_share['mission_id'] != '':
                    telemetry_dic['data']['mission_id'] = self.subordinate_share['mission_id']
                v_stat = self.telemetry_manager.snapshot(telemetry_dic['data'])
                # FOSへテレメトリ通知
                self._send_vehicle_telemetry(v_stat)
        finally:
            CommonUtils.passage_subtract_sleep(self.loop_wait, start_time, self.message_utils)

    def _setup_queue(self):
        """
        MQTTパブリッシャ連携用キューを準備するメソッド
        """
        inmem_host = self.resource_accessor.config_dict['redis_settings']['inmem_host']
        inmem_port = self.resource_accessor.config_dict['redis_settings']['inmem_port']
        self.mqtt_pub_queue = InterProcessQueueAccessor(self.resource_accessor, inmem_host, inmem_port)
        self.mqtt_pub_queue.set_queue_name_with_priority(
            self.resource_accessor.config_dict['redis_for_send']['send_key1'])
        self.mqtt_pub_queue.open()

    def _setup_telemetry_queue(self):
        """
        テレメトリキューを準備するメソッド
        """
        inmem_host = self.resource_accessor.config_dict['redis_settings']['inmem_host']
        inmem_port = self.resource_accessor.config_dict['redis_settings']['inmem_port']
        self.telemetry_queue = InterProcessQueueAccessor(self.resource_accessor, inmem_host, inmem_port)
        self.telemetry_queue.set_queue_name_with_priority(
            self.resource_accessor.config_dict['redis_for_receive']['receive_key_osd'])
        self.telemetry_queue.open()

    def _send_vehicle_telemetry(self, v_stat):
        """
        FOSへテレメトリ情報を通知するメソッド

        :param v_stat: テレメトリ情報
        """
        telemetry_msg = self.pb_message_builder.create_pb_message()
        uas_telemetry = self.pb_message_builder.create_pb_telemetry()

        if 'tracking_id' in self.subordinate_share:
            topic = self.topic_generator.create_tlm_topic(self.subordinate_share['tracking_id'])
        else:
            topic = self.topic_generator.create_tlm_no_tracking_topic()

        uas_telemetry = self.telemetry_manager.set_vehicle_telemetry(v_stat, uas_telemetry)

        telemetry_msg.payload = uas_telemetry.SerializeToString()

        dict_data = {'topic': topic, 'message': telemetry_msg.SerializeToString()}
        self.mqtt_pub_queue.push_dict(dict_data, 0)
        self.message_utils.output_log('I0000030')
