package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.SucursalesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SucursalesDTO implements IBaseDTO< SucursalesDTO, SucursalesModel>{

	private Integer sucu;
	private String codascele;
	private String codpoint;
	private String nomsuc;
	private Integer codciudad;
	private Integer coddepart;
	private String direccion;
	private Date fcreacion;
	private String ucreacion;
	private Date factualiza;
	private String uactualiza;
	private String estado;
	private Integer codpostal;
	private String nomciudad;
	private String nomdepart;

	@Override
	public SucursalesDTO modeloAdto(SucursalesModel modelo) {
		SucursalesDTO dto = new SucursalesDTO();
		
		dto.setSucu(modelo.getSucu());
		dto.setCodascele(modelo.getCodascele());
		dto.setCodpoint(modelo.getCodpoint());
		dto.setNomsuc(modelo.getNomsuc());
		dto.setCodciudad(modelo.getCodciudad());
		dto.setCoddepart(modelo.getCoddepart());
		dto.setDireccion(modelo.getDireccion());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());
		dto.setFactualiza(modelo.getFactualiza());
		dto.setUactualiza(modelo.getUactualiza());	
		dto.setEstado(modelo.getEstado());
		dto.setCodpostal(modelo.getCodpostal());
		
		return dto;
	}
	@Override
	public SucursalesModel dtoAModelo(SucursalesDTO dto) {
		SucursalesModel modelo = new SucursalesModel();
		
		modelo.setSucu(dto.getSucu());
		modelo.setCodascele(dto.getCodascele());
		modelo.setCodpoint(dto.getCodpoint());
		modelo.setNomsuc(dto.getNomsuc());
		modelo.setCodciudad(dto.getCodciudad());
		modelo.setCoddepart(dto.getCoddepart());
		modelo.setDireccion(dto.getDireccion());
		modelo.setFcreacion(dto.getFcreacion());
		modelo.setUcreacion(dto.getUcreacion());
		modelo.setFactualiza(dto.getFactualiza());
		modelo.setUactualiza(dto.getUactualiza());
		modelo.setEstado(dto.getEstado());
		modelo.setCodpostal(dto.getCodpostal());
		return modelo;
	}
	
	
	public boolean comparar(SucursalesDTO obj) {
		
		return this.codascele.equals(obj.getCodascele())
		&& this.codpoint.equals(obj.getCodpoint())
		&& this.nomsuc.equals(obj.getNomsuc())
		&& this.codciudad.equals(obj.getCodciudad())
		&& this.coddepart.equals(obj.getCoddepart())
		&& this.direccion.equals(obj.getDireccion())
		&& this.codpostal.equals(obj.getCodpostal());	
	}
}
