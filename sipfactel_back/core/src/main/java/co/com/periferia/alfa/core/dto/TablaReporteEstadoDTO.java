package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.TablaReporteEstadoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TablaReporteEstadoDTO implements IBaseDTO<TablaReporteEstadoDTO, TablaReporteEstadoModel> {

	private String id;
	private String compania;
	private String sucursal;
	private String fechaTransaccion;
	private String estado;
	private String numPoliza;
	private String adquiriente;
	private String tipoTransaccion;
	private String numTransaccion;
	private String numReferencia;
	private Double valorPrima;
	private Double valorIva;
	private Double total;
	private String mensaje;
	private String errorNumeracion;

	@Override
	public TablaReporteEstadoDTO modeloAdto(TablaReporteEstadoModel modelo) {
		TablaReporteEstadoDTO dto = new TablaReporteEstadoDTO();
		dto.setId(modelo.getId());
		dto.setCompania(modelo.getCompania());
		dto.setSucursal(modelo.getSucursal());
		dto.setFechaTransaccion(modelo.getFecha().concat(" ").concat(modelo.getHora()));
		dto.setEstado(modelo.getEstado());
		dto.setNumPoliza(modelo.getNumPoliza());
		dto.setAdquiriente(modelo.getAdquiriente());
		dto.setTipoTransaccion(modelo.getTipoTransaccion());
		dto.setNumTransaccion(modelo.getNumTransaccion());
		dto.setNumReferencia(modelo.getNumReferencia());
		dto.setValorPrima(modelo.getValorPrima());
		dto.setValorIva(modelo.getValorIva());
		dto.setTotal(modelo.getTotal());
		dto.setMensaje(modelo.getMensaje());
		dto.setErrorNumeracion(modelo.getErrorNumeracion());
		return dto;
	}

	@Override
	public TablaReporteEstadoModel dtoAModelo(TablaReporteEstadoDTO dto) {
		return null;
	}

}
