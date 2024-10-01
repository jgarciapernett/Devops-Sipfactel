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

import co.com.periferia.alfa.core.dto.ConfiguracionDTO;
import co.com.periferia.alfa.core.dto.OpcionesDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.ConfiguracionService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionController {
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	@Autowired
	public JwtTokenData jtd;
		
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionController.class);

	@GetMapping("/opciones")
	@ResponseBody
	@ApiOperation(value = "Lista las opciones", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<OpcionesDTO>>> opciones() throws ExcepcionSegAlfa {
		LOGGER.info("ConfiguracionController: inicio servicio de opciones");
		RespuestaDTO<List<OpcionesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, configuracionService.listaOpciones());
			LOGGER.info("Respuesta exitosa al traer las opciones {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer las opciones, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("ConfiguracionController: Termino el servicio de opciones");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}	
	
	@GetMapping("/lista")
	@ResponseBody
	@ApiOperation(value = "Lista la informacion general", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<ConfiguracionDTO>>> finConfiguracion(@RequestParam(name="id",required=false) Integer  id) {
		LOGGER.info("ConfiguracionController: inicio servicio de informacion general, parametros | id: {} ", id);
		RespuestaDTO<List<ConfiguracionDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, configuracionService.listaTablas(id));
			LOGGER.info("Respuesta exitosa al traer los datos {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer los datos, error: {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("ConfiguracionController: Termino el servicio de informacion general");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}
	
	@PostMapping("/crear")
	@ResponseBody
	@ApiOperation(value = "Agrega un nuevo registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ConfiguracionDTO>> crearConfiguracion(@RequestBody ConfiguracionDTO dto,
			HttpServletRequest http) {		
		LOGGER.info("ConfiguracionController: inicio servicio de crearConfiguracion, parametros | ConfiguracionDTO: {} | http: {}", dto, http);
		RespuestaDTO<ConfiguracionDTO> respuesta;
		dto.setUcrea(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, configuracionService.guardar(dto));
			if(respuesta.getResultado().getCodigo().equals(dto.getCodigo()) || respuesta.getResultado().getNombre().equals(dto.getNombre())
					|| respuesta.getResultado().getDescripcion().equals(dto.getDescripcion())) {
				respuesta.setMensajeEstado("Registro guardado correctamente");
			 }else {
				 respuesta.setMensajeEstado("Se ha presentado un problema, por favor intentar nuevamente");
			 }
			LOGGER.info("Respuesta exitosa al agregar {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al agregar, error: {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("ConfiguracionController: Termino el servicio de crearConfiguracion");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	@ApiOperation(value = "Actualiza los registros", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ConfiguracionDTO>> updateConfiguracion(@RequestBody ConfiguracionDTO dto, 
			@RequestParam Integer id, HttpServletRequest http) {
		LOGGER.info("ConfiguracionController: inicio servicio de actualizar configuracion, parametros | ConfiguracionDTO: {} | id: {} | http: {}", dto, id, http);
		RespuestaDTO<ConfiguracionDTO> respuesta;
		dto.setUactualiza(jtd.extractName(http));
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, configuracionService.actualizar(dto, id));
			if(respuesta.getResultado().getCodigo().equals(dto.getCodigo()) || respuesta.getResultado().getNombre().equals(dto.getNombre())
					|| respuesta.getResultado().getDescripcion().equals(dto.getDescripcion())) {
				respuesta.setMensajeEstado("Registro actualizado correctamente");
			 }else {
				 respuesta.setMensajeEstado("Se ha presentado un problema, por favor intentar nuevamente");
			 }
			LOGGER.info("Respuesta exitosa al actualizar {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al actualizar, error: {} |{} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("ConfiguracionController: Termino el servicio de actualizar configuracion");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);			
	}
	
	@GetMapping("/buscar")
	@ResponseBody
	@ApiOperation(value = "Lista el registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ConfiguracionDTO>> findById(@RequestParam(name="id",required=false) Integer  id) {
		LOGGER.info("ConfiguracionController: inicio servicio de buscar por id, parametros | id: {} ", id);
		RespuestaDTO<ConfiguracionDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, configuracionService.buscarPorID(id));
			LOGGER.info("Respuesta exitosa al traer el registro {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer el registro, error: {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("ConfiguracionController: Termino el servicio de buscar por id");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}
}
