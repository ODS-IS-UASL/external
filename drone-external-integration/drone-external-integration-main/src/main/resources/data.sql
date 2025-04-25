--INSERT INTO dips_access_tokens VALUES ('2aa899fb-920a-479f-9c98-6dff943dbf31', 'aaa', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);
INSERT INTO dips_access_tokens VALUES ('2aa899fb', 'aaa', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);
--INSERT INTO dips_access_tokens VALUES ('2aa899fb-920a-479f-9c98', 'aaa', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);
INSERT INTO dips_access_tokens VALUES ('XXXXX', '2aa899fb-920a-479f-9c98-6dff943dbf31', 10, 600, 'bbb', 'bearer', 'ccc', 0, 'ddd', 'openid profile offline_access', NULL, NULL, false);

/* 関係者周知 Begin */
insert into  tenant (operator_id, operator_name, user_type, notification_type, creation_id, creation_datetime, update_id, update_datetime) 
values ('53e6b8c7-1cec-ad87-1ca9-70349aa0b1bc','事業者A','1','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into  tenant (operator_id, operator_name, user_type, notification_type, creation_id, creation_datetime, update_id, update_datetime) 
values ('60c895e5-321a-fe8a-af39-f005f3206efb','事業者B','2','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant (operator_id, operator_name, user_type, notification_type, creation_id, creation_datetime, update_id, update_datetime) 
values ('8a281868-faef-f554-3b75-c1fd73770f16','事業者C','3','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant (operator_id, operator_name, user_type, notification_type, creation_id, creation_datetime, update_id, update_datetime) 
values ('9e266a93-6095-4824-2ce4-cf5ded9c0a5d','事業者D','3','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant (operator_id, operator_name, user_type, notification_type, creation_id, creation_datetime, update_id, update_datetime) 
values ('7bb2aa54-e686-4b6c-9747-67a4bf0d2374','全権限ユーザ','1','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant (operator_id, operator_name, user_type, notification_type, creation_id, creation_datetime, update_id, update_datetime) 
values ('3ba42661-af06-4707-aa6f-eeb507a35097','航路運営事業者ユーザ','1','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant (operator_id, operator_name, user_type, notification_type, creation_id, creation_datetime, update_id, update_datetime) 
values ('7c53f2bc-d47e-4049-bdf8-38b1094b4161','運航事業者ユーザ','2','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );


insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('53e6b8c7-1cec-ad87-1ca9-70349aa0b1bc','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('60c895e5-321a-fe8a-af39-f005f3206efb','2',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('8a281868-faef-f554-3b75-c1fd73770f16','3',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('9e266a93-6095-4824-2ce4-cf5ded9c0a5d','3',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('9e266a93-6095-4824-2ce4-cf5ded9c0a5d','2',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('9e266a93-6095-4824-2ce4-cf5ded9c0a5d','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('7bb2aa54-e686-4b6c-9747-67a4bf0d2374','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('7bb2aa54-e686-4b6c-9747-67a4bf0d2374','2',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('7bb2aa54-e686-4b6c-9747-67a4bf0d2374','3',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('3ba42661-af06-4707-aa6f-eeb507a35097','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );
insert into   tenant_role (operator_id, role, creation_id, creation_datetime, update_id, update_datetime)
values ('7c53f2bc-d47e-4049-bdf8-38b1094b4161','2',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP );


insert into   notification_target_info (operator_id, notification_target, notification_type, creation_id, creation_datetime, update_id, update_datetime)
values ('53e6b8c7-1cec-ad87-1ca9-70349aa0b1bc', 'test.apbase@example.co.jp','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP);
insert into   notification_target_info (operator_id, notification_target, notification_type, creation_id, creation_datetime, update_id, update_datetime)
values ('60c895e5-321a-fe8a-af39-f005f3206efb', 'test2.apbase@example.co.jp','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP);
insert into   notification_target_info (operator_id, notification_target, notification_type, creation_id, creation_datetime, update_id, update_datetime)
values ('8a281868-faef-f554-3b75-c1fd73770f16', 'test3.apbase@example.co.jp','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP);
insert into   notification_target_info (operator_id, notification_target, notification_type, creation_id, creation_datetime, update_id, update_datetime)
values ('9e266a93-6095-4824-2ce4-cf5ded9c0a5d', 'test4.apbase@example.co.jp','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP);
insert into   notification_target_info (operator_id, notification_target, notification_type, creation_id, creation_datetime, update_id, update_datetime)
values ('7bb2aa54-e686-4b6c-9747-67a4bf0d2374', 'testaccount_ap119@example.com','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP);
insert into   notification_target_info (operator_id, notification_target, notification_type, creation_id, creation_datetime, update_id, update_datetime)
values ('3ba42661-af06-4707-aa6f-eeb507a35097', 'testaccount_ap120@example.com','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP);
insert into   notification_target_info (operator_id, notification_target, notification_type, creation_id, creation_datetime, update_id, update_datetime)
values ('7c53f2bc-d47e-4049-bdf8-38b1094b4161', 'testaccount_ap121@example.com','1',-1,CURRENT_TIMESTAMP, -1,CURRENT_TIMESTAMP);

/* 関係者周知 End */