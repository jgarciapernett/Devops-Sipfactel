package co.com.periferia.alfa.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.ErrorEtlService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/Error")
public class ErrorEtlController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ErrorEtlController.class);

	@Autowired
	ErrorEtlService service;

	@Autowired
	public JwtTokenData jtd;
	
	@Autowired
	UtilExcecion utilException;

	@PostMapping
	@ResponseBody
	@ApiOperation(value = "Error ETL", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> error(@RequestParam Integer iderror, @RequestParam Integer idtabla,
			@RequestParam String tabla, @RequestParam Integer errorid, HttpServletRequest http){
		RespuestaDTO<String> respuesta;
		String usuario = jtd.extractName(http);
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					service.error(iderror, idtabla, tabla, errorid, usuario));
		} catch (ExcepcionSegAlfa ex) {
			LOG.error("error al generar en error etl {} {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PostMapping("/categoria")
	@ResponseBody
	@ApiOperation(value = "Recategorizar", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> recategoria(@RequestParam Integer iderror,
			@RequestParam String categoria, @RequestParam String observacion, HttpServletRequest http){
		RespuestaDTO<String> respuesta;
		String usuario = jtd.extractName(http);
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					service.categoria(iderror, categoria, observacion, usuario));
		} catch (ExcepcionSegAlfa ex) {
			LOG.error("error al recategorizar {} {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
