
CREATE TABLE t_bank (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_bank PRIMARY KEY (id)
);