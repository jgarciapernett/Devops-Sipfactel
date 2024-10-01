package co.com.periferia.alfa.core.dto;

import co.com.periferia.alfa.core.model.FacturasModel;
import co.com.periferia.alfa.core.model.NotaDebitoCreditoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaEstadoDto {

	private FacturasModel factura;
	private NotaDebitoCreditoModel nota;
	private Integer trid;
	private String cufe;
	private String nit;
	private boolean tipo;
	
	public ConsultaEstadoDto() {}

	public ConsultaEstadoDto(Integer trid, FacturasModel factura, NotaDebitoCreditoModel nota, String cufe, String nit, boolean tipo) {
		this.trid = trid;
		this.factura = factura;
		this.nota = nota;
		this.cufe = cufe;
		this.nit = nit;
		this.tipo = tipo;
	}
}
