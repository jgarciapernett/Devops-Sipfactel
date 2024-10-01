package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.DatosAdministrablesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface DatosAdministrablesService {

	String editar(DatosAdministrablesDTO dto, Integer id) throws ExcepcionSegAlfa;

	List<DatosAdministrablesDTO> buscarTodos() throws ExcepcionSegAlfa;

	DatosAdministrablesDTO buscarPorID(Integer id) throws ExcepcionSegAlfa;

}
