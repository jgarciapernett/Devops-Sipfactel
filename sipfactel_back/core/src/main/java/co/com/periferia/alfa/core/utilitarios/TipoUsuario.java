package co.com.periferia.alfa.core.utilitarios;

public enum TipoUsuario {
	

	APLICACION("nAPP"),
	
	LDAP("LDAP");

	private final String descripcion;

	TipoUsuario( String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return getDescripcion();
	}

}
