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

import co.com.periferia.alfa.core.dto.ResolucionFacturasDto;
import co.com.periferia.alfa.core.dto.ResultadoDto;
import co.com.periferia.alfa.core.model.ResolucionModel;
import co.com.periferia.alfa.core.repository.IResolucionNotasRepository;
import co.com.periferia.alfa.core.repository.ResolucionRepository;
import co.com.periferia.alfa.core.services.IResolucionService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que realiza la logica de la administarcion de las resoluciones para las
 * facturas
 * 
 * @author Duvan Rodriguez
 * @version 2.0
 */

@Service
public class ResolucionServiceImpl implements IResolucionService {

	private static final Logger LOG = LoggerFactory.getLogger(ResolucionServiceImpl.class);

	private static final String ACTUALIZACION_TRUE = "La resoluci\u00f3n se ha actualizado correctamente";
	private static final String CREACION_TRUE = "La resoluci\u00f3n se ha creado correctamente";
	private static final String ACTUALIZACION_FALSE = "No se encontraron datos para actualizar";
	private static final String ERROR_FECHAS = "Error en las fechas";
	private static final String CAMPOS_OBLIGATORIOS = "Todos los campos son obligatorios";
	private static final String TR_ID_EXISTENTE = "El c\u00f3digo tr ID ya se encuentra registrado en notas";

	@Autowired
	ResolucionRepository resolucionRepository;
	
	@Autowired
	IResolucionNotasRepository resolucionNotasRepository;

	/**
	 * Metodo que realiza la consulta al repository y se comunica con la conversion
	 * a dto
	 * 
	 * @param sucursal - Integer
	 * @param compania - Integer
	 * @return ResolucionNotasDto - dto armado
	 */

	@Override
	public List<ResolucionFacturasDto> consultarResolucion(Integer sucursal, Integer compania, String producto) {
		LOG.info("Ingreso a consultar la resolucion de facturas");
		List<ResolucionModel> listRes;
		List<ResolucionFacturasDto> listResolDTO = new ArrayList<>();
		if (sucursal != null && compania != null && producto != null) {
			listRes = resolucionRepository.findBySucursalAndCompaniaAndProducto(sucursal, compania, producto);
			if (!listRes.isEmpty()) {
				listResolDTO = modelToDto(listRes);
			}
		}

		return listResolDTO;
	}

	/**
	 * Metodo que convierte el modelo de las resoluciones de facturas a dto
	 * @param listaResolucionModel - List<ResolucionModel>, lista con la informacion del modelo
	 * @return List<ResolucionFacturasDto>, lista con los dto armados
	 */
	
	private List<ResolucionFacturasDto> modelToDto(List<ResolucionModel> listaResolucionModel) {
		LOG.info("Inicio conversion de modelo a dto");
		List<ResolucionFacturasDto> listaDto = new ArrayList<>();
		listaResolucionModel.stream().forEach(modelo -> {
			ResolucionFacturasDto resoluciondto = new ResolucionFacturasDto();
			resoluciondto.setLlave(modelo.getRes());
			resoluciondto.setSucursal(modelo.getSucursal());
			resoluciondto.setCompania(modelo.getCompania());
			resoluciondto.setNumeroResolucion(modelo.getFnresol());
			resoluciondto.setPrefijo(modelo.getFprefijo());
			resoluciondto.setFechaInicial(modelo.getFfini().toString().replace("-", "/"));
			resoluciondto.setFechaFinal(modelo.getFffin().toString().replace("-", "/"));
			resoluciondto.setNumeroInicial(modelo.getFnumini());
			resoluciondto.setNumeroFinal(modelo.getFnumfin());
			resoluciondto.setEstado(modelo.getEstado());
			resoluciondto.setTrTipoId(modelo.getTidFactura());
			resoluciondto.setVigencia(validarVigencia(modelo.getFfini(), modelo.getFffin()));
			resoluciondto.setContador(modelo.getFacturaCont());
			resoluciondto.setProducto(modelo.getProducto());
			listaDto.add(resoluciondto);
		});
		return listaDto;
	}

