package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalesDTO {
	
	@SerializedName("LineExtensionAmount")
	private BigInteger lineExtensionAmount;
	@SerializedName("TaxExclusiveAmount")
	private BigInteger taxExclusiveAmount;
	@SerializedName("TaxInclusiveAmount")
	private BigInteger taxInclusiveAmount;
	@SerializedName("PayableAmount")
	private BigInteger payableAmount;
	
	public TotalesDTO() {
		super();
	}
	
	public TotalesDTO(BigInteger lineExtensionAmount, BigInteger taxExclusiveAmount, BigInteger taxInclusiveAmount,
			BigInteger payableAmount) {
		super();
		this.lineExtensionAmount = lineExtensionAmount;
		this.taxExclusiveAmount = taxExclusiveAmount;
		this.taxInclusiveAmount = taxInclusiveAmount;
		this.payableAmount = payableAmount;
	}

}
