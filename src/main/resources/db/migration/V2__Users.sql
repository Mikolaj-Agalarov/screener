CREATE TABLE USERS
(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE ,
    username CHARACTER VARYING (55) NOT NULL UNIQUE,
    password CHARACTER VARYING (255) NOT NULL,
    email CHARACTER VARYING (55) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT now(),
    role_id bigint references roles (id)
);

INSERT INTO USERS (username, password, email)
VALUES ('test', '$10$KKnXrslfhmepm.TML52b8Onvwe7QdylmhXmug.xF9hOIrPXlJyq22', 'test@test.test');