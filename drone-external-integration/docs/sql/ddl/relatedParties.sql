-- スキーマの作成
CREATE SCHEMA IF NOT EXISTS drone;

-- スキーマパス設定
SET search_path TO drone;

-- テーブルの作成

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
	request_id VARCHAR(36) NOT NULL,
    airway_reserve_id VARCHAR(36) NOT NULL,
    operator_id VARCHAR(36) NOT NULL,
    event_id VARCHAR(36) NOT NULL,
    flight_purpose VARCHAR(10),
    status CHAR(1) NOT NULL,
    reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    uasl_id VARCHAR(300) NOT NULL,
    updated_at  TIMESTAMP ,
    creation_id INT NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_id INT NOT NULL,
    update_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(airway_reserve_id,request_id,uasl_id)
);
COMMENT ON TABLE AIRWAY_RESERVATION IS '航路予約情報';
COMMENT ON COLUMN AIRWAY_RESERVATION.request_id IS '親予約ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.airway_reserve_id IS '航路予約ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.operator_id IS '運航事業者(予約者)ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.event_id IS 'PublishイベントID';
COMMENT ON COLUMN AIRWAY_RESERVATION.flight_purpose IS '飛行目的';
COMMENT ON COLUMN AIRWAY_RESERVATION.status IS '処理区分';
COMMENT ON COLUMN AIRWAY_RESERVATION.reserved_at IS '予約完了日時D';
COMMENT ON COLUMN AIRWAY_RESERVATION.uasl_id IS '航路ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.updated_at IS '予約状態更新日時';
COMMENT ON COLUMN AIRWAY_RESERVATION.creation_id IS '作成者ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.creation_datetime IS '作成日時';
COMMENT ON COLUMN AIRWAY_RESERVATION.update_id IS '更新者ID';
COMMENT ON COLUMN AIRWAY_RESERVATION.update_datetime IS '更新日時';

