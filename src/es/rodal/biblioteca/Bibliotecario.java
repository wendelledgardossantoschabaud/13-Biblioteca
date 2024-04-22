package es.rodal.biblioteca;

import java.sql.SQLException;

public class Bibliotecario {
	public static void main(String[] args) {

		Usuario usuario = new Usuario("00000001T", "Pedro", "Pedrin");
		
		Consultas consulta = new Consultas();
	
		try {
			consulta.insertUsuario(usuario);
			Usuario pedro = consulta.findDni("0000000T");
			
			System.out.println(pedro);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
