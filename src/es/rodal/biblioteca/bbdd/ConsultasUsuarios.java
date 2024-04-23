package es.rodal.biblioteca.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rodal.biblioteca.models.TipoUsuario;
import es.rodal.biblioteca.models.Usuario;

public class ConsultasUsuarios {

	private static final String CONSULTA_USUARIO_POR_DNI = "SELECT id_usuario, nombre, apellidos, tipo_usuario FROM usuarios WHERE dni = ?";
	private static final String INSERT_USUARIO = "INSERT INTO usuarios(dni, nombre, apellidos, tipo_usuario) VALUES (?, ?, ?, ?)";
	private static final String DELETE_USUARIO = "DELETE FROM usuarios WHERE dni = ?";

	/**
	 * Método que inserta un usuario proporcionado como parametro
	 * @param usuario
	 * @throws SQLException
	 */
	public static void insertUsuario(Usuario usuario) throws SQLException {
		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(INSERT_USUARIO);) {
			statement.setString(1, usuario.getDni());
			statement.setString(2, usuario.getNombre());
			statement.setString(3, usuario.getApellidos());
			statement.setString(4, usuario.getTipo_usuario().name());
			statement.executeUpdate();
		}
	}

	/**
	 * Método que busca un usuario en la bbdd a partir de un dni, crea un objeto Usuario y lo devuelve
	 * @param dni
	 * @return usuario
	 * @throws SQLException
	 */
	public static Usuario findDni(String dni) throws SQLException {
		Usuario usuario = null;

		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(CONSULTA_USUARIO_POR_DNI);) {
			statement.setString(1, dni);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					usuario = new Usuario();
					usuario.setDni(dni);
					usuario.setId_usuario(resultSet.getInt("id_usuario"));
					usuario.setNombre(resultSet.getString("nombre"));
					usuario.setApellidos(resultSet.getString("apellidos"));
					usuario.setTipo_usuario(TipoUsuario.valueOf(resultSet.getString("tipo_usuario").toUpperCase()));
				}
			}
		}
		return usuario;
	}

	/**
	 * Método que elimina un usuario de la bbdd a partir de un dni proporcionado pero solo se
	 * ejecutará si este usuario no tiene ningun documento prestado
	 * @param dni
	 * @throws SQLException
	 */
	public static void deleteDni(String dni) throws SQLException {
		Usuario usuarioDni = findDni(dni);
		if (ConsultasPrestamos.contarPrestados(usuarioDni) > 0) {
			System.out.println("ERROR. No se puede eliminar un usuario que no haya devuelto todos los documentos!!!");
		} else {
			try (Connection connection = Conexion.conectar();
					PreparedStatement statement = connection.prepareStatement(DELETE_USUARIO);) {
				statement.setString(1, dni);
				statement.execute();
			}
		}
	}
	
	/**
	 * Método que comprueba si el Usuario proporcionado puede tomar prestados mas documentos 
	 * o ya ha alcanzado su limite (20 para socios y 2 para ocasionales)
	 * @param usuario
	 * @return
	 * @throws SQLException
	 */
	public static boolean tomarPrestado (Usuario usuario) throws SQLException {
		boolean puede = false;
		if (ConsultasPrestamos.contarPrestados(usuario) < usuario.getTipo_usuario().getMaxDocumentos()) {
			puede = true;
		}
		return puede;
	}
}
