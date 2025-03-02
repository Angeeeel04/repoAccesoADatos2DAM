create database if not exists ventaCoches;

USE ventaCoches;

CREATE TABLE CLIENTE( 
	id_cliente int PRIMARY KEY, 
	dni char (9) UNIQUE NOT NULL,
	nombre VARCHAR(50) NOT NULL, 
	direccion VARCHAR(50) NOT NULL,
	ciudad VARCHAR(50) NOT NULL,  
	telefono VARCHAR(50) NOT NULL,
	email varchar (50) NOT NULL,
	edad tinyint check (edad>=18),
	sexo varchar(6) check (sexo in('Hombre','Mujer'))
)engine=innodb;

CREATE TABLE COCHES( 
	id_coche INT PRIMARY KEY,
	Marca varchar (30) not null,
	Modelo varchar (30) not null,
	Color varchar (30) not null,
	Matricula varchar (7) unique not null,
	plazas tinyint not null,
	Extras VARCHAR(200)
)engine=innodb; 

CREATE TABLE VENTA( 
	id_venta int PRIMARY KEY, 
	Fecha_compra date not NULL,
	Fecha_entrega date, 
	Precio int not null,
	cliente int not null,
	coche int not null,
	foreign key (cliente) REFERENCES CLIENTE (id_cliente)
		on delete cascade
		on update cascade, 
	foreign key (coche) REFERENCES COCHES (id_coche)
		on delete cascade
		on update cascade
)engine=innodb;

INSERT INTO CLIENTE
VALUES (1,'11111111A','Pedro De la Rosa', 'Calle Federico García Lorca 56', 'Madrid', 665121232, 'Pedro@mail.es',18,'Hombre'); 
INSERT INTO CLIENTE
VALUES (2,'11111111B','Juan De la Cierva', 'Calle Atalaya 526', 'Valencia', 665129232, 'Juan@mail.es',20,'Hombre'); 
INSERT INTO CLIENTE
VALUES (3,'11111111C','María García', 'Calle la fuente 3', 'Barcelona', 645129233, 'María@mail.es',45,'Mujer');
INSERT INTO CLIENTE
VALUES (4,'11111111D','Federico de Tomás', 'Calle Besubio 13', 'Madrid', 665322112, 'Federico@mail.es',60,'Hombre');
INSERT INTO CLIENTE
VALUES (5,'11111111E','Lucía Tenerías', 'Calle de la luna', 'Madrid', 660099231, 'Lucía@mail.es',18,'Mujer');
INSERT INTO CLIENTE
VALUES (6,'11111111F','Sergio Rodriguez', 'Calle evangelista 1', 'Badajoz', 610404569, 'Sergio@mail.es',19,'Hombre');
INSERT INTO CLIENTE
VALUES (7,'11111111G','Sandra Santo', 'Calle Camerún 4', 'Barcelona', 665932477, 'Sandra@mail.es',32,'Mujer');
INSERT INTO CLIENTE
VALUES (8,'11111111H','Carlos Sevilla', 'Calle Otero 6', 'Sevilla', 665129232, 'Carlos@mail.es',34,'Hombre');
INSERT INTO CLIENTE
VALUES (9,'11111111I','Miguel Monte', 'Calle pez 52', 'Madrid', 665888282, 'Miguel@mail.es',43,'Hombre');
INSERT INTO CLIENTE
VALUES (10,'11111111J','Elisa Botero', 'Calle retiro 9', 'Barcelona', 665129111, 'Elisa@mail.es',21,'Mujer');
INSERT INTO CLIENTE
VALUES (11,'11111111K','Fernando Castro', 'Calle Ventura 109', 'Madrid', 665444232, 'Fernando@mail.es',55,'Hombre'); 
INSERT INTO CLIENTE
VALUES (12,'11111111L','Elisabet Toro', 'Calle Cuba 1', 'Valencia', 615100232, 'Elisabe@mail.es',27,'Mujer'); 
INSERT INTO CLIENTE
VALUES (13,'11111111M','Luis García', 'Calle Villa 3', 'Barcelona', 645104449, 'Luis@mail.es',22,'Hombre');
INSERT INTO CLIENTE
VALUES (14,'11111111N','Tomás Carrión', 'Calle Oceanos 10', 'Madrid', 665322311, 'Tomás@mail.es',19,'Hombre');
INSERT INTO CLIENTE
VALUES (15,'11111111O','Vanesa Rojillo', 'Calle del deseo', 'Córdoba', 660002331, 'Vanesa@mail.es',34,'Mujer');
INSERT INTO CLIENTE
VALUES (16,'11111111P','David Rodriguez', 'Calle mulo 10', 'Badajoz', 610404590, 'David@mail.es',41,'Hombre');
INSERT INTO CLIENTE
VALUES (17,'11111111Q','Santiago Activo', 'Calle de la historia 4', 'Barcelona', 665312303, 'Santiago@mail.es',46,'Hombre');
INSERT INTO CLIENTE
VALUES (18,'11111111R','Laura Sevilla', 'Calle Lucio 9', 'Ourense', 600123232, 'Laura@mail.es',34,'Mujer');
INSERT INTO CLIENTE
VALUES (19,'11111111S','Irene de la Torre', 'Calle aliados 52', 'Toledo', 668664366, 'Irene@mail.es',21,'Mujer');
INSERT INTO CLIENTE
VALUES (20,'11111111T','Esther Boadilla', 'Calle los lagos 9', 'Murcia', 665129000, 'Esther@mail.es',27,'Mujer');

