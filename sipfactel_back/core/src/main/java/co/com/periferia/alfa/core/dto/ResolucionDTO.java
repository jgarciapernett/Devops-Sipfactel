package co.com.periferia.alfa.core.dto;

import java.util.Date;

import co.com.periferia.alfa.core.model.ResolucionModel;
import co.com.periferia.alfa.core.model.ResolucionNotasModel;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResolucionDTO implements IBaseDTO<ResolucionDTO, ResolucionModel>{

	private Integer res;
	private Integer sucursal;
	private Integer compania;
	private String fnresol;
	private String fprefijo;
	private String ffini;
	private String fffin;
	private Integer fnumini;
	private Integer fnumfin;
	private Integer cont;
	private String ndprefijo;
	private Integer ndnumini;
	private Integer ndnumfin;
	private String ncprefijo;
	private Integer ncnumini;
	private Integer ncnumfin;
	private Date fcreacion;
	private String ucreacion;
	private Date factualiza;
	private String uactualiza;
	private String estado;
	private Integer tidFactura;
	private Integer tidNCredito;
	private Integer tidNDebito;
	private String tipo;
	
	@Override
	public ResolucionDTO modeloAdto(ResolucionModel modelo) {
		ResolucionDTO dto = new ResolucionDTO();
		dto.setRes(modelo.getRes());
		dto.setSucursal(modelo.getSucursal());
		dto.setCompania(modelo.getCompania());
		dto.setFnresol(modelo.getFnresol());
		dto.setFprefijo(modelo.getFprefijo());
		dto.setFfini(modelo.getFfini().toString());
		dto.setFffin(modelo.getFffin().toString());
		dto.setFnumini(modelo.getFnumini());
		dto.setFnumfin(modelo.getFnumfin());
		dto.setCont(modelo.getFacturaCont());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());
		dto.setFactualiza(modelo.getFactualiza());
		dto.setUactualiza(modelo.getUactualiza());
		dto.setEstado(modelo.getEstado());
		dto.setTidFactura(modelo.getTidFactura());
		dto.setTipo(FacEnum.FACTURA.getValor());
		return dto;
	}
	
	public ResolucionDTO notasModeloAdto(ResolucionNotasModel modelo) {
		ResolucionDTO dto = new ResolucionDTO();
		dto.setRes(modelo.getDni());
		dto.setSucursal(modelo.getSucursal());
		dto.setCompania(modelo.getCompania());
		dto.setFprefijo(modelo.getPrefijo());
		dto.setFnumini(modelo.getNumeroInicial());
		dto.setFnumfin(modelo.getNumeroFinal());
		dto.setFcreacion(modelo.getFechaCreacion());
		dto.setUcreacion(modelo.getUsuarioCreacion());
		dto.setCont(modelo.getContador());
		dto.setEstado(modelo.getEstado());
		dto.setTidFactura(modelo.getTrTipoId());
		dto.setTipo(modelo.getTipoNota());
		return dto;
	}
	
	@Override
	public ResolucionModel dtoAModelo(ResolucionDTO dto) {
		ResolucionModel modelo = new ResolucionModel();
		modelo.setSucursal(dto.getSucursal());
		modelo.setCompania(dto.getCompania());
		modelo.setFnresol(dto.getFnresol());
		modelo.setFprefijo(dto.getFprefijo());
		modelo.setFnumini(dto.getFnumini());
		modelo.setFnumfin(dto.getFnumfin());
		modelo.setFcreacion(dto.getFcreacion());
		modelo.setUcreacion(dto.getUcreacion());
		modelo.setFactualiza(dto.getFactualiza());
		modelo.setUactualiza(dto.getUactualiza());
		modelo.setEstado(dto.getEstado());
		modelo.setTidFactura(dto.getTidFactura());
		return modelo;
	}
	
public boolean comparar(ResolucionDTO obj) {
		
		return this.sucursal.equals(obj.getSucursal())
		&& this.compania.equals(obj.getCompania())
		&& this.fnresol.equals(obj.getFnresol())
		&& this.fprefijo.equals(obj.getFprefijo())
		&& this.ffini.equals(obj.ffini)
		&& this.fffin.equals(obj.getFffin())
		&& this.fnumini.equals(obj.getFnumini())
		&& this.fnumfin.equals(obj.getFnumfin())
		&& this.ndprefijo.equals(obj.getNdprefijo())
		&& this.ndnumini.equals(obj.getNdnumini())
		&& this.ndnumfin.equals(obj.getNdnumfin())
		&& this.ncprefijo.equals(obj.getNcprefijo())
		&& this.ncnumini.equals(obj.getNcnumini())
		&& this.ncnumfin.equals(obj.getNcnumfin());		
	}
}
