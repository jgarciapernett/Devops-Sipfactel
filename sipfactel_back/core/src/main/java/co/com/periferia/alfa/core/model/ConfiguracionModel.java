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
@Table(name = "tbl_configuracion")
public class ConfiguracionModel {

	@Id
	@ GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_configuracion_seq")
	@ SequenceGenerator(name = "tbl_configuracion_seq", sequenceName = "tbl_configuracion_seq", allocationSize = 1)
	@Column(name = "conf_conf")
	private Integer conf;
	
	@NotNull
	@Column(name = "conf_llave", length = 300)
	private String llave;
	
	@NotNull
	@Column(name = "conf_valor", length = 500)
	private String valor;
	
	@NotNull
	@Column(name = "conf_sistema", length = 100)
	private String sistema;
	
	@Column(name = "conf_padre")
	private Integer padre;
	
	@Column(name = "conf_descripcion", length = 100)
	private String descripcion;
	
	@NotNull
	@Column(name = "conf_ucrea", length = 50)
	private String usuarioCreacion;
	
	@NotNull
	@Column(name = "conf_fcrea")
	private Date fechaCreacion;
	
	@Column(name = "conf_uactualiza", length = 50)
	private String usuarioAct;
	
	@Column(name = "conf_factualiza")
	private Date fechaAct;
	
	@Column(name = "conf_estado")
	private String estado;
	
}
