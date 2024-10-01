package co.com.periferia.alfa.core.services.impl;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.AdquirientesModel;
import co.com.periferia.alfa.core.model.DetalleErrorModel;
import co.com.periferia.alfa.core.model.DetalleFacModel;
import co.com.periferia.alfa.core.model.DetalleNotasModel;
import co.com.periferia.alfa.core.model.FacturasModel;
import co.com.periferia.alfa.core.model.NotaDebitoCreditoModel;
import co.com.periferia.alfa.core.model.PolizasModel;
import co.com.periferia.alfa.core.repository.AdquirientesRepository;
import co.com.periferia.alfa.core.repository.DetalleErrorRepository;
import co.com.periferia.alfa.core.repository.DetalleFacturasRepository;
import co.com.periferia.alfa.core.repository.DetalleNotasRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.PolizasRepository;
import co.com.periferia.alfa.core.services.ErrorEtlService;

@Component
public class ErrorEtlServiceImpl implements ErrorEtlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorEtlServiceImpl.class);

	@Autowired
	UtilExcecion utilException;

	@Autowired
	DetalleErrorRepository detalleRepository;

	@Autowired
	AdquirientesRepository adquirientesRepository;

	@Autowired
	DetalleFacturasRepository detalleFacRepository;

	@Autowired
	FacturasRepository facturasRepository;

	@Autowired
	NotaDebitoCreditoRepository notasRepository;

	@Autowired
	DetalleNotasRepository detalleNotasRepository;

	@Autowired
	PolizasRepository polizasRepository;

	@Override
	public String error(Integer iderror, Integer idtabla, String tabla, Integer errorid, String usuario)
			throws ExcepcionSegAlfa {
		boolean flag;
		try {
			LOGGER.info("Clase errorEtl - Parametros |id error: {} |id tabla: {} | error id: {}  | tabla: {}  | usuario: {}", 
					iderror, idtabla, errorid, tabla, usuario);
			switch (tabla) {
				case "tbl_Personas":
					flag = personas(idtabla, errorid);
					if (flag) {
						detalleError(iderror, usuario);
					}
					break;
				case "tbl_Detalle_Facturas":
					flag = detalleFactura(idtabla, errorid);
					if (flag) {
						detalleError(iderror, usuario);
					}
					break;
	
				case "tbl_Facturas":
					flag = factura(idtabla, errorid);
					if (flag) {
						detalleError(iderror, usuario);
					}
					break;
	
				case "tbl_Notas_Debitocredito":
				case "tbl_Detalle_Notas_Debitocredito":
					flag = notas(idtabla, errorid);
					if (flag) {
						detalleError(iderror, usuario);
					}
					break;
	
				case "tbl_Polizas":
					flag = poliza(errorid, iderror, idtabla);
					if (flag) {
						detalleError(iderror, usuario);
					}
					break;
				default:
					break;
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar metodo error");
			throw utilException.getById(9).crearExcepcion();
		}
		return "Se realizo la remediacion con exito!";
	}

	@Override
	public boolean personas(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa {
		AdquirientesModel resp = new AdquirientesModel();
		try {
			LOGGER.info("Clase errorEtl/personas - Parametros |id tabla: {} | error id: {}", idtabla, errorid);
			resp = adquirientesRepository.getOne(idtabla);
			String[] error = resp.getCalidadmensaje().split(";");
			for (String mensaje : error) {
				boolean flag = mensaje.matches("(.*)" + errorid + "(.*)");
				if (flag) {
					String error1 = resp.getCalidadmensaje().replaceAll(mensaje, "");
					resp.setCalidadmensaje(error1);
					adquirientesRepository.save(resp);
					return true;
				}
			}
			return false;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar metodo personas");
			throw utilException.getById(9).crearExcepcion();
		}
	}

	@Override
	public boolean detalleFactura(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa {
		DetalleFacModel resp = new DetalleFacModel();
		try {
			LOGGER.info("Clase errorEtl/detalle factura - Parametros |id tabla: {} | error id: {}", idtabla, errorid);
			resp = detalleFacRepository.getOne(idtabla);
			String[] error = resp.getCalidadmensaje().split(";");
			for (String mensaje : error) {
				boolean flag = mensaje.matches("(.*)" + errorid + "(.*)");
				if (flag) {
					String error1 = resp.getCalidadmensaje().replaceAll(mensaje, "");
					resp.setCalidadmensaje(error1);
					detalleFacRepository.save(resp);
					return true;
				}
			}
			return false;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar detalle factura");
			throw utilException.getById(9).crearExcepcion();
		}
	}

	@Override
	public boolean factura(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa {
		FacturasModel resp = new FacturasModel();
		try {
			LOGGER.info("Clase errorEtl/factura - Parametros |id tabla: {} | error id: {}", idtabla, errorid);
			resp = facturasRepository.getOne(idtabla);
			String[] error = resp.getCalidadMensaje().split(";");
			for (String mensaje : error) {
				boolean flag = mensaje.matches("(.*)" + errorid + "(.*)");
				if (flag) {
					String error1 = resp.getCalidadMensaje().replaceAll(mensaje, "");
					resp.setCalidadMensaje(error1);
					facturasRepository.save(resp);
					return true;
				}
			}
			return false;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar factura");
			throw utilException.getById(9).crearExcepcion();
		}
	}

	@Override
	public boolean notas(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa {
		NotaDebitoCreditoModel resp = new NotaDebitoCreditoModel();
		try {
			LOGGER.info("Clase errorEtl/notas - Parametros |id tabla: {} | error id: {}", idtabla, errorid);
			resp = notasRepository.getOne(idtabla);
			String[] error = resp.getCalidadMensaje().split(";");
			for (String mensaje : error) {
				boolean flag = mensaje.matches("(.*)" + errorid + "(.*)");
				if (flag) {
					String error1 = resp.getCalidadMensaje().replaceAll(mensaje, "");
					resp.setCalidadMensaje(error1);
					notasRepository.save(resp);
					return true;
				}
			}
			return false;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar notas");
			throw utilException.getById(9).crearExcepcion();
		}
	}

	@Override
	public boolean detalleNotas(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa {
		DetalleNotasModel resp = new DetalleNotasModel();
		try {
			LOGGER.info("Clase errorEtl/detalleNotas - Parametros |id tabla: {} | error id: {}", idtabla, errorid);
			resp = detalleNotasRepository.getOne(idtabla);
			String[] error = resp.getCalidadmensaje().split(";");
			for (String mensaje : error) {
				boolean flag = mensaje.matches("(.*)" + errorid + "(.*)");
				if (flag) {
					String error1 = resp.getCalidadmensaje().replaceAll(mensaje, "");
					resp.setCalidadmensaje(error1);
					detalleNotasRepository.save(resp);
					return true;
				}
			}
			return false;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar detalle notas");
			throw utilException.getById(9).crearExcepcion();
		}
	}

	@Override
	public void detalleError(Integer iderror, String usuario) throws ExcepcionSegAlfa {
		DetalleErrorModel resp1 = new DetalleErrorModel();
		try {
			LOGGER.info("Clase errorEtl/detalleError - Parametros |usuario: {} | id error:  {}", usuario, iderror);
			resp1 = detalleRepository.getOne(iderror);
			resp1.setCodigo(null);
			resp1.setFchactualizacion(new Date());
			resp1.setUsaactualizacion(usuario);
			// cambio
			resp1.setMensaje(resp1.getMensaje());
			detalleRepository.save(resp1);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar detalle error");
			throw utilException.getById(9).crearExcepcion();
		}
	}

	@Override
	public String categoria(Integer iderror, String categoria, String observacion, String usuario)
			throws ExcepcionSegAlfa {
		DetalleErrorModel resp1 = new DetalleErrorModel();
		try {
			LOGGER.info("Clase errorEtl/categoria - Parametros |usuario: {} | id error: {} |categoria: {} |observacion: {}", 
					usuario, iderror, categoria, observacion);
			resp1 = detalleRepository.getOne(iderror);
			resp1.setCategoria(categoria);
			resp1.setObservacion(observacion);
			resp1.setFchactualizacion(new Date());
			resp1.setUsaactualizacion(usuario);
			detalleRepository.save(resp1);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar categoria");
			throw utilException.getById(9).crearExcepcion();
		}
		return "Se realizo la categorizaci\u00f3n con exito!";
	}

	// ----------------------------------polizas
	// impl-------------------------------------

	@Override
	public boolean poliza(Integer errorid, Integer iderror, Integer idtabla) throws ExcepcionSegAlfa {
		PolizasModel resp1 = new PolizasModel();
		try {
			LOGGER.info("Clase errorEtl/poliza - Parametros |error id: {} | id error: {} |id tabla: {}", 
					errorid, iderror, idtabla);
			resp1 = polizasRepository.getOne(idtabla);
			String[] error = resp1.getCalidadmensaje().split(";");
			for (String mensaje : error) {
				boolean flag = mensaje.matches("(.*)" + errorid + "(.*)");
				if (flag) {
					String error1 = resp1.getCalidadmensaje().replaceAll(mensaje, "");
					resp1.setCalidadmensaje(error1);
					polizasRepository.save(resp1);
					return true;
				}
			}
			return false;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar poliza");
			throw utilException.getById(9).crearExcepcion();
		}
	}

}
