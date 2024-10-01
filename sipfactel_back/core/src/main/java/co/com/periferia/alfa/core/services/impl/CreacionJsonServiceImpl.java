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
import co.com.periferia.alfa.core.dto.EncabezadoDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.NumeracionNoUsadaModel;
import co.com.periferia.alfa.core.repository.ErrorInternoRepository;
import co.com.periferia.alfa.core.repository.FacturaDelcopRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.INumeracionNoUsadaRepository;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.repository.ResolucionRepository;
import co.com.periferia.alfa.core.services.CreacionJsonService;
import co.com.periferia.alfa.core.services.EnviarEmailService;
import co.com.periferia.alfa.core.services.IRestClient;
import co.com.periferia.alfa.core.services.IUtilSeccionService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase que realiza la logica incila de las facturas a emitir
 * @author Duvan Rodriguez
 * @version 2.0
 */

@Component
public class CreacionJsonServiceImpl implements CreacionJsonService {

	@Autowired
	FacturaDelcopRepository facturaDelcopRepository;

	@Autowired
	IRestClient restClient;

	@Autowired
	FacturasRepository facturasRepository;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	ErrorInternoRepository errorInternoRepository;

	@Autowired
	EnviarEmailService enviarEmailService;

	@Autowired
	private IUtilSeccionService seccion;

	@Autowired
	IParametrosRepository parametrosRepository;

	@Autowired
	ResolucionRepository resolucionRepository;
	
	@Autowired
	INumeracionNoUsadaRepository numeracionNoUsadaRepository;

	private static final Logger LOG = LoggerFactory.getLogger(CreacionJsonServiceImpl.class);

	private static final Integer CARGADOTITANIO = 1255;
	private static final Integer OPC_FACTURAS = 1;

	/**
	 * Metodo que implementa la interfaz y que se comunica con el armado del json
	 * 
	 * @param id     - codigo estado de la factura
	 * @param numDoc - numero de la transaccion
	 * @param user   - usuario
	 * @return List<ConsultaEstadoDto>
	 * @throws IOException
	 */

	@Override
	public List<ConsultaEstadoDto> emitirFacturas(Integer id, String numdoc, String user) throws ExcepcionSegAlfa {
		return json(id, numdoc, user);
	}

	/**
	 * Metodo que se encarga del armado del json
	 * 
	 * @param id     - codigo estado de la factura
	 * @param numDoc - numero de la transaccion
	 * @param user   - usuario
	 * @return List<ConsultaEstadoDto>
	 * @throws IOException
	 */

	private List<ConsultaEstadoDto> json(Integer id, String numdoc, String user) throws ExcepcionSegAlfa {
		LOG.info("CreacionJsonServiceImpl: Ingreso a metodo json, parametros | id: {} | numdoc: {} | user: {} ", id,
				numdoc, user);
		Integer cont = 1;

		List<ConsultaEstadoDto> addConsultaEstado = new ArrayList<>();
		ConsultaEstadoDto estado;

		List<EncabezadoDTO> enca = new ArrayList<>();

		try {
			if (id.equals(CARGADOTITANIO)) {
				enca = facturaDelcopRepository.getFac();
			} else {
				enca = facturaDelcopRepository.getFacRemediado(numdoc);
			}
			LOG.info("Cantidad de facturas a validar numeracion: {} ", enca.size());
			validarResolucion(enca);
			LOG.info("Cantidad de facturas a emitir: {} ", enca.size());
			for (EncabezadoDTO list : enca) {
				LOG.info("Armando json factura numero {} ", list.getIdFac());
				cont = 1;
				while (cont <= 3) {
					JSONObject jsonObject = new JSONObject();
					JSONObject jsonFinal = new JSONObject();
					jsonObject = seccion.getJsonObject(list, jsonObject);

					String jsonfin = jsonFinal.put(FacEnum.DOCUMENT.getValor(), jsonObject).toString();

					String json = restClient.jsonBase64(jsonfin);

					estado = complementoJson(user, json, jsonfin, cont, list);
					if (estado != null) {
						addConsultaEstado = agregarEstados(addConsultaEstado, estado);
						break;
					}
					cont++;
				}
				LOG.info("Finaliza json factura numero {} ", list.getIdFac());
			}
			return addConsultaEstado;
		} catch (JSONException e) {
			LOG.error("Error al emitir la factura, ERROR: {} | {} ", e.getMessage(), e.getStackTrace());
			e.printStackTrace();
			throw utilException.getById(4).crearExcepcion();
		}
	}

	/**
	 * Metodo que valida las facturas a emitir tengan su resolucion en vigencia antes de que se emitan
	 * @param enca - List<EncabezadoDTO>, lista con las facturas a validar
	 */
	
