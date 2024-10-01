package co.com.periferia.alfa.core.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenData {

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	JwtTokenProvider tokenProvider;
	
	public  String extractName ( HttpServletRequest http) {
		String jwt = jwtAuthenticationFilter.getJwtFromRequest(http);
	return tokenProvider.getUserNameFromJWT(jwt);
	}
	
	public  String extractIp ( HttpServletRequest http) {
		return http.getRemoteAddr();
	}
}
