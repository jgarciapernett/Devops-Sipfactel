package co.com.periferia.alfa.core.model.admin;

import java.util.Date;

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
@Table(name = "tbl_roles")
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_roles_seq")
    @SequenceGenerator(name="tbl_roles_seq", sequenceName="tbl_roles_seq", allocationSize=1)
	@Column(name = "roles_roles")
	private Integer roleRole;
	
	@NotNull
	@Column(name = "roles_nombre", length = 45)
	private String roleNombre;
	
	@Column(name = "roles_descripcion", length = 255)
	private String roleDescripcion;
	
	@NotNull
	@Column(name = "roles_estado", length = 3)
	private String roleEsta;
	
	@Column(name = "roles_factualizacion")
	private Date roleFactualizacion;
	
	@Column(name = "roles_uactualizacion", length = 45)
	private String roleUactualizacion;
	
	@NotNull
	@Column(name = "roles_fcreacion")
	private Date roleFcreacion;
	
	@NotNull
	@Column(name = "roles_ucreacion", length = 45)
	private String roleUcreacion;
	
	@Column(name = "roles_categoria")
	private Integer categoria;

}
