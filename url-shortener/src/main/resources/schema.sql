CREATE TABLE url_table (
    code VARCHAR(10) NOT NULL,
    url VARCHAR(2048) NOT NULL,
    expires_at TIMESTAMP,
    PRIMARY KEY (code)
);