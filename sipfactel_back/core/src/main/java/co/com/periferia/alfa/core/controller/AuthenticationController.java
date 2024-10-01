package co.com.periferia.alfa.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.periferia.alfa.core.dto.JwtAuthenticationResponseDTO;
import co.com.periferia.alfa.core.dto.LoginRequestDTO;
import co.com.periferia.alfa.core.dto.RefreshTokenRequestDTO;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.dto.UserDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.admin.User;
import co.com.periferia.alfa.core.services.IJsonService;
import co.com.periferia.alfa.core.services.impl.AlertasServiceImpl;
import co.com.periferia.alfa.core.services.impl.AuthenticationServiceImpl;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	AuthenticationServiceImpl authenticationService;
	
	@Autowired
	AlertasServiceImpl alertasServiceImpl;
	
	@Autowired
	private IJsonService service;

	@PostMapping("/signin")
	public ResponseEntity<RespuestaDTO<JwtAuthenticationResponseDTO>> authenticateUsers(
			@RequestBody LoginRequestDTO loginRequest, HttpServletRequest http) {
		RespuestaDTO<JwtAuthenticationResponseDTO> respuesta;
		LOGGER.info("AuthenticationController: inicio servicio de signin, parametros | loginRequest: {} | http: {} ", loginRequest, http);
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					authenticationService.authenticateUser(loginRequest, http));
						
			LOGGER.info("Respuesta exitosa al hacer login {}", respuesta);
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida al hacer login, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuthenticationController: Termino el servicio de signin");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);

	}

	// @PostMapping("/signup")
	public ResponseEntity<User> registerUsers(@Valid @RequestBody UserDTO signUpRequest, HttpServletRequest http) {
		LOGGER.info("AuthenticationController: inicio servicio de registrar usuario, parametros | UserDTO: {} | http: {} ", signUpRequest, http);
		ResponseEntity<User> respuesta = null;
		try {
			respuesta = authenticationService.registerUser(signUpRequest, http);
		}catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error en la respuesta del servicio, error: {} | {}", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuthenticationController: Termino el servicio de registrar usuario");
		return respuesta;
	}

	@PostMapping("/logout")
	public void logOutUsers(HttpServletRequest http) {
		LOGGER.info("AuthenticationController: inicio servicio de logout, parametros | http: {}", http);
		try {
			authenticationService.logOut(http.getRemoteAddr());
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.info("Error en el servicio de logout, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuthenticationController: termino servicio de logout");
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<JwtAuthenticationResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest,
			HttpServletRequest http) {
		LOGGER.info("AuthenticationController: inicio servicio de refreshToken, parametros | refreshTokenRequest: {} | http: {}", refreshTokenRequest,  http);
		ResponseEntity<JwtAuthenticationResponseDTO> respuesta = null;
		try {
			respuesta = authenticationService.refreshToken(refreshTokenRequest, http);
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.info("Error en el servicio de logout, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuthenticationController: Termino el servicio de refreshToken");
		return respuesta;
	}

	@PostMapping("/Newrefreshtoken")
	public ResponseEntity<RespuestaDTO<JwtAuthenticationResponseDTO>> newrefreshToken(
			@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest, HttpServletRequest http) {
		LOGGER.info("AuthenticationController: inicio servicio de newrefreshToken, parametros | refreshTokenRequest: {} | http: {}", refreshTokenRequest,  http);
		RespuestaDTO<JwtAuthenticationResponseDTO> respuesta;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					authenticationService.newrefreshToken(refreshTokenRequest, http));
		} catch (ExcepcionSegAlfa ex) {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.ERROR_SOLICITUD_RESPUESTA, ex);
			LOGGER.error("Respuesta fallida en el newRefreshToken, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("AuthenticationController: Termino el servicio de newrefreshToken");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@GetMapping("/descargar")
	public ResponseEntity<Object> descargarReporte(Integer dni) {
		return service.consultar(dni);
	}
	
}
