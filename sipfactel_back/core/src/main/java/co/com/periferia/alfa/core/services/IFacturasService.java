package co.com.periferia.alfa.core.services;


import co.com.periferia.alfa.core.dto.AdquirientesDTO;
import co.com.periferia.alfa.core.dto.FacturasDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface IFacturasService extends IServicio<FacturasDTO, Integer>{

	FacturasDTO buscarPorFacturas(String numdoc) throws ExcepcionSegAlfa;

	FacturasDTO traerTransaccion(Integer id) throws ExcepcionSegAlfa;
	
	String editarAdquiriente(AdquirientesDTO dto, Integer id) throws ExcepcionSegAlfa;

	AdquirientesDTO traerAdquiriente(Integer id) throws ExcepcionSegAlfa;

}