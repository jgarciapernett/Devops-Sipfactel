package co.com.periferia.alfa.core.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConsultaEstadoConNumdocDto;
import co.com.periferia.alfa.core.dto.EstadoDocumentosDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.DatosDelcopModel;
import co.com.periferia.alfa.core.model.JsonEnvioModel;
import co.com.periferia.alfa.core.model.RespDelcopModel;
import co.com.periferia.alfa.core.repository.DatosDelcopRepository;
import co.com.periferia.alfa.core.repository.FacturaDelcopRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.RespDelcopRepository;
import co.com.periferia.alfa.core.services.IConsultaEstadosRestClientService;
import co.com.periferia.alfa.core.services.IEliminarTransaccionRestClientService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.RestClientFactNotUtil;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

import javax.persistence.PersistenceException;

/**
 * Clase que realiza la logica para la consulta de estados a titanio
 * 
 * @author Duvan Rodriguez
 */

@Service
public class ConsultaEstadosRestClientServiceImpl implements IConsultaEstadosRestClientService {

	private static final Logger LOG = LoggerFactory.getLogger(ConsultaEstadosRestClientServiceImpl.class);

	private static final Integer FACTURA = 1198;

	@Autowired
	DatosDelcopRepository datosDelcopRepository;

	@Autowired
	FacturasRepository facturaRepository;

	@Autowired
	NotaDebitoCreditoRepository notaRepository;

	@Autowired
	IEliminarTransaccionRestClientService eliminarTransaccionService;

	@Autowired
	RespDelcopRepository respDelcopRepository;

	@Autowired
	FacturaDelcopRepository facturaDelcopRepository;

	/**
	 * Metodo que se encarga de consultar los estados de las facturas
	 * 
	 * @param codigosTrId     - List<Integer>, lista con los codigos a consultar
	 * @param listaDocumentos - lista con la informacion de numDoc y tipo de
	 *                        documento
	 * @param emisionReenvio  - boolean que define que servicio va a consumir este
	 *                        metodo, true para emision, false para reenvio
	 * @throws JSONException, IOException, ExcepcionSegAlfa, la esception alfa es
	 *                        importante para el control del login delcop
	 */

