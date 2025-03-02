import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

public class MainBDConsulting {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\t*** BD CONSULTING ***");
		System.out.println("1. Migrar datos a neodatis (Fitness.neo)");
		System.out.println("2. Realizar consultas en neodatis");
	
		boolean finBucle = false;
		
		while(!finBucle) {
			System.out.print("\nIntroduce tu opción: ");
			int opUsuario = sc.nextInt();
			
			switch (opUsuario) {
				case 1:
					opMigrarDatos();
					System.out.println("Los datos se han migrado correctamente.");
					break;
					
				case 2:
					opConsultasNeodatis(sc);
					finBucle = true;
					break;
			}
		}
		
		System.out.println("\n> El programa ha terminado.");
		sc.close();
	}
	
	private static void opConsultasNeodatis(Scanner sc) {
		ODB odb = ODBFactory.open("FitnessClub.neo");
		
		System.out.println("\n1. Mostrar el nombre del centro, el nombre del responsable, la localidad del centro ordenado por localidad para una provincia introducida por el usuario.");
		System.out.println("2. Mostrar la media de duración de las clases de un nivel de dificultad introducido por el usuario.");
		System.out.println("3. Mostrar todos los datos de los monitores que sean mujeres y que su nombre no acabe en A.");
		System.out.println("4. Mostrar la suma de la duración de las clases y el nombre del monitor agrupadas por monitor.");
		System.out.println("5. Mostrar la provincia y el número de centros agrupados por provincia para todos los centros.");
		System.out.print("\nIntroduce tu opcion para elegir la consulta: ");
		int opUsuario = sc.nextInt();
		sc.nextLine();
		
		switch (opUsuario) {
			case 1:	
				mostrarGimnasiosPorProvincia(sc, odb);
				break;
				
			case 2:
				mediaDuracionPorDificultad(sc, odb);
				break;
				
			case 3:
				mostrarDatosMonitoresMujeres(odb);
				
				break;
			case 4:
				sumaDuracionClasesAgrupadasMonitor(odb);
				
				break;
			case 5:
				mostrarProvinciaCantCentrosAgrupadosPorProvincia(odb);
				break;
				
			default:
				System.out.println("Opción no encontrada");
				break;
		}		
	}

	private static void mostrarProvinciaCantCentrosAgrupadosPorProvincia(ODB odb) {
		//Recuperamos los objetos de tipo gimnasios y creamos un hashmap el cual nos ayudará a agrupar la cantidad de gimnasios por provincia
		Objects<Gimnasios> listaGimnasios = odb.getObjects(Gimnasios.class);
		HashMap<String, Integer> contGymPorProv = new HashMap<String, Integer>();
		
		while(listaGimnasios.hasNext()) {
			//Instanciamos los gimnasios de la lista
			Gimnasios g = listaGimnasios.next();
			
			//Revisamos el contador de la provincia, si aun la provincia no está registrada será 0 
			int contador = contGymPorProv.getOrDefault(g.getProvincia(), 0);
			
			//Cuando tengamos el contador aumentaremos en uno, además de insertar la key (String provincia)
		    contGymPorProv.put(g.getProvincia(), contador + 1);
		}
		
		System.out.println("\n\t*** RESULTADO ***\n");
		for (Map.Entry<String, Integer> mapSI : contGymPorProv.entrySet()) {
		    String prov = mapSI.getKey();
		    int count = mapSI.getValue();
		    
			//Mostramos los valores por consola
		    System.out.printf("Provincia: %s Contador: %d %n", prov, count);
		}
	}

	private static void sumaDuracionClasesAgrupadasMonitor(ODB odb) {
		//Recuperamos los objetos de tipo clases y creamos un hashmap el cual nos ayudará a gestionar la informacion correctamente
		Objects<Clases> clases = odb.getObjects(Clases.class);
		HashMap<Monitores, Integer> monitoresDuracion = new HashMap<Monitores, Integer>();
		
		while(clases.hasNext()) {
			//Instanciamos las clases de la lista, y nos centramos en recorrer el set de monitores de dicha clase
			Clases c = clases.next();
			Set<Monitores> setMonitores = c.getSetMonitores();
			
			//Recorremos el set de monitores, añadiendo el monitor al hashmap y la duracion de la clase seleccionada
			for(Monitores m : setMonitores) {
				monitoresDuracion.put(m, monitoresDuracion.getOrDefault(m, 0) + c.getDuracMin());
			}
		}
		
		System.out.println("\n\t*** RESULTADO ***\n");
		for (Map.Entry<Monitores, Integer> mapMI : monitoresDuracion.entrySet()) {
		    Monitores mon = mapMI.getKey();
		    int duracion = mapMI.getValue();
		    
			//Mostramos los valores por consola
		    System.out.printf("Monitor: %s Duración: %d %n", mon.getNombre(), duracion);
		}
	}

	private static void mostrarDatosMonitoresMujeres(ODB odb) {
		//Realizamos una query para filtrar los monitores que sean mujeres
		IQuery query1 = new CriteriaQuery(Monitores.class, Where.equal("sexo", 'M'));
		Objects<Monitores> monitoresMujeres = odb.getObjects(query1);
		
		System.out.println("\n\t*** RESULTADO ***\n");
		while(monitoresMujeres.hasNext()) {
			//Instanciamos los objetos uno a uno para poder evaluarlos de manera correcta y verificar si su nombre termina con la vocal 'a'
			Monitores mon = monitoresMujeres.next();
			
			//En caso de que cumpla con la condicion lo mostraremos en pantalla
			if(!mon.getNombre().endsWith("a")){
				System.out.print(mon.toString() + "\n");
			}
		}
	}

	private static void mediaDuracionPorDificultad(Scanner sc, ODB odb) {
		//Solicitamos el nivel de dificultad al usuario y la ponemos a mayusuclas puesto que en la base de datos se encuentra asi
		System.out.print("\nIngresa el nivel de la dificultad: ");
		String dificultad = sc.nextLine().toUpperCase();

		//Indicamos las condiciones establecidas y usamos el metodo avg para extraer la media
		Values media = odb.getValues(new ValuesCriteriaQuery(Clases.class, Where.equal("dificultad", dificultad)).avg("duracMin"));
		ObjectValues values = media.next();
		
		//Mostramos el resultado por consola
		System.out.println("\n\t*** RESULTADO ***\n");
		System.out.println("Valor de media de las clases con nivel de dificultad " + dificultad + " : " + values.getByIndex(0));
	}

	private static void mostrarGimnasiosPorProvincia(Scanner sc, ODB odb) {
		//Para esta consulta, solicitamos la provincia al usuario, realizamos una query para buscar los objetos que cumplan con esta condicion en la base de datos neodatis
		System.out.print("\nIngresa el nombre de la provincia (Recuerda poner las mayúsculas): ");
		String provincia = sc.nextLine();
		
		IQuery query = new CriteriaQuery(Gimnasios.class, Where.equal("provincia", provincia));
		query.orderByAsc("localidad");
		
		//Obtendremos los objetos en una lista, e iteramos sobre la lista para poder mostrarla por consola
		Objects<Gimnasios> gimnasiosProvincia = odb.getObjects(query);
		
		System.out.println("\n\t*** RESULTADO ***\n");
		while(gimnasiosProvincia.hasNext()) {
			Gimnasios gym = gimnasiosProvincia.next();
			System.out.printf("- Nombre del centro: %s, Nombre del responsable: %s, Localidad: %s %n", gym.getNombre(), gym.getMonitor().getNombre() + " " + gym.getMonitor().getApellido(), gym.getLocalidad());
		}
	}
	
	private static void opMigrarDatos() {
		try {
			// Instanciamos el conector JDBC y recuperamos los datos de la base de datos
			// para luego meterlos en un arraylist
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdrel_clases", "root", "root");
			ArrayList<Clases> listaClases = recuperaClases(conexion);
			ArrayList<Gimnasios> listaGimnasios = recuperaGimnasios(conexion);
			ArrayList<Monitores> listaMonitores = recuperaMonitores(conexion, listaGimnasios);

			// Habiendo recuperado los datos de la base de datos, inicializamos el objeto
			// ODB para poder guardar los nuevos datos en la base de datos de Neodatis
			ODB odb = ODBFactory.open("FitnessClub.neo");

			// Insertamos los objetos en el objeto ODB anteriormente creado
			insertarObjetos(listaClases, listaGimnasios, listaMonitores, odb);

			// Introducimos los datos en los sets de gimnasios y clases
			almacenarSetClases(odb, conexion);
			almacenarSetGimnasios(odb, conexion);

			//Al terminar de haber introducido los datos realizamos un commit para confirmar cambios y cerramos el objeto odb
			odb.commit();
			odb.close();
			conexion.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void insertarObjetos(ArrayList<Clases> listaClases, ArrayList<Gimnasios> listaGimnasios,
			ArrayList<Monitores> listaMonitores, ODB odb) {

		//Recorremos el array de clases e instanciamos los objetos Clases creadas en la base de datos neodatis
		//Esto servirá para no introducir datos ya existentes
		for (Clases cl : listaClases) {
			Objects<Clases> clasesCreadas = odb.getObjects(Clases.class);
			if (clasesCreadas.isEmpty()) {
				odb.store(cl);
			}else {
				while (clasesCreadas.hasNext()) {
					if (!cl.equals(clasesCreadas.next()))
						odb.store(cl);
				}
			}
		}

		//Realizamos lo mismo para los objetos Gimnasios, solo guardaremos si es que el objeto no existe
		for (Gimnasios gm : listaGimnasios) {
			Objects<Gimnasios> gimnasiosCreados = odb.getObjects(Gimnasios.class);

			if (gimnasiosCreados.isEmpty()) {
				odb.store(gm);
			} else {
				while (gimnasiosCreados.hasNext()) {
					if (!gm.equals(gimnasiosCreados.next())) {
						odb.store(gm);
					}
				}
			}
		}

		//Finalmente recorremos el array de monitores, además de la lista de objetos Monitores
		//En caso de que encontremos un monitor con el mismo id no lo guardaremos, 
		//solo se guardará si es que el objeto no existe
		for (Monitores mn : listaMonitores) {
			Objects<Monitores> monitoresCreados = odb.getObjects(Monitores.class);
			boolean monitorExiste = false;

			while (monitoresCreados.hasNext()) {
				Monitores monCreado = monitoresCreados.next();

				if (mn.getId() == monCreado.getId())
					monitorExiste = true;
			}

			if (!monitorExiste)
				odb.store(mn);

		}
	}

	// Metodo para recuperar las clases, devolverá un arraylist con todas las clases
	// recuperadas
	private static ArrayList<Clases> recuperaClases(Connection conex) throws SQLException {
		ArrayList<Clases> listaClases = new ArrayList<>();
		Statement sentencia = conex.createStatement();
		ResultSet resul = sentencia.executeQuery("SELECT * FROM clases");

		// Tras realizar la consulta introduciremos los datos recuperados al objeto
		// clases y lo añadiremos al arraylist
		while (resul.next()) {
			Clases c = new Clases(resul.getInt(1), resul.getString(2), resul.getString(3), resul.getInt(4),
					new HashSet<Monitores>());
			listaClases.add(c);
		}

		resul.close();
		return listaClases;
	}

	// Metodo para recuperar los gimnasios, devolverá un arraylist con todos los
	// datos recuperados
	private static ArrayList<Gimnasios> recuperaGimnasios(Connection conex) throws SQLException {
		ArrayList<Gimnasios> listaGimnasios = new ArrayList<>();
		ArrayList<Monitores> listaMonitores = new ArrayList<>();

		Statement sentencia = conex.createStatement();
		ResultSet resul = sentencia
				.executeQuery("SELECT * FROM gimnasios g INNER JOIN monitores m ON g.responsable = m.cod_monitor");

		// Teniendo los datos de la consulta recuperaremos los objetos monitores y
		// gimnasios
		while (resul.next()) {
			// Inicializamos el objeto monitor con y verificamos si ya lo tenemos en el
			// arraylist, esto nos servirá para no meter objetos duplicados y reutilizar los objetos que ya tenemos creados
			Monitores monitorRecuperado = null;
			for (Monitores monitor : listaMonitores) {

				// En caso de qe ya exista se guardarán los datos en el objeto inicializado
				if (monitor.getId() == resul.getInt("cod_monitor")) {
					monitorRecuperado = monitor;
					break;
				}
			}

			// En caso de que no exista y el monitor siga en null, se le asignarán los
			// valores de la consulta realizada
			// Y se creará el objeto
			if (monitorRecuperado == null) {
				monitorRecuperado = new Monitores(resul.getInt("cod_monitor"), resul.getString("m.nombre"),
						resul.getString("apellido"), resul.getDate("fecha_nac"), resul.getString("sexo").charAt(0),
						null);

				// Añadimos el objeto a la lista para verificar si hay duplicados en las
				// siguientes iteraciones
				listaMonitores.add(monitorRecuperado);
			}

			// Tambien recuperamos el objeto gimnasios con los valores de la consulta y lo
			// agregamos al arraylist
			// Para despues asignarle al monitor el gimnasio creado
			Gimnasios gimnasioRecuperado = new Gimnasios(resul.getInt("cod_centro"), resul.getString("g.nombre"),
					monitorRecuperado, resul.getString("direccion"), resul.getString("localidad"),
					resul.getString("provincia"), new HashSet<Monitores>());

			listaGimnasios.add(gimnasioRecuperado);
			monitorRecuperado.setGym(gimnasioRecuperado);
		}
		
		resul.close();

		return listaGimnasios;
	}

	//Metodo para recuperar los monitores, devolverá un arraylist con todos los datos recuperados
	//Usamos un arraylist como parametro para reutilizar los gimnasios que ya han sido creados y de esta manera no introducir duplicados
	private static ArrayList<Monitores> recuperaMonitores(Connection conex, ArrayList<Gimnasios> listaGimnasios)
			throws SQLException {
		ArrayList<Monitores> listaMonitores = new ArrayList<>();

		Statement sentencia = conex.createStatement();
		ResultSet resul = sentencia
				.executeQuery("SELECT * FROM monitores m INNER JOIN gimnasios g ON m.cod_centro = g.cod_centro;");

		//Para esta recuperacion de datos tenemos en cuenta que todos los gimnasios han sido recuperados en el metodo recuperaGimnasios
		//Puesto que los gimnasios siempre tendrán un responsable
		while (resul.next()) {
			
			//Inicializamos un objeto gimnasio y verificamos si es que lo tenemos en el arraylist
			//En caso de que si exista usaremos ese gimnasio para asignarselo al monitor
			Gimnasios gimnasioRecuperado = null;
			for (Gimnasios g : listaGimnasios) {
				if (g.getId() == resul.getInt("cod_centro")) {
					gimnasioRecuperado = g;
					break;
				}
			}

			//Una vez recurperado el gimnasio, recuperamos los datos del monitor, incluyendo el gimnasio anteriormente localizado
			Monitores monitorRecuperado = new Monitores(resul.getInt("cod_monitor"), resul.getString("m.nombre"),
					resul.getString("apellido"), resul.getDate("fecha_nac"), resul.getString("sexo").charAt(0),
					gimnasioRecuperado);

			listaMonitores.add(monitorRecuperado);
		}
		
		resul.close();

		return listaMonitores;
	}

	//Metodo para insertar los sets de monitores en los objetos gimnasios
	private static void almacenarSetGimnasios(ODB odb, Connection conex) throws SQLException {
		//Recuperamos todos los gimnasios guardados en la base de datos neodatis
		Objects<Gimnasios> gimnasiosCreados = odb.getObjects(Gimnasios.class);

		while (gimnasiosCreados.hasNext()) {		
			//Instanciamos cada gimnasio, y realizamos una consulta utilizando su id
			//Esto nos servirá para poder encontrar todos los monitores que estén asignados a cada gimnasio de la lista
			Gimnasios g = gimnasiosCreados.next();
			Statement sentencia = conex.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT cod_monitor FROM monitores WHERE cod_centro = " + g.getId());

			//Iteramos sobre la sentencia sql qe acabamos de realizar para poder encontrar el monitor en neodatis
			while (resul.next()) {
				IQuery query = new CriteriaQuery(Monitores.class, Where.equal("id", resul.getInt(1)));
				Objects<Monitores> monitores = odb.getObjects(query);
				
				//Como el monitor tiene un unico id solo devolverá un objeto. 
				//Por tanto lo recuperamos y se lo asignamos al set de monitores del objeto gimnasios y lo guardamos
				Monitores m = monitores.next();
				g.getSetMonitores().add(m);
				odb.store(g);
			}
					
			resul.close();
		}
	}

	//Metodo para insertar los sets de monitores en los objetos clases
	private static void almacenarSetClases(ODB odb, Connection conex) throws SQLException {
		//Recuperamos todas las clases guardadas en neodatis
		Objects<Clases> clasesCreadas = odb.getObjects(Clases.class);

		while (clasesCreadas.hasNext()) {
			//Instanciamos cada clase de la lista y realizamos una consulta especificando su id
			//Esto nos devolverá los monitores asignados a esa clase
			Clases c = clasesCreadas.next();
			Statement sentencia = conex.createStatement();
			ResultSet resul = sentencia
					.executeQuery("SELECT cod_monitor FROM clasmon WHERE cod_clase = " + c.getCodClase());

			//Iteramos sobre la sentencia sql qe acabamos de realizar para poder encontrar el monitor en neodatis
			while (resul.next()) {
				IQuery query = new CriteriaQuery(Monitores.class, Where.equal("id", resul.getInt(1)));
				Objects<Monitores> monitores = odb.getObjects(query);
				
				//Como el monitor tiene un unico id solo devolverá un objeto. 
				//Por tanto lo recuperamos y se lo asignamos al set de monitores del objeto clases y lo guardamos
				Monitores m = monitores.next();
				c.getSetMonitores().add(m);
				odb.store(c);
			}
			
			resul.close();
		}
	}

	
}