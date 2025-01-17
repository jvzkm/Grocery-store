
CREATE TABLE t_account (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   pin VARCHAR(255) NOT NULL,
   amount DOUBLE PRECISION NOT NULL,
   status VARCHAR(255) NOT NULL,
   bank_id INTEGER NOT NULL,
   CONSTRAINT pk_t_account PRIMARY KEY (id)
);

ALTER TABLE t_account ADD CONSTRAINT FK_T_ACCOUNT_ON_BANK FOREIGN KEY (bank_id) REFERENCES t_bank (id);