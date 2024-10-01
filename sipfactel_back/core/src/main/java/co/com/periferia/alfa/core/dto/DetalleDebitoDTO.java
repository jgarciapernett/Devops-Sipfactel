package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.DetalleDebitoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleDebitoDTO implements IBaseDTO<DetalleDebitoDTO, DetalleDebitoModel> {

	@SerializedName("ID")
	private String id;
	@SerializedName("IDDNL")
	private String idDnl;
	@SerializedName("DebitedQuantity")
	private BigInteger debitedQuantity;
	@SerializedName("DebitedQuantityUnitCode")
	private String debitedQuantityUnitCode;
	@SerializedName("LineExtensionAmount")
	private BigInteger lineExtensionAmount;
	@SerializedName("PriceAmount")
	private BigInteger priceAmount;
	@SerializedName("BaseQuantity")
	private Integer baseQuantity;
	@SerializedName("BaseQuantity_unitCode")
	private String baseQuantityUnitCode;
	@SerializedName("Item_Description")
	private String itemDescription;
	@SerializedName("Standard_ItemID")
	private String standardItemID;
	@SerializedName("Standard_ItemID_SchemeID")
	private String standardItemIDSchemeID;

	@Override
	public DetalleDebitoDTO modeloAdto(DetalleDebitoModel modelo) {
		DetalleDebitoDTO dto = new DetalleDebitoDTO();
		dto.setId(modelo.getId().toString());
		dto.setIdDnl(modelo.getIdDnl().toString());
		dto.setDebitedQuantity(modelo.getDebitedQuantity());
		dto.setDebitedQuantityUnitCode(modelo.getDebitedQuantityUnitCode());
		dto.setLineExtensionAmount(modelo.getLineExtensionAmount());
		dto.setPriceAmount(modelo.getPriceAmount());
		dto.setBaseQuantity(modelo.getBaseQuantity());
		dto.setBaseQuantityUnitCode(modelo.getBaseQuantityUnitCode());
		dto.setItemDescription(modelo.getItemDescription());
		dto.setStandardItemID(modelo.getStandardItemID());
		dto.setStandardItemIDSchemeID(modelo.getStandardItemIDSchemeID());
		return dto;
	}

	@Override
	public DetalleDebitoModel dtoAModelo(DetalleDebitoDTO dto) {
		return null;
	}
}