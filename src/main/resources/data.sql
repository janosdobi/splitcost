INSERT INTO role (role_id, role) values (1, 'ADMIN');
INSERT INTO role (role_id, role) VALUES (2, 'DEFAULT');

INSERT INTO user (user_id, email, first_name, last_name, password, active)
  VALUES (1000, 'test1@test.com', 'Feri', 'Segg', '$2a$10$eL.Dk8cxkzqNUAdPWj0GPe3ETbd8kgeMDGbOlfQ8ZkNxn9TeFdYVS', true);
  
INSERT INTO user (user_id, email, first_name, last_name, password, active)
  VALUES (2000, 'test2@test.com', 'Tibi', 'Picsa', '$2a$10$eL.Dk8cxkzqNUAdPWj0GPe3ETbd8kgeMDGbOlfQ8ZkNxn9TeFdYVS', true);
  
INSERT INTO user (user_id, email, first_name, last_name, password, active)
  VALUES (3000, 'test3@test.com', 'Dani', 'Fasz', '$2a$10$eL.Dk8cxkzqNUAdPWj0GPe3ETbd8kgeMDGbOlfQ8ZkNxn9TeFdYVS', true);
  
INSERT INTO user_role (user_id, role_id) VALUES (1000, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2000, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3000, 1);