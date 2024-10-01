package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.ReceptoresDireccionModel;
import co.com.periferia.alfa.core.utilitarios.Utilitario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceptoresDireccionDto implements IBaseDTO<ReceptoresDireccionDto, ReceptoresDireccionModel> {

	@SerializedName("AdditionalAccountID")
	private String additionalAccountID;
	@SerializedName("PartyName")
	private String partyName;
	@SerializedName("Physical_ADD_ID")
	private String physicalAddId;
	@SerializedName("Tax_RegistrationName")
	private String taxRegistrationName;
	@SerializedName("Tax_CompanyID")
	private String taxCompanyID;
	@SerializedName("Tax_CompanyID_schemeID")
	private String taxCompanyIDschemeID;
	@SerializedName("Tax_CompanyID_schemeName")
	private String taxCompanyIDschemeName;
	@SerializedName("Tax_LevelCode")
	private String taxLevelCode;
	@SerializedName("Tax_LevelCode_listName")
	private String taxLevelCodeListName;
	@SerializedName("Tax_Scheme_ID")
	private String taxSchemeID;
	@SerializedName("Tax_Scheme_Name")
	private String taxSchemeName;
	@SerializedName("Registration_ADD_ID")
	private String registrationAddId;
	@SerializedName("Contact_ID")
	private String contactID;
	@SerializedName("Contact_Name")
	private String contactName;
	@SerializedName("Tipopersona")
	private Integer tipoPersona;
	
	// seccion rec
	
	@SerializedName("Nombre")
	private String nombre;
	@SerializedName("Email")
	private String email;
	@SerializedName("Enviar_Email")
	private boolean enviarEmail;
	@SerializedName("Incluir_Anexos")
	private boolean incluirAnexos;
	@SerializedName("Incluir_PDF")
	private boolean incluirPDF;
	@SerializedName("Incluir_XML")
	private boolean incluirXML;
	
	// seccion add
	
	@SerializedName("ID")
	private String id;
	@SerializedName("CityID")
	private String cityID;
	@SerializedName("CityName")
	private String cityName;
	@SerializedName("PostalZone")
	private String postalZone;
	@SerializedName("CountrySubentity")
	private String countrySubentity;
	@SerializedName("CountrySubentityCode")
	private String countrySubentityCode;
	@SerializedName("AddressLine")
	private String addressLine;
	@SerializedName("CountryName")
	private String countryName;
	@SerializedName("CountryCode")
	private String countryCode;
	
	@Override
	public ReceptoresDireccionDto modeloAdto(ReceptoresDireccionModel modelo) {
		ReceptoresDireccionDto dto = new ReceptoresDireccionDto();
		dto.setAdditionalAccountID(Utilitario.datoNulo(modelo.getAdditionalAccountID()));
		dto.setPartyName(Utilitario.datoNulo(modelo.getPartyName()));
		dto.setPhysicalAddId(Utilitario.datoNulo(modelo.getPhysicalAddId().toString()));
		dto.setTaxRegistrationName(Utilitario.datoNulo(modelo.getTaxRegistrationName()));
		dto.setRegistrationAddId(Utilitario.datoNulo(modelo.getRegistrationAddId().toString()));
		dto.setTaxCompanyID(Utilitario.datoNulo(modelo.getTaxCompanyID()));
		dto.setTaxCompanyIDschemeID(Utilitario.datoNulo(modelo.getTaxCompanyIDschemeID()));
		dto.setTaxCompanyIDschemeName(Utilitario.datoNulo(modelo.getTaxCompanyIDschemeName()));
		dto.setTaxLevelCode(Utilitario.datoNulo(modelo.getTaxLevelCode()));
		dto.setTaxLevelCodeListName(Utilitario.datoNulo(modelo.getTaxLevelCodeListName()));
		dto.setTaxSchemeID(Utilitario.datoNulo(modelo.getTaxSchemeID()));
		dto.setTaxSchemeName(Utilitario.datoNulo(modelo.getTaxSchemeName()));
		dto.setContactID(modelo.getId().toString());
		dto.setContactName(modelo.getContactName());
		dto.setTipoPersona(modelo.getTipoPersona());
		// seccion rec
		dto.setEmail(Utilitario.datoNulo(modelo.getEmail()));
		dto.setNombre(Utilitario.datoNulo(modelo.getNombre()));
		dto.setEnviarEmail(validaCero(modelo.getEnviarEmail()));
		dto.setIncluirAnexos(validaCero(modelo.getIncluirAnexos()));
		dto.setIncluirPDF(validaCero(modelo.getIncluirPDF()));
		dto.setIncluirXML(validaCero(modelo.getIncluirXML()));
		//seccion add
		dto.setId(modelo.getId().toString());
		dto.setCityID(modelo.getCityID());
		dto.setCityName(modelo.getCityName());
		dto.setCountryCode(modelo.getCountryCode());
		dto.setCountryName(modelo.getCountryName());
		dto.setCountrySubentity(modelo.getCountrySubentity());
		dto.setCountrySubentityCode(modelo.getCountrySubentityCode());
		dto.setPostalZone(modelo.getPostalZone());
		dto.setAddressLine(modelo.getAddressLine());
		return dto;
	}
	
	@Override
	public ReceptoresDireccionModel dtoAModelo(ReceptoresDireccionDto dto) {
		return null;
	}
	
	private boolean validaCero(String valor) {
		boolean valida = true;
		if(valor.equals("0")) valida = false;
		return valida;
	}
	
}
