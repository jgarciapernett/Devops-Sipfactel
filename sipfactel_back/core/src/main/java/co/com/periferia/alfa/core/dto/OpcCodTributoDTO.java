package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcCodTributoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcCodTributoDTO implements IBaseDTO<OpcCodTributoDTO, OpcCodTributoModel>{

	private Integer id;
	private String idStr;
	private String nombre;
	

	public OpcCodTributoDTO() {
		super();
	}

	public OpcCodTributoDTO(Integer id, String idStr, String nombre) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.nombre = nombre;
	}

	@Override
	public OpcCodTributoDTO modeloAdto(OpcCodTributoModel modelo) {
		OpcCodTributoDTO dto = new OpcCodTributoDTO();
		dto.setId(modelo.getCodTributo());
		dto.setIdStr(modelo.getDescripcion());
		dto.setNombre(modelo.getDescripcion());				
		return dto;	
	}

	@Override
	public OpcCodTributoModel dtoAModelo(OpcCodTributoDTO dto) {
		OpcCodTributoModel modelo = new OpcCodTributoModel();
		modelo.setCodTributo(dto.getId());
		modelo.setDescripcion(dto.getNombre());
		return modelo;
	}
	
}
