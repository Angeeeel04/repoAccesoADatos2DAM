package aplicacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

import org.hibernate.Session;

import clasesVentasCoches.Cliente;
import clasesVentasCoches.Coches;
import clasesVentasCoches.Venta;

public class ClaseModificaciones {
	/* METODOS PARA REALIZAR LAS MODIFICACIONES EN LA BASE DE DATOS
	 * Solicitan el id de la venta, cliente o coche a modificar
	 * se carga el objeto, se muestra la informacion del objeto y
	 * se solicitan los nuevos datos a modificar, para despues solicitar
	 * una confirmacion para guardar los cambios
	*/
	
	public static boolean modificaVenta(Scanner sc, Session sesion) {
		System.out.print(" Introduzca el ID de la venta a modificar: ");
		int id = MainVentasCoches.verificarEntradaInt(sc);

		Venta venta = sesion.load(Venta.class, id);
		MainVentasCoches.mostrarDatosOb(venta);
		
		System.out.print(" Introduzca la nueva fecha de entrega DD/MM/YYYY: ");
		String fechaEntrega = sc.nextLine();

		try {
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate fechaConv = LocalDate.parse(fechaEntrega, formatoFecha);
			Date fVentaSql = java.sql.Date.valueOf(fechaConv);
			venta.setFechaEntrega(fVentaSql);
		} catch (DateTimeParseException e) {
			System.out.println(" La fecha ingresada no es válida. La fecha de entrega no se cambiará");
		}

		System.out.print(" Introduzca el nuevo precio de la venta: ");
		int nvPrecio = MainVentasCoches.verificarEntradaInt(sc);
		venta.setPrecio(nvPrecio);
		
		System.out.print(" Introduzca el ID del nuevo coche al que corresponde la venta: ");
		int idCoche = MainVentasCoches.verificarEntradaInt(sc);
		Coches cocheNv = sesion.load(Coches.class, idCoche);
		venta.setCoches(cocheNv);
		
		MainVentasCoches.mostrarDatosOb(venta);
		
		return confirmacionModificacion(sc, sesion, venta);
	}
	
	public static boolean modificaCliente(Scanner sc, Session sesion) {
		System.out.print(" Introduzca el ID del cliente a modificar: ");
		int id = MainVentasCoches.verificarEntradaInt(sc);

		Cliente clMod = sesion.load(Cliente.class, id);
		MainVentasCoches.mostrarDatosOb(clMod);
				
		System.out.print(" Introduzca la nueva dirección del cliente: ");
		String nvDirec = sc.nextLine();
		clMod.setDireccion(nvDirec);
		
		System.out.print(" Introduzca la nueva ciudad del cliente: ");
		String nvCiudad = sc.nextLine();
		clMod.setCiudad(nvCiudad);
		
		System.out.print(" Introduzca el nuevo teléfono del cliente: ");
		String nvTelefono = sc.nextLine();
		clMod.setTelefono(nvTelefono);
		
		System.out.print(" Introduzca la nueva direccion email del cliente: ");
		String nvEmail = sc.nextLine();
		clMod.setEmail(nvEmail);
		
		System.out.print(" Introduzca la nueva edad del cliente: ");
		byte nvEdad = (byte) MainVentasCoches.verificarEntradaInt(sc);
		clMod.setEdad(nvEdad);
		sc.nextLine();
		
		//Usamos el metodo de la Clase Altas para verificar si los nuevos datos del cliente cumplen con nuestros requisitos
		//En caso de que se cumplan se enviará el booleano a la clase main y se realizará el commit
		boolean verificado = ClaseAltas.verificaDatosCliente(clMod);
		
		if(verificado) {
			MainVentasCoches.mostrarDatosOb(clMod);
			return confirmacionModificacion(sc, sesion, clMod);
		}else {
			System.out.println(" Los nuevos datos introducidos no han sido aceptados");
			return false;
		}
	}
	
	public static boolean modificaCoche(Scanner sc, Session sesion) {
		System.out.print(" Introduzca el ID del coche a modificar: ");
		int id = MainVentasCoches.verificarEntradaInt(sc);

		Coches coMod = sesion.load(Coches.class, id);
		MainVentasCoches.mostrarDatosOb(coMod);
		
		System.out.print(" Introduzca el nuevo color del coche: ");
		String nvColor = sc.nextLine();
		coMod.setColor(nvColor);
		
		System.out.println(" Introduzca los nuevos extras del coche: ");
		String nvExtras = sc.nextLine();
		coMod.setExtras(nvExtras);
		
		boolean verificado = ClaseAltas.verificaDatosCoche(coMod);
		
		if(verificado) {
			MainVentasCoches.mostrarDatosOb(coMod);
			return confirmacionModificacion(sc, sesion, coMod);
		}else {
			System.out.println(" Los nuevos datos introducidos no han sido aceptados");
			return false;
		}
	}
	
	//Metodo para confirmar la modificacion, pidiendo al usuario que acepte la modificacion
	//En caso de que lo acepte devolverá un booleano
	public static boolean confirmacionModificacion(Scanner sc, Session sesion, Object ob) {
		boolean modificado = false;
		
		System.out.print(" ¿Está seguro de realizar estas modificaciones? (S/N): ");
		String rp = sc.nextLine();
		
		if(rp.equalsIgnoreCase("s")) {
			modificado = true;
			sesion.update(ob);
			System.out.println("\n El registro se ha modificado.");
		}
		
		else
			System.out.println(" No se ha realizado la operacion");
		
		return modificado;
	}
}