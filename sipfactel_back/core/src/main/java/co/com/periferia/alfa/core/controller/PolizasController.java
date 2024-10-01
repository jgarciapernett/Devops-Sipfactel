package co.com.periferia.alfa.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.ApiResponseDto;
import co.com.periferia.alfa.core.dto.DetalleAdquirientesDTO;
import co.com.periferia.alfa.core.dto.DetalleFacturasDTO;
import co.com.periferia.alfa.core.dto.DetalleTransaccionDTO;
import co.com.periferia.alfa.core.dto.InformacionGeneralEdicionPolizasDto;
import co.com.periferia.alfa.core.dto.PolizaErrorEtlDTO;
import co.com.periferia.alfa.core.dto.PolizasDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IConsultaPaginadaPolizasService;
import co.com.periferia.alfa.core.services.IDetalleDocumentoService;
import co.com.periferia.alfa.core.services.IEdicionDetallePolizaService;
import co.com.periferia.alfa.core.services.IPolizasService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/polizas")
public class PolizasController {

	@Autowired
	private IPolizasService polizasService;
	
	@Autowired
	private IDetalleDocumentoService detalleService;
	
	@Autowired 
	private IEdicionDetallePolizaService edicionDetalleService;
	
	@Autowired
	private IConsultaPaginadaPolizasService consultaService;

	@Autowired
	public JwtTokenData jtd;

	private static final Logger LOGGER = LoggerFactory.getLogger(PolizasController.class);

	@Autowired
	UtilExcecion utilException;

	@PostMapping("/editar")
	@ResponseBody
	@ApiOperation(value = "editar campos en el registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> agregar(@RequestParam Integer id, @RequestBody PolizasDTO dto) {
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, polizasService.agregar(dto, id));
			LOGGER.debug("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al editar campos en el registro {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(7).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/FacturaFiltro")
	@ResponseBody
	@ApiOperation(value = "consume el servicio de filtrar por numero poliza o documento", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<ApiResponseDto>> listarTablaDocumento(
			@RequestHeader(value = "numpoliza", required = false) String numpoliza, 
			@RequestHeader(value = "numdoc", required = false) String numdoc, 
			@RequestHeader(value = "fIniEnvio", required = false) String fIniEnvio, 
			@RequestHeader(value = "fFinEnvio", required = false) String fFinEnvio, 
			@RequestHeader(value = "fIniEmision", required = false) String fIniEmision, 
			@RequestHeader(value = "fFinEmision", required = false) String fFinEmision,
			@RequestHeader(value = "pagina", required = true) int pagina,
			@RequestHeader(value = "tamano", required = true) int tamano) {
		RespuestaDTO<ApiResponseDto> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, consultaService.facturaFiltro(numpoliza, numdoc, fIniEnvio, 
					fFinEnvio, fIniEmision, fFinEmision, pagina, tamano));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al filtrar por Documento {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(5).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/FiltroPolizaErrorEtl")
	@ResponseBody
	@ApiOperation(value = "consume el servicio de filtrar por poliza error etl", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<PolizaErrorEtlDTO>>> listarTablaPolizaErrorEtl(String numpoliza,
			String numdoc, Integer categoria) {
		RespuestaDTO<List<PolizaErrorEtlDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					polizasService.filtroPolizasErrorEtl(numpoliza, numdoc, categoria));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al filtrar por Poliza {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/detalleAdquirientes")
	@ResponseBody
	@ApiOperation(value = "consume el servicio detalle Adquirientes", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<DetalleAdquirientesDTO>> detalleAdquirientes(String numpoliza,
			String tipoMovimiento) {
		RespuestaDTO<DetalleAdquirientesDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					polizasService.detalleAdquiriente(numpoliza, tipoMovimiento));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al buscar detalle Adquiriente {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/detalleFactura")
	@ResponseBody
	@ApiOperation(value = "Trae la informacion de detalle de la factura", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<InformacionGeneralEdicionPolizasDto>> detalleFactura(Integer documento, String tipo) {
		RespuestaDTO<InformacionGeneralEdicionPolizasDto> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, detalleService.detalleFactura(documento, tipo));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al traer detalle de la factura {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(5).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/detalleFacturaEtl")
	@ResponseBody
	@ApiOperation(value = "Trae la informacion de detalle de la factura de ETL", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<DetalleFacturasDTO>> detalleFacturaEtl(String numpoliza, Integer idtabla,
			String tabla) {
		RespuestaDTO<DetalleFacturasDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					polizasService.detalleFacturaEtl(numpoliza, idtabla, tabla));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al traer la informacion de detalle de la factura de ETL {} | {}", ex.getMessage(),
					ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/detalleTransaccion")
	@ResponseBody
	@ApiOperation(value = "Trae la informacion de detalle de la transaccion", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<DetalleTransaccionDTO>> detalleTransaccion(
			@RequestHeader(value = "dni", required = true) Integer dniDocumento,
			@RequestHeader(value = "tipo", required = true) String tipo) {
		RespuestaDTO<DetalleTransaccionDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, polizasService.detalleTransaccion(dniDocumento, tipo));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al traer la informacion de detalle de la transaccion {} | {}", ex.getMessage(),
					ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PutMapping("/editarDetalle")
	@ResponseBody
	@ApiOperation(value = "editar la informacion de detalle de la transaccion", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> editarDetalle(@RequestBody InformacionGeneralEdicionPolizasDto dto,
			@RequestHeader(value = "adquiriente", required = true) Integer adquiriente,
			@RequestHeader(value = "tipo", required = true) String tipo) {
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, edicionDetalleService.editarDetalle(dto, tipo, adquiriente));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al edita la informacion de detalle de la transaccion {} | {}", ex.getMessage(),
					ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@PutMapping("/editarDetalleErrorEtl")
	@ResponseBody
	@ApiOperation(value = "Trae la informacion de detalle del error etl", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> editarDetalleErrorEtl(@RequestBody DetalleFacturasDTO dto) {
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, polizasService.editarDetalleErrorEtl(dto));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al editar la informacion de detalle del error etl {} | {}", ex.getMessage(),
					ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/reenviar")
	@ResponseBody
	@ApiOperation(value = "Reenvia la factura remediada", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> reenviar(@RequestParam String numdoc, HttpServletRequest http,
			@RequestParam String tipodoc) {
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					polizasService.reenviar(numdoc, jtd.extractName(http), tipodoc));
		} catch (IOException | ExcepcionSegAlfa ex ) {
			LOGGER.error("error al editar la informacion de detalle del error etl {} | {}", ex.getMessage(),
					ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/consultar")
	@ResponseBody
	@ApiOperation(value = "Reenvia la factura remediada", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> poliza(@RequestParam String numpol, @RequestParam Integer errorid) {
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, polizasService.buscarPorPoliza(numpol, errorid));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al Reenviar la factura remediada {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA,
					utilException.getById(6).crearExcepcion(ex));
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

}
