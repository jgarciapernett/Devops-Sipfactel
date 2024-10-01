package co.com.periferia.alfa.core.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.ExtensionesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class ExtensionesDTO implements IBaseDTO<ExtensionesDTO, ExtensionesModel>, Serializable {

	@SerializedName("InvoiceAuthorization")
	private Long invoiceAuthorization;
	@SerializedName("StartDate")
	private String startDate;
	@SerializedName("EndDate")
	private String endDate;
	@SerializedName("Prefix")
	private String prefix;
	@SerializedName("From")
	private Long from;
	@SerializedName("To")
	private Long to;
	@SerializedName("IdentificationCode")
	private String identificationCode;
	@SerializedName("ProviderID")
	private String providerID;
	@SerializedName("ProviderID_schemeID")
	private String providerIDschemeID;
	@SerializedName("SoftwareID")
	private String softwareID;
	@SerializedName("SoftwareSecurityCode")
	private String softwareSecurityCode;
	@SerializedName("AuthorizationProviderID")
	private String authorizationProviderID;
	@SerializedName("AuthorizationProviderID_schemeID")
	private String authorizationProviderIDschemeID;
	@SerializedName("QRCode")
	private String qrCode;

	@Override
	public ExtensionesDTO modeloAdto(ExtensionesModel modelo) {
		ExtensionesDTO dto = new ExtensionesDTO();
		dto.setInvoiceAuthorization(Long.parseLong(modelo.getInvoiceAuthorization()));
		dto.setStartDate(modelo.getStartDate().toString());
		dto.setEndDate(modelo.getEndDate().toString());
		dto.setPrefix(nulo(modelo.getPrefix()));
		dto.setFrom(Long.parseLong(nulo(modelo.getFrom())));
		dto.setTo(Long.parseLong(nulo(modelo.getTo())));
		dto.setIdentificationCode(nulo(modelo.getIdentificationCode()));
		dto.setProviderID(nulo(modelo.getProviderID()));
		dto.setProviderIDschemeID(nulo(modelo.getProviderIDschemeID()));
		dto.setSoftwareID(nulo(modelo.getSoftwareID()));
		dto.setSoftwareSecurityCode(nulo(modelo.getSoftwareSecurityCode()));
		dto.setAuthorizationProviderID(nulo(modelo.getAuthorizationProviderID()));
		dto.setAuthorizationProviderIDschemeID(nulo(modelo.getAuthorizationProviderIDschemeID()));
		dto.setQrCode(nulo(modelo.getQrCode()));
		return dto;
	}

	@Override
	public ExtensionesModel dtoAModelo(ExtensionesDTO dto) {
		return null;
	}

	public String nulo(String dato) {

		if (dato == null) {
			dato = "";
		}
		return dato;
	}
}
