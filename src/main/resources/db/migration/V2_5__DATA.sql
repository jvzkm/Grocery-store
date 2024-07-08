INSERT INTO t_bank (name)
VALUES ('Bank A'),
       ('Bank B'),
       ('Bank C');

INSERT INTO t_job (name,percentage)
VALUES ('Manager',0.1),
       ('Cashier',0.5),
       ('Trial',0.3);

INSERT INTO t_account (pin,amount,status,bank_id)
VALUES ('1234','100000.00','ACTIVE',1),
       ('2341', '5555.55', 'ACTIVE',2),
       ('4442', '100.32', 'ACTIVE',1),
       ('6632', '4521.33', 'INACTIVE',3),
       ('1234', '0.55', 'ACTIVE',1);


INSERT INTO t_store (bank_account_id)
VALUES (1);

INSERT INTO t_worker (name,surname,salary,job_id,store_id)
VALUES ('John', 'Doe',0, 1, 1),
       ('Luke', 'Skywalker',0, 2, 1),
       ('Trevor', 'Tree', 0, 2, 1);

INSERT INTO t_product  (category,name,price)
VALUES ('DAIRY','Chefir',10.5),
       ('DAIRY','Chefir',5.99),
       ('DAIRY','Chefir',20.15),
       ('SEAFOOD','Oyster',100);

INSERT INTO t_provider (name)
VALUES ('Mr. A'),
       ('Mr. B'),
       ('Mr. C'),
       ('Mrs. D');

INSERT INTO t_card_provider (name,bank_account_id)
VALUES ('INC A',2),
       ('INC B',3),
       ('INC C',4),
       ('INC D',5);
