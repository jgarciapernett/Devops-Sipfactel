package co.com.periferia.alfa.core.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.ConsultaEjecucionEtlDto;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.services.IConsultarEjecucionEtlService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;

@RestController
@RequestMapping("/etl")
public class EjecucionEtlController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EjecucionEtlController.class);

	@Autowired
	private IConsultarEjecucionEtlService service;
	
	@Autowired
	UtilExcecion utilException;
	
	@GetMapping
	public ResponseEntity<RespuestaDTO<List<ConsultaEjecucionEtlDto>>> consulta (){
		RespuestaDTO<List<ConsultaEjecucionEtlDto>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, service.consultarEtl());
			LOGGER.info("Respuesta exitosa al agregar {} ", respuesta);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("error al Agregar un nuevo registro general {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(7).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
}
