package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ConsorciadosModel {

	@Column(name = "PartecipationPercent")
	private Double partecipationPercent;

	@Column(name = "PartyName", length = 10)
	private String partyName;

	@Column(name = "Tax_RegistrationName", length = 300)
	private String taxRegistrationName;

	@Column(name = "Tax_CompanyID", length = 100)
	private String taxCompanyID;

	@Column(name = "Tax_CompanyID_schemeID", length = 300)
	private String taxCompanyIDschemeID;

	@Column(name = "Tax_CompanyID_schemeName", length = 100)
	private String taxCompanyIDschemeName;

	@Column(name = "Tax_LevelCode", length = 100)
	private String taxLevelCode;

	@Column(name = "Tax_LevelCode_listName")
	private String taxLevelCodeListName;

	@Column(name = "Tax_Scheme_ID")
	private String taxSchemeID;

	@Column(name = "Tax_Scheme_Name", length = 300)
	private String taxSchemeName;

	@Id
	@Column(name = "Contact_ID", length = 300)
	private String contactID;
	
	@Column(name = "nombreContact_ID", length = 300)
	private String nombrecontactID;
	
}
