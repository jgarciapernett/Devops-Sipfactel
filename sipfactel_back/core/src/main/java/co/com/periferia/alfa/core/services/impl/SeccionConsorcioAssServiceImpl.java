package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConsorciadosDto;
import co.com.periferia.alfa.core.services.ISeccionConsorcioAssService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase para la seccion de consorcios ass
 * @author Duvan Rodriguez
 */

@Service
public class SeccionConsorcioAssServiceImpl implements ISeccionConsorcioAssService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionConsorcioAssServiceImpl.class);
	
	/**
	 * Metodo que realiza la logica de la seccion ass (informacion miembros consorcio)
	 * @param listaConsorciados - List<ConsorciadosDto> lista con la informacion de los miembros del consorcio.
	 * @return JSONArray - arreglo con los datos de los miembros.
	 */
	
	@Override
	public JSONArray seccionConsorciosAss(List<ConsorciadosDto> listaConsorciados) {
		LOG.info("Entra a armar seccion ass");
        List<Map<String, Object>> listaAss = new ArrayList<>();
		for (ConsorciadosDto consorcio : listaConsorciados) {
			Map<String, Object> seccionAss = new HashedMap<>();
			seccionAss.put(FacEnum.CONTACT_ID.getValor(), consorcio.getContactID());
			seccionAss.put(FacEnum.PARTECIPATIONPERCENT.getValor(), consorcio.getPartecipationPercent());
			seccionAss.put(FacEnum.PARTYNAME.getValor(), Utilitario.datoNulo(consorcio.getPartyName()));
			seccionAss.put(FacEnum.TAX_COMPANYID.getValor(), Utilitario.datoNulo(consorcio.getTaxCompanyID()));
			if (consorcio.getTaxCompanyIDschemeName() != null
					&& consorcio.getTaxCompanyIDschemeName().equals(FacEnum.TIPOIDENTIFICACION_NIT.getValor()))
				seccionAss.put(FacEnum.TAX_COMPANYID_SCHEMEID.getValor(), Utilitario.datoNulo(consorcio.getTaxCompanyIDschemeID()));
			else
				seccionAss.put(FacEnum.TAX_COMPANYID_SCHEMEID.getValor(), FacEnum.NOTWHITE_SPACE.getValor());

			seccionAss.put(FacEnum.TAX_COMPANYID_SCHEMENAME.getValor(), Utilitario.datoNulo(consorcio.getTaxCompanyIDschemeName()));
			seccionAss.put(FacEnum.TAX_LEVELCODE.getValor(), Utilitario.datoNulo(consorcio.getTaxLevelCode()));
			seccionAss.put(FacEnum.TAX_LEVELCODE_LISTNAME.getValor(), Utilitario.datoNulo(consorcio.getTaxLevelCodeListName()));
			seccionAss.put(FacEnum.TAX_REGISTRATIONNAME.getValor(), Utilitario.datoNulo(consorcio.getTaxRegistrationName()));
			seccionAss.put(FacEnum.TAX_SCHEME_ID.getValor(), Utilitario.datoNulo(consorcio.getTaxSchemeID()));
			seccionAss.put(FacEnum.TAX_SCHEME_NAME.getValor(), Utilitario.datoNulo(consorcio.getTaxSchemeName()));
			listaAss.add(seccionAss);
		}

		return UtilJson.armarJsonArray(listaAss);
	}

}
