package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_roles")
public class OpcRolesModel {
	
	@Id
	@Column(name = "roles_roles")
	private Integer roleRole;

	@Column(name = "roles_nombre", length = 45)
	private String roleNombre;

}
