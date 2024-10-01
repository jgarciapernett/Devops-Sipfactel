package co.com.periferia.alfa.core.services.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.persistence.PersistenceException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import co.com.periferia.alfa.core.dto.ConsultaEstadoDto;
import co.com.periferia.alfa.core.dto.EmitirDelcopDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.DatosDelcopModel;
import co.com.periferia.alfa.core.model.EmitirDelcopModel;
import co.com.periferia.alfa.core.model.FacturasModel;
import co.com.periferia.alfa.core.model.NotaDebitoCreditoModel;
import co.com.periferia.alfa.core.repository.DatosDelcopRepository;
import co.com.periferia.alfa.core.repository.EmitirDelcopRepository;
import co.com.periferia.alfa.core.repository.ErrorInternoRepository;
import co.com.periferia.alfa.core.repository.FacturaDelcopRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.RespDelcopRepository;
import co.com.periferia.alfa.core.services.IRestClient;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.RestClientFactNotUtil;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

@Service(value = "RestClient")
public class RestClientServiceImpl implements IRestClient {

	private static final Logger LOG = LoggerFactory.getLogger(RestClientServiceImpl.class);

	private static final Integer ENVIADOTITANIO = 1256;
	private static final Integer RECHAZADOTITANIO = 1257;
	private static final Integer USER = 0;
	private static final Integer JSON = 1;
	private static final Integer NUMDOC = 2;
	private static final Integer NUMPOLIZA = 3;
	private static final Integer JSONFIN = 4;
	private static final Integer NIT = 5;
	private static final Integer FECHAEMISION = 6;

	@Autowired
	RespDelcopRepository respDelcopRepository;

	@Autowired
	EmitirDelcopRepository emitirDelcopRepository;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	DatosDelcopRepository datosDelcopRepository;

	@Autowired
	FacturasRepository facturasRepository;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	ErrorInternoRepository errorInternoRepository;

	@Autowired
	NotaDebitoCreditoRepository notaDebitoCreditoRepository;

	@Autowired
	FacturaDelcopRepository facturaDelcopRepository;

	/**
	 * Metodo que implementa la logica de emitir
	 * @param parametros - arreglo de string que alamcena los parametros necesarios para la emision [0] = user, [1] = json, [2] = numdoc, [3] = numpoliza, [4] = jsonFin, [5] = nit, [6] = fechaEmision
	 * @param fecha
	 * @param tipoDoc
	 * @param idFac - Integer con el id que puede ser de facturas o notas
	 * @param trid - transaccion_id
	 * @param numIntento
	 * @param tipo - boolean que decide que factura o nota se emitira, true para factura y false para notas
	 */
	
