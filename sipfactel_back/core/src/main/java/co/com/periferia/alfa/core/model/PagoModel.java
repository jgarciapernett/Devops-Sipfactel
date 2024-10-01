package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PagoModel {

	@Id
	@Column(name = "ID", length = 300)
	private String id;

	@Column(name = "PaymentMeansCode", length = 300)
	private String paymentMeansCode;

	@Column(name = "PaymentDueDate")
	private String paymentDueDate;

	@Column(name = "InstructionNote")
	private String instructionNote;

	@Column(name = "PaymentID")
	private String paymentID;

}
