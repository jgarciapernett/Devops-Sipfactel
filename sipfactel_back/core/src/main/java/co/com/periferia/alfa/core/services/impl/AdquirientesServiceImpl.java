package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.AdquirientesDTO;
import co.com.periferia.alfa.core.dto.ConsAdquirientesDTO;
import co.com.periferia.alfa.core.dto.ConsultaAdquirienteDto;
import co.com.periferia.alfa.core.dto.DesplegablesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.AdquirientesModel;
import co.com.periferia.alfa.core.model.ConsultaAdquirientesModel;
import co.com.periferia.alfa.core.model.DesplegablesModel;
import co.com.periferia.alfa.core.model.OblFiscalesPerModel;
import co.com.periferia.alfa.core.repository.AdquirientesRepository;
import co.com.periferia.alfa.core.repository.ConsAdquirientesRepository;
import co.com.periferia.alfa.core.repository.DesplegablesRepository;
import co.com.periferia.alfa.core.repository.DetalleErrorRepository;
import co.com.periferia.alfa.core.repository.IConsultaAdquirienteRepository;
import co.com.periferia.alfa.core.repository.OblFiscalesPerRepository;
import co.com.periferia.alfa.core.services.IAdquirientesService;
import co.com.periferia.alfa.core.services.IReencolarDocumentoService;

@Component
public class AdquirientesServiceImpl implements IAdquirientesService {

	private static final Logger LOG = LoggerFactory.getLogger(AdquirientesServiceImpl.class);
	
	@Autowired
	private AdquirientesRepository adquirientesrepositorio;

	@Autowired
	private ConsAdquirientesRepository consadquirientesrepositorio;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	OblFiscalesPerRepository oblFiscalesPerRepository;

	@Autowired
	DesplegablesRepository desplegablesRepository;

	@Autowired
	DetalleErrorRepository detalleErrorRepository;
	
	@Autowired
	private IConsultaAdquirienteRepository adquirientesRepo;
	
	@Autowired
	private IReencolarDocumentoService reenviarDocumento;

