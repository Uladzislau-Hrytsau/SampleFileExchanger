insert into categories (id, category)
values (1, 'default');

insert into categories (id, category)
values (2, 'work');

insert into categories (id, category)
values (3, 'entertainment');

insert into users (user_id, user_name, user_password, user_gender, user_birth_date, user_information)
values (1, 'userLogin1', 'userPassword1', true, '1000-10-10', 'userInformation1');

insert into users (user_id, user_name, user_password, user_gender, user_birth_date, user_information)
values (2, 'userLogin2', 'userPassword2', false, '1000-10-10', 'userInformation2');

insert into files (id, user_id, url, description, date, category)
values (1, 1, 'url1', 'description1', '1111-11-11', 1);

insert into files (id, user_id, url, description, date, category)
values (2, 2, 'url2', 'description2', '2222-12-22', 2);

