package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.FuenteModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuenteDTO implements IBaseDTO<FuenteDTO, FuenteModel>{

	private Integer id;
	private String idStr;
	private String nombre;
	
	@Override
	public FuenteDTO modeloAdto(FuenteModel modelo) {
		FuenteDTO dto = new FuenteDTO();
		dto.setId(Integer.parseInt(modelo.getId()));
		dto.setIdStr(modelo.getValor());
		dto.setNombre(modelo.getValor());
		return dto;
	}
	@Override
	public FuenteModel dtoAModelo(FuenteDTO dto) {
		return null;
	}
	
}
