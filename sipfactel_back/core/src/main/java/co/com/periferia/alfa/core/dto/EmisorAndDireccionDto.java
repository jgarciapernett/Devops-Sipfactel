package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.EmisorAndDireccionModel;
import co.com.periferia.alfa.core.utilitarios.Utilitario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmisorAndDireccionDto implements IBaseDTO<EmisorAndDireccionDto, EmisorAndDireccionModel> {

	@SerializedName("AdditionalAccountID")
	private String additionalAccountID;
	@SerializedName("PartyName")
	private String partyName;
	@SerializedName("Physical_ADD_ID")
	private String physicalAddID;
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
	private String registrationAddID;
	@SerializedName("Contact_id")
	private String contactId;
	@SerializedName("nombre_contacto")
	private String nombreContacto;
	@SerializedName("ElectronicMail")
	private String electronicMail;
	//seccion direcciones
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
	public EmisorAndDireccionDto modeloAdto(EmisorAndDireccionModel modelo) {
		EmisorAndDireccionDto dto = new EmisorAndDireccionDto();
		dto.setAdditionalAccountID(modelo.getAdditionalAccountID());
		dto.setPartyName(modelo.getPartyName());
		dto.setPhysicalAddID(modelo.getPhysicalAddID().toString());
		dto.setTaxRegistrationName(modelo.getTaxRegistrationName());
		dto.setTaxCompanyID(modelo.getTaxCompanyID());
		dto.setTaxCompanyIDschemeID(Utilitario.datoNulo(modelo.getTaxCompanyIDschemeID()));
		dto.setTaxCompanyIDschemeName(modelo.getTaxCompanyIDschemeName());
		dto.setTaxLevelCode(modelo.getTaxLevelCode());
		dto.setTaxLevelCodeListName(modelo.getTaxLevelCodeListName());
		dto.setTaxSchemeID(modelo.getTaxSchemeID());
		dto.setTaxSchemeName(modelo.getTaxSchemeName());
		dto.setRegistrationAddID(modelo.getRegistrationAddID().toString());
		dto.setContactId(modelo.getContactId());
		dto.setNombreContacto(modelo.getNombreContacto());
		dto.setElectronicMail(modelo.getElectronicMail());
		//seccion direcciones
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
	public EmisorAndDireccionModel dtoAModelo(EmisorAndDireccionDto dto) {
		return null;
	}
	
}
