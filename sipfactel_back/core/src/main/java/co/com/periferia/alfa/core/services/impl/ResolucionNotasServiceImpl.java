package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.DatosResolucionNotasDto;
import co.com.periferia.alfa.core.dto.ResolucionNotasDto;
import co.com.periferia.alfa.core.dto.ResultadoDto;
import co.com.periferia.alfa.core.model.ResolucionNotasModel;
import co.com.periferia.alfa.core.repository.IResolucionNotasRepository;
import co.com.periferia.alfa.core.repository.ResolucionRepository;
import co.com.periferia.alfa.core.services.IResolucionNotasService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase que realiza la logica de la administarcion de la numeracion de notas
 * 
 * @author Duvan Rodriguez
 */

@Service
public class ResolucionNotasServiceImpl implements IResolucionNotasService {

	private static final Logger LOG = LoggerFactory.getLogger(ResolucionNotasServiceImpl.class);

	private static final String ACTUALIZACION_TRUE = "La nota se ha actualizado correctamente";
	private static final String ACTUALIZACION_FALSE = "No se encontraron datos para actualizar";
	private static final String CREACION_EXITOSA = "Se ha creado la resoluci\u00f3n";
	private static final String ELIMINACION_EXITOSA = "Se ha creado eliminado la resoluci\u00f3n";

	@Autowired
	IResolucionNotasRepository resolucionNotasRepository;
	
	@Autowired
	ResolucionRepository resolucionRepository;

	/**
	 * Metodo que realiza la consulta al repository y se comunica con la conversion
	 * a dto
	 * 
	 * @param sucursal - Integer
	 * @param compania - Integer
	 * @return ResolucionNotasDto - dto armado
	 */

	@Override
	public ResolucionNotasDto consultarResolucionNotas(Integer sucursal, Integer compania, String producto) {
		LOG.info("Ingreso a consultar la resolucion de notas");
		List<ResolucionNotasModel> notasModel = new ArrayList<>();
		ResolucionNotasDto resolucionDto = new ResolucionNotasDto();
		if (sucursal != null && compania != null && producto != null) {
			notasModel.addAll(resolucionNotasRepository.consultarResolucionNotaVigente(sucursal, compania, producto));
			notasModel.addAll(resolucionNotasRepository.consultarResolucionNotaFutura(sucursal, compania, producto));
			notasModel.addAll(resolucionNotasRepository.consultarResolucionNotaVencida(sucursal, compania, producto));
			resolucionDto = modeloToDto(notasModel);
			
		}
		return resolucionDto;
	}

	/**
	 * Metodo que inicia la edicion de la numeracion de notas
	 * 
	 * @param nota - DatosResolucionNotasDto, objeto con la informacion a actualizar
	 * @return ResultadoDto - objeto con la respuesta de la actualizacion
	 */

	@Override
	public ResultadoDto editarResolucionNotas(DatosResolucionNotasDto nota) {
		LOG.info("Ingreso a editar resolucion nota");
		ResultadoDto respuesta = validarCodigosTrid(nota, new ResultadoDto());
		if(respuesta.getMessage() == null) {
			boolean actualiza;
			if(nota != null) {
				ResolucionNotasModel consultaNota = resolucionNotasRepository.findById(nota.getLlave()).orElse(null);
				if (consultaNota != null) {
					actualiza = validarEdicion(nota, consultaNota);
					actualizacionResolucionNotas(actualiza, nota, consultaNota, respuesta);
				}	
			}
		}
		
		return respuesta;
	}
	
	/**
	 * Metodo que complementa la edicion con la validacion de que la edicion no pise el contador de la numeracion
	 * @param actualizar - boolean, define si los campos son aptos para actualizar
	 * @param nota . DatosResolucionNotasDto, objeto dto con la informacion a actualizar
	 * @param consultaNota - ResolucionNotasModel, objeto modelo de donde saldran los datos a actualizar
	 * @param respuesta - ResultadoDto, dto que almacenara la respuesta de la operacion
	 */
	
