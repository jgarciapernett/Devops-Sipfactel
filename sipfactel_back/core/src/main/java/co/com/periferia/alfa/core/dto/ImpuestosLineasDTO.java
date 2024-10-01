package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.ImpuestosLineasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImpuestosLineasDTO implements IBaseDTO<ImpuestosLineasDTO, ImpuestosLineasModel> {

	@SerializedName("ID")
	private String id;
	@SerializedName("IDLIT")
	private String idLit;
	@SerializedName("TaxAmount")
	private BigInteger taxAmount;
	@SerializedName("SchemeID")
	private String schemeID;
	@SerializedName("SchemeName")
	private String schemeName;
	@SerializedName("TaxSubTotal_TaxableAmount")
	private BigInteger taxSubTotalTaxableAmount;
	@SerializedName("TaxSubTotal_TaxAmount")
	private BigInteger taxSubTotalTaxAmount;
	@SerializedName("TaxSubTotal_Percent")
	private BigInteger taxSubTotalPercent;

	@Override
	public ImpuestosLineasDTO modeloAdto(ImpuestosLineasModel modelo) {
		ImpuestosLineasDTO dto = new ImpuestosLineasDTO();
		dto.setId(modelo.getId().toString());
		dto.setIdLit(modelo.getIdLit().toString());
		dto.setTaxAmount(nuloBig(modelo.getTaxAmount()));
		dto.setSchemeID(nulo(modelo.getSchemeID()));
		dto.setSchemeName(nulo(modelo.getSchemeName()));
		dto.setTaxSubTotalTaxableAmount(nuloBig(modelo.getTaxSubTotal1TaxableAmount()));
		dto.setTaxSubTotalTaxAmount(nuloBig(modelo.getTaxSubTotal1TaxAmount()));
		dto.setTaxSubTotalPercent(nuloBig(modelo.getTaxSubTotal1Percent()));
		return dto;
	}

	@Override
	public ImpuestosLineasModel dtoAModelo(ImpuestosLineasDTO dto) {
		return null;
	}

	public BigInteger nuloBig(BigInteger dato) {
		if (dato == null) {
			dato = BigInteger.ZERO;
		}
		return dato;
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
