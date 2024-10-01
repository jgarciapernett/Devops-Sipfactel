package co.com.periferia.alfa.core.services;

import java.io.IOException;
import java.util.List;

import co.com.periferia.alfa.core.dto.DetalleAdquirientesDTO;
import co.com.periferia.alfa.core.dto.DetalleFacturasDTO;
import co.com.periferia.alfa.core.dto.DetalleTransaccionDTO;
import co.com.periferia.alfa.core.dto.PolizaErrorEtlDTO;
import co.com.periferia.alfa.core.dto.PolizasDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface IPolizasService {

	String editar(PolizasDTO dto, Integer id) throws ExcepcionSegAlfa;

	String agregar(PolizasDTO dto, Integer id) throws ExcepcionSegAlfa;
	
	DetalleFacturasDTO detalleFacturaEtl(String numpoliza, Integer idtabla, String tabla) throws ExcepcionSegAlfa;
	
	DetalleTransaccionDTO detalleTransaccion(Integer dniDocumento, String tipo) throws ExcepcionSegAlfa;
	
	String editarDetalleErrorEtl(DetalleFacturasDTO dto) throws ExcepcionSegAlfa;

	String reenviar(String numdoc, String user, String tipodoc) throws ExcepcionSegAlfa, IOException;

	DetalleAdquirientesDTO detalleAdquiriente(String numpoliza, String tipoMovimiento) throws ExcepcionSegAlfa;

	String buscarPorPoliza(String numeropoliza, Integer errorid) throws ExcepcionSegAlfa;

	String recategorizar(Integer idtabla, String tabla, Integer categoria, String observacion) throws ExcepcionSegAlfa;

	List<PolizaErrorEtlDTO> filtroPolizasErrorEtl(String numpoliza, String numdoc, Integer categoria)
			throws ExcepcionSegAlfa;

	
}
