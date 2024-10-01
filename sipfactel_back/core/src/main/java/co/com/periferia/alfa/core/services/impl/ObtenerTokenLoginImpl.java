package co.com.periferia.alfa.core.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.PersistenceException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import co.com.periferia.alfa.core.dto.LoginDelcopDTO;
import co.com.periferia.alfa.core.model.DatosDelcopModel;
import co.com.periferia.alfa.core.utilitarios.RestClientFactNotUtil;

/**
 * Clase bajo patron Singleton que obtiene el token del servicio signin de Titanio
 * @author jeissoncastillo
 *
 */
public class ObtenerTokenLoginImpl {
	
	private static final Logger LOG = LoggerFactory.getLogger(ObtenerTokenLoginImpl.class);
	
	@Autowired
	private LoginDelcopDTO loginDelcopDTO;
	
	private static ObtenerTokenLoginImpl obtenerTokenLoginImpl;
	
	private Integer intento = 0;
	
	/**
	 * Constructor privado patron Singleton
	 * @param nit = documento del usuario
	 * @param delcop = objeto de la tabla TBL_DATOS_DELCOP
	 * @throws Exception 
	 */
	private ObtenerTokenLoginImpl(String nit, DatosDelcopModel delcop){
		LOG.info("Se crea nueva instancia ejecuta getToken");
		setLoginDelcopDTO(getToken(nit, delcop));	
	}
	
	/**
	 * Funcion que permite instanciar la clase desde el resto de la aplicacion
	 * @param nit = documento del usuario
	 * @param delcop = objeto de la tabla TBL_DATOS_DELCOP
	 * @return ObtenerTokenLoginImpl
	 * @throws Exception 
	 */
	public static ObtenerTokenLoginImpl getInstance(String nit, DatosDelcopModel delcop){
		LOG.info("Ingreso a metodo getInstance");
		if(obtenerTokenLoginImpl != null && obtenerTokenLoginImpl.getLoginDelcopDTO().isSuperoIntentosErroneos()) {
			obtenerTokenLoginImpl = null;
		}
		if (obtenerTokenLoginImpl == null) {
			obtenerTokenLoginImpl = new ObtenerTokenLoginImpl(nit, delcop);
		}
		if(obtenerTokenLoginImpl.getLoginDelcopDTO().getVen() != null) {
			validaTokenFecha(nit, delcop);
		}
		return obtenerTokenLoginImpl;
	}
	
	/**
	 * Metodo que elimina la instancia de la clase usado solo para test
	 */
	public static void deleteInstance() {
		if (obtenerTokenLoginImpl != null) 
			obtenerTokenLoginImpl = null;
	}
	
	public static ObtenerTokenLoginImpl getInstance() {
		return obtenerTokenLoginImpl;
	}
	
