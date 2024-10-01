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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import co.com.periferia.alfa.core.dto.ConsAdquirientesDTO;
import co.com.periferia.alfa.core.dto.ConsultaAdquirienteDto;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IAdquirientesService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/adquirientes")
public class AdquirientesController {
	
	@Autowired
	private IAdquirientesService adquirientesService;

	@Autowired
	public JwtTokenData jtd;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdquirientesController.class);
	
	@GetMapping("/buscarID")
	@ResponseBody
	@ApiOperation(value = "Busca en el registro por ID", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ConsultaAdquirienteDto>> findById(@RequestParam(name="id",required=false) Integer  id) {
		LOGGER.info("AdquirientesController: inicio servicio de busqueda por id, parametro | id: {} ", id);
		RespuestaDTO<ConsultaAdquirienteDto> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, adquirientesService.consultar(id));
			LOGGER.info("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer el registro, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AdquirientesController: termino servicio de busqueda por id");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}	
	
	@PutMapping("/editar")
	@ResponseBody
	@ApiOperation(value = "actualiza el registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> actualizar(@RequestBody ConsultaAdquirienteDto dto, 
			@RequestHeader(value = "documento", required = true) Integer documento,
			@RequestHeader(value = "tipo", required = true) String tipo,
			HttpServletRequest http) {
		RespuestaDTO<String> respuesta;
		dto.setFechaActualizacion(new Date());
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, adquirientesService.editar(dto, documento, tipo));
			LOGGER.info("Respuesta exitosa al editar {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al editar los datos, error {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AdquirientesController: termino servicio de editar");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}
	
	@GetMapping("/ConsAdquirientes")
	@ResponseBody
	@ApiOperation(value = "consume el servicio de Consultar Adquirientes", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<ConsAdquirientesDTO>>> consultarAdquiriente(Integer tipoidentificacion, String numidentificacion, String razonsocial) {
		RespuestaDTO<List<ConsAdquirientesDTO>> respuesta;
		LOGGER.info("AdquirientesController: inicio servicio de consultar adquirientes, parametros | tipoidentificacion: {} | numIdentificacion: {} razonSocial: {} ", tipoidentificacion, numidentificacion, razonsocial);
		try { 
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, adquirientesService.consultaAdquirientes(tipoidentificacion, numidentificacion, razonsocial));
			LOGGER.info("Respuesta exitosa al consultar {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al consultar los datos, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AdquirientesController: termino servicio de consultar adquirientes");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
