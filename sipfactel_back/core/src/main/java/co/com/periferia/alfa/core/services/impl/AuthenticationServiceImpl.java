package co.com.periferia.alfa.core.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.config.properties.LdapActive;
import co.com.periferia.alfa.core.dto.AlertaDTO;
import co.com.periferia.alfa.core.dto.JwtAuthenticationResponseDTO;
import co.com.periferia.alfa.core.dto.LoginRequestDTO;
import co.com.periferia.alfa.core.dto.MenuDTO;
import co.com.periferia.alfa.core.dto.RefreshTokenRequestDTO;
import co.com.periferia.alfa.core.dto.UserDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.jwt.JwtTokenProvider;
import co.com.periferia.alfa.core.jwt.UserPrincipal;
import co.com.periferia.alfa.core.ldap.src.app.ActiveDirectoryHelper;
import co.com.periferia.alfa.core.model.admin.Menu;
import co.com.periferia.alfa.core.model.admin.Roles;
import co.com.periferia.alfa.core.model.admin.Ronu;
import co.com.periferia.alfa.core.model.admin.Sesion;
import co.com.periferia.alfa.core.model.admin.UsaRol;
import co.com.periferia.alfa.core.model.admin.User;
import co.com.periferia.alfa.core.repository.AuditoriaRepository;
import co.com.periferia.alfa.core.repository.MenuRepository;
import co.com.periferia.alfa.core.repository.RolesRepository;
import co.com.periferia.alfa.core.repository.RonuRepository;
import co.com.periferia.alfa.core.repository.SesionRepository;
import co.com.periferia.alfa.core.repository.UserRepository;
import co.com.periferia.alfa.core.repository.UsuaRolRepository;
import co.com.periferia.alfa.core.services.IAuthenticationService;
import co.com.periferia.alfa.core.utilitarios.NombreEstado;

