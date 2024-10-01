package co.com.periferia.alfa.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;
import co.com.periferia.alfa.core.services.ISeccionEmisorAspService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase para la seccion emisor asp
 * @author Duvan Rodriguez
 */

@Service
public class SeccionEmisorAspServiceImpl implements ISeccionEmisorAspService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionEmisorAspServiceImpl.class);
	
	/**
	 * Metodo que realiza la logica de la seccion asp (emisor)
	 * @param emisor - EmisorDTO con la informacion de la seccion
	 * @return JSONObject con el json armado
	 */
	
	@Override
	public JSONObject seccionEmisorAsp(EmisorAndDireccionDto emisor) {
		LOG.info("Ingreso a seccion emisor (asp)");

		Map<String, Object> seccionAsp = new HashMap<>();
		
		if(emisor != null) {
			seccionAsp.put(FacEnum.ADDITIONALACCOUNTID.getValor(), emisor.getAdditionalAccountID());
			seccionAsp.put(FacEnum.PARTYNAME.getValor(), emisor.getPartyName());
			seccionAsp.put(FacEnum.PHYSICAL_ADD_ID.getValor(), emisor.getPhysicalAddID());
			seccionAsp.put(FacEnum.TAX_REGISTRATIONNAME.getValor(), emisor.getTaxRegistrationName());
			seccionAsp.put(FacEnum.TAX_COMPANYID.getValor(), emisor.getTaxCompanyID());
			seccionAsp.put(FacEnum.TAX_COMPANYID_SCHEMEID.getValor(), emisor.getTaxCompanyIDschemeID());
			seccionAsp.put(FacEnum.TAX_COMPANYID_SCHEMENAME.getValor(), emisor.getTaxCompanyIDschemeName());
			seccionAsp.put(FacEnum.TAX_LEVELCODE.getValor(), emisor.getTaxLevelCode());
			seccionAsp.put(FacEnum.TAX_LEVELCODE_LISTNAME.getValor(), emisor.getTaxLevelCodeListName());
			seccionAsp.put(FacEnum.TAX_SCHEME_ID.getValor(), emisor.getTaxSchemeID());
			seccionAsp.put(FacEnum.TAX_SCHEME_NAME.getValor(), emisor.getTaxSchemeName());
			seccionAsp.put(FacEnum.REGISTRATION_ADD_ID.getValor(), emisor.getRegistrationAddID());	
			seccionAsp.put(FacEnum.CONTACT_ID.getValor(), emisor.getContactId());
		}

		return UtilJson.armarJson(seccionAsp);
	}

}
