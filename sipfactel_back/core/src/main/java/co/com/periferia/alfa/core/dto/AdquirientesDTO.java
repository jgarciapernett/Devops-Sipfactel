package co.com.periferia.alfa.core.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import co.com.periferia.alfa.core.model.AdquirientesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdquirientesDTO implements IBaseDTO<AdquirientesDTO, AdquirientesModel>{

	private Integer per;
	private BigInteger codtipopersona;	
	private String nombrerazonsocial;
	private String nombrecomercial;
	private Integer codtipoidentificacion;
	private String numidentificacion;
	private String dv;
	private String telefono;
	private String direccion;
	private String direccionfiscal;
	private String correo;
	private Integer codpais;
	private Integer coddepartamento;
	private Integer codciudad;
	private String contacto;
	private String areapertenece;
	private Integer facturadorelectronico;
	private Integer codenviofe;
	private Integer recibirxml;
	private Integer deshabilitado;
	private Integer codtipotributo;
	private Date fechainsercion;
	private Date fechaactualizacion;
	private String usuario;
	private Integer codRegimen;
	private Integer codPostal;
	private List<DesplegablesDTO> obligacionesfiscales;
	private Integer calidadcod;

	@Override
	public AdquirientesDTO modeloAdto(AdquirientesModel modelo) {
		
		AdquirientesDTO dto= new AdquirientesDTO();
		dto.setPer(modelo.getPer());
		dto.setCodtipopersona(modelo.getCodtipopersona());
		dto.setNombrerazonsocial(modelo.getNombrerazonsocial());
		dto.setNombrecomercial(modelo.getNombrecomercial());
		dto.setCodtipoidentificacion(modelo.getCodtipoidentificacion());
		dto.setNumidentificacion(modelo.getNumidentificacion());
		dto.setTelefono(modelo.getTelefono());
		dto.setDireccion(modelo.getDireccion());
		dto.setDireccionfiscal(modelo.getDireccionfiscal());
		dto.setCorreo(modelo.getCorreo());
		dto.setCodpais(modelo.getCodpais());
		dto.setCoddepartamento(modelo.getCoddepartamento());
		dto.setCodciudad(modelo.getCodciudad());
		dto.setContacto(modelo.getContacto());
		dto.setAreapertenece(modelo.getAreapertenece());
		dto.setFacturadorelectronico(modelo.getFacturadorelectronico());
		dto.setCodenviofe(modelo.getCodenviofe());
		dto.setRecibirxml(modelo.getRecibirxml());
		dto.setDeshabilitado(modelo.getDeshabilitado());
		dto.setCodtipotributo(modelo.getCodtipotributo());
		dto.setFechainsercion(modelo.getFechainsercion());
		dto.setFechaactualizacion(modelo.getFechaactualizacion());
		dto.setUsuario(modelo.getUsuario());
		dto.setCodRegimen(modelo.getCodRegimen());
		dto.setCodPostal(modelo.getCodPostal());
		dto.setCalidadcod(modelo.getCalidadcod());
		return dto;
	}
	
	public AdquirientesDTO modeloAdtolist(AdquirientesModel modelo, List<DesplegablesDTO> obl) {
		
		AdquirientesDTO dto= new AdquirientesDTO();
		dto.setPer(modelo.getPer());
		dto.setCodtipopersona(modelo.getCodtipopersona());
		dto.setNombrerazonsocial(modelo.getNombrerazonsocial());
		dto.setNombrecomercial(modelo.getNombrecomercial());
		dto.setCodtipoidentificacion(modelo.getCodtipoidentificacion());
		dto.setNumidentificacion(modelo.getNumidentificacion());
		dto.setTelefono(modelo.getTelefono());
		dto.setDireccion(modelo.getDireccion());
		dto.setDireccionfiscal(modelo.getDireccionfiscal());
		dto.setCorreo(modelo.getCorreo());
		dto.setCodpais(modelo.getCodpais());
		dto.setCoddepartamento(modelo.getCoddepartamento());
		dto.setCodciudad(modelo.getCodciudad());
		dto.setContacto(modelo.getContacto());
		dto.setAreapertenece(modelo.getAreapertenece());
		dto.setFacturadorelectronico(modelo.getFacturadorelectronico());
		dto.setCodenviofe(modelo.getCodenviofe());
		dto.setRecibirxml(modelo.getRecibirxml());
		dto.setDeshabilitado(modelo.getDeshabilitado());
		dto.setCodtipotributo(modelo.getCodtipotributo());
		dto.setFechainsercion(modelo.getFechainsercion());
		dto.setFechaactualizacion(modelo.getFechaactualizacion());
		dto.setUsuario(modelo.getUsuario());
		dto.setCodRegimen(modelo.getCodRegimen());
		dto.setCodPostal(modelo.getCodPostal());
		dto.setObligacionesfiscales(obl);
		return dto;
	}
	
	@Override
	public AdquirientesModel dtoAModelo(AdquirientesDTO dto) {
		
		AdquirientesModel modelo= new AdquirientesModel();
		modelo.setPer(dto.getPer());
		modelo.setCodtipopersona(dto.getCodtipopersona());
		modelo.setNombrerazonsocial(dto.getNombrerazonsocial());
		modelo.setNombrecomercial(dto.getNombrecomercial());
		modelo.setCodtipoidentificacion(dto.getCodtipoidentificacion());
		modelo.setNumidentificacion(dto.getNumidentificacion());
		modelo.setTelefono(dto.getTelefono());
		modelo.setDireccion(dto.getDireccion());
		modelo.setDireccionfiscal(dto.getDireccionfiscal());
		modelo.setCorreo(dto.getCorreo());
		modelo.setCodpais(dto.getCodpais());
		modelo.setCoddepartamento(dto.getCoddepartamento());
		modelo.setCodciudad(dto.getCodciudad());
		modelo.setContacto(dto.getContacto());
		modelo.setAreapertenece(dto.getAreapertenece());
		modelo.setFacturadorelectronico(dto.getFacturadorelectronico());
		modelo.setCodenviofe(dto.getCodenviofe());
		modelo.setRecibirxml(dto.getRecibirxml());
		modelo.setDeshabilitado(dto.getDeshabilitado());
		modelo.setCodtipotributo(dto.getCodtipotributo());
		modelo.setFechainsercion(dto.getFechainsercion());
		modelo.setFechaactualizacion(dto.getFechaactualizacion());
		modelo.setUsuario(dto.getUsuario());
		modelo.setCodRegimen(dto.getCodRegimen());
		modelo.setCodPostal(dto.getCodPostal());
		modelo.setCalidadcod(dto.getCalidadcod());
		return modelo;
	}
	
}
