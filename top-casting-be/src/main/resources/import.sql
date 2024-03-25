INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME,NAME, EMAIL, BIRTH_DATE, ADDRESS1,ADDRESS2,ZIPCODE, PHONE_NUMBER,ROLES) VALUES (default, 'user1', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '유저1','유저1 이름','nana@naver.com', NOW(), '서울시 낙원구 행복동','46번지','17171771','01012345678','ROLE_USER');
INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME,NAME, EMAIL, BIRTH_DATE, ADDRESS1,ADDRESS2,ZIPCODE, PHONE_NUMBER,ROLES) VALUES (default, 'user2', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '유저2','유저2 이름','nana2@naver.com', NOW(), '서울특별시 서초구 서초대로65길 13-10','서초래미안아파트 101호','06602','01022223333','ROLE_USER');
INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME,NAME, EMAIL, BIRTH_DATE, ADDRESS1,ADDRESS2,ZIPCODE, PHONE_NUMBER,ROLES) VALUES (default, 'admin', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '관리자','관리자 이름','admin@naver.com', NOW(), '관리동','관리사무소','06602','01011111111','ROLE_ADMIN, ROLE_USER');

INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '하드베이트');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '소프트베이트');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '메탈지그&스푼');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '스커트베이트');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '각종장비');

-- 하드베이트 하위
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '플로팅미노우');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '메탈지그');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '타이라바');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '에기');
-- 소프트베이트 하위
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 2, '새드');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 2, '테일');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 2, '호그');

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/9_gs_img_dae.jpg','사파이어14A','사파이어14A',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','d_사파이어14A','사파이어14A',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/10_gs_img_jung.jpg','사파이어142','사파이어142',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어142','사파이어142',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/15_gs_img_dae.jpg','사파이어91','사파이어91',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어91','사파이어91',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/19_gs_img_dae.jpg','사파이어40C','사파이어40C',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어40C','사파이어40C',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/2/135_gs_img_jung.jpg','사파이어103','사파이어103',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어103','사파이어103',now());

INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어14A',9000,1,1,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어142',10000,2,2,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어91',8000,3,3,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어40C',8000,4,4,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어103',12000,5,5,1,1,NOW(),NOW());

--itemId 1번의 옵션 5개
insert into options (id,item_id,color_name,stock) values (default,1,'A390',100);
insert into options (id,item_id,color_name,stock) values (default,1,'A027',200);
insert into options (id,item_id,color_name,stock) values (default,1,'A026',300);
insert into options (id,item_id,color_name,stock) values (default,1,'A028',400);
insert into options (id,item_id,color_name,stock) values (default,1,'F1104',500);
--itemId 2번의 옵션
insert into options (id,item_id,color_name,stock) values (default,2,'B390',100); --6
insert into options (id,item_id,color_name,stock) values (default,2,'B655',200);
--itemId 3번의 옵션
insert into options (id,item_id,color_name,stock) values (default,3,'91-001',100); --8
insert into options (id,item_id,color_name,stock) values (default,3,'91-002',200);
--itemId 4번의 옵션
insert into options (id,item_id,color_name,stock) values (default,4,'F1631',100); --10
insert into options (id,item_id,color_name,stock) values (default,4,'F1632',200);
--itemId 5번의 옵션
insert into options (id,item_id,color_name,stock) values (default,5,'103-001',100); --12
insert into options (id,item_id,color_name,stock) values (default,5,'103-002',200);
