package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.OpcListaDocumentoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcListaDocumentoDTO implements IBaseDTO<OpcListaDocumentoDTO, OpcListaDocumentoModel>{

	private Integer id;
	private String tipodocumento;
	
	public OpcListaDocumentoDTO(Integer id, String tipodocumento) {
		super();
		this.id=id;
		this.tipodocumento= tipodocumento;
	}
	
	
	public OpcListaDocumentoDTO() {}

	@Override
	public OpcListaDocumentoDTO modeloAdto(OpcListaDocumentoModel modelo) {
		OpcListaDocumentoDTO dto = new OpcListaDocumentoDTO();
		dto.setId(modelo.getConf());
		dto.setTipodocumento(modelo.getTipodocumento());
		return dto;
	}


	@Override
	public OpcListaDocumentoModel dtoAModelo(OpcListaDocumentoDTO dto) {
		OpcListaDocumentoModel modelo = new OpcListaDocumentoModel();		
		modelo.setConf(dto.getId());
		modelo.setTipodocumento(dto.getTipodocumento());
		return modelo;
	}


	
	

}
