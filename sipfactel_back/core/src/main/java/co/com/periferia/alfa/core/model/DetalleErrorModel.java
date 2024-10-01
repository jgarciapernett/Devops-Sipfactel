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
@Table(name = "tbl_detalleerror")
public class DetalleErrorModel {

	@Id
	@Column(name = "derr_derr")
	private Integer id;
	
	@Column(name = "derr_idtabla")
	private Integer idtabla;
	
	@Column(name = "derr_tabla")
	private String tabla;
	
	@Column(name = "derr_categoria")
	private String categoria;
	
	@Column(name = "derr_codigo_error")
	private Integer codigo;
	
	@Column(name = "derr_mensaje_error")
	private String mensaje;
	
	@Column(name = "derr_observacion")
	private String observacion;

	@Column(name = "derr_fchinsercion")
	private Date fchinsercion;
	
	@Column(name = "derr_usuinsercion")
	private String usuinsercion;

	@Column(name = "derr_fchactualizacion")
	private Date fchactualizacion;
	
	@Column(name = "derr_usaactualizacion")
	private String usaactualizacion;

}
