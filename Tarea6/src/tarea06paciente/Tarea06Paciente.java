package tarea06paciente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;


// @@jm: Notas añadidas por profe.

// @@jm: Clase ha sido revisada y modificada por profe.

public class Tarea06Paciente {

	// @@jm: Fichero donde se guardan los objetos serializados. Modificar según convenga.
	static final String FICHERO = "O:\\Curso Java\\ejercciosAroa\\paciente.dat";
    
	// @@jm: ArrayList para guardar los objetos del fichero
	static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    
	// @@jm
	// ficheroActualizado:  flag que indica si el ArrayList ha sido serializado en fichero despues
	// del último añadido de un paciente.
	// Cada vez que añadamos un paciente se pondrá a false y cada vez que grabemos
	// (serialicemos) los objetos del arraylist listaPacientes en el fichero
	// lo pondremos a true.

	static private boolean ficheroActualizado = true;

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);

		// @@jm:
		// Lee los objetos serializados y los carga en el arrayList listaPacientes.
		// Nota: Cualquier operación de añadir, buscar, etc. se hace sobre el ArrayList.
		// Antes de salir del programa se serializan todos los objetos en el fichero.
		// También se pueden serializar cada vez que se inserta un nuevo paciente en
		// el ArrayList, pero tened en cuenta que cada vez que serializa lo hace
		// con todos los objetos del ArrayList.

		if (leeFicheroPacientes())
			System.out.println("Pacientes cargados en memoria (deserializados)");

		// @@ flag para controlar salida del bucle del menú.
		boolean terminar = false;

		do { // @@jm: Bucle que muestra menú mientras no se seleccione Salir

			// Menu
			System.out.print("\n\nSeleccione su opción\n");
			System.out.print("1.- Añadir datos del paciente\n");
			System.out.print("2.- Listar pacientes\n");
			System.out.print("3.- Buscar paciente\n");
			System.out.print("4.- Borrar fichero de datos\n");
			System.out.print("5.- Salir\n");

			// @@jm: NOTA: Sería conveniente hacer validación de los datos recogidos y
			// controlar excepciones
			// siguiendo estrategía análoga a la utilizada en el ejercicio Movil.
			// Dejo pendiente para el alumno :).

			String opcion = teclado.nextLine();
			int opc = Integer.parseInt(opcion); // ya tenermos la introdución de opciones en números

			switch (opc) { // descripción de las diferentes opciones dependiendo del valor de entrada
			case 1:		// @@jm: Introducir los datos del paciente y crear el fichero
				try {

					System.out.print("Habitación:");
					String habitacion = teclado.nextLine();
					int habnum = Integer.parseInt(habitacion);

					System.out.print("Cama:");
					String cama = teclado.nextLine();
					int camanum = Integer.parseInt(cama);

					System.out.print("Nombre:");
					String nombreint = teclado.nextLine();

					System.out.print("Primer Apellido:");
					String ape1 = teclado.nextLine();

					System.out.print("Segundo Apellido:");
					String ape2 = teclado.nextLine();

					System.out.print("Dieta:");
					String dieta = teclado.nextLine();

					// Creamos el objeto paciente
					Paciente paciente = new Paciente(habnum, camanum, nombreint, ape1, ape2, dieta);

					// @@jm: Lo añadimos al fichero (al ArrayList que representa la copia del
					// fichero en memoria)

					if (nuevoPaciente(paciente)) {
						// @@jm: Flag indica ArrayList pendiente de serializar (grabar en fichero de disco)
						ficheroActualizado = false; 
						System.out.println("Paciente dado de alta.");

						/*
						 * @@jm: Si se quiere serializar aquí tras cada inserción de un nuevo paciente
						 * haríamos: escribeFicheroPacientes(); ficheroActualizado = true; // Esta
						 * instrucción se podría meter dentro de escribeFicheroPacientes()
						 * 
						 * Lo dejo para que seriálice sólo cuando se seleccione opción salir del
						 * programa
						 */

					} else { // @@jm: Ya había un paciente ocupando habitación y cama introducidas
						System.out.println("El paciente no ha podido ser dato de alta.");
						System.out.println(
								"La cama " + paciente.getHabitacion() + "-" + paciente.getCama() + " ya está ocupada");
					}
				} catch (Exception e) { // Getionar si Scanner o parseInt producen excepción
										// @@jm: Lo dicho antes. Tratad de gestionar de forma más efectiva
										// la validación de datos de entrada.

					e.printStackTrace();
				}

				break;

			case 2:     // @@ Lista pacientes si hay y devuelve el número de estos.
				int numPacientes = listarPacientes(); 
				if (numPacientes == 0) {
					System.out.println("No hay ningún paciente.");
				} else {
					System.out.println("Total pacientes: " + numPacientes); // Esto se muestra tras la lista de
																			// pacientes.
				}
				break;

			case 3: // @@ Búsqueda d un paciente a través de su numero de habitación y cama.

				System.out.print("Busque a un paciente. Introduzca número de habitación:");
				String habitacion = teclado.nextLine();
				int habnum = Integer.parseInt(habitacion);

				System.out.println("Introduza el número de cama:");
				String cama = teclado.nextLine();
				int camanum = Integer.parseInt(cama);

				Paciente paciente = buscarPaciente(habnum, camanum); // @@jm: Busca paciente por habitación y cama.
				if (paciente == null) { // @@jm: Metodo devuelve null si no encuentra ninguna.
					System.out.println("El paciente no se encuentra dado de alta.");
				} else {
					System.out.println("Paciente encontrado:");
					System.out.println(paciente.toString()); // @@jm: Método ha devuelto el objeto paciente encontrado
				} // @@jm: Escribe su información
				break;

			case 4:  // @@jm Borrado del fichero

				switch (borraFichero()) { // @@jm: Método devuelve un int en función de lo que ocurra cuando intenta
											// borrar el fichero
				case -1: // @@jm: Estamos usando un switch anidado dentro del de menú.
					System.out.println("El fichero no existe");
					break;

				case -2:
					System.out.println("Se ha producido algún problema al intentar borrar el fichero");
					break;

				default: // @@jm: El fichero se ha borrado
					// @@jm: borraFichero además borra todos los pacientes de ListaPacientes
					// @@jm: por lo que actualiza flag indicando que no hay nada que grabar en caso
					// @@jm: de que ahora salieramos del programa.
					ficheroActualizado = true;

					System.out.println("Fichero borrado");
					break;
				}

			case 5: // Salida

				// @@jm: Antes de salir escribe fichero de pacientes, solo si está pendiente de
				// grabar.
				if (!ficheroActualizado)
					escribeFicheroPacientes();

				terminar = true;
				break;
				
			} // fin swith
		} while (!terminar);
		teclado.close();
	} // fin main()

	// @@jm: usamos comentarios con tags para generar javadoc.

	/**
	 * Borra el fichero {@value #FICHERO}
	 * 
	 * @return -1: fichero no existe -2: fichero no se puede borrar por algún
	 *         problema 1: el fichero es borrado
	 */
	private static int borraFichero() {

		File fichero = new File(FICHERO);
		int borrado = 0;
		try {

			if (!fichero.exists()) {
				borrado = -1;
			} else if (!fichero.delete()) {
				borrado = -2;
			} else
				borrado = 1;

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		if (borrado == 1) { // Borramos también el listArray
			listaPacientes.clear();
		}

		return borrado;

	}

	/**
	 * Busca en {@code Tarea06Paciente.listaPacientes} el paciente cuyos atributos
	 * {@code DatosPaciente.habitacion y DatosPaciente.cama} coinciden con los
	 * parámetros de entrada
	 * 
	 * @param habnum
	 *            número de habitación
	 * @param camanum
	 *            número de cama
	 * @return el objeto DatoPaciente cuya habitación y cama son coincidentes con
	 *         los parámetros de entrada. {@code null} en caso de no exitir
	 *         coincidencia
	 */
	private static Paciente buscarPaciente(int habnum, int camanum) { // @@jm: Consejo: usar "cammel style":
																			// habNum, camNum
		for (Paciente p : listaPacientes) {                           // o "snake style": hab_num, cam_num
			if (p.getHabitacion() == habnum && p.getCama() == camanum)
				return p;
		}

		return null;
	}

	/**
	 * Lista por consola los pacientes contenidos en listaPacientes
	 * 
	 * @return número de elementos de listaPacientes.
	 * 
	 */
	private static int listarPacientes() {
		if (listaPacientes.isEmpty())
			return 0;

		for (Paciente p : listaPacientes)
			System.out.println(p.toString());

		return listaPacientes.size();
	}

	/**
	 * Inserta en listaPacientes un nuevo paciente si cama de la habitación no está
	 * ya ocupada
	 * 
	 * @param paciente
	 *            Nuevo paciente a insertar
	 * @return true Si paciente es insertado false Si paciente no es insertado
	 */
	private static boolean nuevoPaciente(Paciente paciente) {

		if (buscarPaciente(paciente.getHabitacion(), paciente.getCama()) == null) {
			listaPacientes.add(paciente);
			return true;
		}

		return false;
	}

	/**
	 * Serializa los objetos contenidos en listaPacientes en el fichero
	 * {@code #FICHERO}
	 */
	private static void escribeFicheroPacientes() {
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		
		

		try {
			fileOut = new FileOutputStream(FICHERO);
			out = new ObjectOutputStream(fileOut);
			for (Paciente paciente : listaPacientes) {
				out.writeObject(paciente);
			}
			System.out.printf("Datos serializados en fichero " + FICHERO);
		} catch (IOException i) {
			System.out.println("Se ha producido error IOException");
		} finally {
			try {
				if (out != null)
					out.close();
				if (fileOut != null)
					fileOut.close();

			} catch (IOException e) {
				System.out.println("Se ha producido error IOException");
			}
		}
	}
	
	// Versión que serializa el ArrayList listapacientes de una tacada,
	// no objeto a objeto.
	// Esto lo podemos hacer porque ArrayList implementa Serialize
	
	private static void escribeFicheroPacientesV2() {
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		String fichero = cambiaNombre(FICHERO);
		try {
			fileOut = new FileOutputStream(fichero);
			out = new ObjectOutputStream(fileOut);
			out.writeObject(listaPacientes);
			System.out.printf("Datos serializados en fichero " + fichero);
		} catch (IOException i) {
			System.out.println("Se ha producido error IOException");
		} finally {
			try {
				if (out != null)
					out.close();
				if (fileOut != null)
					fileOut.close();

			} catch (IOException e) {
				System.out.println("Se ha producido error IOException");
			}
		}
	}

	/**
	 * De-serializa objetos guardados en el fichero {@code Tarea06Paciente#FICHERO}
	 * y los carga en listaPacientes
	 */
	private static boolean leeFicheroPacientes() {
		Paciente paciente = null;
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		boolean datosDeserialzados = true;
		try {

			fileIn = new FileInputStream(FICHERO);
			in = new ObjectInputStream(fileIn);
			while ((paciente = (Paciente) in.readObject()) != null) {
				listaPacientes.add(paciente);
			}

		} catch (EOFException eo) {
			// No pasa nada. Esta excepción se produce cuando se llega al final del fichero.
		} catch (FileNotFoundException i) {
			System.out.println("AVISO: No se ha encontrado el archivo " + FICHERO);
			System.out.println("Se comienza sesión sobre fichero nuevo.");
			datosDeserialzados = false;
		} catch (IOException e) {
			System.out.println("Error de Escritura/Lectura en archivo " + FICHERO);
			datosDeserialzados = false;
		} catch (ClassNotFoundException c) {
			System.out.println("Clase Paciente no encontrada");
			c.printStackTrace();
			datosDeserialzados = false;

		} finally { // Esto se ejecuta tanto si el try falla como si no
			try {
				if (in != null)
					in.close();
				if (fileIn != null)
					fileIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return datosDeserialzados;
	}
	
	// Versión que deserializa el ArrayList de una tacada
	// Posible ya que ArrayList es serializable (implementa serialize)
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	private static boolean leeFicheroPacientesV2() {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		boolean datosDeserialzados = true;
		String fichero = cambiaNombre(FICHERO);

		try {

			fileIn = new FileInputStream(fichero);
			in = new ObjectInputStream(fileIn);
			listaPacientes = (ArrayList<Paciente>) in.readObject();
			
		} catch (EOFException eo) {
			// No pasa nada. Esta excepción se produce cuando se llega al final del fichero.
		} catch (FileNotFoundException i) {
			System.out.println("AVISO: No se ha encontrado el archivo " + fichero);
			System.out.println("Se comienza sesión sobre fichero nuevo.");
			datosDeserialzados = false;
		} catch (IOException e) {
			System.out.println("Error de Escritura/Lectura en archivo " + fichero);
			datosDeserialzados = false;
		} catch (ClassNotFoundException c) {
			System.out.println("Clase DatoPaciente no encontrada");
			c.printStackTrace();
			datosDeserialzados = false;

		} finally { // Esto se ejecuta tanto si el try falla como si no
			try {
				if (in != null)
					in.close();
				if (fileIn != null)
					fileIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return datosDeserialzados;
	}
	
	
	// Le pone v2 al nombre del fichero antes del punto de la extensión.
	private static String cambiaNombre(String s) {
		String [] a = s.split("\\.");
		return a[0] + "v2" + "." + a[1];
	}


}
