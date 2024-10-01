package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.periferia.alfa.core.dto.EncabezadoDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaCreDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaDebDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que testea la seccion de totales
 * @author Duvan Rodriguez
 */

class SeccionTotalesTotServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionTotalesTotServiceImplTest.class);
	
	private static final BigInteger NUMEROPRUEBA = BigInteger.valueOf(6518768);
	
	private SeccionTotalesTotServiceImpl service = new SeccionTotalesTotServiceImpl();
	
	private EncabezadoDTO encabezadoFacturas = new EncabezadoDTO();
	private EncabezadoNotaDebDTO encabezadoDebito = new EncabezadoNotaDebDTO();
	private EncabezadoNotaCreDTO encabezadoCredito = new EncabezadoNotaCreDTO();
	
	/**
	 * Metodo que setea los encaebzados de facturas y notas que recibira el servicio
	 */
	
	@BeforeEach
	void setUp() {
		encabezadoFacturas.setLineExtensionAmount(NUMEROPRUEBA);
		encabezadoFacturas.setTaxExclusiveAmount(NUMEROPRUEBA);
		encabezadoFacturas.setTaxInclusiveAmount(NUMEROPRUEBA);
		encabezadoFacturas.setPayableAmount(NUMEROPRUEBA);
		
		encabezadoDebito.setLineExtensionAmount(NUMEROPRUEBA);
		encabezadoDebito.setTaxExclusiveAmount(NUMEROPRUEBA);
		encabezadoDebito.setTaxInclusiveAmount(NUMEROPRUEBA);
		encabezadoDebito.setPayableAmount(NUMEROPRUEBA);
		
		encabezadoCredito.setLineExtensionAmount(NUMEROPRUEBA);
		encabezadoCredito.setTaxExclusiveAmount(NUMEROPRUEBA);
		encabezadoCredito.setTaxInclusiveAmount(NUMEROPRUEBA);
		encabezadoCredito.setPayableAmount(NUMEROPRUEBA);
	}
	
	/**
	 * Metodo que testea el servicio cuando este recibe un encabezado de facturas
	 */
	
	@Test
	void testSeccionTotalesTotFacturas() {
		JSONObject tot = new JSONObject();
		try {
			tot = service.seccionTotalesTot(encabezadoFacturas, NUMEROPRUEBA);
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.LINEEXTENSIONAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.TAXEXCLUSIVEAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.TAXINCLUSIVEAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.PAYABLEAMOUNT.getValor()));
		} catch (JSONException e) {
			LOG.error("Ha ocurrido un error en el testeo de la seccion de totales, error: {} ", e.getMessage());
		}
	}
	
	/**
	 * Metodo que testea el servicio cuando este recibe un encabezado de notas debito
	 */
	
	@Test
	void testSeccionTotalesTotDebito() {
		JSONObject tot = new JSONObject();
		try {
			tot = service.seccionTotalesTot(encabezadoDebito, NUMEROPRUEBA);
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.LINEEXTENSIONAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.TAXEXCLUSIVEAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.TAXINCLUSIVEAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.PAYABLEAMOUNT.getValor()));
		} catch (JSONException e) {
			LOG.error("Ha ocurrido un error en el testeo de la seccion de totales, error: {} ", e.getMessage());
		}
	}
	
	/**
	 * Metodo que testea el servicio cuando este recibe un encabezado de notas credito
	 */
	
	@Test
	void testSeccionTotalesTotCredito() {
		JSONObject tot = new JSONObject();
		try {
			tot = service.seccionTotalesTot(encabezadoCredito, NUMEROPRUEBA);
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.LINEEXTENSIONAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.TAXEXCLUSIVEAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.TAXINCLUSIVEAMOUNT.getValor()));
			assertEquals(NUMEROPRUEBA, tot.get(FacEnum.PAYABLEAMOUNT.getValor()));
		} catch (JSONException e) {
			LOG.error("Ha ocurrido un error en el testeo de la seccion de totales, error: {} ", e.getMessage());
		}
	}

}
