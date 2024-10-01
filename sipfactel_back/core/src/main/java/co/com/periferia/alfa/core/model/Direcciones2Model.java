package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Direcciones2Model {
	
	@Id
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CityID")
	private String cityID;
	
	@Column(name = "CityName")
	private String cityName;
	
	@Column(name = "PostalZone")
	private String postalZone;
	
	@Column(name = "CountrySubentity")
	private String countrySubentity;
	
	@Column(name = "CountrySubentityCode")
	private String countrySubentityCode;
	
	@Column(name = "AddressLine")
	private String addressLine;
	
	@Column(name = "CountryName")
	private String countryName;
	
	@Column(name = "CountryCode")
	private String countryCode;

}
