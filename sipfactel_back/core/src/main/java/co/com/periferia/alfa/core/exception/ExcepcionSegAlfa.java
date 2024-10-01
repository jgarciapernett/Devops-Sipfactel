package co.com.periferia.alfa.core.exception;

import co.com.periferiait.utilitarios.exepcion.ExcepcionPeriferia;

public class ExcepcionSegAlfa extends ExcepcionPeriferia {

	private static final long serialVersionUID = 1L;
	private final transient TipoErrorSegAlfa error;

	public ExcepcionSegAlfa(Throwable causa, TipoErrorSegAlfa error) {
		super(error.getMessage(), causa);
		this.error = error;
	}

	public ExcepcionSegAlfa(TipoErrorSegAlfa error) {
		super(error.getMessage());
		this.error = error;
	}

	public TipoErrorSegAlfa getError() {
		return error;
	}

}
