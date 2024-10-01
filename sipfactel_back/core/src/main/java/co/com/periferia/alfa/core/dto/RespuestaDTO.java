package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaDTO<T> {

	private String codigoEstado;
	private String mensajeEstado;
	private T resultado;

	public RespuestaDTO(EstadoRespuesta codigoEstado, ExcepcionSegAlfa mensajeEstado, T resultado){
		this.codigoEstado = String.valueOf(codigoEstado.getCodigo());
		this.mensajeEstado = mensajeEstado.getError().getMessage();
		this.resultado = resultado;
	}

	public RespuestaDTO(EstadoRespuesta status, T resultado) {
		this.codigoEstado = String.valueOf(status.getCodigo());
		this.mensajeEstado = status.getDescripcion();
		this.resultado = resultado;
	}

	@SuppressWarnings("unchecked")
	public RespuestaDTO(EstadoRespuesta codigoEstado, ExcepcionSegAlfa mensajeEstado) {
		this.codigoEstado = String.valueOf(codigoEstado.getCodigo());
		this.mensajeEstado = mensajeEstado.getError().getMessage();
		this.resultado = (T) mensajeEstado.getError();
	}

}