package co.com.periferia.alfa.core.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import co.com.periferia.alfa.core.CoreApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
@WebAppConfiguration
public class SpringBootWebApplicationTests {
	
	@Test
	public void contextLoads() {
		boolean context = true;
		assertThat(context).isTrue();
	}
}
