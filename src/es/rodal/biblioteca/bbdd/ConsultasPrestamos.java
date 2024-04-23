package es.rodal.biblioteca.bbdd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import es.rodal.biblioteca.models.Documento;
import es.rodal.biblioteca.models.Usuario;

public class ConsultasPrestamos {

	private static final String INSERT_PRESTAMO = "INSERT INTO prestamos(id_usuario, id_documento, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_DEVOLVER_DOCUMENTO = "UPDATE prestamos SET fecha_devolucion = ?, devuelto = ? WHERE id_usuario = ? AND id_documento = ?";
	private static final String COUNT_PRESTADOS = "SELECT count(*) AS prestados FROM prestamos WHERE id_usuario = ? AND devuelto = 0";
	
	/**
	 * Método que comprueba si el Documento sigue disponible para prestar y si el Usuario no ha alcanzado el
	 * máximo de documentos. Si estás condiciones se cumplen se insertará un prestamo en la base de datos
	 * lo que provocará que se ejecute un trigger en la bbdd para quitar la disponibilidad de ese documento
	 * @param usuario
	 * @param documento
	 * @param fecha
	 * @throws SQLException
	 */
	public static void insertPrestamo(Usuario usuario, Documento documento, LocalDate fecha) throws SQLException {
		
		if (ConsultasUsuarios.tomarPrestado(usuario) && ConsultasDocumentos.documentoDisponible(documento)) {
			try (Connection connection = Conexion.conectar();
					PreparedStatement statement = connection.prepareStatement(INSERT_PRESTAMO);) {
				statement.setInt(1, usuario.getId_usuario());
				statement.setString(2, documento.getId_documento());
				statement.setDate(3, Date.valueOf(fecha));
				statement.setDate(4, Date.valueOf(usuario.fechaDevolucion(fecha, documento)));
				statement.executeUpdate();
			}
		} else {
			System.out.println("ERROR. Limite de documentos alcanzado o documento no disponible!!!");
		}
	}
	
	/**
	 * Método que cambia el campo devuelto en la tabla prestamos y actualiza la fecha de devolución, tambien
	 * provoca un trigger en la base de datos que cambiará el campo disponible del documento devuelto
	 * @param usuario
	 * @param documento
	 * @param fecha
	 * @throws SQLException
	 */
	public static void devolverDocumento(Usuario usuario, Documento documento, LocalDate fecha) throws SQLException {
		
		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(UPDATE_DEVOLVER_DOCUMENTO);) {
			statement.setDate(1, Date.valueOf(fecha));
			statement.setInt(2, 1);
			statement.setInt(3, usuario.getId_usuario());
			statement.setString(4, documento.getId_documento());
			statement.executeUpdate();
		}
	}
	
	/**
	 * Método que devuelve el numero de prestamos sin devolver que tiene un usuario en la base de datos
	 * @param usuario
	 * @return
	 * @throws SQLException
	 */
	public static int contarPrestados (Usuario usuario) throws SQLException {
		try (Connection connection = Conexion.conectar();
				PreparedStatement statement = connection.prepareStatement(COUNT_PRESTADOS);) {

			statement.setInt(1, usuario.getId_usuario());
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("prestados");
				}
			}
			return 0;
		}
	}
}
