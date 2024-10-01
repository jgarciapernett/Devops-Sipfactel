package co.com.periferia.alfa.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import co.com.periferia.alfa.core.dto.ResolucionFacturasDto;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.dto.ResultadoDto;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IResolucionService;
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
@RequestMapping("/resolucion/facturas")
public class ResolucionesFacturasController {

	@Autowired
	IResolucionService resolucionService;

	@Autowired
	IValidacionResolucionVigenteService vigenciaService;

	@Autowired
	public JwtTokenData jtd;

	private static final Logger LOG = LoggerFactory.getLogger(ResolucionesFacturasController.class);

	/**
	 * Controller que consulta todas las resoluciones de facturas
	 * @param sucursal - Integer, numero que representa la sucursal
	 * @param compania - Integer, nunmero que representa a la compania
	 * @return List<ResolucionFacturasDto> - lista de dto's con la informacion consultada
	 */
	@GetMapping("/consulta")
	@ApiOperation(value = "Busca en el registro por sucursal y compa�ia", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<ResolucionFacturasDto>>> buscarResolucionFacturas(Integer sucursal,
			Integer compania, String producto) {
		LOG.debug("Inicio servicio de consulta de resoluciones de facturas");
		RespuestaDTO<List<ResolucionFacturasDto>> respuesta;
		List<ResolucionFacturasDto> resolucionNotasDto = resolucionService.consultarResolucion(sucursal, compania, producto);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resolucionNotasDto);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	/**
	 * Controller que busca los ultimos rangos de la resolucion (fecha fin al y numero final
	 * @param sucursal - Integer, numero que representa la sucursal
	 * @param compania - Integer, nunmero que representa a la compania
	 * @return List<String> - lista con string, respectivamente posicion 0 es la fecha y posicion 1 el numero final
	 */
	@GetMapping("/Rango")
	@ApiOperation(value = "Busca en el registro por sucursal y compa�ia", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<String>>> buscarRangoSuperior(Integer sucursal, Integer compania, String producto) {
		LOG.debug(
				"Inicio servicio de consulta el rango superior de la ultima resolucion registrada para la sucursal y la compañia");
		RespuestaDTO<List<String>> respuesta;
		List<String> rangoSuperior = resolucionService.consultarRangoSuperior(sucursal, compania, producto);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, rangoSuperior);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	/**
	 * Metodo encargado de actualizar la resolucion de facturas
	 * @param resolucion - ResolucionFacturasDto, dto con la informacion a editar
	 * @param http - HttpServletRequest,  recopila la informacion del usuario que realiza la accion
	 * @return ResultadoDto, dto con la respuesta del servicio
	 */
	@PostMapping("/update")
	@ApiOperation(value = "edita la resolucion de facturas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ResultadoDto>> editarResolucion(@RequestBody ResolucionFacturasDto resolucion,
			HttpServletRequest http) {
		LOG.info("Inicio servicio de actualizar la resolucion");
		resolucion.setUsuario(jtd.extractName(http));
		RespuestaDTO<ResultadoDto> respuesta;
		ResultadoDto resultado = resolucionService.editarResolucion(resolucion);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resultado);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	/**
	 * Controller que ejecuta el servicio de creacion de resoluciones de facturas
	 * @param resolucion - ResolucionFacturasDto, dto con la informacion a crear
	 * @param http - HttpServletRequest,  recopila la informacion del usuario que realiza la accion
	 * @return ResultadoDto, dto con la respuesta del servicio
	 */
	
	@PostMapping("/crear")
	@ApiOperation(value = "crea resolucion de facturas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ResultadoDto>> crearResolucion(@RequestBody ResolucionFacturasDto resolucion,
			HttpServletRequest http) {
		LOG.info("Inicio servicio de crear la resolucion");
		resolucion.setUsuario(jtd.extractName(http));
		RespuestaDTO<ResultadoDto> respuesta;
		ResultadoDto resultado = resolucionService.crearResolucion(resolucion);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resultado);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	/**
	 * Controller que ejecuta el servicio de elimianr resoluciones de facturas
	 * @param resolucion - ResolucionFacturasDto, dto con la informacion a eliminar
	 * @return ResultadoDto, dto con la respuesta del servicio
	 */
	
	@PostMapping("/eliminar")
	@ApiOperation(value = "crea resolucion de facturas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ResultadoDto>> eliminarResolucion(
			@RequestBody ResolucionFacturasDto resolucion) {
		LOG.info("Inicio servicio de eliminacion de resolucion");
		RespuestaDTO<ResultadoDto> respuesta;
		ResultadoDto resultado = resolucionService.eliminarResolucion(resolucion);
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, resultado);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	/**
	 * JOB que valida la vigencia de las resoluciones, se ejecuta todos los dias a las 0:30:00 am
	 */
	
	@Scheduled(cron = "0 30 0 * * ?")
	public void validarVigencia() {
		String fechaProceso = Utilitario.fechaActual();
		LOG.info("Inicia validacion de vigencias: {} ", fechaProceso);
		vigenciaService.validacionResolucionFacturas();
		LOG.info("Termino servicio de validar vigencia de resoluciones");
	}

}
