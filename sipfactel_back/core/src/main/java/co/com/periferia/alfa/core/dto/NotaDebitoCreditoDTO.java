package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.NotaDebitoCreditoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotaDebitoCreditoDTO implements IBaseDTO<NotaDebitoCreditoDTO, NotaDebitoCreditoModel> {

	private Integer id;
	private String numdoc;
	private Integer estado;

	@Override
	public NotaDebitoCreditoDTO modeloAdto(NotaDebitoCreditoModel modelo) {

		NotaDebitoCreditoDTO dto = new NotaDebitoCreditoDTO();
		dto.setId(modelo.getId());
		dto.setNumdoc(modelo.getNumdoc());
		dto.setEstado(modelo.getEstado());
		return dto;
	}

	@Override
	public NotaDebitoCreditoModel dtoAModelo(NotaDebitoCreditoDTO dto) {
		return null;
	}

}
