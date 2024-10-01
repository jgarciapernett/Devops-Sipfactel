package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.PolizaErrorEtlModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolizaErrorEtlDTO implements IBaseDTO<PolizaErrorEtlDTO, PolizaErrorEtlModel> {

	private Integer iderror;
	private Integer idtabla;
	private String tabla;
	private Integer categoria;
	private String numpoliza;
	private String numdocumento;
	private String tipomovimiento;
	private String fechaemision;
	private String fechainsercion;
	private Integer errorid;
	private String mensaje;
	private String observacion;
	private String numpersona;

	@Override
	public PolizaErrorEtlDTO modeloAdto(PolizaErrorEtlModel modelo) {
		PolizaErrorEtlDTO dto = new PolizaErrorEtlDTO();
		dto.setIderror(modelo.getIderror());
		dto.setIdtabla(modelo.getIdtabla());
		dto.setTabla(modelo.getTabla());
		dto.setNumpoliza(modelo.getNumpoliza());
		dto.setNumdocumento(modelo.getNumdocumento());
		dto.setTipomovimiento(modelo.getTipomovimiento());
		dto.setFechaemision(modelo.getFechaemision());
		dto.setFechainsercion(modelo.getFechainsercion());
		dto.setMensaje(modelo.getMensaje());
		dto.setErrorid(modelo.getErrorid());
		dto.setCategoria(modelo.getCategoria());
		dto.setObservacion(modelo.getObservacion());
		dto.setNumpersona(modelo.getNumpersona());
		return dto;
	}

	@Override
	public PolizaErrorEtlModel dtoAModelo(PolizaErrorEtlDTO dto) {
		PolizaErrorEtlModel modelo = new PolizaErrorEtlModel();
		modelo.setNumpoliza(dto.getNumpoliza());
		modelo.setNumdocumento(dto.getNumdocumento());
		modelo.setTipomovimiento(dto.getTipomovimiento());
		modelo.setMensaje(dto.getMensaje());
		return modelo;
	}

}
