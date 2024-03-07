INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME, EMAIL, BIRTH_DATE, ADDRESS, PHONE_NUMBER) VALUES (default, 'user1', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '유저1','nana@naver.com', NOW(), '경기 고양시','01012345678');
INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME, EMAIL, BIRTH_DATE, ADDRESS, PHONE_NUMBER) VALUES (default, 'user2', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '유저2','nana2@naver.com', NOW(), '서울 서초구','01022223333');

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

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/9_gs_img_dae.jpg','사파이어142','사파이어142',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어142','사파이어142',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/9_gs_img_dae.jpg','사파이어91','사파이어91',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어91','사파이어91',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/9_gs_img_dae.jpg','사파이어40C','사파이어40C',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어40C','사파이어40C',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/9_gs_img_dae.jpg','사파이어103','사파이어103',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,ORIGIN_FILE_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어103','사파이어103',now());

INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어14A',9000,1,1,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어142',10000,2,2,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어91',8000,3,3,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어40C',8000,4,4,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어103',12000,5,5,1,1,NOW(),NOW());

--itemId 1번의 옵션 5개
insert into option (id,item_id,color_name,stock) values (default,1,'A390',100);
insert into option (id,item_id,color_name,stock) values (default,1,'A027',200);
insert into option (id,item_id,color_name,stock) values (default,1,'A026',300);
insert into option (id,item_id,color_name,stock) values (default,1,'A028',400);
insert into option (id,item_id,color_name,stock) values (default,1,'F1104',500);
