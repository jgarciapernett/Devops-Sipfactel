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

@Table(name = "TBL_RESOLUCION_NOTAS")
public class ResolucionNotasModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_RESOLUCION_NOTAS_SEQ")
	@SequenceGenerator(name = "TBL_RESOLUCION_NOTAS_SEQ", sequenceName = "TBL_RESOLUCION_NOTAS_SEQ", allocationSize = 1)
	@Column(name = "RESNOT_DNI")
	private Integer dni;

	@Column(name = "RESNOT_SUCURSAL")
	private Integer sucursal;

	@Column(name = "RESNOT_COMPANIA")
	private Integer compania;

	@Column(name = "RESNOT_PREFIJO")
	private String prefijo;

	@Column(name = "RESNOT_NUMERO_INICIAL")
	private Integer numeroInicial;

	@Column(name = "RESNOT_NUMERO_FINAL")
	private Integer numeroFinal;

	@Column(name = "RESNOT_FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name = "RESNOT_USUARIO_CREACION")
	private String usuarioCreacion;
	
	@Column(name = "RESNOT_CONTADOR")
	private Integer contador;

	@Column(name = "RESNOT_ESTADO")
	private String estado;

	@Column(name = "RESNOT_TRTIPOID")
	private Integer trTipoId;

	@Column(name = "RESNOT_TIPO_NOTA")
	private String tipoNota;
	
	@Column(name = "RESNOT_PRODUCTO")
	private String producto;
	
	@Column(name = "RESNOT_AUDITORIA")
	private String auditoria;

}
