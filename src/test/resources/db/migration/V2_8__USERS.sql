INSERT
INTO
  t_users
  (full_name, email, password, created_at, updated_at, role)
VALUES
  ('Jude', 'belingham@gmail.com', '$2a$10$Fc.OLKCdc.aAwJJqaPeXnuOvJQvCDSBd4EAzOC93dgGHWQFlDLAZa', NOW(), NOW(), 'USER');

INSERT
INTO
  t_users
  (full_name, email, password, created_at, updated_at, role)
VALUES
  ('luke Sky', 'skywalker@gmail.com', '$2a$10$tBy3q/E22nNayzhbwCNcouHIXiTP8TCPLMo00s/Zfl1msIRhR/tr.', NOW(), NOW(), 'WORKER');

INSERT
INTO
  t_users
  (full_name, email, password, created_at, updated_at, role)
VALUES
  ('PROVIDER', 'prov@mail.com', '$2a$10$/Z/WqePpXjj9o/lmL44wlODPnX0Nw3YELYcCrEHN3i2Ex3QgD58lq', NOW(), NOW(), 'PROVIDER');