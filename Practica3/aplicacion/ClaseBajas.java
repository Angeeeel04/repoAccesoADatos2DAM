package aplicacion;

import java.util.Scanner;

import org.hibernate.Session;

import clasesVentasCoches.Cliente;
import clasesVentasCoches.Coches;
import clasesVentasCoches.Venta;

public class ClaseBajas {
	/* METODOS PARA REALIZAR LAS BAJAS EN LA BASE DE DATOS
	 * Solicitan el id de la venta, cliente o coche a dar de baja
	 * se carga el objeto, se muestra la informacion del objeto y
	 * se solicita una confirmacion para eliminar el registro
	*/
	
	public static boolean bajaVenta(Scanner sc, Session sesion) {	
		System.out.print(" Introduzca el ID de la venta a dar de baja: ");
		int id = MainVentasCoches.verificarEntradaInt(sc);

		Venta venta = sesion.load(Venta.class, id);
		MainVentasCoches.mostrarDatosOb(venta);
		
		//Pedimos la confirmacion del borrado por medio del metodo
		return confirmacionBorrado(sc, sesion, venta);
	}
	
	public static boolean bajaCliente(Scanner sc, Session sesion) {
		System.out.print(" Introduzca el ID de la venta a dar de baja: ");
		int id = MainVentasCoches.verificarEntradaInt(sc);

		Cliente cliente = sesion.load(Cliente.class, id);
		MainVentasCoches.mostrarDatosOb(cliente);
		
		//Pedimos la confirmacion del borrado por medio del metodo
		return confirmacionBorrado(sc, sesion, cliente);
	}
	
	public static boolean bajaCoche(Scanner sc, Session sesion) {
		System.out.print(" Introduzca el ID de la venta a dar de baja: ");
		int id = MainVentasCoches.verificarEntradaInt(sc);

		Coches coche = sesion.load(Coches.class, id);
		MainVentasCoches.mostrarDatosOb(coche);
		
		//Pedimos la confirmacion del borrado por medio del metodo
		return confirmacionBorrado(sc, sesion, coche);
	}

	//Metodo para la confirmacion de borrado, si el usuario acepta la eliminacion el metodo devolverá un booleano
	//a la clase main para realizar el commit y guardar los cambios
	private static boolean confirmacionBorrado(Scanner sc, Session sesion, Object ob) {
		boolean eliminado = false;
		
		System.out.print(" ¿Está seguro de eliminar este registro? (S/N): ");
		String rp = sc.nextLine();
		
		if(rp.equals("S") || rp.equals("s")) {
			eliminado = true;
			sesion.delete(ob);
			System.out.println("\n El registro se ha eliminado.");
		}else
			System.out.println(" No se ha realizado la operacion");
		
		return eliminado;
	}
}