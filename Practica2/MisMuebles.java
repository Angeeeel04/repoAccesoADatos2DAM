
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MisMuebles {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/mismuebles", "root", "root");
			iniciarPrograma(conexion);
			System.out.println("\n++++ El programa ha terminado ++++");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void iniciarPrograma(Connection conexion) {
		Scanner sc = new Scanner(System.in);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+++++++++++++++ MIS MUEBLES +++++++++++++++");
		System.out.println("+++++++++++++++ Bienvenido/a ++++++++++++++");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		int intentos = 0;
		String user, passwd;
		boolean salir = false;

		do {
			System.out.println("");
			System.out.print("Introduce usuario: ");
			user = sc.next();

			System.out.print("Introduce contraseña: ");
			passwd = sc.next();
			
			if (verificaUsuarioContr(user, passwd, conexion)) {

				switch (user) {
				case "admin":
					operacionesAdmin(sc, conexion);
					break;

				case "informes":
					operacionesInformes(sc, conexion);
					break;

				case "ventas":
					operacionesVentas(sc, conexion);
					break;
				}

				salir = true;
			} else {
				System.out.println("Usuario y contraseña incorrectos. Intentelo de nuevo.");
				intentos++;
			}

			if (intentos == 3) {
				System.out.println("\nMuchos intentos fallidos. Vuelve a intentarlo más tarde");
				salir = true;
			}

		} while (!salir);

	}
	
	//METODOS PARA VERIFICAR USUARIO Y CONTRASEÑA
	
	private static boolean verificaUsuarioContr(String user, String passwd, Connection conexion) {
		boolean verificado = false;
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM usuarios");

			while (resul.next()) {
				String userBd = resul.getString(1);
				String passwdBd = resul.getString(2);

				if (user.equals(userBd) && passwd.equals(passwdBd)) {
					verificado = true;
					System.out.println("Usuario verificado\n\n");
					System.out.println("+++++++++++++++ PERFIL " + resul.getString(3) + " +++++++++++++++");
				}
			}

		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
		}

		return verificado;
	}
	
	//METODOS PARA CADA PERFIL
	
	private static void operacionesAdmin(Scanner sc, Connection conexion) {
		boolean salir = false;
		
		do {
			System.out.print("\n1. Información sobre la base de datos\n");
			System.out.println("2. Inserción y eliminación de datos");
			System.out.println("3. Consultas");
			System.out.println("");
			System.out.println("(-1) Para cerrar sesión y terminar el programa\n");
			System.out.print("Elige la operacion: ");
			int op = sc.nextInt();

			switch (op) {
			case 1:
				informacionBbdd(sc, conexion);
				break;

			case 2:
				inserEliDatos(sc, conexion);
				break;

			case 3:
				consultas(sc, conexion);
				break;
				
			case -1:
				salir = true;
				break;
			}
		}while(!salir);
	}
	
	private static void operacionesInformes(Scanner sc, Connection conexion) {
		boolean salir = false;
		
		do {
			System.out.println("\n1. Inserción y eliminación de datos");
			System.out.println("2. Consultas");
			System.out.println("");
			System.out.println("(-1) Para cerrar sesión y terminar el programa\n");
			System.out.print("Elige la operacion: ");
			int op = sc.nextInt();

			switch (op) {
			case 1:
				inserEliDatos(sc, conexion);
				break;

			case 2:
				consultas(sc, conexion);
				break;
				
			case -1:
				salir = true;
				break;
			}
		}while(!salir);
	}

	private static void operacionesVentas(Scanner sc, Connection conexion) {
		boolean salir = false;
		do {
			System.out.println("1. Consultas");
			System.out.println("");
			System.out.println("(-1) Para cerrar sesión y terminar el programa\n");
			System.out.print("Elige la operacion: ");
			int op = sc.nextInt();

			switch (op) {
			case 1:
				consultas(sc, conexion);
				break;

			case -1:
				salir = true;
				break;
			}
		}while(!salir);
	}
	

	//INFORMACION SOBRE LA BASE DE DATOS
	
	private static void informacionBbdd(Scanner sc, Connection conexion) {
		System.out.println("\n1. Consultar las tablas de la base de datos");
		System.out.println("2. Consultar la información sobre las columnas");
		System.out.println("3. Consultar las claves primarias");
		System.out.println("");
		System.out.print("Elige la operacion: ");
		int op = sc.nextInt();

		switch (op) {
		case 1:
			consultarTablas(sc, conexion);
			break;

		case 2:
			informacionColumnas(sc, conexion);
			break;

		case 3:
			consultarPk(sc, conexion);
			break;
		}
	}
	
	private static void consultarTablas(Scanner sc, Connection conexion) {
		System.out.println("\n+++++++CONSULTAR TABLAS+++++++");
		System.out.print("\nIngrese el nombre del catálogo: ");
		String catalogo = sc.next();
		System.out.print("\nIngrese el nombre del esquema: ");
		String esquema = sc.next();
		System.out.print("\nIngrese el patrón de búsqueda: ");
		String patron = sc.next();
		System.out.print("\nIngrese el tipo (TABLE, VIEW...): ");
		String tipo = sc.next().toUpperCase();

		if (catalogo.isEmpty())
			catalogo = null;
		else if (esquema.isEmpty())
			esquema = null;
		else if (patron.isEmpty())
			patron = null;
		else if (tipo.isEmpty())
			tipo = null;

		try {
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet resul = dbmd.getTables(catalogo, esquema, patron + "%", new String[] { tipo });

			System.out.println("\nInformación tablas:");

			while (resul.next()) {
				System.out.println("\tNombre> " + resul.getString(3));
				System.out.println("\tTipo> " + resul.getString(4));
			}

		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
		}
	}
	
	private static void informacionColumnas(Scanner sc, Connection conexion) {
		System.out.println("\n+++++++INFORMACION COLUMNAS+++++++");
		System.out.print("\nIngrese el nombre de la tabla: ");
		String tabla = sc.next();

		try {
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet resul = dbmd.getColumns("mismuebles", "mismuebles", tabla, null);

			System.out.println("\nInformación Columnas:");

			while (resul.next()) {
				System.out.println("");
				System.out.println("Nombre Columna> " + resul.getString(4));
				System.out.println("Tipo Columna> " + resul.getString(6));
				System.out.println("Null> " + resul.getString(18));
				System.out.println("Tamaño Columna> " + resul.getString(7));
			}
		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
		}
	}

	private static void consultarPk(Scanner sc, Connection conexion) {
		System.out.println("\n+++++++CONSULTAR PK's+++++++");
		System.out.print("\nIngrese el nombre de la tabla: ");
		String tabla = sc.next();

		try {
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet resul = dbmd.getPrimaryKeys("mismuebles", "mismuebles", tabla);

			System.out.println("\nInformación Primary Key:");

			while (resul.next()) {
				System.out.println("PK> " + resul.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
		}
	}
	
	//INSERTAR Y ELIMINAR DATOS

	private static void inserEliDatos(Scanner sc, Connection conexion) {
		System.out.println("\n1. Carga masiva");
		System.out.println("2. Nueva venta");
		System.out.println("3. Modificacion de precio");
		System.out.println("");
		System.out.print("Elige la operacion: ");
		int op = sc.nextInt();

		switch (op) {
		case 1:
			cargaMasiva(sc, conexion);
			break;

		case 2:
			nuevaVenta(sc, conexion);
			break;
		case 3:
			modPrecio(sc, conexion);
			break;
		}
	}
	
	private static void cargaMasiva(Scanner sc, Connection conexion) {
		File fileSql = new File("./Practica2/Ficheros/cargaDatos.sql");
		System.out.println("Este archivo estará dentro de la carpeta " + fileSql.getName() + "\n");
		System.out.print("¿Desea crear una nueva ruta? (Y-N) ");
		String conf = sc.next();

		File ficheroCarga = null;

		if (conf.equals("Y") || conf.equals("y")) {
			System.out.print("\nIntroduzca la nueva direccion: ");
			String direct = sc.next();
			File nuevoDir = new File("./Practica2/" + direct, fileSql.getName());
			nuevoDir.mkdirs();

			try {
				ficheroCarga = Files.copy(fileSql.toPath(), nuevoDir.toPath(), StandardCopyOption.REPLACE_EXISTING).toFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (conf.equals("N") || conf.equals("n")) {
			System.out.println("El archivo se encuentra en la carpeta por defecto");
			ficheroCarga = fileSql;
		}
		else
			System.out.println("Opción no encontrada. Intentelo de nuevo.");

		try (BufferedReader bfReader = new BufferedReader(new FileReader(ficheroCarga));) {
			String linea = null;
			int rowsAffected = 0;

			while ((linea = bfReader.readLine()) != null) {
				if (!linea.isEmpty()) {
					Statement sentencia = conexion.createStatement();
					rowsAffected += sentencia.executeUpdate(linea);
				}
			}

			System.out.printf("La carga masiva se ha realizado. Filas afectadas: %d \n\n", rowsAffected);
		} catch (FileNotFoundException e) {
			System.out.println("*El archivo no se ha podido encontrar o ha ocurrido un error con el*");
		} catch (IOException e) {
			System.out.println("*Ha ocurrido un error en la entrada o salida en el fichero*");
		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la carga masiva*");
		}
	}
	
	private static void nuevaVenta(Scanner sc, Connection conexion) {
		System.out.print("\nIntroduce la cantidad del producto: ");
		int cant = sc.nextInt();

		System.out.print("\nIntroduce el ID del cliente: ");
		int idCliente = sc.nextInt();

		System.out.print("\nIntroduce el ID del producto ");
		int idProd = sc.nextInt();

		Statement sentencia;

		try {
			boolean exProd = false, exCli = false;
			sentencia = conexion.createStatement();
			ResultSet resulVent = sentencia.executeQuery("SELECT id_venta FROM ventas ORDER BY id_venta DESC LIMIT 1");
			int idVenta = 0;

			while (resulVent.next()) {
				idVenta = resulVent.getInt(1) + 1;
			}
			resulVent.close();

			ResultSet resulProd = sentencia.executeQuery("SELECT id_Producto FROM productos");
			while (resulProd.next()) {
				if (idProd == resulProd.getInt(1))
					exProd = true;
			}
			resulProd.close();

			ResultSet resulCli = sentencia.executeQuery("SELECT id_cliente FROM clientes");
			while (resulCli.next()) {
				if (idCliente == resulCli.getInt(1))
					exCli = true;
			}
			sentencia.close();
			resulCli.close();

			if (exProd && exCli) {
				String sql = "INSERT IGNORE INTO ventas VALUES(?,?,?,?)";
				PreparedStatement prep = conexion.prepareStatement(sql);
				prep.setInt(1, idVenta);
				prep.setInt(2, cant);
				prep.setInt(3, idCliente);
				prep.setInt(4, idProd);

				int rowsAffected = prep.executeUpdate();
				System.out.printf("Nueva venta guardada. Numero de filas afectadas: %d \n", rowsAffected);
			}

		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
		}

	}	

	private static void modPrecio(Scanner sc, Connection conexion) {
		System.out.print("\nIntroduce el nuevo precio: ");
		int nuevoPrecio = sc.nextInt();
		sc.nextLine();

		System.out.print("\nIntroduce el ID del producto a modificar el precio: ");
		int idProd = sc.nextInt();

		boolean prodExiste = false;

		try {
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT id_Producto FROM productos");

			while (resul.next()) {
				if (idProd == resul.getInt(1))
					prodExiste = true;
			}

			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error al realizar la busqueda de productos*");
		}

		if (prodExiste) {
			try {
				
				/* El procedimiento almacenado en la bbdd
				 * DELIMITER //
				 * CREATE PROCEDURE subidaDePrecio(idProd INT, nuevoPrecio INT)
				 * 	BEGIN
				 * 		UPDATE productos SET precio = nuevoPrecio
				 * 			WHERE id_Producto = idProd;
				 * 		COMMIT;
				 * 	END; //
				 */
				String sql = "{call subidaDePrecio(?,?)}";
			
				CallableStatement llamada = conexion.prepareCall(sql);

				llamada.setInt(1, idProd);
				llamada.setInt(2, nuevoPrecio);
				llamada.executeUpdate();
				System.out.println("El producto " + idProd + " ha cambiado su precio a " + nuevoPrecio);

			} catch (SQLException e) {
				System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
			}
		} else
			System.out.printf("No se ha encontrado algún producto con el id %d \n", idProd);
	}

	//CONSULTAS

	private static void consultas(Scanner sc, Connection conexion) {
		System.out.println("\n1. Seleccionar los datos del cliente por ciudad");
		System.out.println("2. Seleccionar los datos de venta");
		System.out.println("3. Seleccionar consultas genericas");
		System.out.println("");
		System.out.print("Elige la operacion: ");
		int op = sc.nextInt();

		switch (op) {
		case 1:
			consultarPorCiudad(sc, conexion);
			break;

		case 2:
			consultarVentas(sc, conexion);
			break;
			
		case 3:
			consultasLibres(sc, conexion);
			break;
		}
	}
	
	private static void consultarPorCiudad(Scanner sc, Connection conexion) {
		System.out.print("\nIntroduzca el nombre de la ciudad a consultar: ");
		String ciudad = sc.next();

		String sql = "SELECT * FROM clientes WHERE ciudad = ?";
		try {
			PreparedStatement prep = conexion.prepareStatement(sql);
			prep.setString(1, ciudad);
			ResultSet resul = prep.executeQuery();

			while (resul.next()) {
				System.out.printf("\nID Cliente: %d", resul.getInt(1));
				System.out.printf("\nNombre: %s", resul.getString(2));
				System.out.printf("\nDirección: %s", resul.getString(3));
				System.out.printf("\nTeléfono: %s", resul.getString(4));
				System.out.printf("\nCiudad: %s", resul.getString(5));
				System.out.printf("\nEmail: %s", resul.getString(6));
				System.out.println("\n\n-------------------------------\n");
			}
		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
		}
	}
	
	private static void consultarVentas(Scanner sc, Connection conexion) {
		ArrayList<String> cols = new ArrayList<String>();
		System.out.println("\nColumnas a las que se pueden filtrar ");

		try {
			DatabaseMetaData dbmd = conexion.getMetaData();
			ResultSet resDbmd = dbmd.getColumns("mismuebles", "mismuebles", "ventas", null);
			
			while(resDbmd.next()) {
				String colName = resDbmd.getString("COLUMN_NAME");
				System.out.println("> " + colName);
				cols.add(colName);
			}
			
		} catch (SQLException e) {
			System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
		}

		System.out.print("\nIntroduzca el filtro de la consulta"
				+ "\n(tenga en cuenta que debe usar la sintaxis SQL): ");
		sc.useDelimiter(System.lineSeparator());
		String filtro = sc.next();
		
		if(verifcaColumnaExistente(filtro, cols)) {
			StringBuilder sql = new StringBuilder();
			String prim = "SELECT c.id_Cliente, c.nombre, v.cantidad, p.descripcion "
					+ "FROM ventas v "
					+ "INNER JOIN clientes c ON v.id_cliente = c.id_cliente "
					+ "INNER JOIN productos p ON v.id_producto = p.id_producto " + "WHERE v.";
			sql.append(prim);
			sql.append(filtro);

			try {
				Statement sentencia = conexion.createStatement();
				ResultSet resul = sentencia.executeQuery(sql.toString());

				while (resul.next()) {
					System.out.println("\n-------------------------------");
					System.out.printf("ID Cliente: %d", resul.getInt(1));
					System.out.printf("\nNombre: %s", resul.getString(2));
					System.out.printf("\nCantidad Vendida: %s", resul.getString(3));
					System.out.printf("\nDescripción: %s", resul.getString(4));
					System.out.println("\n-------------------------------");
				}

			} catch (SQLException e) {
				System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
			}
		}else
			System.out.println("Columna en la tabla Ventas no encontrando. Saliendo de la operación");
	}
	
	private static boolean verifcaColumnaExistente(String filtro, ArrayList<String> cols) {
		boolean existe = false;
		
		for(String col : cols) {
			if(filtro.startsWith(col))
				existe = true;
		}
		return existe;
	}

	private static void consultasLibres(Scanner sc, Connection conexion) {
		System.out.print("\nIntroduzca la consulta: ");
		sc.useDelimiter(System.lineSeparator());
		String consulta = sc.next();
		sc.nextLine();
		
		if(consulta.contains("SELECT") && !consulta.contains("UPDATE") &&
		!consulta.contains("INSERT") && !consulta.contains("DELETE") ){
			
			try{
				Statement sentencia = conexion.createStatement();
				ResultSet resul = sentencia.executeQuery(consulta);
				ResultSetMetaData rsmd = resul.getMetaData();
				
				int numCols = rsmd.getColumnCount();
				
				for(int a=1; a <= numCols; a++)
					System.out.print("| " + rsmd.getColumnName(a) + "| \t");
				
				while(resul.next()) {
					System.out.println("");
					
					for (int a = 1; a <= numCols; a++) {
						System.out.print("| " + resul.getString(a) + "| \t");
					}
				}

				System.out.println("");
			}catch(SQLException e) {
				System.out.println("*Ha ocurrido un error realizando la sentencia SQL*");
			}
		}
	}
}