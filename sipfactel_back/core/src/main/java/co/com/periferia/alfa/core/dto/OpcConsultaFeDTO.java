package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcConsultaFeModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcConsultaFeDTO implements IBaseDTO<OpcConsultaFeDTO, OpcConsultaFeModel>{

	private Integer id;
	private String nombre;

	@Override
	public OpcConsultaFeDTO modeloAdto(OpcConsultaFeModel modelo) {
		OpcConsultaFeDTO dto = new OpcConsultaFeDTO();
		
		dto.setId(modelo.getId());
		dto.setNombre(modelo.getNombre());
		return dto;
	}

	@Override
	public OpcConsultaFeModel dtoAModelo(OpcConsultaFeDTO dto) {
		OpcConsultaFeModel modelo = new OpcConsultaFeModel();
		
		modelo.setId(dto.getId());
		modelo.setNombre(dto.getNombre());
		return modelo;
	}

}
