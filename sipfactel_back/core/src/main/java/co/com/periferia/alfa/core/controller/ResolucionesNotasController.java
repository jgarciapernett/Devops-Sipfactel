package co.com.periferia.alfa.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.DatosResolucionNotasDto;
import co.com.periferia.alfa.core.dto.ResolucionNotasDto;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.dto.ResultadoDto;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IResolucionNotasService;
import co.com.periferia.alfa.core.services.IValidacionResolucionVigenteService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import co.com.periferia.alfa.core.utilitarios.Utilitario;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;


/**
 * Clase controlador para las resoluciones tanto de notas como de facturas
 * @author Duvan Rodriguez
 * @version 2.0
 */

@RestController
@RequestMapping("/resolucion/notas")
public class ResolucionesNotasController {

	@Autowired
	IResolucionNotasService resolucionNotasService;
	
	@Autowired
	IValidacionResolucionVigenteService vigenciaService;

	@Autowired
	public JwtTokenData jtd;

	private static final String HEADER_SUCURSAL = "X-SUCURSAL";
	private static final String HEADER_COMPANIA = "X-COMPANIA";
	private static final String HEADER_PRODUCTO = "X-PRODUCTO";

	private static final Logger LOG = LoggerFactory.getLogger(ResolucionesNotasController.class);

	/**
	 * Controller que ejecuta la consulta la resolucion de notas vigentes
	 * @param sucursal - Integer, numero que representa la sucursal
	 * @param compania - Integer, nunmero que representa a la compania
	 * @return ResolucionNotasDto - objeto con la resolucion de notas consultadas
	 */
	
	@GetMapping("/consulta")
	@ApiOperation(value = "Busca en el registro por sucursal y compa�ia", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ResolucionNotasDto>> buscarResolucionNotas(
			@RequestHeader(value = HEADER_SUCURSAL, required = false) Integer sucursal,
			@RequestHeader(value = HEADER_COMPANIA, required = false) Integer compania,
			@RequestHeader(value = HEADER_PRODUCTO, required = false) String producto) {
		LOG.info("Inicio servicio de consulta de resoluciones de notas");
		RespuestaDTO<ResolucionNotasDto> respuesta;
		ResolucionNotasDto resolucionNotasDto = resolucionNotasService.consultarResolucionNotas(sucursal, compania, producto);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resolucionNotasDto);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	/**
	 * Controller que ejecuta el servicio de editar notas
	 * @param notas - DatosResolucionNotasDto, dto con la informacion a editar
	 * @param http - HttpServletRequest,  recopila la informacion del usuario que realiza la accion
	 */
	
	@PostMapping("/editar")
	@ApiOperation(value = "actualiza la numeracion de notas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ResultadoDto>> editarResolucionNotas(@RequestBody DatosResolucionNotasDto notas,
			HttpServletRequest http) {
		LOG.info("Inicio servicio de actualizar la numeracion de notas");
		notas.setUsuario(jtd.extractName(http));
		RespuestaDTO<ResultadoDto> respuesta;
		ResultadoDto resultado = resolucionNotasService.editarResolucionNotas(notas);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resultado);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Controller que ejecuta el servicio de creacion de resolucion
	 * @param notas - DatosResolucionNotasDto, dto con la informacion a acrear
	 * @param http - HttpServletRequest,  recopila la informacion del usuario que realiza la accion
	 */
	
	@PostMapping("/crear")
	@ApiOperation(value = "crea la numeracion de notas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ResultadoDto>> crearResolucionNotas(@RequestBody DatosResolucionNotasDto notas,
			HttpServletRequest http) {
		LOG.info("Inicio servicio de crear la numeracion de notas");
		notas.setUsuario(jtd.extractName(http));
		RespuestaDTO<ResultadoDto> respuesta;
		ResultadoDto resultado = resolucionNotasService.crearResolucionNotas(notas);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resultado);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Controller que ejecuta el servicio de creacion de resolucion
	 * @param notas - DatosResolucionNotasDto, dto con la informacion a acrear
	 * @param http - HttpServletRequest,  recopila la informacion del usuario que realiza la accion
	 */
	
	@PostMapping("/eliminar")
	@ApiOperation(value = "elimina la numeracion de notas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ResultadoDto>> eliminarResolucionNotas(@RequestBody DatosResolucionNotasDto notas,
			HttpServletRequest http) {
		LOG.info("Inicio servicio de eliminar la numeracion de notas");
		RespuestaDTO<ResultadoDto> respuesta;
		ResultadoDto resultado = resolucionNotasService.eliminarResolucionNotas(notas);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resultado);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Job que se ejecuta todos los dias a media noche y valida que resolucion estara vigente y cual vencida
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	public void validarVigencia() {
		String fechaProceso = Utilitario.fechaActual();
		LOG.info("Inicia validacion de vigencias: {} ", fechaProceso);
		vigenciaService.validacionResolucionNotas();
		LOG.info("Termino servicio de validar vigencia de resoluciones notas");
	}
	
}
