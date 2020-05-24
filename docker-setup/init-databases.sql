CREATE DATABASE IF NOT EXISTS admin_db DEFAULT CHARACTER SET = utf8mb4;
GRANT ALL PRIVILEGES ON admin_db.* TO 'vlad'@'%';

CREATE DATABASE IF NOT EXISTS merchant_db DEFAULT CHARACTER SET = utf8mb4;
GRANT ALL PRIVILEGES ON merchant_db.* TO 'vlad'@'%';

CREATE DATABASE IF NOT EXISTS merchant_service_db DEFAULT CHARACTER SET = utf8mb4;
GRANT ALL PRIVILEGES ON merchant_service_db.* TO 'vlad'@'%';

CREATE TABLE IF NOT EXISTS merchant_service_db.roles (
    id INT AUTO_INCREMENT,
    name ENUM('ROLE_MERCHANT', 'ROLE_ADMIN', 'ROLE_USER'),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS merchant_service_db.users (
    id INT AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    username VARCHAR(15) NOT NULL,
    email VARCHAR(40) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS merchant_service_db.user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE,
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE CASCADE
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_MERCHANT');