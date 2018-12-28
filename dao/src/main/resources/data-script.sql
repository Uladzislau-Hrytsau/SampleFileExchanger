INSERT INTO categories (id, category)
VALUES
(1, 'default'),
(2, 'entertainment'),
(3, 'work');

insert into users (user_id, user_name, user_password)
values (1, 'userLogin1', 'userPassword1');

insert into users (user_id, user_name, user_password)
values (2, 'userLogin2', 'userPassword2');

insert into files (id, user_id, url, description, date, category)
values (1, 1, 'url1', 'description1', '1111-11-11', 1);

insert into files (id, user_id, url, description, date, category)
values (2, 2, 'url2', 'description2', '2222-12-22', 2);

