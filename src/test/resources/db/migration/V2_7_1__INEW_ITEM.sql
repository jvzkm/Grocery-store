INSERT INTO t_item
  (product_id, store_id, price, item_condition, prod_date, exp_date)
VALUES
  (1, 1, 11.99, 'NEW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '1 week');
