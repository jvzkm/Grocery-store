
CREATE TABLE t_provider (
  id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_provider PRIMARY KEY (id)
);