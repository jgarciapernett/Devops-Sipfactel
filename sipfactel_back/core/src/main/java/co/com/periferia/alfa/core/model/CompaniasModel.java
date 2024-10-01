package co.com.periferia.alfa.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_companias")
public class CompaniasModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_companias_seq")
	@SequenceGenerator(name = "tbl_companias_seq", sequenceName = "tbl_companias_seq", allocationSize = 1)
	@NotNull
	@Column(name = "comp_comp")
	private Integer comp;
	
	@Column(name = "comp_nombre", length = 100)
	private String nombre;
	
	@Column(name = "comp_codascele", length = 100)
	private String codascele;	
	
	@Column(name = "comp_cod_tipoidentificacion")
	private Integer tipodocumento;
	
	@Column(name = "comp_cod_pais")
	private Integer codigopais;

	@Column(name = "comp_razonsocial", length = 100)
	private String razonsocial;
	
	@Column(name = "comp_codpoint", length = 100)
	private String codpoint;
	
	@Column(name = "comp_documento", length = 100)
	private String documento;
	
	@Column(name = "comp_nombrepais", length = 100)
	private String nombrepais;
	
	@Column(name = "comp_cod_tipopersona")
	private Integer tipopersona;	
	
	@Column(name = "comp_facelectronica")
	private Integer facelectronica;
	
	@Column(name = "comp_notacredito", length = 100)
	private String notacredito;

	@Column(name = "comp_notadebito", length = 100)
	private String notadebito;
	
	@Column(name = "comp_codempresa", length = 100)
	private String codempresa;
	
	@Column(name = "comp_cod_estandar")
	private Integer codestandar;
	
	@Column(name = "comp_cod_tipotributo")
	private Integer codatributo;
	
	@Column(name = "comp_nomatributo", length = 100)
	private String nomatributo;

	@Column(name = "comp_porc_porc")
	private Integer porcatributo;	
	
	@Column(name = "comp_cantidad", length = 100)
	private String cantidad;
	
	@Column(name = "comp_cod_formapago")
	private Integer formapago;
	
	@Column(name = "comp_cod_mediopago")
	private Integer metodopago;

	@Column(name = "comp_cod_unidadmedida")
	private Integer undmedida;
	
	@Column(name = "comp_estado", length = 100)
	private String estado;
	
	@Column(name = "comp_fcreacion")
	private Date fcreacion;
	
	@Column(name = "comp_ucreacion", length = 100)
	private String ucreacion;
	
	@Column(name = "comp_notadebh", length = 100)
	private String notadebh;
	
	@Column(name = "comp_notacreh", length = 100)
	private String notacreh;
		
	@Column(name = "comp_cod_regimen")
	private Integer regimen;

}
