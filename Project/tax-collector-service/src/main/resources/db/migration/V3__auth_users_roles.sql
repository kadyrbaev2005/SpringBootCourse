CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(128) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (name) VALUES ('COLLECTOR');
INSERT INTO roles (name) VALUES ('KING');

INSERT INTO users (username, password_hash) VALUES
    ('collector', '$2y$10$hwB/RPGcFl1DHijCrjkKuOL3Pr.JorpCeu0rYvVCCmEipDEWCBpxS'),
    ('king', '$2y$10$eBWLCAqh5nPvsrvxlqo2R.FAwBofiDnq.eZM9Q4QxSwtWOagjtecC');

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'collector' AND r.name = 'COLLECTOR';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'king' AND r.name = 'KING';
