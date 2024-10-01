package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ConsAdquirientesModel {

	@Id
	@Column(name = "per_per")
	private Integer id;
	
	@Column(name = "conf_valor")
	private String tipoDocumento;
	
	@Column(name = "per_nombrerazonsocial")
	private String nombrerazonsocial;	
	
	@Column(name = "per_numidentificacion", length = 100)
	private String numeroIdentificacion;
	
	@Column(name = "tipo_persona", length = 150)
	private String tipoPersona;

}
