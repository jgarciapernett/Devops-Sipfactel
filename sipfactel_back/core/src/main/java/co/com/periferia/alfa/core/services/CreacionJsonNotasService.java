package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.ConsultaEstadoDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface CreacionJsonNotasService {
	
	List<ConsultaEstadoDto> emitirNotasDeb(boolean id, String numdoc, String user) throws ExcepcionSegAlfa;
	
	List<ConsultaEstadoDto> emitirNotasCred(boolean id, String numdoc, String user) throws ExcepcionSegAlfa;

}
