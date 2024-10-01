package co.com.periferia.alfa.core.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.CompaniasDTO;
import co.com.periferia.alfa.core.dto.OpcCompaniasDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.services.CompaniasService;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/companias")
public class CompaniasController {

	@Autowired
	private CompaniasService companiasService;
	
	@Autowired
	public JwtTokenData jtd;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompaniasController.class);

	@GetMapping("/opciones")
	@ResponseBody
	@ApiOperation(value = "Lista las opciones", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<List<OpcCompaniasDTO>>> opciones() {
		LOGGER.info("CompaniasController: inicio servicio de opciones");
		RespuestaDTO<List<OpcCompaniasDTO>> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, companiasService.listaOpciones());
			LOGGER.info("Respuesta exitosa al traer las opciones {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer las opciones, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("CompaniasController: termino servicio de opciones");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("/buscarID")
	@ResponseBody
	@ApiOperation(value = "Busca en el registro por ID", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<CompaniasDTO>> findById(@RequestParam(name="id",required=false) Integer  id) {
		LOGGER.info("CompaniasController: inicio servicio de buscar registro por id, parametro | id: {} ", id);
		RespuestaDTO<CompaniasDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, companiasService.buscarPorID(id));
			LOGGER.info("Respuesta exitosa al traer el registro {} ", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer el registro, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("CompaniasController: termino servicio de buscar registro por id");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}
	
	@PutMapping("/editar")
	@ResponseBody
	@ApiOperation(value = "Cambio en el registro", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> agregar(@RequestParam Integer  id, @RequestBody CompaniasDTO dto) {
		LOGGER.info("CompaniasController: inicio servicio de agergar registro, parametro | id: {} | CompaniasDTO: {} ", id, dto);
		RespuestaDTO<String> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, companiasService.editar(dto, id));
			LOGGER.info("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer el registro, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("CompaniasController: termino servicio de agergar registro");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}
	
	
	@PostMapping("/crear")
	@ResponseBody
	@ApiOperation(value = "crear una compañia", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<CompaniasDTO>> crear(@RequestBody CompaniasDTO dto, HttpServletRequest http) {
		LOGGER.info("CompaniasController: inicio servicio de crear compañia, parametro | CompaniasDTO: {} | http: {} ", dto, http);
		RespuestaDTO<CompaniasDTO> respuesta;
		dto.setUcreacion(jtd.extractName(http));
        dto.setFcreacion(new Date());
        dto.setEstado("TRUE");
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK, companiasService.guardar(dto));
			LOGGER.info("Respuesta exitosa al traer el registro {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al traer el registro, erro: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("CompaniasController: termino servicio de crear compañia");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);		
	}
	
	
	
}
