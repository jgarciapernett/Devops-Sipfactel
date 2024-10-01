package co.com.periferia.alfa.core.model;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EncabezadoNotaDebModel {

	@Id
	@Column(name = "IDDEB")
	private Integer idDeb;

	@Column(name = "UBLVersionID")
	private String ublVersionID;

	@Column(name = "CustomizationID")
	private String customizationID;

	@Column(name = "ProfileID")
	private String profileID;

	@Column(name = "ProfileExecutionID")
	private String profileExecutionID;

	@Column(name = "ID")
	private String id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "IssueDate")
	private String issueDate;

	@Column(name = "IssueTime")
	private Timestamp issueTime;

	@Column(name = "DocumentCurrencyCode")
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

	@Column(name = "NumReferencia")
	private Integer numReferencia;

	@Column(name = "NumEndoso")
	private String numEndoso;

	@Column(name = "Nit")
	private String nit;

	@Column(name = "IdPoliza")
	private Integer idPoliza;

}
