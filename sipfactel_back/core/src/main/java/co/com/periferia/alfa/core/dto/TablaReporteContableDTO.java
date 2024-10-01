package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.TablaReporteContableModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TablaReporteContableDTO implements IBaseDTO<TablaReporteContableDTO, TablaReporteContableModel>{

	private String id;	
	private String compania;	
	private Integer cantidadRegistros;
	private String valorPrima;
	private String valorIva;
	private String total;

	@Override
	public TablaReporteContableDTO modeloAdto(TablaReporteContableModel modelo) {
		TablaReporteContableDTO dto = new TablaReporteContableDTO();
		dto.setId(modelo.getId());
		dto.setCompania(modelo.getCompania());
		dto.setCantidadRegistros(modelo.getCantidadRegistros());
		dto.setValorPrima(modelo.getValorPrima());
		dto.setValorIva(modelo.getValorIva());
		dto.setTotal(modelo.getTotal());
		return dto;
	}

	@Override
	public TablaReporteContableModel dtoAModelo(TablaReporteContableDTO dto) {
		return null;
	}

}
