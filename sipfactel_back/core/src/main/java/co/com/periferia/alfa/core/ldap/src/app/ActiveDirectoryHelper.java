package co.com.periferia.alfa.core.ldap.src.app;

import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;

@Component
public class ActiveDirectoryHelper {

	@Autowired
	private LdapContextSource contextSource;

	public ActiveDirectoryHelper() {
		super();
	}

	public boolean authenticate(String userDn, String credentials) {
		DirContext ctx = null;
		try {
			ctx = contextSource.getContext(userDn, credentials);
			return true;
		}
		catch (NamingException e) {
			return false;
		}
		finally {
			LdapUtils.closeContext(ctx);
		}
	}
}
