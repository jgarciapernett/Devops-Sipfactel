package co.com.periferia.alfa.core.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.HomologacionDTO;
import co.com.periferia.alfa.core.dto.OpcHomologacionDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.HomologacionService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/homologacion")
public class HomologacionController {

	@Autowired
	private HomologacionService homologacionService;

	@Autowired
	public JwtTokenData jtd;
	
	@Autowired
	UtilExcecion utilException;

	private static final Logger LOGGER = LoggerFactory.getLogger(HomologacionController.class);

	@GetMapping("/opciones")
	@ResponseBody
	@ApiOperation(value = "Lista las opciones", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<OpcHomologacionDTO>>> opciones(){
		RespuestaDTO<List<OpcHomologacionDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, homologacionService.listaOpciones());
			LOGGER.debug("Respuesta exitosa al traer las opciones {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al listar opciones {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/lista")
	@ResponseBody
	@ApiOperation(value = "Lista la información general", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<HomologacionDTO>>> finHomologacion(
			@RequestParam(name = "id", required = false) Integer id){
		RespuestaDTO<List<HomologacionDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, homologacionService.listaTablas(id));
			LOGGER.debug("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al listar informacion general {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, utilException.getById(5).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PostMapping("/crear")
	@ResponseBody
	@ApiOperation(value = "Agrega un nuevo registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<HomologacionDTO>> crearHomologacion(@RequestBody HomologacionDTO dto,
			HttpServletRequest http){
		RespuestaDTO<HomologacionDTO> respuesta;
		dto.setUcrea(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, homologacionService.guardar(dto));
			if (respuesta.getResultado().getConf().equals(dto.getConf())
					|| respuesta.getResultado().getHomologado().equals(dto.getHomologado())
					|| respuesta.getResultado().getDescripcion().equals(dto.getDescripcion())) {
				respuesta.setMensajeEstado("Registro guardado correctamente");
			} else {
				respuesta.setMensajeEstado("Se ha presentado un problema, por favor intentar nuevamente");
			}
			LOGGER.debug("Respuesta exitosa al actualizar {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al Agregar un nuevo registro {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, utilException.getById(13).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	@PutMapping("/actualizar")
	@ResponseBody
	@ApiOperation(value = "Actualiza los registros", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<HomologacionDTO>> updateHomologacion(@RequestBody HomologacionDTO dto,
			@RequestParam Integer id, HttpServletRequest http){
		RespuestaDTO<HomologacionDTO> respuesta;
		dto.setUactualiza(jtd.extractName(http));
		dto.setFactualiza(new Date());
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, homologacionService.actualizar(dto, id));
			if (respuesta.getResultado().getConf().equals(dto.getConf())
					|| respuesta.getResultado().getHomologado().equals(dto.getHomologado())
					|| respuesta.getResultado().getDescripcion().equals(dto.getDescripcion())) {
				respuesta.setMensajeEstado("Registro guardado correctamente");
			} else {
				respuesta.setMensajeEstado("Se ha presentado un problema, por favor intentar nuevamente");
			}
			LOGGER.debug("Respuesta exitosa al actualizar {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al Actualizar los registros {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, utilException.getById(8).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	@DeleteMapping("/eliminar")
	@ResponseBody
	@ApiOperation(value = "elimina los registros", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<HomologacionDTO>> deleteHomologacion(@RequestParam Integer id, HttpServletRequest http){
		RespuestaDTO<HomologacionDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, homologacionService.eliminar(id));
			LOGGER.debug("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al Eliminar registro {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, utilException.getById(9).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}
}
