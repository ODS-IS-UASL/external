DROP TABLE IF EXISTS SWIM_MAX_FALL_RANGE;
CREATE TABLE SWIM_MAX_FALL_RANGE (
    business_number     VARCHAR(40) NOT NULL,
    max_fall_range_id   VARCHAR(40) NOT NULL,
    operator_id         TEXT NOT NULL,
    
    identifier          VARCHAR(3) DEFAULT NULL,
    internal_identifier VARCHAR(3) NOT NULL,
    swim_id VARCHAR(8) NOT NULL,
    
    enable BOOLEAN DEFAULT TRUE,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	PRIMARY KEY (business_number, max_fall_range_id)
);
COMMENT ON TABLE SWIM_MAX_FALL_RANGE IS '事業者情報';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.business_number IS '事業者番号';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.max_fall_range_id IS '最大落下範囲ID';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.operator_id IS '事業者ID(UUID)';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.identifier IS '事業者ID';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.internal_identifier IS '事業者内ID';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.swim_id IS 'SWIM designator';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.enable IS '有効フラグ';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.create_at IS '作成日時';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE.update_at IS '更新日時';

DROP TABLE IF EXISTS SWIM_MAX_FALL_RANGE_HISTORY;
CREATE TABLE SWIM_MAX_FALL_RANGE_HISTORY (
    business_number     VARCHAR(40) NOT NULL,
    max_fall_range_id   VARCHAR(40) NOT NULL,
    history_index integer,
    
    feature_id TEXT,
    feature_timesliceid TEXT,
    feature_begin_position TEXT,
    feature_end_position TEXT,
    feature_designator TEXT,  
    feature_activation_index Integer,
    feature_geometry_component_index Integer,
    
    object10_objid TEXT,
    object10_timeInterval_index Integer,
    
    object13_par_objid TEXT,
    object13_objid TEXT,
    
    object24_objid TEXT,
    object24_air_space_volume_index Integer,
    
    object29_par_objid TEXT,
    object29_objid TEXT,
    object29_gml TEXT,
    
    object38_par_objid TEXT,
    object38_objid TEXT,
    object38_upperlimit TEXT, 
    object38_horizontal_projection_index Integer,

    enable BOOLEAN DEFAULT TRUE,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	PRIMARY KEY (business_number, max_fall_range_id, history_index)
);

COMMENT ON TABLE SWIM_MAX_FALL_RANGE_HISTORY IS 'Excel発行履歴　';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.business_number IS '事業者番号';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.max_fall_range_id IS '最大落下範囲ID';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.history_index IS '履歴番号';

COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.feature_id IS 'FeatureID';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.feature_timesliceid IS 'TimesliceID';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.feature_begin_position IS 'beginPosition';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.feature_end_position IS 'endPosition';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.feature_designator   IS 'SWIM designator';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.feature_activation_index IS 'Object10に入力したオブジェクト数';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.feature_geometry_component_index IS 'Object24に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object10_objid IS 'object10_objid';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object10_timeInterval_index IS 'Object13に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object13_par_objid IS 'object10_objid';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object13_objid IS 'object13_objid';

COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object24_objid IS 'object24_objid';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object24_air_space_volume_index IS 'Object38に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object29_par_objid IS 'object38_objid';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object29_objid IS 'object29_objid';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object29_gml IS 'GML文字列';

COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object38_par_objid IS 'object24_objid';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object38_objid IS 'object38_objid';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object38_upperlimit  IS '最高高度';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.object38_horizontal_projection_index IS 'Object29に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.enable IS '有効フラグ';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.create_at IS '作成日時';
COMMENT ON COLUMN SWIM_MAX_FALL_RANGE_HISTORY.update_at IS '更新日時';  
 


DROP TABLE IF EXISTS SWIM_FEEDBACK;
CREATE TABLE SWIM_FEEDBACK (
    business_number     VARCHAR(40) NOT NULL,
    max_fall_range_id   VARCHAR(40) NOT NULL,
    history_index integer,

    feature_id TEXT,
    feature_timesliceid TEXT,
    feature_begin_position TEXT,
    feature_end_position TEXT,
    feature_designator TEXT,  
    feature_activation_index Integer,
    feature_geometry_component_index Integer,
    
    object10_objid TEXT,
    object10_timeInterval_index Integer,
    
    object13_par_objid TEXT,
    object13_objid TEXT,
    
    object24_objid TEXT,
    object24_air_space_volume_index Integer,
    
    object29_par_objid TEXT,
    object29_objid TEXT,
    object29_gml TEXT,
    
    object38_par_objid TEXT,
    object38_objid TEXT,
    object38_upperlimit TEXT, 
    object38_horizontal_projection_index Integer,

    enable BOOLEAN DEFAULT TRUE,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	PRIMARY KEY (business_number, max_fall_range_id, history_index)
);

COMMENT ON TABLE SWIM_FEEDBACK IS 'フィードバック情報';

COMMENT ON COLUMN SWIM_FEEDBACK.business_number IS '事業者番号';
COMMENT ON COLUMN SWIM_FEEDBACK.max_fall_range_id IS '最大落下範囲ID';
COMMENT ON COLUMN SWIM_FEEDBACK.history_index IS '履歴番号';

COMMENT ON COLUMN SWIM_FEEDBACK.feature_id IS 'FeatureID';
COMMENT ON COLUMN SWIM_FEEDBACK.feature_timesliceid IS 'TimesliceID';
COMMENT ON COLUMN SWIM_FEEDBACK.feature_begin_position IS 'beginPosition';
COMMENT ON COLUMN SWIM_FEEDBACK.feature_end_position IS 'endPosition';
COMMENT ON COLUMN SWIM_FEEDBACK.feature_designator   IS 'SWIM designator';
COMMENT ON COLUMN SWIM_FEEDBACK.feature_activation_index IS 'Object10に入力したオブジェクト数';
COMMENT ON COLUMN SWIM_FEEDBACK.feature_geometry_component_index IS 'Object24に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_FEEDBACK.object10_objid IS 'object10_objid';
COMMENT ON COLUMN SWIM_FEEDBACK.object10_timeInterval_index IS 'Object13に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_FEEDBACK.object13_par_objid IS 'object10_objid';
COMMENT ON COLUMN SWIM_FEEDBACK.object13_objid IS 'object13_objid';

COMMENT ON COLUMN SWIM_FEEDBACK.object24_objid IS 'object24_objid';
COMMENT ON COLUMN SWIM_FEEDBACK.object24_air_space_volume_index IS 'Object38に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_FEEDBACK.object29_par_objid IS 'object38_objid';
COMMENT ON COLUMN SWIM_FEEDBACK.object29_objid IS 'object29_objid';
COMMENT ON COLUMN SWIM_FEEDBACK.object29_gml IS 'GML文字列';

COMMENT ON COLUMN SWIM_FEEDBACK.object38_par_objid IS 'object24_objid';
COMMENT ON COLUMN SWIM_FEEDBACK.object38_objid IS 'object38_objid';
COMMENT ON COLUMN SWIM_FEEDBACK.object38_upperlimit  IS '最高高度';
COMMENT ON COLUMN SWIM_FEEDBACK.object38_horizontal_projection_index IS 'Object29に入力したオブジェクト数';

COMMENT ON COLUMN SWIM_FEEDBACK.enable IS '有効フラグ';
COMMENT ON COLUMN SWIM_FEEDBACK.create_at IS '作成日時';
COMMENT ON COLUMN SWIM_FEEDBACK.update_at IS '更新日時';    
