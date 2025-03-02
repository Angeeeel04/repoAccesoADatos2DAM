package aplicacion;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;

import clasesVentasCoches.Cliente;
import clasesVentasCoches.Coches;
import clasesVentasCoches.Venta;

public class ClaseAltas {
	/* METODOS PARA REALIZAR LAS ALTAS EN LA BASE DE DATOS
	 * Solicitan los datos de cada tipo de operacion al cliente, para luego poder verificarlos.
	 * Si es que los datos cumplen con las especificaciones de la base de datos, se enviará un booleano
	 * a la clase main para que se realice la transaccion
	*/
	
	public static boolean altaVenta(Scanner sc, Session sesion) {
		boolean txCommit = false;
		Integer id = generacionID(sesion, "Venta");

		/* CLIENTE */
		System.out.print(" Introduce el ID del cliente: ");
		int idCliente = MainVentasCoches.verificarEntradaInt(sc);
		Cliente cl1 = (Cliente) sesion.load(Cliente.class, (int) idCliente);
		System.out.println("*	El nombre del cliente es : " + cl1.getNombre() + "	*\n");

		/* COCHE */
		System.out.print(" Introduce el ID del coche: ");
		int idCoche = MainVentasCoches.verificarEntradaInt(sc);
		Coches ch1 = (Coches) sesion.load(Coches.class, (int) idCoche);
		System.out.println("*	La matrícula del coche es : " + ch1.getMatricula() + "	*\n");

		// La fecha de compra será la fecha del día actual
		// Y la fecha de entrega será 3 días despues de haber hecho la compra
		java.util.Date hoy = new java.util.Date();
		java.sql.Date fechaCompra = new java.sql.Date(hoy.getTime());

		// Calculamos la fecha de entrega aumentando la cantidad
		// de milisegundos que hay en 3 días
		java.sql.Date fechaEntrega = new java.sql.Date(hoy.getTime() + (3 * 24 * 60 * 60 * 1000));

		// Solicitamos el precio de la venta
		System.out.print(" Introduce el precio de la venta: ");
		int precio = MainVentasCoches.verificarEntradaInt(sc);

		// La venta se registrará si el precio es más de 0
		// Para luego mostrar los datos de la nueva venta
		if (precio > 0) {
			Venta nvVenta = new Venta(id, cl1, ch1, fechaCompra, fechaEntrega, precio);
			sesion.save(nvVenta);
			MainVentasCoches.mostrarDatosOb(nvVenta);
			txCommit = true;
		} else
			System.out.println(" La venta no se ha registrado.");

		return txCommit;
	}

	public static boolean altaCliente(Scanner sc, Session sesion) {
		boolean txCommit = false;
		Integer id = generacionID(sesion, "Cliente");
		
		System.out.print(" Introduce el DNI del cliente: ");
		String dni = sc.nextLine();

		System.out.print(" Introduce el nombre del cliente: ");
		String nombre = sc.nextLine();

		System.out.print(" Introduce la direccion del cliente: ");
		String direccion = sc.nextLine();

		System.out.print(" Introduce el ciudad del cliente: ");
		String ciudad = sc.nextLine();

		System.out.print(" Introduce el teléfono del cliente: ");
		String telefono = sc.nextLine();

		System.out.print(" Introduce el email del cliente: ");
		String email = sc.nextLine();

		System.out.print(" Introduce el sexo del cliente: ");
		String sexo = sc.nextLine();

		System.out.print(" Introduce la edad del cliente: ");
		byte edad = (byte) MainVentasCoches.verificarEntradaInt(sc);

		Set ventas = new HashSet(0);

		Cliente nvCliente = new Cliente(id, dni, nombre, direccion, ciudad, telefono, email, edad, sexo, ventas);
		boolean verificado = verificaDatosCliente(nvCliente);
		
		// Una vez que el cliente se ha creado, verificamos los datos del objeto cliente para comprobar
		// si es que se cumple con nuestras especificaciones, en caso de que sea así
		// lo guardaremos y se enviará un booleano a la clase main para poder realizar el commit
		if (verificado) {
			sesion.save(nvCliente);
			MainVentasCoches.mostrarDatosOb(nvCliente);
			txCommit = true;
		} else
			System.out.println("\n Los datos introducidos no han sido aceptados");

		return txCommit;
	}

	public static boolean altaCoche(Scanner sc, Session sesion) {
		boolean txCommit = false;
		Integer id = generacionID(sesion, "Coches");
		
		System.out.print(" Introduce la marca del coche: ");
		String marca = sc.nextLine();

		System.out.print(" Introduce el modelo del coche: ");
		String modelo = sc.nextLine();

		System.out.print(" Introduce el color del coche: ");
		String color = sc.nextLine();

		System.out.print(" Introduce la matricula del coche: ");
		String matricula = sc.nextLine();

		System.out.print(" Introduce los extras del coche: ");
		String extras = sc.nextLine();

		System.out.print(" Introduce la cantidad de plazas del coche: ");
		byte plazas = (byte) sc.nextByte();

		Set ventas = new HashSet(0);

		Coches nvCoche = new Coches(plazas, marca, modelo, color, matricula, plazas, extras, ventas);
		
		boolean verificado = verificaDatosCoche(nvCoche);

		// Una vez que el coche se ha creado, verificamos los datos del objeto coche para comprobar
		// si es que se cumple con nuestras especificaciones, en caso de que sea así
		// lo guardaremos y se enviará un booleano a la clase main para poder realizar el commit
		if (verificado) {
			sesion.save(nvCoche);
			MainVentasCoches.mostrarDatosOb(nvCoche);
			txCommit = true;
		} else
			System.out.println(" Los datos introducidos no han sido aceptados");

		return txCommit;
	}

	// Metodos que verifican si los datos introducidos del cliente o del coche están
	// correctos y cumplen con las especificaciones
	// de la base de datos
	public static boolean verificaDatosCliente(Cliente cli) {
		String regex = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}";

		if (cli.getDni().length() == 9 && cli.getNombre().length() < 50 && cli.getDireccion().length() <= 50 && cli.getCiudad().length() <= 50
				&& cli.getTelefono().length() <= 50 && cli.getEmail().length() <= 50 && cli.getEmail().matches(regex)
				&& (cli.getSexo().equalsIgnoreCase("hombre") || cli.getSexo().equalsIgnoreCase("mujer")) && cli.getEdad() >= 18)

			return true;

		return false;
	}

	public static boolean verificaDatosCoche(Coches co) {
		if (!co.getMarca().isEmpty() && co.getMarca().length() <= 30 && !co.getModelo().isEmpty() && co.getModelo().length() <= 30 
			&& !co.getColor().isEmpty() && co.getColor().length() <= 30 && !co.getMatricula().isEmpty() && co.getMatricula().length() == 7 
			&& co.getPlazas() > 0)
			return true;

		return false;
	}

	//Este metodo genera un ID en base a una consulta HQL
	private static Integer generacionID(Session sesion, String nomTabla) {
		Query q;

		if (nomTabla.equals("Coches"))
			q = sesion.createQuery("select max(idCoche)" + " from " + nomTabla);
		else
			q = sesion.createQuery("select max(id" + nomTabla + ") from " + nomTabla);

		Integer idVenta = (Integer) q.uniqueResult() + 1;
		return idVenta;
	}
}