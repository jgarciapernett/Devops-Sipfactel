package co.com.periferia.alfa.core.model.admin;

import java.util.Date;

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
@Table(name = "tbl_auditoria")
public class Auditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_auditoria_audi_audi_seq")
	@SequenceGenerator(name = "tbl_auditoria_audi_audi_seq", sequenceName = "tbl_auditoria_audi_audi_seq", allocationSize = 1)
	@Column(name = "audi_audi")
	private Integer audiAudi;

	@Column(name = "audi_descripcion", length = 500)
	private String audiDescripcion;

	@Column(name = "audi_fecha")
	private Date audiFecha;

	@Column(name = "audi_usuario")
	private String audiUsuario;

	@Column(name = "audi_funcionalidad", length = 45)
	private String audiFuncionalidad;

	@Column(name = "audi_ip", length = 45)
	private String audiIp;

	@Column(name = "audi_nombre", length = 45)
	private String audiNombre;

	@Column(name = "audi_rol", length = 45)
	private String audiRol;

	@Column(name = "audi_detalle", length = 45)
	private String audiDetalle;
}
