-- role
insert into role (id, role)
values (1, 'ROLE_USER');
insert into role (id, role)
values (2, 'ROLE_ADMIN');
-- category
insert into category (id, category)
values (1, 'default');
insert into category (id, category)
values (2, 'work');
insert into category (id, category)
values (3, 'home');
-- user
insert into user (id, user_name, user_password, user_gender, user_birth_date, user_information)
values (1, 'admin', '$2a$10$I4B7WgbB4QwYDznQ8JKGQOnjv3AOFK2sOpFw3tLj0xOoxKRO1prUa', true, '1000-10-10', 'information1');
insert into user (id, user_name, user_password, user_gender, user_birth_date, user_information)
values (2, 'user', '$2a$10$I4B7WgbB4QwYDznQ8JKGQOnjv3AOFK2sOpFw3tLj0xOoxKRO1prUa', false, '1000-11-10', 'information2');
insert into user (id, user_name, user_password, user_gender, user_birth_date, user_information)
values (3, 'user3', '$2a$10$I4B7WgbB4QwYDznQ8JKGQOnjv3AOFK2sOpFw3tLj0xOoxKRO1prUa', true, '1001-10-10', 'information3');
insert into user (id, user_name, user_password, user_gender, user_birth_date, user_information)
values (3000, 'user3000', '$2a$10$I4B7WgbB4QwYDznQ8JKGQOnjv3AOFK2sOpFw3tLj0xOoxKRO1prUa', true, '1001-10-10', 'information3000');
insert into user (id, user_name, user_password, user_gender, user_birth_date, user_information)
values (4000, 'user4000', '$2a$10$I4B7WgbB4QwYDznQ8JKGQOnjv3AOFK2sOpFw3tLj0xOoxKRO1prUa', true, '1001-10-10', 'information4000');
-- user_role
insert into user_role (user_id, role_id)
values (1, 1);
insert into user_role (user_id, role_id)
values (1, 2);
insert into user_role (user_id, role_id)
values (2, 1);
insert into user_role (user_id, role_id)
values (3, 1);
-- user_has_category
insert into user_has_category (user_id, category_id)
values (1, 1);
insert into user_has_category (user_id, category_id)
values (1, 2);
insert into user_has_category (user_id, category_id)
values (1, 3);
insert into user_has_category (user_id, category_id)
values (2, 1);
insert into user_has_category (user_id, category_id)
values (2, 2);
insert into user_has_category (user_id, category_id)
values (3, 1);
-- folder
insert into folder (id, name, user_id, parent_id)
values (1, 'folder10', 1, null);
insert into folder (id, name, user_id, parent_id)
values (2, 'folder20', 1, null);
insert into folder (id, name, user_id, parent_id)
values (3, 'folder31', 1, 1);
insert into folder (id, name, user_id, parent_id)
values (4, 'folder43', 1, 3);
insert into folder (id, name, user_id, parent_id)
values (4000, 'folder43', 1, null);
insert into folder (id, name, user_id, parent_id)
values (5000, 'folder43', 2, null);
insert into folder (id, name, user_id, parent_id)
values (4001, 'folder43', 1, null);
-- file
insert into file (id, user_id, date, description, folder_id, real_name, encode_name)
values (1, 1, '1000-10-10', 'description1', 1, 'real_name1', 'encode_name1');
insert into file (id, user_id, date, description, folder_id, real_name, encode_name)
values (2, 1, '1000-11-10', 'description2', 1, 'real_name2', 'encode_name2');
insert into file (id, user_id, date, description, folder_id, real_name, encode_name)
values (3, 1, '1001-10-10', 'description3', 3, 'real_name3', 'encode_name3');
insert into file (id, user_id, date, description, folder_id, real_name, encode_name)
values (4, 2, '1010-10-10', 'description4', 3, 'real_name4', 'encode_name4');
insert into file (id, user_id, date, description, folder_id, real_name, encode_name)
values (5, 1, '1100-10-10', 'description5', 1, 'real_name5', 'encode_name5');
-- file_category
insert into file_category (category_id, file_id)
values (1, 1);
insert into file_category (category_id, file_id)
values (2, 1);
insert into file_category (category_id, file_id)
values (3, 1);
insert into file_category (category_id, file_id)
values (1, 2);
insert into file_category (category_id, file_id)
values (2, 2);
insert into file_category (category_id, file_id)
values (3, 3);
insert into file_category (category_id, file_id)
values (2, 4);
insert into file_category (category_id, file_id)
values (3, 4);

