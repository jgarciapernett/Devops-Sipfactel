package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.ConsultaEstadoDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface CreacionJsonService {

	List<ConsultaEstadoDto> emitirFacturas(Integer id, String numdoc, String user)
			throws ExcepcionSegAlfa;
}
