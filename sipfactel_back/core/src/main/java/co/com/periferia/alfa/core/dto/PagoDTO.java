package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.PagoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoDTO implements IBaseDTO<PagoDTO, PagoModel>{
	
	@SerializedName("ID")
	private String id;
	@SerializedName("PaymentMeansCode")
	private String paymentMeansCode;
	@SerializedName("PaymentDueDate")
	private String paymentDueDate;
	@SerializedName("InstructionNote")
	private String instructionNote;
	@SerializedName("PaymentID")
	private String paymentID;
	
	
	public PagoDTO() {
		super();
	}
	
	public PagoDTO(String iD, String paymentMeansCode, String paymentDueDate, String instructionNote,
			String paymentID) {
		super();
		this.id = iD;
		this.paymentMeansCode = paymentMeansCode;
		this.paymentDueDate = paymentDueDate;
		this.instructionNote = instructionNote;
		this.paymentID = paymentID;
	}
	
	@Override
	public PagoDTO modeloAdto(PagoModel modelo) {
		PagoDTO dto = new PagoDTO();
		dto.setId(nulo(modelo.getId()));
		dto.setPaymentMeansCode(nulo(modelo.getPaymentMeansCode()));
		dto.setPaymentDueDate(nulo(modelo.getPaymentDueDate()));
		dto.setInstructionNote(nulo(modelo.getInstructionNote()));
		dto.setPaymentID(nulo(modelo.getPaymentID()));
		return dto;
	}
	@Override
	public PagoModel dtoAModelo(PagoDTO dto) {
		return null;
	}
	
	public String nulo(String dato) {
		
		if(dato == null) {
			dato = "";
		}
		return dato;
	}
	
}