	private void actualizacionResolucionNotas (boolean actualiza,DatosResolucionNotasDto nota, ResolucionNotasModel consultaNota, ResultadoDto respuesta) {
		if (actualiza) {
			if(consultaNota.getContador() == 0) {
				complementoActualizarResolucionNotas(consultaNota, nota, respuesta);	
			} else {
				if((nota.getNumeroInicial() > consultaNota.getContador()) || (nota.getNumeroFinal() < consultaNota.getContador())) {
					respuesta.setSucces(false);
					respuesta.setMessage("El rango de numeraci\u00f3n afecta el contador, por favor ajuste el rango de numeraci\u00f3n");	
				} else {
					complementoActualizarResolucionNotas(consultaNota, nota, respuesta);
				}	
			}
		} else {
			respuesta.setSucces(false);
			respuesta.setMessage(ACTUALIZACION_FALSE);
		}
	}
	
	/**
	 * Metodo que complementa la actualizacion de notas, realiza el guardado de la edicion en bd
	 * @param consultaNota - ResolucionNotasModel, objeto modelo de donde saldran los datos a actualizar
	 * @param nota . DatosResolucionNotasDto, objeto dto con la informacion a actualizar
	 * @param respuesta - ResultadoDto, dto que almacenara la respuesta de la operacion
	 */
	
	private void complementoActualizarResolucionNotas (ResolucionNotasModel consultaNota, DatosResolucionNotasDto nota, ResultadoDto respuesta) {
		LOG.info("actualiza resolucion notas");
		resolucionNotasRepository.save(dtoToModelCopiaAuditoria(consultaNota));
		resolucionNotasRepository.save(dtoToModel(true, nota, consultaNota));
		respuesta.setSucces(true);
		respuesta.setMessage(ACTUALIZACION_TRUE);	
	}
	
	/**
	 * Metodo que convierte un modelo para reinciarle la llaver¿ primaria y gaurdar el registro como auditoria
	 * @param consultaNota - ResolucionNotasModel objeto con la informacion del
	 *                     modelo a crear como copia, se guarda la misma unfiormacion solo se cambian los estados
	 * @return ResolucionNotasModel - modelo armado
	 */
	
	private ResolucionNotasModel dtoToModelCopiaAuditoria (ResolucionNotasModel consultaNota) {
		ResolucionNotasModel enviarNota = new ResolucionNotasModel();
		enviarNota.setSucursal(consultaNota.getSucursal());
		enviarNota.setCompania(consultaNota.getCompania());
		enviarNota.setPrefijo(consultaNota.getPrefijo());
		enviarNota.setNumeroInicial(consultaNota.getNumeroInicial());
		enviarNota.setNumeroFinal(consultaNota.getNumeroFinal());
		enviarNota.setFechaCreacion(new Date());
		enviarNota.setUsuarioCreacion(consultaNota.getUsuarioCreacion());
		enviarNota.setContador(consultaNota.getContador());
		enviarNota.setTrTipoId(consultaNota.getTrTipoId());
		enviarNota.setTipoNota(consultaNota.getTipoNota());
		enviarNota.setProducto(consultaNota.getProducto());
		enviarNota.setAuditoria(FacEnum.TRUE.getValor());
		enviarNota.setEstado(FacEnum.FALSE.getValor());
		
		return enviarNota;
	}
	
	/**
	 * Metodo que convierte un dto a modelo
	 * @param actualizaCrea - boolean, true cuando actualiza y false cuando crea
	 * @param nota - DatosResolucionNotasDto, dto de donde se extraeran los datos para armar el modelo
	 * @param consultaNota - ResolucionNotasModel objeto con la informacion del
	 *                     modelo a modificar
	 * @return ResolucionNotasModel - modelo armado
	 */
	
