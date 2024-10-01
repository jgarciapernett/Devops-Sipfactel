package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransaccionIdTitanioListar {

	@SerializedName("tr_id")
	private Integer trId;
	@SerializedName("tipo_tran_id")
	private Integer tipoTranId;
	@SerializedName("tipoDocId")
	private Integer tipoDocId;
	private String receptor;
	private String doc;
	private String fecha;
	@SerializedName("estado_id")
	private Integer estadoId;
	@SerializedName("pausa_id")
	private String pausaId;
	private String borrado;
	private String fanexo;
	
}
