package co.com.periferia.alfa.core.utilitarios;

public enum EstadoRespuesta {

	/**
	 * {@code 10 Ok}.
	 */
	OK(200, "Ok"),

	/**
	 * {@code 500 Error en la solicitud}.
	 */
	ERROR_SOLICITUD_RESPUESTA(500, "Error en la solicitud"),
	ERROR_VALIDACION_RESPUESTA(400, "Bad request"),

	/**
	 * {@code 90 No hay resultados}.
	 */
	NO_RESULTADOS(99, "No hay resultados");

	private final int codigo;

	private final String descripcion;

	EstadoRespuesta(int codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return this.codigo + " " + getDescripcion();
	}

}
