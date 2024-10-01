package co.com.periferia.alfa.core.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_facturas")
public class FacturasModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_facturas_seq")
	@SequenceGenerator(name = "tbl_facturas_seq", sequenceName = "tbl_facturas_seq", allocationSize = 1)
	@NotNull
	@Column(name = "FACT_FACT")
	private Integer id;
	@Column(name = "FACT_COD_UBL")
	private Integer ubl;
	@Column(name = "FACT_COD_TIPOOPERACION")
	private Integer codtipooperacion;
	@Column(name = "FACT_COD_VERSIONFORMATO")
	private Integer versionformato;
	@Column(name = "FACT_COD_AMBIENTE")
	private Integer codambiente;
	@Column(name = "FACT_NUMDOC", length = 100)
	private String numdoc;
	@Column(name = "FACT_CUFE", length = 100)
	private String cufe;
	@Column(name = "FACT_FECHAEMISION")
	private Date fechaemision;
	@Column(name = "FACT_HORAEMISION")
	private Timestamp horaemision;
	@Column(name = "FACT_COD_TIPODOCUMENTO")
	private Integer codtipodocumento;
	@Column(name = "FACT_COD_MONEDA")
	private Integer codmoneda;
	@Column(name = "FACT_CANT_LINEASDETALLE")
	private Integer cantlineasdetalle;
	@Column(name = "FACT_COD_ESTADO")
	private Integer codestado;
	@Column(name = "FACT_VALORBRUTOANTESIMPUESTO")
	private BigDecimal valorbrutoantesimpuesto;
	@Column(name = "FACT_BASEIMPONIBLE")
	private BigDecimal baseimponible;
	@Column(name = "FACT_VALORBRUTOMASIMPUESTO")
	private BigDecimal valorbrutomasimpuesto;
	@Column(name = "FACT_TOTALFACTURA")
	private BigDecimal totalfactura;
	@Column(name = "FACT_LOG_LOG")
	private Integer log;
	@Column(name = "FACT_VALORTRIBUTO")
	private BigDecimal valorTributo;
	@Column(name = "FACT_COD_TIPOTRIBUTO")
	private Integer tipoTributo;
	@Column(name = "FACT_VALORTRIBUTO1")
	private BigDecimal valorTributo1;
	@Column(name = "FACT_PORC_PORC1")
	private Integer porcPorc1;
	@Column(name = "FACT_CALIDADCOD")
	private Integer calidadCod;
	@Column(name = "FACT_CALIDADMENSAJE")
	private String calidadMensaje;
	@Column(name = "FACT_ENDOSO")
	private String endoso;
	@Column(name = "FACT_NUMEROPOLIZA")
	private String numeroPoliza;
	@Column(name = "FACT_PER_PER")
	private Integer persona;
	@Column(name = "FACT_SUC_SUC")
	private Integer sucursal;
	@Column(name = "FACT_TIPOPOLIZA")
	private String tipoPoliza;
	@Column(name = "FACT_FECHAVIGENCIA")
	private LocalDate fechaVigencia;
	@Column(name = "FACT_MODULO")
	private String modulo;
	@Column(name = "FACT_OBSERVACION")
	private String observacion;
	@Column(name = "FACT_COMP_COMP")
	private BigDecimal compania;
	@Column(name = "FACT_FECHORA_EMISION")
	private String fechaHorEmision;
	@Column(name = "FACT_ERROR_1260")
	private String error1260;
	                                                                       
        

}
