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
@Table(name = "tbl_datos_correo")
public class DatosCorreoModel {

	@Id
	@Column(name = "dato_dato")
	private Integer id;
	
	@Column(name = "dato_host")
	private String host;
	
	@Column(name = "dato_puerto")
	private String puerto;
	
	@Column(name = "dato_emisor")
	private String emisor;
	
	@Column(name = "dato_pass")
	private String pass;
	
	@Column(name = "dato_receptor")
	private String receptor;

}
