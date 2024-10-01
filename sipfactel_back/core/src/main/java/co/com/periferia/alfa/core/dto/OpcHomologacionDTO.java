package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcHomologacionModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcHomologacionDTO implements IBaseDTO<OpcHomologacionDTO, OpcHomologacionModel> {

	private Integer id;
	private String idStr;
	private String nombre;
	

	public OpcHomologacionDTO() {
		super();
	}

	public OpcHomologacionDTO(Integer id, String idStr, String nombre) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.nombre = nombre;
	}

	@Override
	public OpcHomologacionDTO modeloAdto(OpcHomologacionModel modelo) {
		OpcHomologacionDTO dto = new OpcHomologacionDTO();
		
		dto.setNombre(modelo.getDescripcion());
		dto.setIdStr(modelo.getDescripcion());
		dto.setId(modelo.getId());
		return dto;
	}


	@Override
	public OpcHomologacionModel dtoAModelo(OpcHomologacionDTO dto) {
		OpcHomologacionModel modelo = new OpcHomologacionModel();
		
		modelo.setDescripcion(dto.getNombre());
		modelo.setId(dto.getId());
		return modelo;
	}
	
	
	
}
