CREATE SCHEMA IF NOT EXISTS api;
SET SCHEMA 'api';

CREATE TABLE IF NOT EXISTS  users (
    uuid UUID PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS prompts (
    uuid UUID PRIMARY KEY,
    user_uuid UUID NOT NULL CONSTRAINT fk_user_uuid REFERENCES users(uuid),
    content TEXT NOT NULL
);

INSERT INTO users (uuid, name, email, password) VALUES ('7c55f4b1-f69d-428f-9aa6-394b06851f45', 'index', 'example@gmail.com', 'password');