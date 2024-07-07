INSERT
INTO
  t_users
  (full_name, email, password, created_at, updated_at, role)
VALUES
  ('admin', 'admin@store.com', 'admin', NOW(), NOW(), 'ADMIN');