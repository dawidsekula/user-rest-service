INSERT INTO roles(name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users(email, encrypted_password, username, is_email_verified)
VALUES ('email@email', '$2a$10$8aGmM10jR.l3QDEdDtnMwOAWLZ5yo6MwBOJtu4wlkWHinsPOqktV6', 'user', true),
       ('email@admin', '$2a$10$9wDRfubQ5YvyoHiidOvm3euD/JEiplgGwOxmS8QiHzIz/sSYfXDE.', 'admin', true);

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);