	@Override
	public AdquirientesDTO actualizar(AdquirientesDTO dto, Integer id) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public AdquirientesDTO eliminar(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public List<AdquirientesDTO> buscarTodos() throws ExcepcionSegAlfa {
		return new ArrayList<>();
	}

	@Override
	public String editar(ConsultaAdquirienteDto dto, Integer documento, String tipo) throws ExcepcionSegAlfa {
		LOG.info("AdquirientesServiceImpl: Ingreso a metodo editar");
		AdquirientesModel model = null;
		try {
			model = adquirientesrepositorio.getOne(dto.getDni());
			dtoToModel(model, dto);
			adquirientesrepositorio.save(model);
			oblFiscalesPerRepository.eliminar(dto.getDni());
			for (DesplegablesDTO obl : dto.getObligacionesfiscales()) {
				OblFiscalesPerModel result = new OblFiscalesPerModel();
				result.setIdPer(dto.getDni());
				result.setCodFiscales(obl.getId());
				oblFiscalesPerRepository.save(result);
			}
			detalleErrorRepository.deleteById(dto.getDni());
			if(Boolean.TRUE.equals(dto.getReenviar())) 
				reenviarDocumento.reencolarDocumento(documento, tipo, dto.getDni());
		} catch (IllegalArgumentException | NullPointerException | EntityNotFoundException ex) {
			LOG.error("Error en metodo editar, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			throw utilException.getById(8).crearExcepcion();
		}
		return "Registro guardado correctamente";
	}
	
	private void dtoToModel(AdquirientesModel model, ConsultaAdquirienteDto dto) {
		model.setNombrerazonsocial(dto.getRazonSocial());
		model.setTelefono(dto.getTelefono());
		model.setDireccion(dto.getDireccion());
		model.setCorreo(dto.getCorreo());
		model.setCodpais(dto.getPais());
		model.setCoddepartamento(dto.getDepartamento());
		model.setCodciudad(dto.getCiudad());
		model.setContacto(dto.getNombreContacto());
		model.setAreapertenece(dto.getAreaPertenece());
		model.setFacturadorelectronico(dto.getFacturadorElectronico());
		model.setCodenviofe(dto.getAutorizaEntregaEmail());
		model.setRecibirxml(dto.getRecibeXml());
		model.setDeshabilitado(dto.getDeshabilitado());
		model.setCodtipotributo(dto.getTributo());
		model.setFechaactualizacion(dto.getFechaActualizacion());
		model.setCodRegimen(dto.getRegimen());
		model.setCodPostal(dto.getCodigoPostal());
		model.setCalidadcod(1);
	}

	@Override
	public AdquirientesDTO buscarPorID(Integer id) throws ExcepcionSegAlfa {
	   return null;
	}

	public AdquirientesDTO guardar(AdquirientesDTO dto) throws ExcepcionSegAlfa {
		LOG.info("AdquirientesServiceImpl: Ingreso a metodo guardar, Parametro - AdquirientesDTO {} ", dto);
		try {
			AdquirientesModel resultado = new AdquirientesModel();
			resultado.setPer(dto.getPer());
			resultado.setNombrerazonsocial(dto.getNombrerazonsocial());
			resultado.setNumidentificacion(dto.getNumidentificacion());
			resultado.setCodtipoidentificacion(dto.getCodtipoidentificacion());
			resultado.setDireccion(dto.getDireccion());
			resultado.setContacto(dto.getContacto());
			resultado.setCorreo(dto.getCorreo());
			resultado.setTelefono(dto.getTelefono());
			resultado.setAreapertenece(dto.getAreapertenece());
			resultado.setCalidadcod(1);
			adquirientesrepositorio.save(resultado);
			return dto;
		} catch (IllegalArgumentException | NullPointerException e) {
			LOG.error("Error en metodo guardar, error: {} | {}", e.getMessage(), e.getStackTrace());
			throw utilException.getById(7).crearExcepcion();
		}
	}

	@Override
	public List<ConsAdquirientesDTO> consultaAdquirientes(Integer id, String tipopersona, String numidentificacion)
			throws ExcepcionSegAlfa {
		LOG.info("AdquirientesServiceImpl: Ingreso a metodo consultaAdquirientes, parametros | id {} | tipoPersona {} | numidentificacion {}", id, tipopersona, numidentificacion);
		List<ConsAdquirientesDTO> result = new ArrayList<>();
		try {
			result = consadquirientesrepositorio.getAdquirientes(id, tipopersona, numidentificacion);
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("Error en metodo consultaAdquirientes, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			throw utilException.getById(5).crearExcepcion();
		}
		return result;
	}

	@Override
	public ConsultaAdquirienteDto consultar(Integer dni) {
		LOG.info("AdquirientesServiceImpl: Ingreso a metodo consulta");
		ConsultaAdquirientesModel consulta = adquirientesRepo.consulta(dni);
		ConsultaAdquirienteDto dto = new ConsultaAdquirienteDto();
		dto.setDni(dni);
		dto.setAreaPertenece(consulta.getAreaPertenece());
		dto.setAutorizaEntregaEmail(consulta.getAutorizaEntregaEmail());
		dto.setCiudad(consulta.getCiudad());
		dto.setCodigoPostal(consulta.getCodigoPostal());
		dto.setCorreo(consulta.getCorreo());
		dto.setDepartamento(consulta.getDepartamento());
		dto.setDeshabilitado(consulta.getDeshabilitado());
		dto.setDireccion(consulta.getDireccion());
		dto.setFacturadorElectronico(consulta.getFacturadorElectronico());
		dto.setNit(consulta.getIdentificacion());
		dto.setNombreContacto(consulta.getContacto());
		
		List<DesplegablesDTO> obligaciones = new ArrayList<>();
		List<OblFiscalesPerModel> obl = oblFiscalesPerRepository.findByPer(dni);
		for (OblFiscalesPerModel res : obl) {
			DesplegablesModel model = desplegablesRepository.findByobl(res.getCodFiscales());
			obligaciones.add(new DesplegablesDTO().modeloAdto(model));
		}
		dto.setObligacionesfiscales(obligaciones);
		
		dto.setPais(consulta.getPais());
		dto.setRazonSocial(consulta.getRazonSocial());
		dto.setRecibeXml(consulta.getRecibeXml());
		dto.setRegimen(consulta.getRegimen());
		dto.setTelefono(consulta.getTelefono());
		dto.setTipoDocumento(consulta.getTipoIdentificacion());
		dto.setTipoPersona(consulta.getTipoPersona());
		dto.setTributo(consulta.getTributo());
		return dto;
	}

}
