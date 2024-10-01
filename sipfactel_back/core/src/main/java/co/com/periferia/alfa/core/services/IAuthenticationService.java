package co.com.periferia.alfa.core.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import co.com.periferia.alfa.core.dto.JwtAuthenticationResponseDTO;
import co.com.periferia.alfa.core.dto.LoginRequestDTO;
import co.com.periferia.alfa.core.dto.RefreshTokenRequestDTO;
import co.com.periferia.alfa.core.dto.UserDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.admin.User;

public interface IAuthenticationService {

	JwtAuthenticationResponseDTO authenticateUser(LoginRequestDTO loginRequest, HttpServletRequest http)
			throws ExcepcionSegAlfa;

	void logOut(String ip) throws ExcepcionSegAlfa;

	ResponseEntity<User> registerUser(UserDTO signUpRequest, HttpServletRequest http) throws ExcepcionSegAlfa;

	ResponseEntity<JwtAuthenticationResponseDTO> refreshToken(RefreshTokenRequestDTO refreshTokenRequest, HttpServletRequest http)
			throws ExcepcionSegAlfa;

	JwtAuthenticationResponseDTO newrefreshToken(RefreshTokenRequestDTO refreshTokenRequest, HttpServletRequest http) throws ExcepcionSegAlfa;

	/**
	 * JwtAuthenticationResponseDTO authenticateUserLdap(LoginRequestDTO loginRequest, HttpServletRequest http)
	 * throws ExcepcionSegAlfa;
	 */		
}