	/**
	 * Metodo que realiza la consulta el rango superior la ultima resolucion creado
	 * para la sucursal y compañia
	 * 
	 * @param sucursal - Integer
	 * @param compania - Integer
	 * @return ResolucionNotasDto - dto armado
	 */

	@Override
	public List<String> consultarRangoSuperior(Integer sucursal, Integer compania, String producto) {
		LOG.debug("Ingreso a consultar el rango superior la ultima resolucion creado para la sucursal");
		int posicionFecha = 0;
		int posicionNumeroFinal = 1;
		DateFormat formato = new SimpleDateFormat(FacEnum.FORMATO_FECHA1.getValor());
		List<String> rangosResolucion = new ArrayList<>();
		if (sucursal != null && compania != null) {
			List<String[]> rangos = resolucionRepository.findByLastResolucion(sucursal, compania, producto);
			if (rangos != null && !rangos.isEmpty()) {
				String fechaFinal = FacEnum.NOTWHITE_SPACE.getValor();
				try {
					fechaFinal = formato.format(formato.parse(rangos.get(0)[posicionFecha].split(" ")[0].replace("-", "/")));
				} catch (ParseException e) {
					LOG.warn("Ha ocurrido un error con el parseo de la fecha, error: {} ", e.getMessage());
				}
				rangosResolucion.add(fechaFinal);
				rangosResolucion.add(rangos.get(0)[posicionNumeroFinal]);
			}
		}
		return rangosResolucion;
	}

	/**
	 * Metodo que realiza la edicion de las resoluciones
	 * 
	 * @param resolucionDTO objeto con la informacion de la resolucion a actualizar
	 * @return ResultadoDto - objeto con la respuesta de la actualizacion
	 */

	@Override
	public ResultadoDto editarResolucion(ResolucionFacturasDto resolucionDto) {
		LOG.info("Ingreso a editar resolucion Factura");
		DateFormat formato = new SimpleDateFormat(FacEnum.FORMATO_FECHA1.getValor());
		if (resolucionDto != null && resolucionDto.getLlave() != null) {
			Integer trIdExistenteNotas = resolucionNotasRepository.consultarTrIdExistenteNotas(resolucionDto.getTrTipoId());
			if(trIdExistenteNotas == 0) {
				ResolucionModel resolucion = resolucionRepository.findById(resolucionDto.getLlave()).orElse(null);
				if (resolucion != null) {
					return complementoEdicion(resolucionDto, resolucion, formato);
				} else {
					return armarResultado(ACTUALIZACION_FALSE, false);
				}	
			} else {
				return armarResultado(TR_ID_EXISTENTE, false);
			}
		} else {
			return armarResultado(CAMPOS_OBLIGATORIOS, false);
		}
	}
	
	/**
	 * Metodo que compementa la edicion con el try - catch  
	 * @param resolucionDto - ResolucionFacturasDto, dto con la informacion a validar y editar
	 * @param resolucion - ResolucionModel, modelo con la informacion a editar
	 * @param formato - DateFormat, formato para las fechas
	 * @return ResultadoDto, dto con la respuesta armada
	 */
	
	private ResultadoDto complementoEdicion (ResolucionFacturasDto resolucionDto, ResolucionModel resolucion, DateFormat formato) {
		try {
			if (validarEdicion(resolucionDto, resolucion, formato)) 
				return guardarResolucionEditada(resolucionDto, resolucion, formato);
			else 
				return armarResultado(ACTUALIZACION_FALSE, false);
			
		} catch (ParseException e) {
			LOG.info("Error al parsear el formato de la fecha, error: {} ", e.getMessage());
			return armarResultado(ERROR_FECHAS, false);
		}
	}
	
	/**
	 * Metodo que complementa la edicion
	 * @param resolucionDto - ResolucionFacturasDto, dto con la informacion a actualizar
	 * @param resolucion - ResolucionModel, modelo de donde se sacara la copia de la resolucion editada
	 * @param formato - DateFormat, formato de las fechas
	 * @return ResultadoDto, dto con la respuesta del servicio
	 * @throws ParseException, exception para controlar errores de parse en las fechas
	 */
	
