package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.DetalleAdquirientesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleAdquirientesDTO implements IBaseDTO<DetalleAdquirientesDTO, DetalleAdquirientesModel>{

	private String codtipoidentificacion;
	private String numidentificacion;
	private String nombre;
	private String direccion;
	private String nombreciudad;
	private String nombredepartamento;

	@Override
	public DetalleAdquirientesDTO modeloAdto(DetalleAdquirientesModel modelo) {
	
		DetalleAdquirientesDTO dto = new DetalleAdquirientesDTO();
		dto.setCodtipoidentificacion(modelo.getCodtipoidentificacion());
		dto.setNumidentificacion(modelo.getNumidentificacion());
		dto.setNombre(modelo.getNombre());
		dto.setDireccion(modelo.getDireccion());
		dto.setNombreciudad(modelo.getNombreciudad());
		dto.setNombredepartamento(modelo.getNombredepartamento());
		return dto;
	}

	@Override
	public DetalleAdquirientesModel dtoAModelo(DetalleAdquirientesDTO dto) {
		
		DetalleAdquirientesModel modelo = new DetalleAdquirientesModel();
		modelo.setCodtipoidentificacion(dto.getCodtipoidentificacion());
		modelo.setNumidentificacion(dto.getNumidentificacion());
		modelo.setNombre(dto.getNombre());
		modelo.setDireccion(dto.getDireccion());
		modelo.setNombreciudad(dto.getNombreciudad());
		modelo.setNombredepartamento(dto.getNombredepartamento());
		return modelo;
	}

}
