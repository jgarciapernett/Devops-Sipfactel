package co.com.periferia.alfa.core.controller;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.services.LdapService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/Ldap")
public class LdapController {

	@Autowired
	private LdapService ldap;

	private static final Logger LOGGER = LoggerFactory.getLogger(LdapController.class);

	@GetMapping("/Buscar")
	@ResponseBody
	@ApiOperation(value = "ldap", authorizations = @Authorization(value = "Bearer"))
	public boolean newldap(String user, String password) {
		boolean respuesta = false;
		try {
			LOGGER.info("Clase EnvioEmail - Parametros |menasaje: ");
			respuesta = ldap.newldap(user, password);
		} catch (NamingException e) {
			LOGGER.error("error al buscar en el LDAP {} | {}", e.getMessage(), e.getStackTrace());
		}
		return respuesta;
	}

}