	//CONSULTA PARA EL REENVIO
	@Override
	public void consultarEstadoFacturas(List<ConsultaEstadoConNumdocDto> codigosTrId, List<JsonEnvioModel> listaDocumentos,
			boolean emisionReenvio) throws JSONException, IOException, ExcepcionSegAlfa {
		String fechaEjecucion = Utilitario.fechaActual();
		
		LOG.info("consultarEstadoFacturas - Ingreso a consultaEstadosFactura {} ", fechaEjecucion);
		DatosDelcopModel delcop = datosDelcopRepository.getOne(Integer.parseInt(FacEnum.ID_DATOS_DELCOP.getValor()));
		HttpHeaders headers = new HttpHeaders();
		List<RespDelcopModel> respuesta;
		EstadoDocumentosDto estadoDocumentos;
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = RestClientFactNotUtil.obtenerTokenLogin(String.valueOf(delcop.getNit()), delcop);
		if (token != null && !token.equals(FacEnum.NOTWHITE_SPACE.getValor())) {
			HttpEntity<String> request = new HttpEntity<>(getJson(codigosTrId, token).toString(), headers);
			estadoDocumentos = RestClientFactNotUtil.peticionEstadoDocumentosTitanio(delcop.getUrlDocumentos(),
					request);
			fechaEjecucion = Utilitario.fechaActual();
			LOG.info("consultarEstadoFacturas - Finalizo consulta de estados de titanio {} ", fechaEjecucion);
			respuesta = estadoDocumentos.dtoAModelo(estadoDocumentos, codigosTrId);

			if (emisionReenvio) {
				if (!respuesta.isEmpty())
					respDelcopRepository.saveAll(respuesta);

				String mensaje = facturaDelcopRepository.actualizarEstados();
				LOG.info("consultarEstadoFacturas - Fin consulta estado dto, mensaje = {} ", mensaje);
			} else {
				List<RespDelcopModel> listaRespuestasConsulta;
				listaRespuestasConsulta = casoEliminar(respuesta, codigosTrId, listaDocumentos);

				if (!listaRespuestasConsulta.isEmpty()) {
					respDelcopRepository.saveAll(listaRespuestasConsulta);
					String mensaje = facturaDelcopRepository.actualizarEstados();
					LOG.info("consultarEstadoFacturas - actualizacion de estados terminada, estado => {} ", mensaje);
				}
			}
		}
	}

	
	//CONSULTA CON NUMDOC PARA LA EMISION
	@Override
	public void consultarEstadoDocFacturas(List<ConsultaEstadoConNumdocDto> numDocEmision, List<JsonEnvioModel> listaDocumentos,
			boolean emisionReenvio) throws JSONException, IOException, ExcepcionSegAlfa {
		try{
			String fechaEjecucion = Utilitario.fechaActual();
			LOG.info("Ingreso a consultarEstadoDocFacturas {} ", fechaEjecucion);
			DatosDelcopModel delcop = datosDelcopRepository.getOne(Integer.parseInt(FacEnum.ID_DATOS_DELCOP.getValor()));
			HttpHeaders headers = new HttpHeaders();
			List<RespDelcopModel> respuesta;
			EstadoDocumentosDto estadoDocumentos;
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("User-Agent", "PostmanRuntime/7.36.1");
			String token = RestClientFactNotUtil.obtenerTokenLogin(String.valueOf(delcop.getNit()), delcop);
			if (token != null && !token.equals(FacEnum.NOTWHITE_SPACE.getValor())) {

				HttpEntity<String> request = new HttpEntity<>(getJsonDoc(numDocEmision, token).toString(), headers);
				estadoDocumentos = RestClientFactNotUtil.peticionEstadoDocumentosTitanio(delcop.getUrlDocumentos(),
						request);
				LOG.info("consultarEstadoDocFacturas - Documento: código = {}, estado = {}", estadoDocumentos.getDocumentos(), estadoDocumentos.getErrorMsg());
				fechaEjecucion = Utilitario.fechaActual();
				LOG.info("consultarEstadoDocFacturas - Finalizo consulta de estados de titanio {} ", fechaEjecucion);
				actualizar1260(estadoDocumentos, numDocEmision);
				respuesta = estadoDocumentos.dtoAModelo(estadoDocumentos, numDocEmision);
				try{
					if (!respuesta.isEmpty()) {
						respDelcopRepository.saveAll(respuesta);
						respDelcopRepository.flush();
						facturaDelcopRepository.actualizarEstados();
					}
				}catch(PersistenceException e){
					LOG.error("Error en consultarEstadoDocFacturas - persistencia numdoc " + numDocEmision + " " + e.getMessage());
				}
			}
		}catch(Exception e){
			LOG.error("Error en consultarEstadoDocFacturas - " + e.getMessage());
		}
	}
	
	private void actualizar1260(EstadoDocumentosDto dto, List<ConsultaEstadoConNumdocDto> numdocs) {
		if (dto.getDocumentos() != null) {
			Integer cantidadDocumentos = dto.getDocumentos().size();
			LOG.info("actualizar1260 - Cantidad documentos = {} ", cantidadDocumentos);
			int codDebito = 1202;
			int codCredito = 1203;
			int codFact = 1198;
			for (int i = 0; i < cantidadDocumentos; i++) {
				
				if (dto.getDocumentos().get(i).getDocid() != null 
						&& dto.getDocumentos().get(i).getError() != null
						&& numdocs.get(i).getNumdoc().equals(dto.getDocumentos().get(i).getDocid())) {
							if (numdocs.get(i).getTipo() == codFact) {
								facturaRepository.guardarError1260(validarLimiteCaracteres(dto.getDocumentos().get(i).getError()),
										numdocs.get(i).getNumdoc());
							}
							if (numdocs.get(i).getTipo() == codDebito || numdocs.get(i).getTipo() == codCredito) {
								notaRepository.guardarError1260(validarLimiteCaracteres(dto.getDocumentos().get(i).getError()),
										numdocs.get(i).getNumdoc());
							}
				}
			}
		}
	}
	
