package co.com.periferia.alfa.core.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.DetalleAdquirientesDTO;
import co.com.periferia.alfa.core.dto.DetalleFacturasDTO;
import co.com.periferia.alfa.core.dto.DetalleTransaccionDTO;
import co.com.periferia.alfa.core.dto.PolizaErrorEtlDTO;
import co.com.periferia.alfa.core.dto.PolizasDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.FacturasModel;
import co.com.periferia.alfa.core.model.JsonEnvioModel;
import co.com.periferia.alfa.core.model.NotaDebitoCreditoModel;
import co.com.periferia.alfa.core.model.PolizasModel;
import co.com.periferia.alfa.core.model.RespDelcopModel;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.JsonEnvioRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.PolizasRepository;
import co.com.periferia.alfa.core.repository.RespDelcopRepository;
import co.com.periferia.alfa.core.repository.TablaPolizasRepository;
import co.com.periferia.alfa.core.services.CreacionJsonNotasService;
import co.com.periferia.alfa.core.services.CreacionJsonService;
import co.com.periferia.alfa.core.services.IPolizasService;

@Component
public class PolizasServiceImpl implements IPolizasService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PolizasServiceImpl.class);

	@Autowired
	private PolizasRepository polizasrepositorio;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	TablaPolizasRepository tablaPolizasRepository;

	@Autowired
	FacturasRepository facturasRepository;

	@Autowired
	CreacionJsonService json;

	@Autowired
	NotaDebitoCreditoRepository notaDebitoCreditoRepository;

	@Autowired
	CreacionJsonNotasService creacionJsonNotasService;

	@Autowired
	JsonEnvioRepository emitirRepository;

	@Autowired
	RespDelcopRepository respDelcopRepository;

	@Override
	public String editar(PolizasDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("Se editan registros de la tabla");
		boolean resp;
		PolizasModel resultado = new PolizasModel();
		PolizasDTO resultado2 = new PolizasDTO();
		try {
			LOGGER.info("Ejecutando editar - Parametros |dto |id: {}", id);
			resultado = polizasrepositorio.getOne(id);
			resultado2 = resultado2.modeloAdto(resultado);
			resp = dto.comparar(resultado2);
			dto.setPer(resultado2.getPer());
			dto.setFechainsercion(resultado2.getFechainsercion());
			dto.setFechaactualizacion(new Date());
			if (!resp) {
				polizasrepositorio.save(dto.dtoAModelo(dto));
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar editar");
			throw utilException.getById(8).crearExcepcion();
		}
		return "El registro fue actualizado correctamente";
	}

	@Override
	public String agregar(PolizasDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("Se agrega cambios a la tabla y crea nuevo registro con los cambios");
		boolean resp;
		PolizasModel resultado = new PolizasModel();
		PolizasDTO resultado2 = new PolizasDTO();
		try {
			LOGGER.info("Ejecutando agregar - Parametros |dto |id: {}", id);
			resultado = polizasrepositorio.getOne(id);
			resultado2 = resultado2.modeloAdto(resultado);
			resp = dto.comparar(resultado2);
			if (!resp) {
				resultado.setFechaactualizacion(new Date());
				polizasrepositorio.save(resultado);
				resultado = dto.dtoAModelo(dto);
				resultado.setFechainsercion(resultado2.getFechainsercion());
				polizasrepositorio.save(resultado);
				return "El registro se edito correctamente";
			} else {
				LOGGER.error("Error al ejecutar agregar");
				return "No se guardo el registro porque no se hizo ningun cambio";
			}

		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			throw utilException.getById(7).crearExcepcion();
		}
	}

	@Override
	public List<PolizaErrorEtlDTO> filtroPolizasErrorEtl(String numpoliza, String numdoc, Integer categoria)
			throws ExcepcionSegAlfa {
		List<PolizaErrorEtlDTO> result = new ArrayList<>();
		try {
			LOGGER.info("Ejecutando FiltroPolizasErrorEtl - Parametros |numdoc: {} |numero poliza: {} |categoria: {}",
					numdoc, numpoliza, categoria);
			result = tablaPolizasRepository.getFiltroPolizasErrorEtl(numpoliza, numdoc, categoria);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar FiltroPolizasErrorEtl");
			throw utilException.getById(6).crearExcepcion();
		}
		return result;
	}

	@Override
	public DetalleAdquirientesDTO detalleAdquiriente(String numpoliza, String tipoMovimiento) throws ExcepcionSegAlfa {
		DetalleAdquirientesDTO result = new DetalleAdquirientesDTO();
		try {
			LOGGER.info("Ejecutando detalleAdquiriente- Parametros |tipo movimiento: {}  |numero poliza: {}",
					tipoMovimiento, numpoliza);
			result = tablaPolizasRepository.getFiltroAdqurientes(numpoliza, tipoMovimiento);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar detalleAdquiriente");
			throw utilException.getById(6).crearExcepcion();
		}
		return result;
	}

	@Override
	public DetalleFacturasDTO detalleFacturaEtl(String numpoliza, Integer idtabla, String tabla)
			throws ExcepcionSegAlfa {
		DetalleFacturasDTO result = new DetalleFacturasDTO();
		try {
			LOGGER.info("Ejecutando detalleFacturaEtl- Parametros | tabla: {} |id tabla: {} |numero poliza: {}",
					tabla, idtabla, numpoliza);
			result = tablaPolizasRepository.getDetalleFacturaEtl(numpoliza, idtabla, tabla);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar detalleFacturaEtl");
			throw utilException.getById(6).crearExcepcion();
		}
		return result;
	}

	@Override
	public DetalleTransaccionDTO detalleTransaccion(Integer dniDocumento, String tipo) throws ExcepcionSegAlfa {
		List<Object[]> consulta = null;
		final int tipoDocumento = 0;
		final int numeroDocumento = 1;
		final int transaccionid = 2;
		final int mensaje = 3;

		try {
			if(tipo.equals("Factura de Venta Nacional"))
				consulta = polizasrepositorio.consultaDetalleTransaccionFactura(dniDocumento);
			else 
				consulta = polizasrepositorio.consultaDetalleTransaccionNota(dniDocumento);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar detalleTransaccion");
			throw utilException.getById(6).crearExcepcion();
		}
		return new DetalleTransaccionDTO(
				consulta.get(0)[tipoDocumento].toString(),
				validarNull(consulta.get(0)[numeroDocumento]),
				validarNull(consulta.get(0)[transaccionid]),
				validarNull(consulta.get(0)[mensaje]));
	}
	
	private String validarNull(Object data) {
		return data == null ? "" : data.toString();
	}

	@Override
	public String editarDetalleErrorEtl(DetalleFacturasDTO dto) throws ExcepcionSegAlfa {
		try {
			LOGGER.info("Ejecutando editarDetalleErrorEtl- Parametros | dto");
			tablaPolizasRepository.editarDetalleOrErrorEtl(false, dto);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar editarDetalleErrorEtl");
			throw utilException.getById(6).crearExcepcion();
		}
		return "El registro se guardo correctamente";
	}

	@Override
	public String reenviar(String numdoc, String user, String tipodoc) throws ExcepcionSegAlfa, IOException {
		try {
			LOGGER.info("Ejecutando reenviar- Parametros |numdoc: {} |usuario: {} |tipo documento: {}",
					numdoc, user, tipodoc);
			tipodoc = tipodoc.toLowerCase().replace('�', 'a').replace('�', 'e').replace('�', 'i').replace('�', 'o')
					.replace('�', 'u');
			if (tipodoc.equalsIgnoreCase("nota credito")) {
				NotaDebitoCreditoModel notas = notaDebitoCreditoRepository.findByDebitoCredito(numdoc);
				notas.setEstado(1258);
				notaDebitoCreditoRepository.save(notas);
				creacionJsonNotasService.emitirNotasCred(false, numdoc, user);
			} else if (tipodoc.equalsIgnoreCase("nota debito")) {
				NotaDebitoCreditoModel notas = notaDebitoCreditoRepository.findByDebitoCredito(numdoc);
				notas.setEstado(1258);
				notaDebitoCreditoRepository.save(notas);
				creacionJsonNotasService.emitirNotasDeb(false, numdoc, user);
			} else {
				FacturasModel factura = facturasRepository.findByFacturas(numdoc);
				factura.setCodestado(1258);
				facturasRepository.save(factura);
				json.emitirFacturas(0, numdoc, user);
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar reenviar");
			throw utilException.getById(6).crearExcepcion();
		}
		return "La factura se envio correctamente";
	}

	@Override
	public String buscarPorPoliza(String numeropoliza, Integer errorid) throws ExcepcionSegAlfa {
		List<PolizasModel> resp = new ArrayList<>();
		try {
			LOGGER.info("Ejecutando buscarPorPoliza- Parametros |numero poliza: {} | error id: {}",
					numeropoliza, errorid);
			resp = polizasrepositorio.findByPoliza(numeropoliza);
			for (PolizasModel polizasModel : resp) {
				String[] error = polizasModel.getCalidadmensaje().split(";");
				for (String mensaje : error) {
					boolean flag = mensaje.matches("(.*)" + errorid + "(.*)");
					if (flag) {
						String error1 = polizasModel.getCalidadmensaje().replaceAll(mensaje, "");
						polizasModel.setCalidadmensaje(error1);
						polizasrepositorio.save(polizasModel);
						LOGGER.info("polizasModel CalidadMensaje ---> {}", error1);
					}
				}

			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar buscarPorPoliza");
			throw utilException.getById(6).crearExcepcion();
		}
		return "Se realizo el cambio con exito";
	}

	@Override
	public String recategorizar(Integer idtabla, String tabla, Integer categoria, String observacion)
			throws ExcepcionSegAlfa {
		JsonEnvioModel resp = new JsonEnvioModel();
		RespDelcopModel resp1 = new RespDelcopModel();
		try {
			LOGGER.info("Ejecutando recategorizar- Parametros |id tabla: {} | tabla: {} | categoria: {} | observacion: {} ", idtabla, tabla, categoria, observacion);
			switch (tabla) {
			case "TBL_JSON_ENVIO":
				resp = emitirRepository.getOne(idtabla);
				resp.setCategoria(categoria);
				resp.setObservacion(observacion);
				emitirRepository.save(resp);
				break;

			case "TBL_RESPUESTA_DELCOP":
				resp1 = respDelcopRepository.getOne(idtabla);
				resp1.setCategoria(categoria);
				resp1.setObservacion(observacion);
				respDelcopRepository.save(resp1);
				break;
			default:
				break;
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar recategorizar");
			throw utilException.getById(9).crearExcepcion();
		}
		return "Se realizo la categorizaci\u00f3n con exito!";
	}

}
