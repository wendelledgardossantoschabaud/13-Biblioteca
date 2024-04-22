package es.rodal.biblioteca.models;

import java.time.LocalDate;

public class Usuario {

	private int id_usuario;
	private String dni;
	private String nombre;
	private String apellidos;	
	private TipoUsuario tipo_usuario;
	
	public Usuario() {
		this.tipo_usuario = TipoUsuario.OCASIONAL;
	}

	public Usuario(String dni, String nombre, String apellidos) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tipo_usuario = TipoUsuario.OCASIONAL;
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
	
	public TipoUsuario getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(TipoUsuario tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}
	
	public void setTipo_usuario(String tipo_usuario) {
		this.tipo_usuario = TipoUsuario.valueOf(tipo_usuario.toUpperCase());
	}

	public void hacerSocio() {
		this.tipo_usuario = TipoUsuario.SOCIO;
	}
	
	public LocalDate fechaDevolucion(LocalDate fecha, Documento documento) {
		
		int numDias = this.tipo_usuario.getMaxDiasLibro();
		
		if (documento instanceof Revista) {
			numDias = numDias/3;
		}
		
		return fecha.plusDays(numDias);
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName()).append(", id=").append(id_usuario).append(", dni=")
		.append(dni).append(", nombre=").append(nombre).append(", apellidos=").append(apellidos)
		.append(", tipo de usuario=").append(tipo_usuario);
		return builder.toString();
	}

	
}
