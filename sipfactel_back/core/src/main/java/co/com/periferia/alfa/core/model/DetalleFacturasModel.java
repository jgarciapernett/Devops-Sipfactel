package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DetalleFacturasModel {

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "tipodoc")
	private Integer tipodoc;
	
	@Column(name = "idDet")
	private Integer idDet;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "valor")
	private Integer valor;
	
	@Column(name = "valoriva")
	private Integer valoriva;
	
	@Column(name = "tasaiva")
	private Integer tasaiva;
	
	@Column(name = "subtotalvalor")
	private Integer subtotalvalor;
	
	@Column(name = "subtotaliva")
	private Integer subtotaliva;
	
	@Column(name = "total")
	private Integer total;
	
}
