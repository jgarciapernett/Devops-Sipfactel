package co.com.periferia.alfa.core.model;

import java.math.BigInteger;
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
@Table(name = "tbl_personas")
public class AdquirientesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_personas_seq")
	@SequenceGenerator(name = "tbl_personas_seq", sequenceName = "tbl_personas_seq", allocationSize = 1)
	@Column(name = "per_per")
	private Integer per;

	@Column(name = "per_cod_tipopersona")
	private BigInteger codtipopersona;

	@Column(name = "per_nombrerazonsocial", length = 350)
	private String nombrerazonsocial;

	@Column(name = "per_nombrecomercial", length = 350)
	private String nombrecomercial;

	@Column(name = "per_cod_tipoidentificacion")
	private Integer codtipoidentificacion;

	@Column(name = "per_numidentificacion", length = 100)
	private String numidentificacion;

	@Column(name = "per_dv", length = 5)
	private String dv;

	@Column(name = "per_telefono")
	private String telefono;

	@Column(name = "per_direccionfisica", length = 200)
	private String direccion;

	@Column(name = "per_correo", length = 200)
	private String correo;

	@Column(name = "per_direccionfiscal", length = 200)
	private String direccionfiscal;

	@Column(name = "per_cod_pais")
	private Integer codpais;

	@Column(name = "per_cod_departamento")
	private Integer coddepartamento;

	@Column(name = "per_cod_ciudad")
	private Integer codciudad;

	@Column(name = "per_contacto", length = 200)
	private String contacto;

	@Column(name = "per_areapertenece", length = 200)
	private String areapertenece;

	@Column(name = "per_facturadorelectronico")
	private Integer facturadorelectronico;

	@Column(name = "per_cod_enviofe")
	private Integer codenviofe;

	@Column(name = "per_recibir_xml")
	private Integer recibirxml;

	@Column(name = "per_deshabilitado")
	private Integer deshabilitado;

	@Column(name = "per_cod_tipotributo")
	private Integer codtipotributo;

	@Temporal(TemporalType.DATE)
	@Column(name = "per_fechainsercion")
	private Date fechainsercion;

	@Temporal(TemporalType.DATE)
	@Column(name = "per_fechaactualizacion")
	private Date fechaactualizacion;

	@Column(name = "per_usuario", length = 45)
	private String usuario;

	@Column(name = "per_cod_regimen")
	private Integer codRegimen;

	@Column(name = "per_post_post")
	private Integer codPostal;

	@Column(name = "per_calidadcod")
	private Integer calidadcod;

	@Column(name = "per_calidadmensaje")
	private String calidadmensaje;
	
	@Column(name = "per_cod_fuente")
	private String codFuente;

}
