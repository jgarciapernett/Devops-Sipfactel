package co.com.periferia.alfa.core.services;

import javax.naming.NamingException;

import org.springframework.stereotype.Component;

@Component
public interface LdapService {
	
	boolean newldap(String user, String pass) throws NamingException;
}
