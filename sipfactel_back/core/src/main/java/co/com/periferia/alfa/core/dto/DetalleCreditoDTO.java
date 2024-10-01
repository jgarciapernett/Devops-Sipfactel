package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.DetalleCreditoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleCreditoDTO implements IBaseDTO<DetalleCreditoDTO, DetalleCreditoModel> {

	@SerializedName("ID")
	private String id;
	@SerializedName("CreditedQuantity")
	private float creditedQuantity;
	@SerializedName("CreditedQuantityUnitCode")
	private String creditedQuantityUnitCode;
	@SerializedName("LineExtensionAmount")
	private float lineExtensionAmount;
	@SerializedName("PriceAmount")
	private float priceAmount;
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
	public DetalleCreditoDTO modeloAdto(DetalleCreditoModel modelo) {
		DetalleCreditoDTO dto = new DetalleCreditoDTO();
		dto.setId(modelo.getId().toString());
		dto.setCreditedQuantity(modelo.getDebitedQuantity());
		dto.setCreditedQuantityUnitCode(modelo.getDebitedQuantityUnitCode());
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
	public DetalleCreditoModel dtoAModelo(DetalleCreditoDTO dto) {
		return null;
	}

}
