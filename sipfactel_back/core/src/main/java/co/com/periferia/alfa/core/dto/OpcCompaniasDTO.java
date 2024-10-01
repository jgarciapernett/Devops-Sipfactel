package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcCompaniasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcCompaniasDTO implements IBaseDTO<OpcCompaniasDTO, OpcCompaniasModel>{

	private Integer id;
	private String idStr;
	private String nombre;
	
	public OpcCompaniasDTO() {
		super();
	}

	public OpcCompaniasDTO(Integer id, String idStr, String nombre) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.nombre = nombre;
	}

	@Override
	public OpcCompaniasDTO modeloAdto(OpcCompaniasModel modelo) {
		OpcCompaniasDTO dto= new OpcCompaniasDTO();
		
		dto.setId(modelo.getId());
		dto.setIdStr(modelo.getNombre());
		dto.setNombre(modelo.getNombre());
		return dto;
	}

	@Override
	public OpcCompaniasModel dtoAModelo(OpcCompaniasDTO dto) {
		OpcCompaniasModel modelo= new OpcCompaniasModel();
		
		modelo.setId(dto.getId());
		modelo.setNombre(dto.getNombre());
		return modelo;
	}

}
