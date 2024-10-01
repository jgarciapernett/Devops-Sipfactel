package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.DatosProveedorModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosProveedorDTO implements IBaseDTO<DatosProveedorDTO, DatosProveedorModel>{

	private Integer proveedor;
	private Integer codPais;
	private String nitprovtecno;
	private String dvprovtecno;
	private String softwareid;
	private String softseccode;
	private String nitdian;
	private String digveridian;
	private String qrcode;
	private String versbaseubl;
	private String versformato;
	private String cufe;
	private String cantlineas;
	private String estado;
	private Date fcreacion;
	private String ucreacion;	

	@Override
	public DatosProveedorDTO modeloAdto(DatosProveedorModel modelo) {
		DatosProveedorDTO dto =new DatosProveedorDTO();
		
		dto.setProveedor(modelo.getProveedor());
		dto.setCodPais(modelo.getCodPais());
		dto.setNitprovtecno(modelo.getNitprovtecno());
		dto.setDvprovtecno(modelo.getDvprovtecno());
		dto.setSoftwareid(modelo.getSoftwareid());
		dto.setSoftseccode(modelo.getSoftseccode());
		dto.setNitdian(modelo.getNitdian());
		dto.setDigveridian(modelo.getDigveridian());
		dto.setQrcode(modelo.getQrcode());
		dto.setVersbaseubl(modelo.getVersbaseubl());
		dto.setVersformato(modelo.getVersformato());
		dto.setCufe(modelo.getCufe());
		dto.setCantlineas(modelo.getCantlineas());
		dto.setEstado(modelo.getEstado());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());
		
		return dto;
	}

	@Override
	public DatosProveedorModel dtoAModelo(DatosProveedorDTO dto) {		
		DatosProveedorModel modelo = new DatosProveedorModel();
		
		modelo.setProveedor(dto.getProveedor());
		modelo.setCodPais(dto.getCodPais());
		modelo.setNitprovtecno(dto.getNitprovtecno());
		modelo.setDvprovtecno(dto.getDvprovtecno());
		modelo.setSoftwareid(dto.getSoftwareid());
		modelo.setSoftseccode(dto.getSoftseccode());
		modelo.setNitdian(dto.getNitdian());
		modelo.setDigveridian(dto.getDigveridian());
		modelo.setQrcode(dto.getQrcode());
		modelo.setVersbaseubl(dto.getVersbaseubl());
		modelo.setVersformato(dto.getVersformato());
		modelo.setCufe(dto.getCufe());
		modelo.setCantlineas(dto.getCantlineas());
		modelo.setEstado(dto.getEstado());
		modelo.setFcreacion(dto.getFcreacion());
		modelo.setUcreacion(dto.getUcreacion());
		return modelo;
	}

}
