# -*- coding: utf-8 -*-
"""
フライトコントローラから取得可能な情報をTelemetryとして保持するモジュール
"""


class TelemetryHolder:
    """
    フライトコントローラから取得可能な情報をTelemetryとして保持するクラス

    インスタンス変数で保持するTelemetry情報取得にあたっては必ずGetterメソッドを作成し、
    Getterメソッド経由で情報を扱うようにする。

    本クラスで保持している情報は明確な型指定をしていないため、型指定が必要な場合はGetterメソッドを
    利用する側で行うこと。
    """
    armed = None
    """armed"""
    location_global_lat = None
    """latitude (GlobalLocation)"""
    location_global_lon = None
    """longitude (GlobalLocation)"""
    location_global_alt = None
    """altitude (GlobalLocation)"""
    location_global_relative_alt = None
    """relative altitude"""
    location_local_north = None
    """north (LocalLocation)"""
    location_local_east = None
    """east (LocalLocation)"""
    location_local_down = None
    """down (LocalLocation)"""
    heading = None
    """heading"""
    attitude_pitch = None
    """pitch"""
    attitude_yaw = None
    """yaw"""
    attitude_roll = None
    """roll"""
    velocity_north = None
    """north (velocity)"""
    velocity_east = None
    """east (velocity)"""
    velocity_ground = None
    """ground (velocity)"""
    speed_air = None
    """air speed"""
    speed_ground = None
    """ground speed"""
    battery_voltage = None
    """voltage (battery)"""
    battery_current = None
    """current (battery)"""
    battery_level = None
    """level (battery)"""
    location_home_lat = None
    """latitude (HomeLocation)"""
    location_home_lon = None
    """longitude (HomeLocation)"""
    gps_satellites = None
    """gps satellites num"""
    pressure = None
    """pressure"""
    temperature = None
    """temperature"""
    version = None
    """firmware version"""
    in_flight = False
    """in flight"""
    start_time_in_flight = None
    """start time in flight"""
    system_status = None
    """drone system status"""
    mode_name = None
    """mode name"""
    next_waypoint = None
    """next waypoint"""
    last_heartbeat = None
    """last heartbeat"""
    uas_status = None
    """uas status"""
    mission_id = None
    """mission id"""
    register_waypoints_dic = {}
    """register mission時のWaypoint位置情報辞書"""
    location_global_alt_gps = None
    """altitude (GlobalLocation GPS)"""
    location_global_relative_alt_gps = None
    """relative altitude (GPS)"""
    fc_status = None
    """fc status"""
    compass_data = None
    """compass data"""

    def __init__(self):
        """
        コンストラクタ
        """
        pass

    def is_armed(self):
        """
        armedを取得する。

        :return: armed
        """
        return self.armed

    def get_location_global_lat(self):
        """
        latitude (GlobalLocation)を取得する。
        単位をradianからdegreeに変換する

        :return: latitude (GlobalLocation)
        """
        return (self.location_global_lat)

    def get_location_global_lon(self):
        """
        longitude (GlobalLocation)を取得する。
        単位をradianからdegreeに変換する

        :return: longitude (GlobalLocation)
        """
        return (self.location_global_lon)

    def get_location_global_alt(self):
        """
        altitude (GlobalLocation)を取得する。

        :return: altitude (GlobalLocation)
        """
        return self.location_global_alt

    def get_location_global_relative_alt(self):
        """
        relative altitudeを取得する。

        :return: relative altitude
        """
        return self.location_global_relative_alt

    def get_location_local_north(self):
        """
        north (LocalLocation)を取得する。

        :return: north (LocalLocation)
        """
        return self.location_local_north

    def get_location_local_east(self):
        """
        east (LocalLocation)を取得する。

        :return: east (LocalLocation)
        """
        return self.location_local_east

    def get_location_local_down(self):
        """
        down (LocalLocation)を取得する。

        :return: down (LocalLocation)
        """
        return self.location_local_down

    def get_heading(self):
        """
        headingを取得する。

        :return: heading
        """
        return self.heading

    def get_attitude_pitch(self):
        """
        pitchを取得する。
        degree(-180度〜+180度)へ変換する

        :return: pitch
        """
        return self.attitude_pitch

    def get_attitude_yaw(self):
        """
        yawを取得する。
        degree(-180度〜+180度)へ変換する

        :return: yaw
        """
        return self.attitude_yaw

    def get_attitude_roll(self):
        """
        rollを取得する。
        degree(-180度〜+180度)へ変換する

        :return: roll
        """
        return self.attitude_roll

    def get_velocity_north(self):
        """
        north (velocity)を取得する。

        :return: north (velocity)
        """
        return self.velocity_north

    def get_velocity_east(self):
        """
        east (velocity)を取得する。

        :return: east (velocity)
        """
        return self.velocity_east

    def get_velocity_ground(self):
        """
        ground (velocity)を取得する。

        :return: ground (velocity)
        """
        return self.velocity_ground

    def get_speed_air(self):
        """
        air speedを取得する。

        :return: air speed
        """
        return self.speed_air

    def get_speed_ground(self):
        """
        ground speedを取得する。

        :return: ground speed
        """
        return self.speed_ground

    def get_battery_voltage(self):
        """
        voltage (battery)を取得する。

        :return: voltage (battery)
        """
        return self.battery_voltage

    def get_battery_current(self):
        """
        current (battery)を取得する。

        :return: current (battery)
        """
        return self.battery_current

    def get_battery_level(self):
        """
        level (battery)を取得する。

        :return: level (battery)
        """
        return self.battery_level

    def get_location_home_lat(self):
        """
        latitude (HomeLocation)を取得する。
        単位をradianからdegreeに変換する

        :return: latitude (HomeLocation)
        """
        return (self.location_home_lat)

    def get_location_home_lon(self):
        """
        longitude (HomeLocation)を取得する。
        単位をradianからdegreeに変換する

        :return: longitude (HomeLocation)
        """
        return (self.location_home_lon)

    def get_gps_satellites(self):
        """
        gps satellites numを取得する。

        :return: gps satellites num
        """
        return self.gps_satellites

    def get_pressure(self):
        """
        pressureを取得する。

        :return: pressure
        """
        return self.pressure

    def get_temperature(self):
        """
        temperatureを取得する。

        :return: temperature
        """
        return self.temperature

    def get_version(self):
        """
        firmware versionを取得する。

        :return: firmware version
        """
        return self.version

    def is_in_flight(self):
        """
        in flightを取得する。

        :return: in flight
        """
        return self.in_flight

    def get_start_time_in_flight(self):
        """
        start time in flightを取得する。

        :return: start time in flight
        """
        return self.start_time_in_flight

    def get_system_status(self):
        """
        drone system statusを取得する。

        :return: drone system status
        """
        return self.system_status

    def get_mode_name(self):
        """
        mode nameを取得する。

        :return: mode name
        """
        return self.mode_name

    def get_next_waypoint(self):
        """
        next waypointを取得する。(FOS取得時のWayPoint)

        :return: next waypoint
        """
        return 0

    def get_next_waypoint_fc_vehicle(self):
        """
        next waypointを取得する。(Vehicle ObjectのWaypoint)

        :return: next waypoint
        """
        return self.next_waypoint

    def get_last_heartbeat(self):
        """
        last heartbeatを取得する。

        :return: last heartbeat
        """
        return self.last_heartbeat

    def get_uas_status(self):
        """
        uas_statusを取得する。

        :return: uas_status
        """
        return self.uas_status

    def get_mission_id(self):
        """
        mission_idを取得する。

        :return: mission_id
        """
        return self.mission_id

    def get_location_global_alt_gps(self):
        """
        altitude (GlobalLocation GPS)を取得する。

        :return: altitude (GlobalLocation GPS)
        """
        return self.location_global_alt_gps

    def get_location_global_relative_alt_gps(self):
        """
        relative altitude (GPS)を取得する。

        :return: relative altitude (GPS)
        """
        return self.location_global_relative_alt_gps

    def get_fc_status(self):
        """
        fc statusを取得する。

        :return: fc status
        """
        return self.fc_status

    def get_compass_data(self):
        """
        compass dataを取得する。

        :return: compass data
        """
        return self.compass_data
