package co.com.periferia.alfa.core.services.impl;

import java.util.List;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.DetalleEdicionPolizaDto;
import co.com.periferia.alfa.core.dto.InformacionGeneralEdicionPolizasDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.DetalleFacModel;
import co.com.periferia.alfa.core.model.DetalleNotasModel;
import co.com.periferia.alfa.core.model.FacturasModel;
import co.com.periferia.alfa.core.model.NotaDebitoCreditoModel;
import co.com.periferia.alfa.core.model.PolizasModel;
import co.com.periferia.alfa.core.repository.DetalleFacturasRepository;
import co.com.periferia.alfa.core.repository.DetalleNotasRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.PolizasRepository;
import co.com.periferia.alfa.core.services.IEdicionDetallePolizaService;
import co.com.periferia.alfa.core.services.IReencolarDocumentoService;

@Service
public class EdicionDetallePolizaServiceImpl implements IEdicionDetallePolizaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EdicionDetallePolizaServiceImpl.class);
	
	@Autowired
	private PolizasRepository polizaRepository;
	
	@Autowired
	private FacturasRepository facturaRepository;
	
	@Autowired
	private NotaDebitoCreditoRepository notasRepository;
	
	@Autowired
	private DetalleNotasRepository detalleNotaRepository;
	
	@Autowired 
	private DetalleFacturasRepository detalleFacturaRepository;
	
	@Autowired
	UtilExcecion utilException;
	
	@Autowired
	private IReencolarDocumentoService reenviarDocumento;
	
	@Override
	@Transactional
	public String editarDetalle(InformacionGeneralEdicionPolizasDto dto, String tipo, Integer adquiriente) throws ExcepcionSegAlfa {
		LOGGER.info("Inicio edicion de detalle");
		try {
			if(dto.getTipo() == 'n') 
				editarNota(dto);
			else 
				editarFactura(dto);
			
			if(Boolean.TRUE.equals(dto.getReenviar())) 
				reenviarDocumento.reencolarDocumento(dto.getIdDocumento(), tipo, adquiriente);
		} catch (Exception ex) {
			LOGGER.error("Error al ejecutar editarDetalle | {} ", ex.getMessage());
			throw utilException.getById(6).crearExcepcion();
		}
		return "El registro se guardo correctamente";
	}
	
	private void editarNota(InformacionGeneralEdicionPolizasDto dto) {
		LOGGER.info("ingreso a editar nota");
		NotaDebitoCreditoModel nota = notasRepository.findNotaById(dto.getIdDocumento());
		nota.setVbaImpuesto(dto.getVbaImpuesto());
		nota.setBaseImponible(dto.getBaseImponible());
		nota.setVbmImpuesto(dto.getTotal());
		nota.setTotal(dto.getTotal());
		nota.setPorcPorc(dto.getIdPorcentaje());
		nota.setValorTributo(dto.getValorTributo());
		nota.setValorTributo1(dto.getValorTributo());
		notasRepository.save(nota);
		editarPoliza(dto, true);
	}
	
	private void editarFactura(InformacionGeneralEdicionPolizasDto dto) {
		LOGGER.info("ingreso a editar factura");
		FacturasModel factura = facturaRepository.findFacturaById(dto.getIdDocumento());
		factura.setValorbrutoantesimpuesto(dto.getVbaImpuesto());
		factura.setBaseimponible(dto.getBaseImponible());
		factura.setValorbrutomasimpuesto(dto.getTotal());
		factura.setTotalfactura(dto.getTotal());
		factura.setPorcPorc1(dto.getIdPorcentaje());
		factura.setValorTributo(dto.getValorTributo());
		factura.setValorTributo1(dto.getValorTributo());
		facturaRepository.save(factura);
		editarPoliza(dto, false);
	}
	
	private void editarPoliza(InformacionGeneralEdicionPolizasDto dto, boolean tipo) {
		dto.getPolizas().stream().forEach(p -> {
			PolizasModel poliza = polizaRepository.findPolizaById(p.getIdPoliza());
			poliza.setPrima(p.getPrima());
			poliza.setTasa(dto.getPorcentaje());
			poliza.setPrimatotal(p.getValorTributo());
			poliza.setIvaEmRep(p.getValorTributo());
			polizaRepository.save(poliza);
			if(tipo) 
				editarDetalleNota(p.getDetalle(), dto.getIdPorcentaje());
			else 
				editarDetalleFactura(p.getDetalle(), dto.getIdPorcentaje());
		});
	}
	
	private void editarDetalleNota(List<DetalleEdicionPolizaDto> dto, Integer porcPorc) {
		dto.stream().forEach(d -> {
			DetalleNotasModel detalle = detalleNotaRepository.findDetalleById(d.getIdDetalle());
			detalle.setValorBruto(d.getPrima());
			detalle.setValorServicio(d.getPrima());
			detalle.setBaseImponible(d.getPrima());
			detalle.setValorTributo(d.getValorTributo());
			detalle.setValorTributo1(d.getValorTributo());
			detalle.setPorcPorc(porcPorc);
			detalleNotaRepository.save(detalle);
		});
	}
	
	private void editarDetalleFactura(List<DetalleEdicionPolizaDto> dto, Integer porcPorc) {
		dto.stream().forEach(d -> {
			DetalleFacModel detalle = detalleFacturaRepository.findDetalleById(d.getIdDetalle());
			detalle.setValorBruto(d.getPrima());
			detalle.setValorServicio(d.getPrima());
			detalle.setBaseImponible(d.getPrima());
			detalle.setValorTributo(d.getValorTributo());
			detalle.setValorTributo1(d.getValorTributo());
			detalle.setPorcPorc(porcPorc);
			detalleFacturaRepository.save(detalle);
		});
	}

}
