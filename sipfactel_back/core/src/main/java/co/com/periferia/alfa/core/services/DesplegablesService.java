package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.DesplegableCategoriaDTO;
import co.com.periferia.alfa.core.dto.DesplegableSucursalesDTO;
import co.com.periferia.alfa.core.dto.DesplegablesDTO;
import co.com.periferia.alfa.core.dto.FuenteDTO;
import co.com.periferia.alfa.core.dto.PorcentajesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface DesplegablesService {

	List<DesplegablesDTO> listaFormaPago() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaTipoPersona() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaTipoIdentificacion() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaOblFiscales() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaCiudades() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaDepartamentos() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaCodTributo() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaRegFiscal() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaUmedidas() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaProductos() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaEnvioFE() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaPaises() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaAmbDestino() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaMonedas() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaMediosPago() throws ExcepcionSegAlfa;

	List<PorcentajesDTO> listaPorcentajes() throws ExcepcionSegAlfa;
	
	List<PorcentajesDTO> listaPorcentajesByImpuesto(String impuesto) throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaTiposDocumento() throws ExcepcionSegAlfa;
	
	List<DesplegableSucursalesDTO> listaSucursales() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaEstadoFac() throws ExcepcionSegAlfa;

	List<DesplegablesDTO> listaTipoOperacion() throws ExcepcionSegAlfa;
	
	List<DesplegablesDTO> listaTipoPolizas() throws ExcepcionSegAlfa;
	
	List<DesplegableCategoriaDTO> listaCategorias() throws ExcepcionSegAlfa;

	List<FuenteDTO> listaFuente() throws ExcepcionSegAlfa;
	
}
