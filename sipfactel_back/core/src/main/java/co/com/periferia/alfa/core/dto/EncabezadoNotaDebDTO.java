package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.EncabezadoNotaDebModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncabezadoNotaDebDTO implements IBaseDTO<EncabezadoNotaDebDTO, EncabezadoNotaDebModel>{

	@SerializedName("IDDEB")
	private Integer idDeb;
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
	public EncabezadoNotaDebDTO modeloAdto(EncabezadoNotaDebModel modelo) {
		EncabezadoNotaDebDTO dto = new EncabezadoNotaDebDTO();
		dto.setIdDeb(nulocero(modelo.getIdDeb()));
		dto.setUblVersionID(nulo(modelo.getUblVersionID()));
		dto.setCustomizationID(nulo(modelo.getCustomizationID()));
		dto.setProfileID(nulo(modelo.getProfileID()));
		dto.setProfileExecutionID(nulo(modelo.getProfileExecutionID()));
		dto.setId(nulo(modelo.getId()));
		dto.setUuid(nulo(modelo.getUuid()));
		dto.setIssueDate(nulo(modelo.getIssueDate()));
		dto.setIssueTime(nulo(modelo.getIssueTime().toString()));
		dto.setDocumentCurrencyCode(nulo(modelo.getDocumentCurrencyCode()));
		dto.setLineCountNumeric(nulocero(modelo.getLineCountNumeric()));
		dto.setLineExtensionAmount(nuloBigInteger(modelo.getLineExtensionAmount()));
		dto.setTaxExclusiveAmount(nuloBigInteger(modelo.getTaxExclusiveAmount()));
		dto.setTaxInclusiveAmount(nuloBigInteger(modelo.getTaxInclusiveAmount()));
		dto.setPayableAmount(nuloBigInteger(modelo.getPayableAmount()));
		dto.setNumRefencia(modelo.getNumReferencia());
		dto.setNumPoliza(modelo.getNumPoliza());
		dto.setTipoDocumento(modelo.getTipoDocumento());
		dto.setNumEndoso(modelo.getNumEndoso());
		dto.setNit(modelo.getNit());
		dto.setIdPoliza(modelo.getIdPoliza());
		return dto;
	}

	@Override
	public EncabezadoNotaDebModel dtoAModelo(EncabezadoNotaDebDTO dto) {
		return null;
	}

	public BigInteger nuloBigInteger(BigInteger dato){
		if (dato==null) {
			dato = BigInteger.ZERO;
		}
		return dato;
	}
	
	public String nulo(String dato) {
		if(dato == null) {
			dato = "";
		}
		return dato;
	}
	
	public Integer nulocero(Integer dato) {
		if(dato == null) {
			dato = 0;
		}
		return dato;
	}
}
