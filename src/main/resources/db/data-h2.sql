INSERT INTO user_authority
VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_authority
VALUES (2, 'ROLE_PUBLIC');

INSERT INTO user_authority_info
VALUES (1, 1, 1);
INSERT INTO user_authority_info
VALUES (2, 1, 2);
INSERT INTO user_authority_info
VALUES (3, 2, 2);
INSERT INTO user_authority_info
VALUES (4, 3, 2);

INSERT INTO user_info
VALUES (1, 'admin', '$2a$10$oXaWH.m5cKYjhNOjtkiO.ube2AV2SvV4dNLeD3TCFVSQ30/T1rQV2', true, CURRENT_TIMESTAMP);
INSERT INTO user_info
VALUES (2, 'user', '$2a$10$QRW5XwMpcpBgvipaqGGUwevfVumFphIOfrGPL3u9ZnHl5VKzEKiCa', true, CURRENT_TIMESTAMP);
INSERT INTO user_info
VALUES (3, 'disabled', '$2a$10$3SF6sVJARU5tAdwbHBMWnOg4GwsArp5vHNY4v5NYa7WqZ7PuCW2yu', false, CURRENT_TIMESTAMP);
