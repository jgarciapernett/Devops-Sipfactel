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
public class DetalleModel {

	@Column(name = "ID")
	private Integer id;

	@Id
	@Column(name = "IDIVL")
	private Integer idIvl;
	
	@Column(name = "InvoicedQuantity")
	private Integer invoicedQuantity;
	
	@Column(name = "InvoicedQuantityUnitCode")
	private String invoicedQuantityUnitCode;
	
	@Column(name = "LineExtensionAmount")
	private BigInteger lineExtensionAmount;
	
	@Column(name = "PriceAmount")
	private BigInteger priceAmount;
	
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