	/**
	 * Metodo que consume servicio de Titanio para recuperar el token
	 * @param nit = documento del usuario
	 * @param delcop = objeto de la tabla TBL_DATOS_DELCOP
	 * @return LoginDelcopDTO
	 */
	private LoginDelcopDTO getToken(String nit, DatosDelcopModel delcop) {
		LOG.info("Ejecutando getToken - Parametros |nit: {}", nit);
		LoginDelcopDTO personResultAsJsonStr = new LoginDelcopDTO();

		try {
			byte[] decodedBytes = Base64.getDecoder().decode(delcop.getPassword());
			String password = new String(decodedBytes);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("User-Agent", "PostmanRuntime/7.36.1");
			HttpEntity<String> request = new HttpEntity<>(setJSONObjectRequest(nit, password, delcop).toString(), headers);
			RestClientFactNotUtil.escribirLogRequestTitanio(delcop.getUrlSignin(), request);
			HttpEntity<String> response = restTemplate.exchange(delcop.getUrlSignin(), HttpMethod.POST, request, String.class);
			RestClientFactNotUtil.escribirLogResponseTitanio(delcop.getUrlSignin(), response);
			Gson gson = new Gson();
			personResultAsJsonStr = gson.fromJson(response.getBody(), LoginDelcopDTO.class);
			extraerHeadersResponse(personResultAsJsonStr, response);
			
			validarIntentos(nit, delcop, personResultAsJsonStr);
		} catch (HttpClientErrorException | PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("ERROR al ejecutar get token -----> {}", e.getMessage());
			e.printStackTrace();
			personResultAsJsonStr.setError(e.getMessage());
			personResultAsJsonStr.setSuccess(false);
			personResultAsJsonStr.setSuperoIntentosErroneos(true);
			validarIntentos(nit, delcop, personResultAsJsonStr);
		}
		return personResultAsJsonStr;
	}
	
	
	/**
	 * Metodo que arma el objeto JSONObject con los datos de login 
	 * para enviar al servicio signin de Titanio
	 * @param nit = documento del usuario
	 * @param password
	 * @param delcop = objeto de la tabla TBL_DATOS_DELCOP
	 * @return
	 */
	private JSONObject setJSONObjectRequest(String nit, String password, DatosDelcopModel delcop) {
		JSONObject personJsonObject = new JSONObject();
		try {
			personJsonObject.put("NIT", nit);
			personJsonObject.put("password", password);
			personJsonObject.put("usuario", delcop.getUsuario());
		} catch (JSONException e) {
			LOG.error("JSONException ---> {}", e.getMessage());
		}
		return personJsonObject;
	}
	
	
	/**
	 * Metodo que extrae los headers del response
	 * @param personResultAsJsonStr = objeto DTO a guardar en cache
	 * @param response = response del servicio Signin de Titanio
	 */
	private static void extraerHeadersResponse(LoginDelcopDTO personResultAsJsonStr, 
			HttpEntity<String> response) {
		LOG.info("Se extraen los datos de cabecera");
		HttpHeaders headerResponse = response.getHeaders();
		List<String> xRateLimitLimit = headerResponse.get("X-RateLimit-Limit");
		if(xRateLimitLimit != null && xRateLimitLimit.get(0) != null) {
			personResultAsJsonStr.setXRateLimitLimit(Integer.parseInt(xRateLimitLimit.get(0)));
		}
		List<String> xRateLimitRemaining = headerResponse.get("X-RateLimit-Remaining");
		if(xRateLimitRemaining != null && xRateLimitRemaining.get(0) != null) {
			personResultAsJsonStr.setXRateLimitRemaining(Integer.parseInt(xRateLimitRemaining.get(0)));
		}
	}
	
	
	/**
	 * Metodo que valida la fecha del token 
	 * @param nit = documento del usuario
	 * @param delcop = objeto de la tabla TBL_DATOS_DELCOP
	 * @throws Exception 
	 */
	private static void validaTokenFecha(String nit, DatosDelcopModel delcop) {
		TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
		Calendar fechaVen = Calendar.getInstance();
		fechaVen.setTimeZone(timeZone);
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.setTimeZone(timeZone);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			fechaVen.setTime(sdf.parse(obtenerTokenLoginImpl.getLoginDelcopDTO().getVen()));
			fechaVen.add(Calendar.SECOND, -10);
			fechaActual.setTime(new Date());
			LOG.info("fechaVen ----> {}  =>  {}", fechaVen, fechaActual);
		} catch (ParseException e) {
			LOG.error("Error de parseo de fecha ---------------->");
			LOG.error(e.getMessage());
		}
		Boolean validacionFecha = comparaFechaVen(fechaVen, fechaActual);
		if(Boolean.FALSE.equals(validacionFecha)) {
			LOG.info("El token ha caducado va a obtener nuevo token");
			try {
				obtenerTokenLoginImpl = new ObtenerTokenLoginImpl(nit, delcop);
			}catch(Exception e){
				LOG.error("Excepcion ----> {}", e.getMessage());
			}
		}
	}
	
	
	/**
	 * Metodo que compara las fechas y valida si el token ya ha expirado
	 * @param fechaVen
	 * @param timeZone
	 * @return Boolean
	 */
	private static Boolean comparaFechaVen(Calendar fechaVen, Calendar fechaActual) {
		Boolean validacion = false;
		if(fechaVen.compareTo(fechaActual) >= 0) {
			validacion = true;
		}
		return validacion;
	}
	
	
	/**
	 * Metodo que valida los intentos del servicio signin de Titanio en caso de falla
	 * @param nit = documento del usuario
	 * @param delcop = objeto de la tabla TBL_DATOS_DELCOP
	 * @param personResultAsJsonStr = objeto DTO a guardar en cache
	 */
	private void validarIntentos(String nit, DatosDelcopModel delcop, LoginDelcopDTO personResultAsJsonStr) {
		if(intento < 3 && !personResultAsJsonStr.isSuccess()) {
			intento++;
			LOG.info("Intento get token numero ----> {}", intento);
			setLoginDelcopDTO(getToken(nit, delcop));
		}else if(intento == 3 && !personResultAsJsonStr.isSuccess()) {
			LOG.info("Supero los 3 intentos se guarda mensaje de error y se pausa la ejecucion");
			personResultAsJsonStr.setSuperoIntentosErroneos(true);
			intento = 0;
		} else {
			LOG.info("No hubo error se continua normal");
			personResultAsJsonStr.setSuperoIntentosErroneos(false);
			intento = 0;
		}
	}

	
	public LoginDelcopDTO getLoginDelcopDTO() {
		return loginDelcopDTO;
	}

	public void setLoginDelcopDTO(LoginDelcopDTO loginDelcopDTO) {
		this.loginDelcopDTO = loginDelcopDTO;
	}

}
