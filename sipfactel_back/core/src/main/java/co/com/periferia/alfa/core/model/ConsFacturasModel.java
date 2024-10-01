package co.com.periferia.alfa.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_facturas")
public class ConsFacturasModel {

	@Id
	@Column(name = "fact_fact")
	private Integer id;

	@Column(name = "fact_valorbrutomasimpuesto")
	private Integer numeropoliza;

	@Column(name = "fact_cod_tipooperacion")
	private Integer tipofactura;

	@Column(name = "fact_fechaemision")
	private Date fechaemision;

	@Column(name = "fact_cod_estado")
	private Integer codestado;

}