	private void validarResolucion(List<EncabezadoDTO> enca) {
		LOG.info("validando resoluciones vigentes de la emision de facturas");
		List<EncabezadoDTO> removerFacturas = new ArrayList<>();
		StringBuilder codigos = new StringBuilder();
		enca.stream().forEach(data -> {
			Integer existe = resolucionRepository.validarVigenciaEmisionFacturas(data.getIdFac());
			if (existe == 0) {
				facturasRepository.encolarNumeracion(data.getId());
				removerFacturas.add(data);
				codigos.append(data.getId() + ",");
			}
		});
		
		if(!removerFacturas.isEmpty()) {
			enca.removeAll(removerFacturas);
			guardarNumeracionNoUsada(codigos.toString());
		}
	}
	
	/**
	 * Metodo qeu guarda la numeracion no vigente en bd
	 * @param codigos- String, codigos separados por ',', los caules seran guardados en bd
	 */
	
	private void guardarNumeracionNoUsada(String codigos) {
		LOG.info("Guarda numeracion no vigente");
		NumeracionNoUsadaModel model = new NumeracionNoUsadaModel();
		model.setCodigos(codigos.substring(0, codigos.length()-1));
		model.setFechaHora(Utilitario.fechaActual());
		numeracionNoUsadaRepository.save(model);
	}

	/**
	 * Metodo que valida si la emision de la factura fue satisfactoria de lo
	 * contraria hace que vuelva a armarla
	 * 
	 * @param user     - El usuario
	 * @param json     - resultado del json convertyido a base 64
	 * @param jsonFin  - json armado con la data de los sp
	 * @param intentos - contador que verifica cuantas veces se ha intentado emitir
	 *                 la factura
	 * @param enca     - encabezado con datos de la factura
	 * @throws IOException
	 * @throws JSONException
	 * @throws ExcepcionSegAlfa
	 */

	private ConsultaEstadoDto complementoJson(String user, String json, String jsonFin, Integer intentos,
			EncabezadoDTO enca) throws ExcepcionSegAlfa {
		LOG.info("Ingreso a metodo complementario Json");
		String numpolendoso;
		if (enca.getNumPoliza() == null) {
			numpolendoso = enca.getNumEndoso();
		} else {
			numpolendoso = enca.getNumPoliza();
		}
		try {
			Integer trid = facturaDelcopRepository.trTipoId(enca.getIdPoliza(), OPC_FACTURAS);
			String fecha = Utilitario.fechaInvertida(enca.getIssueDate());
			String hora = enca.getIssueTime().split(FacEnum.WHITE_SPACE.getValor())[1]
					.replace(FacEnum.PUNTO0.getValor(), FacEnum.NOTWHITE_SPACE.getValor());
			String fechEmision = fecha + FacEnum.WHITE_SPACE.getValor() + hora;
			String[] parametros = new String[7];
			parametros[0] = user;
			parametros[1] = json;
			parametros[2] = enca.getId();
			parametros[3] = numpolendoso;
			parametros[4] = jsonFin;
			parametros[5] = enca.getNit();
			parametros[6] = fechEmision;

			return restClient.emitir(parametros, new Date(), enca.getTipoDocumento(), enca.getIdFac(), trid, intentos,
					true);

		} catch (JSONException | IOException | NoResultException | UnexpectedRollbackException | PatternSyntaxException
				| NullPointerException e) {

			LOG.warn("ERROR AL EMITIR FACTURA {} | intento = {} | error =  {} ", enca.getIdFac(), intentos,
					e.getMessage());
			return null;
		} catch (ExcepcionSegAlfa e) {
			throw e;
		}
	}

	/**
	 * Metodo que complementa la funcionalidad de agregar documentos para consultar su estado
	 * @param addConsultaEstado - List<ConsultaEstadoDto>, lista con los estado, esta misma sera retornada
	 * @param estado - ConsultaEstadoDto, dto con los estados a agregar
	 * @return List<ConsultaEstadoDto>, lista con los estado agregados
	 */
	
	/**
	 * Metodo que complementa la funcionalidad de agregar documentos para consultar su estado
	 * @param addConsultaEstado - List<ConsultaEstadoDto>, lista con los estado, esta misma sera retornada
	 * @param estado - ConsultaEstadoDto, dto con los estados a agregar
	 * @return List<ConsultaEstadoDto>, lista con los estado agregados
	 */
	private List<ConsultaEstadoDto> agregarEstados(List<ConsultaEstadoDto> addConsultaEstado,
			ConsultaEstadoDto estado) {
		if (estado.getTrid() != null && estado.getTrid() > 0) {
			addConsultaEstado.add(estado);
			LOG.info("Facturas armadas = {} ", addConsultaEstado.size());
		}
		return addConsultaEstado;
	}

}
