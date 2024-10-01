package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.periferia.alfa.core.AppConfigurationJNDI;
import co.com.periferia.alfa.core.dto.DatosNotasDTO;
import co.com.periferia.alfa.core.repository.NotasCreDebRepository;
import co.com.periferia.alfa.core.services.ISeccionDocumentosBrfService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfigurationJNDI.class})
class SeccionDocumentosBrfServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionDocumentosBrfServiceImplTest.class);
	
	@TestConfiguration
	class SeccionDocumentosBrfServiceImplTestContextConfiguration {
		@Bean
		public ISeccionDocumentosBrfService service () {
			return new SeccionDocumentosBrfServiceImpl();
		}
	}
	
	@Autowired
	private ISeccionDocumentosBrfService service;
	
	@MockBean
	private NotasCreDebRepository notasCreDebRepository;
	
	@BeforeEach
	void setUp() {
		DatosNotasDTO datos = new DatosNotasDTO();
		datos.setInvoiceID(FacEnum.STRING_PRUEBA.getValor());
		datos.setInvoiceUUID(FacEnum.STRING_PRUEBA.getValor());
		datos.setInvoiceIssueDate(FacEnum.STRING_PRUEBA.getValor());
		Mockito.when(notasCreDebRepository.getBrf(5)).thenReturn(datos);
	}
	
	@Test
	void testSeccionDocumentosBrf() {
		JSONArray array = service.seccionDocumentosBrf(5);
		try {
			JSONObject json = array.getJSONObject(0);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), json.get(FacEnum.INVOICE_ID.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error con el testo de la seccion de documentos brf, error: {} ", e.getMessage());
		}
	
	}

}
