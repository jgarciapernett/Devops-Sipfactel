package co.com.periferia.alfa.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.services.EnviarEmailService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/Email")
public class EnviarEmailController {

	private static final Logger LOG = LoggerFactory.getLogger(EnviarEmailController.class);

	@Autowired
	EnviarEmailService enviarEmail;

	@Autowired
	UtilExcecion utilException;

	@GetMapping("")
	@ResponseBody
	@ApiOperation(value = "Enviar Email", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> enviar() {
		RespuestaDTO<String> respuesta;
		try {
			LOG.info("Ejecutando servicio enviar email");
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					enviarEmail.sendmail("Hola Estos es una prueba", "Prueba Email"));
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(17).crearExcepcion(ex));
			LOG.error("error al enviar email {} {}", ex.getMessage(), ex.getStackTrace());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