	private ResultadoDto guardarResolucionEditada(ResolucionFacturasDto resolucionDto, ResolucionModel resolucion, DateFormat formato) throws ParseException {
		if(resolucion.getFacturaCont() == 0) {
			return complementoGuardarResolucionEditada(resolucionDto, resolucion, formato);
		} else {
			if((resolucionDto.getNumeroInicial() > resolucion.getFacturaCont()) || (resolucionDto.getNumeroFinal() < resolucion.getFacturaCont())) 
				return armarResultado("El rango de numeraci\u00f3n afecta el contador, por favor ajuste el rango de numeraci\u00f3n", false);
			else 
				return complementoGuardarResolucionEditada(resolucionDto, resolucion, formato);
			
		}
	}
	
	/**
	 * Metodo que complementa el guardado de una resolucion
	 * @param resolucionDto - ResolucionFacturasDto, dto con la informacion a actualizar
	 * @param resolucion - ResolucionModel, modelo de donde se sacara la copia de la resolucion editada
	 * @param formato - DateFormat, formato de las fechas
	 * @return ResultadoDto, dto con la respuesta del servicio
	 * @throws ParseException, exception para controlar errores de parse en las fechas
	 */
	
	private ResultadoDto complementoGuardarResolucionEditada (ResolucionFacturasDto resolucionDto, ResolucionModel resolucion, DateFormat formato) throws ParseException {
		resolucionRepository.save(generarCopiaModelo(resolucion));
		
		resolucion.setFprefijo(resolucionDto.getPrefijo());
		resolucion.setFfini(formato.parse(resolucionDto.getFechaInicial()));
		resolucion.setFffin(formato.parse(resolucionDto.getFechaFinal()));
		resolucion.setFnumini(resolucionDto.getNumeroInicial());
		resolucion.setFnumfin(resolucionDto.getNumeroFinal());
		resolucion.setFactualiza(new Date());
		resolucion.setUactualiza(resolucionDto.getUsuario());
		
		if(resolucionDto.getEstado().equals(FacEnum.FALSE.getValor()) && resolucionDto.isVigencia()) 
			resolucion.setEstado(FacEnum.FALSE.getValor());
		else 
			resolucion.setEstado(FacEnum.TRUE.getValor());
		
		resolucion.setTidFactura(resolucionDto.getTrTipoId());
		resolucion.setAuditoria(FacEnum.FALSE.getValor());
		resolucionRepository.save(resolucion);
		return armarResultado(ACTUALIZACION_TRUE, true);
	}

	/**
	 * Metodo que realiza la creacion de resolucion
	 * @param resolucionDto - ResolucionFacturasDto, dto con al infromacion a crear
	 * @return ResultadoDto, dto con la respuesta del servicio
	 */
	
	@Override
	public ResultadoDto crearResolucion(ResolucionFacturasDto resolucionDto) {
		LOG.info("Ingreso a la creacion de resolucion");
		if (resolucionDto != null) {
			Integer trIdExistenteNotas = resolucionNotasRepository.consultarTrIdExistenteNotas(resolucionDto.getTrTipoId());
			if(trIdExistenteNotas == 0) {
				return complementoCrearResolucion (resolucionDto);
			} else {
				return armarResultado(TR_ID_EXISTENTE, false);
			}
		} else {
			return armarResultado(CAMPOS_OBLIGATORIOS, false);
		}
	}
	
