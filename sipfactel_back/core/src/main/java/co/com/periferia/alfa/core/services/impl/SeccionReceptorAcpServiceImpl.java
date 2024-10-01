package co.com.periferia.alfa.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;
import co.com.periferia.alfa.core.services.ISeccionReceptorAcpService;
import co.com.periferia.alfa.core.services.IValidarPersonaJuridicaService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase para la seccion receptor acp
 * @author Duvan Rodriguez
 */

@Service
public class SeccionReceptorAcpServiceImpl implements ISeccionReceptorAcpService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionReceptorAcpServiceImpl.class);
	
	@Autowired
	IValidarPersonaJuridicaService validacionService;
	
	/**
	 * Metodo que realiza la logica de la seccion acp (receptor)
	 * @param rec     - objeto que puede ser ReceptorDto o ReceptorNotasDTO
	 * @param mostrar - boolean que identifica si se debe mostrar el
	 *                CustomerAssignedAccountID de notasDto
	 * @return JSONObject con el json armado
	 */
	
	@Override
	public JSONObject seccionReceptorAcp(ReceptoresDireccionDto rec) {
		LOG.info("Ingreso a seccion receptor (acp)");

		Map<String, Object> seccionAcp = new HashMap<>();
		
		if (rec.getClass() == ReceptoresDireccionDto.class) {
			LOG.debug("Ingreso la clase ReceptorDTO = {} ", rec.getClass());
			seccionAcp.put(FacEnum.ADDITIONALACCOUNTID.getValor(), rec.getAdditionalAccountID());
			seccionAcp.put(FacEnum.PARTYNAME.getValor(), rec.getPartyName());
			seccionAcp.put(FacEnum.PHYSICAL_ADD_ID.getValor(), rec.getPhysicalAddId());
			seccionAcp.put(FacEnum.TAX_REGISTRATIONNAME.getValor(), rec.getTaxRegistrationName());
			seccionAcp.put(FacEnum.TAX_COMPANYID.getValor(), rec.getTaxCompanyID());
			seccionAcp.put(FacEnum.TAX_COMPANYID_SCHEMEID.getValor(), rec.getTaxCompanyIDschemeID());
			seccionAcp.put(FacEnum.TAX_COMPANYID_SCHEMENAME.getValor(), rec.getTaxCompanyIDschemeName());
			seccionAcp.put(FacEnum.TAX_LEVELCODE.getValor(), rec.getTaxLevelCode());
			seccionAcp.put(FacEnum.TAX_LEVELCODE_LISTNAME.getValor(), rec.getTaxLevelCodeListName());
			seccionAcp.put(FacEnum.TAX_SCHEME_ID.getValor(), rec.getTaxSchemeID());
			seccionAcp.put(FacEnum.TAX_SCHEME_NAME.getValor(), rec.getTaxSchemeName());
			seccionAcp.put(FacEnum.REGISTRATION_ADD_ID.getValor(), rec.getRegistrationAddId());
			if (validacionService.validaPersonaJuridica(rec.getTipoPersona()))
				seccionAcp.put(FacEnum.CONTACT_ID.getValor(), rec.getContactID());
		}

		return UtilJson.armarJson(seccionAcp);
	}
	
	

}
