package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ReceptoresModel {

	
	@Id
	@Column(name = "Nombre", length = 350)
	private String nombre;
	
	@Column(name = "Email", length = 200)
	private String email;
	
	@Column(name = "Enviar_Email", length = 300)
	private String enviarEmail;
	
	@Column(name = "Incluir_Anexos", length = 300)
	private String incluirAnexos;
	
	@Column(name = "Incluir_PDF", length = 300)
	private String incluirPDF;
	
	@Column(name = "Incluir_XML")
	private String incluirXML;

}
