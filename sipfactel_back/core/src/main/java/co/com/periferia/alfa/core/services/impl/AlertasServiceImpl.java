package co.com.periferia.alfa.core.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.AlertaDTO;
import co.com.periferia.alfa.core.dto.ResolucionDTO;
import co.com.periferia.alfa.core.model.CompaniasModel;
import co.com.periferia.alfa.core.model.ResolucionModel;
import co.com.periferia.alfa.core.model.ResolucionNotasModel;
import co.com.periferia.alfa.core.model.SucursalesModel;
import co.com.periferia.alfa.core.repository.CompaniasRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.repository.IResolucionNotasRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.ResolucionRepository;
import co.com.periferia.alfa.core.repository.SucursalesRepository;
import co.com.periferia.alfa.core.services.IAlertasService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

@Component
public class AlertasServiceImpl implements IAlertasService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlertasServiceImpl.class);

	@Autowired
	IParametrosRepository parametrosRepository;

	@Autowired
	ResolucionRepository resolucionRepository;

	@Autowired
	IResolucionNotasRepository resolucionNotasRepository;

	@Autowired
	FacturasRepository facturasRepository;

	@Autowired
	NotaDebitoCreditoRepository notaDebitoCreditoRepository;

	@Autowired
	CompaniasRepository companiasRepository;

	@Autowired
	SucursalesRepository sucursalesRepository;

	/**
	 * Metodo que retorna la lista de alertas que se va a mostrar en la pantalla de inicio
	 * @return List<AlertaDTO> que contiene la lista con las alertas a mostrar en la pantalla de inicio 
	 */
	@Override
	public List<AlertaDTO> calcularAlertasResolucion() {

		List<AlertaDTO> listAlerta = new ArrayList<>();

		try {

			LOGGER.info("Consulta parametros para realizar la validacion ");

			Integer consecutivoFE = consultaParametro(FacEnum.CONSECUTIVO_FE.getValor());
			Integer consecutivoNotas = consultaParametro(FacEnum.CONSECUTIVO_NOTAS.getValor());
			Integer numeroDias = consultaParametro(FacEnum.DIAS_RESTANTES_VENCIMIENTO_RESOLUCION.getValor());

			LOGGER.info("Consulta lista de resoluciones activas para hacer los calculos ");

			List<ResolucionDTO> listResolDTO = resoluciones(numeroDias);

			LOGGER.info("Recorro las resoluciones para validar cada una de ellas y armar la lista de alertas");
			
			for (ResolucionDTO res : listResolDTO) {

				crearListAlert(listAlerta, res, consecutivoFE, consecutivoNotas, numeroDias);

			}

		} catch (Exception e) {
			LOGGER.error("Error al calcular las alertas");
		}

		return listAlerta;
	}
	
	/**
	 * Metodo que llena la lista de alertas que se va a mostrar en la pantalla de inicio
	 * @param List<AlertaDTO> listAlerta, lista de alertas a llenar 
	 * @param ResolucionDTO res, resolucion a validar
	 * @param Integer consecutivoFE, consecutivo de facturas con el cual se compara la numeración para verificar si esta por agotarse 
	 * @param Integer consecutivoNotas, consecutivo de notas con el cual se compara la numeración para verificar si esta por agotarse 
	 * @param Integer consecutivoNotas, consecutivo que representa el numero de dias restantes desde los cuales se debe presentear la alerta por fecha de vencimiento
	 */
	public void crearListAlert(List<AlertaDTO> listAlerta, ResolucionDTO res, Integer consecutivoFE,
			Integer consecutivoNotas, Integer numeroDias) {

		try {
			CompaniasModel compania = companiasRepository.findById(res.getCompania()).orElse(new CompaniasModel());

			SucursalesModel sucursal = sucursalesRepository.findById(res.getSucursal()).orElse(new SucursalesModel());
			
			if (res.getTipo().equals(FacEnum.FACTURA.getValor())) {
				
				LOGGER.info("Inicio calculo porcentaje para facturas ");

				validarNumeracion(res.getCont(), res.getFnumini(), res.getFnumfin(), consecutivoFE,
						"Consecutivos restantes Factura Electronica: ", listAlerta, compania.getNombre(),
						sucursal.getNomsuc());
				
				validarDiasRestantes(numeroDias, res.getFffin(), listAlerta, compania.getNombre(), sucursal.getNomsuc());
				
			}
			
			if (res.getTipo().equals(FacEnum.DEBITO.getValor())) {
				
				LOGGER.info("Inicio calculo porcentaje para nota credito");

				validarNumeracion(res.getCont(), res.getFnumini(), res.getFnumfin(), consecutivoNotas,
						"Consecutivos restantes nota debito: ", listAlerta, compania.getNombre(), sucursal.getNomsuc());
			}
			
			if (res.getTipo().equals(FacEnum.CREDITO.getValor())) {
				
				LOGGER.info("Inicio calculo porcentaje para nota credito");

				validarNumeracion(res.getCont(), res.getFnumini(), res.getFnumfin(), consecutivoNotas,
						"Consecutivos restantes nota credito: ", listAlerta, compania.getNombre(), sucursal.getNomsuc());
			}
			
		} catch (Exception e) {
			LOGGER.error("Error al calcular las alertas");
		}

	}
	
	/**
	 * Metodo que valida la numeracion 
	 * @param Integer numeracionActual, numeracion actual de la resolucion  
	 * @param Integer numeracionInicial, numeracion inicial de la resolucion  
	 * @param Integer numeracionFinal, numeracion final de la resolucion  
	 * @param Integer consecutivo, consecutivo para evaluar el porcentaje minimo
	 * @param String validacion, indica el tipo de validacion que se va a realizar 
	 * @param List<AlertaDTO> listAlerta, lista de alertas a llenar 
	 * @param String compania, compania de la resolucion 
	 * @param String sucursal, sucursal de la resolucion
	 */
	private void validarNumeracion(Integer numeracionActual, Integer numeracionInicial, Integer numeracionFinal,
			Integer consecutivo, String validacion, List<AlertaDTO> listAlerta, String compania, String sucursal) {

		try {

			double numTotal = (double) numeracionFinal - numeracionInicial;
			double porcentajeMin = (numTotal * consecutivo) / 100;
			double numeracionMaxima = numeracionFinal - porcentajeMin;

			if (numeracionActual != null && (numeracionMaxima <= numeracionActual)) {

				Integer consecutivosRestantes = numeracionFinal - numeracionActual;

				if (consecutivosRestantes < 0)
					consecutivosRestantes = 0;

				AlertaDTO alerta = new AlertaDTO(compania, sucursal, validacion + consecutivosRestantes);

				listAlerta.add(alerta);
			}

		} catch (Exception e) {
			LOGGER.error("Error al calcular la numeracion de la resolucion");
		}
	}

	private void validarDiasRestantes(Integer numeroDias, String strfechaFin, List<AlertaDTO> listAlerta, String compania,
			String sucursal) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat(FacEnum.DATE_FORMAT.getValor());
			sdf.setTimeZone(TimeZone.getTimeZone(FacEnum.ZONA_HORARIA.getValor()));
			Date fechaFin = sdf.parse(strfechaFin);

			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaFin);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - numeroDias);

			Date fechaAlerta = cal.getTime();

			Date fechaActual = new Date();

			if (fechaActual.compareTo(fechaAlerta) >= 0) {

				cal.setTime(fechaFin);

				Calendar calActual = Calendar.getInstance();
				calActual.setTime(fechaActual);

				Integer finalDay = cal.get(Calendar.DAY_OF_YEAR) + numeroDias;
				Integer actualDay = calActual.get(Calendar.DAY_OF_YEAR) + numeroDias;

				Integer dias = finalDay - actualDay;

				AlertaDTO alerta = new AlertaDTO(compania, sucursal, "Días para vencimiento: " + dias);

				listAlerta.add(alerta);

			}
		} catch (Exception e) {

			LOGGER.error("Error al calcular los dias restantes: {} ", e.getMessage());
		}

	}

	private Integer consultaParametro(String validacion) {
		LOGGER.debug("Inicio consulta de parametros para alertas");
		int cantidad = 0;
		try {
			String strParametro = parametrosRepository.buscarNombre(validacion);
			cantidad = Integer.parseInt(strParametro);
		} catch (SQLGrammarException | NoResultException | NullPointerException e) {
			LOGGER.warn("Ha ocurrido un error en la consulta de los parametros para las alertas");
		}
		return cantidad;
	}

	private List<ResolucionDTO> resoluciones(int numeroDias) {

		LOGGER.debug("Inicio consulta del resoluciones en el mes actual");	

		List<ResolucionDTO> listResolDTO = new ArrayList<>();

		try {
			
			List<ResolucionModel> listRes = null;
			List<ResolucionNotasModel> listNotRes = null;
			
			LOGGER.debug("Busco las resolcuiones activas para facturas");

			listRes = resolucionRepository.buscarPorEstadoYVigencia(numeroDias);

			if (listRes != null)
				listResolDTO.addAll(
						listRes.stream().map(e -> new ResolucionDTO().modeloAdto(e)).collect(Collectors.toList()));
			
			LOGGER.debug("Busco las resolcuiones activas para notas");

			listNotRes = resolucionNotasRepository.findAllByEstado("TRUE");

			if (listNotRes != null)
				listResolDTO.addAll(listNotRes.stream().map(e -> new ResolucionDTO().notasModeloAdto(e))
						.collect(Collectors.toList()));

		} catch (SQLGrammarException | NoResultException | NullPointerException e) {
			LOGGER.warn("Ha ocurrido un error en la consulta de las resoluciones para el mes actuales");
		}

		return listResolDTO;
	}

}
