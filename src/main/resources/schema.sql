DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS transaction;

CREATE TABLE account (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  balance VARCHAR(20) NOT NULL,
  currency VARCHAR(20) NOT NULL
);

CREATE TABLE transaction (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  from_account_id BIGINT NOT NULL,
  to_account_id BIGINT NOT NULL,
  amount VARCHAR(20) NOT NULL,
  currency VARCHAR(20) NOT NULL,
  row_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX transaction_from_account_id on transaction(from_account_id);
CREATE INDEX transaction_to_account_id on transaction(to_account_id);
