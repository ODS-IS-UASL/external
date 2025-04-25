/* Create Schema */
CREATE SCHEMA IF NOT EXISTS drone;

/* Search_path H2DB UnUse*/
--ALTER ROLE postgres SET search_path TO drone, sample, public;

/* Drop Tables */
DROP TABLE IF EXISTS dips_access_tokens;

CREATE TABLE IF NOT EXISTS dips_access_tokens
(
    client_id varchar(50) NOT NULL,
    access_token varchar(2000) NOT NULL,
    expires_in int NOT NULL,
    refresh_expires_in int NOT NULL,
    refresh_token varchar(2000) NOT NULL,
    token_type varchar(10) NOT NULL,
    id_token varchar(2000) NOT NULL,
    not_before_policy int NOT NULL,
    session_state varchar(50) NOT NULL,
    scope varchar(50) NOT NULL,
    create_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logic_delete_flag BOOLEAN,
    PRIMARY KEY (client_id)
);

COMMENT ON COLUMN dips_access_tokens.client_id IS '事業者ID';
COMMENT ON COLUMN dips_access_tokens.access_token IS 'アクセストークン';
COMMENT ON COLUMN dips_access_tokens.expires_in IS 'アクセストークン有効時間';
COMMENT ON COLUMN dips_access_tokens.refresh_expires_in IS 'リフレッシュトークン有効時間';
COMMENT ON COLUMN dips_access_tokens.refresh_token IS 'リフレッシュトークン';
COMMENT ON COLUMN dips_access_tokens.token_type IS 'トークンタイプ';
COMMENT ON COLUMN dips_access_tokens.id_token IS 'IDトークン';
COMMENT ON COLUMN dips_access_tokens.not_before_policy IS 'アクセストークン有効性確認値';
COMMENT ON COLUMN dips_access_tokens.session_state IS 'セッション状態';
COMMENT ON COLUMN dips_access_tokens.scope IS 'スコープ';
COMMENT ON COLUMN dips_access_tokens.create_datetime IS '作成日時';
COMMENT ON COLUMN dips_access_tokens.update_datetime IS '更新日時';
COMMENT ON COLUMN dips_access_tokens.logic_delete_flag IS '論理削除フラグ';


