package co.com.periferia.alfa.core.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.AdquirientesDTO;
import co.com.periferia.alfa.core.dto.FacturasDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IFacturasService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/facturas")
public class FacturasController {

	@Autowired
	private IFacturasService facturasService;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	UtilExcecion utilException;

	private static final Logger LOGGER = LoggerFactory.getLogger(FacturasController.class);

	@GetMapping("/buscarFactura")
	@ResponseBody
	@ApiOperation(value = "Busca en el registro por Numero de factura", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<FacturasDTO>> findByFacturas(
			@RequestParam(name = "numero de factura", required = false) String numdoc) {
		RespuestaDTO<FacturasDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, facturasService.buscarPorFacturas(numdoc));
			LOGGER.info("Respuesta exitosa al traer el registro {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al buscar Factura {} {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(5).crearExcepcion());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/traerTransaccion")
	@ResponseBody
	@ApiOperation(value = "trae los registros de transaccion", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<FacturasDTO>> findByTransaccion(
			@RequestParam(name = "id", required = false) Integer id) {
		RespuestaDTO<FacturasDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, facturasService.traerTransaccion(id));
			LOGGER.debug("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al traer Transaccion {} {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(5).crearExcepcion());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/traerAdquiriente")
	@ResponseBody
	@ApiOperation(value = "trae los registros de Adquirientes", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<AdquirientesDTO>> findByAdquiriente(
			@RequestParam(name = "id", required = false) Integer id) {
		RespuestaDTO<AdquirientesDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, facturasService.traerAdquiriente(id));
			LOGGER.debug("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al traer Adquiriente {} {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(5).crearExcepcion());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PutMapping("/editarAdquiriente")
	@ResponseBody
	@ApiOperation(value = "actualiza el registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> actualizar(@RequestParam Integer id, @RequestBody AdquirientesDTO dto,
			HttpServletRequest http) {
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, facturasService.editarAdquiriente(dto, id));
			LOGGER.debug("Respuesta exitosa al editar {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al editar Adquiriente {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(5).crearExcepcion());
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
