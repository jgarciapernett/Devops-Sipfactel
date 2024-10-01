package co.com.periferia.alfa.core.services;

import co.com.periferia.alfa.core.dto.InformacionGeneralEdicionPolizasDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface IDetalleDocumentoService {

	public InformacionGeneralEdicionPolizasDto detalleFactura(Integer documento, String tipo) throws ExcepcionSegAlfa;
	
}
