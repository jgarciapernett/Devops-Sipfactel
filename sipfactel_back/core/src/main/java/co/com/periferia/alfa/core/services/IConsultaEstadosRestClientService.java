package co.com.periferia.alfa.core.services;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConsultaEstadoConNumdocDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.JsonEnvioModel;

@Service
public interface IConsultaEstadosRestClientService {

	public void consultarEstadoFacturas(List<ConsultaEstadoConNumdocDto> codigosTrId, List<JsonEnvioModel> listaDocumentos, boolean emisionReenvio)
			throws JSONException, IOException, ExcepcionSegAlfa;

	public void consultarEstadoDocFacturas(List<ConsultaEstadoConNumdocDto> codigosTrId, List<JsonEnvioModel> listaDocumentos,
			boolean emisionReenvio) throws JSONException, IOException, ExcepcionSegAlfa;
	
}