	private ResultadoDto complementoCrearResolucion (ResolucionFacturasDto resolucionDto) {
		DateFormat formato = new SimpleDateFormat(FacEnum.FORMATO_FECHA1.getValor());
		try {
			Date fechaIni = formato.parse(resolucionDto.getFechaInicial());
			Date fechaFin = formato.parse(resolucionDto.getFechaFinal());
			if (validarVigencia(fechaIni, fechaFin)) {
				Integer existe = resolucionRepository.buscarCoincidencias(resolucionDto.getSucursal(), resolucionDto.getCompania(), 
						resolucionDto.getProducto(), resolucionDto.getNumeroResolucion());
				if (existe != null && existe > 0) {
					return armarResultado("La resoluci\u00f3n ya existe, por favor verifique los datos ingresados",
							false);
				} else {
					resolucionRepository.save(dtoToModel(resolucionDto, formato, fechaIni, fechaFin));
					return armarResultado(CREACION_TRUE,
							true);
				}
			} else {
				return armarResultado("Las fechas no se encuentran dentro del rango de vigencia", false);
			}
		} catch (ParseException e) {
			LOG.warn("Ha ocurrido un error con las fechas, error: {} ", e.getMessage());
			return armarResultado(ERROR_FECHAS, false);
		}	
	}

	/**
	 * metodo que convierte un dto a modelo
	 * @param dto - ResolucionFacturasDto, dto a convertir a modelo
	 * @param formato - DateFormat, formato de la fecha a usar
	 * @param fechaIni - Date, fecha inicial
	 * @param fechaFin - Date, fecha final
	 */
	
	private ResolucionModel dtoToModel(ResolucionFacturasDto dto, DateFormat formato, Date fechaIni, Date fechaFin) throws ParseException {
		ResolucionModel enviarResolucion = new ResolucionModel();
		enviarResolucion.setSucursal(dto.getSucursal());
		enviarResolucion.setCompania(dto.getCompania());
		enviarResolucion.setFnresol(dto.getNumeroResolucion());
		enviarResolucion.setFprefijo(dto.getPrefijo());
		enviarResolucion.setFfini(formato.parse(dto.getFechaInicial()));
		enviarResolucion.setFffin(formato.parse(dto.getFechaFinal()));
		enviarResolucion.setFnumini(dto.getNumeroInicial());
		enviarResolucion.setFnumfin(dto.getNumeroFinal());
		enviarResolucion.setFcreacion(new Date());
		enviarResolucion.setUcreacion(dto.getUsuario());
		enviarResolucion.setFactualiza(null);
		enviarResolucion.setUactualiza(null);
		
		Date fechaActual = formato.parse(formato.format(new Date()));
		if (fechaActual.compareTo(fechaIni) >= 0 && fechaActual.compareTo(fechaFin) <= 0) 
			enviarResolucion.setEstado(FacEnum.TRUE.getValor());
		else 
			enviarResolucion.setEstado(FacEnum.FALSE.getValor());
		
		enviarResolucion.setTidFactura(dto.getTrTipoId());
		enviarResolucion.setFacturaCont(0);
		enviarResolucion.setAuditoria(FacEnum.FALSE.getValor());
		enviarResolucion.setProducto(dto.getProducto());
		return enviarResolucion;
	}

	/**
	 * Metodo que genera una copia de los datos existentes en el modelo modificandole el estado a false
	 * @param modelo - ResolucionModel, modelo con la informacion copiada
	 * @return ResolucionModel, modelo copia armado
	 */
	
	private ResolucionModel generarCopiaModelo(ResolucionModel modelo){
		ResolucionModel enviarResolucion = new ResolucionModel();
		enviarResolucion.setSucursal(modelo.getSucursal());
		enviarResolucion.setCompania(modelo.getCompania());
		enviarResolucion.setFnresol(modelo.getFnresol());
		enviarResolucion.setFprefijo(modelo.getFprefijo());
		enviarResolucion.setFfini(modelo.getFfini());
		enviarResolucion.setFffin(modelo.getFffin());
		enviarResolucion.setFnumini(modelo.getFnumini());
		enviarResolucion.setFnumfin(modelo.getFnumfin());
		enviarResolucion.setFcreacion(modelo.getFcreacion());
		enviarResolucion.setUcreacion(modelo.getUcreacion());
		enviarResolucion.setFactualiza(modelo.getFactualiza());
		enviarResolucion.setUactualiza(modelo.getUactualiza());
		enviarResolucion.setEstado(FacEnum.FALSE.getValor());
		enviarResolucion.setTidFactura(modelo.getTidFactura());
		enviarResolucion.setFacturaCont(modelo.getFacturaCont());
		enviarResolucion.setAuditoria(FacEnum.TRUE.getValor());
		enviarResolucion.setProducto(modelo.getProducto());
		return enviarResolucion;
	}

