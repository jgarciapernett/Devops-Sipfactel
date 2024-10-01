package co.com.periferia.alfa.core.services;

import co.com.periferia.alfa.core.dto.InformacionGeneralEdicionPolizasDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface IEdicionDetallePolizaService {

	public String editarDetalle(InformacionGeneralEdicionPolizasDto dto, String tipo, Integer adquiriente) throws ExcepcionSegAlfa;
	
}
