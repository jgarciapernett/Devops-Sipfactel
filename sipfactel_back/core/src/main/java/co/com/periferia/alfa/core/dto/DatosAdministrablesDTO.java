package co.com.periferia.alfa.core.dto;

import java.util.Date;
import java.util.List;

import co.com.periferia.alfa.core.model.DatosAdministrablesModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosAdministrablesDTO implements IBaseDTO<DatosAdministrablesDTO, DatosAdministrablesModel> {

	private Integer vad;
	private String nombreRazonSocial;
	private String nombreComercial;
	private Integer telefono;
	private String direccionFisica;
	private String direccionFiscal;
	private String correo;
	private Integer codPais;
	private Integer codDepartamento;
	private Integer codCiudad;
	private String contacto;
	private String areaPertenece;
	private Integer facturadorElectronico;
	private Integer codTipoTributo;
	private Integer codRegimen;
	private Integer post;
	private Date fcreacion;
	private String ucreacion;
	private Date factualizacion;
	private String uactualizacion;
	private String estado;
	private List<DesplegablesDTO> obligacionesfiscales;

	@Override
	public DatosAdministrablesDTO modeloAdto(DatosAdministrablesModel modelo) {
		DatosAdministrablesDTO dto = new DatosAdministrablesDTO();

		dto.setVad(modelo.getVad());
		dto.setNombreRazonSocial(modelo.getNombreRazonSocial());
		dto.setNombreComercial(modelo.getNombreComercial());
		dto.setTelefono(modelo.getTelefono());
		dto.setDireccionFisica(modelo.getDireccionFisica());
		dto.setDireccionFiscal(modelo.getDireccionFiscal());
		dto.setCorreo(modelo.getCorreo());
		dto.setCodPais(modelo.getCodPais());
		dto.setCodDepartamento(modelo.getCodDepartamento());
		dto.setCodCiudad(modelo.getCodCiudad());
		dto.setContacto(modelo.getContacto());
		dto.setAreaPertenece(modelo.getAreaPertenece());
		dto.setFacturadorElectronico(modelo.getFacturadorElectronico());
		dto.setCodTipoTributo(modelo.getCodTipoTributo());
		dto.setCodRegimen(modelo.getCodRegimen());
		dto.setPost(modelo.getPost());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());
		dto.setFactualizacion(modelo.getFactualizacion());
		dto.setUactualizacion(modelo.getUactualizacion());
		dto.setEstado(modelo.getEstado());
		return dto;
	}

	@Override
	public DatosAdministrablesModel dtoAModelo(DatosAdministrablesDTO dto) {
		DatosAdministrablesModel modelo = new DatosAdministrablesModel();
		
		modelo.setVad(dto.getVad());
		modelo.setNombreRazonSocial(dto.getNombreRazonSocial());
		modelo.setNombreComercial(dto.getNombreComercial());
		modelo.setTelefono(dto.getTelefono());
		modelo.setDireccionFisica(dto.getDireccionFisica());
		modelo.setDireccionFiscal(dto.getDireccionFiscal());
		modelo.setCorreo(dto.getCorreo());
		modelo.setCodPais(dto.getCodPais());
		modelo.setCodDepartamento(dto.getCodDepartamento());
		modelo.setCodCiudad(dto.getCodCiudad());
		modelo.setContacto(dto.getContacto());
		modelo.setAreaPertenece(dto.getAreaPertenece());
		modelo.setFacturadorElectronico(dto.getFacturadorElectronico());
		modelo.setCodTipoTributo(dto.getCodTipoTributo());
		modelo.setCodRegimen(dto.getCodRegimen());
		modelo.setPost(dto.getPost());
		modelo.setFcreacion(dto.getFcreacion());
		modelo.setUcreacion(dto.getUcreacion());
		modelo.setFactualizacion(dto.getFactualizacion());
		modelo.setUactualizacion(dto.getUactualizacion());
		modelo.setEstado(dto.getEstado());
		return modelo;
	}
	
	public DatosAdministrablesDTO modeloAdtolist (DatosAdministrablesModel modelo, List<DesplegablesDTO> obl) {
		
		DatosAdministrablesDTO dto = new DatosAdministrablesDTO();
		dto.setVad(modelo.getVad());
		dto.setNombreRazonSocial(modelo.getNombreRazonSocial());
		dto.setNombreComercial(modelo.getNombreComercial());
		dto.setTelefono(modelo.getTelefono());
		dto.setDireccionFisica(modelo.getDireccionFisica());
		dto.setDireccionFiscal(modelo.getDireccionFiscal());
		dto.setCorreo(modelo.getCorreo());
		dto.setCodPais(modelo.getCodPais());
		dto.setCodDepartamento(modelo.getCodDepartamento());
		dto.setCodCiudad(modelo.getCodCiudad());
		dto.setContacto(modelo.getContacto());
		dto.setAreaPertenece(modelo.getAreaPertenece());
		dto.setFacturadorElectronico(modelo.getFacturadorElectronico());
		dto.setCodTipoTributo(modelo.getCodTipoTributo());
		dto.setCodRegimen(modelo.getCodRegimen());
		dto.setPost(modelo.getPost());
		dto.setFcreacion(modelo.getFcreacion());
		dto.setUcreacion(modelo.getUcreacion());
		dto.setFactualizacion(modelo.getFactualizacion());
		dto.setUactualizacion(modelo.getUactualizacion());
		dto.setEstado(modelo.getEstado());
		dto.setObligacionesfiscales(obl);
		return dto;
	}
	
	public boolean comparar(DatosAdministrablesDTO obj) {
		
		return this.nombreRazonSocial.equals(obj.getNombreRazonSocial())
		&& this.nombreComercial.equals(obj.getNombreComercial())
		&& this.telefono.equals(obj.getTelefono())
		&& this.direccionFisica.equals(obj.getDireccionFisica())
		&& this.direccionFiscal.equals(obj.getDireccionFiscal())
		&& this.correo.equals(obj.getCorreo())
		&& this.codPais.equals(obj.getCodPais())
		&& this.codDepartamento.equals(obj.getCodDepartamento())
		&& this.codCiudad.equals(obj.getCodCiudad())
		&& this.contacto.equals(obj.getContacto())
		&& this.areaPertenece.equals(obj.getAreaPertenece())
		&& this.facturadorElectronico.equals(obj.getFacturadorElectronico())
		&& this.codTipoTributo.equals(obj.getCodTipoTributo())
		&& this.codRegimen.equals(obj.getCodRegimen())
		&& this.post.equals(obj.getPost())
		&& this.fcreacion.equals(obj.getFcreacion())
		&& this.ucreacion.equals(obj.getUcreacion())
		&& this.factualizacion.equals(obj.getFactualizacion())
		&& this.estado.equals(obj.getEstado())
		&& this.obligacionesfiscales.equals(obj.getObligacionesfiscales())
		&& this.uactualizacion.equals(obj.getUactualizacion());
	}

}
