package co.com.periferia.alfa.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_detalle_notas_debitocredito")
public class DetalleNotasModel {
	
	@Id
	@Column(name = "detnot_detnot")
	private Integer id; 
	@Column(name = "DETNOT_NUMLINEA")             
	private Integer numLinea;
	@Column(name = "DETNOT_CANTIDAD")             
	private Integer cantidad;             
	@Column(name = "DETNOT_COD_UNIDADMEDIDA")
	private Integer unidadMedida;     
	@Column(name = "DETNOT_VALORBRUTO")
	private BigDecimal valorBruto;           
	@Column(name = "DETNOT_VALORSERVICIO")
	private BigDecimal valorServicio;        
	@Column(name = "DETNOT_CANTIDADREAL")
	private Double cantidadReal;         
	@Column(name = "DETNOT_DESCRIPCIONSERVICIO")
	private String descripcionServicio;  
	@Column(name = "DETNOT_COD_RAMO")
	private Integer codRamo;             
	@Column(name = "DETNOT_COD_ESTANDAR")
	private Integer codEstandar;         
	@Column(name = "DETNOT_NOT_NOT")
	private Integer nota;              
	@Column(name = "DETNOT_LOG_LOG")
	private Integer log;              
	@Column(name = "DETNOT_FECHAEMISION")
	private String fechaEmision;         
	@Column(name = "DETNOT_VALORTRIBUTO")
	private BigDecimal valorTributo;         
	@Column(name = "DETNOT_COD_TIPOTRIBUTO")
	private Integer tipoTributo;      
	@Column(name = "DETNOT_BASEIMPONIBLE")
	private BigDecimal baseImponible ;       
	@Column(name = "DETNOT_VALORTRIBUTO1")
	private BigDecimal valorTributo1;        
	@Column(name = "DETNOT_PORC_PORC")
	private Integer porcPorc;            
	@Column(name = "DETNOT_CALIDADCOD")
	private Integer calidadCod;           
	@Column(name = "detnot_calidadmensaje")
	private String calidadmensaje;      
	@Column(name = "DETNOT_TIPOPOLIZA")
	private String tipoPoliza;          

}
