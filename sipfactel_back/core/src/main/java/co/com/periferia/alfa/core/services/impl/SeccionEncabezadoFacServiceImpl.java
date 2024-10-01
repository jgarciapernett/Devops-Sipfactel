package co.com.periferia.alfa.core.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.EncabezadoDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaCreDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaDebDTO;
import co.com.periferia.alfa.core.services.ISeccionEncabezadoFacService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase que realiza la logica de la seccion encabezado para FAC, DEB o Cre
 * @author Duvan Rodriguez
 */

@Service
public class SeccionEncabezadoFacServiceImpl implements ISeccionEncabezadoFacService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionEncabezadoFacServiceImpl.class);
	
	/**
	 * Metodo que recopila los datos para la aseccion encabezado ya sean de factura, nota debito o credito
	 * @param encabezadosDto - objeto que puede ser EncabezadoDto o EncabezadoNotaDebDTO o EncabezadoNotaCreDTO
	 * @return JSONObject con el json ya armado
	 */
	
	@Override
	public JSONObject seccionEncabezadoFac(Object encabezadosDto) {
		LOG.info("Ingreso a seccion encabezado");

		Map<String, Object> seccionFac = new HashedMap<>();
		
		validateTransactionDate(encabezadosDto);

		if (encabezadosDto.getClass() == EncabezadoDTO.class) {
			LOG.debug("Entro la clase EncabezadoDTO: {} ", encabezadosDto.getClass());
			seccionFac.put(FacEnum.UBLVERSIONID.getValor(), ((EncabezadoDTO) encabezadosDto).getUblVersionID());
			seccionFac.put(FacEnum.CUSTOMIZATIONID.getValor(), ((EncabezadoDTO) encabezadosDto).getCustomizationID());
			seccionFac.put(FacEnum.PROFILEID.getValor(), ((EncabezadoDTO) encabezadosDto).getProfileID());
			seccionFac.put(FacEnum.PROFILEEXECUTIONID.getValor(), ((EncabezadoDTO) encabezadosDto).getProfileExecutionID());
			seccionFac.put(FacEnum.ID.getValor(), ((EncabezadoDTO) encabezadosDto).getId());
			seccionFac.put(FacEnum.UUID.getValor(), ((EncabezadoDTO) encabezadosDto).getUuid());
			seccionFac.put(FacEnum.ISSUEDATE.getValor(), ((EncabezadoDTO) encabezadosDto).getIssueDate());
			seccionFac.put(FacEnum.ISSUETIME.getValor(), fueraDeRango(((EncabezadoDTO) encabezadosDto).getIssueTime()));
			seccionFac.put(FacEnum.INVOICETYPECODE.getValor(), ((EncabezadoDTO) encabezadosDto).getInvoiceTypeCode());
			seccionFac.put(FacEnum.DOCUMENTCURRENCYCODE.getValor(), ((EncabezadoDTO) encabezadosDto).getDocumentCurrencyCode());
			seccionFac.put(FacEnum.LINECOUNTNUMERIC.getValor(), ((EncabezadoDTO) encabezadosDto).getLineCountNumeric());
		} else if (encabezadosDto.getClass() == EncabezadoNotaDebDTO.class) {
			LOG.debug("Entro la clase EncabezadoNotaDebDTO: {} ", encabezadosDto.getClass());
			seccionFac.put(FacEnum.UBLVERSIONID.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getUblVersionID());
			seccionFac.put(FacEnum.CUSTOMIZATIONID.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getCustomizationID());
			seccionFac.put(FacEnum.PROFILEID.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getProfileID());
			seccionFac.put(FacEnum.PROFILEEXECUTIONID.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getProfileExecutionID());
			seccionFac.put(FacEnum.ID.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getId());
			seccionFac.put(FacEnum.UUID.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getUuid());
			seccionFac.put(FacEnum.ISSUEDATE.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getIssueDate());
			seccionFac.put(FacEnum.ISSUETIME.getValor(), fueraDeRango(((EncabezadoNotaDebDTO) encabezadosDto).getIssueTime()));
			seccionFac.put(FacEnum.DOCUMENTCURRENCYCODE.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getDocumentCurrencyCode());
			seccionFac.put(FacEnum.LINECOUNTNUMERIC.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getLineCountNumeric());
		} else if (encabezadosDto.getClass() == EncabezadoNotaCreDTO.class) {
			LOG.debug("Entro la clase EncabezadoNotaCreDTO: {} ", encabezadosDto.getClass());
			seccionFac.put(FacEnum.UBLVERSIONID.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getUblVersionID());
			seccionFac.put(FacEnum.CUSTOMIZATIONID.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getCustomizationID());
			seccionFac.put(FacEnum.PROFILEID.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getProfileID());
			seccionFac.put(FacEnum.PROFILEEXECUTIONID.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getProfileExecutionID());
			seccionFac.put(FacEnum.ID.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getId());
			seccionFac.put(FacEnum.UUID.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getUuid());
			seccionFac.put(FacEnum.ISSUEDATE.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getIssueDate());
			seccionFac.put(FacEnum.ISSUETIME.getValor(), fueraDeRango(((EncabezadoNotaCreDTO) encabezadosDto).getIssueTime()));
			seccionFac.put(FacEnum.CREDITNOTETYPECODE.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getCreditNoteTypeCode());
			seccionFac.put(FacEnum.DOCUMENTCURRENCYCODE.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getDocumentCurrencyCode());
			seccionFac.put(FacEnum.LINECOUNTNUMERIC.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getLineCountNumeric());
		} else {
			LOG.warn("no es un dto de encabezado");
		}
		
		return UtilJson.armarJson(seccionFac);
	}
	
	private void validateTransactionDate(Object encabezadosDto) {
	    LOG.info("ingreso a validar le fecha de transaccion");
			try {

				SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

				String strIssueDate = FacEnum.NOTWHITE_SPACE.getValor();

				String strTransactionDate = FacEnum.NOTWHITE_SPACE.getValor();

				if (encabezadosDto.getClass() == EncabezadoDTO.class) {
					strIssueDate = ((EncabezadoDTO) encabezadosDto).getIssueDate().split(" ")[0];
					strTransactionDate = ((EncabezadoDTO) encabezadosDto).getIssueTime().split(" ")[0];
				} else if (encabezadosDto.getClass() == EncabezadoNotaDebDTO.class) {
					strIssueDate = ((EncabezadoNotaDebDTO) encabezadosDto).getIssueDate().split(" ")[0];
					strTransactionDate = ((EncabezadoNotaDebDTO) encabezadosDto).getIssueTime().split(" ")[0];
				} else if (encabezadosDto.getClass() == EncabezadoNotaCreDTO.class) {
					strIssueDate = ((EncabezadoNotaCreDTO) encabezadosDto).getIssueDate().split(" ")[0];
					strTransactionDate = ((EncabezadoNotaCreDTO) encabezadosDto).getIssueTime().split(" ")[0];
				}

				int yearIssue = Integer.parseInt(strIssueDate.split("-")[0]);
				int yearTransaction = Integer.parseInt(strTransactionDate.split("-")[0]);
				int monthIssue = Integer.parseInt(strIssueDate.split("-")[1]);
				int monthTransaction = Integer.parseInt(strTransactionDate.split("-")[1]);

				if (monthIssue < monthTransaction || yearIssue < yearTransaction) {

					Date issueDate = dbDateFormat.parse(strIssueDate);
					
					Calendar cal = Calendar.getInstance();

					cal.setTime(issueDate);

					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
					cal.set(Calendar.DAY_OF_MONTH, 1);
					cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.HOUR_OF_DAY, 23);

				}

			} catch (ParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
				LOG.warn("Ha oucrrido un error en el metodo validateTransactionDate, error: {} ", e.getMessage() );
			}

		}

	/**
	 * Metodo que valida si se puede extraer el campo hora de un formato timestamp
	 * @param fecha - string a validar
	 * @return String - retorna la hora dependiendo si la validacion fue correcta
	 */
	
	private String fueraDeRango(String hora) {
		try {
			hora = hora.split(FacEnum.WHITE_SPACE.getValor())[1];	
		} catch (ArrayIndexOutOfBoundsException e) {
			LOG.warn("Error, el arreglo de la fecha no puede extraer la hora, esta fuera de rango, error: {} ", e.getMessage());
			hora = FacEnum.NOTWHITE_SPACE.getValor();
		}

		return hora;
		
	}
	
}
