package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.Direcciones2Model;
import co.com.periferia.alfa.core.model.DireccionesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionesDTO implements IBaseDTO<DireccionesDTO, DireccionesModel>{

	@SerializedName("ID")
	private String id;
	@SerializedName("CityID")
	private String cityID;
	@SerializedName("CityName")
	private String cityName;
	@SerializedName("PostalZone")
	private String postalZone;
	@SerializedName("CountrySubentity")
	private String countrySubentity;
	@SerializedName("CountrySubentityCode")
	private String countrySubentityCode;
	@SerializedName("AddressLine")
	private String addressLine;
	@SerializedName("CountryName")
	private String countryName;
	@SerializedName("CountryCode")
	private String countryCode;

	@Override
	public DireccionesDTO modeloAdto(DireccionesModel modelo) {
		DireccionesDTO dto = new DireccionesDTO();
		dto.setId(modelo.getId().toString());
		dto.setCityID(modelo.getCityID());
		dto.setCityName(modelo.getCityName());
		dto.setCountryCode(modelo.getCountryCode());
		dto.setCountryName(modelo.getCountryName());
		dto.setCountrySubentity(modelo.getCountrySubentity());
		dto.setCountrySubentityCode(modelo.getCountrySubentityCode());
		dto.setPostalZone(modelo.getPostalZone());
		dto.setAddressLine(modelo.getAddressLine());
		return dto;
	}

	@Override
	public DireccionesModel dtoAModelo(DireccionesDTO dto) {
		return null;
	}
	
	public DireccionesDTO modeloAdto2(Direcciones2Model modelo) {
		DireccionesDTO dto = new DireccionesDTO();
		dto.setId(modelo.getId().toString());
		dto.setCityID(modelo.getCityID());
		dto.setCityName(modelo.getCityName());
		dto.setCountryCode(modelo.getCountryCode());
		dto.setCountryName(modelo.getCountryName());
		dto.setCountrySubentity(modelo.getCountrySubentity());
		dto.setCountrySubentityCode(modelo.getCountrySubentityCode());
		dto.setPostalZone(modelo.getPostalZone());
		dto.setAddressLine(modelo.getAddressLine());
		return dto;
	}
}
