package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_menus")
public class OpcMenuModel {
	
	@Id
	@Column(name = "menu_menu")
	private Integer menuMenu;

	@NotNull
	@Column(name = "menu_titulo", length = 100)
	private String menuTitulo;

}
