package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.DatosGeneralesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosGeneralesDTO implements IBaseDTO<DatosGeneralesDTO, DatosGeneralesModel>{

	private Integer dato;
	private Integer codPais;
	private String nomPais;
	private Integer moneda;
	private Integer ambiente;
	private String estado;
	private Date fcreacion;
	private String ucreacion;
	
	@Override
	public DatosGeneralesDTO modeloAdto(DatosGeneralesModel modelo) {
		DatosGeneralesDTO dto = new DatosGeneralesDTO();
		
		dto.setDato(modelo.getDato());
		dto.setCodPais(modelo.getCodPais());
		dto.setNomPais(modelo.getNomPais());
		dto.setMoneda(modelo.getMoneda());
		dto.setAmbiente(modelo.getAmbiente());
		dto.setEstado(modelo.getEstado());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());		
		return dto;
	}
	
	@Override
	public DatosGeneralesModel dtoAModelo(DatosGeneralesDTO dto) {
		DatosGeneralesModel modelo = new DatosGeneralesModel();
		
		modelo.setDato(dto.getDato());
		modelo.setCodPais(dto.getCodPais());
		modelo.setNomPais(dto.getNomPais());
		modelo.setMoneda(dto.getMoneda());
		modelo.setAmbiente(dto.getAmbiente());
		modelo.setEstado(dto.getEstado());
		modelo.setFcreacion(dto.getFcreacion());
		modelo.setUcreacion(dto.getUcreacion());		
		return modelo;
	}
	
	
	
}
