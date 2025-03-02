package aplicacion;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import clasesVentasCoches.*;

public class MainVentasCoches {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		
		while (!fin) {
			System.out.println("\n\t============= VENTAS COCHES =============\n");
			System.out.println(" 1. Altas");
			System.out.println(" 2. Bajas");
			System.out.println(" 3. Modificaciones");
			System.out.println(" 4. Informes");
			System.out.println(" 5. Salir");
			System.out.print("\n Introduce una opcion: ");

			int opUser = verificarEntradaInt(sc);

			switch (opUser) {
				case 1:
					opAltas(sc);
					break;
	
				case 2:
					opBajas(sc);
					break;
	
				case 3:
					opModificaciones(sc);
					break;
	
				case 4:
					opInformes(sc);
					break;
	
				case 5:
					System.out.println("\n\t\t ** La aplicacion ha terminado **");
					fin = true;
					break;
					
				default:
					System.out.println("\n * Opcion no encontrada *");
			}
		}
		
		System.exit(0);
		sc.close();
	}

	/*METODOS PARA REALIZAR LAS OPERACIONES DEL PROGRAMA
	 * Solicitan al usuario que elija una opcion y en caso de que el usuario introduzca una opcion valida
	 * se abrirá una sesion y una transaccion para poder enviar los nuevos datos a la base de datos
	*/
	public static void opAltas(Scanner sc) {
		System.out.println("\n\t============= ALTAS =============\n");
		System.out.println(" 1. Alta de venta");
		System.out.println(" 2. Alta de cliente");
		System.out.println(" 3. Alta de vehiculo");
		System.out.print("\n Introduce una opcion: ");
		int opUser = verificarEntradaInt(sc);
		
		if(opUser >= 1 && opUser <= 3) {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session sesion = sf.openSession();
			
			Transaction tx = sesion.beginTransaction();
			
			boolean txCommit = false;
			
			try {	
				switch (opUser) {
					case 1:
						System.out.println("\n · Alta de venta");
						txCommit = ClaseAltas.altaVenta(sc, sesion);
					
						break;
					
					case 2:
						System.out.println("\n · Alta de cliente");
						txCommit = ClaseAltas.altaCliente(sc, sesion);
						break;
						
					case 3:
						System.out.println("\n · Alta de coche");
						txCommit = ClaseAltas.altaCoche(sc, sesion);
						
						break;	
				}
				
				if(txCommit) {
					tx.commit();
					System.out.println(" La operación se ha realizado correctamente.");
				}else {
					tx.rollback();
					System.out.println(" La operación no se ha realizado.");
				}
			
			}catch(ObjectNotFoundException e) {
				System.out.println(" El registro introducido no existe. No es posible continuar.");
				tx.rollback();
			}finally {
				sesion.close();
			}
		}else
			System.out.println("\n * Opcion no encontrada *");
	}
	
	public static void opBajas(Scanner sc) {
		System.out.println("\n\t============= BAJAS =============\n");
		System.out.println(" 1. Baja de venta");
		System.out.println(" 2. Baja de cliente");
		System.out.println(" 3. Baja de vehiculo");
		System.out.print("\n Introduce una opcion: ");
		int opUser = verificarEntradaInt(sc);
		
		if(opUser >= 1 && opUser <= 3) {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session sesion = sf.openSession();
			
			Transaction tx = sesion.beginTransaction();
			
			boolean txCommit = false;
			
			try {
				switch (opUser) {
					case 1:
						System.out.println("\n · Baja de venta");
						txCommit = ClaseBajas.bajaVenta(sc, sesion);
											
						break;
			
					case 2:
						System.out.println("\n · Baja de cliente");
						txCommit = ClaseBajas.bajaCliente(sc, sesion);
											
						break;
			
					case 3:
						System.out.println("\n · Baja de vehiculo");
						txCommit = ClaseBajas.bajaCoche(sc, sesion);
											
						break;
				}
				
				if(txCommit) {
					tx.commit();
					System.out.println(" La operación se ha realizado correctamente.");
				}else {
					tx.rollback();
					System.out.println(" La operación no se ha realizado.");
				}
				
			}catch(ObjectNotFoundException e) {
				System.out.println(" El registro introducido no existe. No es posible continuar.");
				tx.rollback();
			}finally {
				sesion.close();
			}
		}else
			System.out.println("\n * Opcion no encontrada *");
	}

	public static void opModificaciones(Scanner sc) {
		System.out.println("\n\t============= MODIFICACIONES =============\n");
		System.out.println(" 1. Modificación de venta");
		System.out.println(" 2. Modificación de cliente");
		System.out.println(" 3. Modificación de vehiculo");
		System.out.print("\n Introduce una opcion: ");
		
		int opUser = verificarEntradaInt(sc);
		
		if(opUser >= 1 && opUser <= 3) {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session sesion = sf.openSession();
			
			Transaction tx = sesion.beginTransaction();
			boolean txCommit = false;
			
			try {
				
				switch (opUser) {
					case 1:
						System.out.println("\n · Modificación de venta");
						txCommit = ClaseModificaciones.modificaVenta(sc, sesion);
											
						break;
			
					case 2:
						System.out.println("\n · Modificación de cliente");
						txCommit = ClaseModificaciones.modificaCliente(sc, sesion);
											
						break;
			
					case 3:
						System.out.println("\n · Modificación de vehiculo");
						txCommit = ClaseModificaciones.modificaCoche(sc, sesion);
											
						break;
				}
				
				if(txCommit) {
					tx.commit();
					System.out.println(" La operación se ha realizado correctamente.");
				}else {
					tx.rollback();
					System.out.println(" La operación no se ha realizado.");
				}
				
			}catch(ObjectNotFoundException e) {
				System.out.println(" El registro introducido no existe. No es posible continuar.");
				tx.rollback();
			}finally {
				sesion.close();
			}
		}else
			System.out.println("\n * Opcion no encontrada *");	
	}

	public static void opInformes(Scanner sc) {
		System.out.println("\n\t============= INFORMES =============\n");
		System.out.println(" 1. Datos de los clientes por ciudad");
		System.out.println(" 2. Datos de los vehiculos por numero de plazas");
		System.out.println(" 3. Datos de las ventas por rango de fechas");
		System.out.println(" 4. Consultas genéricas");
		System.out.print("\n Introduce una opcion: ");
		
		int opUser = verificarEntradaInt(sc);

		if(opUser >= 1 && opUser <= 4) {
			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session sesion = sf.openSession();
			
			try {
				
				switch (opUser) {
					case 1:
						System.out.println("\n · Datos de los clientes por ciudad");
						ClaseInformes.datosClienteCiudad(sc, sesion);
											
						break;
			
					case 2:
						System.out.println("\n · Datos de los vehiculos por numero de plazas");
						ClaseInformes.datosCochePlazas(sc, sesion);
											
						break;
			
					case 3:
						System.out.println("\n · Datos de las ventas por rango de fechas");
						ClaseInformes.datosVentasRangoFechas(sc, sesion);
											
						break;
						
					case 4:
						System.out.println("\n · Consultas genéricas");
						ClaseInformes.consultasGenericas(sc, sesion);
						
						break;
				}
			}catch(ObjectNotFoundException e) {
				System.out.println(" El registro introducido no existe. No es posible continuar.");
			}finally {
				sesion.close();
			}
		}else
			System.out.println("\n * Opcion no encontrada *");	
	}
	
	
	//Este metodo recibe objetos de tipo Object y 
	//segun su instancia mostrará sus respectivos datos
	public static void mostrarDatosOb(Object ob) {
		if(ob instanceof Venta) {
			Venta v = (Venta) ob;
			
			System.out.println("\n\t* DATOS VENTA *\n");
			System.out.println(" - CLIENTE: " + v.getCliente().getNombre());
			System.out.println(" - COCHE: " + v.getCoches().getMatricula());
			System.out.println(" - FECHA COMPRA: " + v.getFechaCompra());
			System.out.println(" - FECHA ENTREGA (ESTIMADA): " + v.getFechaEntrega());
			System.out.println(" - PRECIO: " + v.getPrecio() + "\n");
		}
		
		else if(ob instanceof Cliente) {
			Cliente c = (Cliente) ob;
			
			System.out.println("\n* DATOS CLIENTE *\n");
			System.out.println(" - DNI: " + c.getDni());
			System.out.println(" - NOMBRE: " + c.getNombre());
			System.out.println(" - DIRECCION: " + c.getDireccion());
			System.out.println(" - CIUDAD: " + c.getCiudad());
			System.out.println(" - TELEFONO: " + c.getTelefono());
			System.out.println(" - EMAIL: " + c.getEmail());
			System.out.println(" - EDAD: " + c.getEdad());
			System.out.println(" - SEXO: " + c.getSexo() + "\n");
		}
		
		else if(ob instanceof Coches) {
			Coches c = (Coches) ob;
			
			System.out.println("\n* DATOS COCHE *\n");
			System.out.println(" - MARCA: " + c.getMarca());
			System.out.println(" - MODELO: " + c.getModelo());
			System.out.println(" - COLOR: " + c.getColor());
			System.out.println(" - MATRICULA: " + c.getMatricula());
			System.out.println(" - PLAZAS: " + c.getPlazas());
			System.out.println(" - EXTRAS: " + c.getExtras() + "\n");
		}
	}
	
	//Este metodo verifica que la entrada del usuario sea un entero y no de otro tipo º
	public static int verificarEntradaInt(Scanner sc) {
		int opUsuario = 0;
		
		try{
			opUsuario = sc.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("\n * El dato introducido no está permitido * ");
			opUsuario = 0;
		}finally {
			sc.nextLine();
		}
	
		return opUsuario;
	}
}