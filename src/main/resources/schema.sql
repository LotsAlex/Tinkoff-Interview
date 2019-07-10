CREATE TABLE invoice (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  invoice_time TIMESTAMP NOT NULL,
  input_params ARRAY NOT NULL,
  client_ip VARCHAR(20) NOT NULL
);