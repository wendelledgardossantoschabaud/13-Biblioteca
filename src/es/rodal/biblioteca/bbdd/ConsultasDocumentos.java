package es.rodal.biblioteca.bbdd;

import java.nio.file.ProviderNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.rodal.biblioteca.models.Documento;
import es.rodal.biblioteca.models.Libro;
import es.rodal.biblioteca.models.Revista;
import es.rodal.biblioteca.models.TipoDocumento;

public class ConsultasDocumentos {

	private static final String INSERT_LIBRO = "INSERT INTO documentos(id_documento, titulo, anho_publicacion, tipo_documento) VALUES (?, ?, ?, ?)";
	private static final String INSERT_REVISTA = "INSERT INTO documentos(id_documento, titulo, tipo_documento) VALUES (?, ?, ?)";
	private static final String DOCUMENTO_POR_TITULO = "SELECT * FROM documentos WHERE titulo LIKE ?;";
	
	public static Documento findDocumentoPorTitulo(String titulo) {
		Documento documento = null;
		try (Connection conn = Conexion.conectar();
				PreparedStatement ps = conn.prepareStatement(DOCUMENTO_POR_TITULO);
				){
			ps.setString(1, titulo);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				TipoDocumento tipo = TipoDocumento.valueOf(rs.getString("tipo_documento"));
				String id = rs.getString("id_documetn");
				String title = rs.getString("titulo");
				if(tipo.name().equalsIgnoreCase("LIBRO")) {
					int anhoPublicacion = rs.getInt("anho_publicacion");
					documento = new Libro(id, title, anhoPublicacion);
				}
				if(tipo.name().equalsIgnoreCase("REVISTA")) {
					documento = new Revista(id, title);
				}
			}
		} catch (Exception e) {
			System.out.println("No se ha podido encontrar el documento con titulo " + titulo);
			e.printStackTrace();
			throw new ProviderNotFoundException("Error. documento no encontrado.");
		}
		return documento;
	}
	
	/**
	 * Método que realiza un insert de un documento en la base de datos, este método comprueba
	 * si es una revista o un libro para insertarlo en la base de datos y añadir año de publicacion
	 * si es un libro
	 * @param documento
	 * @throws SQLException
	 */
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



	
}
