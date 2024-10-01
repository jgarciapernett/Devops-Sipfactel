package co.com.periferia.alfa.core.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import co.com.periferia.alfa.core.dto.DesplegableCategoriaDTO;
import co.com.periferia.alfa.core.dto.DesplegableSucursalesDTO;
import co.com.periferia.alfa.core.dto.DesplegablesDTO;
import co.com.periferia.alfa.core.dto.FuenteDTO;
import co.com.periferia.alfa.core.dto.PorcentajesDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.services.DesplegablesService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/adquirientes")
public class DesplegablesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesplegablesController.class);
	
	@Autowired
	DesplegablesService desplegablesService;

	@GetMapping("/FormaDePago")
	@ResponseBody
	@ApiOperation(value = "Busca lista froma de pago", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByFormaPago() {
       LOGGER.info("DesplegablesController: inicio servicio de buscar por forma de pago.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaFormaPago());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByFormaPago), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar por forma de pago");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/TipoPersona")
	@ResponseBody
	@ApiOperation(value = "Busca lista tipo persona", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByTipoPersona() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar por tipo persona.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaTipoPersona());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByTipoPersona), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar por tipo persona.");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/TipoIdentificacion")
	@ResponseBody
	@ApiOperation(value = "Busca lista tipo identificacion", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByTipoIdentificacion() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar por tipo identificacion.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaTipoIdentificacion());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByTipoIdentificacion), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar por tipo identificacion");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/OblFiscales")
	@ResponseBody
	@ApiOperation(value = "Busca lista obligaciones fiscales", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByOblFiscales() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar por obligaciones fiscales.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaOblFiscales());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByOblFiscales), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar por obligaciones fiscales");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Paises")
	@ResponseBody
	@ApiOperation(value = "Busca lista paises", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByPaises() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar paises.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaPaises());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByPaises), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar paises");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Ciudades")
	@ResponseBody
	@ApiOperation(value = "Busca lista ciudades", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByCiudades() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar ciudades.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaCiudades());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByCiudades), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar ciudades");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Departamentos")
	@ResponseBody
	@ApiOperation(value = "Busca lista departamentos", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByDepartamentos() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar departamentos.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaDepartamentos());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByDepartamentos), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar departamentos");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/TipoTributos")
	@ResponseBody
	@ApiOperation(value = "Busca lista tipo tributos", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByCodTributo() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar tipo tributos.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaCodTributo());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByCodTributo), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar tipo atributos");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/TipoOperacion")
	@ResponseBody
	@ApiOperation(value = "Busca lista tipo de operacion", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByTipoOperacion() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar tipo operacion.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaTipoOperacion());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByTipoOperacion), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar tipo operacion");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/RegimenFiscal")
	@ResponseBody
	@ApiOperation(value = "Busca lista regimen fiscal ", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByRegfiscal() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar regimen fiscal.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaRegFiscal());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByRegfiscal), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar regimen fiiscal");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/UnidadesMedida")
	@ResponseBody
	@ApiOperation(value = "Busca lista unidades de medida ", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByUmedidas() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar unidades de medida.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaUmedidas());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByUmedidas), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar unidades de medida");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Productos")
	@ResponseBody
	@ApiOperation(value = "Busca lista unidades productos", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByProductos() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar unidades de producto.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaProductos());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByProductos), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar unidades de producto");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/EnvioFE")
	@ResponseBody
	@ApiOperation(value = "Busca lista envio FE", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByEnvioFE() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar envio fe.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaEnvioFE());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByEnvioFE), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar envio fe");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/AmbientesDestino")
	@ResponseBody
	@ApiOperation(value = "Busca lista ambientes de destino", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByAmbDestino() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar ambientes de destino.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaAmbDestino());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByAmbDestino), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar ambientes de destino.");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Monedas")
	@ResponseBody
	@ApiOperation(value = "Busca lista monedas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByMonedas() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar monedas.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaMonedas());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByMonedas), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar monedas");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/MediosPago")
	@ResponseBody
	@ApiOperation(value = "Busca lista medios de pago", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByMediosPago() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar medios de pago.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaMediosPago());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByMediosPago), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar medios de pago");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Porcentajes")
	@ResponseBody
	@ApiOperation(value = "Busca lista porcentajes", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<PorcentajesDTO>>> findByPorcentaje() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar porcentajes.");
		RespuestaDTO<List<PorcentajesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaPorcentajes());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByPorcentaje), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar porcentajes");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("/impuesto")
	@ResponseBody
	@ApiOperation(value = "Busca lista porcentajes por impuesto", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<PorcentajesDTO>>> findPorcentajeByImpuesto(
			@RequestParam(name = "valor", required = true) String valor) {
		LOGGER.info("DesplegablesController: inicio servicio de buscar porcentajes.");
		RespuestaDTO<List<PorcentajesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaPorcentajesByImpuesto(valor));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByPorcentaje), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar porcentajes");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/TipoDocumentos")
	@ResponseBody
	@ApiOperation(value = "Busca lista medios de pago", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByTipoDoc() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar medios de pago (findByTipoDoc).");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaTiposDocumento());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByTipoDoc), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar medios de pago (findByTipoDoc).");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Sucursales")
	@ResponseBody
	@ApiOperation(value = "Busca lista sucursales", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegableSucursalesDTO>>> findBySucursales() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar suscursales.");
		RespuestaDTO<List<DesplegableSucursalesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaSucursales());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findBySucursales), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar sucursales");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/EstadoFactura")
	@ResponseBody
	@ApiOperation(value = "Busca lista estado factura", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> findByEstadoFac() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar estado factura.");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaEstadoFac());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByEstadoFac), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar estado factura");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/Categorias")
	@ResponseBody
	@ApiOperation(value = "Busca lista categorias", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegableCategoriaDTO>>> findByCategoria() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar categorias.");
		RespuestaDTO<List<DesplegableCategoriaDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaCategorias());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByCategoria), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar categorias");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("/Fuente")
	@ResponseBody
	@ApiOperation(value = "Busca lista fuente", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<FuenteDTO>>> findByFuente() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar fuente.");
		RespuestaDTO<List<FuenteDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaFuente());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByFuente), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar fuente");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("/tiposPoliza")
	@ResponseBody
	@ApiOperation(value = "Busca lista de tipos de poliza para resolucion", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<DesplegablesDTO>>> buscarTiposPoliza() {
		LOGGER.info("DesplegablesController: inicio servicio de buscar tipos de poliza resoluciones");
		RespuestaDTO<List<DesplegablesDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, desplegablesService.listaTipoPolizas());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en el servicio (findByFuente), error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		LOGGER.info("DesplegablesController: Termino servicio de buscar fuente");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
}