-- 予約情報航路関連情報
DROP TABLE IF EXISTS RESERVATION_AIRWAY_ASSOCIATION CASCADE;
CREATE TABLE IF NOT EXISTS RESERVATION_AIRWAY_ASSOCIATION (
    airway_reserve_id VARCHAR(36) NOT NULL,
    airway_section_id VARCHAR(36) NOT NULL,
    sequence INT NOT NULL,
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
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.sequence IS '通過順';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.start_at IS '予約開始日時';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.end_at IS '予約終了日時';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.creation_id IS '作成者ID';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.creation_datetime IS '作成日時';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.update_id IS '更新者ID';
COMMENT ON COLUMN RESERVATION_AIRWAY_ASSOCIATION.update_datetime IS '更新日時';

-- 関係者情報
DROP TABLE IF EXISTS RELATED_PARTIES_INFO CASCADE;
CREATE TABLE IF NOT EXISTS RELATED_PARTIES_INFO (
    operator_id             VARCHAR(36)      NOT NULL,  -- 事業者ID (主キー)
    related_category        CHAR(1)          NOT NULL,  -- 関係者のカテゴリー
    flight_prefecture       VARCHAR(100)     NOT NULL,  -- 飛行エリアの都道府県
    extraction_conditions   CHAR(1)          NOT NULL,  -- 抽出条件
    notification_remark     TEXT,                       -- 備考
    creation_id             INTEGER          NOT NULL,  -- 作成者ID
    creation_datetime       TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 作成日時
    update_id               INTEGER          NOT NULL,  -- 更新者ID
    update_datetime         TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP -- 更新日時
);

COMMENT ON TABLE RELATED_PARTIES_INFO IS '関係者情報';
COMMENT ON COLUMN RELATED_PARTIES_INFO.operator_id             IS '事業者ID';
COMMENT ON COLUMN RELATED_PARTIES_INFO.related_category        IS '関係者のカテゴリー';
COMMENT ON COLUMN RELATED_PARTIES_INFO.flight_prefecture       IS '飛行エリアの都道府県';
COMMENT ON COLUMN RELATED_PARTIES_INFO.extraction_conditions   IS '抽出条件';
COMMENT ON COLUMN RELATED_PARTIES_INFO.notification_remark     IS '備考';
COMMENT ON COLUMN RELATED_PARTIES_INFO.creation_id             IS '作成者ID';
COMMENT ON COLUMN RELATED_PARTIES_INFO.creation_datetime       IS '作成日時';
COMMENT ON COLUMN RELATED_PARTIES_INFO.update_id               IS '更新者ID';
COMMENT ON COLUMN RELATED_PARTIES_INFO.update_datetime         IS '更新日時';

-- 関係者市区町村
DROP TABLE IF EXISTS RELATED_MUNICIPALITY_INFO CASCADE;
CREATE TABLE IF NOT EXISTS RELATED_MUNICIPALITY_INFO (
    operator_id         VARCHAR(36)      NOT NULL,  -- 事業者ID
    flight_municipality VARCHAR(100),               -- 飛行エリアの市区町村
    creation_id         INTEGER          NOT NULL,  -- 作成者ID
    creation_datetime   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 作成日時
    update_id           INTEGER          NOT NULL,  -- 更新者ID
    update_datetime     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP-- 更新日時
);
COMMENT ON TABLE RELATED_MUNICIPALITY_INFO IS '関係者市区町村';
COMMENT ON COLUMN RELATED_MUNICIPALITY_INFO.operator_id         IS '事業者ID';
COMMENT ON COLUMN RELATED_MUNICIPALITY_INFO.flight_municipality IS '飛行エリアの市区町村';
COMMENT ON COLUMN RELATED_MUNICIPALITY_INFO.creation_id         IS '作成者ID';
COMMENT ON COLUMN RELATED_MUNICIPALITY_INFO.creation_datetime   IS '作成日時';
COMMENT ON COLUMN RELATED_MUNICIPALITY_INFO.update_id           IS '更新者ID';
COMMENT ON COLUMN RELATED_MUNICIPALITY_INFO.update_datetime     IS '更新日時';

-- 航路予約関係者
DROP TABLE IF EXISTS AIRWAY_RESERVE_RELATED CASCADE;
CREATE TABLE IF NOT EXISTS AIRWAY_RESERVE_RELATED (
    airway_reserve_id    VARCHAR(36)      NOT NULL,  -- 航路予約ID
    request_id            VARCHAR(36)      NOT NULL,  -- 親予約ID
    operator_id          VARCHAR(36)      NOT NULL,  -- 事業者ID (外部キー)
    operator_name        VARCHAR(100)     NOT NULL,  -- 事業者名
    related_category     CHAR(1)          NOT NULL,  -- 関係者のカテゴリー
    notification_email   VARCHAR(254)     NOT NULL,  -- 周知先メールアドレス
    notification_phone   VARCHAR(20),                -- 電話番号
    notification_remark  TEXT,                       -- 備考
    creation_id          INTEGER          NOT NULL,  -- 作成者ID
    creation_datetime    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 作成日時
    update_id            INTEGER          NOT NULL,  -- 更新者ID
    update_datetime      TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 更新日時
    PRIMARY KEY (airway_reserve_id, operator_id)
);

COMMENT ON TABLE AIRWAY_RESERVE_RELATED IS '航路予約関係者';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.airway_reserve_id    IS '航路予約ID';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.request_id            IS '親予約ID';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.operator_id          IS '事業者ID';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.operator_name        IS '事業者名';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.related_category     IS '関係者のカテゴリー';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.notification_email   IS '周知先メールアドレス';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.notification_phone   IS '電話番号';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.notification_remark  IS '備考';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.creation_id          IS '作成者ID';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.creation_datetime    IS '作成日時';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.update_id            IS '更新者ID';
COMMENT ON COLUMN AIRWAY_RESERVE_RELATED.update_datetime      IS '更新日時';

-- メール周知履歴
DROP TABLE IF EXISTS MAIL_SENT_INFO CASCADE;
CREATE TABLE IF NOT EXISTS MAIL_SENT_INFO (
    operator_id VARCHAR(36) NOT NULL,
    message_type VARCHAR(2) NOT NULL,
    notification_target VARCHAR(254) NOT NULL,
    notification_type CHAR (1) NOT NULL,
    mail_detail VARCHAR(2048) NOT NULL,
    sent_result CHAR (2)  NOT NULL,
    failed_reason VARCHAR(2048),  
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
