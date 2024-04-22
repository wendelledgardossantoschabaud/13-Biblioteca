package es.rodal.biblioteca.bbdd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import es.rodal.biblioteca.models.Documento;
import es.rodal.biblioteca.models.Libro;
import es.rodal.biblioteca.models.Revista;
import es.rodal.biblioteca.models.TipoDocumento;
import es.rodal.biblioteca.models.TipoUsuario;
import es.rodal.biblioteca.models.Usuario;

public class Consultas {

	private static final String CONSULTA_USUARIO_POR_DNI = "SELECT id_usuario, nombre, apellidos, tipo_usuario FROM usuarios WHERE dni = ?";
	private static final String CONSULTA_DOCUMENTO_POR_ID = "SELECT * FROM documentos WHERE id_documento = ?";
	private static final String INSERT_USUARIO = "INSERT INTO usuarios(dni, nombre, apellidos, tipo_usuario) VALUES (?, ?, ?, ?)";
	private static final String INSERT_LIBRO = "INSERT INTO documentos(id_documento, titulo, anho_publicacion, tipo_documento) VALUES (?, ?, ?, ?)";
	private static final String INSERT_REVISTA = "INSERT INTO documentos(id_documento, titulo, tipo_documento) VALUES (?, ?, ?)";
	private static final String INSERT_PRESTAMO = "INSERT INTO prestamos(id_usuario, id_documento, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";

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

	public static void insertDocumento(Documento documento) throws SQLException {

		String insert = documento instanceof Libro ? INSERT_LIBRO : INSERT_REVISTA;

		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(insert);) {
			statement.setString(1, documento.getId_documento());
			statement.setString(2, documento.getTitulo());
			if (documento instanceof Libro ) {
				statement.setInt(3, ((Libro) documento).getAnhoPublicacion());
				statement.setString(4, TipoDocumento.LIBRO.name());
			} else {
				statement.setString(3, TipoDocumento.REVISTA.name());
			}

			statement.executeUpdate();
		}
	}

	public static Documento findDocumento(String id_documento) throws SQLException {
		Documento documento = null;

		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(CONSULTA_DOCUMENTO_POR_ID);) {
			statement.setString(1, id_documento);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					if (resultSet.getString("tipo_documento").equalsIgnoreCase(TipoDocumento.LIBRO.name())) {
						documento = new Libro();
					} else {
						documento = new Revista();
					}
					documento.setId_documento(id_documento);
					documento.setTitulo(resultSet.getString("titulo"));
					if (documento instanceof Libro) {
						((Libro) documento).setAnhoPublicacion(resultSet.getInt("anho_publicacion"));
					}
					
				}
			}
		}
		return documento;
	}
	
	public static void insertPrestamo(Usuario usuario, Documento documento, LocalDate fecha) throws SQLException {
		
		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(INSERT_PRESTAMO);) {
			statement.setInt(1, usuario.getId_usuario());
			statement.setString(2, documento.getId_documento());
			statement.setDate(3, Date.valueOf(fecha));
			System.out.println(usuario.getClass().getSimpleName());
			statement.setDate(4, Date.valueOf(usuario.fechaDevolucion(fecha, documento)));
			statement.executeUpdate();
		}
	}
}
