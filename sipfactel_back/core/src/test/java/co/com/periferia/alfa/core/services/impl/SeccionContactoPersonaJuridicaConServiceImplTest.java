package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.periferia.alfa.core.dto.ReceptorDTO;
import co.com.periferia.alfa.core.dto.ReceptorNotasDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase para testear la seccion del contacto de la persona juridica
 * @author Duvan Rodriguez
 */

class SeccionContactoPersonaJuridicaConServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionContactoPersonaJuridicaConServiceImplTest.class);
	
	private SeccionContactoPersonaJuridicaConServiceImpl service = new SeccionContactoPersonaJuridicaConServiceImpl();
	private ReceptorDTO receptorDto = new ReceptorDTO();
	private ReceptorNotasDTO receptorNotasDto = new ReceptorNotasDTO();
	
	/**
	 * Metodo que setea los parametros que recebira el servicia que se esta testeando
	 */
	
	@BeforeEach
	void setUp() {
		receptorDto.setContactID(FacEnum.STRING_PRUEBA.getValor());
		receptorDto.setContactName(FacEnum.STRING_PRUEBA.getValor());
		
		receptorNotasDto.setContactID(FacEnum.STRING_PRUEBA.getValor());
		receptorNotasDto.setContactName(FacEnum.STRING_PRUEBA.getValor());
	}
	
	/**
	 * Metodo qeu testea el flujo normal del servicio
	 */
	
	@Test
	void testSeccionPersonaJuridicaCon() {
		try {
			JSONArray jsonReceptorDto = service.seccionPersonaJuridicaCon(receptorDto);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), jsonReceptorDto.getJSONObject(0).get(FacEnum.CONTACT_NAME.getValor()));
			
			JSONArray jsonReceptorNotasDto = service.seccionPersonaJuridicaCon(receptorNotasDto);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), jsonReceptorNotasDto.getJSONObject(0).get(FacEnum.CONTACT_NAME.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error con el json de la seccion de persona juridica, error: {} ", e.getMessage());
		}				
		
	}

	/**
	 * Metodo qeu testea el flujo cuando el servicio recibe como parametro un null
	 */
	
	@Test
	void testSeccionPersonaJuridicaConRecibeNull() {
			JSONArray json = service.seccionPersonaJuridicaCon(null);
			assertEquals(0, json.length());
	}
	
}
