package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.periferia.alfa.core.dto.PagoDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que testea la seccion de pagos
 * @author Duvan Rodriguez
 */

class SeccionPagoPymServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionPagoPymServiceImplTest.class);

	private SeccionPagoPymServiceImpl service = new SeccionPagoPymServiceImpl();

	private PagoDTO pagoDto = new PagoDTO();

	/**
	 * Metodo qeu setea los parametros que recibira el servicio
	 */
	
	@BeforeEach
	void setUp() {
		pagoDto.setId(FacEnum.STRING_PRUEBA.getValor());
		pagoDto.setPaymentMeansCode(FacEnum.STRING_PRUEBA.getValor());
		pagoDto.setPaymentDueDate(FacEnum.STRING_PRUEBA.getValor());
		pagoDto.setInstructionNote(FacEnum.STRING_PRUEBA.getValor());
		pagoDto.setPaymentID(FacEnum.STRING_PRUEBA.getValor());
	}

	/**
	 * Metodo que testea el flujo normal del servicio
	 */
	
	@Test
	void testSeccionPagoPym() {
		JSONArray array = new JSONArray();
		try {
			array = service.seccionPagoPym(pagoDto);
			JSONObject propiedad = array.getJSONObject(0);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.ID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.PAYMENTMEANSCODE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.PAYMENTDUEDATE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.INSTRUCTIONNOTE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.PAYMENTID.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error en el testo de las seccion de impuestos", e.getMessage());
		}
	}

	/**
	 * Metodo que testea le servicio cuando este recibe un parametro nulo
	 */
	
	@Test
	void testSeccionPagoPymRecibeNull() {
		JSONArray array = service.seccionPagoPym(null);
		assertEquals(0, array.length());
	}

}