	private ResolucionNotasModel dtoToModel (boolean actualizaCrea, DatosResolucionNotasDto nota, ResolucionNotasModel consultaNota) {
		ResolucionNotasModel enviarNota = new ResolucionNotasModel();
		if(actualizaCrea) {
			enviarNota.setDni(nota.getLlave());
		}
		enviarNota.setSucursal(nota.getSucursal());
		enviarNota.setCompania(nota.getCompania());
		enviarNota.setPrefijo(nota.getPrefijo());
		enviarNota.setNumeroInicial(nota.getNumeroInicial());
		enviarNota.setNumeroFinal(nota.getNumeroFinal());
		enviarNota.setFechaCreacion(new Date());
		enviarNota.setUsuarioCreacion(nota.getUsuario());
		enviarNota.setContador((actualizaCrea) ? consultaNota.getContador() : 0);
		enviarNota.setTrTipoId(nota.getTrTipoId());
		enviarNota.setTipoNota(nota.getTipoNota());
		enviarNota.setProducto(nota.getProducto());
		enviarNota.setAuditoria(nota.getAuditoria());
		enviarNota.setEstado(nota.getEstado());
		
		return enviarNota;
	}

	/**
	 * metodo que valida si hay datos que requieran la actualizacion en BD
	 * 
	 * @param nota         - DatosResolucionNotasDto, objeto con los datos que seran
	 *                     cotejados con los existentes en bd
	 * @param consultaNota - ResolucionNotasModel, objeto con los datos que seran
	 *                     cotejados con los recibidos en el dto
	 * @return boolean, true si hay modificaciones, false si no las hay.
	 */

	private boolean validarEdicion(DatosResolucionNotasDto nota, ResolucionNotasModel consultaNota) {		
		return !nota.getTrTipoId().equals(consultaNota.getTrTipoId())
				|| !nota.getNumeroInicial().equals(consultaNota.getNumeroInicial())
				|| !nota.getNumeroFinal().equals(consultaNota.getNumeroFinal())
				|| !nota.getPrefijo().equals(consultaNota.getPrefijo());
	}

	/**
	 * Metodo que convierte el modelo a un dto
	 * 
	 * @param notasModel - lista modelo de donde se extraera la informacion
	 * @return ResolucionNotasDto - dto armado
	 */

	private ResolucionNotasDto modeloToDto(List<ResolucionNotasModel> notasModel) {
		LOG.info("Inicio conversion de modelo a dto");
		ResolucionNotasDto notasDto = new ResolucionNotasDto();
		List<DatosResolucionNotasDto> notasDebito = new ArrayList<>();
		List<DatosResolucionNotasDto> notasCredito = new ArrayList<>();
		notasModel.stream().forEach(notas -> {
			DatosResolucionNotasDto datosNotasDto = new DatosResolucionNotasDto();
			if (notas.getTipoNota().equals(FacEnum.DEBITO.getValor())) 
				notasDebito.add(armarDebitoCredito(datosNotasDto, notas));
			else 
				notasCredito.add(armarDebitoCredito(datosNotasDto, notas));
			
		});
		notasDto.setDebito(notasDebito);
		notasDto.setCredito(notasCredito);
		return notasDto;
	}

	/**
	 * Metodo que valida los datos que se convierten a dto
	 * 
	 * @param datosNotasDto - dto que sera armado
	 * @param notas         - modelo de donde el dto extraera la informacion
	 * @return DatosResolucionNotasDto - dto armado
	 */

