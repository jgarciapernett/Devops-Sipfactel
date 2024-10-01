package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmisorAndDireccionModel {

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
	
	// seccion direccion
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CityID", length = 300)
	private String cityID;
	
	@Column(name = "CityName", length = 500)
	private String cityName;
	
	@Column(name = "PostalZone", length = 50)
	private String postalZone;
	
	@Column(name = "CountrySubentity", length = 500)
	private String countrySubentity;
	
	@Column(name = "CountrySubentityCode", length = 300)
	private String countrySubentityCode;
	
	@Column(name = "AddressLine")
	private String addressLine;
	
	@Column(name = "CountryName", length = 500)
	private String countryName;
	
	@Column(name = "CountryCode", length = 300)
	private String countryCode;
	
}
