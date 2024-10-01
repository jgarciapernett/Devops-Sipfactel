package co.com.periferia.alfa.core.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import co.com.periferia.alfa.core.model.FacturasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacturasDTO implements IBaseDTO<FacturasDTO, FacturasModel>{

	private Integer id;
	private Integer ubl;
	private Integer codtipooperacion;
	private Integer versionformato;
	private Integer codambiente;
	private String numdoc;
	private String cufe;
	private Date fechaemision;
	private Timestamp horaemision;
	private Integer codtipodocumento;
	private Integer codmoneda;
	private Integer cantlineasdetalle;
	private Integer codestado;
	private BigDecimal valorbrutoantesimpuesto;
	private BigDecimal baseimponible;
	private BigDecimal valorbrutomasimpuesto;
	private BigDecimal totalfactura;
	private Integer pol;
	private String fechaHorEmision;

	@Override
	public FacturasDTO modeloAdto(FacturasModel modelo) {

		FacturasDTO dto = new FacturasDTO();
		dto.setId(modelo.getId());
		dto.setCodtipooperacion(modelo.getCodtipooperacion());
		dto.setCodambiente(modelo.getCodambiente());
		dto.setNumdoc(modelo.getNumdoc());
		dto.setCufe(modelo.getCufe());
		dto.setFechaemision(modelo.getFechaemision());
		dto.setHoraemision(modelo.getHoraemision());
		dto.setCodtipodocumento(modelo.getCodtipodocumento());
		dto.setCodmoneda(modelo.getCodmoneda());
		dto.setCantlineasdetalle(modelo.getCantlineasdetalle());
		dto.setCodestado(modelo.getCodestado());
		dto.setValorbrutoantesimpuesto(modelo.getValorbrutoantesimpuesto());
		dto.setBaseimponible(modelo.getBaseimponible());
		dto.setValorbrutomasimpuesto(modelo.getValorbrutomasimpuesto());
		dto.setTotalfactura(modelo.getTotalfactura());
		
		return dto;
	}

	@Override
	public FacturasModel dtoAModelo(FacturasDTO dto) {
		
		FacturasModel modelo = new FacturasModel();
		modelo.setId(dto.getId());
		modelo.setCodtipooperacion(dto.getCodtipooperacion());
		modelo.setCodambiente(dto.getCodambiente());
		modelo.setNumdoc(dto.getNumdoc());
		modelo.setCufe(dto.getCufe());
		modelo.setFechaemision(dto.getFechaemision());
		modelo.setHoraemision(dto.getHoraemision());
		modelo.setCodtipodocumento(dto.getCodtipodocumento());
		modelo.setCodmoneda(dto.getCodmoneda());
		modelo.setCantlineasdetalle(dto.getCantlineasdetalle());
		modelo.setCodestado(dto.getCodestado());
		modelo.setValorbrutoantesimpuesto(dto.getValorbrutoantesimpuesto());
		modelo.setBaseimponible(dto.getBaseimponible());
		modelo.setValorbrutomasimpuesto(dto.getValorbrutomasimpuesto());
		modelo.setTotalfactura(dto.getTotalfactura());
		
		return modelo;
	}
}
