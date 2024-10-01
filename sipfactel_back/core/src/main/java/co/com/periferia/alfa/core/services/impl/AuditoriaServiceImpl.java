package co.com.periferia.alfa.core.services.impl;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.AuditoriaDTO;
import co.com.periferia.alfa.core.excel.CrearExcel;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.ObjetoHoja;
import co.com.periferia.alfa.core.model.admin.Auditoria;
import co.com.periferia.alfa.core.repository.AuditoriaRepository;
import co.com.periferia.alfa.core.services.IAuditoriaService;
import co.com.periferia.alfa.core.utilitarios.NombreEstado;

@Component
public class AuditoriaServiceImpl implements IAuditoriaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditoriaServiceImpl.class);

	List<String> titulos = Arrays.asList("ID", "DESCRIPCION", "FECHA Y HORA", "FUNCIONALIDAD", "IP", "USUARIO",
			"NOMBRES Y APELLIDO", "ROL", "DETALLE");

	List<IndexedColors> colores = Arrays.asList(IndexedColors.RED, IndexedColors.RED, IndexedColors.RED,
			IndexedColors.RED, IndexedColors.RED, IndexedColors.RED, IndexedColors.RED, IndexedColors.RED,
			IndexedColors.RED);

	@Autowired
	private AuditoriaRepository repositorio;

	@Autowired
	UtilExcecion utilException;

	@Override
	public AuditoriaDTO guardar(AuditoriaDTO clase) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public AuditoriaDTO actualizar(AuditoriaDTO clase, Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public AuditoriaDTO eliminar(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public AuditoriaDTO buscarPorID(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public List<AuditoriaDTO> buscarTodos() throws ExcepcionSegAlfa {

		LOGGER.info("AuditoriaServiceImpl: Se inicia la busqueda de todos los datos de la tabla");
		try {
			List<Auditoria> resultado = repositorio.findByGroup();
			return resultado.stream().map(e -> new AuditoriaDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (NullPointerException | IllegalArgumentException e) {
			LOGGER.error("Error en metodo buscarTodos, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public ResponseEntity<InputStreamResource> buscarTodosExcel() throws ExcepcionSegAlfa {

		LOGGER.info("AuditoriaServiceImpl: Se inicia la busqueda de todos los datos de la tabla (excel)");
		MediaType mediaType = MediaType.parseMediaType(NombreEstado.MEDIATYPE);
		try {
			List<List<Auditoria>> datos = repositorio.findByAll();
			ByteArrayInputStream in = CrearExcel
					.generar(new ObjetoHoja(NombreEstado.AUDITORIA, titulos, colores, datos));
			HttpHeaders headers = new HttpHeaders();
			headers.add(NombreEstado.HEADERS_1, NombreEstado.HEADERS_2);
			return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new InputStreamResource(in));
		} catch (UnsupportedOperationException e) {
			LOGGER.error("Error en metodo buscarTodosExcel, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<AuditoriaDTO> buscarPorUsuario(String audiUsuario) throws ExcepcionSegAlfa {
		LOGGER.info(
				"AuditoriaServiceImpl: Se inicia la busqueda de los datos de usuario de la tabla, parametro - audiUsuario {} ",
				audiUsuario);
		try {
			List<Auditoria> resultado = repositorio.findByTipo(audiUsuario);
			if (resultado.isEmpty()) {
				throw utilException.getById(6).crearExcepcion();
			}
			return resultado.stream().map(e -> new AuditoriaDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (NullPointerException | IllegalArgumentException e) {
			LOGGER.error("Error en metodo buscarPorUsuario, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<AuditoriaDTO> buscarPorFecha(String fechaIni, String fechaFin) throws ExcepcionSegAlfa {
		LOGGER.info(
				"AuditoriaServiceImpl: Se inicia la busqueda de los datos por fecha en la tabla, parametros | fechaIni: {} | fechaFin: {} ",
				fechaIni, fechaFin);
		try {
			LOGGER.info("Parametros | fechaIni: {} | fechaFin: {} ", fechaIni, fechaFin);
			DateFormat format = new SimpleDateFormat(NombreEstado.FORMATO_FECHA);
			Integer fechafin = Integer.parseInt((fechaFin.split("/")[0]));
			fechafin++;
			fechaFin = fechafin.toString() + "/" + (fechaFin.split("/")[1]) + "/" + (fechaFin.split("/")[2]);
			Date dateIni = format.parse(fechaIni);
			Date dateFin = format.parse(fechaFin);
			Timestamp timestampIni = new Timestamp(dateIni.getTime());
			Timestamp timestampFin = new Timestamp(dateFin.getTime());

			List<Auditoria> resultado = repositorio.findByfecha(timestampIni, timestampFin);
			if (resultado.isEmpty()) {
				throw utilException.getById(6).crearExcepcion();
			}
			return resultado.stream().map(e -> new AuditoriaDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (ParseException | NumberFormatException e) {
			LOGGER.error("Error en metodo buscarPorFecha, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<AuditoriaDTO> buscarPorFechaYUsuario(String fechaIni, String fechaFin, String audiUsuario)
			throws ExcepcionSegAlfa {

		LOGGER.info(
				"AuditoriaServiceImpl: Se inicia la busqueda de los datos por fecha y usuario en la tabla, parametros | fechaIni: {} | fechaFin: {} | audiUsuario: {} ",
				fechaIni, fechaFin, audiUsuario);
		try {
			DateFormat format = new SimpleDateFormat(NombreEstado.FORMATO_FECHA);
			Integer fechafin = Integer.parseInt((fechaFin.split("/")[0]));
			fechafin++;
			fechaFin = fechafin.toString() + "/" + (fechaFin.split("/")[1]) + "/" + (fechaFin.split("/")[2]);
			Date dateIni = format.parse(fechaIni);
			Date dateFin = format.parse(fechaFin);
			Timestamp timestampIni = new Timestamp(dateIni.getTime());
			Timestamp timestampFin = new Timestamp(dateFin.getTime());

			List<Auditoria> resultado = repositorio.findByUsuYFecha(timestampIni, timestampFin, audiUsuario);
			if (resultado.isEmpty()) {
				throw utilException.getById(6).crearExcepcion();
			}
			LOGGER.info("Se encontrar�n los sigueintes datos {}", resultado.size());
			return resultado.stream().map(e -> new AuditoriaDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (ParseException | NumberFormatException e) {
			LOGGER.error("Error en metodo buscarPorFechaYUsuario, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}



	@Override
	public ResponseEntity<InputStreamResource> generarExcelUsuario(String audiUsuario) throws ExcepcionSegAlfa {
		LOGGER.info("AuditoriaServiceImpl: Ingreso a metodo generarExcelUsuario, parametro - audiUsuario {} ",
				audiUsuario);
		MediaType mediaType = MediaType.parseMediaType(NombreEstado.MEDIATYPE);
		try {
			List<List<Auditoria>> datos = repositorio.findByUsuarioExcel(audiUsuario);
			ByteArrayInputStream in = CrearExcel
					.generar(new ObjetoHoja(NombreEstado.AUDITORIA, titulos, colores, datos));
			HttpHeaders headers = new HttpHeaders();
			headers.add(NombreEstado.HEADERS_1, NombreEstado.HEADERS_2);
			return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new InputStreamResource(in));
		} catch (UnsupportedOperationException e) {
			LOGGER.error("Error en metodo generarExcelUsuario, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public ResponseEntity<InputStreamResource> generarExcelUsuaFecha(String fechaIni, String fechaFin,
			String audiUsuario) throws ExcepcionSegAlfa {
		LOGGER.info(
				"AuditoriaServiceImpl: Ingreso a metodo generarExcelUsuaFecha, parametros | fechaIni: {} | fechaFin: {} | audiUsuario: {} ",
				fechaIni, fechaFin, audiUsuario);
		MediaType mediaType = MediaType.parseMediaType(NombreEstado.MEDIATYPE);
		try {
			DateFormat format = new SimpleDateFormat(NombreEstado.FORMATO_FECHA);
			Integer fechafin = Integer.parseInt((fechaFin.split("/")[0]));
			fechafin++;
			fechaFin = fechafin.toString() + "/" + (fechaFin.split("/")[1]) + "/" + (fechaFin.split("/")[2]);
			Date dateIni = format.parse(fechaIni);
			Date dateFin = format.parse(fechaFin);
			Timestamp timestampIni = new Timestamp(dateIni.getTime());
			Timestamp timestampFin = new Timestamp(dateFin.getTime());
			List<List<Auditoria>> datos = null;
			
			if (audiUsuario != null) {
				datos = repositorio.findByUsuYFechaExcel(timestampIni, timestampFin, audiUsuario);
			} else {
				datos = repositorio.findByfechaExcel(timestampIni, timestampFin);
			}
			
			ByteArrayInputStream in = CrearExcel
					.generar(new ObjetoHoja(NombreEstado.AUDITORIA, titulos, colores, datos));
			HttpHeaders headers = new HttpHeaders();
			headers.add(NombreEstado.HEADERS_1, NombreEstado.HEADERS_2);
			return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new InputStreamResource(in));
		} catch (UnsupportedOperationException | ParseException | NumberFormatException e) {
			LOGGER.error("Error en metodo generarExcelUsuaFecha, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}

	}

}
