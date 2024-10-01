package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.EncabezadoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncabezadoDTO implements IBaseDTO<EncabezadoDTO, EncabezadoModel> {

	@SerializedName("IDFAC")
	private Integer idFac;
	@SerializedName("UBLVersionID")
	private String ublVersionID;
	@SerializedName("CustomizationID")
	private String customizationID;
	@SerializedName("ProfileID")
	private String profileID;
	@SerializedName("ProfileExecutionID")
	private String profileExecutionID;
	@SerializedName("ID")
	private String id;
	@SerializedName("UUID")
	private String uuid;
	@SerializedName("IssueDate")
	private String issueDate;
	@SerializedName("IssueTime")
	private String issueTime;
	@SerializedName("InvoiceTypeCode")
	private String invoiceTypeCode;
	@SerializedName("DocumentCurrencyCode")
	private String documentCurrencyCode;
	@SerializedName("LineCountNumeric")
	private Integer lineCountNumeric;
	@SerializedName("LineExtensionAmount")
	private BigInteger lineExtensionAmount;
	@SerializedName("TaxExclusiveAmount")
	private BigInteger taxExclusiveAmount;
	@SerializedName("TaxInclusiveAmount")
	private BigInteger taxInclusiveAmount;
	@SerializedName("PayableAmount")
	private BigInteger payableAmount;
	private String numPoliza;
	private Integer tipoDocumento;
	private String numEndoso;
	private String nit;
	private Integer idPoliza;

	@Override
	public EncabezadoDTO modeloAdto(EncabezadoModel modelo) {
		EncabezadoDTO dto = new EncabezadoDTO();
		dto.setIdFac(nulocero(modelo.getIdFac()));
		dto.setUblVersionID(nulo(modelo.getUblVersionID()));
		dto.setCustomizationID(nulo(modelo.getCustomizationID()));
		dto.setProfileID(nulo(modelo.getProfileID()));
		dto.setProfileExecutionID(nulo(modelo.getProfileExecutionID()));
		dto.setId(nulo(modelo.getId()));
		dto.setUuid(nulo(modelo.getUuid()));
		dto.setIssueDate(nulo(modelo.getIssueDate()));
		dto.setIssueTime(nulo(modelo.getIssueTime()));
		dto.setInvoiceTypeCode(nulo(modelo.getInvoiceTypeCode()));
		dto.setDocumentCurrencyCode(nulo(modelo.getDocumentCurrencyCode()));
		dto.setLineCountNumeric(nulocero(modelo.getLineCountNumeric()));
		dto.setLineExtensionAmount(modelo.getLineExtensionAmount());
		dto.setTaxExclusiveAmount(modelo.getTaxExclusiveAmount());
		dto.setTaxInclusiveAmount(modelo.getTaxInclusiveAmount());
		dto.setPayableAmount(modelo.getPayableAmount());
		dto.setNumPoliza(modelo.getNumPoliza());
		dto.setTipoDocumento(modelo.getTipoDocumento());
		dto.setNumEndoso(modelo.getNumEndoso());
		dto.setNit(modelo.getNit());
		dto.setIdPoliza(modelo.getIdPoliza());
		return dto;
	}

	@Override
	public EncabezadoModel dtoAModelo(EncabezadoDTO dto) {
		return null;
	}

	public String nulo(String dato) {

		if (dato == null) {
			dato = "";
		}
		return dato;
	}

	public Integer nulocero(Integer dato) {

		if (dato == null) {
			dato = 0;
		}
		return dato;
	}

}
