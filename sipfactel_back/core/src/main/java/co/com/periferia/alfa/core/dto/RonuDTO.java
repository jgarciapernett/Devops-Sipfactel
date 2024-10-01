package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.admin.Ronu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RonuDTO implements IBaseDTO<RonuDTO, Ronu> {

	private Integer menuRol;
	private Integer rolesRoles;
	private Integer menuMenu;
	private Integer crear;
	private Integer consultar;
	private Integer modificar;

	@Override
	public RonuDTO modeloAdto(Ronu modelo) {
		return null;
	}

	@Override
	public Ronu dtoAModelo(RonuDTO dto) {
		Ronu modelo = new Ronu();
		modelo.setMenurol(dto.getMenuRol());
		modelo.setMenuMenu(dto.getMenuMenu());
		modelo.setRolesRoles(dto.getRolesRoles());
		modelo.setCrear(dto.getCrear());
		modelo.setConsultar(dto.getConsultar());
		modelo.setModificar(dto.getModificar());
		return modelo;
	}

}
