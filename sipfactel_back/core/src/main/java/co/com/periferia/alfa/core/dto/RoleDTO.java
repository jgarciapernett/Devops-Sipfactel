package co.com.periferia.alfa.core.dto;

import java.util.Date;
import java.util.List;

import co.com.periferia.alfa.core.model.admin.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO implements IBaseDTO<RoleDTO, Roles> {
	
	
	private Integer roleRole;
	private String  roleNombre;
	private String 	roleDescripcion;
	private String 	roleEsta;
	private String 	roleUcreacion;
	private Date 	roleFcreacion;
	private String 	roleUactualizacion;
	private Date 	roleFactualizacion;
	private Integer categoria;
	private List<RonuNuevoDTO> modulos;

	@Override
	public RoleDTO modeloAdto(Roles modelo) {
		RoleDTO dto = new RoleDTO();
		if(modelo.getRoleRole() != null) {
			dto.setRoleRole(modelo.getRoleRole());
		}
		dto.setRoleNombre(modelo.getRoleNombre());
		dto.setRoleDescripcion(modelo.getRoleDescripcion());
        dto.setRoleEsta(modelo.getRoleEsta());
        dto.setRoleUcreacion(modelo.getRoleUcreacion());
        dto.setRoleFcreacion(modelo.getRoleFcreacion());
        dto.setRoleUactualizacion(modelo.getRoleUactualizacion());
        dto.setRoleFactualizacion(modelo.getRoleFactualizacion());
        dto.setCategoria(modelo.getCategoria());
        return dto;
	}

	@Override
	public Roles dtoAModelo(RoleDTO dto) {
		Roles modelo = new Roles();
		if(modelo.getRoleRole() != null) {
			modelo.setRoleRole(dto.getRoleRole());
		}
		modelo.setRoleNombre(dto.getRoleNombre());
		modelo.setRoleDescripcion(dto.getRoleDescripcion());
		modelo.setRoleEsta(dto.getRoleEsta());
		modelo.setRoleUcreacion(dto.getRoleUcreacion());
		modelo.setRoleFcreacion(dto.getRoleFcreacion());
		modelo.setRoleUactualizacion(dto.getRoleUactualizacion());
		modelo.setRoleFactualizacion(dto.getRoleFactualizacion());
		modelo.setCategoria(dto.getCategoria());
		return modelo;
	}
	
	public RoleDTO modeloAdtomenu(Roles modelo, List<RonuNuevoDTO> hijos) {
		RoleDTO dto = new RoleDTO();
		if(modelo.getRoleRole() != null) {
			dto.setRoleRole(modelo.getRoleRole());
		}
		dto.setRoleNombre(modelo.getRoleNombre());
		dto.setRoleDescripcion(modelo.getRoleDescripcion());
		dto.setRoleEsta(modelo.getRoleEsta());
        dto.setRoleUcreacion(modelo.getRoleUcreacion());
        dto.setRoleFcreacion(modelo.getRoleFcreacion());
        dto.setRoleUactualizacion(modelo.getRoleUactualizacion());
        dto.setRoleFactualizacion(modelo.getRoleFactualizacion());
        dto.setCategoria(modelo.getCategoria());
        dto.setModulos(hijos);
        return dto;
	}

}
