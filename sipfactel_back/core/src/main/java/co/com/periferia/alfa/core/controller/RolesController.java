package co.com.periferia.alfa.core.controller;

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

import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.dto.RoleDTO;
import co.com.periferia.alfa.core.dto.RonuNuevoDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IRoleService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/roles")
public class RolesController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	public JwtTokenData jtd;

	private static final Logger LOGGER = LoggerFactory.getLogger(RolesController.class);

	@GetMapping("/buscar")
	@ResponseBody
	@ApiOperation(value = "buscar por nombre en los registros", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<RoleDTO>>> buscarPorNombre(String nombre){
		RespuestaDTO<List<RoleDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, roleService.buscarPorNombre(nombre));
			LOGGER.info("Respuesta exitosa al traer los datos {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al buscar por nombre en los registros {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PostMapping("/crear")
	@ResponseBody
	@ApiOperation(value = "Agrega un nuevo registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<RoleDTO>> crearRoles(@RequestBody RoleDTO dto, HttpServletRequest http) {
		RespuestaDTO<RoleDTO> respuesta;
		dto.setRoleUcreacion(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, roleService.guardar(dto));
			if (respuesta.getResultado().getRoleDescripcion().equals(dto.getRoleDescripcion())
					|| respuesta.getResultado().getRoleNombre().equals(dto.getRoleNombre())
					|| respuesta.getResultado().getRoleEsta().equals(dto.getRoleEsta())) {
				respuesta.setMensajeEstado("Registro creado correctamente");
			} else {
				respuesta.setMensajeEstado("Se ha presentado un problema, por favor intentar nuevamente");
			}
			LOGGER.info("Respuesta exitosa al crear {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al Agregar un nuevo registro {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PutMapping("/actualizar")
	@ResponseBody
	@ApiOperation(value = "Actualiza los registros", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<RoleDTO>> updateRoles(@RequestBody RoleDTO dto, @RequestParam Integer id,
			HttpServletRequest http) {
		RespuestaDTO<RoleDTO> respuesta;
		dto.setRoleUactualizacion(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, roleService.actualizar(dto, id));
			if (respuesta.getResultado().getRoleNombre().equals(dto.getRoleNombre())
					|| respuesta.getResultado().getRoleDescripcion().equals(dto.getRoleDescripcion())
					|| respuesta.getResultado().getRoleRole().equals(dto.getRoleRole())) {
				respuesta.setMensajeEstado("Registro actualizado correctamente");
			} else {
				respuesta.setMensajeEstado("Se ha presentado un problema, por favor intentar nuevamente");
			}
			LOGGER.info("Respuesta exitosa al actualizar {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al Actualizar el registro {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/menus")
	@ResponseBody
	@ApiOperation(value = "buscar los menus", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<RonuNuevoDTO>>> buscarMenu() {
		RespuestaDTO<List<RonuNuevoDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, roleService.buscarTodosMenu());
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al buscar los menus {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultarById")
	@ResponseBody
	@ApiOperation(value = "buscar rol por id", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<RoleDTO>> buscarId(@RequestParam Integer id) {
		RespuestaDTO<RoleDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, roleService.buscarPorID(id));
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al buscar rol por id {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultarTodos")
	@ResponseBody
	@ApiOperation(value = "buscar todos los roles", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<RoleDTO>>> buscarTodos(){
		RespuestaDTO<List<RoleDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, roleService.buscarTodos());
			LOGGER.info("Respuesta exitosa al traer los datos {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("error al buscar todos los roles {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
