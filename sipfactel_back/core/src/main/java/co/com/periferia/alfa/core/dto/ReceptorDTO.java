package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.ReceptorModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceptorDTO implements IBaseDTO<ReceptorDTO, ReceptorModel> {

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

	@Override
	public ReceptorDTO modeloAdto(ReceptorModel modelo) {
		ReceptorDTO dto = new ReceptorDTO();
		dto.setAdditionalAccountID(nulo(modelo.getAdditionalAccountID()));
		dto.setPartyName(nulo(modelo.getPartyName()));
		dto.setPhysicalAddId(nulo(modelo.getPhysicalAddId().toString()));
		dto.setTaxRegistrationName(nulo(modelo.getTaxRegistrationName()));
		dto.setRegistrationAddId(nulo(modelo.getRegistrationAddId().toString()));
		dto.setTaxCompanyID(nulo(modelo.getTaxCompanyID()));
		dto.setTaxCompanyIDschemeID(nulo(modelo.getTaxCompanyIDschemeID()));
		dto.setTaxCompanyIDschemeName(nulo(modelo.getTaxCompanyIDschemeName()));
		dto.setTaxLevelCode(nulo(modelo.getTaxLevelCode()));
		dto.setTaxLevelCodeListName(nulo(modelo.getTaxLevelCodeListName()));
		dto.setTaxSchemeID(nulo(modelo.getTaxSchemeID()));
		dto.setTaxSchemeName(nulo(modelo.getTaxSchemeName()));
		dto.setContactID(modelo.getContactID());
		dto.setContactName(modelo.getContactName());
		dto.setTipoPersona(modelo.getTipoPersona());
		return dto;
	}

	@Override
	public ReceptorModel dtoAModelo(ReceptorDTO dto) {
		return null;
	}

	public String nulo(String dato) {

		if (dato == null) {
			dato = "";
		}
		return dato;
	}
}
