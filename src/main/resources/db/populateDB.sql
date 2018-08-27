DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq
  RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (id, user_id, datetime, description, calories)
VALUES (100011, 100000, '29/05/2015 09:00', 'Завтрак', 500),
       (100012, 100000, '29/05/2015 14:00', 'Обед', 1500),
       (100013, 100000, '29/05/2015 20:00', 'Ужин', 500),
       (100014, 100000, '30/05/2015 09:00', 'Завтрак', 500),
       (100015, 100000, '30/05/2015 14:00', 'Обед', 1500),
       (100016, 100000, '30/05/2015 20:00', 'Ужин', 500),
       (100017, 100000, '31/05/2015 09:00', 'Завтрак', 500),
       (100018, 100000, '31/05/2015 14:00', 'Обед', 1500),
       (100019, 100000, '31/05/2015 20:00', 'Ужин', 500),
       (100020, 100001, '29/05/2015 09:00', 'Завтрак', 500),
       (100021, 100001, '29/05/2015 14:00', 'Обед', 1500),
       (100022, 100001, '29/05/2015 20:00', 'Ужин', 500);
