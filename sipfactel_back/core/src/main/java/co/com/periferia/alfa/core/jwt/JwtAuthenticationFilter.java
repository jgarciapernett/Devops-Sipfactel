package co.com.periferia.alfa.core.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import co.com.periferia.alfa.core.model.admin.Auditoria;
import co.com.periferia.alfa.core.model.admin.Roles;
import co.com.periferia.alfa.core.model.admin.User;
import co.com.periferia.alfa.core.repository.AuditoriaRepository;
import co.com.periferia.alfa.core.repository.RolesRepository;
import co.com.periferia.alfa.core.repository.UserRepository;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	AuditoriaRepository auditoriaRepository;

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			Timestamp timestam = new Timestamp(new java.util.Date().getTime());
			CachedBodyHttpServletRequest body = new CachedBodyHttpServletRequest(request);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				String parameter = body.getParameter("id");
				String bodyRequest = IOUtils.toString(body.getInputStream(), StandardCharsets.UTF_8.name());
				Integer userId = tokenProvider.getUserIdFromJWT(jwt).intValue();
				User user = userRepository.findByID(userId);
				UserDetails userDetails = customUserDetailsService.loadUserById(userId);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				Auditoria auditoria = new Auditoria();
				Roles rol;
				switch (request.getMethod()) {
				case "GET":
					auditoria.setAudiFuncionalidad("CONSULTAR");
					break;
				case "POST":
					auditoria.setAudiFuncionalidad("CREAR");
					break;
				case "PUT":
					auditoria.setAudiFuncionalidad("MODIFICAR");
					break;
				case "DELETE":
					auditoria.setAudiFuncionalidad("ELIMINAR");
					break;
				default:
					break;
				}
				
				try {
					String[] ruta = request.getRequestURI().split("/");
					auditoria.setAudiDescripcion(ruta[2].toUpperCase());
					if(ruta.length > 2) {
						auditoria.setAudiDescripcion(ruta[2].toUpperCase() + "/" + ruta[3].toUpperCase());
					}
				} catch (Exception e){
					auditoria.setAudiDescripcion("error ruta no encontrada");
				}
				
				auditoria.setAudiFecha(timestam);
				auditoria.setAudiIp(request.getRemoteAddr());
				auditoria.setAudiUsuario(user.getUsuaUsuario());
				auditoria.setAudiNombre(user.getUsuaNombres() + " " + user.getUsuaApellidos());
				rol = rolesRepository.roleNombre(user.getUsuaRol());
				auditoria.setAudiRol(rol.getRoleNombre());
				auditoria.setAudiDetalle("id: " + parameter + " body: " + bodyRequest);
				auditoriaRepository.save(auditoria);
			}

			filterChain.doFilter(body, response);
		} catch (Exception ex) {
			LOGGER.error("Error en filtro de la clase JwtAuthenticationFilter | {} ", ex.getMessage());
		}
	}

	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
