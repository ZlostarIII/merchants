DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS merchants;

CREATE TABLE IF NOT EXISTS merchants (
    id VARCHAR(40) NOT NULL,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(200) NOT NULL,
    email VARCHAR(40) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE'),
    total_transaction_sum INT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS transactions (
    id VARCHAR(40) NOT NULL,
    amount INT,
    status ENUM('APPROVED', 'REVERSED', 'REFUNDED', 'ERROR'),
    customer_email VARCHAR(40) NOT NULL,
    customer_phone VARCHAR(40),
    reference_id VARCHAR(40) NOT NULL,
    merchant_id VARCHAR(40) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (merchant_id)
        REFERENCES merchants (id)
        ON DELETE CASCADE
);