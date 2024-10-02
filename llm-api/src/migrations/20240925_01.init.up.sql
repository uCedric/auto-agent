-- Enable the pgVector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- sentences_vector --
CREATE TABLE IF NOT EXISTS sentences_vector (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    embedding VECTOR(512)  -- Adjust dimension as needed
);