package co.com.periferia.alfa.core.services.impl;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.ReporteAdquirienteDto;
import co.com.periferia.alfa.core.dto.TablaReporteContableDTO;
import co.com.periferia.alfa.core.dto.TablaReporteEstadoDTO;
import co.com.periferia.alfa.core.excel.CrearExcelDetalle;
import co.com.periferia.alfa.core.excel.CrearExcelEstado;
import co.com.periferia.alfa.core.excel.ExcelAdqurientes;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.AdquirientesModel;
import co.com.periferia.alfa.core.model.ObjetoHojaDetalle;
import co.com.periferia.alfa.core.model.ObjetoHojaEstado;
import co.com.periferia.alfa.core.model.ReporteContableModel;
import co.com.periferia.alfa.core.model.TablaReporteEstadoModel;
import co.com.periferia.alfa.core.repository.AdquirientesRepository;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.repository.ReportesRepository;
import co.com.periferia.alfa.core.services.IReportesService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.NombreEstado;

@Component
public class ReportesServiceImpl implements IReportesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportesServiceImpl.class);

	private Integer cantidadAdquirientes = 0;
	private Integer idAdquiriente = 0;

	List<String> titulos = Arrays.asList("Compa\u00f1"+"\u00EDa", "Fecha de emisi\u00f3n", "Fecha de transacci\u00f3n", "Tipo Movimiento",
			"No. Transacci\u00f3n", "No. Referencia", "No. P\u00f3liza/No. Acuerdo", "Valor Prima", "Valor Iva", "Total",
			"Tipo de documento", "N\u00FAmero de documento", "Nombre o Raz\u00f3n Social", "Sucursal", "Estado", "Mensaje");

	List<String> titulosEstado = Arrays.asList("Compa\u00f1ia", "Sucursal", "Fecha Transacci\u00f3n", "Estado",
			"No. Poliza", "Adquirientes", "Tipo Movimiento", "No. Transacci\u00f3n", "No. Referencia", "Valor Prima",
			"Valor Iva", "Total", "Respuesta", "Error Numeracion");

	List<IndexedColors> colores = Arrays.asList(IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT);

	List<IndexedColors> coloresEstado = Arrays.asList(IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
			IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT);

	@Autowired
	private ReportesRepository tablaReporteContableRepo;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	CrearExcelDetalle crearExcelDetalle;

	@Autowired
	CrearExcelEstado crearExcelEstado;

	@Autowired
	ExcelAdqurientes excelAdquirientes;

	@Autowired
	AdquirientesRepository adquirienteRepository;

	@Autowired
	IParametrosRepository parametrosRepository;

	/*
	 * SP REPORTE CONTABLE
	 */
	@Override
	public List<TablaReporteContableDTO> filtroReporteContable(String fechaini, String fechafin, Integer tipodoc,
			String numdoc, String numpoliza) throws ExcepcionSegAlfa {
		List<TablaReporteContableDTO> result = new ArrayList<>();
		try {
			LOGGER.info(
					"Ejecutando FiltroReporteContable - Parametros |fecha incial : {} |fecha final : {} "
							+ "|tipodoc : {} |numdoc : {} |numpoliza : {}",
					fechaini, fechafin, tipodoc, numdoc, numpoliza);
			result = tablaReporteContableRepo.getFiltroReporteContable(fechaini, fechafin, tipodoc, numdoc, numpoliza);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar FiltroReporteContable");
			throw utilException.getById(6).crearExcepcion();
		}
		return result;
	}

	/*
	 * SP REPORTE CONTABLE EXCEL
	 */
	@Override
	public ResponseEntity<InputStreamResource> detalleExcel(String fechaini, String fechafin, Integer tipodoc,
			String numdoc, String numpoliza, String compania) throws ExcepcionSegAlfa {

		LOGGER.info("Se inicia la busqueda de todos los datos de la tabla");
		LOGGER.info(
				"Ejecutando detalleExcel - Parametros |fecha incial : {} |fecha final : {} |tipodoc : {} "
						+ "|numdoc : {} |numpoliza : {} |compania : {}",
				fechaini, fechafin, tipodoc, numdoc, numpoliza, compania);
		MediaType mediaType = MediaType.parseMediaType(NombreEstado.MEDIATYPE);
		List<ReporteContableModel> datos = tablaReporteContableRepo.reporteDetalle(fechaini, fechafin, tipodoc, numdoc,
				numpoliza, compania);
		if (!datos.isEmpty()) {
			try {
				ByteArrayInputStream in = crearExcelDetalle
						.generar(new ObjetoHojaDetalle(NombreEstado.DETALLE, titulos, colores, datos));
				HttpHeaders headers = new HttpHeaders();
				headers.add(NombreEstado.HEADERS_1, NombreEstado.HEADERS_4);
				return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new InputStreamResource(in));
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOGGER.error("Error al ejecutar detalleExcel");
				throw utilException.getById(12).crearExcepcion();
			}
		} else {
			throw utilException.getById(6).crearExcepcion();
		}
	}

	/*
	 * SP REPORTE ESTADO
	 */
	@Override
	public List<TablaReporteEstadoDTO> filtroReporteEstado(Integer estado, String fechaini, String fechafin,
			String numpoliza, String numdoc, String compania, String sucursal) throws ExcepcionSegAlfa {
		List<TablaReporteEstadoDTO> result = new ArrayList<>();
		try {
			LOGGER.info(
					"Ejecutando FiltroReporteEstado - Parametros |fecha incial : {} |fecha final : {} "
							+ "|estado : {} |numdoc : {} |numpoliza : {} |compania : {} |sucursal : {}",
					fechaini, fechafin, estado, numdoc, numpoliza, compania, sucursal);
			result = tablaReporteContableRepo.getFiltroReporteEstado(estado, fechaini, fechafin, numpoliza, numdoc,
					compania, sucursal);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar FiltroReporteEstado");
			throw utilException.getById(6).crearExcepcion();
		}
		return result;
	}

	/*
	 * SP REPORTE ESTADO EXCEL
	 */
	@Override
	public ResponseEntity<InputStreamResource> estadoExcel(Integer estado, String fechaini, String fechafin,
			String numpoliza, String numtransaccion, String compania, String sucursal) throws ExcepcionSegAlfa {

		LOGGER.info("Se inicia la busqueda de todos los datos de la tabla");
		MediaType mediaType = MediaType.parseMediaType(NombreEstado.MEDIATYPE);
		List<TablaReporteEstadoModel> datos = tablaReporteContableRepo.reporteEstado(estado, fechaini, fechafin,
				numpoliza, numtransaccion, compania, sucursal);
		if (!datos.isEmpty()) {
			try {
				LOGGER.info(
						"Ejecutando estadoExcel - Parametros |fecha incial : {} |fecha final : {} |estado : {} "
								+ "|numero transaccion : {} |numpoliza : {} |compania : {} |sucursal : {}",
						fechaini, fechafin, estado, numtransaccion, numpoliza, compania, sucursal);
				ByteArrayInputStream in = crearExcelEstado
						.generar(new ObjetoHojaEstado(NombreEstado.ESTADO, titulosEstado, coloresEstado, datos));
				HttpHeaders headers = new HttpHeaders();
				headers.add(NombreEstado.HEADERS_1, NombreEstado.HEADERS_5);
				return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new InputStreamResource(in));
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOGGER.error("Error al ejecutar estadoExcel");
				throw utilException.getById(12).crearExcepcion();
			}
		} else {
			throw utilException.getById(6).crearExcepcion();
		}
	}

	/**
	 * Metodo que arma el excel de reportes de adquirientes
	 * 
	 * @return ResponseEntity<InputStreamResource>
	 */

	@Override
	public ResponseEntity<InputStreamResource> reporteAdquirientesExcel() throws ExcepcionSegAlfa {

		LOGGER.info("Se inicia servicio de reporte adquirientes excel");
		cantidadAdquirientes = 0;	
		idAdquiriente = 0;
		MediaType mediaType = MediaType.parseMediaType(NombreEstado.MEDIATYPE);
		List<AdquirientesModel> listaModel;
		String limiteAdquirientes = parametrosRepository.buscarNombre(FacEnum.LIMITE_ADQUIRIENTES.getValor());

		listaModel = adquirienteRepository.enviarAdquirientes(Integer.parseInt(limiteAdquirientes) + 1);

		if (listaModel.size() > Integer.parseInt(limiteAdquirientes)) {
			LOGGER.info("La cantidad de adquirientes es mayor a {} ", Integer.parseInt(limiteAdquirientes));
			cantidadAdquirientes = listaModel.size();
			idAdquiriente = listaModel.get(listaModel.size()-1).getPer();
			listaModel.remove(listaModel.size()-1);
		}

		if (!listaModel.isEmpty()) {
			try {
				ByteArrayInputStream in = excelAdquirientes.armarDatosExcel(modelToDto(listaModel));
				HttpHeaders headers = new HttpHeaders();
				headers.add(NombreEstado.HEADERS_1, NombreEstado.HEADERS_REPORTE_ADQUIRIENTES);
				return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new InputStreamResource(in));
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOGGER.error("Error al ejecutar estadoExcel, error: {} | {} ", e.getMessage(), e.getStackTrace());
				throw utilException.getById(12).crearExcepcion();
			}
		} else {
			throw utilException.getById(6).crearExcepcion();
		}

	}

	/**
	 * Metodo que define cuantos adquirientes ahi
	 * 
	 * @return String con mensaje para el front
	 */

	@Override
	public Integer reporteAdquirientesCantidad() {
		LOGGER.info("Inicio consulta de la cantidad de adquirientes");
		int cantidad = 0;
		try {
			String limiteAdquirientes = parametrosRepository.buscarNombre(FacEnum.LIMITE_ADQUIRIENTES.getValor());
			if(cantidadAdquirientes > Integer.parseInt(limiteAdquirientes)) {
				cantidad = adquirienteRepository.cantidadFaltanteAdquirientes(idAdquiriente);
			}
		} catch (SQLGrammarException | NoResultException | NullPointerException e) {
			LOGGER.warn("Ha ocurrido un error en las consultas de limite adquirientes");
		}

		LOGGER.info("Cantidad faltante de adquirientes: {} ", cantidad);

		return cantidad;
	}

	private List<ReporteAdquirienteDto> modelToDto(List<AdquirientesModel> adquirienteModel) {
		LOGGER.info("Inciio conversion de adquirienteModel a adquirienteDto");
		List<ReporteAdquirienteDto> listaDto = new ArrayList<>();
		LOGGER.info("tama�os de la lista: {} ", adquirienteModel.size());
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FacEnum.DATE_FORMAT2.getValor());
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(FacEnum.ZONA_HORARIA.getValor()));
		adquirienteModel.stream().forEach(model -> {
			ReporteAdquirienteDto dto = new ReporteAdquirienteDto();
			dto.setTipoPersona(String.valueOf(model.getCodtipopersona()));
			dto.setRazonSocialAdquriente(model.getNombrerazonsocial());
			dto.setNombreComercial(model.getNombrecomercial());
			dto.setTipoDocumentoAdquiriente(String.valueOf(model.getCodtipoidentificacion()));
			dto.setNitAdquiriente(model.getNumidentificacion());
			dto.setObligacionesFiscales(model.getDv());//para este caso especifico dv representa las obligaciones fiscales
			dto.setTipoRegimen(String.valueOf(model.getCodRegimen()));
			dto.setCodigoPostal(String.valueOf(model.getCodPostal()));
			dto.setTelefono(String.valueOf(model.getTelefono()));
			dto.setDireccionFisica(model.getDireccion());
			dto.setDireccionFiscal(model.getDireccionfiscal());
			dto.setCorreo(model.getCorreo());
			dto.setPais(String.valueOf(model.getCodpais()));
			dto.setDepartamento(String.valueOf(model.getCoddepartamento()));			
			dto.setCiudad(String.valueOf(model.getCodciudad()));
			dto.setNombreContacto(model.getContacto());
			dto.setAreaPertenece(model.getAreapertenece());
			dto.setFacturadorElectronico(String.valueOf(model.getFacturadorelectronico()));
			dto.setAutorizaEntregaElectronicaCorreoElectronico(String.valueOf(model.getCodenviofe()));
			dto.setRecibirXml(String.valueOf(model.getRecibirxml()));
			dto.setDeshabilitado(String.valueOf(model.getDeshabilitado()));
			dto.setTributo(String.valueOf(model.getCodtipotributo()));
			String fechaInsercion = FacEnum.WHITE_SPACE.getValor();
			String fechaActualizacion = FacEnum.WHITE_SPACE.getValor();
			if(model.getFechainsercion() != null) {
				fechaInsercion = simpleDateFormat.format(model.getFechainsercion());
			}
			if(model.getFechaactualizacion() != null) {
				fechaActualizacion = simpleDateFormat.format(model.getFechaactualizacion());
			}
			
			dto.setFechaInsercion(fechaInsercion);
			dto.setFechaActualizacion(fechaActualizacion);
			dto.setRegimen(model.getUsuario());
			dto.setCodigoFuente(model.getCodFuente());
			listaDto.add(dto);
		});
		LOGGER.info("termino conversion de lista de adquirientes a dto");
		return listaDto;
	}

}
