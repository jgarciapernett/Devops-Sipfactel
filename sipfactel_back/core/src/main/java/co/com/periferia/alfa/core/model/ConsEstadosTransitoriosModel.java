package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ConsEstadosTransitoriosModel {

	@Id
	@Column(name = "fact_numdoc")
	private String numdoc;
	
	@Column(name = "fact_fact_estado")
	private int estado;
	
	@Column(name = "FACT_COD_TIPODOCUMENTO")
	private int tipoDoc;
	
}
