DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, '2018-03-18 09:10:00', 'User завтрак', 500),
  (100000, '2018-03-18 12:30:00', 'User обед', 1000),
  (100000, '2018-03-18 19:00:00', 'User ужин', 700),
  (100001, '2018-03-17 08:55:00', 'Admin завтрак', 300),
  (100001, '2018-03-17 13:00:00', 'Admin обед', 1100),
  (100001, '2018-03-17 20:45:00', 'Admin ужин', 500);