	private String validarLimiteCaracteres(String texto) {
		return texto.length() > 900 ? texto.substring(0, 900) : texto;
	}
	
	
	/**
	 * Metodo que recopila informacion de los codigos para su posterior reenvio o
	 * eliminacion
	 * 
	 * @param respuesta       - List<RespDelcopModel> lista con la informacion
	 *                        retornada por titanio
	 * @param codigosTrId     - List<Integer> lista con los codigos trid consultados
	 * @param listaDocumentos - List<JsonEnvioModel> lista con la infromacion de
	 *                        numDoc y tipo de documento
	 * @return List<RespDelcopModel> lista con las respuestas de titanio
	 * @throws ExcepcionSegAlfa exception importante para el control del login
	 *                          delcop
	 */


	private List<RespDelcopModel> casoEliminar(List<RespDelcopModel> respuesta, List<ConsultaEstadoConNumdocDto> codigosTrId,
			List<JsonEnvioModel> listaDocumentos) throws ExcepcionSegAlfa {
		LOG.info("Ingreso a metodo que valida los trid para eliminar");
		boolean actualizarEstados = false;
		List<RespDelcopModel> listaRespuestaEstados = new ArrayList<>();
		if (!respuesta.isEmpty()) {
			for (RespDelcopModel titanio : respuesta) {
				Integer posicion = null;
				
				for(int i = 0; i < codigosTrId.size(); i++) {
					if(codigosTrId.get(i).getNumdoc().equals(titanio.getDoc())) {
						posicion = i;
						break;
					}
				}
				
				if (posicion != null) {
					actualizarEstados = validaEstadoError(listaDocumentos, codigosTrId.get(posicion).getTrid(), titanio.getEstados());
					codigosTrId.remove(posicion.intValue());
				}
				actualizarEstados(actualizarEstados, titanio, listaRespuestaEstados);
			}
			listaDocumentos.stream().forEach(datos -> {
				for (ConsultaEstadoConNumdocDto codigo : codigosTrId) {
					if (codigo.getTrid().equals(datos.getTrId())) {
						reEncolarFacturaNota(datos.getTipodocumento(), datos.getNumdocumento());
						break;
					}
				}
			});
		} else {
			listaDocumentos.stream()
					.forEach(datos -> reEncolarFacturaNota(datos.getTipodocumento(), datos.getNumdocumento()));
		}
		return listaRespuestaEstados;
	
	}
	/**
	 * Metodo que se encarga de validar si se deben agregar datos retornados por
	 * titanio a una lista de respuesta
	 * 
	 * @param actualizacion         - boolean que define si el servicio de
	 *                              eliminacion fue exitoso o no
	 * @param titanio               - RespDelcopModel Objeto con la informacion para
	 *                              agergar a la lista de respuesta
	 * @param listaRespuestaEstados - List<RespDelcopModel> lista que guardara el
	 *                              obejto de respuesta, esta misma sera retornada
	 * @return List<RespDelcopModel> lista con las respuestas de titanio
	 */

	private List<RespDelcopModel> actualizarEstados(boolean actualizacion, RespDelcopModel titanio,
			List<RespDelcopModel> listaRespuestaEstados) {
		if (actualizacion)
			listaRespuestaEstados.add(titanio);

		return listaRespuestaEstados;
	}

	/**
	 * Metodo que envia a eliminar la transaccion
	 * @param listaDocumentos - List<JsonEnvioModel> lista con la informacion de
	 *                        numDoc y tipo de documento
	 * @param numDocs      - codigo de transaccion que se validara para ser
	 *                        eliminado o no
	 * @return boolean que define si se debe actualizar el estado o no
	 * @throws ExcepcionSegAlfa exception importante para el control del login
	 *                          delcop
	 */

