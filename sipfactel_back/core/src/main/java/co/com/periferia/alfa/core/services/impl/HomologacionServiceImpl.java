package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.HomologacionDTO;
import co.com.periferia.alfa.core.dto.OpcHomologacionDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.ConfiguracionModel;
import co.com.periferia.alfa.core.model.HomologacionModel;
import co.com.periferia.alfa.core.model.OpcHomologacionModel;
import co.com.periferia.alfa.core.repository.ConfiguracionRepository;
import co.com.periferia.alfa.core.repository.HomologacionRepository;
import co.com.periferia.alfa.core.repository.OpcHomologacionRepository;
import co.com.periferia.alfa.core.services.HomologacionService;

@Service(value = "HomologacionService")
public class HomologacionServiceImpl implements HomologacionService {

	@Autowired
	OpcHomologacionRepository homologacionRepo;

	@Autowired
	HomologacionRepository homologacionrepository;

	@Autowired
	ConfiguracionRepository config;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	UtilExcecion utilException;

	public static final Logger LOGGER = LoggerFactory.getLogger(HomologacionServiceImpl.class);

	@Override
	public List<OpcHomologacionDTO> listaOpciones() throws ExcepcionSegAlfa {
		LOGGER.info("Se inicia la busqueda de la lista de opciones");
		try {
			List<OpcHomologacionModel> result = homologacionRepo.findByTipo();
			LOGGER.info("Se encontro la siguiente cantidad de opciones {}", result.size());
			return result.stream().map(e -> new OpcHomologacionDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar listaOpciones");
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<HomologacionDTO> listaTablas(Integer id) throws ExcepcionSegAlfa {
		List<HomologacionDTO> result = new ArrayList<>();
		HomologacionDTO resp = new HomologacionDTO();
		try {
			LOGGER.info("Se inicia la busqueda de los datos de la tabla - paramtros: id {} ", id);
			List<HomologacionModel> resultado = homologacionrepository.findByTipo(id);
			for (HomologacionModel homologacionModel : resultado) {
				ConfiguracionModel conf = config.findByNombreHomo(homologacionModel.getFuente());
				resp = resp.modeloAdto(homologacionModel);
				resp.setNomfuente(conf.getValor());
				result.add(resp);
			}
			LOGGER.info("Se encontro la siguiente cantidad de datos {}", resultado.size());
			return result;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar listaTablas");
			throw utilException.getById(5).crearExcepcion();
		}
	}

	@Override
	public HomologacionDTO guardar(HomologacionDTO dto) throws ExcepcionSegAlfa {
		HomologacionModel resultado1;
		resultado1 = homologacionrepository.findByRepetido(dto.getFuente(), dto.getOriginal());
		if (resultado1 == null) {
			try {
				LOGGER.info("Se inicia el servicio guardar");
				HomologacionModel resultado = new HomologacionModel();
				resultado.setConf(dto.getConf());
				resultado.setFuente(dto.getFuente());
				resultado.setOrigen(dto.getOriginal());
				resultado.setHomologado(dto.getHomologado());
				resultado.setDescripcion(dto.getDescripcion());
				resultado.setUsuarioCreacion(dto.getUcrea());
				resultado.setFechaCreacion(new Date());
				resultado.setUsuarioAct(dto.getUactualiza());
				resultado.setFechaAct(dto.getFactualiza());
				resultado.setEstado("ACT");
				homologacionrepository.save(resultado);
				return dto;
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOGGER.error("Error al ejecutar guardar");
				throw utilException.getById(7).crearExcepcion();
			}
		} else {
			throw utilException.getById(13).crearExcepcion();
		}
	}

	@Override
	public HomologacionDTO actualizar(HomologacionDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("Se inicia la actualizaci�n de los datos de la tabla");
		HomologacionModel resultado1;
		resultado1 = homologacionrepository.findByRepetido(dto.getFuente(), dto.getOriginal());
		boolean flag = false;
		if (resultado1 == null) {
			flag = true;
		}
		if (flag || (!resultado1.getFuente().equals(dto.getFuente())
				|| resultado1.getOrigen().equals(dto.getOriginal()) && resultado1.getHomo().equals(id))) {
			try {
				LOGGER.info("Se inicia el servicio actualizar- parametros id: {} ", id);
				HomologacionModel resultado = homologacionrepository.getOne(id);
				resultado.setConf(dto.getConf());
				resultado.setFuente(dto.getFuente());
				resultado.setHomologado(dto.getHomologado());
				resultado.setOrigen(dto.getOriginal());
				resultado.setDescripcion(dto.getDescripcion());
				resultado.setUsuarioAct(dto.getUactualiza());
				resultado.setFechaAct(dto.getFactualiza());
				resultado.setEstado(dto.getEstado());
				resultado = homologacionrepository.save(resultado);
				dto = dto.modeloAdto(resultado);
				dto.setEstado(resultado.getEstado());
				return dto;
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOGGER.error("Error al ejecutar actualizar");
				throw utilException.getById(8).crearExcepcion();
			}
		} else {
			throw utilException.getById(13).crearExcepcion();
		}
	}

	@Override
	public HomologacionDTO eliminar(Integer id) throws ExcepcionSegAlfa {
		HomologacionModel resultado = null;
		try {
			resultado = homologacionrepository.getOne(id);
		} catch (IllegalArgumentException | NullPointerException | NoResultException e) {
			LOGGER.warn("Ha ocurrido un error durante la consulta");
			resultado = null;
		}
		if (resultado != null) {
			try {
				LOGGER.info("Se inicia el servicio eliminar- parametros id: {} ", id);
				resultado.setEstado("INACT");
				homologacionrepository.save(resultado);
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOGGER.error("Error al ejecutar eliminar");
				throw utilException.getById(9).crearExcepcion();
			}
		} else {
			throw utilException.getById(5).crearExcepcion();
		}

		return null;
	}

	@Override
	public List<HomologacionDTO> buscarTodos() throws ExcepcionSegAlfa {
		return new ArrayList<>();
	}

	@Override
	public HomologacionDTO buscarPorID(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

}
