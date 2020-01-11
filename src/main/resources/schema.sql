DROP TABLE IF EXISTS account;

CREATE TABLE account (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  balance VARCHAR(20) NOT NULL,
  currency VARCHAR(20) NOT NULL,
);

-- CREATE INDEX jog_user_id on jog(user_id);
