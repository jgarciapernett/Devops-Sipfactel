package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.HomologacionModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomologacionDTO implements IBaseDTO<HomologacionDTO, HomologacionModel> {

	private Integer id;
	private Integer conf;
	private String homologado;
	private Integer fuente;
	private String original;
	private String descripcion;
	private String ucrea;
	private Date fcrea;
	private String uactualiza;
	private Date factualiza;
	private String estado;
	private String nomfuente;

	@Override
	public HomologacionDTO modeloAdto(HomologacionModel modelo) {
		HomologacionDTO dto = new HomologacionDTO();

		dto.setId(modelo.getHomo());
		dto.setConf(modelo.getConf());
		dto.setFuente(modelo.getFuente());
		dto.setOriginal(modelo.getOrigen());
		dto.setHomologado(modelo.getHomologado());
		dto.setDescripcion(modelo.getDescripcion());
		dto.setUcrea(modelo.getUsuarioCreacion());
		dto.setFcrea(modelo.getFechaCreacion());
		dto.setUactualiza(modelo.getUsuarioAct());
		dto.setFactualiza(modelo.getFechaAct());
		dto.setEstado(modelo.getEstado());
		return dto;
	}

	@Override
	public HomologacionModel dtoAModelo(HomologacionDTO dto) {
		HomologacionModel modelo = new HomologacionModel();

		modelo.setHomo(dto.getId());
		modelo.setConf(dto.getConf());
		modelo.setFuente(dto.getFuente());
		modelo.setOrigen(dto.getOriginal());
		modelo.setHomologado(dto.getHomologado());
		modelo.setDescripcion(dto.getDescripcion());
		modelo.setUsuarioCreacion(dto.getUcrea());
		modelo.setFechaCreacion(dto.getFcrea());
		modelo.setUsuarioAct(dto.getUactualiza());
		modelo.setFechaAct(dto.getFactualiza());
		modelo.setEstado(dto.getEstado());
		return modelo;
	}
	
}
