SET SCHEMA 'api';
-- Enable the pgVector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- sentences_vector --
CREATE TABLE IF NOT EXISTS chunks (
    id SERIAL PRIMARY KEY NOT NULL,
    vector VECTOR(512) NOT NULL,  -- Adjust dimension as needed
    content TEXT NOT NULL
);