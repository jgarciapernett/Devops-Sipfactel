package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.periferia.alfa.core.dto.EmisorDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que testea la seccion de emisor
 * @author Duvan Rodriguez
 */

class SeccionEmisorAspServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionEmisorAspServiceImplTest.class);

	private SeccionEmisorAspServiceImpl service = new SeccionEmisorAspServiceImpl();

	private EmisorDTO emisorDto = new EmisorDTO();

	/**
	 * Metodo que setea los parametros que recibira el servicio
	 */
	
	@BeforeEach
	void setUp() {
		emisorDto.setAdditionalAccountID(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setPartyName(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setPhysicalAddID(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxRegistrationName(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxCompanyID(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxCompanyIDschemeID(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxCompanyIDschemeName(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxLevelCode(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxLevelCodeListName(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxSchemeID(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setTaxSchemeName(FacEnum.STRING_PRUEBA.getValor());
		emisorDto.setRegistrationAddID(FacEnum.STRING_PRUEBA.getValor());
	}

	/**
	 * Metodo que testea el flujo normal del servicio
	 */
	
	/*@Test
	void testSeccionEmisorAsp() {
	/*	JSONObject asp = new JSONObject();
		try {
			asp = service.seccionEmisorAsp(this.emisorDto);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.ADDITIONALACCOUNTID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.PARTYNAME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.PHYSICAL_ADD_ID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_REGISTRATIONNAME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_COMPANYID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_COMPANYID_SCHEMEID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_COMPANYID_SCHEMENAME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_LEVELCODE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_LEVELCODE_LISTNAME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_SCHEME_ID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.TAX_SCHEME_NAME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), asp.get(FacEnum.REGISTRATION_ADD_ID.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error en el test de la seccion de emisor asp, error: {} ", e.getMessage());
		}
	}*/

	/**
	 * Metodo que testea el servicio cuando este recibe un null como parametro
	 */
	
	@Test
	void testSeccionEmisorAspRecibeNull() {
		JSONObject asp = service.seccionEmisorAsp(null);
		assertEquals(0, asp.length());

	}

}
