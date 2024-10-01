package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.DatosGeneralesDTO;
import co.com.periferia.alfa.core.dto.DatosProveedorDTO;
import co.com.periferia.alfa.core.dto.ParametrosDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface ParametrosService {

	String agregarProveedor(DatosProveedorDTO dto) throws ExcepcionSegAlfa;

	String agregarGenerales(DatosGeneralesDTO dto) throws ExcepcionSegAlfa;

	DatosGeneralesDTO buscarGenerales() throws ExcepcionSegAlfa;

	DatosProveedorDTO buscarProveedor() throws ExcepcionSegAlfa;
	
	List<ParametrosDto> consultar() throws ExcepcionSegAlfa;
	
	ParametrosDto consultarDni(Integer dni) throws ExcepcionSegAlfa;

	String actualizar(ParametrosDto dto) throws ExcepcionSegAlfa;

}
