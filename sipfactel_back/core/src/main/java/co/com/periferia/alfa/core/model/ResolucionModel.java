package co.com.periferia.alfa.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tbl_resolucion")
public class ResolucionModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_resolucion_seq")
	@SequenceGenerator(name = "tbl_resolucion_seq", sequenceName = "tbl_resolucion_seq", allocationSize = 1)
	@Column(name = "res_res")
	private Integer res;
	
	@Column(name = "res_suc_suc")
	private Integer sucursal;

	@Column(name = "res_comp_comp")
	private Integer compania;

	@Column(name = "res_fnresol", length = 100)
	private String fnresol;

	@Column(name = "res_fprefijo", length = 100)
	private String fprefijo;

	@Temporal(TemporalType.DATE)
	@Column(name = "res_ffini")
	private Date ffini;

	@Temporal(TemporalType.DATE)
	@Column(name = "res_fffin")
	private Date fffin;

	@Column(name = "res_fnumini")
	private Integer fnumini;

	@Column(name = "res_fnumfin")
	private Integer fnumfin;

	@Column(name = "res_fcreacion")
	private Date fcreacion;

	@Column(name = "res_ucreacion", length = 100)
	private String ucreacion;
	
	@Column(name = "res_factura_cont")
	private Integer facturaCont;

	@Column(name = "res_factualiza")
	private Date factualiza;

	@Column(name = "res_uactualiza", length = 100)
	private String uactualiza;
	
	@Column(name = "res_estado")
	private String estado;
	
	@Column(name = "res_trtipoid")
	private Integer tidFactura;
	
	@Column(name = "auditoria")
	private String auditoria;
	
	@Column(name = "RES_PRODUCTO")
	private String producto;
	
}
