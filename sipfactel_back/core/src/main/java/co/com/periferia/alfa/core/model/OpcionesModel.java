package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@Entity
@Table(name = "tbl_configuracion")
public class OpcionesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_configuracion_seq")
	@SequenceGenerator(name = "tbl_configuracion_seq", sequenceName = "tbl_configuracion_seq", allocationSize = 1)
	@Column(name = "conf_conf")
	private Integer id;
		
	@Column(name = "conf_sistema", length = 100)
	private String sistema;
	
}
