package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EliminarTransaccionDto {

	@SerializedName("error_id")
	private Integer errorId;
	@SerializedName("error_msg")
	private String errorMsg;
	
}
