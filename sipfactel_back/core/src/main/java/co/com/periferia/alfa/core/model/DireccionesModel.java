package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DireccionesModel {

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CityID", length = 300)
	private String cityID;
	
	@Column(name = "CityName", length = 500)
	private String cityName;
	
	@Column(name = "PostalZone", length = 50)
	private String postalZone;
	
	@Column(name = "CountrySubentity", length = 500)
	private String countrySubentity;
	
	@Column(name = "CountrySubentityCode", length = 300)
	private String countrySubentityCode;
	
	@Column(name = "AddressLine")
	private String addressLine;
	
	@Column(name = "CountryName", length = 500)
	private String countryName;
	
	@Column(name = "CountryCode", length = 300)
	private String countryCode;

}
