package co.com.periferia.alfa.core.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.DatosGeneralesDTO;
import co.com.periferia.alfa.core.dto.DatosProveedorDTO;
import co.com.periferia.alfa.core.dto.ParametrosDto;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.impl.ParametrosServiceImpl;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/parametros")
public class ParametrosController {

	@Autowired
	private ParametrosServiceImpl parametrosService;

	@Autowired
	public JwtTokenData jtd;

	private static final Logger LOGGER = LoggerFactory.getLogger(ParametrosController.class);

	@Autowired
	UtilExcecion utilException;

	@PostMapping("/agregarG")
	@ResponseBody
	@ApiOperation(value = "Agrega un nuevo registro general", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> agregarGenerales(@RequestBody DatosGeneralesDTO dto,
			HttpServletRequest http) {
		RespuestaDTO<String> respuesta;
		dto.setUcreacion(jtd.extractName(http));
		dto.setFcreacion(new Date());
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, parametrosService.agregarGenerales(dto));
			LOGGER.info("Respuesta exitosa al agregar {} ", respuesta);
		} catch (ExcepcionSegAlfa | IllegalArgumentException ex) {
			LOGGER.error("error al Agregar un nuevo registro general {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(7).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	@GetMapping("/BuscarG")
	@ResponseBody
	@ApiOperation(value = "busca el ultimo registro general", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<DatosGeneralesDTO>> buscarGenerales() {
		RespuestaDTO<DatosGeneralesDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, parametrosService.buscarGenerales());
			LOGGER.debug("Respuesta exitosa al agregar {} ", respuesta);
		} catch (ExcepcionSegAlfa | IllegalArgumentException ex) {
			LOGGER.error("error al buscar el ultimo registro general {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	@PostMapping("/agregarP")
	@ResponseBody
	@ApiOperation(value = "Agrega un nuevo registro proveedor", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> agregarProveedores(@RequestBody DatosProveedorDTO dto,
			HttpServletRequest http) {
		RespuestaDTO<String> respuesta;
		dto.setUcreacion(jtd.extractName(http));
		dto.setFcreacion(new Date());
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, parametrosService.agregarProveedor(dto));
			LOGGER.debug("Respuesta exitosa al agregar {}", respuesta);
		} catch (ExcepcionSegAlfa | IllegalArgumentException ex) {
			LOGGER.error("error al Agregar un nuevo registro proveedor {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(7).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	@GetMapping("/BuscarP")
	@ResponseBody
	@ApiOperation(value = "busca el ultimo registro proveedor", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<DatosProveedorDTO>> buscarProveedor() {
		RespuestaDTO<DatosProveedorDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, parametrosService.buscarProveedor());
			LOGGER.debug("Respuesta exitosa al agregar {}", respuesta);
		} catch (ExcepcionSegAlfa | IllegalArgumentException ex) {
			LOGGER.error("error al buscar el ultimo registro proveedor {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}
	
	@GetMapping("/consultar")
	@ResponseBody
	@ApiOperation(value = "busca el parametro por id", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ParametrosDto>> buscarPorId(@RequestParam Integer dni) {
		RespuestaDTO<ParametrosDto> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, parametrosService.consultarDni(dni));
			LOGGER.debug("Respuesta exitosa al agregar {}", respuesta);
		} catch (ExcepcionSegAlfa | IllegalArgumentException ex) {
			LOGGER.error("error al buscar el ultimo registro proveedor {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("/consultar/todos")
	@ResponseBody
	@ApiOperation(value = "busca todos los parametros", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<ParametrosDto>>> buscarTodos() {
		RespuestaDTO<List<ParametrosDto>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, parametrosService.consultar());
			LOGGER.debug("Respuesta exitosa al agregar {}", respuesta);
		} catch (ExcepcionSegAlfa | IllegalArgumentException ex) {
			LOGGER.error("error al buscar el ultimo registro proveedor {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@PutMapping("/editar")
	@ResponseBody
	@ApiOperation(value = "edita parametros", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> editar(@RequestBody ParametrosDto dto) {
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, parametrosService.actualizar(dto));
			LOGGER.debug("Respuesta exitosa al agregar {}", respuesta);
		} catch (ExcepcionSegAlfa | IllegalArgumentException ex) {
			LOGGER.error("error al buscar el ultimo registro proveedor {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
}
