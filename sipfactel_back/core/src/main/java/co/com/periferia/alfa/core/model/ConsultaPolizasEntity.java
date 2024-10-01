package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ConsultaPolizasEntity {

	@Id
	private Integer dni;
	
	@Column(name = "adquiriente")
	private Integer adquiriente;
	
	@Column(name = "numero_documento")
	private String numeroDocumento;
	
	@Column(name = "numero_poliza")
	private String numeroPoliza;
	
	@Column(name = "tipo_documento")
	private String tipoDocumento;
	
	@Column(name = "fecha_emision")
	private String fechaEmision;
	
	@Column(name = "fecha_envio")
	private String fechaEnvio;
	
	private String estado;
	private String causa;
	
	
}
