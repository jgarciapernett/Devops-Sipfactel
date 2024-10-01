package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.CodigoPostalDTO;
import co.com.periferia.alfa.core.dto.SucursalesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface ISucursalesService extends IServicio<SucursalesDTO, Integer>{

	List<SucursalesDTO> consultarDatos() throws ExcepcionSegAlfa;
	String editar(SucursalesDTO dto, Integer id) throws ExcepcionSegAlfa;
	List<CodigoPostalDTO> consCodigoPostal() throws ExcepcionSegAlfa;
	SucursalesDTO guardar(SucursalesDTO dto) throws ExcepcionSegAlfa;

}
