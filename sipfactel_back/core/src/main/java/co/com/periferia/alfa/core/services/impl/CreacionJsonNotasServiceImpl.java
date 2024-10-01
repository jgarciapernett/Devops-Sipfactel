package co.com.periferia.alfa.core.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.persistence.NoResultException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;

import co.com.periferia.alfa.core.dto.ConsultaEstadoDto;
import co.com.periferia.alfa.core.dto.EncabezadoNotaCreDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaDebDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.repository.ErrorInternoRepository;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.NotasCreDebRepository;
import co.com.periferia.alfa.core.services.CreacionJsonNotasService;
import co.com.periferia.alfa.core.services.EnviarEmailService;
import co.com.periferia.alfa.core.services.IRestClient;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilSeccion;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

@Component
public class CreacionJsonNotasServiceImpl implements CreacionJsonNotasService {

	@Autowired
	IRestClient restClient;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	NotasCreDebRepository notasCreDebRepository;

	@Autowired
	NotaDebitoCreditoRepository notaDebitoCreditoRepository;

	@Autowired
	EnviarEmailService enviarEmailService;

	@Autowired
	ErrorInternoRepository errorInternoRepository;

	@Autowired
	IParametrosRepository parametrosRepository;

	@Autowired
	private UtilSeccion seccion;

	private static final Logger LOG = LoggerFactory.getLogger(CreacionJsonNotasServiceImpl.class);
	private static final Integer OPC_DEBITO = 2;
	private static final Integer OPC_CREDITO = 3;

	/**
	 * Metodo que implementa la interfaz y que se comunica con el armado del json de
	 * notas debitp
	 * 
	 * @param id     - boolean, valida si se debe armar el json
	 * @param numDoc - numero de la transaccion
	 * @param user   - usuario
	 * @return List<ConsultaEstadoDto> - lista con los datos a consultar en titanio
	 */

	@Override
	public List<ConsultaEstadoDto> emitirNotasDeb(boolean id, String numdoc, String user)
			throws ExcepcionSegAlfa {
		return jsonNotaDeb(id, numdoc, user);
	}

	/**
	 * Metodo que implementa la interfaz y que se comunica con el armado del json de
	 * notas credito
	 * 
	 * @param id     - boolean, valida si se debe armar el json
	 * @param numDoc - numero de la transaccion
	 * @param user   - usuario
	 * @return List<ConsultaEstadoDto> - lista con los datos a consultar en titanio
	 * @throws ExcepcionSegAlfa
	 */

	@Override
	public List<ConsultaEstadoDto> emitirNotasCred(boolean id, String numdoc, String user)
			throws ExcepcionSegAlfa {
		return jsonNotaCre(id, numdoc, user);
	}

	/**
	 * Metodo que se encarga del armado de notas debito
	 * 
	 * @param id     - boolean, valida si se debe armar el json
	 * @param numDoc - numero de la transaccion
	 * @param user   - usuario
	 * @return List<ConsultaEstadoDto> - lista con los datos a consultar en titanio
	 * @throws ExcepcionSegAlfa
	 */

