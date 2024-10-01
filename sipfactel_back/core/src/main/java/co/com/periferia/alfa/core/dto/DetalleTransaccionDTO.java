package co.com.periferia.alfa.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetalleTransaccionDTO{

	private String tipoDocumento;
	private String numeroDocumento;
	private String transaccionid;
	private String mensaje;

}
