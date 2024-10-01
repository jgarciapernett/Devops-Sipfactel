package co.com.periferia.alfa.core.services;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;

import co.com.periferia.alfa.core.dto.ConsultaEstadoDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface IRestClient {

	String jsonBase64(String json);

	ConsultaEstadoDto emitir(String[] parametros, Date fecha, Integer tipodoc,
			Integer idFac, Integer trid, Integer numIntento, boolean tipo)
			throws JSONException, IOException, ExcepcionSegAlfa;
	
}
