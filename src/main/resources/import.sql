INSERT INTO user (id, created_at, created_by, updated_at, updated_by, age, email, gender, name, password, status) values (1, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', 30, 'choising@gmail.com', 'MALE', 'seungmin', '12345678', 'ACTIVE');
INSERT INTO user (id, created_at, created_by, updated_at, updated_by, age, email, gender, name, password, status) values (2, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', 30, 'dom@gmail.com', 'MALE', 'sehun', '12345678', 'ACTIVE');

INSERT INTO taste (id, created_at, created_by, updated_at, updated_by, name, priority, status) values (1, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', 'sauce', 1, 'ACTIVE');
INSERT INTO taste (id, created_at, created_by, updated_at, updated_by, name, priority, status) values (2, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', 'coke', 2, 'ACTIVE');

INSERT INTO taste_option (id, created_at, created_by, updated_at, updated_by, active_yn, name, taste_id) values (1, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', true, 'dip', 1);
INSERT INTO taste_option (id, created_at, created_by, updated_at, updated_by, active_yn, name, taste_id) values (2, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', true, 'pour', 1);
INSERT INTO taste_option (id, created_at, created_by, updated_at, updated_by, active_yn, name, taste_id) values (3, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', true, 'coca', 2);
INSERT INTO taste_option (id, created_at, created_by, updated_at, updated_by, active_yn, name, taste_id) values (4, CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', true, 'pepsi', 2);