	private List<ConsultaEstadoDto> jsonNotaDeb(boolean id, String numdoc, String user)
			throws ExcepcionSegAlfa {
		LOG.info(
				"CreacionJsonNotasServiceImpl: Ingreso al metodo jsonNotaDeb, parametros| id: {} |numdoc: {} | usuario: {}",
				id, numdoc, user);
		Integer cont = 1;

		List<ConsultaEstadoDto> addConsultaEstado = new ArrayList<>();
		ConsultaEstadoDto estadoDebito;

		List<EncabezadoNotaDebDTO> enca = new ArrayList<>();

		try {
			if (id) {
				enca = notasCreDebRepository.getConsultaDeb();
			} else {
				enca = notasCreDebRepository.getConsultaDebRemediado(numdoc);
			}
			LOG.info("Cantidad de notas debito: {} ", enca.size());
			for (EncabezadoNotaDebDTO list : enca) {
				LOG.info("Armando json de nota debito n� {} ", list.getIdDeb());
				cont = 1;
				while (cont <= 3) {
					JSONObject jsonObject = new JSONObject();
					JSONObject jsonFinal = new JSONObject();

					jsonObject = seccion.getJsonObject(list, jsonObject);

					String jsonfin = jsonFinal.put(FacEnum.DOCUMENT.getValor(), jsonObject).toString();

					String json = restClient.jsonBase64(jsonfin);

					estadoDebito = complementoJsonDebito(user, json, jsonfin, cont, list);

					if(estadoDebito != null) {
						addConsultaEstado = agregarEstados(addConsultaEstado, estadoDebito);
						break;
					}
					cont++;
				}
				LOG.info("Finalizo json de nota debito {} ", list.getIdDeb());
			}
			return addConsultaEstado;
		} catch (JSONException e) {
			LOG.error("Error en jsonNotasDeb, ERROR: {} | {} ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(4).crearExcepcion();
		}

	}

	/**
	 * Metodo que valida si la emision de la nota deb fue satisfactoria de lo
	 * contraria hace que vuelva a armarla
	 * 
	 * @param user     - El usuario
	 * @param json     - resultado del json convertyido a base 64
	 * @param jsonFin  - json armado con la data de los sp
	 * @param intentos - contador que verifica cuantas veces se ha intentado emitir
	 *                 la factura
	 * @param enca     - encabezado con datos de la nota debito
	 * @return ConsultaEstadoDto - Objeto con los datos que se agregaran a la lista
	 *         para consultar a titanio
	 * @throws ExcepcionSegAlfa
	 */

	private ConsultaEstadoDto complementoJsonDebito(String user, String json, String jsonFin, Integer intentos,
			EncabezadoNotaDebDTO encaNota) throws ExcepcionSegAlfa {
		LOG.info("Ingreso a metodo complementario Json");
		String numpolendoso;
		if (encaNota.getNumPoliza() == null) {
			numpolendoso = encaNota.getNumEndoso();
		} else {
			numpolendoso = encaNota.getNumPoliza();
		}
		try {
			Integer trid = notasCreDebRepository.trTipoId(encaNota.getIdPoliza(), OPC_DEBITO);
			String fecha = Utilitario.fechaInvertida(encaNota.getIssueDate());
			String hora = encaNota.getIssueTime().split(FacEnum.WHITE_SPACE.getValor())[1]
					.replace(FacEnum.PUNTO0.getValor(), FacEnum.NOTWHITE_SPACE.getValor());
			String fechEmision = fecha + FacEnum.WHITE_SPACE.getValor() + hora;

			String[] parametros = new String[7];
			parametros[0] = user;
			parametros[1] = json;
			parametros[2] = encaNota.getId();
			parametros[3] = numpolendoso;
			parametros[4] = jsonFin;
			parametros[5] = encaNota.getNit();
			parametros[6] = fechEmision;

			return restClient.emitir(parametros, new Date(), encaNota.getTipoDocumento(),
					encaNota.getIdDeb(), trid, intentos, false);

		} catch (JSONException | IOException | NoResultException | UnexpectedRollbackException
				| PatternSyntaxException e) {
			LOG.error("ERROR AL EMITIR NOTA DEB {} | intento = {} | error =  {} ", encaNota.getIdDeb(), intentos,
					e.getMessage());
			return null;
		}
	}

	/**
	 * Metodo que se encarga del armado de notas credito
	 * 
	 * @param id     - boolean, valida si se debe armar el json
	 * @param numDoc - numero de la transaccion
	 * @param user   - usuario
	 * @return List<ConsultaEstadoDto> - lista con los datos a consultar en titanio
	 * @throws ExcepcionSegAlfa
	 */

	private List<ConsultaEstadoDto> jsonNotaCre(boolean id, String numdoc, String user)
			throws ExcepcionSegAlfa {
		LOG.info(
				"CreacionJsonNotasServiceImpl: Ingreso al metodo jsonNotaCre, parametros notas credito | id: {} |numdoc: {} | usuario: {} ",
				id, numdoc, user);
		Integer cont = 1;

		List<ConsultaEstadoDto> addConsultaEstado = new ArrayList<>();
		ConsultaEstadoDto estadoCredito;

		List<EncabezadoNotaCreDTO> enca = new ArrayList<>();

		try {
			if (id) {
				enca = notasCreDebRepository.getConsultaCre();
			} else {
				enca = notasCreDebRepository.getConsultaCreRemediado(numdoc);
			}
			LOG.info("Cantidad de notas credito: {} ", enca.size());
			for (EncabezadoNotaCreDTO list : enca) {
				LOG.info("Armando json de nota debito n� {} ", list.getIdCre());
				cont = 1;
				while (cont <= 3) {
					JSONObject jsonObject = new JSONObject();
					JSONObject jsonFinal = new JSONObject();

					jsonObject = seccion.getJsonObject(list, jsonObject);

					String jsonfin = jsonFinal.put(FacEnum.DOCUMENT.getValor(), jsonObject).toString();

					String json = restClient.jsonBase64(jsonfin);

					estadoCredito = complementoJsonCredito(user, json, jsonfin, cont, list);

					if(estadoCredito != null) {
						addConsultaEstado = agregarEstados(addConsultaEstado, estadoCredito);
						break;
					}
					cont++;
				}
				LOG.info("Finalizo json de nota debito {} ", list.getIdCre());
			}
			return addConsultaEstado;
		} catch (JSONException e) {
			LOG.error("Error en jsonNotasCre, ERROR: {} | {} ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(4).crearExcepcion();
		}

	}

	/**
	 * Metodo que valida si la emision de la nota Cred fue satisfactoria de lo
	 * contraria hace que vuelva a armarla
	 * 
	 * @param user     - El usuario
	 * @param json     - resultado del json convertyido a base 64
	 * @param jsonFin  - json armado con la data de los sp
	 * @param intentos - contador que verifica cuantas veces se ha intentado emitir
	 *                 la factura
	 * @param enca     - encabezado con datos de la nota credito
	 * @return ConsultaEstadoDto - Objeto con los datos que se agregaran a la lista
	 *         para consultar a titanio
	 * @throws ExcepcionSegAlfa
	 */

	private ConsultaEstadoDto complementoJsonCredito(String user, String json, String jsonFin, Integer intentos,
			EncabezadoNotaCreDTO encaNC) throws ExcepcionSegAlfa {
		LOG.info("Ingreso a metodo complementario Json de nota credito");
		String numpolendoso;
		if (encaNC.getNumPoliza() == null) {
			numpolendoso = encaNC.getNumEndoso();
		} else {
			numpolendoso = encaNC.getNumPoliza();
		}
		try {
			Integer trid = notasCreDebRepository.trTipoId(encaNC.getIdPoliza(), OPC_CREDITO);
			String fecha = Utilitario.fechaInvertida(encaNC.getIssueDate());
			String hora = encaNC.getIssueTime().split(FacEnum.WHITE_SPACE.getValor())[1]
					.replace(FacEnum.PUNTO0.getValor(), FacEnum.NOTWHITE_SPACE.getValor());
			String fechEmision = fecha + FacEnum.WHITE_SPACE.getValor() + hora;
			String[] parametros = new String[7];
			parametros[0] = user;
			parametros[1] = json;
			parametros[2] = encaNC.getId();
			parametros[3] = numpolendoso;
			parametros[4] = jsonFin;
			parametros[5] = encaNC.getNit();
			parametros[6] = fechEmision;
			
			return restClient.emitir(parametros, new Date(), encaNC.getTipoDocumento(),
					encaNC.getIdCre(), trid, intentos, false);
        
		} catch (JSONException | IOException | NoResultException | UnexpectedRollbackException
				| PatternSyntaxException | NullPointerException e) {
			LOG.error("ERROR AL EMITIR NOTA CRED {} | intento = {} | error =  {} ", encaNC.getIdCre(), intentos,
					e.getMessage());
			return null;
		}
	}
	
	private List<ConsultaEstadoDto> agregarEstados (List<ConsultaEstadoDto> addConsultaEstado, ConsultaEstadoDto estado) {
		if(estado.getTrid() != null && estado.getTrid() > 0) {
			addConsultaEstado.add(estado);	
			LOG.info("Notas armadas armadas = {} ", addConsultaEstado.size());
		}
		return addConsultaEstado;
	}

}