@Component
public class AuthenticationServiceImpl implements IAuthenticationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuditoriaRepository auditoriaRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenData jwtTokenData;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UserPrincipal userPrincipal;

	@Autowired
	SesionRepository sesionRepository;

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	UsuaRolRepository usuaRolRepository;

	@Autowired
	RonuRepository ronuRepository;

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	private UtilExcecion utilException;

	@Autowired
	LdapServiceImpl newldap;

	@Autowired(required = true)
	ActiveDirectoryHelper ldap;
	
	@Autowired
	AlertasServiceImpl alertasServiceImpl;
	
	@Autowired
	private LdapActive ldapActive;

	@Override
	public JwtAuthenticationResponseDTO authenticateUser(LoginRequestDTO loginRequest, HttpServletRequest http)
			throws ExcepcionSegAlfa {
		LOGGER.info("AuthenticationServiceImpl: Ingreso a metodo authenticateUser, parametros | loginRequest: {} | http: {} ", loginRequest, http);
		User user = userRepository.findByName(loginRequest.getUserName());
		List<Sesion> ip = new ArrayList<>();
		Timestamp timestam = new Timestamp(new java.util.Date().getTime());
		List<Roles> listRoleResponse = new ArrayList<>();
		List<MenuDTO> listMenuResponse = new ArrayList<>();
		List<AlertaDTO> listAlerta;
		try {
			ip.add(sesionRepository.findSesionByIp(jwtTokenData.extractIp(http)));
		} catch (NonUniqueResultException | IncorrectResultSizeDataAccessException ex) {
			LOGGER.error("Error en authenticateUser: error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		boolean auten = ldapActive.isActive() ? authLdap(loginRequest) : true;
		if (auten) {
			if (!ip.isEmpty()) {
				sesionRepository.logOut(http.getRemoteAddr());
			}
			if (user != null) { 
				if(user.getUsuaEstado().equals(NombreEstado.ESTADO_ACTIVO)) {
					try {
						String jwt = tokenProvider.generateOrRefreshToken(user, http);
						String refreshToken = tokenProvider.generateRefreshToken();
						Sesion sesion = llenarSesion(user.getUsuaUsua(), jwt, timestam, jwtTokenData.extractIp(http), refreshToken);
	
						List<UsaRol> listUsuaRol = usuaRolRepository.findByUserId(user.getUsuaUsua());
						
						obtenerListaMenu(listUsuaRol, listRoleResponse, listMenuResponse);
						
						listAlerta = alertasServiceImpl.calcularAlertasResolucion();
						
						sesionRepository.save(sesion);
						userRepository.save(user);
						return new JwtAuthenticationResponseDTO(jwt, refreshToken, user, listMenuResponse,
								listRoleResponse, listAlerta);
					} catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException e) {
						LOGGER.error("Error en metodo authenticateUser, error: {} | {} ", e.getMessage(), e.getStackTrace());
						throw utilException.getById(2).crearExcepcion();
					}
				} else {
					LOGGER.error("Error en metodo authenticateUser, el estado del usuario es {} ", user.getUsuaEstado());
					throw utilException.getById(3).crearExcepcion();
				}
			}
		} else {
			LOGGER.error("Error en metodo authenticateUser, boolean auten = {} ", auten);
			throw utilException.getById(14).crearExcepcion();
		}
		return new JwtAuthenticationResponseDTO("", "");
	}
	
	/**
	 * Metodo que autentica al usuario en el Directorio Activo Ldap
	 * @return boolean
	 */
	private boolean authLdap(LoginRequestDTO loginRequest) {
		boolean auten = false;
		try {
			auten = newldap.newldap(loginRequest.getUserName(), loginRequest.getPassword());
		} catch (NamingException ex) {
			LOGGER.error("Error en authenticateUser: error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		return auten;
	}

	@Override
	public void logOut(String ip) throws ExcepcionSegAlfa {
		LOGGER.info("AuthenticationServiceImpl: Ingreso al metodo logOut, parametro - ip {} ", ip);
		try {
			sesionRepository.logOut(ip);
		} catch (Exception ex) {
			LOGGER.info("Error en el servicio de logout, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
	}

	@Override
	public ResponseEntity<JwtAuthenticationResponseDTO> refreshToken(RefreshTokenRequestDTO refreshTokenRequest, HttpServletRequest http) throws ExcepcionSegAlfa {
		LOGGER.info("AuthenticationServiceImpl: Ingreso al metodo refreshToken, parametros | refreshTokenRequest: {} | http: {} ", refreshTokenRequest, http);
		ResponseEntity<JwtAuthenticationResponseDTO> respuesta = null;
		try {
			User user = userRepository
					.findByID(sesionRepository.findSesionByUUID(refreshTokenRequest.getRefreshToken()).getSesiUsuario());
			String accessToken = tokenProvider.generateOrRefreshToken(user, http);
			String refreshToken = tokenProvider.generateRefreshToken();
			Sesion sesionUpdate = sesionRepository.findSesionByUUID(refreshTokenRequest.getRefreshToken());
			sesionUpdate.setSesiRefreshToken(refreshToken);
			sesionRepository.save(sesionUpdate);
			respuesta = ResponseEntity.ok(new JwtAuthenticationResponseDTO(accessToken, refreshToken
					/*
					 * sesionRepository.findSesionByUUID(refreshTokenRequest.getRefreshToken()).
					 * getSesiToken()
					 */));
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al refrescar el token, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}

		return respuesta;
	}

	@Override
	public JwtAuthenticationResponseDTO newrefreshToken(RefreshTokenRequestDTO refreshTokenRequest,
			HttpServletRequest http) throws ExcepcionSegAlfa {
		LOGGER.info("AuthenticationServiceImpl: Ingreso al metodo newrefreshToken, parametros | refreshTokenRequest: {} | http: {} ", refreshTokenRequest, http);
		User user = null;
		String accessToken = null;
		String refreshToken = null;
		Sesion sesionUpdate = null;
		List<Roles> listRoleResponse = new ArrayList<>();
		List<MenuDTO> listMenuResponse = new ArrayList<>();
		List<AlertaDTO> listAlerta = new ArrayList<>();
		try {
	    user = userRepository
				.findByID(sesionRepository.findSesionByUUID(refreshTokenRequest.getRefreshToken()).getSesiUsuario());
		accessToken = tokenProvider.generateOrRefreshToken(user, http);
		refreshToken = tokenProvider.generateRefreshToken();
		sesionUpdate = sesionRepository.findSesionByUUID(refreshTokenRequest.getRefreshToken());
		sesionUpdate.setSesiRefreshToken(refreshToken);
		sesionUpdate.setSesiToken(accessToken);
			sesionRepository.save(sesionUpdate);
			List<UsaRol> listUsuaRol = usuaRolRepository.findByUserId(user.getUsuaUsua());
			obtenerListaMenu(listUsuaRol, listRoleResponse, listMenuResponse);
			listAlerta = alertasServiceImpl.calcularAlertasResolucion();
		} catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException  ex) {
			LOGGER.error("Error en newRefreshToken, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		
		return new JwtAuthenticationResponseDTO(accessToken, refreshToken, user, listMenuResponse, listRoleResponse, listAlerta);

	}

	@Override
	public ResponseEntity<User> registerUser(UserDTO signUpRequest, HttpServletRequest http) throws ExcepcionSegAlfa {
		LOGGER.info("AuthenticationServiceImpl: Ingreso al metodo registerUser, parametros | signUpRequest: {} | http: {} ", signUpRequest, http);
		User result = null;
		try {
			User user = signUpRequest.dtoAModelo(signUpRequest);
			user.setUsuaContrasenia(passwordEncoder.encode(user.getUsuaContrasenia()));
			user.setUsuaUcreacion("Paula");
			user.setUsuaFcreacion(new Timestamp(new java.util.Date().getTime()));
		    result = userRepository.save(user);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Error en el registro de usuario, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Metodo que llena el objeto Sesion
	 * @param user
	 * @param jwt
	 * @param timestamp
	 * @param ip
	 * @param refreshToken
	 * @return
	 */
	private Sesion llenarSesion(int user, String jwt, Timestamp timestamp,
			String ip, String refreshToken) {
		Sesion sesion = new Sesion();
		sesion.setSesiUsuario(user);
		sesion.setSesiToken(jwt);
		sesion.setSesiFecha(timestamp);
		sesion.setSesiIp(ip);
		sesion.setSesiRefreshToken(refreshToken);
		return sesion;
	}
	
	
	/**
	 * Metodo que llena las listas de Menu y Rol
	 * @param listUsuaRol
	 * @param menuhijos
	 * @param listRoleResponse
	 * @param listMenuResponse
	 */
	private void obtenerListaMenu(List<UsaRol> listUsuaRol, 
			List<Roles> listRoleResponse, List<MenuDTO> listMenuResponse) {
		for (UsaRol usaRol : listUsuaRol) {
			List<Roles> listRoles = rolesRepository.findRolesByUser(usaRol.getRolesRoles());
			for (Roles rol : listRoles) {
				List<Ronu> listRonu = ronuRepository.findMenuByRol(rol.getRoleRole());
				for (Ronu ronu : listRonu) {
					List<Menu> menu = menuRepository.findMenuByRol(ronu.getMenuMenu());
					for (Menu menup : menu) {
						List<Ronu> listRonu1 = ronuRepository.findMenuByhijo(menup.getMenuMenu(),
								ronu.getRolesRoles());
						List<MenuDTO> menuhijos = new ArrayList<>();
						for (Ronu ronu1 : listRonu1) {
							Menu menuhijo = menuRepository.findMenuByHijo(ronu1.getMenuMenu());
							menuhijos.add(new MenuDTO().modeloAdto(menuhijo, ronu1.getCrear(),
									ronu1.getConsultar(), ronu1.getModificar()));
						}
						listMenuResponse.add(new MenuDTO().modeloAdtomenu(menup, menuhijos, ronu.getCrear(),
								ronu.getConsultar(), ronu.getModificar()));
					}
				}
			}
			listRoleResponse.add(rolesRepository.getOne(usaRol.getRolesRoles()));
		}
	}
	
	

}
