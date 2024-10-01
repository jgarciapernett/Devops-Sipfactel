package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DetalleAdquirientesModel {
	
	@Id
	@Column(name = "codtipoidentificacion")
	private String codtipoidentificacion;
	
	@Column(name = "numidentificacion", length = 100)
	private String numidentificacion;
	
	@Column(name = "nombre", length = 350)
	private String nombre;
	
	@Column(name = "direccion", length = 350)
	private String direccion;

	@Column(name = "nombreciudad")
	private String nombreciudad;

	@Column(name = "nombredepartamento")
	private String nombredepartamento;

}
