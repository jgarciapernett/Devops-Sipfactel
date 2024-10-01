package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.ConsorciadosModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsorciadosDto {
	
	private Double partecipationPercent;
	private String partyName;
	private String taxRegistrationName;
	private String taxCompanyID;
	private String taxCompanyIDschemeID;
	private String taxCompanyIDschemeName;
	private String taxLevelCode;
	private String taxLevelCodeListName;
	private String taxSchemeID;
	private String taxSchemeName;
	private String contactID;
	private String nombreContactId;

	public ConsorciadosDto modeloAdto(ConsorciadosModel modelo) {
		ConsorciadosDto dto = new ConsorciadosDto();
		dto.setPartecipationPercent(modelo.getPartecipationPercent());
		dto.setPartyName(modelo.getPartyName());
		dto.setTaxRegistrationName(modelo.getTaxRegistrationName());
		dto.setTaxCompanyID(modelo.getTaxCompanyID());
		dto.setTaxCompanyIDschemeID(modelo.getTaxCompanyIDschemeID());
		dto.setTaxCompanyIDschemeName(modelo.getTaxCompanyIDschemeName());
	    dto.setTaxLevelCode(modelo.getTaxLevelCode());
	    dto.setTaxLevelCodeListName(modelo.getTaxLevelCodeListName());
	    dto.setTaxSchemeID(modelo.getTaxSchemeID());
	    dto.setTaxSchemeName(modelo.getTaxSchemeName());
	    dto.setContactID(modelo.getContactID());
	    dto.setNombreContactId(modelo.getNombrecontactID());
		return dto;
	}

}
