package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.EmisorModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmisorDTO implements IBaseDTO<EmisorDTO, EmisorModel> {

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

	@Override
	public EmisorDTO modeloAdto(EmisorModel modelo) {
		EmisorDTO dto = new EmisorDTO();
		dto.setAdditionalAccountID(modelo.getAdditionalAccountID());
		dto.setPartyName(modelo.getPartyName());
		dto.setPhysicalAddID(modelo.getPhysicalAddID().toString());
		dto.setTaxRegistrationName(modelo.getTaxRegistrationName());
		dto.setTaxCompanyID(modelo.getTaxCompanyID());
		dto.setTaxCompanyIDschemeID(nulo(modelo.getTaxCompanyIDschemeID()));
		dto.setTaxCompanyIDschemeName(modelo.getTaxCompanyIDschemeName());
		dto.setTaxLevelCode(modelo.getTaxLevelCode());
		dto.setTaxLevelCodeListName(modelo.getTaxLevelCodeListName());
		dto.setTaxSchemeID(modelo.getTaxSchemeID());
		dto.setTaxSchemeName(modelo.getTaxSchemeName());
		dto.setRegistrationAddID(modelo.getRegistrationAddID().toString());
		dto.setContactId(modelo.getContactId());
		dto.setNombreContacto(modelo.getNombreContacto());
		dto.setElectronicMail(modelo.getElectronicMail());
		return dto;
	}

	@Override
	public EmisorModel dtoAModelo(EmisorDTO dto) {
		return null;
	}

	public String nulo(String dato) {

		if (dato == null) {
			dato = "";
		}
		return dato;
	}

}
