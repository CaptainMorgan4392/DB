INSERT INTO artist(id, name, surname, birthdate) VALUES (1, 'Nikita', 'Kosarev', '2000-10-30');
INSERT INTO artist(id, name, surname, birthdate) VALUES (2, 'Marat', 'Pashentsev', '2000-05-24');
INSERT INTO artist(id, name, surname, birthdate) VALUES (3, 'Andrey', 'Nikolotov', '2000-12-31');
INSERT INTO artist(id, name, surname, birthdate) VALUES (4, 'Vasya', 'Pupkin', '2000-01-01');

INSERT INTO impresario(id, name, surname, birthdate)  VALUES (1, 'Nikita', 'Kosarev', '2000-10-30');
INSERT INTO impresario(id, name, surname, birthdate)  VALUES (2, 'Marat', 'Pashentsev', '2000-05-24');
INSERT INTO impresario(id, name, surname, birthdate)  VALUES (3, 'Andrey', 'Nikolotov', '2000-12-31');
INSERT INTO impresario(id, name, surname, birthdate)  VALUES (4, 'Vasya', 'Pupkin', '2000-01-01');

INSERT INTO impresario_artist_jenre(impresario, artist, jenre) VALUES (1, 2, 8);
INSERT INTO impresario_artist_jenre(impresario, artist, jenre) VALUES (1, 3, 8);
INSERT INTO impresario_artist_jenre(impresario, artist, jenre) VALUES (1, 3, 9);
INSERT INTO impresario_artist_jenre(impresario, artist, jenre) VALUES (2, 3, 1);
INSERT INTO impresario_artist_jenre(impresario, artist, jenre) VALUES (2, 4, 2);