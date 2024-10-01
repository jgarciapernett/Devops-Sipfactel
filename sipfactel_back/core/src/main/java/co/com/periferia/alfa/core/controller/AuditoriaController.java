package co.com.periferia.alfa.core.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.AuditoriaDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.services.IAuditoriaService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/auditoria")
public class AuditoriaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditoriaController.class);

	@Autowired
	private IAuditoriaService auditoriaService;

	@GetMapping("/BuscarTodos")
	@ResponseBody
	@ApiOperation(value = "Lista la informacion general", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<AuditoriaDTO>>> finBuscarTodos() {
		LOGGER.info("AuditoriaController: Inicio servicio de buscar todos");
		RespuestaDTO<List<AuditoriaDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, auditoriaService.buscarTodos());
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer los datos, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuditoriaController: termino servicio de buscar todos");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultar")
	@ResponseBody
	@ApiOperation(value = "Lista la informacion por Fecha y usuario", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<AuditoriaDTO>>> consultar(@RequestParam(required = false) String fechaIni,
			@RequestParam(required = false) String fechaFin, @RequestParam(required = false) String usuario) {
		LOGGER.info("AuditoriaController: Inicio servicio de consultar por fecha y usuario, parametros | fechaIni: {} | fechaFin: {} | usuario: {} ", fechaIni, fechaFin, usuario);
		RespuestaDTO<List<AuditoriaDTO>> respuesta;
		try {
			if (!fechaIni.equals("null") && !fechaFin.equals("null") && !usuario.equals("null")) {
				respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
						auditoriaService.buscarPorFechaYUsuario(fechaIni, fechaFin, usuario));
			} else if (fechaIni.equals("null") && fechaFin.equals("null") && !usuario.equals("null")) {
				respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, auditoriaService.buscarPorUsuario(usuario));
			} else if (fechaIni.equals("null") && fechaFin.equals("null") && usuario.equals("null")) {
				respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, auditoriaService.buscarTodos());
			} else {
				respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, auditoriaService.buscarPorFecha(fechaIni, fechaFin));
			}
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer los datos, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuditoriaController: termino servicio de consultar por fecha y usuario");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Generar excel")
	@ResponseBody
	@ApiOperation(value = "Genera el excel por Usuario y Fecha", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<InputStreamResource> todoexcel(@RequestParam(required = false) String fechaIni,
			@RequestParam(required = false) String fechaFin, @RequestParam(required = false) String usuario) {
		LOGGER.info("AuditoriaController: Inicio servicio de generar excel, parametros | fechaIni: {} | fechaFin: {} | usuario: {} ", fechaIni, fechaFin, usuario);
		ResponseEntity<InputStreamResource> resultado = null;
		try {
			if (!fechaIni.equals("null") && !fechaFin.equals("null") && !usuario.equals("null")) {
				resultado = auditoriaService.generarExcelUsuaFecha(fechaIni, fechaFin, usuario);
				return resultado;
			} else if (fechaIni.equals("null") && fechaFin.equals("null") && !usuario.equals("null")) {
				resultado = auditoriaService.generarExcelUsuario(usuario);
				return resultado;
			} else if (fechaIni.equals("null") && fechaFin.equals("null") && usuario.equals("null")) {
				return auditoriaService.buscarTodosExcel();
			} else {
				resultado = auditoriaService.generarExcelUsuaFecha(fechaIni, fechaFin, null);
				return resultado;
			}
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error al consumir el servicio, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuditoriaController: termino servicio de generar excel");
		return resultado;
	}
}
