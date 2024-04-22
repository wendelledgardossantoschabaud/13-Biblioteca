package es.rodal.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Consultas {
	public Usuario findDni(String dni) throws SQLException {
	    Usuario usuario = null;

	    try (
	        Connection connection = Conexion.conectar();
	        PreparedStatement statement = connection.prepareStatement("SELECT id_usuario, nombre, apellidos FROM usuarios WHERE dni=?");
	    ) {
	        statement.setString(1, dni);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                usuario = new Usuario();
	                usuario.setDni(dni);
	                usuario.setId_usuario(resultSet.getInt("id_usuario"));
	                usuario.setNombre(resultSet.getString("nombre"));
	                usuario.setApellidos(resultSet.getString("apellidos"));
	            }
	        }
	    }       
	    return usuario;
	}
	
	public void insertUsuario(Usuario usuario) throws SQLException {
		try (
		        Connection connection = Conexion.conectar();
		        PreparedStatement statement = connection.prepareStatement("INSERT INTO usuarios(dni, nombre, apellidos) VALUES (?, ?, ?)");
		    ) {
			statement.setString(1, usuario.getDni());
			statement.setString(2, usuario.getNombre());
			statement.setString(3, usuario.getApellidos());
			
			statement.executeUpdate();
		}
		
	}
}
