CREATE TABLE t_card_provider (
  id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255) NOT NULL,
   bank_account_id INT NOT NULL,
   CONSTRAINT pk_t_cardprovider PRIMARY KEY (id)
);

ALTER TABLE t_card_provider ADD CONSTRAINT uc_t_cardprovider_bankaccount UNIQUE (bank_account_id);

ALTER TABLE t_card_provider ADD CONSTRAINT FK_T_CARDPROVIDER_ON_BANKACCOUNT FOREIGN KEY (bank_account_id) REFERENCES t_account (id);
