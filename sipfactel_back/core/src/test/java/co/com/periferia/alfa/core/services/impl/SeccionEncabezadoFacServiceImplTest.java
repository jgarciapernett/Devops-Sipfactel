package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

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
 * Clase de testeo para la seccion de encabezados
 * @author Duvan Rodriguez
 */

class SeccionEncabezadoFacServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionEncabezadoFacServiceImplTest.class);

	private SeccionEncabezadoFacServiceImpl service = new SeccionEncabezadoFacServiceImpl();

	private EncabezadoDTO dtoEncabezadoFactura = new EncabezadoDTO();
	
	private EncabezadoNotaDebDTO dtoEncabezadoDebito = new EncabezadoNotaDebDTO();
	
	private EncabezadoNotaCreDTO dtoencabezadoCredito = new EncabezadoNotaCreDTO();

	private static final Integer NUMEROINTPRUEBA = 181702;
	
	private static final String HORA = "2021-02-07 18:00:00.0";
	
	private static final String FECHA = "2021-02-07";

	/**
	 * Metodo de seteo para los encabezados de notas y facturas
	 */
	
	@BeforeEach
	void setUp() {
		dtoEncabezadoFactura.setUblVersionID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setCustomizationID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setProfileID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setProfileExecutionID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setId(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setUuid(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setIssueDate(FECHA);
		dtoEncabezadoFactura.setIssueTime(HORA);
		dtoEncabezadoFactura.setInvoiceTypeCode(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setDocumentCurrencyCode(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoFactura.setLineCountNumeric(NUMEROINTPRUEBA);
		
		dtoEncabezadoDebito.setUblVersionID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoDebito.setCustomizationID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoDebito.setProfileID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoDebito.setProfileExecutionID(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoDebito.setId(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoDebito.setUuid(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoDebito.setIssueDate(FECHA);
		dtoEncabezadoDebito.setIssueTime(HORA);
		dtoEncabezadoDebito.setDocumentCurrencyCode(FacEnum.STRING_PRUEBA.getValor());
		dtoEncabezadoDebito.setLineCountNumeric(NUMEROINTPRUEBA);
		
		dtoencabezadoCredito.setUblVersionID(FacEnum.STRING_PRUEBA.getValor());
		dtoencabezadoCredito.setCustomizationID(FacEnum.STRING_PRUEBA.getValor());
		dtoencabezadoCredito.setProfileID(FacEnum.STRING_PRUEBA.getValor());
		dtoencabezadoCredito.setProfileExecutionID(FacEnum.STRING_PRUEBA.getValor());
		dtoencabezadoCredito.setId(FacEnum.STRING_PRUEBA.getValor());
		dtoencabezadoCredito.setUuid(FacEnum.STRING_PRUEBA.getValor());
		dtoencabezadoCredito.setIssueDate(FECHA);
		dtoencabezadoCredito.setIssueTime(HORA);
		dtoencabezadoCredito.setDocumentCurrencyCode(FacEnum.STRING_PRUEBA.getValor());
		dtoencabezadoCredito.setLineCountNumeric(NUMEROINTPRUEBA);

	}

	/**
	 * Metodo que testea el flujo normal del servicio cuando recibe un encabezado para factura
	 */
	
	@Test
	void testSeccionEncabezadoFactura() {
		JSONObject fact = new JSONObject();
		try {
			fact = service.seccionEncabezadoFac(dtoEncabezadoFactura);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.UBLVERSIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.CUSTOMIZATIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.PROFILEEXECUTIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.PROFILEID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.ID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.UUID.getValor()));
			assertEquals(FECHA, fact.get(FacEnum.ISSUEDATE.getValor()));
			assertEquals(HORA.split(" ")[1], fact.get(FacEnum.ISSUETIME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.INVOICETYPECODE.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), fact.get(FacEnum.DOCUMENTCURRENCYCODE.getValor()));
			assertEquals(NUMEROINTPRUEBA, fact.get(FacEnum.LINECOUNTNUMERIC.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error en el testeo de la seccion encabezados factura, error: {} ", e.getMessage());
		}
	}
	
	/**
	 * Metodo que testea el flujo normal del servicio cuando recibe un encabezado para nota debito
	 */
	
	@Test
	void testSeccionEncabezadoDebito() {
		JSONObject debito = new JSONObject();
		try {
			debito = service.seccionEncabezadoFac(dtoEncabezadoDebito);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), debito.get(FacEnum.UBLVERSIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), debito.get(FacEnum.CUSTOMIZATIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), debito.get(FacEnum.PROFILEEXECUTIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), debito.get(FacEnum.PROFILEID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), debito.get(FacEnum.ID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), debito.get(FacEnum.UUID.getValor()));
			assertEquals(FECHA, debito.get(FacEnum.ISSUEDATE.getValor()));
			assertEquals(HORA.split(" ")[1], debito.get(FacEnum.ISSUETIME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), debito.get(FacEnum.DOCUMENTCURRENCYCODE.getValor()));
			assertEquals(NUMEROINTPRUEBA, debito.get(FacEnum.LINECOUNTNUMERIC.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error en el testeo de la seccion encabezados factura, error: {} ", e.getMessage());
		}
	}
	
	/**
	 * Metodo que testea el flujo normal del servicio cuando recibe un encabezado para nota credito
	 */
	
	@Test
	void testSeccionEncabezadoCredito() {
		JSONObject credito = new JSONObject();
		try {
			credito = service.seccionEncabezadoFac(dtoencabezadoCredito);
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), credito.get(FacEnum.UBLVERSIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), credito.get(FacEnum.CUSTOMIZATIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), credito.get(FacEnum.PROFILEEXECUTIONID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), credito.get(FacEnum.PROFILEID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), credito.get(FacEnum.ID.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), credito.get(FacEnum.UUID.getValor()));
			assertEquals(FECHA, credito.get(FacEnum.ISSUEDATE.getValor()));
			assertEquals(HORA.split(" ")[1], credito.get(FacEnum.ISSUETIME.getValor()));
			assertEquals(FacEnum.STRING_PRUEBA.getValor(), credito.get(FacEnum.DOCUMENTCURRENCYCODE.getValor()));
			assertEquals(NUMEROINTPRUEBA, credito.get(FacEnum.LINECOUNTNUMERIC.getValor()));
		} catch (JSONException e) {
			LOG.warn("Ha ocurrido un error en el testeo de la seccion encabezados factura, error: {} ", e.getMessage());
		}
	}

}
