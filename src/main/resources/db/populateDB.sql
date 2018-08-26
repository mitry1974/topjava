DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
INSERT INTO meals (user_id, created, calories, dscription) VALUES
  (100000, '29/05/2015 09:00', 500, 'Завтрак'),
  (100000, '29/05/2015 14:00', 1500, 'Обед'),
  (100000, '29/05/2015 20:00', 500, 'Ужин'),
  (100000, '30/05/2015 09:00', 500, 'Завтрак'),
  (100000, '30/05/2015 14:00', 800, 'Обед'),
  (100000, '30/05/2015 20:00', 500, 'Ужин'),
  (100000, '31/05/2015 09:00', 500, 'Завтрак'),
  (100000, '31/05/2015 14:00', 1800, 'Обед'),
  (100000, '31/05/2015 20:00', 500, 'Ужин');