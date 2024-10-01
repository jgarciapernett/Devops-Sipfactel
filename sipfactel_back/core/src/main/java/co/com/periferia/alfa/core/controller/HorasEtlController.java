package co.com.periferia.alfa.core.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.HorarioEdicionDto;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.HorasModel;
import co.com.periferia.alfa.core.services.IHorasService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(value = "/hora")
public class HorasEtlController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HorasEtlController.class);
	
	@Autowired
	private IHorasService service;
	
	@Autowired
	UtilExcecion utilException;
	
	@GetMapping
	@ResponseBody
	@ApiOperation(value = "consultar las horas dsponibles para la ejecucion de etl", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<HorasModel>>> poliza() {
		RespuestaDTO<List<HorasModel>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, service.consultarHoras());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al consultar las horas {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@PutMapping("/editar")
	@ResponseBody
	@ApiOperation(value = "crear un horario de etl", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> agregar(@RequestBody List<HorarioEdicionDto> dto) {
		RespuestaDTO<String> respuesta;
		try {
			String resp= service.editarHorario(dto);
			if(resp==null){
				respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resp);
				LOGGER.debug("Respuesta exitosa al traer el registro {}", respuesta);
			}else{
				respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_VALIDACION_RESPUESTA, resp);
				LOGGER.debug("Error en al validacion {}", respuesta);
			}
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al editar campos en el registro {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(7).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
}
