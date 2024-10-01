package co.com.periferia.alfa.core.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AlertaDTO {

	private String compania;
	
	private String sucursal;
	
	private String descripcion;
	
}
