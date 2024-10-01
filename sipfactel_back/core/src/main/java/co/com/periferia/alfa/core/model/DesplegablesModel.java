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
public class DesplegablesModel {

	@Id
	@Column(name = "conf_conf")
	private Integer id;
	
	@Column(name = "conf_valor", length = 100)
	private String valor;

}
