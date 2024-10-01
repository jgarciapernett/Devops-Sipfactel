package co.com.periferia.alfa.core.dto;

import java.util.List;

import co.com.periferia.alfa.core.model.OpcMenuModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RonuNuevoDTO {

	private Integer idMenu;
	private String menuMenu;
	private Integer crear;
	private Integer consultar;
	private Integer modificar;
	private Integer padre;
	private List<RonuNuevoDTO> hijos;

	public RonuNuevoDTO dto(Integer idMenu, String menu, Integer crear, Integer modificar, Integer consultar) {
		RonuNuevoDTO dto = new RonuNuevoDTO();
		dto.setIdMenu(idMenu);
		dto.setMenuMenu(menu);
		dto.setCrear(crear);
		dto.setConsultar(consultar);
		dto.setModificar(modificar);
		return dto;

	}

	public RonuNuevoDTO dto(Integer idMenu, String menu, Integer crear, Integer modificar, Integer consultar,
			List<RonuNuevoDTO> hijos) {
		RonuNuevoDTO dto = new RonuNuevoDTO();
		dto.setIdMenu(idMenu);
		dto.setMenuMenu(menu);
		dto.setCrear(crear);
		dto.setConsultar(consultar);
		dto.setModificar(modificar);
		dto.setHijos(hijos);
		return dto;

	}

	public RonuNuevoDTO modeloAdto(OpcMenuModel modelo) {
		RonuNuevoDTO dto = new RonuNuevoDTO();
		dto.setIdMenu(modelo.getMenuMenu());
		dto.setMenuMenu(modelo.getMenuTitulo());
		dto.setConsultar(0);
		dto.setCrear(0);
		dto.setModificar(0);
		return dto;

	}

	public RonuNuevoDTO modeloAdtomenu(OpcMenuModel modelo, List<RonuNuevoDTO> hijos) {
		RonuNuevoDTO dto = new RonuNuevoDTO();
		dto.setIdMenu(modelo.getMenuMenu());
		dto.setMenuMenu(modelo.getMenuTitulo());
		dto.setConsultar(0);
		dto.setCrear(0);
		dto.setModificar(0);
		dto.setHijos(hijos);
		return dto;

	}
}
