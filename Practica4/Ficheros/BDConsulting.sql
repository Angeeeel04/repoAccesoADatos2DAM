DROP DATABASE BDrel_clases;
CREATE DATABASE BDrel_clases;
USE BDrel_clases;

CREATE TABLE Gimnasios( 
Cod_centro INT PRIMARY KEY, 
Nombre VARCHAR(20) NOT NULL, 
Responsable INT NOT NULL,
Direccion VARCHAR(25) NOT NULL,  
Localidad VARCHAR(20) NOT NULL,
Provincia varchar (20) NOT NULL
) engine=innodb;

CREATE TABLE Monitores( 
Cod_monitor INT PRIMARY KEY, 
Nombre VARCHAR(20) NOT NULL, 
Apellido VARCHAR(20) NOT NULL,
Fecha_nac DATE NOT NULL,  
Sexo CHAR(1) check (Sexo in('H','M')),
Cod_centro INT,
foreign key (Cod_centro) REFERENCES Gimnasios (Cod_centro)
on delete cascade
on update cascade
) engine=innodb;

CREATE TABLE Clases( 
Cod_clase INT PRIMARY KEY, 
Nombre VARCHAR(20) NOT NULL, 
Dificultad VARCHAR(7) check (Dificultad in('BAJA','MEDIA','ALTA','EXPERTO')),
Durac_min INT
) engine=innodb;

CREATE TABLE ClasMon( 
Cod_monitor INT, 
Cod_clase INT, 
PRIMARY KEY (Cod_monitor, Cod_clase),
foreign key (Cod_monitor) REFERENCES Monitores (Cod_monitor)
on delete cascade
on update cascade,
foreign key (Cod_clase) REFERENCES Monitores (Cod_monitor)
on delete cascade
on update cascade
) engine=innodb;

ALTER TABLE Gimnasios ADD foreign key (Responsable) REFERENCES Monitores (Cod_monitor)
on delete cascade
on update cascade;

SET FOREIGN_KEY_CHECKS=0;

INSERT INTO Gimnasios VALUES (1, 'VALBUENA', 4, 'C/ Tenerías, 5', 'Pinto', 'Madrid');
INSERT INTO Gimnasios VALUES (2, 'COPET', 1, 'C/ De Blas, 13', 'Madrid', 'Madrid');
INSERT INTO Gimnasios VALUES (3, 'MODELO', 2, 'C/ Pozuelo, 15', 'Aravaca', 'Madrid');
INSERT INTO Gimnasios VALUES (4, 'PARIS', 14, 'C/ Remedios, 51', 'Barcelona', 'Barcelona');
INSERT INTO Gimnasios VALUES (5, 'BERLIN', 13, 'C/ Asaltos, 5', 'Barcelona', 'Barcelona');
INSERT INTO Gimnasios VALUES (6, 'SPORTSEVILLA', 18, 'C/ Fandangos, s/n', 'Sevilla', 'Sevilla');
INSERT INTO Gimnasios VALUES (7, 'ACTIVATE', 7, 'C/ De Sellos, 88', 'Zaragoza', 'Zaragoza');
INSERT INTO Gimnasios VALUES (8, 'RITMOS', 9, 'C/ Servidores, 12', 'Boadilla del Monte', 'Madrid');
INSERT INTO Gimnasios VALUES (9, 'LIBERTY', 11, 'C/ La Torre, 5', 'Pozuelo', 'Madrid');
INSERT INTO Gimnasios VALUES (10, 'INTEMPO', 20, 'C/ Bali, 5', 'Benidorm', 'Alicante');

SET FOREIGN_KEY_CHECKS=1;

