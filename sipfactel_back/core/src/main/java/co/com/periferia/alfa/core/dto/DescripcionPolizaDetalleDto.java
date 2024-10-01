package co.com.periferia.alfa.core.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DescripcionPolizaDetalleDto {

	private Integer idPoliza;
	private String poliza;
	private String ramo;
	private BigDecimal prima;
	private BigDecimal valorTributo;
	private List<DetalleEdicionPolizaDto> detalle;
	
}
