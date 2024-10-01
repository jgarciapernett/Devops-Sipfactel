package co.com.periferia.alfa.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResolucionFacturasDto {

	private Integer llave;
    private Integer sucursal;
    private Integer compania;
    private String numeroResolucion;
    private String prefijo;
    private String fechaInicial;
    private String fechaFinal;
    private Integer numeroInicial;
    private Integer numeroFinal;
    private String estado;
    private Integer trTipoId;
    private boolean vigencia;
    private String usuario;
    private Integer contador;
    private String producto;
	
}
