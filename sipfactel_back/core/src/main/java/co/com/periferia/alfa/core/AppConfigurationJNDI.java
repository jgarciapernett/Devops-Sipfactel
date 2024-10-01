package co.com.periferia.alfa.core;

import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class AppConfigurationJNDI {

	@Value("${spring.datasource.jndi-name}")
	private String jndi;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	private String factory = "org.apache.tomcat.jdbc.pool.DataSourceFactory";
	private String jndiName = "JDBC/SIPFACTELV2";

	@Bean
	public JdbcTemplate jdbcTemplate() {
		try {
			return new JdbcTemplate(jndiDataSource());	
		} catch (BeanInstantiationException | IllegalArgumentException e) {
			return null;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(AppConfigurationJNDI.class);

	@Bean
	public TomcatServletWebServerFactory tomcatFactory() {
		LOG.info("initializing tomcat factory... ");
		return new TomcatServletWebServerFactory() {

			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
				tomcat.enableNaming();
				return new TomcatWebServer(tomcat, getPort() >= 0);
			}

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();
				resource.setName(jndiName);
				resource.setType(DataSource.class.getName());
				resource.setProperty("factory", factory);
				resource.setProperty("driverClassName", driverClassName);
				resource.setProperty("url", url);
				resource.setProperty("username", username);
				resource.setProperty("password", password);
				context.getNamingResources().addResource(resource);
				context.getNamingResources().addResource(resource);
			}
		};
	}

	@Bean(destroyMethod = "")
	public DataSource jndiDataSource() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		try {
			LOG.info("Ingreso a credenciales");
			bean.setJndiName(jndi);
			bean.setProxyInterface(DataSource.class);
			bean.setLookupOnStartup(true);
			bean.afterPropertiesSet();
		} catch (NamingException e) {
			LOG.error("ERROR factory... {}", e.getMessage());
		}
		return (DataSource) bean.getObject();
	}
}
