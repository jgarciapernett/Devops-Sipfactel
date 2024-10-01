package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.periferia.alfa.core.dto.ImpuestosDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que testea la seccion de impuestos
 * @author Duvan Rodriguez
 */

class SeccionImpuestosTxtServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionImpuestosTxtServiceImplTest.class);

	private SeccionImpuestosTxtServiceImpl service = new SeccionImpuestosTxtServiceImpl();

	private static final BigInteger NUMEROBIGPRUEBA = BigInteger.valueOf(6518768);

	private List<ImpuestosDTO> listaImpuestos = new ArrayList<>();

	/**
	 * Metodo que setea los parametros que recibira el servicio
	 */
	
	@BeforeEach
	void setUp() {
		ImpuestosDTO txtDto = new ImpuestosDTO();

		txtDto.setTaxAmount(NUMEROBIGPRUEBA);
		txtDto.setSchemeID(FacEnum.STRING_PRUEBA.getValor());
		txtDto.setSchemeName(FacEnum.STRING_PRUEBA.getValor());
		txtDto.setTaxSubTotal1TaxableAmount(NUMEROBIGPRUEBA);
		txtDto.setTaxSubTotal1TaxAmount(NUMEROBIGPRUEBA);
		txtDto.setTaxSubTotal1Percent(NUMEROBIGPRUEBA);
		listaImpuestos.add(txtDto);
	}

	/**
	 * Metodo que testea el flujo normal del servicio
	 */
	
	@Test
	void testSeccionImpuestosTxt() {
		JSONArray array = new JSONArray();
		try {
			array = service.seccionImpuestosTxt(listaImpuestos);
			JSONObject propiedad = array.getJSONObject(0);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.SCHEMEID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), propiedad.get(FacEnum.SCHEMENAME.getValor()));
			assertEquals(NUMEROBIGPRUEBA, propiedad.get(FacEnum.TAXSUBTOTAL1_TAXABLEAMOUNT.getValor()));
			assertEquals(NUMEROBIGPRUEBA, propiedad.get(FacEnum.TAXSUBTOTAL1_TAXAMOUNT.getValor()));
			assertEquals(NUMEROBIGPRUEBA, propiedad.get(FacEnum.TAXSUBTOTAL1_PERCENT.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error en el testo de la seccion txt impuestos, error: {} ", e.getMessage());
		}
	}

	/**
	 * Metodo que testea el servico cuando este recibe un parametro null
	 */
	
	@Test
	void testSeccionImpuestosTxtRecibeNull() {
		JSONArray array = service.seccionImpuestosTxt(null);
		assertEquals(0, array.length());
	}

}
