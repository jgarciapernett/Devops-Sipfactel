package co.com.periferia.alfa.core.dto;


import co.com.periferia.alfa.core.model.OpcMenuModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcMenuDTO implements IBaseDTO<OpcMenuDTO, OpcMenuModel>{

	private Integer id;
	private String idStr;
	private String nombre;
	
	@Override
	public OpcMenuDTO modeloAdto(OpcMenuModel modelo) {
		OpcMenuDTO dto = new OpcMenuDTO();
		dto.setId(modelo.getMenuMenu());
		dto.setIdStr(modelo.getMenuTitulo());
		dto.setNombre(modelo.getMenuTitulo());
		return dto;
	}
	@Override
	public OpcMenuModel dtoAModelo(OpcMenuDTO dto) {
		return null;
	}
	
}
