package co.com.periferia.alfa.core.model.admin;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_menus")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_menu_menu_menu_seq")
	@SequenceGenerator(name = "tbl_menu_menu_menu_seq", sequenceName = "tbl_menu_menu_menu_seq", allocationSize = 1)
	@Column(name = "menu_menu")
	private Integer menuMenu;

	@NotNull
	@Column(name = "menu_alias", length = 45)
	private String menuAlias;

	@NotNull
	@Column(name = "menu_titulo", length = 100)
	private String menuTitulo;
	
	@Column(name = "menu_icono", length = 30)
	private String menuIcono;
	
	@Column(name = "menu_url", length = 100)
	private String menuUrl;

	@Column(name = "menu_padre")
	private Integer menuPadre;	
	
	@NotNull
	@Column(name = "menu_estado", length = 3)
	private String menuEstado;
	
	@Column(name = "menu_factualizacion")
	private Timestamp menuFactualizacion;
	
	@Column(name = "menu_uactualizacion", length = 45)
	private String menuUactualizacion;
	
	@NotNull
	@Column(name = "menu_fcreacion")
	private Timestamp menuFcreacion;
	
	@NotNull
	@Column(name = "menu_ucreacion", length = 45)
	private String menuUcreacion;
	
	@Override
	public String toString() {
		return "Menu [menuMenu=" + menuMenu + ", menuAlias=" + menuAlias + ", menuTitulo=" + menuTitulo + ", menuPadre="
				+ menuPadre + ", menuUrl=" + menuUrl + ", menuIcono=" + menuIcono + ", menuEstado=" + menuEstado
				+ ", menuUcreacion=" + menuUcreacion + ", menuFcreacion=" + menuFcreacion + ", menuUactualizacion="
				+ menuUactualizacion + ", menuFactualizacion=" + menuFactualizacion + "]";
	}
	
}
