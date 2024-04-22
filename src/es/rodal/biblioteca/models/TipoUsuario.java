package es.rodal.biblioteca.models;

public enum TipoUsuario{
	SOCIO(30, 20), OCASIONAL(15, 2);
	
	private int maxDiasLibro;
	private int maxDocumentos;
	
	private TipoUsuario(int maxDiasLibro, int maxDocumentos) {
		this.maxDiasLibro = maxDiasLibro;
		this.maxDocumentos = maxDocumentos;
	}

	public int getMaxDiasLibro() {
		return maxDiasLibro;
	}
	public int getMaxDocumentos() {
		return maxDocumentos;
	}
}