	@Override
	public ConsultaEstadoDto emitir(String[] parametros, Date fecha,
			Integer tipodoc, Integer idFac, Integer trid, Integer numIntento, boolean tipo) throws JSONException, IOException, ExcepcionSegAlfa {
		LOG.info(
				"Clase emitir.emitir- parametros:|usuario: {} | numDoc {} | numPoliza {} | tipoDoc {} | id_fac {} | nit {} | trid: {} | numIntento {} ",
				parametros[USER], parametros[NUMDOC], parametros[NUMPOLIZA], tipodoc, idFac, parametros[NIT], trid, numIntento);

		String fechaEjecucion = Utilitario.fechaActual();
		LOG.info("Inicio proceso de emision {} ", fechaEjecucion);

		Integer codEstado;
		String cufe = null;
		Integer tr = 0;
		EmitirDelcopDTO resultEmitir = new EmitirDelcopDTO();
		EmitirDelcopModel resultado = new EmitirDelcopModel();		
		DatosDelcopModel delcop = datosDelcopRepository.getOne(Integer.parseInt(FacEnum.ID_DATOS_DELCOP.getValor()));
		FacturasModel factura = new FacturasModel();
		NotaDebitoCreditoModel nota = new NotaDebitoCreditoModel();
		ConsultaEstadoDto consultaEstados;
		try {
			emitirDelcopRepository.delete(parametros[NUMDOC]);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("User-Agent", "PostmanRuntime/7.36.1");
			JSONObject jsonBody = new JSONObject();	
			String token = RestClientFactNotUtil.obtenerTokenLogin(parametros[NIT], delcop);
			if (token != null && !token.equals("")) {
				jsonBody.put(FacEnum.TOKEN.getValor(), token);
				jsonBody.put(FacEnum.TR_TIPO_ID.getValor(), trid);
				jsonBody.put(FacEnum.DATA.getValor(), parametros[JSON]);
				HttpEntity<String> request = new HttpEntity<>(jsonBody.toString(), headers);
				resultEmitir = RestClientFactNotUtil.peticionEmitirTitanio(delcop.getUrlEmitir(), request);
				if (resultEmitir != null && !resultEmitir.getErrorMsg().equals(FacEnum.SESION_CADUCADA.getValor())) {
					fechaEjecucion = Utilitario.fechaActual();
					LOG.info("respuesta emitir de titanio {} ", fechaEjecucion);
					tr = resultEmitir.getTrId();
					cufe = resultEmitir.getCufe();
					resultado = resultEmitir.dtoAModelo(resultEmitir);
					resultado.setJson(parametros[JSONFIN]);
					resultado.setUcreacion(parametros[USER]);
					resultado.setFcreacion(fecha);
					resultado.setNumdocumento(parametros[NUMDOC]);
					resultado.setNumpoliza(parametros[NUMPOLIZA]);
					resultado.setTipodocumento(new BigDecimal(tipodoc));
					resultado.setIntentos(new BigDecimal(numIntento));
					resultado.setIdDocumento(idFac);
					resultado = emitirDelcopRepository.save(resultado);

					if (resultEmitir.getErrorId().equals(0)) {
						LOG.info("Factrura emitida, codigo cambia a {} ", ENVIADOTITANIO);
						codEstado = ENVIADOTITANIO;
					} else {
						LOG.warn("ERROR al ejecutar emitir factura, codigo cambia a {} ", RECHAZADOTITANIO);
						codEstado = RECHAZADOTITANIO;
					}
					if (tipo) {
						LOG.info("Se guarda factura");
						factura = facturasRepository.getOne(idFac);
						factura.setCodestado(codEstado);
						factura.setFechaHorEmision(parametros[FECHAEMISION]);
						factura.setError1260(null);
						facturasRepository.save(factura);
					} else {
						LOG.info("Se guarda nota");
						nota = notaDebitoCreditoRepository.getOne(idFac);
						nota.setEstado(codEstado);
						nota.setFechoraEmision(parametros[FECHAEMISION]);
						nota.setError1260(null);
						notaDebitoCreditoRepository.save(nota);
					}
				}
			}
			consultaEstados = new ConsultaEstadoDto(tr, factura, nota, cufe, parametros[NIT], tipo);

		} catch (JSONException | PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.error("Error al emitir factura: {} || error: {} ", idFac, e.getMessage());
			resultado.setErrorMsg(e.getMessage());
			resultado.setJson(parametros[JSONFIN]);
			resultado.setNumdocumento(parametros[NUMDOC]);
			resultado.setNumpoliza(parametros[NUMPOLIZA]);
			resultado.setTipodocumento(new BigDecimal(tipodoc));
			resultado.setIntentos(new BigDecimal(numIntento));
			emitirDelcopRepository.save(resultado);
			throw utilException.getById(4).crearExcepcion();
		}

		return consultaEstados;
	}

	/**
	 * Metodo que convierte un json a base64
	 * 
	 * @param json - para que recibe el jsonFinal
	 * @return String con el jsonfinal
	 */
	@Override
	public String jsonBase64(String json) {
		try {
			LOG.info("Convirtiendo json a Base 64: ");
			byte[] result = json.getBytes(StandardCharsets.UTF_8);
			return Base64.getEncoder().encodeToString(result);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOG.warn(FacEnum.ERROR_BASE_64.getValor(), " {} ", e.getMessage());
			return FacEnum.ERROR_BASE_64.getValor();
		}
	}

}
