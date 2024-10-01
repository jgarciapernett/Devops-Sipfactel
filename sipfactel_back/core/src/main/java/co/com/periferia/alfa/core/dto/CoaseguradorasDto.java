package co.com.periferia.alfa.core.dto;

import java.io.Serializable;

import co.com.periferia.alfa.core.model.CoaseguradorasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoaseguradorasDto implements IBaseDTO<CoaseguradorasDto, CoaseguradorasModel>, Serializable  {
	
	private String coaseguradora;
	private String tdoc;
	private String ndoc;
	private String porcPart;
	private String numpol;
	private String ramo;

	@Override
	public CoaseguradorasDto modeloAdto(CoaseguradorasModel modelo) {
		CoaseguradorasDto dto = new CoaseguradorasDto();
		dto.setCoaseguradora(modelo.getCoaseguradora());
		dto.setTdoc(modelo.getTdoc());
		dto.setNdoc(modelo.getNdoc());
		dto.setPorcPart(modelo.getPorcPart());
		dto.setNumpol(modelo.getNumpol());
		dto.setRamo(modelo.getRamo());
		return dto;
	}

	@Override
	public CoaseguradorasModel dtoAModelo(CoaseguradorasDto dto) {
		return null;
	}
	
	private static final long serialVersionUID = 1L;

}
