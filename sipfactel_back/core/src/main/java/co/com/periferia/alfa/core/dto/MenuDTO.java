package co.com.periferia.alfa.core.dto;

import java.sql.Timestamp;
import java.util.List;

import co.com.periferia.alfa.core.model.admin.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO implements IBaseDTO<MenuDTO, Menu> {

	private Integer menuMenu;
	private String menuAlias;
	private String menuTitulo;
	private Integer menuPadre;
	private String menuUrl;
	private String menuIcono;
	private String menuEstado;
	private Timestamp factualizacion;
	private Timestamp fcreacion;
	private String uactualizacion;
	private String ucreacion;
	private Integer crear;
	private Integer modificar;
	private Integer consultar;
	private List<MenuDTO> menuHijos;

	@Override
	public Menu dtoAModelo(MenuDTO dto) {
		Menu modelo = new Menu();
		modelo.setMenuMenu(dto.getMenuMenu());
		modelo.setMenuAlias(dto.getMenuAlias());
		modelo.setMenuTitulo(dto.getMenuTitulo());
		modelo.setMenuPadre(dto.getMenuPadre());
		modelo.setMenuUrl(dto.getMenuUrl());
		modelo.setMenuIcono(dto.getMenuIcono());
		modelo.setMenuEstado(dto.getMenuEstado());
		modelo.setMenuFactualizacion(dto.getFactualizacion());
		modelo.setMenuFcreacion(dto.getFcreacion());
		modelo.setMenuUactualizacion(dto.getUactualizacion());
		modelo.setMenuUcreacion(dto.getUcreacion());
		return modelo;
	}

	@Override
	public MenuDTO modeloAdto(Menu modelo) {
		MenuDTO dto = new MenuDTO();
		dto.setMenuMenu(modelo.getMenuMenu());
		dto.setMenuAlias(modelo.getMenuAlias());
		dto.setMenuTitulo(modelo.getMenuTitulo());
		dto.setMenuPadre(modelo.getMenuPadre());
		dto.setMenuUrl(modelo.getMenuUrl());
		dto.setMenuIcono(modelo.getMenuIcono());
		dto.setMenuEstado(modelo.getMenuEstado());
		dto.setFactualizacion(modelo.getMenuFactualizacion());
		dto.setFcreacion(modelo.getMenuFcreacion());
		dto.setUactualizacion(modelo.getMenuUactualizacion());
		dto.setUcreacion(modelo.getMenuUcreacion());
		return dto;
	}

	public MenuDTO modeloAdtomenu(Menu modelo, List<MenuDTO> hijos, Integer crear, Integer consultar,
			Integer modificar) {
		MenuDTO dto = new MenuDTO();
		dto.setMenuMenu(modelo.getMenuMenu());
		dto.setMenuAlias(modelo.getMenuAlias());
		dto.setMenuTitulo(modelo.getMenuTitulo());
		dto.setMenuPadre(modelo.getMenuPadre());
		dto.setMenuUrl(modelo.getMenuUrl());
		dto.setMenuIcono(modelo.getMenuIcono());
		dto.setMenuEstado(modelo.getMenuEstado());
		dto.setFactualizacion(modelo.getMenuFactualizacion());
		dto.setFcreacion(modelo.getMenuFcreacion());
		dto.setUactualizacion(modelo.getMenuUactualizacion());
		dto.setUcreacion(modelo.getMenuUcreacion());
		dto.setCrear(crear);
		dto.setConsultar(consultar);
		dto.setModificar(modificar);
		dto.setMenuHijos(hijos);
		return dto;
	}

	public MenuDTO modeloAdtomenu(Menu modelo, List<MenuDTO> hijos) {
		MenuDTO dto = new MenuDTO();
		dto.setMenuMenu(modelo.getMenuMenu());
		dto.setMenuAlias(modelo.getMenuAlias());
		dto.setMenuTitulo(modelo.getMenuTitulo());
		dto.setMenuPadre(modelo.getMenuPadre());
		dto.setMenuUrl(modelo.getMenuUrl());
		dto.setMenuIcono(modelo.getMenuIcono());
		dto.setMenuEstado(modelo.getMenuEstado());
		dto.setFactualizacion(modelo.getMenuFactualizacion());
		dto.setFcreacion(modelo.getMenuFcreacion());
		dto.setUactualizacion(modelo.getMenuUactualizacion());
		dto.setUcreacion(modelo.getMenuUcreacion());
		dto.setMenuHijos(hijos);
		return dto;
	}

	public MenuDTO modeloAdto(Menu modelo, Integer crear, Integer consultar, Integer modificar) {
		MenuDTO dto = new MenuDTO();
		dto.setMenuMenu(modelo.getMenuMenu());
		dto.setMenuAlias(modelo.getMenuAlias());
		dto.setMenuTitulo(modelo.getMenuTitulo());
		dto.setMenuPadre(modelo.getMenuPadre());
		dto.setMenuUrl(modelo.getMenuUrl());
		dto.setMenuIcono(modelo.getMenuIcono());
		dto.setMenuEstado(modelo.getMenuEstado());
		dto.setFactualizacion(modelo.getMenuFactualizacion());
		dto.setFcreacion(modelo.getMenuFcreacion());
		dto.setUactualizacion(modelo.getMenuUactualizacion());
		dto.setUcreacion(modelo.getMenuUcreacion());
		dto.setCrear(crear);
		dto.setConsultar(consultar);
		dto.setModificar(modificar);
		return dto;
	}

}
