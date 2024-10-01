package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_mensajes")
public class MensajesErrorModel {

	@Id
	@Column(name = "msj_msj")
	private Long id;
	
	@Column(name = "msj_codigo")
	private Integer codigo;
	
	@Column(name = "msj_mensaje")
	private String mensaje;

}
