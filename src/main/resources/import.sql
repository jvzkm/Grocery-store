delete from sale_items;
delete from t_sale;
delete from t_cash_contract;
delete from t_card_contract;
delete from t_provider;
delete from t_card_provider;
delete from t_discount;
delete from t_item;
delete from t_card_transaction;
delete from t_cash_transaction;
delete from t_worker;
delete from t_store;
delete from t_account;
delete from t_job;
delete from t_bank;

-- Insert data into t_bank table
INSERT INTO t_bank
VALUES (1,'Example Bank');

-- Insert data into t_job table
INSERT INTO t_job
VALUES (1,10.5,'Manager');

-- Insert data into t_bank_account table
INSERT INTO t_account
VALUES (1,1, '100000.00','1234', 'ACTIVE');

-- Insert data into t_store table
INSERT INTO t_store
VALUES (1, 1);

-- Insert data into t_worker table
INSERT INTO t_worker
VALUES (1,1, 50000.00, 1, 'John', 'Doe');
