package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DetalleCreditoModel {

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "DebitedQuantity")
	private Integer debitedQuantity;
	
	@Column(name = "DebitedQuantityUnitCode")
	private String debitedQuantityUnitCode;
	
	@Column(name = "LineExtensionAmount")
	private Integer lineExtensionAmount;
	
	@Column(name = "PriceAmount")
	private Integer priceAmount;
	
	@Column(name = "BaseQuantity")
	private Integer baseQuantity;
	
	@Column(name = "BaseQuantity_unitCode")
	private String baseQuantityUnitCode;
	
	@Column(name = "Item_Description")
	private String itemDescription;
	
	@Column(name = "Standard_ItemID")
	private String standardItemID;
	
	@Column(name = "Standard_ItemID_SchemeID")
	private String standardItemIDSchemeID;

}
