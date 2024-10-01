package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.DesplegablesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesplegablesDTO implements IBaseDTO<DesplegablesDTO, DesplegablesModel>{

	private Integer id;
	private String idStr;
	private String nombre;

	public DesplegablesDTO() {
		super();
	}

	public DesplegablesDTO(Integer id, String idStr, String nombre) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.nombre = nombre;
	}

	@Override
	public DesplegablesDTO modeloAdto(DesplegablesModel modelo) {
		DesplegablesDTO dto =  new DesplegablesDTO();
		dto.setId(modelo.getId());
		dto.setIdStr(modelo.getValor());
		dto.setNombre(modelo.getValor());
		return dto;
	}

	@Override
	public DesplegablesModel dtoAModelo(DesplegablesDTO dto) {
		return null;
	}

}