INSERT INTO Monitores VALUES (1, 'Alicia', 'Valle', '1995-11-03', 'M', 1);
INSERT INTO Monitores VALUES (2, 'Ramón', 'García', '2000-12-12', 'H', 2);
INSERT INTO Monitores VALUES (3, 'Alexa', 'Vitoria', '1999-01-03', 'M', 1);
INSERT INTO Monitores VALUES (4, 'Romina', 'Carballo', '2001-05-05', 'M', 4);
INSERT INTO Monitores VALUES (5, 'Roberto', 'Alcaide', '1995-04-11', 'H', 2);
INSERT INTO Monitores VALUES (6, 'Alicia', 'Sobre', '1996-07-06', 'M', 4);
INSERT INTO Monitores VALUES (7, 'Rosa', 'Casares', '1982-12-01', 'M', 1);
INSERT INTO Monitores VALUES (8, 'Alberto', 'Bodegas', '1995-03-03', 'H', 1);
INSERT INTO Monitores VALUES (9, 'Sergio', 'Casero', '2001-10-07', 'H', 7);
INSERT INTO Monitores VALUES (10, 'Virginia', 'Brasero', '1993-11-04', 'M', 2);
INSERT INTO Monitores VALUES (11, 'Gerónimo', 'Vázquez', '1987-06-12', 'H', 1);
INSERT INTO Monitores VALUES (12, 'Tomás', 'Tijeras', '1992-10-10', 'H', 1);
INSERT INTO Monitores VALUES (13, 'Cristina', 'Rebollo', '2003-02-03', 'M', 2);
INSERT INTO Monitores VALUES (14, 'Alberto', 'San José', '2000-04-09', 'H', 1);
INSERT INTO Monitores VALUES (15, 'Rafael', 'Santana', '1982-05-05', 'H', 3);
INSERT INTO Monitores VALUES (16, 'María', 'Sarriá', '1999-01-10', 'M', 1);
INSERT INTO Monitores VALUES (17, 'Daniel', 'Valle', '1999-10-01', 'H', 3);
INSERT INTO Monitores VALUES (18, 'Lucia', 'Miralles', '1998-07-08', 'M', 7);
INSERT INTO Monitores VALUES (19, 'Lucía', 'Pajares', '1988-11-03', 'M', 6);
INSERT INTO Monitores VALUES (20, 'Sara', 'Candelas', '1984-08-04', 'M', 6);
INSERT INTO Monitores VALUES (21, 'Adela', 'California', '1985-08-06', 'M', 3);
INSERT INTO Monitores VALUES (22, 'Laura', 'Tomillo', '2000-12-12', 'M', 7);
INSERT INTO Monitores VALUES (23, 'Tomás', 'Figura', '1999-01-06', 'H', 5);
INSERT INTO Monitores VALUES (24, 'Elsa', 'Torres', '1986-06-03', 'M', 1);
INSERT INTO Monitores VALUES (25, 'Catalina', 'Gaceta', '1998-11-07', 'M', 3);
INSERT INTO Monitores VALUES (26, 'Dario', 'Gracoa', '1996-10-03', 'H', 10);
INSERT INTO Monitores VALUES (27, 'Andrés', 'Bonilla', '1995-11-03', 'H', 10);
INSERT INTO Monitores VALUES (28, 'Miriam', 'Ventas', '1983-01-02', 'M', 9);
INSERT INTO Monitores VALUES (29, 'Manuel', 'Acosta', '1984-05-10', 'H', 8);
INSERT INTO Monitores VALUES (30, 'Marcos', 'Relojero', '1987-07-09', 'H', 8);
INSERT INTO Monitores VALUES (31, 'Cristina', 'Pardo', '1989-09-03', 'M', 7);
INSERT INTO Monitores VALUES (32, 'Cristina', 'Bernardo', '1995-12-12', 'M', 5);
INSERT INTO Monitores VALUES (33, 'Beatriz', 'Montes', '1995-09-01', 'M', 4);
INSERT INTO Monitores VALUES (34, 'Esther', 'Medina', '1988-04-08', 'M', 10);
INSERT INTO Monitores VALUES (35, 'Sabina', 'Collet', '2003-12-10', 'M', 6);
INSERT INTO Monitores VALUES (36, 'Patricia', 'Calcedo', '2001-09-08', 'M', 5);
INSERT INTO Monitores VALUES (37, 'Paula', 'Bonilla', '2002-08-02', 'M', 8);
INSERT INTO Monitores VALUES (38, 'Ruben', 'Catedral', '1986-02-10', 'H', 9);
INSERT INTO Monitores VALUES (39, 'Daniel', 'Olivo', '1999-02-08', 'H', 9);
INSERT INTO Monitores VALUES (40, 'Zaida', 'Basilio', '2000-01-09', 'M', 5);

INSERT INTO Clases VALUES (1, 'Zumba', 'BAJA', 45);
INSERT INTO Clases VALUES (2, 'Padel', 'MEDIA', 120);
INSERT INTO Clases VALUES (3, 'Judo', 'ALTA', 60);
INSERT INTO Clases VALUES (4, 'Karate', 'ALTA', 60);
INSERT INTO Clases VALUES (5, 'Balance', 'BAJA', 45);
INSERT INTO Clases VALUES (6, 'Cardio', 'BAJA', 45);
INSERT INTO Clases VALUES (7, 'Bike', 'EXPERTO', 50);
INSERT INTO Clases VALUES (8, 'Spinning', 'ALTA', 60);
INSERT INTO Clases VALUES (9, 'Pilates', 'BAJA', 45);
INSERT INTO Clases VALUES (10, 'Yoga', 'MEDIA', 60);
INSERT INTO Clases VALUES (11, 'Crossfit', 'ALTA', 45);
INSERT INTO Clases VALUES (12, 'Pump', 'EXPERTO', 45);
INSERT INTO Clases VALUES (13, 'Abd', 'ALTA', 30);
INSERT INTO Clases VALUES (14, 'Hit box', 'ALTA', 45);
INSERT INTO Clases VALUES (15, 'Kombat', 'EXPERTO', 60);
INSERT INTO Clases VALUES (16, 'Training', 'EXPERTO', 60);
INSERT INTO Clases VALUES (17, 'Musculación', 'BAJA', 45);
INSERT INTO Clases VALUES (18, 'Aerobic', 'MEDIA', 45);
INSERT INTO Clases VALUES (19, 'Acuarobic', 'BAJA', 60);
INSERT INTO Clases VALUES (20, 'Natación', 'ALTA', 120);

