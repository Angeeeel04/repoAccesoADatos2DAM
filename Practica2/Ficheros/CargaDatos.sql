/*desactivamos temporalmente las validaciones de las foreign key.*/
SET FOREIGN_KEY_CHECKS=0;
USE MisMuebles;

/*insertamos clientes.*/
INSERT IGNORE INTO clientes VALUES(123,'Margot Robbie', 'Kra11#9-56', '674702291', 'Madrid','Margot@mail.com'); 
INSERT IGNORE INTO clientes VALUES(456,'Will Smith', 'Cll 21#95-52', '693281239', 'Barcelona','Will@mail.com'); 
INSERT IGNORE INTO clientes VALUES(789,'Drew Barrymore', 'Kra52#65-05', '600203918', 'Madrid','Drew@mail.com'); 
INSERT IGNORE INTO clientes VALUES(741,'Morgan Freeman', 'Cll 05#52-95', '701212921', 'Valencia','Morgan@mail.com');
INSERT IGNORE INTO clientes VALUES(147,'Jennifer Lawrence', 'Cll 52#65-56', '799229399', 'Barcelona','Jennifer@mail.com'); 
INSERT IGNORE INTO clientes VALUES(852, 'Scarlett Johansson', 'Kra 21#65-52', '666982295', 'Córdoba','Scarlett@mail.com'); 
INSERT IGNORE INTO clientes VALUES(258,'George Clooney', 'Cll 11#95-9', '658952294', 'Córdoba','Georget@mail.com'); 
INSERT IGNORE INTO clientes VALUES(963,'Charlize Theron', 'Cll 05#52-56', '777052957', 'Valencia','Charlize@mail.com'); 
INSERT IGNORE INTO clientes VALUES(369,'Tom Hanks', 'Kra 21#05-56', '776622966', 'Madrid','Tom@mail.com'); 
INSERT IGNORE INTO clientes VALUES(159,'Jennifer Lawrence', 'Kra05#65-05', '670229388','Barcelona','Jennifer@mail.com'); 
INSERT IGNORE INTO clientes VALUES(753,'Daniel Radcliffe', 'Cll 11#65-11', '669702299', 'Barcelona','Daniel@mail.com'); 
INSERT IGNORE INTO clientes VALUES(153,'Emma Watson', 'Kra 9#9-95', '631569638', 'Valencia','Emma@mail.com'); 

/*insertamos productos.*/
INSERT IGNORE INTO productos VALUES(1,'Cama Romelier',2400);
INSERT IGNORE INTO productos VALUES(2,'Mesa salón Zen',1000); 
INSERT IGNORE INTO productos VALUES(3,'Sofa grey design',3600); 
INSERT IGNORE INTO productos VALUES(4,'Mesita noche Romelier',500); 
INSERT IGNORE INTO productos VALUES(5,'Comoda Vest',1000); 
INSERT IGNORE INTO productos VALUES(6,'Salón conjunto prim',8000); 
INSERT IGNORE INTO productos VALUES(7,'Salón conjunto suite',4563); 
INSERT IGNORE INTO productos VALUES(8,'Estantería New York',1800); 
INSERT IGNORE INTO productos VALUES(9,'Porche Chill Out',7856); 
INSERT IGNORE INTO productos VALUES(10,'Mesa salón Sense',1800); 
INSERT IGNORE INTO productos VALUES(11,'Cocina conjunto VIP',12000); 
INSERT IGNORE INTO productos VALUES(12,'Habitación Conjunto child',7800); 
INSERT IGNORE INTO productos VALUES(13,'Recibidor Numic',1400); 
INSERT IGNORE INTO productos VALUES(14,'Fuente natura',1800); 
INSERT IGNORE INTO productos VALUES(15,'Mueble lavabo Canto',1200); 

/*insertamos ventas.*/
INSERT IGNORE INTO ventas VALUES(1,5,123,1); 
INSERT IGNORE INTO ventas VALUES(2,6,123,2); 
INSERT IGNORE INTO ventas VALUES(3,7,123,3); 
INSERT IGNORE INTO ventas VALUES(4,8,123,4); 
INSERT IGNORE INTO ventas VALUES(5,2,456,5); 
INSERT IGNORE INTO ventas VALUES(6,4,741,6); 
INSERT IGNORE INTO ventas VALUES(7,5,456,7); 
INSERT IGNORE INTO ventas VALUES(8,600,741,8); 
INSERT IGNORE INTO ventas VALUES(9,69,852,9); 
INSERT IGNORE INTO ventas VALUES(10,15,789,10); 
INSERT IGNORE INTO ventas VALUES(11,11,456,5); 
INSERT IGNORE INTO ventas VALUES(12,22,789,6); 
INSERT IGNORE INTO ventas VALUES(13,11,753,7); 
INSERT IGNORE INTO ventas VALUES(14,10,963,12); 
INSERT IGNORE INTO ventas VALUES(15,65,963,11); 
INSERT IGNORE INTO ventas VALUES(16,12,852,10); 
INSERT IGNORE INTO ventas VALUES(17,65,741,9); 
INSERT IGNORE INTO ventas VALUES(18,78,147,8); 
INSERT IGNORE INTO ventas VALUES(19,92,258,9); 
INSERT IGNORE INTO ventas VALUES(20,12,258,6); 
INSERT IGNORE INTO ventas VALUES(21,32,147,3); 
INSERT IGNORE INTO ventas VALUES(22,3,789,1); 
INSERT IGNORE INTO ventas VALUES(23,45,456,2); 
INSERT IGNORE INTO ventas VALUES(24,5,123,3); 
INSERT IGNORE INTO ventas VALUES(25,5,789,4); 
INSERT IGNORE INTO ventas VALUES(26,6,456,1); 
INSERT IGNORE INTO ventas VALUES(27,4,123,2); 
INSERT IGNORE INTO ventas VALUES(28,7,789,12); 
INSERT IGNORE INTO ventas VALUES(29,8,258,13); 
INSERT IGNORE INTO ventas VALUES(30,9,852,14); 
INSERT IGNORE INTO ventas VALUES(31,9,753,15); 
INSERT IGNORE INTO ventas VALUES(32,6,753,10); 
INSERT IGNORE INTO ventas VALUES(33,7,159,9); 
INSERT IGNORE INTO ventas VALUES(34,8,963,10); 
INSERT IGNORE INTO ventas VALUES(35,9,369,8); 
INSERT IGNORE INTO ventas VALUES(36,15,369,7); 
INSERT IGNORE INTO ventas VALUES(37,5,123,5); 
INSERT IGNORE INTO ventas VALUES(38,6,123,6); 
INSERT IGNORE INTO ventas VALUES(39,7,123,7); 
INSERT IGNORE INTO ventas VALUES(40,8,123,8); 
INSERT IGNORE INTO ventas VALUES(41,5,123,9); 
INSERT IGNORE INTO ventas VALUES(42,6,123,10); 
INSERT IGNORE INTO ventas VALUES(43,7,123,11);
INSERT IGNORE INTO ventas VALUES(44,8,123,12); 
INSERT IGNORE INTO ventas VALUES(45,5,123,13); 
INSERT IGNORE INTO ventas VALUES(46,6,123,14); 
INSERT IGNORE INTO ventas VALUES(47,7,123,15);
INSERT IGNORE INTO ventas VALUES(48,10,159,1);

/*Activamos las validaciones de las foreign key.*/
SET FOREIGN_KEY_CHECKS=1;

/*Confirmamos.*/
commit;

