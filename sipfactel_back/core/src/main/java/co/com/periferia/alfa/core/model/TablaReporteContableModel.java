package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TablaReporteContableModel {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "Compania")
	private String compania;
	
	@Column(name = "CantidadRegistros")
	private Integer cantidadRegistros;

	@Column(name = "valorPrima")
	private String valorPrima;
	
	@Column(name = "valorIva")
	private String valorIva;
	
	@Column(name = "Total")
	private String total;

}
