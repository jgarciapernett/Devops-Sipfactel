package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.EmitirDelcopModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmitirDelcopDTO implements IBaseDTO<EmitirDelcopDTO, EmitirDelcopModel>{
	
	@SerializedName("error_id")
	private Integer errorId;
	@SerializedName("error_msg")
	private String errorMsg;
	private String mensaje;
	@SerializedName("tr_id")
	private Integer trId;
	private String cufe;
	private String qr;
	
	@Override
	public EmitirDelcopDTO modeloAdto(EmitirDelcopModel modelo) {
		return null;
	}
	
	@Override
	public EmitirDelcopModel dtoAModelo(EmitirDelcopDTO dto) {
		EmitirDelcopModel modelo = new EmitirDelcopModel();
		modelo.setErrorId(dto.getErrorId());
		modelo.setErrorMsg(dto.getErrorMsg());
		modelo.setMensaje(dto.getMensaje());
		modelo.setTrId(dto.getTrId());
		modelo.setCufe(dto.getCufe());
		modelo.setQr(dto.getQr());
		return modelo;
	}
	
	
	
}
