package es.rodal.biblioteca.models;

public abstract class Documento {
	private String id_documento;
	private String titulo;
	
	public Documento() {
	}
	
	public Documento(String id_documento, String titulo) {
		this.id_documento = id_documento;
		this.titulo = titulo;
	}

	public String getId_documento() {
		return id_documento;
	}

	public void setId_documento(String id_documento) {
		this.id_documento = id_documento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName()).append(", id_documento=")
		.append(id_documento).append(", titulo=").append(titulo);
		return builder.toString();
	}
		
}
