package co.com.periferia.alfa.core.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * clase que pamea la repsuesta de titanio en el servicio de listar documentos
 * @author Duvan Rodriguez
 */

@Getter
@Setter
public class ListarDocumentosDto {
	
	@SerializedName("transaccion_id")
	private List<TransaccionIdTitanioListar> transaccionId;
	private PaginatorTitanioDto paginator;
	@SerializedName("error_id")
	private Integer errorId;
	@SerializedName("error_msg")
	private String errorMsg;
	
}
