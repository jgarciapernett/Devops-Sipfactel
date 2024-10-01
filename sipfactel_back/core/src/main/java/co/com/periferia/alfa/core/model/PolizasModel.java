package co.com.periferia.alfa.core.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
@Table(name = "tbl_polizas")
public class PolizasModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_poliza_seq")
	@SequenceGenerator(name = "tbl_poliza_seq", sequenceName = "tbl_poliza_seq", allocationSize = 1)
	@Column(name = "pol_pol")
	private Integer id;
	
	@Column(name = "pol_per_per")
	private Integer per;
	
	@Column(name = "pol_fechaemision")
	private Date fechaemision;
	
	@Column(name = "pol_horaexpedicion")
	private Timestamp horaexpedicion;
	
	@Column(name = "pol_prima")
	private BigDecimal prima;
	
	@Column(name = "pol_iva_primatotal")
	private BigDecimal primatotal;
	
	@Column(name = "pol_endoso", length = 150)
	private String endoso;

	@Column(name = "pol_cod_ramo")
	private Integer numeroramo;
	
	@Column(name = "pol_numeropoliza", length = 10)
	private String numeropoliza;
	
	@Column(name = "pol_tasa")
	private Integer tasa;
	
	@Column(name = "pol_cod_fuente")
	private Integer fuente;
	
	@Column(name = "pol_fechainserccion")
	private Date fechainsercion;
	
	@Column(name = "pol_fechaactualizacion")
	private Date fechaactualizacion;
	
	@Column(name = "pol_usuario", length = 45)
	private String usuario;
	
	@Column(name = "pol_suc_suc")
	private Integer sucu;
	
	@Column(name = "pol_comp_comp")
	private Integer comp;
	
	@Column(name = "pol_log_log")
	private Integer log;
	
	@Column(name = "pol_movimiento")
	private String movimiento;
	
	@Column(name = "pol_modulo", length = 5)
	private String modulo;
	
	@Column(name = "pol_fechavigencia")
	private Date fechavigencia;

	@Column(name = "pol_tipopoliza", length = 6)
	private String tipopoliza;
	
	@Column(name = "pol_calidadcod")
	private Integer calidadcod;
	
	@Column(name = "pol_calidadmensaje", length = 200)
	private String calidadmensaje;
	
	@Column(name = "pol_fact_fact")
	private Integer fact;
	
	@Column(name = "pol_not_not")
	private Integer not;
	
	@Column(name = "pol_ivaemrep")
	private BigDecimal ivaEmRep ;

}
