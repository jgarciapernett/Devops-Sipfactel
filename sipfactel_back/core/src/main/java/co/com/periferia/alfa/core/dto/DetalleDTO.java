package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.DetalleModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleDTO implements IBaseDTO<DetalleDTO, DetalleModel> {

	@SerializedName("ID")
	private String id;
	@SerializedName("IDIVL")
	private String idIvl; 
	@SerializedName("InvoicedQuantity")
	private float invoicedQuantity;
	@SerializedName("InvoicedQuantityUnitCode")
	private String invoicedQuantityUnitCode;
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
	public DetalleDTO modeloAdto(DetalleModel modelo) {
		DetalleDTO dto = new DetalleDTO();
		dto.setId(modelo.getId().toString());
		dto.setIdIvl(modelo.getIdIvl().toString()); 
		dto.setInvoicedQuantity(modelo.getInvoicedQuantity());
		dto.setInvoicedQuantityUnitCode(modelo.getInvoicedQuantityUnitCode());
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
	public DetalleModel dtoAModelo(DetalleDTO dto) {
		return null;
	}


}
