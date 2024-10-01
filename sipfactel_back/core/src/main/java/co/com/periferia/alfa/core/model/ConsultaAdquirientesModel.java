package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ConsultaAdquirientesModel {

	@Id
	private Integer dni;
	@Column(name = "tipo_persona")
	private String tipoPersona;
	@Column(name = "razon_social")
	private String razonSocial;
	private String identificacion;
	@Column(name = "tipo_identificacion")
	private String tipoIdentificacion;
	private Integer pais;
	private Integer ciudad;
	private Integer departamento;
	private String direccion;
	@Column(name = "codigo_postal")
	private Integer codigoPostal;
	private String contacto;
	private String correo;
	private String telefono;
	@Column(name = "area_pertenece")
	private String areaPertenece;
	@Column(name = "facturador_electronico")
	private Integer facturadorElectronico;
	@Column(name = "autoriza_entrega")
	private Integer autorizaEntregaEmail;
	@Column(name = "recibe_xml")
	private Integer recibeXml;
	private Integer deshabilitado;
	private Integer tributo;
	private Integer regimen;
	
}