/* 関係者周知 Begin */
-- 事業者情報
DROP TABLE IF EXISTS TENANT CASCADE;
CREATE TABLE IF NOT EXISTS TENANT (
    operator_id VARCHAR(36) NOT NULL PRIMARY KEY,
    operator_name VARCHAR(100) NOT NULL,
    user_type CHAR(1) NOT NULL,
    notification_type CHAR(1) NOT NULL,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMENT ON TABLE TENANT IS '事業者情報';
COMMENT ON COLUMN TENANT.operator_id IS '事業者ID';
COMMENT ON COLUMN TENANT.operator_name IS '事業者名称';
COMMENT ON COLUMN TENANT.user_type IS 'ユーザ区分';
COMMENT ON COLUMN TENANT.notification_type IS '周知方法';
COMMENT ON COLUMN TENANT.creation_id IS '作成者ID';
COMMENT ON COLUMN TENANT.creation_datetime IS '作成日時';
COMMENT ON COLUMN TENANT.update_id IS '更新者ID';
COMMENT ON COLUMN TENANT.update_datetime IS '更新日時';

-- 周知先情報
DROP TABLE IF EXISTS NOTIFICATION_TARGET_INFO CASCADE;
CREATE TABLE IF NOT EXISTS NOTIFICATION_TARGET_INFO (
    operator_id VARCHAR(36) NOT NULL,
    notification_target VARCHAR(254) NOT NULL,
    notification_type CHAR (1) NOT NULL,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(operator_id,notification_target)
   );
COMMENT ON TABLE NOTIFICATION_TARGET_INFO IS '周知先情報';
COMMENT ON COLUMN NOTIFICATION_TARGET_INFO.operator_id IS '事業者ID';
COMMENT ON COLUMN NOTIFICATION_TARGET_INFO.notification_target IS '周知先';
COMMENT ON COLUMN NOTIFICATION_TARGET_INFO.notification_type IS '周知方法';
COMMENT ON COLUMN NOTIFICATION_TARGET_INFO.creation_id IS '作成者ID';
COMMENT ON COLUMN NOTIFICATION_TARGET_INFO.creation_datetime IS '作成日時';
COMMENT ON COLUMN NOTIFICATION_TARGET_INFO.update_id IS '更新者ID';
COMMENT ON COLUMN NOTIFICATION_TARGET_INFO.update_datetime IS '更新日時';

-- メール周知履歴
DROP TABLE IF EXISTS MAIL_SENT_INFO CASCADE;
CREATE TABLE IF NOT EXISTS MAIL_SENT_INFO (
    operator_id VARCHAR(36) NOT NULL,
    message_type VARCHAR(2) NOT NULL,
    notification_target VARCHAR(254) NOT NULL,
    notification_type CHAR (1) NOT NULL,
    mail_detail VARCHAR(2048) NOT NULL,
    sent_result CHAR (2)  NOT NULL,
    failed_reason VARCHAR(100),  
    sent_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMENT ON TABLE MAIL_SENT_INFO IS 'メール周知履歴';
COMMENT ON COLUMN MAIL_SENT_INFO.operator_id IS '事業者ID';
COMMENT ON COLUMN MAIL_SENT_INFO.message_type IS '周知種別';
COMMENT ON COLUMN MAIL_SENT_INFO.notification_target IS '周知先';
COMMENT ON COLUMN MAIL_SENT_INFO.notification_type IS '周知方法';
COMMENT ON COLUMN MAIL_SENT_INFO.mail_detail IS '送信内容';
COMMENT ON COLUMN MAIL_SENT_INFO.sent_result IS '送信結果';
COMMENT ON COLUMN MAIL_SENT_INFO.failed_reason IS '失敗理由';
COMMENT ON COLUMN MAIL_SENT_INFO.sent_datetime IS '送信日時';
COMMENT ON COLUMN MAIL_SENT_INFO.creation_id IS '作成者ID';
COMMENT ON COLUMN MAIL_SENT_INFO.creation_datetime IS '作成日時';
COMMENT ON COLUMN MAIL_SENT_INFO.update_id IS '更新者ID';
COMMENT ON COLUMN MAIL_SENT_INFO.update_datetime IS '更新日時';

-- 航路区画情報
DROP TABLE IF EXISTS AIRWAY_SECTION CASCADE;
CREATE TABLE IF NOT EXISTS AIRWAY_SECTION (
    airway_id VARCHAR(300) NOT NULL,
    airway_section_id VARCHAR(36) NOT NULL,
    airway_section_name VARCHAR(100) NOT NULL,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(airway_id,airway_section_id)
);
COMMENT ON TABLE AIRWAY_SECTION IS '航路区画情報';
COMMENT ON COLUMN AIRWAY_SECTION.airway_id IS '航路ID';
COMMENT ON COLUMN AIRWAY_SECTION.airway_section_id IS '航路区画ID';
COMMENT ON COLUMN AIRWAY_SECTION.airway_section_name IS '航路区画名';
COMMENT ON COLUMN AIRWAY_SECTION.creation_id IS '作成者ID';
COMMENT ON COLUMN AIRWAY_SECTION.creation_datetime IS '作成日時';
COMMENT ON COLUMN AIRWAY_SECTION.update_id IS '更新者ID';
COMMENT ON COLUMN AIRWAY_SECTION.update_datetime IS '更新日時';

-- 航路情報
DROP TABLE IF EXISTS AIRWAY CASCADE;
CREATE TABLE IF NOT EXISTS AIRWAY (
    airway_id VARCHAR(300) NOT NULL  PRIMARY KEY,
    airway_name VARCHAR(100) NOT NULL,
    airway_administrator_id VARCHAR(100) NOT NULL,
    status CHAR (1) NOT NULL,
    registered_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    airway_created_at TIMESTAMP,
    airway_updated_at TIMESTAMP,
    flight_purpose VARCHAR(100),
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMENT ON TABLE AIRWAY IS '航路情報';
COMMENT ON COLUMN AIRWAY.airway_id IS '航路ID';
COMMENT ON COLUMN AIRWAY.airway_name IS '航路名称';
COMMENT ON COLUMN AIRWAY.airway_administrator_id IS '航路運営者ID';
COMMENT ON COLUMN AIRWAY.status IS '処理区分';
COMMENT ON COLUMN AIRWAY.registered_at IS '登録日時';
COMMENT ON COLUMN AIRWAY.updated_at IS '更新日時';
COMMENT ON COLUMN AIRWAY.airway_created_at IS '航路作成日時';
COMMENT ON COLUMN AIRWAY.airway_updated_at IS '航路更新日時';
COMMENT ON COLUMN AIRWAY.flight_purpose IS '飛行目的';
COMMENT ON COLUMN AIRWAY.creation_id IS '作成者ID';
COMMENT ON COLUMN AIRWAY.creation_datetime IS '作成日時';
COMMENT ON COLUMN AIRWAY.update_id IS '更新者ID';
COMMENT ON COLUMN AIRWAY.update_datetime IS '更新日時';

-- 航路予約情報
DROP TABLE IF EXISTS AIRWAY_RESERVATION CASCADE;
CREATE TABLE IF NOT EXISTS AIRWAY_RESERVATION (
    airway_reserve_id VARCHAR(36) NOT NULL PRIMARY KEY,
    operator_id VARCHAR(36) NOT NULL,
    event_id VARCHAR(36) NOT NULL,
    status CHAR(1) NOT NULL,
    reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP ,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
COMMENT ON TABLE AIRWAY_RESERVATION IS '航路予約情報';
COMMENT ON COLUMN AIRWAY_RESERVATION.airway_reserve_id IS '航路予約ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.operator_id IS '運航事業者(予約者)ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.event_id IS 'PublishイベントID';
COMMENT ON COLUMN AIRWAY_RESERVATION.status IS '処理区分';
COMMENT ON COLUMN AIRWAY_RESERVATION.reserved_at IS '予約完了日時D';
COMMENT ON COLUMN AIRWAY_RESERVATION.updated_at IS '予約状態更新日時';
COMMENT ON COLUMN AIRWAY_RESERVATION.creation_id IS '作成者ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.creation_datetime IS '作成日時';
COMMENT ON COLUMN AIRWAY_RESERVATION.update_id IS '更新者ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.update_datetime IS '更新日時';

-- 航路事業者関連情報
DROP TABLE IF EXISTS  AIRWAY_TENANT_ASSOCIATION CASCADE;
CREATE TABLE IF NOT EXISTS AIRWAY_TENANT_ASSOCIATION (
    airway_id VARCHAR(300) NOT NULL,
    operator_id VARCHAR(36) NOT NULL,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(airway_id,operator_id)
);
COMMENT ON TABLE AIRWAY_TENANT_ASSOCIATION IS '航路事業者関連情報';
COMMENT ON COLUMN AIRWAY_TENANT_ASSOCIATION.airway_id IS '航路ID';
COMMENT ON COLUMN AIRWAY_TENANT_ASSOCIATION.operator_id IS '事業者ID';
COMMENT ON COLUMN AIRWAY_TENANT_ASSOCIATION.creation_id IS '作成者ID';
COMMENT ON COLUMN AIRWAY_TENANT_ASSOCIATION.creation_datetime IS '作成日時';
COMMENT ON COLUMN AIRWAY_TENANT_ASSOCIATION.update_id IS '更新者ID';
COMMENT ON COLUMN AIRWAY_TENANT_ASSOCIATION.update_datetime IS '更新日時';

-- 予約情報航路関連情報
DROP TABLE IF EXISTS RESERVATION_AIRWAY_ASSOCIATION CASCADE;
CREATE TABLE IF NOT EXISTS RESERVATION_AIRWAY_ASSOCIATION (
    airway_reserve_id VARCHAR(36) NOT NULL,
    airway_section_id VARCHAR(36) NOT NULL,
    start_at TIMESTAMP ,
    end_at  TIMESTAMP ,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(airway_reserve_id,airway_section_id)
);
COMMENT ON TABLE RESERVATION_AIRWAY_ASSOCIATION IS '予約情報航路関連情報';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.airway_reserve_id IS '航路予約ID';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.airway_section_id IS '航路区画ID';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.start_at IS '予約開始日時';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.end_at IS '予約終了日時';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.creation_id IS '作成者ID';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.creation_datetime IS '作成日時';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.update_id IS '更新者ID';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.update_datetime IS '更新日時';

-- 事業者権限
DROP TABLE IF EXISTS TENANT_ROLE CASCADE;
CREATE TABLE IF NOT EXISTS TENANT_ROLE (
    operator_id VARCHAR(36) NOT NULL,
    role CHAR (1) NOT NULL,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(operator_id,role)
);
COMMENT ON TABLE TENANT_ROLE IS '事業者権限';
COMMENT ON COLUMN TENANT_ROLE.operator_id IS '事業者ID';
COMMENT ON COLUMN TENANT_ROLE.role IS '権限';
COMMENT ON COLUMN TENANT_ROLE.creation_id IS '作成者ID';
COMMENT ON COLUMN TENANT_ROLE.creation_datetime IS '作成日時';
COMMENT ON COLUMN TENANT_ROLE.update_id IS '更新者ID';
COMMENT ON COLUMN TENANT_ROLE.update_datetime IS '更新日時';

/* 関係者周知 End */