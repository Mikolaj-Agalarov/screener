-- Create a schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS my_schema;

-- Set the search path to the new schema
SET search_path TO my_schema;

-- Create a simple "user" table
CREATE TABLE IF NOT EXISTS "user" (
                                      id SERIAL PRIMARY KEY,
                                      username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
    );

-- Create an index on the username column for faster lookups
CREATE INDEX IF NOT EXISTS idx_user_username ON "user"(username);

-- Insert some sample data (optional)
INSERT INTO "user" (username, password, email)
VALUES
    ('john_doe', 'password123', 'john@example.com'),
    ('jane_smith', 'secret456', 'jane@example.com');

-- Commit the changes
COMMIT;
