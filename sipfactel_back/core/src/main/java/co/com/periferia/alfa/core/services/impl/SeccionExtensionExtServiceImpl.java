package co.com.periferia.alfa.core.services.impl;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ExtensionesDTO;
import co.com.periferia.alfa.core.services.ISeccionExtensionExtService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase que realiza la logica de la seccion ext (extension)
 * @author Duvan Rodriguez
 */

@Service
public class SeccionExtensionExtServiceImpl implements ISeccionExtensionExtService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SeccionExtensionExtServiceImpl.class);

	/**
	 * Metodo que recopila los datos de la seccion ext
	 * @param ext - Dto con la informacion de la seccion
	 * @return JSONObject con el json ya armado
	 */
	
	@Override
	public JSONObject seccionExtensionExt(ExtensionesDTO ext) {
		LOG.info("Ingreso a seccion extension (ext)");

		Map<String, Object> seccionExt = new HashedMap<>();
		
		seccionExt.put(FacEnum.INVOICEAUTHORIZATION.getValor(), ext.getInvoiceAuthorization());
		seccionExt.put(FacEnum.STARTDATE.getValor(), ext.getStartDate());
		seccionExt.put(FacEnum.ENDDATE.getValor(), ext.getEndDate());
		seccionExt.put(FacEnum.PREFIX.getValor(), ext.getPrefix());
		seccionExt.put(FacEnum.FROM.getValor(), ext.getFrom());
		seccionExt.put(FacEnum.TO.getValor(), ext.getTo());
		seccionExt.put(FacEnum.IDENTIFICATIONCODE.getValor(), ext.getIdentificationCode());
		seccionExt.put(FacEnum.PROVIDERID.getValor(), ext.getProviderID());
		seccionExt.put(FacEnum.PROVIDERID_SCHEMAID.getValor(), ext.getProviderIDschemeID());
		seccionExt.put(FacEnum.SOFTWAREID.getValor(), ext.getSoftwareID());
		seccionExt.put(FacEnum.SOFTWARESECURITYCODE.getValor(), ext.getSoftwareSecurityCode());
		seccionExt.put(FacEnum.AUTHORIZATIONPROVIDERID.getValor(), ext.getAuthorizationProviderID());
		seccionExt.put(FacEnum.AUTHORIZATIONPROVIDERID_SCHEMEID.getValor(), ext.getAuthorizationProviderIDschemeID());
		seccionExt.put(FacEnum.QRCODE.getValor(), ext.getQrCode());

		return UtilJson.armarJson(seccionExt);
	}

	
	
}
