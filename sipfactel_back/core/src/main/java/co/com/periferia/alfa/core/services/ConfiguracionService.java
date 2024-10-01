package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.ConfiguracionDTO;
import co.com.periferia.alfa.core.dto.OpcionesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
public interface ConfiguracionService extends IServicio<ConfiguracionDTO, Integer>{
	

List<OpcionesDTO> listaOpciones() throws ExcepcionSegAlfa;

List<ConfiguracionDTO> listaTablas(Integer id) throws ExcepcionSegAlfa;

List<OpcionesDTO> listaAdquirientes() throws ExcepcionSegAlfa;

}

