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
@Table(name = "tbl_ejecucion_etl")
public class EjecucionEtlModel {

	@Id
	@Column(name = "EJEC_EJEC")
	private Integer dni;
	@Column(name = "EJEC_FECHA")
	private String fecha;
	@Column(name = "EJEC_PROCESADO")
	private Integer procesado;
	@Column(name = "EJEC_EJECUCION")
	private String fechaEjecucion;
	
}
