package co.com.periferia.alfa.core.model.admin;

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
@Table(name = "tbl_usuarios_rol")
public class UsaRol {

	@Id
	@NotNull
	@Column(name = "usuarol_usuausua")
	private Integer usuaUsua;
   	
	@NotNull
	@Column(name = "usuarol_rolesroles")
	private Integer rolesRoles;

}
