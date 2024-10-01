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
@Table(name = "tbl_json_envio")
public class JsonEnvioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_json_envio_seq")
	@SequenceGenerator(name = "tbl_json_envio_seq", sequenceName = "tbl_json_envio_seq", allocationSize = 1)
	@Column(name = "env_env")
	private Integer id;
	
	@Column(name = "env_json")
	private String json;
	
	@Column(name = "env_error_id")
	private Integer errorId;
	
	@Column(name = "env_error_msg")
	private String errorMsg;
	
	@Column(name = "env_mensaje")
	private String mensaje;
	
	@Column(name = "env_tr_id")
	private Integer trId;
	
	@Column(name = "env_cufe")
	private String cufe;
	
	@Column(name = "env_qr")
	private String qr;
	
	@Column(name = "env_ucreacion")
	private String ucreacion;
	
	@Column(name = "env_fcreacion")
	private Date fcreacion;
	
	@Column(name = "env_numdocumento")
	private String numdocumento;
	
	@Column(name = "env_numpoliza")
	private String numpoliza;
	
	@Column(name = "env_cod_tipodocumento")
	private Integer tipodocumento;
	
	@Column(name = "env_intentos")
	private Integer intentos;
	
	@Column(name = "env_categoria")
	private Integer categoria;
	
	@Column(name = "env_observacion")
	private String observacion;
	
	@Column(name = "env_id_documento")
	private Integer idDocumento;

}
