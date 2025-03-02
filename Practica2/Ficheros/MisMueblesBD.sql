DROP DATABASE IF EXISTS MisMuebles;
CREATE DATABASE IF NOT EXISTS MisMuebles;
USE MisMuebles;

CREATE TABLE usuarios( 
usuario VARCHAR(25) NOT NULL PRIMARY KEY, 
contrasena VARCHAR(25) NOT NULL,  
perfil VARCHAR(8) NOT NULL CHECK (perfil= "ADMIN" OR perfil="VENTAS" OR perfil="INFORMES")
)ENGINE=innodb; 

CREATE TABLE clientes( 
id_cliente INT NOT NULL PRIMARY KEY, 
nombre VARCHAR(50) NOT NULL, 
direccion VARCHAR(50) NOT NULL, 
telefono CHAR(9) NOT NULL, 
ciudad VARCHAR(50) NOT NULL,
email VARCHAR(50) not null
)ENGINE=innodb; 

CREATE TABLE productos(
id_producto INT NOT NULL PRIMARY KEY, 
descripcion VARCHAR(200) NOT NULL, 
precio INT NOT NULL 
)ENGINE=innodb; 

CREATE TABLE ventas(
id_venta INT NOT NULL PRIMARY KEY, 
cantidad INT NOT NULL, 
id_cliente INT NOT NULL,
id_producto INT NOT NULL,
FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente)
ON DELETE RESTRICT
ON UPDATE CASCADE, 
FOREIGN KEY (id_producto) REFERENCES productos (id_producto)
ON DELETE RESTRICT
ON UPDATE CASCADE
)ENGINE=innodb;

INSERT INTO usuarios VALUES ('admin','admin','ADMIN');
INSERT INTO usuarios VALUES ('ventas','ventas','VENTAS');
INSERT INTO usuarios VALUES ('informes','informes','INFORMES');