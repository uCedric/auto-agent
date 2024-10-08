SET SCHEMA 'api';
-- Enable the pgVector extension --
CREATE EXTENSION IF NOT EXISTS vector;

-- documents --
CREATE TABLE IF NOT EXISTS documents (
    uuid UUID PRIMARY KEY NOT NULL,
    user_uuid UUID NOT NULL CONSTRAINT fk_user_uuid REFERENCES users(uuid),
    s3_path VARCHAR(100) NOT NULL
);

-- sentences_vector --
CREATE TABLE IF NOT EXISTS chunks (
    id SERIAL PRIMARY KEY NOT NULL,
    document_uuid UUID NOT NULL CONSTRAINT fk_document_uuid REFERENCES documents(uuid),
    vector VECTOR(768) NOT NULL,  -- Adjust dimension as needed
    content TEXT NOT NULL
);