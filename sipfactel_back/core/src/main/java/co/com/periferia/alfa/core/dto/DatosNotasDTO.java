package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.DatosNotasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosNotasDTO implements IBaseDTO<DatosNotasDTO, DatosNotasModel>{

	@SerializedName("Invoice_ID")
	private String invoiceID;
	@SerializedName("Invoice_UUID")
	private String invoiceUUID;
	@SerializedName("Invoice_IssueDate")
	private String invoiceIssueDate;
	
	public DatosNotasDTO() {
		super();
	}	
	
	public DatosNotasDTO(String invoiceID, String invoiceUUID,
			String invoiceIssueDate) {
		super();
		this.invoiceID = invoiceID;
		this.invoiceUUID = invoiceUUID;
		this.invoiceIssueDate = invoiceIssueDate;
	}

	@Override
	public DatosNotasDTO modeloAdto(DatosNotasModel modelo) {
		DatosNotasDTO dto = new DatosNotasDTO();
		dto.setInvoiceID(modelo.getInvoiceID());
		dto.setInvoiceUUID(modelo.getInvoiceUUID());
		dto.setInvoiceIssueDate(modelo.getInvoiceIssueDate());
		return dto;
	}

	@Override
	public DatosNotasModel dtoAModelo(DatosNotasDTO dto) {
		return null;
	}
}
