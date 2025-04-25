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
