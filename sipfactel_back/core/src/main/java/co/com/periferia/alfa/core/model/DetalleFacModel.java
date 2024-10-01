package co.com.periferia.alfa.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_detalle_factura")
public class DetalleFacModel {

	@Id
	@Column(name = "detfac_detfac")
	private Integer id;              
	@Column(name = "DETFAC_NUMLINEA")
	private Integer numLinea;            
	@Column(name = "DETFAC_CANTIDAD")
	private Integer cantidad;            
	@Column(name = "DETFAC_COD_UNIDADMEDIDA")
	private Integer unidadMedida;    
	@Column(name = "DETFAC_VALORBRUTO")
	private BigDecimal valorBruto;           
	@Column(name = "DETFAC_VALORSERVICIO")
	private BigDecimal valorServicio;        
	@Column(name = "DETFAC_CANTIDADREAL")
	private Double cantidadReal;         
	@Column(name = "DETFAC_DESCRIPCIONSERVICIO")
	private String descripcionServicio;  
	@Column(name = "DETFAC_COD_RAMO")
	private Integer ramo;            
	@Column(name = "DETFAC_COD_ESTANDAR")
	private Integer codEstandar;        
	@Column(name = "DETFAC_FACT_FACT")
	private Integer factura;           
	@Column(name = "DETFAC_LOG_LOG")
	private Integer log;
	@Column(name = "DETFAC_FECHAEMISION")             
	private LocalDate fechaEmision;         
	@Column(name = "DETFAC_VALORTRIBUTO")
	private BigDecimal valorTributo;         
	@Column(name = "DETFAC_COD_TIPOTRIBUTO")
	private Integer tipoTributo;     
	@Column(name = "DETFAC_BASEIMPONIBLE")
	private BigDecimal baseImponible;        
	@Column(name = "DETFAC_VALORTRIBUTO1")
	private BigDecimal valorTributo1;        
	@Column(name = "DETFAC_PORC_PORC")
	private Integer porcPorc; 
	@Column(name = "DETFAC_CALIDADCOD")          
	private Integer calidadCod;          
	@Column(name = "detfac_calidadmensaje")
	private String calidadmensaje; 
	@Column(name = "DETFAC_TIPOPOLIZA")      
	private String tipoPoliza;           
	
}
