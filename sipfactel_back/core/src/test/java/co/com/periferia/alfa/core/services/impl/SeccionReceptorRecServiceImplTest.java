package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.periferia.alfa.core.dto.ReceptoresDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que testea la seccion receptor REC
 * @author Duvan Rodriguez
 */

class SeccionReceptorRecServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionReceptorRecServiceImpl.class);
	
	private SeccionReceptorRecServiceImpl service = new SeccionReceptorRecServiceImpl();
	
	private ReceptoresDTO receptoresDto = new ReceptoresDTO();
	
	/**
	 * Metodo que setea los parametros que recibira el servicio 
	 */
	
	@BeforeEach
	void setUp() {
		
		receptoresDto.setNombre(FacEnum.STRING_PRUEBA.getValor());
		receptoresDto.setEmail(FacEnum.STRING_PRUEBA.getValor());
		receptoresDto.setEnviarEmail(false);
		receptoresDto.setIncluirAnexos(false);
		receptoresDto.setIncluirPDF(false);
		receptoresDto.setIncluirXML(false);
	}
	
	/**
	 * Metodo que testea el flujo normal del servicio
	 */
	
	/*@Test
	void testSeccionReceptorRec() {
		/*JSONArray array = new JSONArray();
		try {
			array = service.seccionReceptorRec(receptoresDto);
			JSONObject propiedad = array.getJSONObject(0);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.NOMBRE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.EMAIL.getValor()));
			assertEquals(false, propiedad.get(FacEnum.ENVIAR_EMAIL.getValor()));
			assertEquals(false, propiedad.get(FacEnum.INCLUIR_ANEXOS.getValor()));
			assertEquals(false, propiedad.get(FacEnum.INCLUIR_PDF.getValor()));
			assertEquals(false, propiedad.get(FacEnum.INCLUIR_XML.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error en el testeo de la seccion receptor datos de contacto, error: {} ", e.getMessage());
		}
	}*/
	
	/**
	 * Metodo qeu etstea el servicio cuando este recibe como parametro un null
	 */
	
	@Test
	void testSeccionReceptorRecRecibeNull () {
		JSONArray array = service.seccionReceptorRec(null);
		assertEquals(0, array.length());
	}

}
