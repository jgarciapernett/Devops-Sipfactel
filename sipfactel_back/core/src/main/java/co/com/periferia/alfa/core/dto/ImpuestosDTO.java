package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.ImpuestosModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImpuestosDTO implements IBaseDTO<ImpuestosDTO, ImpuestosModel> {

	@SerializedName("TaxAmount")
	private BigInteger taxAmount;
	@SerializedName("SchemeID")
	private String schemeID;
	@SerializedName("SchemeName")
	private String schemeName;
	@SerializedName("TaxSubTotal1_TaxableAmount")
	private BigInteger taxSubTotal1TaxableAmount;
	@SerializedName("TaxSubTotal1_TaxAmount")
	private BigInteger taxSubTotal1TaxAmount;
	@SerializedName("TaxSubTotal1_Percent")
	private BigInteger taxSubTotal1Percent;

	public ImpuestosDTO() {}
	
	public ImpuestosDTO(BigInteger taxAmount, String schemeID, String schemeName, BigInteger taxSubTotal1TaxableAmount,
			BigInteger taxSubTotal1TaxAmount, BigInteger taxSubTotal1Percent) {
		super();
		this.taxAmount = taxAmount;
		this.schemeID = schemeID;
		this.schemeName = schemeName;
		this.taxSubTotal1TaxableAmount = taxSubTotal1TaxableAmount;
		this.taxSubTotal1TaxAmount = taxSubTotal1TaxAmount;
		this.taxSubTotal1Percent = taxSubTotal1Percent;
	}

	@Override
	public ImpuestosDTO modeloAdto(ImpuestosModel modelo) {
		ImpuestosDTO dto = new ImpuestosDTO();
		dto.setTaxAmount(modelo.getTaxAmount());
		dto.setSchemeID(modelo.getSchemeID());
		dto.setSchemeName(modelo.getSchemeName());
		dto.setTaxSubTotal1TaxableAmount(modelo.getTaxSubTotal1TaxableAmount());
		dto.setTaxSubTotal1TaxAmount(modelo.getTaxSubTotal1TaxAmount());
		dto.setTaxSubTotal1Percent(modelo.getTaxSubTotal1Percent());
		return dto;
	}

	@Override
	public ImpuestosModel dtoAModelo(ImpuestosDTO dto) {
		return null;
	}

}
