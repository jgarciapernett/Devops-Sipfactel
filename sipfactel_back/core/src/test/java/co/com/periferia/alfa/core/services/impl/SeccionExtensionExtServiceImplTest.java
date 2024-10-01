package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.periferia.alfa.core.dto.ExtensionesDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase de testeo para la seccion de extension
 * @author Duvan Rodriguez
 */

class SeccionExtensionExtServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionExtensionExtServiceImplTest.class);
	
	private SeccionExtensionExtServiceImpl service = new SeccionExtensionExtServiceImpl();
	
	private static final long NUMEROPRUEBA = 187600104;
	
	private ExtensionesDTO dtoExt = new ExtensionesDTO();
	
	/**
	 * Metodo que setea los parametros que recibira el servicio
	 */
	
	@BeforeEach
	void setUp() {

		dtoExt.setInvoiceAuthorization(NUMEROPRUEBA);
		dtoExt.setStartDate(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setEndDate(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setPrefix(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setFrom(NUMEROPRUEBA);
		dtoExt.setTo(NUMEROPRUEBA);
		dtoExt.setIdentificationCode(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setProviderID(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setProviderIDschemeID(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setSoftwareID(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setSoftwareSecurityCode(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setAuthorizationProviderID(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setAuthorizationProviderIDschemeID(FacEnum.STRING_PRUEBA.getValor());
		dtoExt.setQrCode(FacEnum.STRING_PRUEBA.getValor());
	}
	
	/**
	 * Metodo que testea el flujo normal del servicio
	 */
	
	@Test
	void testSeccionExtensionExt() {
		JSONObject ext = new JSONObject();
		try {
			ext = service.seccionExtensionExt(dtoExt);
			assertEquals(NUMEROPRUEBA, ext.get(FacEnum.INVOICEAUTHORIZATION.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.STARTDATE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.ENDDATE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.PREFIX.getValor()));
			assertEquals(NUMEROPRUEBA, ext.get(FacEnum.FROM.getValor()));
			assertEquals(NUMEROPRUEBA, ext.get(FacEnum.TO.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.IDENTIFICATIONCODE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.PROVIDERID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.PROVIDERID_SCHEMAID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.SOFTWAREID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.SOFTWARESECURITYCODE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.AUTHORIZATIONPROVIDERID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.AUTHORIZATIONPROVIDERID_SCHEMEID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), ext.get(FacEnum.QRCODE.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error con el testeo de la seccion ext", e.getMessage());
		}
	}

}
