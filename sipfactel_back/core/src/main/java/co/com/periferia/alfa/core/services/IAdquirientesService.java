package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.AdquirientesDTO;
import co.com.periferia.alfa.core.dto.ConsAdquirientesDTO;
import co.com.periferia.alfa.core.dto.ConsultaAdquirienteDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface IAdquirientesService extends IServicio<AdquirientesDTO, Integer>{

	String editar(ConsultaAdquirienteDto dto, Integer documento, String tipo) throws ExcepcionSegAlfa;

	List<ConsAdquirientesDTO> consultaAdquirientes(Integer id, String tipopersona, String numidentificacion)
			throws ExcepcionSegAlfa;
	
	public ConsultaAdquirienteDto consultar (Integer dni) throws ExcepcionSegAlfa;
	
	

}
