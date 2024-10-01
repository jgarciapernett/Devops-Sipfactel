package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.HomologacionDTO;
import co.com.periferia.alfa.core.dto.OpcHomologacionDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface HomologacionService extends IServicio<HomologacionDTO, Integer> {

	List<OpcHomologacionDTO> listaOpciones() throws ExcepcionSegAlfa;

	List<HomologacionDTO> listaTablas(Integer id) throws ExcepcionSegAlfa;

}
