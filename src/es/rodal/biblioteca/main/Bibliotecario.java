package es.rodal.biblioteca.main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import es.rodal.biblioteca.bbdd.ConsultasDocumentos;
import es.rodal.biblioteca.bbdd.ConsultasPrestamos;
import es.rodal.biblioteca.bbdd.ConsultasUsuarios;
import es.rodal.biblioteca.models.Documento;
import es.rodal.biblioteca.models.Usuario;

public class Bibliotecario {
	public static void main(String[] args) throws SQLException {

		Scanner scan = new Scanner(System.in);
		boolean salir = false;
		int opcion;
		
		Documento documentoSeleccionado = null;

		while (!salir) {

			System.out.println("\n1. Seleccionar documento");
			System.out.println("2. Prestar documento actual");
			System.out.println("3. Devuelve documento actual");
			System.out.println("4. Busca documentos");
			System.out.println("5. Informe prestamos");
			System.out.println("6. Salir");
			
			try {
				 
                System.out.println("Escribe una de las opciones");
                opcion = scan.nextInt();
                scan.nextLine();
 
                switch (opcion) {
                    case 1://Selecciona un documento
                        System.out.println("Introduce el id del documento que quieras seleccionar: ");
                        documentoSeleccionado = ConsultasDocumentos.findDocumento(scan.nextLine());
                        break;
                    case 2://Crea un prestamo si es posible
                    	if (documentoSeleccionado != null) {
                    		System.out.println("Introduce dni: ");
                    		Usuario usuario = ConsultasUsuarios.findDni(scan.nextLine());
                    		ConsultasPrestamos.insertPrestamo(usuario, documentoSeleccionado, LocalDate.now());
                    	} else {
                    		System.out.println("Primero es necesario seleccionar un documento");
                    	}
                        break;
                    case 3://Devuelve el prestamo
                    	if (documentoSeleccionado != null) {
                    		System.out.println("Introduce dni: ");
                    		Usuario usuario = ConsultasUsuarios.findDni(scan.nextLine());
                    		ConsultasPrestamos.devolverDocumento(usuario, documentoSeleccionado, LocalDate.now());
                    	} else {
                    		System.out.println("Primero es necesario seleccionar un documento");
                    	}
                        break;
                    case 4://Busca documento por titulo
                        System.out.println("Introduce titulo: ");
                        documentoSeleccionado = ConsultasDocumentos.findTitulo(scan.nextLine());
                        System.out.println(documentoSeleccionado);
                        break;
                    case 5://Devuelve informe prestamos no devueltos
                        System.out.println("Prestamos no devueltos:");
                        ConsultasPrestamos.informePrestados();
                        break;
                    case 6:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 6");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                scan.next();
            }
		}
		scan.close();
	}
}
