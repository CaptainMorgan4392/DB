INSERT INTO building(id, name, capacity, buildingtype) VALUES (1, 'Kakoi-to teatr', 3228, 'theatre');
INSERT INTO building(id, name, capacity, buildingtype) VALUES (2, 'Kakoi-to kinoteatr', 1488, 'cinema');
INSERT INTO theatre(id, address) VALUES (1, 'Ulitsa Pushkina Dom Kolotushkina');
INSERT INTO cinema(id, diagonal, address) VALUES (2, 322, 'Ulitsa Esenina Dom Karuselina');

INSERT INTO event(name, eventtype, eventplace, eventdate) VALUES ('Concert of Pushkin', 1, 1, '2100-01-01');
INSERT INTO event(name, eventtype, eventplace, eventdate) VALUES ('Opera of Kolotushkin', 2, 1, '2100-01-01');
INSERT INTO event(name, eventtype, eventplace, eventdate) VALUES ('Musicle of Pushkin', 4, 2, '2100-01-01');
INSERT INTO event(name, eventtype, eventplace, eventdate) VALUES ('Triller of Kolotushkin', 4, 2, '2100-01-01');
