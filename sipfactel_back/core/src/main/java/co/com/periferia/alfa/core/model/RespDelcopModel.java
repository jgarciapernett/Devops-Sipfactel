package co.com.periferia.alfa.core.model;

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
@Table(name = "tbl_respuesta_delcop")
public class RespDelcopModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_respuesta_delcop_seq")
	@SequenceGenerator(name = "tbl_respuesta_delcop_seq", sequenceName = "tbl_respuesta_delcop_seq", allocationSize = 1)
	@Column(name = "resp_resp")
	private Integer id;
	
	@Column(name = "resp_tr_id")
	private Integer trid;
	
	@Column(name = "resp_tipo_tran_id")
	private Integer tipoTranId;
	
	@Column(name = "resp_tipo_doc_id")
	private Integer tipoDocId;
	
	@Column(name = "resp_receptor")
	private String receptor;
	
	@Column(name = "resp_doc")
	private String doc;
	
	@Column(name = "resp_fecha")
	private String fecha;
	
	@Column(name = "resp_estado_id")
	private Integer estadoId;
	
	@Column(name = "resp_pausa_id")
	private String pausaId;
	
	@Column(name = "resp_borrado")
	private String borrado;
	
	@Column(name = "resp_fanexo")
	private String fanexo;
	
	@Column(name = "resp_from")
	private Integer from;
	
	@Column(name = "resp_to")
	private Integer to;
	
	@Column(name = "resp_total")
	private Integer total;
	
	@Column(name = "resp_currentpage")
	private Integer currentpage;
	
	@Column(name = "resp_lastpage")
	private Integer lastpage;
	
	@Column(name = "resp_error_id")
	private Integer errorId;
	
	@Column(name = "resp_error_msg")
	private String errorMsg;
	
	@Column(name = "resp_categoria")
	private Integer categoria;
	
	@Column(name = "resp_observacion")
	private String observacion;
	
	@Column(name = "res_estados")
	private String estados;
	
}
