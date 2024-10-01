package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.admin.Auditoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditoriaDTO implements IBaseDTO<AuditoriaDTO, Auditoria> {

	private Integer audiAudi;
	private String audiDescripcion;
	private Date audiFecha;
	private String audiUsuario;
	private String audiFuncionalidad;
	private String audiIp;
	private String audiNombre;
	private String audiRol;
	private String audiDetalle;

	@Override
	public AuditoriaDTO modeloAdto(Auditoria modelo) {
		AuditoriaDTO dto = new AuditoriaDTO();
		dto.setAudiAudi(modelo.getAudiAudi());
		dto.setAudiDescripcion(modelo.getAudiDescripcion());
		dto.setAudiFecha(modelo.getAudiFecha());
		dto.setAudiFuncionalidad(modelo.getAudiFuncionalidad());
		dto.setAudiIp(modelo.getAudiIp());
		dto.setAudiNombre(modelo.getAudiNombre());
		dto.setAudiUsuario(modelo.getAudiUsuario());
		dto.setAudiRol(modelo.getAudiRol());
		dto.setAudiDetalle(modelo.getAudiDetalle());
		return dto;
	}

	@Override
	public Auditoria dtoAModelo(AuditoriaDTO dto) {
		Auditoria auditoria = new Auditoria();
		auditoria.setAudiAudi(dto.getAudiAudi());
		auditoria.setAudiDescripcion(dto.getAudiDescripcion());
		auditoria.setAudiFecha(dto.getAudiFecha());
		auditoria.setAudiFuncionalidad(dto.getAudiFuncionalidad());
		auditoria.setAudiIp(dto.getAudiIp());
		auditoria.setAudiNombre(dto.getAudiNombre());
		auditoria.setAudiUsuario(dto.getAudiUsuario());
		auditoria.setAudiRol(dto.getAudiRol());
		auditoria.setAudiDetalle(dto.getAudiDetalle());
		return auditoria;

	}
}
