package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcCiuDepModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcCiuDepDTO implements IBaseDTO<OpcCiuDepDTO, OpcCiuDepModel>{

	private Integer id;
	private String idStr;
	private String nombre;
	
	public OpcCiuDepDTO() {
		super();
	}

	public OpcCiuDepDTO(Integer id, String idStr, String nombre) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.nombre = nombre;
	}

	@Override
	public OpcCiuDepDTO modeloAdto(OpcCiuDepModel modelo) {
		OpcCiuDepDTO dto= new OpcCiuDepDTO();
		
		dto.setId(modelo.getLlave());
		dto.setNombre(modelo.getValor());
		dto.setIdStr(modelo.getValor());
		return dto;
		
	}

	@Override
	public OpcCiuDepModel dtoAModelo(OpcCiuDepDTO dto) {
		OpcCiuDepModel modelo = new OpcCiuDepModel();
		
		modelo.setLlave(dto.getId());
		modelo.setValor(modelo.getValor());
		return modelo;
	}

}
