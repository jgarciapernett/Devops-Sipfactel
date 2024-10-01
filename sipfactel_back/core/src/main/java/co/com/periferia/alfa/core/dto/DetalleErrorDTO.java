package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.DetalleErrorModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleErrorDTO implements IBaseDTO<DetalleErrorDTO, DetalleErrorModel> {

	private Integer id;
	private Integer idtabla;
	private String tabla;
	private Integer codigo;
	private String categoria;
	private String mensaje;
	private String observacion;
	private Date fchinsercion;
	private String usuinsercion;
	private Date fchactualizacion;
	private String usaactualizacion;

	@Override
	public DetalleErrorDTO modeloAdto(DetalleErrorModel modelo) {
		DetalleErrorDTO dto = new DetalleErrorDTO();
		dto.setId(modelo.getId());
		dto.setIdtabla(modelo.getIdtabla());
		dto.setTabla(modelo.getTabla());
		dto.setCodigo(modelo.getCodigo());
		dto.setCategoria(modelo.getCategoria());
		dto.setMensaje(modelo.getMensaje());
		dto.setObservacion(modelo.getObservacion());
		dto.setFchinsercion(modelo.getFchinsercion());
		dto.setUsuinsercion(modelo.getUsuinsercion());
		dto.setFchactualizacion(modelo.getFchactualizacion());
		dto.setUsaactualizacion(modelo.getUsaactualizacion());
		return dto;
	}

	@Override
	public DetalleErrorModel dtoAModelo(DetalleErrorDTO dto) {
		return null;
	}

}
