package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcionesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcionesDTO implements IBaseDTO<OpcionesDTO, OpcionesModel> {
	
	private Integer id;
	private String idStr;
	private String nombre;
	

	public OpcionesDTO() {
		super();
	}

	public OpcionesDTO(Integer id, String idStr, String nombre) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.nombre = nombre;
	}

	@Override
	public OpcionesDTO modeloAdto(OpcionesModel modelo) {
		OpcionesDTO dto = new OpcionesDTO();
		
		dto.setNombre(modelo.getSistema());
		dto.setIdStr(modelo.getSistema());
		dto.setId(modelo.getId());
		return dto;
	}


	@Override
	public OpcionesModel dtoAModelo(OpcionesDTO dto) {
		OpcionesModel modelo = new OpcionesModel();
		
		modelo.setSistema(dto.getNombre());
		modelo.setId(dto.getId());
		return modelo;
	}
	
	
}
