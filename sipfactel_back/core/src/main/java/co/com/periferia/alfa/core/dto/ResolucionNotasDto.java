package co.com.periferia.alfa.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResolucionNotasDto {

	private List<DatosResolucionNotasDto> debito;
	private List<DatosResolucionNotasDto> credito;
	
}
