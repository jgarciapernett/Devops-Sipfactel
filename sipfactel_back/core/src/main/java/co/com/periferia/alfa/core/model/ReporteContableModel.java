package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ReporteContableModel {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "Compania")
	private String compania;

	@Column(name = "Fecha")
	private String fecha;
	
	@Column(name = "FechaTransaccion")
	private String fechaTransaccion;

	@Column(name = "Hora")
	private String hora;

	@Column(name = "TipoTransaccion")
	private String tipoTransaccion;

	@Column(name = "NumTransaccion")
	private String numTransaccion;

	@Column(name = "NumReferencia")
	private String numReferencia;

	@Column(name = "NumPoliza")
	private String numPoliza;

	@Column(name = "valorPrima")
	private Double valorPrima;

	@Column(name = "valorIva")
	private Double valorIva;

	@Column(name = "Total")
	private Double total;
	
	@Column(name = "tipoDocumento")
	private String tipoDocumento;
	
	@Column(name = "numeroIdentificacion")
	private String numeroIdentificacion;
	
	@Column(name = "razonSocial")
	private String razonSocial;
	
	@Column(name = "sucursal")
	private String sucursal;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "mensaje")
	private String mensaje;

}
