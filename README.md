# repoAccesoADatos2DAM

En este repositorio encontramos las prácticas realizadas en el 2DAM

## PRÁCTICA 1 - UNIDAD MANEJO DE FICHEROS
La práctica consistió en leer un fichero .txt en donde se contenía toda la información de los empleados de una empresa, fichero del cual crearíamos un archivo .dat con los objetos de tipo EMPLEADO (Escritos en formato binario) y un archivo .xml que permita gestionar la información de los empleados menores de 25 y mayores de 55.

## PRÁCTICA 2 - UNIDAD MANEJO DE CONECTORES
La práctica consistió en crear una aplicación capaz de recibir un archivo .sql y poder realizar una carga de datos a la base de datos MISMUEBLESBD ([Script sql](Practica2/Ficheros/MisMueblesBD.sql)).

La aplicación además es capaz de:
### CONSULTAR INFORMACIÓN DE LA BASE DE DATOS
- Las tablas
- Las columnas
- Las claves primarias

### INSERCIÓN Y ELIMINACIÓN DE DATOS
- Carga masiva
- Insertar una nueva venta
- Modificación de precio

### CONSULTAR A LA BASE DE DATOS
- Seleccionar los datos del cliente por ciudad
- Seleccionar los datos de venta
- Seleccionar consultas genéricas

## PRÁCTICA 3 - UNIDAD HERRAMIENTAS DE MAPEO OBJETO RELACIONAL
La práctica consistió en realizar una aplicación implementando la herramienta HIBERNATE, la cual por medio de su configuración se conectaba con la base de datos VENTACOCHES ([Script sql](Practica3/Ficheros/VentasCochesBD.sql)).

La aplicación con ayuda de la implementación HIBERNATE es capaz de realizar:
### ALTAS
- Alta de venta
- Alta de cliente
- Alta de vehículo

### BAJAS
- Baja de venta
- Baja de cliente
- Baja de vehículo

### MODIFICACIONES
- Modificación de venta
- Modificación de cliente
- Modificación de vehículo

### INFORMES
- Informes de clientes filtrados por ciudad
- Informes de vehículos filtrados por número de plazas
- Informes de ventas por rango de fechas
- Consultas genéricas

## PRÁCTICA 4 - UNIDAD BASES DE DATOS ORIENTADAS A OBJETOS Y OBJETO RELACIONALES
La práctica consistió en crear una aplicación que pueda convertir nuestra base de datos relacional ([Script sql](Practica4/Ficheros/BDConsulting.sql)) a una base de datos orientada a objetos (NeoDatis).

Los datos fueron recogidos de la base de datos MYSQL por medio del JAR de MYSQL para que sean almacenados en el programa JAVA, una vez recogidos los datos realizaríamos la migración de datos a NeoDatis.

Además de realizar la migración, la aplicación debió de ser capaz de brindar la posibilidad de consultar a la base de datos en NeoDatis, lo cual conseguimos por medio del JAR de NeoDatis.