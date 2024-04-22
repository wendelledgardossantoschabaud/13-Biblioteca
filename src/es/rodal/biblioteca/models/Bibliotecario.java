package es.rodal.biblioteca.models;

import java.sql.SQLException;
import java.time.LocalDate;

import es.rodal.biblioteca.bbdd.Consultas;

public class Bibliotecario {
	public static void main(String[] args) {

		try {

			Usuario pedro = Consultas.findDni("00000001T");
			Usuario pedroSocio = Consultas.findDni("00000000T");

			Documento libro = Consultas.findDocumento("AA00");
			Documento revista = Consultas.findDocumento("R01230");
			
			System.out.println(pedroSocio);
			
			Consultas.insertPrestamo(pedro, revista, LocalDate.now());
			Consultas.insertPrestamo(pedro, libro, LocalDate.now());
			
			Consultas.insertPrestamo(pedroSocio, revista, LocalDate.now());
			Consultas.insertPrestamo(pedroSocio, libro, LocalDate.now());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
