INSERT INTO STUDENT (ID, NAME) VALUES (1, 'JOAO 1')
INSERT INTO STUDENT (ID, NAME) VALUES (2, 'JOAO 2')
INSERT INTO STUDENT (ID, NAME) VALUES (3, 'JOAO 3')
INSERT INTO STUDENT (ID, NAME) VALUES (4, 'JOAO 4')
INSERT INTO STUDENT (ID, NAME) VALUES (5, 'JOAO 5')
INSERT INTO STUDENT (ID, NAME) VALUES (6, 'JOAO 5')
INSERT INTO STUDENT (ID, NAME) VALUES (7, 'JOAO 5')
INSERT INTO STUDENT (ID, NAME) VALUES (8, 'JOAO 5')
INSERT INTO STUDENT (ID, NAME) VALUES (9, 'JOAO 5')
INSERT INTO STUDENT (ID, NAME) VALUES (10, 'JOAO 6')
INSERT INTO STUDENT (ID, NAME) VALUES (11, 'JOAO 7')
INSERT INTO STUDENT (ID, NAME) VALUES (12, 'JOAO 12')
INSERT INTO STUDENT (ID, NAME) VALUES (13, 'JOAO 13')
INSERT INTO STUDENT (ID, NAME) VALUES (14, 'JOAO 14')

INSERT INTO USER (ID, USERNAME, PASSWORD, NAME, ADMIN) VALUES (1, 'joao',  '$2a$10$TgaN0QNPJpFPCqC8pZxA9OilUtwzu1o.nu/a.FWCRd9kgx21aP7Wi', 'JOÃO', false)
INSERT INTO USER (ID, USERNAME, PASSWORD, NAME, ADMIN) VALUES (2, 'maria', '$2a$10$TgaN0QNPJpFPCqC8pZxA9OilUtwzu1o.nu/a.FWCRd9kgx21aP7Wi', 'MARIA', true)

INSERT INTO ROLE VALUES (1,  'ADMIN')
INSERT INTO ROLE VALUES (2, 'USER')

INSERT INTO USER_ROLE VALUES (1,2)
INSERT INTO USER_ROLE VALUES (2,1)
INSERT INTO USER_ROLE VALUES (2,2)