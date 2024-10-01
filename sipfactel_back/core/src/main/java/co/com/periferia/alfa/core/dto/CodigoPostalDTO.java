package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.CodigoPostalModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodigoPostalDTO implements IBaseDTO<CodigoPostalDTO, CodigoPostalModel> {
	
	private Integer id;
	private String nombre;
	private String codpostal;
	
	@Override
	public CodigoPostalDTO modeloAdto(CodigoPostalModel modelo) {
		CodigoPostalDTO dto = new CodigoPostalDTO();
		dto.setId(modelo.getId());
		dto.setNombre(modelo.getNombre());
		dto.setCodpostal(modelo.getCodpostal());
		return dto;
	}
	@Override
	public CodigoPostalModel dtoAModelo(CodigoPostalDTO dto) {
		return null;
	}
	
	

}
