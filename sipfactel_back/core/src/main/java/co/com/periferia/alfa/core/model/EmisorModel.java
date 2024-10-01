package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmisorModel {

	@Id
	@Column(name = "AdditionalAccountID", length = 300)
	private String additionalAccountID;
	
	@Column(name = "PartyName", length = 200)
	private String partyName;
	
	@Column(name = "Physical_ADD_ID")
	private Integer physicalAddID;
	
	@Column(name = "Tax_RegistrationName", length = 200)
	private String taxRegistrationName;
	
	@Column(name = "Tax_CompanyID", length = 15)
	private String taxCompanyID;
	
	@Column(name = "Tax_CompanyID_schemeID", length = 5)
	private String taxCompanyIDschemeID;

	@Column(name = "Tax_CompanyID_schemeName", length = 300)
	private String taxCompanyIDschemeName;
	
	@Column(name = "Tax_LevelCode", length = 300)
	private String taxLevelCode;
	
	@Column(name = "Tax_LevelCode_listName", length = 300)
	private String taxLevelCodeListName;
	
	@Column(name = "Tax_Scheme_ID", length = 300)
	private String taxSchemeID;
	
	@Column(name = "Tax_Scheme_Name", length = 500)
	private String taxSchemeName;
	
	@Column(name = "Registration_ADD_ID")
	private Integer registrationAddID;
	
	@Column(name = "Contact_id")
	private String contactId;
	
	@Column(name = "nombre_contacto")
	private String nombreContacto;
	
	@Column(name = "ElectronicMail")
	private String electronicMail;

}
