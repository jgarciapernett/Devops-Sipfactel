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
@Table(name = "tbl_homologacion")
public class HomologacionModel {

	@Id
	@ GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_homologacion_seq")
	@ SequenceGenerator(name = "tbl_homologacion_seq", sequenceName = "tbl_homologacion_seq", allocationSize = 1)
	@Column(name = "homo_homo")
	private Integer homo;
	
	@Column(name = "homo_conf_conf")
	private Integer conf;

	@Column(name = "homo_llave", length = 300)
	private String homologado;
	
	@Column(name = "homo_fuente")
	private Integer fuente;
	
	@Column(name = "homo_origen", length = 300)
	private String origen;	
	
	@Column(name = "homo_descripcion", length = 300)
	private String descripcion;
	
	@Column(name = "homo_ucrea", length = 50)
	private String usuarioCreacion;

	@Column(name = "homo_fcrea")
	private Date fechaCreacion;	
	
	@Column(name = "homo_uactualiza", length = 50)
	private String usuarioAct;
	
	@Column(name = "homo_factualiza")
	private Date fechaAct;
	
	@Column(name = "homo_estado")
	private String estado;
	
}
