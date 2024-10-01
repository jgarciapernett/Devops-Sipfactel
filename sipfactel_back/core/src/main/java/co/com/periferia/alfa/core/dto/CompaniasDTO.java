package co.com.periferia.alfa.core.dto;

import java.util.Date;
import java.util.List;

import co.com.periferia.alfa.core.model.CompaniasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompaniasDTO implements IBaseDTO<CompaniasDTO, CompaniasModel>{

	private Integer comp;
    private String nombre;
    private String codascele;
	private Integer tipodocumento;
	private Integer codigopais;
	private List<DesplegablesDTO> obligacionesfiscales;
	private String razonsocial;
	private String codpoint;
	private String documento;
    private String nombrepais;
	private Integer tipopersona;
	private Integer facelectronica;
	private String notacredito;
	private String notadebito;
	private String codempresa;
    private Integer codestandar;
	private Integer codatributo;
	private String nomatributo;
	private Integer porcatributo;
	private String cantidad;	
	private Integer formapago;
	private Integer metodopago;
	private Integer undmedida;
	private String estado;
	private Date fcreacion;
	private String ucreacion;
	private String notadebh;
	private String notacreh;
	private Integer regimen;

	@Override
	public CompaniasDTO modeloAdto(CompaniasModel modelo) {
		
		CompaniasDTO dto = new CompaniasDTO();
		dto.setComp(modelo.getComp());
		dto.setNombre(modelo.getNombre());
		dto.setCodascele(modelo.getCodascele());
		dto.setTipodocumento(modelo.getTipodocumento());
		dto.setCodigopais(modelo.getCodigopais());
		dto.setRazonsocial(modelo.getRazonsocial());
		dto.setCodpoint(modelo.getCodpoint());
		dto.setDocumento(modelo.getDocumento());
		dto.setNombrepais(modelo.getNombrepais());
		dto.setTipopersona(modelo.getTipopersona());
		dto.setFacelectronica(modelo.getFacelectronica());
		dto.setNotacredito(modelo.getNotacredito());
		dto.setNotadebito(modelo.getNotadebito());
		dto.setCodempresa(modelo.getCodempresa());
		dto.setCodestandar(modelo.getCodestandar());
		dto.setCodatributo(modelo.getCodatributo());
		dto.setNomatributo(modelo.getNomatributo());
		dto.setPorcatributo(modelo.getPorcatributo());
		dto.setCantidad(modelo.getCantidad());
		dto.setFormapago(modelo.getFormapago());
		dto.setMetodopago(modelo.getMetodopago());
		dto.setUndmedida(modelo.getUndmedida());
		dto.setEstado(modelo.getEstado());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());	
		dto.setNotadebh(modelo.getNotadebh());
		dto.setNotacreh(modelo.getNotacreh());
		dto.setRegimen(modelo.getRegimen());
		return dto;
		
	}
	
	public CompaniasDTO modeloAdtolist(CompaniasModel modelo, List<DesplegablesDTO> obl) {
		
		CompaniasDTO dto = new CompaniasDTO();
		dto.setComp(modelo.getComp());
		dto.setNombre(modelo.getNombre());
		dto.setCodascele(modelo.getCodascele());
		dto.setTipodocumento(modelo.getTipodocumento());
		dto.setCodigopais(modelo.getCodigopais());
		dto.setRazonsocial(modelo.getRazonsocial());
		dto.setCodpoint(modelo.getCodpoint());
		dto.setDocumento(modelo.getDocumento());
		dto.setNombrepais(modelo.getNombrepais());
		dto.setTipopersona(modelo.getTipopersona());
		dto.setFacelectronica(modelo.getFacelectronica());
		dto.setNotacredito(modelo.getNotacredito());
		dto.setNotadebito(modelo.getNotadebito());
		dto.setCodempresa(modelo.getCodempresa());
		dto.setCodestandar(modelo.getCodestandar());
		dto.setCodatributo(modelo.getCodatributo());
		dto.setNomatributo(modelo.getNomatributo());
		dto.setPorcatributo(modelo.getPorcatributo());
		dto.setCantidad(modelo.getCantidad());
		dto.setFormapago(modelo.getFormapago());
		dto.setMetodopago(modelo.getMetodopago());
		dto.setUndmedida(modelo.getUndmedida());
		dto.setEstado(modelo.getEstado());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());	
		dto.setNotadebh(modelo.getNotadebh());
		dto.setNotacreh(modelo.getNotacreh());
		dto.setRegimen(modelo.getRegimen());
		dto.setObligacionesfiscales(obl);
		return dto;
		
	}

	@Override
	public CompaniasModel dtoAModelo(CompaniasDTO dto) {
		
		CompaniasModel modelo =new CompaniasModel();
		modelo.setComp(dto.getComp());
		modelo.setNombre(dto.getNombre());
		modelo.setCodascele(dto.getCodascele());
		modelo.setTipodocumento(dto.getTipodocumento());
		modelo.setCodigopais(dto.getCodigopais());
		modelo.setRazonsocial(dto.getRazonsocial());
		modelo.setCodpoint(dto.getCodpoint());
		modelo.setDocumento(dto.getDocumento());
		modelo.setNombrepais(dto.getNombrepais());
		modelo.setTipopersona(dto.getTipopersona());
		modelo.setFacelectronica(dto.getFacelectronica());
		modelo.setNotacredito(dto.getNotacredito());
		modelo.setNotadebito(dto.getNotadebito());
		modelo.setCodempresa(dto.getCodempresa());
		modelo.setCodestandar(dto.getCodestandar());
		modelo.setCodatributo(dto.getCodatributo());
		modelo.setNomatributo(dto.getNomatributo());
		modelo.setPorcatributo(dto.getPorcatributo());
		modelo.setCantidad(dto.getCantidad());
		modelo.setFormapago(dto.getFormapago());
		modelo.setMetodopago(dto.getMetodopago());
		modelo.setUndmedida(dto.getUndmedida());
		modelo.setEstado(dto.getEstado());
		modelo.setFcreacion(dto.getFcreacion());
		modelo.setUcreacion(dto.getUcreacion());
		modelo.setNotadebh(dto.getNotadebh());
		modelo.setNotacreh(dto.getNotacreh());
		modelo.setRegimen(dto.getRegimen());
		return modelo;
	}

	public boolean comparar(CompaniasDTO obj) {
	
		return this.nombre.equals(obj.getNombre()) 
		&& this.codascele.equals(obj.getCodascele())
		&& this.tipodocumento.equals(obj.getTipodocumento())
		&& this.codigopais.equals(obj.getCodigopais())
		&& this.obligacionesfiscales.equals(obj.getObligacionesfiscales())
		&& this.razonsocial.equals(obj.getRazonsocial())
		&& this.codpoint.equals(obj.getCodpoint())
		&& this.documento.equals(obj.getDocumento())
		&& this.nombrepais.equals(obj.getNombrepais())
		&& this.tipopersona.equals(obj.getTipopersona())
		&& this.facelectronica.equals(obj.getFacelectronica())
		&& this.notacredito.equals(obj.getNotacredito())
		&& this.notadebito.equals(obj.getNotadebito())
		&& this.codempresa.equals(obj.getCodempresa())
		&& this.codestandar.equals(obj.getCodestandar())
		&& this.codatributo.equals(obj.getCodatributo())
		&& this.nomatributo.equals(obj.getNomatributo())
		&& this.porcatributo.equals(obj.getPorcatributo())
		&& this.cantidad.equals(obj.getCantidad())
		&& this.formapago.equals(obj.getFormapago())
		&& this.metodopago.equals(obj.getMetodopago())
		&& this.undmedida.equals(obj.getUndmedida())
		&& this.notadebh.equals(obj.getNotadebh())
		&& this.notacreh.equals(obj.getNotacreh())
		&& this.regimen.equals(obj.getRegimen());		
	}
}
