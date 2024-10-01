package co.com.periferia.alfa.core.services;

import co.com.periferia.alfa.core.dto.DatosResolucionNotasDto;
import co.com.periferia.alfa.core.dto.ResolucionNotasDto;
import co.com.periferia.alfa.core.dto.ResultadoDto;

public interface IResolucionNotasService {

	public ResolucionNotasDto consultarResolucionNotas(Integer sucursal, Integer compania, String producto);
	
	public ResultadoDto editarResolucionNotas(DatosResolucionNotasDto nota);
	
	public ResultadoDto crearResolucionNotas(DatosResolucionNotasDto nota);
	
	public ResultadoDto eliminarResolucionNotas(DatosResolucionNotasDto nota);
	
}