	private DatosResolucionNotasDto armarDebitoCredito(DatosResolucionNotasDto datosNotasDto,
			ResolucionNotasModel notas) {
		datosNotasDto.setLlave(notas.getDni());
		datosNotasDto.setCompania(notas.getCompania());
		datosNotasDto.setSucursal(notas.getSucursal());
		datosNotasDto.setTrTipoId(Utilitario.intNulo(notas.getTrTipoId()));
		datosNotasDto.setPrefijo(Utilitario.datoNulo(notas.getPrefijo()));
		datosNotasDto.setNumeroInicial(Utilitario.intNulo(notas.getNumeroInicial()));
		datosNotasDto.setNumeroFinal(Utilitario.intNulo(notas.getNumeroFinal()));
		datosNotasDto.setTipoNota(Utilitario.datoNulo(notas.getTipoNota()));
		datosNotasDto.setContador(Utilitario.intNulo(notas.getContador()));
		datosNotasDto.setProducto(Utilitario.datoNulo(notas.getProducto()));
		datosNotasDto.setEstado(Utilitario.datoNulo(notas.getEstado()));
		datosNotasDto.setAuditoria(Utilitario.datoNulo(notas.getAuditoria()));
		return datosNotasDto;
	}

	/**
	 * Metodo que crea las resoluciones para notas
	 * @Param nota, DatosResolucionNotasDto -> dto con la informacion a crear
	 * @return ResultadoDto -> dto con al respuesta a la solicitud de creacion
	 */
	
	@Override
	public ResultadoDto crearResolucionNotas(DatosResolucionNotasDto nota) {
		LOG.info("Ingreso a crear resolucion nota");
		ResultadoDto respuesta = validarCodigosTrid(nota, new ResultadoDto());
		if(respuesta.getMessage() == null) {
			try {
				ResolucionNotasModel consultaNota = dtoToModel(false, nota, null);
				resolucionNotasRepository.save(consultaNota);
				respuesta.setSucces(true);
				respuesta.setMessage(CREACION_EXITOSA);		
			} catch(NullPointerException e) {
				LOG.info("Ha ocurriod un error al guardar resolcuion de notas: {} ", e.getMessage());
			}
		}
		return respuesta;
	}
	
	/**
	 * Metodo que valida si los codigos trid ya existen en otro registro
	 * @param nota, DatosResolucionNotasDto -> dto con la informacion de la resoluciona  validar
	 * @param respuesta, ResultadoDto -> dto con la repsuesta que sera modificada dependiendo de si encuentra errores
	 * 
	 */
	
	private ResultadoDto validarCodigosTrid(DatosResolucionNotasDto nota, ResultadoDto respuesta) {
		LOG.info("Ingreso a validar codigp trid de nota");
		if (nota != null) {
			String tipoNota = (nota.getTipoNota().equals(FacEnum.DEBITO.getValor())) ? FacEnum.CREDITO.getValor() : FacEnum.DEBITO.getValor();
			Integer trIdExistenteNotas = resolucionNotasRepository.consultarTrIdExistente(nota.getTrTipoId(), tipoNota);
			if(trIdExistenteNotas == 0) {
				Integer trIdExistenteFactura = resolucionRepository.validarExistenciaTrIdFactura(nota.getTrTipoId());
				if(trIdExistenteFactura != 0) {
					respuesta.setSucces(false);
					respuesta.setMessage("El c\u00f3digo tr ID ya se encuentra registrado en facturas.");
				}
			} else {
				respuesta.setSucces(false);
				respuesta.setMessage("El c\u00f3digo tr ID ya se encuentra registrado en notas " + tipoNota);
			}
		}
		return respuesta;
	}

	@Override
	public ResultadoDto eliminarResolucionNotas(DatosResolucionNotasDto nota) {
		LOG.info("Ingreso a eliminar resolucion de notas");
		ResultadoDto respuesta = new ResultadoDto();
		try {
			resolucionNotasRepository.eliminarResolucionNotas(nota.getLlave());
			respuesta.setSucces(true);
			respuesta.setMessage(ELIMINACION_EXITOSA);
		} catch (Exception e) {
			respuesta.setSucces(false);
			respuesta.setMessage("Ha ocurrido un error");
			LOG.info("Ha ocurrido un error al eliminar la resolucion de notas: {} ", e.getMessage());
		}
		return respuesta;
	}

}
