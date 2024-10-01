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
@Table(name = "tbl_configuracion")
public class OpcHomologacionModel {

	@Id
	@Column(name = "conf_conf")
	private Integer id;

	@Column(name = "conf_sistema", length = 300)
	private String descripcion;

}
