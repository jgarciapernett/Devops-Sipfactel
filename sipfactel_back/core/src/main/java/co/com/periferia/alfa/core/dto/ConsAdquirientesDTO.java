package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.ConsAdquirientesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsAdquirientesDTO implements IBaseDTO<ConsAdquirientesDTO, ConsAdquirientesModel>{

	private Integer id;
	private String tipoDocumento;
	private String nombrerazonsocial;
	private String numeroIdentificacion;
	private String tipoPersona;
	
	@Override
	public ConsAdquirientesDTO modeloAdto(ConsAdquirientesModel modelo) {
		ConsAdquirientesDTO dto = new ConsAdquirientesDTO();
		dto.setId(modelo.getId());
		dto.setTipoDocumento(modelo.getTipoDocumento());
		dto.setNombrerazonsocial(modelo.getNombrerazonsocial());
		dto.setNumeroIdentificacion(modelo.getNumeroIdentificacion());
		dto.setTipoPersona(modelo.getTipoPersona());
		return dto;
	}
	@Override
	public ConsAdquirientesModel dtoAModelo(ConsAdquirientesDTO dto) {
		ConsAdquirientesModel modelo = new ConsAdquirientesModel();
		modelo.setId(dto.getId());
		modelo.setTipoDocumento(dto.getTipoDocumento());
		modelo.setNombrerazonsocial(dto.getNombrerazonsocial());
		modelo.setNumeroIdentificacion(dto.getNumeroIdentificacion());
		modelo.setTipoPersona(dto.getTipoPersona());
		return modelo;
	}
}
