package es.rodal.biblioteca.main;

import java.sql.SQLException;
import java.time.LocalDate;

import es.rodal.biblioteca.bbdd.ConsultasDocumentos;
import es.rodal.biblioteca.bbdd.ConsultasPrestamos;
import es.rodal.biblioteca.bbdd.ConsultasUsuarios;
import es.rodal.biblioteca.models.Documento;
import es.rodal.biblioteca.models.Libro;
import es.rodal.biblioteca.models.Revista;
import es.rodal.biblioteca.models.Usuario;

public class Bibliotecario {
	public static void main(String[] args) {

		try {

			Usuario pedro = ConsultasUsuarios.findDni("12312312A");
			Usuario pedroSocio = ConsultasUsuarios.findDni("00000000T");

			Documento libro = new Libro("AA02", "Pedro y pedritos volumen 2", 2020);
			Documento revista = new Revista("R01232", "MaxiPedro2");
			
			//ConsultasDocumentos.insertDocumento(libro);
			//ConsultasDocumentos.insertDocumento(revista);

			ConsultasPrestamos.insertPrestamo(pedroSocio, revista, LocalDate.now());
			ConsultasPrestamos.insertPrestamo(pedroSocio, libro, LocalDate.now());
			//Consultas.insertPrestamo(pedro, libro, LocalDate.now());
			
			//Consultas.devolverDocumento(pedro, libro, LocalDate.now());
		
			//System.out.println(ConsultasPrestamos.contarPrestados(pedroSocio));
			//ConsultasUsuarios.deleteDni(pedroSocio.getDni());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