INSERT INTO COCHES
VALUES (1,'Renault','Megane', 'Rojo', '1254ABF', 5, 'Techo solar, paquete sport, Climatizador');
INSERT INTO COCHES
VALUES (2,'Mercedes','SLK', 'Negro', '1009JTF', 2, 'Paquete Confort, Climatizador');
INSERT INTO COCHES
VALUES (3,'BMW','320', 'Blanco', '4354ATO', 5, 'Serie M, Climatizador');
INSERT INTO COCHES
VALUES (4,'Renault','Space', 'Gris', '1288ABS', 7, 'Paquete Family, Silla de bebe, Climatizador');
INSERT INTO COCHES
VALUES (5,'Volvo','C60', 'Blanco', '1209BBF', 7, 'Transporte mascotas, Aire acondicionado, paquete sport');
INSERT INTO COCHES
VALUES (6,'Volskwagen','California', 'Rojo', '1200BMF', 7, 'Techo solar, paquete travel, Soporte Bicicletas');
INSERT INTO COCHES
VALUES (7,'Kia','Niro', 'Blanco', '5554AZZ', 5, 'Techo solar, paquete sport, Climatizador');
INSERT INTO COCHES
VALUES (8,'Tesla','Model S', 'Rojo', '1202KHG', 5, 'Techo solar, paquete sport, electrico, climatizador');
INSERT INTO COCHES
VALUES (9,'Tesla','Model 3', 'Blanco', '1200JGF', 5, 'Techo solar, paquete sport, electrico, climatizador');
INSERT INTO COCHES
VALUES (10,'Nissan','Micra', 'Gris', '9988CBF', 5, 'Techo solar, paquete sport, Aire Acondicionado');

INSERT INTO VENTA
VALUES (1,'2021-01-12','2021-01-15', 390, 1, 5);
INSERT INTO VENTA
VALUES (2,'2021-02-03','2021-02-10', 786, 5, 4);
INSERT INTO VENTA
VALUES (3,'2021-01-11','2021-01-15', 450, 3, 10);
INSERT INTO VENTA
VALUES (4,'2021-02-11','2021-02-28', 2450, 19, 2);
INSERT INTO VENTA
VALUES (5,'2021-03-1','2021-03-15', 1270, 10, 9);
INSERT INTO VENTA
VALUES (6,'2021-01-20','2021-01-25', 640, 4, 3);
INSERT INTO VENTA
VALUES (7,'2021-03-12','2021-03-18', 760, 8, 9);
INSERT INTO VENTA
VALUES (8,'2021-03-21','2021-03-22', 90, 7, 1);
INSERT INTO VENTA
VALUES (9,'2021-03-12','2021-01-15', 390, 12, 5);
INSERT INTO VENTA
VALUES (10,'2021-02-7','2021-01-14', 600, 1, 9);
INSERT INTO VENTA
VALUES (11,'2021-04-12','2021-04-15', 390, 1, 5);
INSERT INTO VENTA
VALUES (12,'2021-05-03','2021-05-10', 786, 5, 4);
INSERT INTO VENTA
VALUES (13,'2021-04-11','2021-04-15', 450, 3, 10);
INSERT INTO VENTA
VALUES (14,'2021-05-11','2021-05-28', 2450, 19, 2);
INSERT INTO VENTA
VALUES (15,'2021-06-1','2021-06-15', 1270, 10, 9);
INSERT INTO VENTA
VALUES (16,'2021-04-20','2021-04-25', 640, 4, 3);
INSERT INTO VENTA
VALUES (17,'2021-06-12','2021-06-18', 760, 8, 9);
INSERT INTO VENTA
VALUES (18,'2021-06-21','2021-06-22', 90, 7, 1);
INSERT INTO VENTA
VALUES (19,'2021-04-12','2021-04-15', 390, 12, 8);
INSERT INTO VENTA
VALUES (20,'2021-05-7','2021-05-14', 600, 1, 9);
