package es.rodal.biblioteca.models;

/**
 * Clase que extiende de Documento y añade el anño de publicacion
 * @author Admin
 *
 */
public class Libro extends Documento {
	
	private int anhoPublicacion;
	
	public Libro() {
		super();
	}
	
	public Libro(String id_documento, String titulo, int anhoPublicacion) {
		super(id_documento, titulo);
		this.anhoPublicacion = anhoPublicacion;
	}

	public int getAnhoPublicacion() {
		return anhoPublicacion;
	}

	public void setAnhoPublicacion(int anhoPublicacion) {
		this.anhoPublicacion = anhoPublicacion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(", año de publicacion=").append(anhoPublicacion);
		return builder.toString();
	}
	
	
}