INSERT INTO ClasMon VALUES (1, 1);
INSERT INTO ClasMon VALUES (1, 10);
INSERT INTO ClasMon VALUES (1, 5);
INSERT INTO ClasMon VALUES (2, 11);
INSERT INTO ClasMon VALUES (2, 3);
INSERT INTO ClasMon VALUES (2, 14);
INSERT INTO ClasMon VALUES (20, 19);
INSERT INTO ClasMon VALUES (3, 1);
INSERT INTO ClasMon VALUES (3, 3);
INSERT INTO ClasMon VALUES (3, 20);
INSERT INTO ClasMon VALUES (3, 15);
INSERT INTO ClasMon VALUES (4, 12);
INSERT INTO ClasMon VALUES (4, 11);
INSERT INTO ClasMon VALUES (4, 9);
INSERT INTO ClasMon VALUES (4, 7);
INSERT INTO ClasMon VALUES (4, 18);
INSERT INTO ClasMon VALUES (5, 18);
INSERT INTO ClasMon VALUES (5, 4);
INSERT INTO ClasMon VALUES (5, 5);
INSERT INTO ClasMon VALUES (5, 6);
INSERT INTO ClasMon VALUES (5, 16);
INSERT INTO ClasMon VALUES (6, 20);
INSERT INTO ClasMon VALUES (6, 7);
INSERT INTO ClasMon VALUES (6, 6);
INSERT INTO ClasMon VALUES (2, 1);
INSERT INTO ClasMon VALUES (8, 15);
INSERT INTO ClasMon VALUES (23,17);
INSERT INTO ClasMon VALUES (36,8);
INSERT INTO ClasMon VALUES (31,8);
INSERT INTO ClasMon VALUES (39,8);
INSERT INTO ClasMon VALUES (27,7);
INSERT INTO ClasMon VALUES (37,12);
INSERT INTO ClasMon VALUES (18,17);
INSERT INTO ClasMon VALUES (13,2);
INSERT INTO ClasMon VALUES (36,10);
INSERT INTO ClasMon VALUES (2,13);
INSERT INTO ClasMon VALUES (38,7);
INSERT INTO ClasMon VALUES (17,5);
INSERT INTO ClasMon VALUES (2,7);
INSERT INTO ClasMon VALUES (29,20);
INSERT INTO ClasMon VALUES (10,14);
INSERT INTO ClasMon VALUES (15,2);
INSERT INTO ClasMon VALUES (33,11);
INSERT INTO ClasMon VALUES (28,1);
INSERT INTO ClasMon VALUES (9,3);
INSERT INTO ClasMon VALUES (35,14);
INSERT INTO ClasMon VALUES (2,19);
INSERT INTO ClasMon VALUES (24,3);
INSERT INTO ClasMon VALUES (35,19);
INSERT INTO ClasMon VALUES (16,1);
INSERT INTO ClasMon VALUES (21,19);
INSERT INTO ClasMon VALUES (19,13);
INSERT INTO ClasMon VALUES (28,10);
INSERT INTO ClasMon VALUES (17,15);
INSERT INTO ClasMon VALUES (9,11);
INSERT INTO ClasMon VALUES (19,16);
INSERT INTO ClasMon VALUES (11,10);
INSERT INTO ClasMon VALUES (17,13);
INSERT INTO ClasMon VALUES (40,18);
INSERT INTO ClasMon VALUES (34,20);
INSERT INTO ClasMon VALUES (25,12);
INSERT INTO ClasMon VALUES (20,13);
INSERT INTO ClasMon VALUES (38,2);
INSERT INTO ClasMon VALUES (14,7);
INSERT INTO ClasMon VALUES (31,2);
INSERT INTO ClasMon VALUES (15,9);
INSERT INTO ClasMon VALUES (11,9);
INSERT INTO ClasMon VALUES (22,6);
INSERT INTO ClasMon VALUES (11,7);
INSERT INTO ClasMon VALUES (28,19);
INSERT INTO ClasMon VALUES (23,5);
INSERT INTO ClasMon VALUES (25,13);
INSERT INTO ClasMon VALUES (4,19);
INSERT INTO ClasMon VALUES (10,18);
INSERT INTO ClasMon VALUES (9,14);

