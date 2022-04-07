-- ensure that the table with this name is removed before creating a new one.
DROP TABLE IF EXISTS user_group;
DROP TABLE IF EXISTS tg_user;

-- Create tg_user table
CREATE TABLE tg_user (
    chat_id BIGINT PRIMARY KEY,
    active BOOLEAN
);

-- Create tg_group table
CREATE TABLE user_group (
    id INT PRIMARY KEY AUTO_INCREMENT,
    chat_id BIGINT NOT NULL,
    title VARCHAR(100),
    users VARCHAR(100)
);
