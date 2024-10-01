package co.com.periferia.alfa.core.services.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.EliminarTransaccionDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.DatosDelcopModel;
import co.com.periferia.alfa.core.repository.DatosDelcopRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.services.IEliminarTransaccionRestClientService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.RestClientFactNotUtil;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Servicio que elimina la transaccion guardada en titanio
 * 
 * @author Duvan Rodriguez
 */

@Service
public class EliminarTransaccionRestClientServiceImpl implements IEliminarTransaccionRestClientService {

	private static final Logger LOG = LoggerFactory.getLogger(EliminarTransaccionRestClientServiceImpl.class);

	private static final Integer FACTURA = 1198;
	
	private static final Integer SINERRORES = 0;
	
	@Autowired
	DatosDelcopRepository datosDelcopRepository;

	@Autowired
	FacturasRepository facturaRepository;

	@Autowired
	NotaDebitoCreditoRepository notaRepository;

	/**
	 * Metodo que realiza la logica de la eliminacion de transacciones en titanio
	 * @param transaccion - Integer, codigo de la transaccion a eliminar
	 * @param numDoc - String, numero de documento para validar el re-encolamiento
	 * @param facturaNota - Integer, codigo que define el tipo de documento a re-encolar, 1198 factura, 1202 credito, 1203, debito
	 * @return boolean que define si la eliminacion fue existosa
	 * @throws ExcepcionSegAlfa exception importnate para el control del login delcop
	 */
	
	@Override
	public boolean eliminarTransaccion(Integer transaccion, String numDoc, Integer facturaNota) throws ExcepcionSegAlfa {
		String fechaEjecucion = Utilitario.fechaActual();
		LOG.info("Ingreso a eliminacion de transaccion titanio {} ", fechaEjecucion);
        boolean resultado = false;
		DatosDelcopModel delcop = datosDelcopRepository.getOne(Integer.parseInt(FacEnum.ID_DATOS_DELCOP.getValor()));
		HttpHeaders headers = new HttpHeaders();
		EliminarTransaccionDto eliminarTransaccion;
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = RestClientFactNotUtil.obtenerTokenLogin(String.valueOf(delcop.getNit()), delcop);
		if (token != null && !token.equals(FacEnum.NOTWHITE_SPACE.getValor())) {
			int intentos = 0;
			HttpEntity<String> request = new HttpEntity<>(getJson(transaccion, token).toString(), headers);
			while(intentos < 3) {
				eliminarTransaccion = RestClientFactNotUtil.peticionEliminarTransaccionTitanio(delcop.getUrlEliminarTransaccion(),
						request);
				fechaEjecucion = Utilitario.fechaActual();
				LOG.info("Finalizo eliminacion de transaccion de titanio {} ", fechaEjecucion);
				if(!eliminarTransaccion.getErrorId().equals(SINERRORES)) {
					intentos++;
					LOG.warn("Ha ocurrido un error en la eliminacion, error: {} | intento => {} ", eliminarTransaccion.getErrorMsg(), intentos);
				} else {
					LOG.info(eliminarTransaccion.getErrorMsg());
					if(facturaNota.equals(FACTURA)) 
						facturaRepository.encolarFactura(numDoc);
					else 
						notaRepository.encolarNota(numDoc);
					
					resultado = true;
					break;
				}	
			}
		}
		return resultado;
	}

	/**
	 * Metodo que arma el json a enviar para la eliminacion de transacciones
	 * @param codigoTransaccion - Integer, codigo de la transaccion a eliminar
	 * @param token - String, token necesario para el consumo del servicio
	 * @return JSONObject json armado
	 */
	
	private JSONObject getJson(Integer codigoTransaccion, String token) {
		LOG.info("Inicio armado del json de eliminacion de transaccion");
		JSONObject jsonBody = new JSONObject();

		try {
			jsonBody.put(FacEnum.TRANSACCION.getValor(), String.valueOf(codigoTransaccion));
			jsonBody.put(FacEnum.TOKEN.getValor(), token);
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error con el armado del json de documentos, error: {} ", e.getMessage());
		}
		return jsonBody;
	}

}
