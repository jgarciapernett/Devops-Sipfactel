package co.com.periferia.alfa.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.DatosAdministrablesDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.DatosAdministrablesService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/datosAdministrables")
public class DatosAdministrablesController {

	@Autowired
	private DatosAdministrablesService datosAdministrablesService;

	@Autowired
	public JwtTokenData jtd;

	private static final Logger LOGGER = LoggerFactory.getLogger(DatosAdministrablesController.class);

	@PutMapping("/editar")
	@ResponseBody
	@ApiOperation(value = "Cambio en el registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> agregar(@RequestParam Integer id,
			@RequestBody DatosAdministrablesDTO dto, HttpServletRequest http) {
		LOGGER.info("DatosAdministrablesController: Inicio servicio de agergar, paraemtros | id: {} | DatosAdministrablesDTO: {} | http: {} ", id, dto, http);
		RespuestaDTO<String> respuesta;
		dto.setUcreacion(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, datosAdministrablesService.editar(dto, id));
			LOGGER.info("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer el registro, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("DatosAdministrablesController: Termino servicio de agregar");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/buscarID")
	@ResponseBody
	@ApiOperation(value = "Busca en el registro por ID", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<DatosAdministrablesDTO>> findById(
			@RequestParam(name = "id", required = false) Integer id) {
		LOGGER.info("DatosAdministrablesController: inicio servicio de buscar por id, parametro id: {} ", id);
		RespuestaDTO<DatosAdministrablesDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, datosAdministrablesService.buscarPorID(id));
			LOGGER.info("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer el registro, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/BuscarTodos")
	@ResponseBody
	@ApiOperation(value = "Lista la informacion de la tabla", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DatosAdministrablesDTO>>> findBuscarTodos() {
		LOGGER.info("DatosAdministrablesController: inicio servicio de buscar todos");
		RespuestaDTO<List<DatosAdministrablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, datosAdministrablesService.buscarTodos());
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer los datos, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

}
