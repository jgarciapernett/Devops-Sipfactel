package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.ResolucionFacturasDto;
import co.com.periferia.alfa.core.dto.ResultadoDto;

public interface IResolucionService {

	public List<ResolucionFacturasDto> consultarResolucion(Integer sucursal, Integer compania, String producto);
	
	public List<String> consultarRangoSuperior(Integer sucursal, Integer compania, String producto);
	
	public ResultadoDto editarResolucion(ResolucionFacturasDto resolucion);
	
	public ResultadoDto crearResolucion(ResolucionFacturasDto resolucion);
	
	public ResultadoDto eliminarResolucion(ResolucionFacturasDto resolucion);
	
}
