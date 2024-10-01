package co.com.periferia.alfa.core.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleEdicionPolizaDto {

	private Integer idDetalle;
	private BigDecimal prima;
	private BigDecimal valorTributo;

}