	/**
	 * Metodo que valida si hay datos para modificar, esto con objeto de no crear copias innecesarias  
	 * @param dto - ResolucionFacturasDto, dto con la informacion a cotejar contra el modelo
	 * @param modelo - ResolucionModel, modelo con la informacion a cotejas contra el dto
	 * @param formato - DateFormat, formato para las fechas
	 * @return boolean, valida si hay datos para actualizar
	 * @throws ParseException, exception para controlar desde el metodo padre si hay error en las fechas
	 */
	
	private boolean validarEdicion(ResolucionFacturasDto dto, ResolucionModel modelo, DateFormat formato)
			throws ParseException {
		return !dto.getPrefijo().equals(modelo.getFprefijo())
				|| !formato.parse(dto.getFechaInicial()).equals(formato.parse(formato.format(modelo.getFfini())))
				|| !formato.parse(dto.getFechaFinal()).equals(formato.parse(formato.format(modelo.getFffin())))
				|| !dto.getNumeroInicial().equals(modelo.getFnumini())
				|| !dto.getNumeroFinal().equals(modelo.getFnumfin())
				|| !dto.getTrTipoId().equals(modelo.getTidFactura());
	}

	
	/**
	 * Metodo que valida las fechas de una resolucion para saber si se encuentran en vigencia 
	 * @param fechaIni - Date, fecha inicial
	 * @param fcehaFin - Date, fecha final
	 * @return boolean, valida si hay datos para actualizar
	 */
	
	private boolean validarVigencia(Date fechaIni, Date fechaFin) {
		boolean validacion = false;
		try {
			DateFormat formato = new SimpleDateFormat(FacEnum.FORMATO_FECHA1.getValor());
			fechaIni = formato.parse(formato.format(fechaIni));
			fechaFin = formato.parse(formato.format(fechaFin));
			Date fechaActual = formato.parse(formato.format(new Date()));
			if (fechaActual.compareTo(fechaIni) >= 0 && fechaActual.compareTo(fechaFin) <= 0) 
				validacion = true;
			
			if (fechaIni.compareTo(fechaActual) >= 0) 
				validacion = true;
			
			if (fechaIni.compareTo(fechaFin) > 0) 
				validacion = false;
			
		} catch (ParseException e) {
			LOG.warn("Ha ocurrido un error con las fechas, error: {} ", e.getMessage());
		}
		return validacion;
	}

	/**
	 * Metodo que arma el resultado de las peticiones (crear, actualizar, eliminar)
	 * @param mensaje - String, mensaje alusivo a la accion realizada
	 * @param respuesta- boolean, define si la respuesta es satisfactoria o no
	 * @return ResultadoDto, respeusta armada
	 */
	
	private ResultadoDto armarResultado(String mensaje, boolean respuesta) {
		ResultadoDto dto = new ResultadoDto();
		dto.setSucces(respuesta);
		dto.setMessage(mensaje);
		return dto;
	}

	/**
	 * metodo que elimina la resolucion
	 * @param resolucion - ResolucionFacturasDto, dto con la infromacion de la resolucion a eliminar
	 * @return ResultadoDto, dto con la respuesta del servicio
	 */
	
	@Override
	public ResultadoDto eliminarResolucion(ResolucionFacturasDto resolucion) {
		LOG.info("Ingreso a eliminar resolucion");
		if(resolucion != null) {
			if(resolucion.getEstado().equals(FacEnum.FALSE.getValor())) {
				resolucionRepository.eliminarResolucion(resolucion.getLlave());
				return armarResultado("La resoluci\u00f3n ha sido eliminada", true);
			} else {
				return armarResultado("No se puede eliminar, la resoluci\u00f3n se encuentra en vigencia", false);
			}
		} else {
			return armarResultado("La resoluci\u00f3n a eliminar es obligatoria", false);
		}
	}

}
