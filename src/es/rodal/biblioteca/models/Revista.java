package es.rodal.biblioteca.models;

/**
 * Clase que hereda de Documento 
 * @author Admin
 *
 */
public class Revista extends Documento {

	public Revista() {
	}
	
	public Revista(String id_revista, String titulo) {
		super(id_revista, titulo);
	}
}
