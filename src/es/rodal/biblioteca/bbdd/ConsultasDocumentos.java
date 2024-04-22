package es.rodal.biblioteca.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rodal.biblioteca.models.Documento;
import es.rodal.biblioteca.models.Libro;
import es.rodal.biblioteca.models.Revista;
import es.rodal.biblioteca.models.TipoDocumento;

public class ConsultasDocumentos {

	private static final String CONSULTA_DOCUMENTO_POR_ID = "SELECT * FROM documentos WHERE id_documento = ?";
	private static final String INSERT_LIBRO = "INSERT INTO documentos(id_documento, titulo, anho_publicacion, tipo_documento) VALUES (?, ?, ?, ?)";
	private static final String INSERT_REVISTA = "INSERT INTO documentos(id_documento, titulo, tipo_documento) VALUES (?, ?, ?)";
	private static final String DELETE_DOCUMENTO = "DELETE FROM documentos WHERE id_documento = ?";
	private static final String DOCUMENTO_PRESTADO = "SELECT disponible FROM documentos WHERE id_documento = ?";
	
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
	
	
	public static void deleteDocumento(String id_documento) throws SQLException {
		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(DELETE_DOCUMENTO);) {
			
			statement.setString(1, id_documento);
			statement.execute();
		}
	}
	
	
	public static boolean documentoDisponible (Documento documento) throws SQLException {
		try(Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(DOCUMENTO_PRESTADO);) {
			
			statement.setString(1, documento.getId_documento());
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					if (resultSet.getInt("disponible") != 0) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
