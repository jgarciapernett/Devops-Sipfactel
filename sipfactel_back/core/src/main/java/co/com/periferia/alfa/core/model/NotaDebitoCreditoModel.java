package co.com.periferia.alfa.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_notas_debitocredito")
public class NotaDebitoCreditoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_notas_debitocredito_seq")
	@SequenceGenerator(name = "tbl_notas_debitocredito_seq", sequenceName = "tbl_notas_debitocredito_seq", allocationSize = 1)
	@Column(name = "not_not")
	private Integer id; 
	@Column(name = "NOT_COD_UBL")                   
	private Integer notCodUbl; 
	@Column(name = "NOT_COD_TIPOOPERACION")                 
	private Integer notCodTipoOperacion;   
	@Column(name = "NOT_COD_VERSIONFORMATO")     
	private Integer notCodVersionFormato;
	@Column(name = "NOT_COD_AMBIENTE")       
	private Integer notCodAmbiente;             
	@Column(name = "not_numdoc")
	private String numdoc;                 
	@Column(name = "not_cufe")
	private String cufe;                     
	@Column(name = "NOT_FECHAEMISION")
	private LocalDate notFchaEmision;            
	@Column(name = "NOT_HORAEMISION")
	private String notHoraEmision;        
	@Column(name = "NOT_COD_MONEDA")
	private Integer notCodMoneda;               
	@Column(name = "NOT_CANT_LINEASDETALLE")
	private Integer notCantLineasDetalle;       
	@Column(name = "NOT_COD_TIPODOCUMENTO")
	private Integer tipoDoucmento;        
	@Column(name = "NOT_FACT_FACT")
	private Integer factura;                
	@Column(name = "NOT_LOG_LOG")
	private Integer log;                  
	@Column(name = "not_cod_estado")
	private Integer estado;  
	@Column(name = "NOT_VALORBRUTOANTESIMPUESTO")            
	private BigDecimal vbaImpuesto;  
	@Column(name = "NOT_BASEIMPONIBLE")
	private BigDecimal baseImponible;            
	@Column(name = "NOT_VALORBRUTOMASIMPUESTO")
	private BigDecimal vbmImpuesto;    
	@Column(name = "NOT_TOTAL")
	private BigDecimal total;                    
	@Column(name = "NOT_VALORTRIBUTO")
	private BigDecimal valorTributo;             
	@Column(name = "NOT_COD_TIPOTRIBUTO")
	private Integer tipoTributo;          
	@Column(name = "NOT_VALORTRIBUTO1")
	private BigDecimal valorTributo1;            
	@Column(name = "NOT_PORC_PORC1")
	private Integer porcPorc;               
	@Column(name = "NOT_CALIDADCOD")
	private Integer calidadCod;               
	@Column(name = "NOT_CALIDADMENSAJE")
	private String calidadMensaje;         
	@Column(name = "NOT_FECHAVIGENCIA")
	private LocalDate fechaVigencia;            
	@Column(name = "NOT_TIPOPOLIZA")
	private String tipoPoliza;           
	@Column(name = "NOT_NUMEROPOLIZA")
	private String numeroPoliza;             
	@Column(name = "NOT_MODULO")
	private String modulo;                   
	@Column(name = "NOT_ENDOSO")
	private String endoso;                
	@Column(name = "POL_SUC_SUC")
	private Integer sucursalPoliza;                  
	@Column(name = "POL_COMP_COMP")
	private Integer companiaPoliza;                
	@Column(name = "NOT_SUC_SUC")
	private Integer sucursal;                  
	@Column(name = "NOT_COMP_COMP")
	private Integer compania;                
	@Column(name = "NOT_PER_PER")
	private Integer persona;                  
	@Column(name = "not_fechora_emision")
	private String fechoraEmision;
	@Column(name = "NOT_ERROR_1260 ")
	private String error1260;
	

}
