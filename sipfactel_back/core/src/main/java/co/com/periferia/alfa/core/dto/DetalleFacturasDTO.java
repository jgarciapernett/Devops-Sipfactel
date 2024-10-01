package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.DetalleFacturasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleFacturasDTO implements IBaseDTO<DetalleFacturasDTO, DetalleFacturasModel>{

	private Integer id;
	private Integer idDet;
	private Integer tipodoc;
	private Integer cantidad;
	private String descripcion;
	private Integer valor;
	private Integer valoriva;
	private Integer tasaiva;
	private Integer subtotalvalor;
	private Integer subtotaliva;
	private Integer total;

	@Override
	public DetalleFacturasDTO modeloAdto(DetalleFacturasModel modelo) {
		
		DetalleFacturasDTO dto= new DetalleFacturasDTO();
		dto.setCantidad(modelo.getCantidad());
		dto.setDescripcion(modelo.getDescripcion());
		dto.setValor(modelo.getValor());
		dto.setValoriva(modelo.getValoriva());
		dto.setTasaiva(modelo.getTasaiva());
		dto.setSubtotalvalor(modelo.getSubtotalvalor());
		dto.setSubtotaliva(modelo.getSubtotaliva());
		dto.setTotal(modelo.getTotal());
		dto.setId(modelo.getId());
		dto.setIdDet(modelo.getIdDet());
		dto.setTipodoc(modelo.getTipodoc());
		return dto;
	}

	@Override
	public DetalleFacturasModel dtoAModelo(DetalleFacturasDTO dto) {
		DetalleFacturasModel modelo = new DetalleFacturasModel();
		
		modelo.setCantidad(dto.getCantidad());
		modelo.setDescripcion(dto.getDescripcion());
		modelo.setValor(dto.getValor());
		modelo.setValoriva(dto.getValoriva());
		modelo.setTasaiva(dto.getTasaiva());
		modelo.setSubtotalvalor(dto.getSubtotalvalor());
		modelo.setSubtotaliva(dto.getSubtotaliva());
		modelo.setTotal(dto.getTotal());
		modelo.setId(dto.getId());
		modelo.setIdDet(dto.getIdDet());
		modelo.setTipodoc(dto.getTipodoc());
		return modelo;
	}

}
