package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tbl_errores_delcop")
@Data
public class ErroresDelcopModel {

	@Id
	@Column(name = "dni")
	private Integer id;
	
	@Column(name = "id_error")
	private String codigoError;
	
	@Column(name = "mensaje")
	private String mensaje;
	
	@Column(name = "nombre_error")
	private String nombreError;
	
	@Column(name = "estado")
	private String estado;
	
}
