package co.com.periferia.alfa.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Clase que mapea las propiedades de activar o no el ldap
 * @author Duvan Rodriguez
 * @date 16/08/2024
 */

@Component
@ConfigurationProperties(prefix = "ldap")
public class LdapActive {

	private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean valid) {
        this.active = valid;
    }
	
}
