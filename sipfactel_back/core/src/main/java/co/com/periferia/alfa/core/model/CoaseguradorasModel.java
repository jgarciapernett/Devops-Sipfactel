package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CoaseguradorasModel {

	@Id
	@Column(name = "dni")
	private Integer dni;
	
	@Column(name = "coa_coa")
	private Integer coa;
	
	@Column(name = "coaseguradora")
	private String coaseguradora;

	@Column(name = "tdoc")
	private String tdoc;

	@Column(name = "ndoc")
	private String ndoc;

	@Column(name = "porc")
	private String porcPart;

	@Column(name = "numpol")
	private String numpol;

	@Column(name = "ramo")
	private String ramo;

	
}
