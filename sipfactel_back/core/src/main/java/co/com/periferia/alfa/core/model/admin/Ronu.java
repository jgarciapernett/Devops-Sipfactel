package co.com.periferia.alfa.core.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_menus_roles")
public class Ronu {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_menus_roles_seq")
	@SequenceGenerator(name = "tbl_menus_roles_seq", sequenceName = "tbl_menus_roles_seq", allocationSize = 1)
	@Column(name = "menurol_menurol")
	private Integer menurol;

	@Column(name = "menurol_rolesroles")
	private Integer rolesRoles;

	@Column(name = "menurol_menumenu")
	private Integer menuMenu;

	@Column(name = "menurol_crear")
	private Integer crear;

	@Column(name = "menurol_consultar")
	private Integer consultar;

	@Column(name = "menurol_modificar")
	private Integer modificar;

	@Column(name = "menurol_padre")
	private Integer padre;

}
