CREATE TABLE ROLES
(
    id bigserial not null unique primary key,
    role varchar not null unique
);

INSERT INTO ROLES (role) VALUES ('ROLE_ADMIN');
INSERT INTO ROLES (role) VALUES ('ROLE_USER');