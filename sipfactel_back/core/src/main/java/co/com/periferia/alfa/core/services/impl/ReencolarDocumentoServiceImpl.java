package co.com.periferia.alfa.core.services.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.repository.AdquirientesRepository;
import co.com.periferia.alfa.core.repository.DetalleFacturasRepository;
import co.com.periferia.alfa.core.repository.DetalleNotasRepository;
import co.com.periferia.alfa.core.repository.FacturaDelcopRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.PolizasRepository;
import co.com.periferia.alfa.core.services.IReencolarDocumentoService;

@Service
public class ReencolarDocumentoServiceImpl implements IReencolarDocumentoService {

	private static final Logger LOG = LoggerFactory.getLogger(ReencolarDocumentoServiceImpl.class);
	
	@Autowired
	private FacturasRepository facturaRepo;
	
	@Autowired
	private NotaDebitoCreditoRepository notasRepo;
	
	@Autowired
	private AdquirientesRepository adquirienteRepo;
	
	@Autowired
	private DetalleFacturasRepository detalleFacturaRepo;
	
	@Autowired
	private DetalleNotasRepository detalleNotasRepo;
	
	@Autowired
	private PolizasRepository polizasRepo;
	
	@Autowired
	private FacturaDelcopRepository facturaDelcopRepository;
	
	@Override
	public boolean reencolarDocumento(Integer documento, String tipo, Integer adquiriente) {
		LOG.info("Inicio habilitacion de documento: {} | tipo: {} ", documento, tipo);
		try {
			updateDocuments(documento, tipo, adquiriente);
			facturaDelcopRepository.numeracionGeneral();
			return true;
		} catch (Exception e) {
			LOG.error("Error al re-encolar documento | {} ", e.getMessage());
			return false;
		}
	}
	
	@Transactional
	private void updateDocuments(Integer documento, String tipo, Integer adquiriente) {
		adquirienteRepo.calidad1Adquiriente(adquiriente);
		if(tipo.equals("Factura de Venta Nacional")) {
			facturaRepo.encolarFacturaPorId(documento);
			detalleFacturaRepo.calidad1Detalle(documento);
			polizasRepo.encolarFacturaPorId(documento);
		} else {
			notasRepo.encolarNotaPorId(documento);
			detalleNotasRepo.calidad1Detalle(documento);
			polizasRepo.encolarNotaPorId(documento);
		}
	}

}
