package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.ConfiguracionModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConfiguracionDTO implements IBaseDTO<ConfiguracionDTO, ConfiguracionModel> {
	
	private Integer id;
	private String codigo;
	private String nombre;
	private String sistema;
	private Integer padre;
	private String descripcion;
	private String ucrea;
	private Date fcrea;
	private String uactualiza;
	private Date  factualiza;
	private String estado;

	@Override
	public ConfiguracionDTO modeloAdto(ConfiguracionModel modelo) {
		ConfiguracionDTO dto = new ConfiguracionDTO();
		
		dto.setId(modelo.getConf());
		dto.setCodigo(modelo.getLlave());
		dto.setNombre(modelo.getValor());
		dto.setSistema(modelo.getSistema());
		dto.setPadre(modelo.getPadre());
		dto.setDescripcion(modelo.getDescripcion());
		dto.setUcrea(modelo.getUsuarioCreacion());
		dto.setFcrea(modelo.getFechaCreacion());
		dto.setUactualiza(modelo.getUsuarioAct());
		dto.setFactualiza(modelo.getFechaAct());
		dto.setEstado(modelo.getEstado());
		return dto;
	}


	@Override
	public ConfiguracionModel dtoAModelo(ConfiguracionDTO dto) {
		ConfiguracionModel modelo = new ConfiguracionModel();
		
		modelo.setConf(dto.getId());
		modelo.setLlave(dto.getCodigo());
		modelo.setValor(dto.getNombre());
		modelo.setSistema(dto.getSistema());
		modelo.setPadre(dto.getPadre());
		modelo.setDescripcion(dto.getDescripcion());
		modelo.setUsuarioCreacion(dto.getUcrea());
		modelo.setFechaCreacion(dto.getFcrea());
		modelo.setUsuarioAct(dto.getUactualiza());
		modelo.setFechaAct(dto.getFactualiza());
		modelo.setEstado(dto.getEstado());
		return modelo;
	}
	
	
	

}
