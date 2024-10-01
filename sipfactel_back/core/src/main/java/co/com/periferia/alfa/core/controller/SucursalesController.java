package co.com.periferia.alfa.core.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.CodigoPostalDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.dto.SucursalesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.ISucursalesService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/sucursales")
public class SucursalesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SucursalesController.class);

	@Autowired
	ISucursalesService sucursalesService;

	@Autowired
	public JwtTokenData jtd;

	@GetMapping("/consultar")
	@ResponseBody
	@ApiOperation(value = "Lista la informacion general", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<SucursalesDTO>> buscarById(@RequestParam Integer id) {
		RespuestaDTO<SucursalesDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, sucursalesService.buscarPorID(id));
			LOGGER.info("Respuesta exitosa al traer los datos {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al Listar la informacion general {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PutMapping("/editar")
	@ResponseBody
	@ApiOperation(value = "actualiza el registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> actualizar(@RequestParam Integer id, @RequestBody SucursalesDTO dto,
			HttpServletRequest http) {
		RespuestaDTO<String> respuesta;
		dto.setFactualiza(new Date());
		dto.setUactualiza(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, sucursalesService.editar(dto, id));
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al actualizar el registro {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultarDatos")
	@ResponseBody
	@ApiOperation(value = "consulta todos los datos de la tabla", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<SucursalesDTO>>> finBuscarTodos() {
		RespuestaDTO<List<SucursalesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, sucursalesService.consultarDatos());
			LOGGER.info("Respuesta exitosa al traer los datos de la tabla {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al consultar todos los datos de la tabla {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PostMapping("/crear")
	@ResponseBody
	@ApiOperation(value = "agrega un registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<SucursalesDTO>> agregar(@RequestBody SucursalesDTO dto,
			HttpServletRequest http) {
		RespuestaDTO<SucursalesDTO> respuesta;
		dto.setFcreacion(new Date());
		dto.setUcreacion(jtd.extractName(http));
		dto.setEstado("TRUE");
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, sucursalesService.guardar(dto));
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al agregar un registro {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/codigoPostal")
	@ResponseBody
	@ApiOperation(value = "consulta todos los datos de la tabla", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<CodigoPostalDTO>>> consCodigo() {
		RespuestaDTO<List<CodigoPostalDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, sucursalesService.consCodigoPostal());
			LOGGER.info("Respuesta exitosa al traer los datos de la tabla {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al consulta todos los datos de la tabla {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
