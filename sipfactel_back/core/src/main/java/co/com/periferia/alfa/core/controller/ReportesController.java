package co.com.periferia.alfa.core.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.dto.TablaReporteContableDTO;
import co.com.periferia.alfa.core.dto.TablaReporteEstadoDTO;
import co.com.periferia.alfa.core.excel.PruebaImagen;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.IReportesService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/Reportes")
public class ReportesController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportesController.class);

	@Autowired
	private IReportesService reportesService;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	PruebaImagen pruebaImagen;

	@GetMapping("/FiltroReporteContable")
	@ResponseBody 
	@ApiOperation(value = "consume el servicio de filtrar el reporte contable", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<TablaReporteContableDTO>>> filtrarReporteContable(String fechaini,
			String fechafin, Integer tipodoc, String numdoc, String numpoliza) {
		RespuestaDTO<List<TablaReporteContableDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					reportesService.filtroReporteContable(fechaini, fechafin, tipodoc, numdoc, numpoliza));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al filtrar el reporte contable {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@GetMapping("/GenerarExcel")
	@ResponseBody
	@ApiOperation(value = "Genera el excel detalle facturas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<InputStreamResource> generarExcel(String fechaini, String fechafin, Integer tipodoc,
			String numdoc, String numpoliza, String compania) {
		ResponseEntity<InputStreamResource> resultado = null;
		try {
			resultado = reportesService.detalleExcel(fechaini, fechafin, tipodoc, numdoc, numpoliza, compania);
			return resultado;
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al generar el excel {} {}", ex.getMessage(), ex.getStackTrace());
			return resultado;
		}
	}

	@GetMapping("/GenerarExcelEstado")
	@ResponseBody
	@ApiOperation(value = "Genera el excel estado facturas", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<InputStreamResource> generarExcelEstado(Integer estado, String fechaini, String fechafin,
			String numpoliza, String numtransaccion, String compania, String sucursal) {
		ResponseEntity<InputStreamResource> resultado = null;
		try {
			resultado = reportesService.estadoExcel(estado, fechaini, fechafin, numpoliza, numtransaccion, compania,
					sucursal);
			return resultado;
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al Generar el excel estado facturas {} | {}", ex.getMessage(), ex.getStackTrace());
			return resultado;
		}
	}

	@GetMapping("/FiltroReporteEstado")
	@ResponseBody
	@ApiOperation(value = "consume el servicio de filtrar el reporte estado", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<TablaReporteEstadoDTO>>> filtrarReporteEstable(Integer estado,
			String fechaini, String fechafin, String numpoliza, String numdoc, String compania, String sucursal) {
		LOGGER.info("Entro al metodo controller de reporte estado");
		RespuestaDTO<List<TablaReporteEstadoDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, reportesService.filtroReporteEstado(estado, fechaini,
					fechafin, numpoliza, numdoc, compania, sucursal));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al filtrar el reporte estado {} | {}", ex.getMessage(), ex.getStackTrace());
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
		}
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Controller para saber la cantidad de adquirientes que falata por reportar
	 * @return ResponseEntity<RespuestaDTO<Integer>> - solo retorna el numero de la cantidad de adquirientes faltanres
	 */
	
	@GetMapping("/adquirientes/cantidad")
	@ResponseBody
	@ApiOperation(value = "Genera el excel reporte adquirientes", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<Integer>> reporteAdquirienteCantidad(){
		LOGGER.info("Entro a metodo de reporte adquiriente cantidad");
		RespuestaDTO<Integer> respuesta;
		respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, reportesService.reporteAdquirientesCantidad());
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	/**
	 * Controller para la generacion del excel de adquirientes
	 * @return ResponseEntity<InputStreamResource> - Excel
	 */
	
	@GetMapping("/generar/excel/adquirientes")
	@ResponseBody
	@ApiOperation(value = "consume el servicio de cantidad faltante de adquirientes", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<InputStreamResource> genrarExcelAdquirientes(){
		ResponseEntity<InputStreamResource> excelAdquirientes = null;
		try {
			excelAdquirientes = reportesService.reporteAdquirientesExcel();
			return excelAdquirientes;
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("error al generar el excel {} {}", ex.getMessage(), ex.getStackTrace());
			return excelAdquirientes;
		}
	}

}
