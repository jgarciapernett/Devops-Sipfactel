package co.com.periferia.alfa.core.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.periferia.alfa.core.AppConfigurationJNDI;
import co.com.periferia.alfa.core.dto.ConsorciadosDto;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase de testeo para la seccion ass Consorcios
 * @author Duvan Rodriguez
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfigurationJNDI.class})
class SeccionConsorcioAssServiceImplTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(SeccionConsorcioAssServiceImplTest.class);
	private static final Double NUMEROPRUEBA = 30.5;
	
	SeccionConsorcioAssServiceImpl consorcioAssService = new SeccionConsorcioAssServiceImpl();

	List<ConsorciadosDto> listaConsorciados = new ArrayList<>();
	
	ConsorciadosDto dto = new ConsorciadosDto();;
	
	/**
	 * Metodo que setea los datos que recibira el servicio que se esta probando
	 */
	
	@BeforeEach
	void setUp() {
		dto.setPartecipationPercent(NUMEROPRUEBA);
		dto.setPartyName(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxRegistrationName(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxCompanyID(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxCompanyIDschemeID(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxCompanyIDschemeName(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxLevelCode(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxLevelCodeListName(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxSchemeID(FacEnum.STRING_PRUEBA.getValor());
		dto.setTaxSchemeName(FacEnum.STRING_PRUEBA.getValor());
		dto.setContactID(FacEnum.STRING_PRUEBA.getValor());
		
		listaConsorciados.add(dto);
		
	}
	
	/**
	 * Metodo que prueba la funcionalidad normal del servicio
	 */
	
	@Test
	void testSeccionConsorciosAss() {
		JSONArray arrayConData = new JSONArray();
		arrayConData = consorcioAssService.seccionConsorciosAss(listaConsorciados);
		JSONObject json = new JSONObject();
		try {
			json = arrayConData.getJSONObject(0);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), json.get(FacEnum.PARTYNAME.getValor()));	
			assertEquals(NUMEROPRUEBA, json.get(FacEnum.PARTECIPATIONPERCENT.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error con el testeo de la seccion Ass (testSeccionConsorciosAss), error: {} ", e.getMessage());
		}
		
	}
	
	/**
	 * Metodo que prueba la funcionalidad cuando entre los parametros recibe un NIT
	 */
	
	@Test
	void testSeccionConsorcioAssRecibeNit() {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		listaConsorciados.get(0).setTaxCompanyIDschemeName(FacEnum.TIPOIDENTIFICACION_NIT.getValor());
		array = consorcioAssService.seccionConsorciosAss(listaConsorciados);
		try {
			json = array.getJSONObject(0);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), json.get(FacEnum.TAX_COMPANYID_SCHEMEID.getValor()));
		} catch(JSONException e) {
			LOG.warn("Ha ocurrido un error con el testeo de la seccion Ass (testSeccionConsorcioAssRecibeNit), error: {} ", e.getMessage());
		}
		
	}
	
	/**
	 * Metodo que prueba la funcionalidad cuando entre los parametros no recibe un NIT
	 */
	
	@Test
	void testSeccionConsorcioAssNoRecibeNit() {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		listaConsorciados.get(0).setTaxCompanyIDschemeName(FacEnum.STRING_PRUEBA.getValor());
		array = consorcioAssService.seccionConsorciosAss(listaConsorciados);
		try {
			json = array.getJSONObject(0);
			assertEquals(FacEnum.NOTWHITE_SPACE.getValor(), json.get(FacEnum.TAX_COMPANYID_SCHEMEID.getValor()));
		} catch(JSONException e) {
			LOG.warn("Ha ocurrido un error con el testeo de la seccion Ass (testSeccionConsorcioAssRecibeNit), error: {} ", e.getMessage());
		}
		
	}
	
	/**
	 * Metodo que prueba la funcionalidad cuando recibe como parametro un arreglo vacio
	 */
	
	@Test
	void testSeccionConsorcioAssRecibeArregloVacio() {
		JSONArray arraySinData = new JSONArray();
		arraySinData = consorcioAssService.seccionConsorciosAss(new ArrayList<>());
		assertEquals(0, arraySinData.length());
	}

}
