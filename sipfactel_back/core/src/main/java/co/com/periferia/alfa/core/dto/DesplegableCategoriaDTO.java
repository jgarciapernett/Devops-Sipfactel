package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.DesplegableCategoriaModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesplegableCategoriaDTO implements IBaseDTO<DesplegableCategoriaDTO, DesplegableCategoriaModel> {

	private Integer cat;
	private String nombre;
	private String descripcion;

	@Override
	public DesplegableCategoriaDTO modeloAdto(DesplegableCategoriaModel modelo) {
		DesplegableCategoriaDTO dto = new DesplegableCategoriaDTO();
		dto.setCat(modelo.getCat());
		dto.setNombre(modelo.getNombre());
		dto.setDescripcion(modelo.getDescripcion());
		return dto;
	}

	@Override
	public DesplegableCategoriaModel dtoAModelo(DesplegableCategoriaDTO dto) {
		DesplegableCategoriaModel modelo = new DesplegableCategoriaModel();
		modelo.setCat(dto.getCat());
		modelo.setNombre(dto.getNombre());
		modelo.setDescripcion(dto.getDescripcion());
		return modelo;
	}

}
