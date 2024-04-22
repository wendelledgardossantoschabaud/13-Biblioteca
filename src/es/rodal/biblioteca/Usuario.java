package es.rodal.biblioteca;

public class Usuario {

	private int id_usuario;
	private String dni;
	private String nombre;
	private String apellidos;	
	
	public Usuario() {
	}

	public Usuario(String dni, String nombre, String apellidos) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id_usuario=").append(id_usuario).append(", dni=").append(dni).append(", nombre=")
				.append(nombre).append(", apellidos=").append(apellidos).append("]");
		return builder.toString();
	}

	
}
