package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcRolesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcRolesDTO implements IBaseDTO<OpcRolesDTO, OpcRolesModel> {

	private Integer id;
	private String idStr;
	private String nombre;
	
	@Override
	public OpcRolesDTO modeloAdto(OpcRolesModel modelo) {
		OpcRolesDTO dto = new OpcRolesDTO();
		dto.setId(modelo.getRoleRole());
		dto.setNombre(modelo.getRoleNombre());
		dto.setIdStr(modelo.getRoleNombre());
		return dto;
	}
	@Override
	public OpcRolesModel dtoAModelo(OpcRolesDTO dto) {
		return null;
	}
	
	
}
