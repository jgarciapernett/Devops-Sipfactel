package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.EncabezadoNotaCreModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncabezadoNotaCreDTO implements IBaseDTO<EncabezadoNotaCreDTO, EncabezadoNotaCreModel> {

	@SerializedName("IDCRE")
	private Integer idCre;
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
	@SerializedName("CreditNoteTypeCode")
	private Integer creditNoteTypeCode;
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
	private Integer numRefencia;
	private String numPoliza;
	private Integer tipoDocumento;
	private String numEndoso;
	private String nit;
	private Integer idPoliza;

	@Override
	public EncabezadoNotaCreDTO modeloAdto(EncabezadoNotaCreModel modelo) {
		EncabezadoNotaCreDTO dto = new EncabezadoNotaCreDTO();
		dto.setIdCre(nulocero(modelo.getIdCre()));
		dto.setUblVersionID(nulo(modelo.getUblVersionID()));
		dto.setCustomizationID(nulo(modelo.getCustomizationID()));
		dto.setProfileID(nulo(modelo.getProfileID()));
		dto.setProfileExecutionID(nulo(modelo.getProfileExecutionID()));
		dto.setId(nulo(modelo.getId()));
		dto.setUuid(nulo(modelo.getUuid()));
		dto.setIssueDate(nulo(modelo.getIssueDate()));
		dto.setIssueTime(nulo(modelo.getIssueTime().toString()));
		dto.setCreditNoteTypeCode(Integer.parseInt(modelo.getCreditNoteTypeCode()));
		dto.setDocumentCurrencyCode(nulo(modelo.getDocumentCurrencyCode()));
		dto.setLineCountNumeric(nulocero(modelo.getLineCountNumeric()));
		dto.setLineExtensionAmount(modelo.getLineExtensionAmount());
		dto.setTaxExclusiveAmount(modelo.getTaxExclusiveAmount());
		dto.setTaxInclusiveAmount(modelo.getTaxInclusiveAmount());
		dto.setPayableAmount(modelo.getPayableAmount());
		dto.setNumRefencia(modelo.getNumReferencia());
		dto.setNumPoliza(modelo.getNumPoliza());
		dto.setTipoDocumento(modelo.getTipoDocumento());
		dto.setNumEndoso(modelo.getNumEndoso());
		dto.setNit(modelo.getNit());
		dto.setIdPoliza(modelo.getIdPoliza());
		return dto;
	}

	@Override
	public EncabezadoNotaCreModel dtoAModelo(EncabezadoNotaCreDTO dto) {
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
