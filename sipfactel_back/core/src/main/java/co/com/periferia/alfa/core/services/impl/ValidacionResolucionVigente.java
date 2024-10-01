package co.com.periferia.alfa.core.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.model.ResolucionNotasModel;
import co.com.periferia.alfa.core.repository.IResolucionNotasRepository;
import co.com.periferia.alfa.core.repository.ResolucionRepository;
import co.com.periferia.alfa.core.services.IValidacionResolucionVigenteService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que realiza la logica de la vigencia
 * 
 * @author Duvan Rodriguez
 */

@Service
public class ValidacionResolucionVigente implements IValidacionResolucionVigenteService {

	private static final int LLAVE_PRIMARIA = 0;
	private static final int FECHA_INICIAL = 1;
	private static final int FECHA_FINAL = 2;
	private static final int NUMERO_FINAL = 3;
	private static final int CONTADOR = 4;

	private static final Logger LOG = LoggerFactory.getLogger(ValidacionResolucionVigente.class);

	@Autowired
	ResolucionRepository resolucionRepository;
	
	@Autowired
	IResolucionNotasRepository resolucionNotasRepository;

	/**
	 * Metodo que realiza la logica de la actualziacion de vigencias
	 */
	
	@Override
	public void validacionResolucionFacturas() {
		
		LOG.info("Ingreso a servicio de validacion de vigencias");
		resolucionRepository.inactivarResolucionesVencidas();
		resolucionRepository.activarResolucionesVigentes();
		
		/*LOG.info("Ingreso a servicio de validacion de vigencias");
		DateFormat formato = new SimpleDateFormat(FacEnum.FORMATO_FECHA3.getValor());
		List<String[]> listaResoluciones = resolucionRepository.busquedaParaHablitarVigencia();

		List<Integer> habilitarResoluciones = new ArrayList<>();
		List<Integer> deshabilitarResoluciones = new ArrayList<>();
		LOG.info("Cantidad de resoluciones a evaluar: {} ", listaResoluciones.size());
		try {
			Date fechaActual = formato.parse(formato.format(new Date()));
			listaResoluciones.stream().forEach(data -> {
				try {
					Date fechaInicial = formato.parse(data[FECHA_INICIAL]);
					Date fechaFinal = formato.parse(data[FECHA_FINAL]);
					if (fechaActual.compareTo(fechaInicial) >= 0 && fechaActual.compareTo(fechaFinal) <= 0) {
						if (Integer.parseInt(data[CONTADOR]) < Integer.parseInt(data[NUMERO_FINAL])) {
							habilitarResoluciones.add(Integer.parseInt(data[LLAVE_PRIMARIA]));
						} else {
							deshabilitarResoluciones.add(Integer.parseInt(data[LLAVE_PRIMARIA]));
						}
					} else {
						deshabilitarResoluciones.add(Integer.parseInt(data[LLAVE_PRIMARIA]));
					}
				} catch (ParseException e) {
					LOG.warn("Ha ocurrdio un error con las fechas, error: {} ", e.getMessage());
				}
			});
			actualizarVigencias(habilitarResoluciones, deshabilitarResoluciones);
		} catch (ParseException e) {
			LOG.warn("Ha ocurrdio un error con las fechas, error: {} ", e.getMessage());
		}*/

	}

	/**
	 * Metodo que hace el consumo del repositorio para las vigencias
	 */
	
	private void actualizarVigencias(List<Integer> habilitarResoluciones, List<Integer> deshabilitarResoluciones) {
		if (!habilitarResoluciones.isEmpty()) {
			habilitarResoluciones.stream()
					.forEach(habilitar -> resolucionRepository.actualizarVigencia(FacEnum.TRUE.getValor(), habilitar));
		}
		if (!deshabilitarResoluciones.isEmpty()) {
			deshabilitarResoluciones.stream().forEach(
					deshabilitar -> resolucionRepository.actualizarVigencia(FacEnum.FALSE.getValor(), deshabilitar));
		}
	}

	/**
	 * Metodo que valida la vigencia de las resoluciones de notas
	 */
	
	@Override
	public void validacionResolucionNotas() {
	  LOG.info("Ingresa a validar resolucion de notas");
	  LOG.info("Actualiza el estado de las resoluciones vencidas");
	  resolucionNotasRepository.actualizarEstadosVencidos();
	  
	  List<ResolucionNotasModel> listaConsulta = resolucionNotasRepository.consultarTodasResolucionNotaFutura();
	  
	  for(ResolucionNotasModel resolucion : listaConsulta) {
		  ResolucionNotasModel consultaFutura = resolucionNotasRepository.consultarResolucionNotaFuturaPorTipoNota(resolucion.getSucursal(), resolucion.getCompania(), resolucion.getProducto(), resolucion.getTipoNota());
		  if(consultaFutura != null && consultaFutura.getDni() != null) {
			  int yaExisteVigente = resolucionNotasRepository.consultarVigenciaExistente(consultaFutura.getSucursal(), consultaFutura.getCompania(), consultaFutura.getProducto(), consultaFutura.getTipoNota());
			  if(yaExisteVigente == 0) {
				  consultaFutura.setEstado(FacEnum.TRUE.getValor());
				  resolucionNotasRepository.save(consultaFutura);  
			  }
		  }
	  }
	  
	  LOG.info("Finaliza validacion de resolucion de notas");
	}

}
