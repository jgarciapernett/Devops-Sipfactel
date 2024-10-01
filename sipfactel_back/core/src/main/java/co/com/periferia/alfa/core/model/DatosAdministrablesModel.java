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
@Table(name = "tbl_valores_defecto")
public class DatosAdministrablesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_valores_defecto_seq")
	@SequenceGenerator(name = "tbl_valores_defecto_seq", sequenceName = "tbl_valores_defecto_seq", allocationSize = 1)
	@Column(name = "vad_vad")
	private Integer vad;
	
	@Column(name = "vad_nombrerazonsocial")
	private String nombreRazonSocial;
	
	@Column(name = "vad_nombrecomercial")
	private String nombreComercial;
	
	@Column(name = "vad_telefono")
	private Integer telefono;
	
	@Column(name = "vad_direccionfisica")
	private String direccionFisica;
	
	@Column(name = "vad_direccionfiscal")
	private String direccionFiscal;
	
	@Column(name = "vad_correo")
	private String correo;
	
	@Column(name = "vad_cod_pais")
	private Integer codPais;
	
	@Column(name = "vad_cod_departamento")
	private Integer codDepartamento;
	
	@Column(name = "vad_cod_ciudad")
	private Integer codCiudad;
	
	@Column(name = "vad_contacto")
	private String contacto;
	
	@Column(name = "vad_areapertenece")
	private String areaPertenece;
	
	@Column(name = "vad_facturadorelectronico")
	private Integer facturadorElectronico;
	
	@Column(name = "vad_cod_tipotributo")
	private Integer codTipoTributo;
	
	@Column(name = "vad_cod_regimen")
	private Integer codRegimen;
	
	@Column(name = "vad_post_post")
	private Integer post;
	
	@Column(name = "vad_fechacreacion")
	private Date fcreacion;
	
	@Column(name = "vad_usuariocreacion")
	private String ucreacion;
	
	@Column(name = "vad_fechaactualizacion")
	private Date factualizacion;
	
	@Column(name = "vad_usuarioactualizacion")
	private String uactualizacion;
	
	@Column(name = "vad_suc_estado")
	private String estado;
	
}
