package co.com.periferia.alfa.core.model;

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
@Table(name = "tbl_sucursales")
public class SucursalesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_sucursales_seq")
	@SequenceGenerator(name = "tbl_sucursales_seq", sequenceName = "tbl_sucursales_seq", allocationSize = 1)
	@Column(name = "suc_suc")
	private Integer sucu;

	@Column(name = "suc_codascele", length = 100)
	private String codascele;

	@Column(name = "suc_codpoint", length = 100)
	private String codpoint;

	@Column(name = "suc_nomsuc", length = 100)
	private String nomsuc;

	@Column(name = "suc_cod_ciudad")
	private Integer codciudad;

	@Column(name = "suc_cod_departamento")
	private Integer coddepart;

	@Column(name = "suc_direccion", length = 100)
	private String direccion;

	@Column(name = "suc_fcreacion")
	private Date fcreacion;

	@Column(name = "suc_ucreacion", length = 100)
	private String ucreacion;

	@Column(name = "suc_factualiza")
	private Date factualiza;

	@Column(name = "suc_uactualiza", length = 100)
	private String uactualiza;

	@Column(name = "suc_estado", length = 100)
	private String estado;
	
	@Column(name = "suc_post_post", length = 100)
	private Integer codpostal;

}
