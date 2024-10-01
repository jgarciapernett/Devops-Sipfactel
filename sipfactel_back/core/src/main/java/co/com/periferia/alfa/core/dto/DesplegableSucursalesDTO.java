package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.DesplegableSucursalesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesplegableSucursalesDTO implements IBaseDTO< DesplegableSucursalesDTO, DesplegableSucursalesModel>{

	private Integer sucu;
	private String nomsuc;	

	@Override
	public DesplegableSucursalesDTO modeloAdto(DesplegableSucursalesModel modelo) {
		DesplegableSucursalesDTO dto = new DesplegableSucursalesDTO();
		dto.setSucu(modelo.getSucu());
		dto.setNomsuc(modelo.getNomsuc());
		return dto;
	}

	@Override
	public DesplegableSucursalesModel dtoAModelo(DesplegableSucursalesDTO dto) {
		DesplegableSucursalesModel modelo = new DesplegableSucursalesModel();
		modelo.setSucu(dto.getSucu());
		modelo.setNomsuc(dto.getNomsuc());
		return modelo;
	}


	
}
