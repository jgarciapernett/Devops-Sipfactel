package co.com.periferia.alfa.core.utilitarios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import co.com.periferia.alfa.core.dto.EliminarTransaccionDto;
import co.com.periferia.alfa.core.dto.EmitirDelcopDTO;
import co.com.periferia.alfa.core.dto.EstadoDocumentosDto;
import co.com.periferia.alfa.core.dto.ListarDocumentosDto;
import co.com.periferia.alfa.core.dto.LoginDelcopDTO;
import co.com.periferia.alfa.core.dto.RespuestaDetalleTitanioDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.TipoErrorSegAlfa;
import co.com.periferia.alfa.core.model.DatosDelcopModel;
import co.com.periferia.alfa.core.services.impl.ObtenerTokenLoginImpl;

public class RestClientFactNotUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(RestClientFactNotUtil.class);
	
	@Autowired
	static TipoErrorSegAlfa tipoErrorSegAlfa ;
	
	private RestClientFactNotUtil() {}
	

	
	/**
	 * Metodo utilitario para obtener el token de Titanio
	 * @param nit
	 * @param delcop - objeto que contiene la informaci�n para invocar el servicio de titanio
	 * @return
	 * @throws ExcepcionSegAlfa
	 */
	public static String obtenerTokenLogin(String nit, DatosDelcopModel delcop) throws ExcepcionSegAlfa {
		LOG.info("RestClientFactNotUtil --> Entro a utilitario obtenerTokenLogin");
		LoginDelcopDTO signin = ObtenerTokenLoginImpl.getInstance(nit, delcop).getLoginDelcopDTO();
		if(signin.getError() != null && signin.isSuperoIntentosErroneos()) {
			LOG.warn("Ha ocurrido un error con la respuesta del token");
			tipoErrorSegAlfa = new TipoErrorSegAlfa(1003, "Ha ocurrido un error con la respuesta del token" , null);
			throw tipoErrorSegAlfa.crearExcepcion();
		}
		return signin.getToken();
	}
	
	/**
	 * Metodo utilitario para realizar la petici�n de emitir a Titanio
	 * @param url = url del servicio de Emitir Titanio
	 * @param request
	 * @return
	 */
	public static EmitirDelcopDTO peticionEmitirTitanio(String url, HttpEntity<String> request) {
		HttpEntity<String> response = complemento(url, request);
		if(response != null) {
			Gson gson = new Gson();
			return gson.fromJson(response.getBody(), EmitirDelcopDTO.class);
		}
		return null;
	}
	
	/**
	 * Metodo utilitario para realizar la petici�n de consultar estado documentos a Titanio
	 * @param url = url del servicio de estado documentos Titanio
	 * @param request
	 * @return
	 */
	public static EstadoDocumentosDto peticionEstadoDocumentosTitanio(String url, HttpEntity<String> request) {
		HttpEntity<String> response = complemento(url, request);
		if(response != null) {
			Gson gson = new Gson();
			return gson.fromJson(response.getBody(), EstadoDocumentosDto.class);
		}
		return null;
	}
	
	/**
	 * Metodo utilitario para realizar la petici�n de consultar estado detalle de factura/nota
	 * @param url = url del servicio de estado documentos Titanio
	 * @param request
	 * @return
	 */
	public static RespuestaDetalleTitanioDto peticionEstadoDocumentosDetalle(String url, HttpEntity<String> request) {
		HttpEntity<String> response = complemento(url, request);
		if(response != null) {
			Gson gson = new Gson();
			return gson.fromJson(response.getBody(), RespuestaDetalleTitanioDto.class);
		}
		return null;
	}
	
	/**
	 * Metodo utilitario para realizar la petici�n de listar  documentos a Titanio
	 * @param url = url del servicio de listar documentos Titanio
	 * @param request
	 * @return
	 */
	public static ListarDocumentosDto peticionListarDocumentosTitanio(String url, HttpEntity<String> request) {
		HttpEntity<String> response = complemento(url, request);
		if(response != null) {
			Gson gson = new Gson();
			return gson.fromJson(response.getBody(), ListarDocumentosDto.class);
		}
		return null;
	}
	
	/**
	 * Metodo utilitario para realizar la peticion de eliminar transaccion
	 * @param url = url del servicio de eliminar transaccion de Titanio
	 * @param request - informacion http del servicio de titanio
	 * @return
	 */
	public static EliminarTransaccionDto peticionEliminarTransaccionTitanio(String url, HttpEntity<String> request) {
		HttpEntity<String> response = complemento(url, request);
		if(response != null) {
			Gson gson = new Gson();
			return gson.fromJson(response.getBody(), EliminarTransaccionDto.class);
		}
		return null;
	}
	
	/**
	 * Metodo complemento para emitir y consultarEstado
	 * @param url = url del servicio de Emitir Titanio
	 * @param request
	 * @param tipo - boolean, true para emitir, flase para estadoDocumentos
	 * @return boolean
	 */
	
	private static HttpEntity<String> complemento (String url, HttpEntity<String> request) {
		String fecha = Utilitario.fechaActual();
		LOG.info("RestClientFactNotUtil --> Entro a utilitario de peticiones titanio {} ", fecha);
		RestTemplate restTemplate = new RestTemplate();
		RestClientFactNotUtil.escribirLogRequestTitanio(url, request);
		if(validaRateLimit()) {
			HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			RestClientFactNotUtil.escribirLogResponseTitanio(url, response);
			actualizaXRateLimit(response);
			return response;
		}
		return null;
	}
	
	/**
	 * Metodo utilitario que valida si xRateLimitRemaining es mayor a 0
	 * para poder seguir haciendo peticiones a Titanio
	 * @return
	 */
	private static boolean validaRateLimit() {
		LOG.info("RestClientFactNotUtil --> Entro a utilitario validaRateLimit");
		ObtenerTokenLoginImpl tokenLogin = ObtenerTokenLoginImpl.getInstance();
		LOG.info("RateLimitRemaining actual ----------> {} ", tokenLogin.getLoginDelcopDTO().getXRateLimitRemaining());
		boolean valido = false;
		if(tokenLogin.getLoginDelcopDTO().getXRateLimitRemaining() > 0)
			valido = true;
		
		return valido;
	}
	
	/**
	 * Metodo utilitario que actualiza X-RateLimit-Limit 
	 * y X-RateLimit-Remaining en LoginDelcopDTO
	 * @param response = respuesta de la llamada a servicio de Titanio
	 */
	private static void actualizaXRateLimit(HttpEntity<String> response) {
		LOG.info("RestClientFactNotUtil --> Entro a utilitario actualizaXRateLimit");
		ObtenerTokenLoginImpl tokenLoginObj = ObtenerTokenLoginImpl.getInstance();
		HttpHeaders headerResponse = response.getHeaders();
		List<String> xRateLimitLimit = headerResponse.get("X-RateLimit-Limit");
		if(xRateLimitLimit != null && xRateLimitLimit.get(0) != null) {
			tokenLoginObj.getLoginDelcopDTO().setXRateLimitLimit(Integer.parseInt(xRateLimitLimit.get(0)));
		}
		List<String> xRateLimitRemaining = headerResponse.get("X-RateLimit-Remaining");
		if(xRateLimitRemaining != null && xRateLimitRemaining.get(0) != null) {
			LOG.info("respuesta xRateLimitRemaining ------------------> {} ", xRateLimitRemaining.get(0));
			tokenLoginObj.getLoginDelcopDTO().setXRateLimitRemaining(Integer.parseInt(xRateLimitRemaining.get(0)));
		}
	}
	
	/**
	 * Metodo que escribe en el Log el request de los servicios de Titanio
	 * @param servicio - url del servicio que se invoca
	 * @param request
	 */
	public static void escribirLogRequestTitanio(String servicio, HttpEntity<String> request) {
		LOG.info("Request servicio {} -----> \n {}", servicio, request.getBody());
	}
	
	/**
	 * Metodo que escribe en el Log el response de los servicios de Titanio
	 * @param servicio - url del servicio que se invoca
	 * @param response
	 */
	public static void escribirLogResponseTitanio(String servicio, HttpEntity<String> response) {
		LOG.info("Response servicio {} -----> \n {}", servicio, response.getBody());
	}

}
