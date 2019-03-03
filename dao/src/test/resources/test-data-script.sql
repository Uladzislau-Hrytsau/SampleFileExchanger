-- insert into categories (id, category)
-- values (1, 'default');
--
-- insert into categories (id, category)
-- values (2, 'work');
--
-- insert into categories (id, category)
-- values (3, 'entertainment');
--
-- insert into users (user_id, user_name, user_password, user_gender, user_birth_date, user_information)
-- values (1, 'userLogin1', 'userPassword1', true, '1000-10-10', 'userInformation1');
--
-- insert into users (user_id, user_name, user_password)
-- values (2, 'userLogin2', 'userPassword2');
--
-- insert into files (id, user_id, url, description, date, category)
-- values (1, 1, 'url1', 'description1', '1111-11-11', 1);
--
-- insert into files (id, user_id, url, description, date, category)
-- values (2, 2, 'url2', 'description2', '2222-12-22', 2);
--
-- insert into role (id, role)
-- values (1, 'ROLE_USER');
--
-- insert into role (id, role)
-- values (2, 'ROLE_ADMIN');
--
-- insert into user_role (user_id, role_id, id)
-- values (1, 1, 1);
--
-- insert into user_role (user_id, role_id, id)
-- values (1, 2, 2);
--
-- insert into user_role (user_id, role_id, id)
-- values (2, 1, 3);

insert into user(id, user_name, user_password)
values (1, 'userLogin1', 'userPassword1');

insert into user(id, user_name, user_password)
values (2, 'userLogin2', 'userPassword2');

insert into category(id, category)
values (1, 'default');

insert into category(id, category)
values (2, 'work');

insert into category(id, category)
values (3, 'home');

insert into user_has_category(user_id, category_id)
values (1, 1);

insert into user_has_category(user_id, category_id)
values (2, 2);

insert into user_has_category(user_id, category_id)
values (1, 2);
