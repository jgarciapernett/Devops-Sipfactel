package co.com.periferia.alfa.core.model;

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
@Table(name = "tbl_configuracion")
public class OpcConsultaFeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_configuracion_seq")
	@SequenceGenerator(name = "tbl_configuracion_seq", sequenceName = "tbl_configuracion_seq", allocationSize = 1)
	
	@Column(name = "conf_llave")
	private Integer id;
	
	@Column(name = "conf_valor")
	private String nombre;

}
