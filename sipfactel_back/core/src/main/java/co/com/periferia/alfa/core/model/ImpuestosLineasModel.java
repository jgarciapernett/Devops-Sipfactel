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
public class ImpuestosLineasModel {
	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "IDLIT")
	private Integer idLit;

	@Column(name = "TaxAmount")
	private BigInteger taxAmount;

	@Column(name = "SchemeID")
	private String schemeID;

	@Column(name = "SchemeName")
	private String schemeName;

	@Column(name = "TaxSubTotal1_TaxableAmount")
	private BigInteger taxSubTotal1TaxableAmount;

	@Column(name = "TaxSubTotal1_TaxAmount")
	private BigInteger taxSubTotal1TaxAmount;

	@Column(name = "TaxSubTotal1_Percent")
	private BigInteger taxSubTotal1Percent;

}
