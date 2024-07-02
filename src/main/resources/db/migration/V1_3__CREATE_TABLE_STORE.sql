CREATE TABLE t_store (
  id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   bank_account_id INT NOT NULL,
   CONSTRAINT pk_t_store PRIMARY KEY (id)
);

ALTER TABLE t_store ADD CONSTRAINT FK_T_STORE_ON_BANK_ACCOUNT FOREIGN KEY (bank_account_id) REFERENCES t_account (id);

