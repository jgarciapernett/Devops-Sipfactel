package co.com.periferia.alfa.core.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import co.com.periferia.alfa.core.model.PolizasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolizasDTO implements IBaseDTO<PolizasDTO, PolizasModel>{

	private Integer id;
	private Integer per;
	private Date fechaemision;
	private Timestamp horaexpedicion;
	private BigDecimal prima;
	private BigDecimal primatotal;
	private String endoso;
	private Integer numeroramo;
	private String numeropoliza;
	private Integer tasa;
	private Integer fuente;
	private Date fechainsercion;
	private Date fechaactualizacion;
	private String usuario;
	private Integer sucu;
	private Integer comp;
	private Integer log;
	private String movimiento;
	private String modulo;
	private Date fechavigencia;
	private String tipopoliza;
	private Integer calidadcod;
	private String calidadmensaje;
	private Integer fact;
	private Integer not;

	@Override
	public PolizasDTO modeloAdto(PolizasModel modelo) {
		
		PolizasDTO dto= new PolizasDTO();
		dto.setId(modelo.getId());
		dto.setPer(modelo.getPer());
		dto.setFechaemision(modelo.getFechaemision());
		dto.setHoraexpedicion(modelo.getHoraexpedicion());
		dto.setPrima(modelo.getPrima());
		dto.setPrimatotal(modelo.getPrimatotal());
		dto.setNumeroramo(modelo.getNumeroramo());
		dto.setNumeropoliza(modelo.getNumeropoliza());
		dto.setTasa(modelo.getTasa());
		dto.setFuente(modelo.getFuente());
		dto.setFechainsercion(modelo.getFechainsercion());
		dto.setFechaactualizacion(modelo.getFechaactualizacion());
		dto.setUsuario(modelo.getUsuario());
		dto.setSucu(modelo.getSucu());
		dto.setComp(modelo.getComp());
		dto.setLog(modelo.getLog());
		dto.setMovimiento(modelo.getMovimiento());
		dto.setModulo(modelo.getModulo());
		dto.setFechavigencia(modelo.getFechavigencia());
		dto.setTipopoliza(modelo.getTipopoliza());
		dto.setCalidadmensaje(modelo.getCalidadmensaje());
		dto.setFact(modelo.getFact());
		dto.setNot(modelo.getNot());
		return dto;
	}

	@Override
	public PolizasModel dtoAModelo(PolizasDTO dto) {

		PolizasModel modelo = new PolizasModel();
		modelo.setId(dto.getId());
		modelo.setPer(dto.getPer());
		modelo.setFechaemision(dto.getFechaemision());
		modelo.setHoraexpedicion(dto.getHoraexpedicion());
		modelo.setPrima(dto.getPrima());
		modelo.setPrimatotal(dto.getPrimatotal());
		modelo.setNumeroramo(dto.getNumeroramo());
		modelo.setNumeropoliza(dto.getNumeropoliza());
		modelo.setTasa(dto.getTasa());
		modelo.setFuente(dto.getFuente());
		modelo.setFechainsercion(dto.getFechainsercion());
		modelo.setFechaactualizacion(dto.getFechaactualizacion());
		modelo.setUsuario(dto.getUsuario());
		modelo.setSucu(dto.getSucu());
		modelo.setComp(dto.getComp());
		modelo.setLog(dto.getLog());
		modelo.setMovimiento(dto.getMovimiento());
		modelo.setModulo(dto.getModulo());
		modelo.setFechavigencia(dto.getFechavigencia());
		modelo.setTipopoliza(dto.getTipopoliza());
		modelo.setCalidadmensaje(dto.getCalidadmensaje());
		modelo.setFact(dto.getFact());
		modelo.setNot(dto.getNot());
		return modelo;
	}
	
	public boolean comparar(PolizasDTO obj) {
		return 
		this.prima.equals(obj.getPrima())
		&& this.primatotal.equals(obj.getPrimatotal());
	}

		
}
