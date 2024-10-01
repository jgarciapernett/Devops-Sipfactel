package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DatosNotasModel {
	
	@Id
	@Column(name = "Invoice_ID", length = 100)
	private String invoiceID;
	
	@Column(name = "Invoice_UUID", length = 200)
	private String invoiceUUID;
	
	@Column(name = "Invoice_IssueDate")
	private String invoiceIssueDate;

}
