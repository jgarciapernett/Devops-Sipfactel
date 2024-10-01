package co.com.periferia.alfa.core.ldap.src.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

	@Bean
	public LdapContextSource getContextSource(){
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://172.168.10.37:389");
		ldapContextSource.setBase("OU=Factory,OU=Holding,DC=PERIFERIAGROUP,DC=MS");		
		ldapContextSource.setBase("CN=prueba-ro,CN=Holding,DC=periferiagroup,DC=ms");		
		ldapContextSource.setBase("CN=Holding,DC=periferiagroup,DC=ms");	
		
		/**
		 * ldapContextSource.setUrl("ldap://192.168.29.9:389");
		 * ldapContextSource.setBase("CN=Administrador SIPFACTEL,OU=Sophos,OU=Contratistas,OU=Gerencia de Tecnología,OU=ALFA,DC=segurosalfa,DC=net");
		 */
		return ldapContextSource;
	}
	
	@Bean
	public LdapTemplate ldapTemplate(){
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());
		return ldapTemplate;
	}	
}
