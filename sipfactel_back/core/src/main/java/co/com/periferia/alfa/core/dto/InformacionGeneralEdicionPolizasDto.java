package co.com.periferia.alfa.core.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class InformacionGeneralEdicionPolizasDto {

	private Integer idDocumento;
	private char tipo;
	private String numeroDocumento;
	private BigDecimal vbaImpuesto;
	private BigDecimal baseImponible;
	private BigDecimal valorTributo;
	private Integer idPorcentaje;
	private Integer porcentaje;
	private BigDecimal total;
	private List<DescripcionPolizaDetalleDto> polizas;
	private Boolean reenviar;
	
}
