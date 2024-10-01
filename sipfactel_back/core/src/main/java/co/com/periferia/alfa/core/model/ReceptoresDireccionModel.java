package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ReceptoresDireccionModel {

	@Id
	@Column(name = "AdditionalAccountID", length = 300)
	private String additionalAccountID;
	
	@Column(name = "PartyName", length = 350)
	private String partyName;
	
	@Column(name = "Physical_ADD_ID")
	private Integer physicalAddId;
	
	@Column(name = "Tax_RegistrationName", length = 350)
	private String taxRegistrationName;
	
	@Column(name = "Tax_CompanyID", length = 100)
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
	private Integer registrationAddId;
	
	@Column(name = "Tipopersona")
	private Integer tipoPersona;
	
	@Column(name = "NAME")
	private String contactName;
	
	// seccion rec
	
	@Column(name = "Nombre", length = 350)
	private String nombre;
	
	@Column(name = "Email", length = 200)
	private String email;
	
	@Column(name = "Enviar_Email", length = 300)
	private String enviarEmail;
	
	@Column(name = "Incluir_Anexos", length = 300)
	private String incluirAnexos;
	
	@Column(name = "Incluir_PDF", length = 300)
	private String incluirPDF;
	
	@Column(name = "Incluir_XML")
	private String incluirXML;
	
	// seccion add
	
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CityID")
	private String cityID;
	
	@Column(name = "CityName")
	private String cityName;
	
	@Column(name = "PostalZone")
	private String postalZone;
	
	@Column(name = "CountrySubentity")
	private String countrySubentity;
	
	@Column(name = "CountrySubentityCode")
	private String countrySubentityCode;
	
	@Column(name = "AddressLine")
	private String addressLine;
	
	@Column(name = "CountryName")
	private String countryName;
	
	@Column(name = "CountryCode")
	private String countryCode;
	
}
