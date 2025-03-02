
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class GeneradorArchivosEmpleados {

	/* HECHO POR ÁNGEL CHAVEZ 2º DAM */
	public static void main(String[] args) {
		iniciarPrograma();
	}

	private static void iniciarPrograma() {
		System.out.println("\t******************************************************");
		System.out.println("\t******************* ELIGE TU CESTA *******************");
		System.out.println("\t******************************************************");
		System.out.println("\nTe damos la bienvenida a nuestro programa de generación de ficheros :)");
		boolean fin = false;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("\n\t********************* MENU *************************");
			System.out.println("\t 1. Generar archivo DAT");
			System.out.println("\t 2. Generar archivo XML");
			System.out.println("\t 3. Finalizar el programa");
			System.out.print("\nIntroduce la opción que necesites: ");
			int opUsuario = 0;

			try {
				opUsuario = sc.nextInt();

				switch (opUsuario) {
				case 1:
					generaArchivoDat();
					break;

				case 2:
					generaArchivoXml();
					break;

				case 3:
					System.out.println("El programa ha terminado");
					fin = true;
					break;

				default:
					System.out.println("Opción elegida no encontrada. Intentalo de nuevo");
					break;
				}

			} catch (InputMismatchException e) {
				System.out.println("El valor que ha introducido no es válido");
			}
		} while (!fin);
		sc.close();
	}

	private static void generaArchivoDat() {
		Scanner sc = new Scanner(System.in);
		File fichero = new File("./Practica1/Incorporaciones/fichero.txt");
		File carp = new File("./Practica1/Empleados");
		carp.mkdir();
		System.out.println("Se ha seleccionado la opcion 1\n");
		System.out.println("Para esta operación se usará el archivo " + fichero.getPath());
		System.out
				.print("Ingrese un nombre para el nuevo archivo a generar (incluya la extension .dat en el nombre): ");
		String nomNuevoFichero = sc.nextLine();
		File nvFichero = new File(carp.getPath() + "/" + nomNuevoFichero);

		if (!nvFichero.exists()) {
			try {
				BufferedReader bfReader = new BufferedReader(new FileReader(fichero));
				ObjectOutputStream obStream = new ObjectOutputStream(new FileOutputStream(nvFichero));
				String linea = "";

				// Usamos el bufferReader para leer una línea del documento y luego accceder a
				// los datos mediante un split
				while ((linea = bfReader.readLine()) != null) {
					String[] datos = linea.split("#");
					Empleado emp = asignaDatos(datos);
					obStream.writeObject(emp);
				}

				System.out.println("El archivo " + nvFichero.getName() + " se ha creado correctamente en la carpeta "
						+ carp.getPath());
				bfReader.close();
				obStream.close();
			} catch (FileNotFoundException e) {
				System.out.println("El archivo .txt no se ha encontrado" + e.getMessage());
			} catch (IOException e) {
				System.out.println("Ha ocurrido un error en la entrada/salida");
			}
		} else
			System.out.println("El archivo ya ha sido creado anteriormente");
	}

	private static void generaArchivoXml() {
		Scanner sc = new Scanner(System.in);
		File carp = new File("./Practica1/Empleados");
		System.out.println("Se ha seleccionado la opcion 2");

		if (carp.exists()) { // Si la carpeta Empleados existe se podrá generar el archivo XML
			System.out.println("\nLista del directorio " + carp.getName());
			String[] listFicheros = carp.list();

			// Listamos los ficheros dat por si en un futuro tenemos otro fichero que
			// tambien se necesite usar
			for (int i = 0; i < listFicheros.length; i++)
				System.out.println((i + 1) + ". " + listFicheros[i]);

			System.out.print("\nSelecciona el archivo que quieres convertir a XML: ");
			int opUsuario = sc.nextInt();

			File fich = new File(carp.getPath() + "/" + listFicheros[opUsuario - 1]);
			System.out.println("\nElige los empleados a filtrar en el XML");
			System.out.println("1. Empleados menores de 25");
			System.out.println("2. Empleados mayores de 55");
			System.out.print("\nOpcion: ");
			int filtroUsuario = sc.nextInt();

			switch (filtroUsuario) {
			case 1:
				estructuraXML(fich, generarListaMenores25(fich));
				break;

			case 2:
				estructuraXML(fich, generarListaMayores55(fich));
				break;
			}

		} else
			System.out.println("Debes generar el archivo .dat antes de poder generar el XML");
	}

	private static Empleado asignaDatos(String[] datos) {
		// Recibe un array con los datos de un empleado y los guardamos en un objeto
		// Empleado
		int id = Integer.parseInt(datos[0]);
		String nombre = datos[1];
		String apellido1 = datos[2];
		String apellido2 = datos[3];
		int edad = Integer.parseInt(datos[4]);
		char sexo = datos[5].charAt(0);
		String telefono = datos[6];
		Double salario = Double.parseDouble(datos[7].replace(",", "."));
		String dni = datos[8];

		Empleado nvEmpleado = new Empleado(id, nombre, apellido1, apellido2, edad, sexo, telefono, salario, dni);
		return nvEmpleado;
	}

	private static void estructuraXML(File fich, ArrayList<Empleado> lista) {
		// Dentro de este metodo implementamos la estructura para crear un XML

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document documento = implementation.createDocument(null, "Empleados", null);
			documento.setXmlVersion("1.0");

			// Recorremos la lista de empleados segun el filtro y los vamos adjuntando en el
			// XML virtual
			for (Empleado emp : lista)
				crearNodoEmpleado(documento, emp);

			File carp = new File("./Practica1/Subvenciones");
			carp.mkdir();
			Scanner sc = new Scanner(System.in);

			System.out.print("\nIngrese un nombre para el archivo XML (incluya la extension .xml en el nombre): ");
			String nomNvFichero = sc.nextLine();
			File nvFichero = new File(carp.getPath() + "/" + nomNvFichero);

			DOMSource source = new DOMSource(documento);
			StreamResult result = new StreamResult(nvFichero);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			System.out.println("El archivo se ha creado correctamente en la carpeta " + carp.getPath());

		} catch (ParserConfigurationException e) {
			System.out.println("Error al configurar el parser");
		} catch (TransformerConfigurationException e) {
			System.out.println("Error al configurar el transformador XML");
		} catch (TransformerFactoryConfigurationError e) {
			System.out.println("Error al configurar la fábrica de transformadores XML");
		} catch (TransformerException e) {
			System.out.println("Error al transformar el documento XML");
		}
	}

	private static ArrayList<Empleado> generarListaMenores25(File fich) {
		ArrayList<Empleado> lista = new ArrayList<Empleado>();
		try {
			ObjectInputStream obStream = new ObjectInputStream(new FileInputStream(fich));
			boolean fin = false;

			while (!fin) {
				try {
					Empleado emp = (Empleado) obStream.readObject();
					if (emp.getEdad() < 25)
						lista.add(emp);
				} catch (EOFException e) {
					obStream.close();
					fin = true;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lista;
	}

	private static ArrayList<Empleado> generarListaMayores55(File fich) {
		ArrayList<Empleado> lista = new ArrayList<Empleado>();
		try {
			ObjectInputStream obStream = new ObjectInputStream(new FileInputStream(fich));
			boolean fin = false;

			while (!fin) {
				try {
					Empleado emp = (Empleado) obStream.readObject();
					if (emp.getEdad() > 55)
						lista.add(emp);
				} catch (EOFException e) {
					obStream.close();
					fin = true;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lista;
	}

	private static void crearNodoEmpleado(Document documento, Empleado emp) {
		Element raiz = documento.createElement("Empleado");
		documento.getDocumentElement().appendChild(raiz);
		crearElemento("dni", emp.getDni(), raiz, documento);
		crearElemento("nombre", emp.getNombre(), raiz, documento);
		crearElemento("apellidos", emp.getApellido1().concat(" " + emp.getApellido2()), raiz, documento);
		crearElemento("edad", Integer.toString(emp.getEdad()), raiz, documento);
		crearElemento("telefono", emp.getTelefono(), raiz, documento);
		crearElemento("sexo", ("" + emp.getSexo()), raiz, documento);
		crearElemento("salario", Double.toString(emp.getSalario() * 14), raiz, documento);
	}

	private static void crearElemento(String nombreDato, String valor, Element raiz, Document document) {
		Element elem = document.createElement(nombreDato);
		Text texto = document.createTextNode(valor);
		raiz.appendChild(elem);
		elem.appendChild(texto);
	}
}