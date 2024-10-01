package co.com.periferia.alfa.core.services;

import co.com.periferia.alfa.core.dto.ApiResponseDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface IConsultaPaginadaPolizasService {

	ApiResponseDto facturaFiltro(String numPoliza, String numdoc, String fIniEnvio, 
			String fFinEnvio, String fIniEmision, String fFinEmision, 
			int pagina, int tamano) throws ExcepcionSegAlfa;
	
}
