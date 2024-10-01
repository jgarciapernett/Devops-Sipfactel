package co.com.periferia.alfa.core.services.impl;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.ldap.src.app.ActiveDirectoryHelper;
import co.com.periferia.alfa.core.model.DatosDelcopModel;
import co.com.periferia.alfa.core.repository.DatosDelcopRepository;
import co.com.periferia.alfa.core.services.LdapService;

@Component
public class LdapServiceImpl implements LdapService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(LdapServiceImpl.class);

	@Autowired
	ActiveDirectoryHelper ldap;

	@Autowired
	DatosDelcopRepository datosDelcopRepository;

	/**
	 * url_ldap = port = distinguisedNameCnUser = BD = Administrador SIPFACTEL
	 * grupos = OU base= DC passwordCN =
	 */

	public boolean newldap(String user, String pass) throws NamingException {

		DatosDelcopModel datos = datosDelcopRepository.getOne(1);

		// service user
		String serviceUserDN = datos.getConfigCN() + "," + datos.getConfigOU() + "," + datos.getConfigDC();
		String serviceUserPassword = datos.getPassLdap();

		// user to authenticate
		String identifyingAttribute = "SamAccountName";
		String identifier = user;
		String password = pass;
		String base = datos.getConfigDC();

		// LDAP connection info
		String ldapUrl = datos.getIpLdap();

		// first create the service context
		DirContext serviceCtx = null;
		try {
			LOGGER.info("Ejecutando newldap - Parametros |usuario: {} | password: {} ", user, pass);
			// use the service user to authenticate
			Properties serviceEnv = new Properties();
			serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			serviceEnv.put(Context.PROVIDER_URL, ldapUrl);
			serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			serviceEnv.put(Context.SECURITY_PRINCIPAL, serviceUserDN);
			serviceEnv.put(Context.SECURITY_CREDENTIALS, serviceUserPassword);
			serviceCtx = new InitialDirContext(serviceEnv);

			// we don't need all attributes, just let it get the identifying one
			String[] attributeFilter = { identifyingAttribute };
			SearchControls sc = new SearchControls();
			sc.setReturningAttributes(attributeFilter);
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// use a search filter to find only the user we want to authenticate
			String searchFilter = "(" + identifyingAttribute + "=" + identifier + ")";
			NamingEnumeration<SearchResult> results = serviceCtx.search(base, searchFilter, sc);

			if (results.hasMore()) {
				// get the users DN (distinguishedName) from the result
				SearchResult result = results.next();
				String distinguishedName = result.getNameInNamespace();
				LOGGER.info("Dist Name: {} ", distinguishedName);

				// attempt another authentication, now with the user
				Properties authEnv = new Properties();
				authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				authEnv.put(Context.PROVIDER_URL, ldapUrl);
				authEnv.put(Context.SECURITY_PRINCIPAL, distinguishedName);
				authEnv.put(Context.SECURITY_CREDENTIALS, password);
				new InitialDirContext(authEnv);
				return true;
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar newldap");
			e.printStackTrace();
		} finally {
			if (serviceCtx != null) {
				try {
					serviceCtx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
		LOGGER.error("Authentication failed");
		return false;
	}
}