	private boolean validaEstadoError(List<JsonEnvioModel> listaDocumentos, Integer codigoTrId, String estado)
			throws ExcepcionSegAlfa {
		LOG.info("Ingreso a metodo para validar los estados de rechazo TRANSACCI\u00D3N INV\u00C1LIDA del codigo {} con estados {} devueltos por titanio", codigoTrId, estado.toUpperCase());
		boolean actualizarEstados = true;
		String numeroDoc = null;
		Integer facturaNota = null;
		for (JsonEnvioModel numDoc : listaDocumentos) {
			if (numDoc.getTrId().equals(codigoTrId)) {
				numeroDoc = numDoc.getNumdocumento();
				facturaNota = numDoc.getTipodocumento();
				break;
			}
		}
		if(estado.toUpperCase().contains("RECHAZADA AL LLEGAR") || estado.toUpperCase().contains("TRANSACCI\u00D3N INV\u00C1LIDA")) {
			LOG.info("Tiene estado de rechazo, se procede a eliminar");
			if (eliminarTransaccionService.eliminarTransaccion(codigoTrId, numeroDoc, facturaNota)) {
				reEncolarFacturaNota(facturaNota, numeroDoc);
				actualizarEstados = false;
			}			
		}
		return actualizarEstados;
	}

	/**
	 * Metodo qeu valida que documento debe ser re-encolado para emision, factura o
	 * nota
	 * 
	 * @param tipoDocumento - Integer codgo que define el tipo de documento a
	 *                      re-encolar, 1198 factura, 1202, credito, 1203 debito
	 * @param numDoc        - String Numero de factura o nota para ser re-encolada
	 */

	private void reEncolarFacturaNota(Integer tipoDocumento, String numDoc) {
		if (tipoDocumento != null && numDoc != null) {
			if (tipoDocumento.equals(FACTURA))
				facturaRepository.encolarFactura(numDoc);
			else
				notaRepository.encolarNota(numDoc);

		}
	}

	/**
	 * Metodo para armnar la estructura json de documentos titanio
	 * 
	 * @param codigosTrId - List<Integer>, lista con los codigos a enviar para la
	 *                    consulta
	 * @param token       - String, token validado con el nit de la tabla
	 *                    datos_delcop
	 * @return JSONObject con la estructura a enviar a titanio
	 */
//
	private JSONObject getJson(List<ConsultaEstadoConNumdocDto> codigosTrId, String token) {
		LOG.info("Inicio armado del json de estado documentos");
		JSONObject jsonBody = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			jsonBody.put(FacEnum.TOKEN.getValor(), token);

			for (ConsultaEstadoConNumdocDto numdoc : codigosTrId) {
				JSONObject transacciones = new JSONObject();
				transacciones.put(FacEnum.ENVDOCS.getValor(), numdoc.getNumdoc());
				array.put(transacciones);
			}
			jsonBody.put(FacEnum.DOCUMENTOS.getValor(), array);
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error con el armado del json de documentos, error: {} ", e.getMessage());
		}
		return jsonBody;
	}

	
	private JSONObject getJsonDoc(List<ConsultaEstadoConNumdocDto> numDocEmision, String token) {
		LOG.info("Inicio armado del json de estado documentos");
		JSONObject jsonBody = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			jsonBody.put(FacEnum.TOKEN.getValor(), token);

			for (ConsultaEstadoConNumdocDto docEmision : numDocEmision) {
				JSONObject transacciones = new JSONObject();
				transacciones.put(FacEnum.ENVDOCS.getValor(), docEmision.getNumdoc());
				array.put(transacciones);
			}
			jsonBody.put(FacEnum.DOCUMENTOS.getValor(), array);
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error con el armado del json de documentos, error: {} ", e.getMessage());
		}
		return jsonBody;
	}

}
