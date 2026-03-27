--INSERT INTO dips_access_tokens VALUES ('2aa899fb-920a-479f-9c98-6dff943dbf31', 'aaa', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);
INSERT INTO dips_access_tokens VALUES ('2aa899fb', 'aaa', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);
--INSERT INTO dips_access_tokens VALUES ('2aa899fb-920a-479f-9c98', 'aaa', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);
INSERT INTO dips_access_tokens VALUES ('XXXXX', '2aa899fb-920a-479f-9c98-6dff943dbf31', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);

/* 関係者周知 Begin */
--航路情報テーブル
INSERT INTO AIRWAY (
    airway_id,
    airway_name,
    airway_administrator_id,
    status,
    registered_at,
    updated_at,
    airway_created_at,
    airway_updated_at,
    flight_purpose,
    creation_id,
    creation_datetime,
    update_id,
    update_datetime
)
VALUES (
    'uasl_001',        
    '东京-大阪航路',   
    'ADMIN001',        
    '1',               
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP, 
    '旅客輸送',        
    1001,              
    CURRENT_TIMESTAMP, 
    1001,              
    CURRENT_TIMESTAMP  
);

--関係者情報テーブル
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010101','1','北海道','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010201','1','青森県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010301','1','岩手県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010401','1','宮城県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010501','1','秋田県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010601','1','山形県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010701','1','福島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010801','1','東京都','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a010901','1','茨城県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011001','1','栃木県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011101','1','群馬県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011201','1','埼玉県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011301','1','千葉県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011401','1','神奈川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011501','1','新潟県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011601','1','山梨県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011701','1','長野県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011801','1','静岡県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a011901','1','富山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012001','1','石川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012101','1','福井県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012201','1','岐阜県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012301','1','愛知県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012401','1','三重県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012501','1','滋賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012601','1','京都府','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012701','1','大阪府','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012801','1','兵庫県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a012901','1','奈良県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013001','1','和歌山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013101','1','鳥取県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013201','1','島根県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013301','1','岡山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013401','1','広島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013501','1','山口県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013601','1','徳島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013701','1','香川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013801','1','愛媛県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a013901','1','高知県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014001','1','福岡県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014101','1','佐賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014201','1','長崎県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014301','1','熊本県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014401','1','大分県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014501','1','宮崎県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014601','1','鹿児島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--関係者市区町村テーブル
--国境離島を除く沖縄県全域
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','1','沖縄県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014702','1','沖縄県','3','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','宜野湾市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','浦添市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','名護市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','糸満市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','沖縄市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','豊見城市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','うるま市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','国頭村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','大宜味村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','東村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','本部町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','今帰仁村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','恩納村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','金武町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','読谷村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','嘉手納町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','北谷町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','北中城村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','中城村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','西原町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','那覇市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','南城市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','南風原町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','与那原町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a014701','八重瀬町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--関係者情報テーブル
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020101','2','北海道','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020201','2','青森県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020301','2','岩手県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020401','2','宮城県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020402','2','宮城県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020501','2','秋田県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020601','2','山形県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020701','2','福島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020801','2','東京都','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020802','2','東京都','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020901','2','茨城県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021001','2','栃木県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021101','2','群馬県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021201','2','埼玉県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021301','2','千葉県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021401','2','神奈川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','2','神奈川県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021501','2','新潟県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021601','2','山梨県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021701','2','長野県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021801','2','静岡県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021901','2','富山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022001','2','石川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022101','2','福井県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022201','2','岐阜県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022301','2','愛知県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022401','2','三重県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022501','2','滋賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022601','2','京都府','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022701','2','大阪府','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022801','2','兵庫県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a022901','2','奈良県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023001','2','和歌山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023101','2','鳥取県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023201','2','島根県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023301','2','岡山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023302','2','岡山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023401','2','広島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023402','2','広島県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023501','2','山口県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023601','2','徳島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023701','2','香川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023801','2','愛媛県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a023901','2','高知県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024001','2','福岡県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024002','2','福岡県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024101','2','佐賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024201','2','長崎県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024301','2','熊本県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024401','2','大分県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024501','2','宮崎県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024601','2','鹿児島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--関係者市区町村テーブル
--神奈川県(県一部) 
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a020402','仙台市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','金沢区福浦',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','金沢区八景島',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','金沢区海の公園',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','金沢区平潟町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','金沢区乙舳町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','金沢区野島町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a021402','横須賀市夏島町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a024002','北九州市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--関係者情報テーブル
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030101','3','北海道','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030201','3','青森県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030202','3','青森県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030301','3','岩手県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030302','3','岩手県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030401','3','宮城県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030501','3','秋田県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030601','3','山形県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','3','山形県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030701','3','福島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030702','3','福島県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030801','3','東京都','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030901','3','茨城県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','3','茨城県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031001','3','栃木県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','3','栃木県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031101','3','群馬県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','3','群馬県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031201','3','埼玉県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031301','3','千葉県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031302','3','千葉県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031401','3','神奈川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','3','神奈川県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031501','3','新潟県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031601','3','山梨県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031602','3','山梨県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031701','3','長野県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031702','3','長野県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031801','3','静岡県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031802','3','静岡県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031901','3','富山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032001','3','石川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032101','3','福井県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032102','3','福井県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032103','3','福井県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032201','3','岐阜県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032202','3','岐阜県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032301','3','愛知県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','3','愛知県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032401','3','三重県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032501','3','滋賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032601','3','京都府','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032701','3','大阪府','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032801','3','兵庫県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032802','3','兵庫県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032803','3','兵庫県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032901','3','奈良県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033001','3','和歌山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033101','3','鳥取県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033102','3','鳥取県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033103','3','鳥取県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033201','3','島根県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033202','3','島根県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033301','3','岡山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033302','3','岡山県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033401','3','広島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','3','広島県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','3','広島県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033501','3','山口県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033601','3','徳島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033701','3','香川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033702','3','香川県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033801','3','愛媛県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033901','3','高知県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034001','3','福岡県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034101','3','佐賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034102','3','佐賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034103','3','佐賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034104','3','佐賀県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034201','3','長崎県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','3','長崎県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','3','長崎県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034301','3','熊本県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034302','3','熊本県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034401','3','大分県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034501','3','宮崎県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034502','3','宮崎県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034601','3','鹿児島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034602','3','鹿児島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034603','3','鹿児島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034604','3','鹿児島県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','3','鹿児島県','2','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034701','3','沖縄県','1','',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--関係者市区町村テーブル
--青森県(県南部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030202','弘前市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030202','鯵ヶ沢町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030202','深浦町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--山形県(県北部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','遊佐町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','酒田市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','真室川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','鮭川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','金山町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','庄内町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','戸沢村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','新庄市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030602','最上町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--福島県(県の一部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030702','矢祭町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030702','塙町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--茨城県(県南部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','鉾田市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','行方市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','鹿嶋市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','潮来市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','神栖市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','美浦村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','稲敷市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','牛久市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','龍ケ崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','河内町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','利根町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','取手市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','つくばみらい市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','守谷市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030902','常総市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--栃木県(県東部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','那珂川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','那須烏山市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','茂木町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','市貝町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','益子町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','真岡市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','さくら市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','高根沢町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031002','芳賀町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--群馬県(群馬県南部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','伊勢崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','玉村町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','太田市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','大泉町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','館林市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','板倉町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','明和町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','千代田町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','邑楽町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','藤岡市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','高崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','吉井町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','神流町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031102','上野村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--神奈川県(県の一部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','川崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','横須賀市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','鎌倉市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','逗子市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','三浦市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','葉山町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','相模原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','厚木市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','大和市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','海老名市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','座間市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','綾瀬市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','愛川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','清川村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','平塚市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','藤沢市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','茅ヶ崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','秦野市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','伊勢原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','寒川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','大磯町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','二宮町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','小田原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','南足柄市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','中井町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','大井町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','松田町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','山北町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','開成町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','山北町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','箱根町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','真鶴町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031402','湯河原町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--福井県(県の一部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032103','大野市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--岐阜県(飛騨地域北部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032202','高山市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032202','飛騨市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032202','白川村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--静岡県(県西部及び中部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031802','湖西市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031802','焼津市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031802','藤枝市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031802','島田市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a031802','川根本町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--愛知県(尾張地方)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','上野村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','名古屋市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','一宮市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','瀬戸市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','春日井市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','犬山市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','江南市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','小牧市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','稲沢市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','尾張旭市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','岩倉市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','豊明市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','日進市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','清須市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','北名古屋市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','長久手市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','東郷町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','豊山町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','大口町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','扶桑町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','津島市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','愛西市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','弥富市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','あま市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','大治町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','蟹江町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','飛島村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','半田市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','常滑市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','東海市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','大府市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','知多市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','阿久比町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','東浦町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','南知多町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','美浜町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032302','武豊町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--兵庫県(県の一部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032802','赤穂市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032802','赤穂郡上郡町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032802','佐用郡佐用町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032802','宍粟市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032803','赤穂市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032803','赤穂郡上郡町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032803','佐用郡佐用町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a032803','宍粟市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--島根県(県の一部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033202','益田市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033202','津和野町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033202','吉賀町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--広島県(県東部)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','三次市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','庄原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','府中市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','神石高原町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','福山市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','尾道市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','世羅郡世羅町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033402','三原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','三次市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','庄原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','府中市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','神石高原町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','福山市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','尾道市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','世羅郡世羅町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a033403','三原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--長崎県(県本土)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','長崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','長与町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','時津町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','諫早市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','大村市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','島原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','雲仙市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','南島原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','佐世保市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','平戸市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','松浦市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','西海市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','小値賀町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','佐々町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034202','新上五島町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','長崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','長与町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','時津町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','諫早市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','大村市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','島原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','雲仙市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','南島原市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','佐世保市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','平戸市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','松浦市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','西海市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','小値賀町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','佐々町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034203','新上五島町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','上野村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--鹿児島県(県本土)
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','鹿児島市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','日置市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','いちき串木野市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','三島村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','十島村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','枕崎市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','指宿市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','南さつま市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','南九州市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','阿久根市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','出水市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','薩摩川内市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','さつま町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','長島町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','霧島市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','伊佐市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','姶良市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','湧水町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','鹿屋市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','垂水市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','曽於市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','志布志市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','大崎町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','東串良町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','錦江町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','南大隅町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a034605','肝付町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--関係者情報テーブル
insert into related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','3','北海道','2','道央エリア',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','3','北海道','2','道北エリア',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','3','北海道','2','道東エリア',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_parties_info (operator_id, related_category, flight_prefecture, extraction_conditions, notification_remark, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','3','北海道','2','道南エリア',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
--関係者市区町村テーブル
--石狩振興局（6市1町1村） 道央エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','札幌市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','江別市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','千歳市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','恵庭市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','北広島市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','石狩市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','当別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','新篠津村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--後志総合振興局（1市13町6村） 道央エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','小樽市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','島牧村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','寿都町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','黒松内町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','蘭越町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','ニセコ町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','真狩村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','留寿都村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','喜茂別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','京極町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','倶知安町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','共和町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','岩内町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','泊村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','神恵内村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','積丹町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','古平町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','仁木町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','余市町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','赤井川村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--空知総合振興局（10市14町） 道央エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','夕張市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','岩見沢市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','美唄市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','芦別市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','赤平市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','三笠市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','滝川市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','砂川市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','歌志内市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','深川市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','南幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','奈井江町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','上砂川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','由仁町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','長沼町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','栗山町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','月形町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','浦臼町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','新十津川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','妹背牛町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','秩父別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','雨竜町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','北竜町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','沼田町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--胆振総合振興局（4市7町） 道央エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','室蘭市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','苫小牧市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','登別市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','伊達市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','豊浦町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','壮瞥町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','白老町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','厚真町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','洞爺湖町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','安平町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','むかわ町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--日高振興局（7町） 道央エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','日高町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','平取町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','新冠町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','浦河町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','様似町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','えりも町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','新ひだか町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--上川（富良野圏域）（1市4町村） 道央エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','富良野市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','上富良野町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','中富良野町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','南富良野町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','占冠村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--留萌振興局（1市6町1村） 道央エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','留萌市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','増毛町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','小平町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','苫前町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','羽幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','遠別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','天塩町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030102','初山別村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--宗谷総合振興局（1市8町1村） 道北エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','稚内市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','豊富町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','猿払村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','浜頓別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','中頓別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','枝幸町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','礼文町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','利尻町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','利尻富士町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','幌延町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--オホーツク（遠紋圏域）（1市6町1村） 道北エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','紋別市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','佐呂間町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','遠軽町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','湧別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','滝上町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','興部町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','雄武町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','西興部村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--上川総合振興局（4市17町2村） 道北エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','旭川市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','名寄市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','富良野市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','士別市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','鷹栖町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','東神楽町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','当麻町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','比布町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','愛別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','上川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','東川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','美瑛町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','上富良野町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','中富良野町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','南富良野町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','占冠村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','和寒町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','剣淵町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','下川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','美深町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','音威子府村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','中川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','幌加内町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--留萌振興局（1市6町1村） 道北エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','留萌市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','増毛町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','小平町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','苫前町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','羽幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','遠別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','天塩町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','初山別村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--空知（北空知圏域）（1市4町） 道北エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','深川市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','妹背牛町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','秩父別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','北竜町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','沼田町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--空知（中空知圏域（一部）〔道北エリア側・一部利用想定〕）（5市5町） 道北エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','芦別市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','赤平市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','滝川市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','砂川市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','歌志内市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','奈井江町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','上砂川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','浦臼町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','新十津川町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','雨竜町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--十勝（一部）（『一部』の範囲が明確でないため、全市区町村を含めています。）（1市16町2村） 道北エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','帯広市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','音更町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','士幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','上士幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','鹿追町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','新得町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','清水町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','芽室町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','中札内村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','更別村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','大樹町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','広尾町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','幕別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','池田町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','豊頃町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','本別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','足寄町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','陸別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030103','浦幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--釧路総合振興局（1市6町1村） 道東エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','釧路市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','釧路町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','厚岸町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','浜中町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','標茶町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','弟子屈町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','鶴居村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','白糠町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--根室振興局（1市4町） 道東エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','根室市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','別海町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','中標津町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','標津町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','羅臼町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--十勝総合振興局（1市16町2村） 道東エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','帯広市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','音更町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','士幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','上士幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','鹿追町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','新得町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','清水町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','芽室町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','中札内村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','更別村',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','大樹町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','広尾町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','幕別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','池田町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','豊頃町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','本別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','足寄町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','陸別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','浦幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--オホーツク（北網）（2市8町） 道東エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','北見市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','網走市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','美幌町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','津別町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','斜里町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','清里町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','小清水町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','訓子府町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','置戸町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030104','大空町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--檜山振興局（7町） 道南エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','江差町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','上ノ国町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','厚沢部町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','乙部町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','奥尻町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','今金町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','せたな町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

--渡島総合振興局（2市9町） 道南エリア
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','函館市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','北斗市',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','松前町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','福島町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','知内町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','木古内町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','七飯町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','鹿部町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','森町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','八雲町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into related_municipality_info (operator_id, flight_municipality, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349a030105','長万部町',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );

/* 関係者周知 End */