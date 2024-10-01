package co.com.periferia.alfa.core.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EncabezadoModel {

	@Id
	@Column(name = "IDFAC")
	private Integer idFac;

	@Column(name = "UBLVersionID", length = 10)
	private String ublVersionID;

	@Column(name = "CustomizationID", length = 300)
	private String customizationID;

	@Column(name = "ProfileID", length = 100)
	private String profileID;

	@Column(name = "ProfileExecutionID", length = 300)
	private String profileExecutionID;

	@Column(name = "ID", length = 100)
	private String id;

	@Column(name = "UUID", length = 100)
	private String uuid;

	@Column(name = "IssueDate")
	private String issueDate;

	@Column(name = "IssueTime")
	private String issueTime;

	@Column(name = "InvoiceTypeCode", length = 300)
	private String invoiceTypeCode;

	@Column(name = "DocumentCurrencyCode", length = 300)
	private String documentCurrencyCode;

	@Column(name = "LineCountNumeric")
	private Integer lineCountNumeric;

	@Column(name = "LineExtensionAmount")
	private BigInteger lineExtensionAmount;

	@Column(name = "TaxExclusiveAmount")
	private BigInteger taxExclusiveAmount;

	@Column(name = "TaxInclusiveAmount")
	private BigInteger taxInclusiveAmount;

	@Column(name = "PayableAmount")
	private BigInteger payableAmount;

	@Column(name = "Estado")
	private Integer estado;

	@Column(name = "NumPoliza")
	private String numPoliza;

	@Column(name = "TipoDocumento")
	private Integer tipoDocumento;

	@Column(name = "NumEndoso")
	private String numEndoso;

	@Column(name = "Nit")
	private String nit;

	@Column(name = "IdPoliza")
	private Integer idPoliza;

}
