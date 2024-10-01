package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.CompaniasDTO;
import co.com.periferia.alfa.core.dto.OpcCompaniasDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface CompaniasService extends IServicio<CompaniasDTO, Integer>{

	List<OpcCompaniasDTO> listaOpciones() throws ExcepcionSegAlfa;

	String editar(CompaniasDTO dto, Integer id) throws ExcepcionSegAlfa;
}
