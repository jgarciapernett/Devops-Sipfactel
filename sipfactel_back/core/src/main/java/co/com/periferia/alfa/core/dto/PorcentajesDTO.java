package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.PorcentajesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PorcentajesDTO implements IBaseDTO<PorcentajesDTO, PorcentajesModel>{

	private Integer id;
	private String idStr;
	private String nombre;
	private Integer llave;
	

	public PorcentajesDTO() {
		super();
	}

	public PorcentajesDTO(Integer id, String idStr, String nombre) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.nombre = nombre;
	}

	@Override
	public PorcentajesDTO modeloAdto(PorcentajesModel modelo) {
		PorcentajesDTO dto =  new PorcentajesDTO();
		dto.setId(modelo.getId());
		dto.setIdStr(modelo.getIdStr());
		dto.setNombre(modelo.getNombre());
		dto.setLlave(modelo.getLlave());
		return dto;
	}

	@Override
	public PorcentajesModel dtoAModelo(PorcentajesDTO dto) {
		return null;
	}

}
