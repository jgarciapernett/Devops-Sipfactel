package co.com.periferia.alfa.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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

import co.com.periferia.alfa.core.dto.OpcRolesDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.dto.UserDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IUserService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/Usuarios")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	IUserService userService;

	@Autowired
	public JwtTokenData jtd;

	@PostMapping("/agregar")
	@ResponseBody
	@ApiOperation(value = "Agregar Usuario", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<UserDTO>> addUser(@RequestBody UserDTO user, HttpServletRequest http) {
		RespuestaDTO<UserDTO> respuesta;
		user.setUsuaUcreacion(jtd.extractName(http));
		LOGGER.info("Agregando el usuario con id {}", user.getUsuaUsua());
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, userService.guardar(user));
			LOGGER.info("Respuesta exitosa al agregar el usuario {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al Agregar Usuario {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultar")
	@ResponseBody
	@ApiOperation(value = "Consultar Usuario", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<UserDTO>>> findUSer(@RequestParam(required = false) String nombre,
			HttpServletRequest http) {
		RespuestaDTO<List<UserDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, userService.buscarPorNombre(nombre));
			LOGGER.info("Respuesta exitosa al consultar el usuario {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al consultar Usuario {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PutMapping("/actualizar")
	@ResponseBody
	@ApiOperation(value = "actualizar Usuario", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<UserDTO>> updateUser(@RequestBody UserDTO user, @RequestParam Integer id,
			HttpServletRequest http) {
		RespuestaDTO<UserDTO> respuesta;
		user.setUsuaUactualiza(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, userService.actualizar(user, id));
			LOGGER.info("Respuesta exitosa al actualizar el usuario {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al actualizar Usuario {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultarRoles")
	@ResponseBody
	@ApiOperation(value = "Consultar Roles", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<OpcRolesDTO>>> roles(HttpServletRequest http) {
		RespuestaDTO<List<OpcRolesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, userService.rol());
			LOGGER.info("Respuesta exitosa al consultar el usuario {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al Consultar Roles {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultarPorId")
	@ResponseBody
	@ApiOperation(value = "Consultar Usuario", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<UserDTO>> buscarByID(@RequestParam(required = false) Integer id) {
		RespuestaDTO<UserDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, userService.buscarPorID(id));
			LOGGER.info("Respuesta exitosa al consultar el usuario {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al Consultar usuario {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/BuscarTodos")
	@ResponseBody
	@ApiOperation(value = "Consultar todos los usuarios", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<UserDTO>>> buscarTodos() {
		RespuestaDTO<List<UserDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, userService.buscarTodos());
			LOGGER.info("Respuesta exitosa al consultar el usuario {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al Consultar todos los usuarios {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/GeneraExcel")
	@ResponseBody
	@ApiOperation(value = "Genera el excel", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<InputStreamResource> generalExcel() {
		ResponseEntity<InputStreamResource> resultado = null;
		try {
			resultado = userService.generarExcelUsuarios();
			return resultado;
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al Generar el excel  {} | {}", ex.getMessage(), ex.getStackTrace());
			return resultado;
		}
	}
}
