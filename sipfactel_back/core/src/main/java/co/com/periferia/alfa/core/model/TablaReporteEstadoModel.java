package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TablaReporteEstadoModel {

	@Id
	@Column(name = "Id")
	private String id;

	@Column(name = "Compania")
	private String compania;

	@Column(name = "Sucursal")
	private String sucursal;

	@Column(name = "Fecha")
	private String fecha;

	@Column(name = "Hora")
	private String hora;

	@Column(name = "Estado")
	private String estado;

	@Column(name = "NumPoliza")
	private String numPoliza;

	@Column(name = "Adquiriente")
	private String adquiriente;

	@Column(name = "TipoTransaccion")
	private String tipoTransaccion;

	@Column(name = "NumTransaccion")
	private String numTransaccion;

	@Column(name = "NumReferencia")
	private String numReferencia;

	@Column(name = "valorPrima")
	private Double valorPrima;

	@Column(name = "valorIva")
	private Double valorIva;

	@Column(name = "Total")
	private Double total;

	@Column(name = "Mensaje")
	private String mensaje;
	
	@Column(name = "ErrorNumeracion")
	private String errorNumeracion;

}
