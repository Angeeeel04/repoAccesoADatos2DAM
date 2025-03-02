package aplicacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class ClaseInformes {
	public static void datosClienteCiudad(Scanner sc, Session sesion) {
		// Solciitamos al usuario que introduzca la ciudad para poder filtrar los
		// clientes
		System.out.print(" Introduzca la ciudad para filtrar los clientes: ");
		String ciudadCli = sc.nextLine();

		// Establecemos la consulta e introducimos el valor recibido dentro
		// Y se la pasamos por parametro a un metodo para mostrar el resultado
		Query q = sesion.createQuery("from Cliente c where c.ciudad = :ciudad");
		q.setParameter("ciudad", ciudadCli);

		muestraValoresQuery(q);
	}

	public static void datosCochePlazas(Scanner sc, Session sesion) {
		// Solicitamos al usuario que introduzca la cantidad de plazas para poder
		// filtrar los coches
		System.out.print(" Introduzca la cantidad de plazas para filtrar los coches: ");
		byte cantPlazas;

		try {
			cantPlazas = sc.nextByte();
		} catch (InputMismatchException e) {
			System.out.println(" Se ha introducido un valor no válido. Mostrando los coches con 4 plazas");
			cantPlazas = 4;
		}

		Query q = sesion.createQuery("from Coches c where c.plazas = :nPlazas");
		q.setParameter("nPlazas", cantPlazas);

		muestraValoresQuery(q);
	}

	public static void datosVentasRangoFechas(Scanner sc, Session sesion) {
		// Solciitamos al usuario que introduzca las fechas de entrega y compra para
		// poder filtrar el coche
		// Que tenga ambas fechas registradas
		Date fechaCompra, fechaEntrega;

		while (true) {
			try {
				System.out.print("\n Introduzca la fecha de compra: ");
				String fCompraUsuario = sc.nextLine();

				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fechaConv = LocalDate.parse(fCompraUsuario, formatoFecha);
				fechaCompra = java.sql.Date.valueOf(fechaConv);

				break;
			} catch (DateTimeParseException e) {
				System.out.println(" La fecha ingresada no es válida.\n");
			}
		}

		while (true) {
			try {
				System.out.print("\n Introduzca la fecha de entrega: ");
				String fEntregaUsuario = sc.nextLine();

				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate fechaConv = LocalDate.parse(fEntregaUsuario, formatoFecha);
				fechaEntrega = java.sql.Date.valueOf(fechaConv);

				break;
			} catch (DateTimeParseException e) {
				System.out.println(" La fecha ingresada no es válida.\n");
			}
		}

		// Hacemos la consulta SQL para poder obtener los campos requeridos de la venta
		// Para despues, introducir los parametros a la consulta
		Query nativeQuery = sesion
				.createNativeQuery("SELECT co.marca, co.modelo, cl.nombre, cl.edad, cl.sexo, v.precio "
						+ "FROM Venta v " + "INNER JOIN Coches co ON v.coche = co.id_coche "
						+ "INNER JOIN Cliente cl ON v.cliente = cl.id_cliente "
						+ "WHERE :fecha1 = v.Fecha_compra AND :fecha2 = v.Fecha_Entrega");

		nativeQuery.setParameter("fecha1", fechaCompra);
		nativeQuery.setParameter("fecha2", fechaEntrega);

		List<Object[]> listNative = nativeQuery.list();

		if (listNative.size() > 0) {
			System.out.println("\t * RESULTADOS CONSULTA *");
			for (Object[] resul : listNative) {
				System.out.println(" - MARCA COCHE: " + resul[0].toString());
				System.out.println(" - MODELO COCHE: " + resul[1].toString());
				System.out.println(" - NOMBRE CLIENTE: " + resul[2].toString());
				System.out.println(" - EDAD CLIENTE: " + resul[3].toString());
				System.out.println(" - SEXO CLIENTE: " + resul[4].toString());
				System.out.println(" - PRECIO VENTA: " + resul[5].toString() + "\n");
			}
		} else
			System.out.println(" No se han encontrado datos");

	}

	// Solicitamos al usuario que introduzca una consulta sql para poder convertirla
	// en native query
	// y mostrar los resultados de la consulta. En caso de que la consulta tenga
	// clausulas no permitidas
	// se notificará por consola y no se realizará ninguna accion
	public static void consultasGenericas(Scanner sc, Session sesion) {
		System.out.print("Introduce la consulta SQL: ");
		String sql = sc.nextLine().toLowerCase();

		if (!sql.contains("update") && !sql.contains("insert") && !sql.contains("delete")) {
			Query nativeQuery = sesion.createNativeQuery(sql);
			List<?> list = nativeQuery.list();

			System.out.println("\n\t * MOSTRANDO CAMPOS SOLICITADOS *\n");

			if (list.size() > 0) {
				// Recorremos los items de la lista generica y verificamos si el item es de tipo
				// Object o de tipo Objetc[]
				// ya que cada uno tiene un tratamiento especial
				for (int i = 0; i < list.size(); i++) {
					Object ob = list.get(i);

					if (ob instanceof Object[]) {
						// Si fuera de tipo Object[], lo casteamos y recorremos el array para mostrar
						// los datos que hay dentro
						Object[] item = (Object[]) ob;

						for (int j = 0; j < item.length; j++)
							System.out.println(" - Campo " + (j + 1) + " : " + item[j]);
					} else
						// Si es de tipo Object solamente mostraremos la informacion que contiene
						System.out.println(" - Campo " + (i + 1) + " : " + list.get(i));
					
					System.out.println("");
				}

			} else
				System.out.println(" No se han encontrado datos");

		} else {
			System.out.println(" La consulta tiene clausulas no permitidas");
		}
	}

	private static void muestraValoresQuery(Query q) {
		List<Object> listOb = q.list();

		if (listOb.size() > 0) {
			for (Object ob : listOb) {
				MainVentasCoches.mostrarDatosOb(ob);
			}
		}else {
			System.out.println(" No se han encontrado datos");
		}
	}
}
