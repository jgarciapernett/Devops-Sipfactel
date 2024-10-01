package co.com.periferia.alfa.core.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExtensionesModel {

	@Id
	@Column(name = "InvoiceAuthorization", length = 15)
	private String invoiceAuthorization;
	
	@Column(name = "StartDate")
	private Date startDate;
	
	@Column(name = "EndDate")
	private Date endDate;
	
	@Column(name = "Prefix")
	private String prefix;
	
	@Column(name = "RANGOINICIAL", length = 15)
	private String from;
	
	@Column(name = "RANGOFINAL", length = 15)
	private String to;
	
	@Column(name = "IdentificationCode", length = 300)
	private String identificationCode;
	
	@Column(name = "ProviderID", length = 15)
	private String providerID;
	
	@Column(name = "ProviderID_schemeID", length = 10)
	private String providerIDschemeID;
	
	@Column(name = "SoftwareID", length = 10)
	private String softwareID;
	
	@Column(name = "SoftwareSecurityCode", length = 10)
	private String softwareSecurityCode;
	
	@Column(name = "AuthorizationProviderID", length = 15)
	private String authorizationProviderID;
	
	@Column(name = "AuthorizationProviderID_schemeID", length = 1)
	private String authorizationProviderIDschemeID;
	
	@Column(name = "QRCode", length = 10)
	private String qrCode;

}
