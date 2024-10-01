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
@Table(name = "tbl_porcentajes")
public class PorcentajesModel {

	@Id
	@Column(name = "porc_porc")
	private Integer id;
	
	@Column(name = "porc_impuesto", length = 100)
	private String idStr;

	@Column(name = "porc_concepto")
	private String nombre;

	@Column(name = "porc_tarifa")
	private Integer llave;
	
	@Column(name = "porc_estado")
	private String estado;

}
