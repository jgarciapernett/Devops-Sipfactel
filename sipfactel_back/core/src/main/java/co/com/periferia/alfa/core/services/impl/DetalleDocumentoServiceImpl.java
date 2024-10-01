package co.com.periferia.alfa.core.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.DescripcionPolizaDetalleDto;
import co.com.periferia.alfa.core.dto.DetalleEdicionPolizaDto;
import co.com.periferia.alfa.core.dto.InformacionGeneralEdicionPolizasDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.DetalleFacModel;
import co.com.periferia.alfa.core.model.DetalleNotasModel;
import co.com.periferia.alfa.core.repository.DetalleFacturasRepository;
import co.com.periferia.alfa.core.repository.DetalleNotasRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.PolizasRepository;
import co.com.periferia.alfa.core.services.IDetalleDocumentoService;

@Service
public class DetalleDocumentoServiceImpl implements IDetalleDocumentoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetalleDocumentoServiceImpl.class);
	
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
	
	@Override
	public InformacionGeneralEdicionPolizasDto detalleFactura(Integer documento, String tipo) throws ExcepcionSegAlfa {
		try {
			return buildDto(documento, tipo);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar detalleFactura");
			throw utilException.getById(6).crearExcepcion();
		}
	}
	
	private InformacionGeneralEdicionPolizasDto buildDto(Integer documento, String tipo) {
		if(tipo.equalsIgnoreCase("Factura de Venta Nacional")) 
			return buildFacturas(documento);
		
		return buildNotas(documento);
	}
	
	private InformacionGeneralEdicionPolizasDto buildNotas(Integer documento) {
		InformacionGeneralEdicionPolizasDto dto = new InformacionGeneralEdicionPolizasDto();
		List<Object[]> nota = notasRepository.findNotaByPoliza(documento);
		dto.setIdDocumento(Integer.parseInt(nota.get(0)[0].toString()));
		dto.setTipo('n');
		dto.setBaseImponible(new BigDecimal(nota.get(0)[1].toString()));
		dto.setNumeroDocumento(nota.get(0)[2] == null ? null : nota.get(0)[2].toString());
		dto.setTotal(new BigDecimal(nota.get(0)[3].toString()));
		dto.setValorTributo(new BigDecimal(nota.get(0)[4].toString()));
		dto.setVbaImpuesto(new BigDecimal(nota.get(0)[5].toString()));
		dto.setIdPorcentaje(Integer.parseInt(nota.get(0)[6].toString()));
		dto.setPorcentaje(Integer.parseInt(nota.get(0)[7].toString()));
		dto.setPolizas(buildPolizas(dto.getIdDocumento(), null));
		return dto;
	}
	
	private InformacionGeneralEdicionPolizasDto buildFacturas(Integer documento) {
		InformacionGeneralEdicionPolizasDto dto = new InformacionGeneralEdicionPolizasDto();
		List<Object[]> factura = facturaRepository.findFactByPoliza(documento);
		dto.setIdDocumento(Integer.parseInt(factura.get(0)[0].toString()));
		dto.setTipo('f');
		dto.setBaseImponible(new BigDecimal(factura.get(0)[1].toString()));
		dto.setNumeroDocumento(factura.get(0)[2] == null ? null : factura.get(0)[2].toString());
		dto.setTotal(new BigDecimal(factura.get(0)[3].toString()));
		dto.setValorTributo(new BigDecimal(factura.get(0)[4].toString()));
		dto.setVbaImpuesto(new BigDecimal(factura.get(0)[5].toString()));
		dto.setIdPorcentaje(Integer.parseInt(factura.get(0)[6].toString()));
		dto.setPorcentaje(Integer.parseInt(factura.get(0)[7].toString()));
		dto.setPolizas(buildPolizas(null, dto.getIdDocumento()));
		return dto;
	}
	
	private List<DescripcionPolizaDetalleDto> buildPolizas(Integer nota, Integer factura){
		List<Object[]> polizas = polizaRepository.findByNotaOrFactura(nota, factura);
		List<DescripcionPolizaDetalleDto> listDto = new ArrayList<>();
		polizas.stream().forEach(p -> {
			DescripcionPolizaDetalleDto dto = new DescripcionPolizaDetalleDto();
			dto.setIdPoliza(Integer.parseInt(p[0].toString()));
			dto.setPoliza(p[1].toString());
			dto.setPrima(new BigDecimal(p[2].toString()));
			dto.setRamo(p[4].toString());
			dto.setValorTributo(new BigDecimal(p[5].toString()));
			dto.setDetalle(factura == null ? buildDetalleNotas(nota, Integer.parseInt(p[3].toString())) : buildDetalleFacturas(factura, Integer.parseInt(p[3].toString())));
			listDto.add(dto);
		});
		return listDto;
	}
	
	private List<DetalleEdicionPolizaDto> buildDetalleNotas(Integer nota, Integer ramo){
		List<DetalleNotasModel> detalleList = detalleNotaRepository.findByRamoAndNota(nota, ramo);
		List<DetalleEdicionPolizaDto> listDto = new ArrayList<>();
		detalleList.stream().forEach(d -> {
			DetalleEdicionPolizaDto dto = new DetalleEdicionPolizaDto();
			dto.setIdDetalle(d.getId());
			dto.setValorTributo(d.getValorTributo());
			dto.setPrima(d.getBaseImponible());
			listDto.add(dto);
		});
		return listDto;
	}
	
	private List<DetalleEdicionPolizaDto> buildDetalleFacturas(Integer factura, Integer ramo){
		List<DetalleFacModel> detalleList = detalleFacturaRepository.findByRamoAndFactura(factura, ramo);
		List<DetalleEdicionPolizaDto> listDto = new ArrayList<>();
		detalleList.stream().forEach(d -> {
			DetalleEdicionPolizaDto dto = new DetalleEdicionPolizaDto();
			dto.setIdDetalle(d.getId());
			dto.setValorTributo(d.getValorTributo());
			dto.setPrima(d.getBaseImponible());
			listDto.add(dto);
		});
		return listDto;
	}

}
