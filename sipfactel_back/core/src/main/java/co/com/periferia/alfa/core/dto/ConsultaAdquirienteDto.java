package co.com.periferia.alfa.core.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ConsultaAdquirienteDto {

	private Integer dni;
	private String tipoPersona;
	private String razonSocial;
	private String nit;
	private String tipoDocumento;
	private List<DesplegablesDTO> obligacionesfiscales;
	private Integer pais;
	private Integer ciudad;
	private Integer departamento;
	private String direccion;
	private Integer codigoPostal;
	private String nombreContacto;
	private String correo;
	private String telefono;
	private String areaPertenece;
	private Integer facturadorElectronico;
	private Integer autorizaEntregaEmail;
	private Integer recibeXml;
	private Integer deshabilitado;
	private Integer tributo;
	private Integer regimen;
	private Date fechaActualizacion;
	private Boolean reenviar;
	
}
