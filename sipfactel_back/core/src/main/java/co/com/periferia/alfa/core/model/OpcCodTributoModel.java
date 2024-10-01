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
public class OpcCodTributoModel {

	@Id
	@Column(name = "conf_conf")
	private Integer codTributo;
	
	@Column(name = "conf_valor")
	private String descripcion;

}
