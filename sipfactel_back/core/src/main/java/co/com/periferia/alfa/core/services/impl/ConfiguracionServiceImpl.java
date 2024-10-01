package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConfiguracionDTO;
import co.com.periferia.alfa.core.dto.OpcionesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.ConfiguracionModel;
import co.com.periferia.alfa.core.model.OpcionesModel;
import co.com.periferia.alfa.core.repository.ConfiguracionRepository;
import co.com.periferia.alfa.core.repository.OpcionesRepository;
import co.com.periferia.alfa.core.services.ConfiguracionService;

@Service(value = "ConfiguracionService")
public class ConfiguracionServiceImpl implements ConfiguracionService {

	@Autowired
	ConfiguracionRepository configuracionrepo;

	@Autowired
	OpcionesRepository opcionesrepo;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	UtilExcecion utilException;

	public static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionServiceImpl.class);

	@Override
	public List<OpcionesDTO> listaOpciones() throws ExcepcionSegAlfa {
		LOGGER.info("ConfiguracionServiceImpl: Se inicia la busqueda de la lista de opciones");
		try {
			List<OpcionesModel> result = opcionesrepo.findByTipo();
			LOGGER.info("Se encontro la siguiente cantidad de opciones {}", result.size());
			return result.stream().map(e -> new OpcionesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en metodo listaOpciones, error: {} | {} ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}

	}

	@Override
	public List<OpcionesDTO> listaAdquirientes() throws ExcepcionSegAlfa {
		LOGGER.info("ConfiguracionServiceImpl: Se inicia la busqueda de la lista de opciones (listaAdquirientes)");
		try {
			List<OpcionesModel> result = opcionesrepo.findByTipo();
			LOGGER.info("Se encontro la siguiente cantidad de opciones {}", result.size());
			return result.stream().map(e -> new OpcionesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en metodo listaAdquirientes, error: {} | {} ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}

	}

	@Override
	public List<ConfiguracionDTO> listaTablas(Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("ConfiguracionServiceImpl: Se inicia la busqueda de los datos de la tabla, parametro - id: {} ",
				id);
		try {
			List<ConfiguracionModel> resultado = configuracionrepo.findByTipo(id);
			LOGGER.info("Se encontro la siguiente cantidad de datos {}", resultado.size());
			return resultado.stream().map(e -> new ConfiguracionDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en el metodo listaTablas, error: {} | {} ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public ConfiguracionDTO guardar(ConfiguracionDTO dto) throws ExcepcionSegAlfa {
		LOGGER.info("ConfiguracionServiceImpl: Se inicia el guardado, parametro - ConfiguracionDTO: {} ", dto);
		ConfiguracionModel resultado1 = configuracionrepo.findByRepetido(dto.getCodigo(), dto.getPadre());
		if (resultado1 == null) {
			try {
				ConfiguracionModel resultado = new ConfiguracionModel();
				resultado.setLlave(dto.getCodigo());
				resultado.setValor(dto.getNombre());
				resultado.setSistema(dto.getSistema());
				resultado.setPadre(dto.getPadre());
				resultado.setDescripcion(dto.getDescripcion());
				resultado.setUsuarioCreacion(dto.getUcrea());
				resultado.setFechaCreacion(new Date());
				resultado.setUsuarioAct(dto.getUactualiza());
				resultado.setFechaAct(dto.getFactualiza());
				resultado.setEstado(dto.getEstado());
				resultado = configuracionrepo.save(resultado);
				return new ConfiguracionDTO().modeloAdto(resultado);
			} catch (IllegalArgumentException | NullPointerException e) {
				LOGGER.error("Error en metodo guardar, error: {} | {} ", e.getMessage(), e.getStackTrace());
				throw utilException.getById(7).crearExcepcion();
			}
		} else {
			LOGGER.error("Error en metodo guardar, resultado1 = {} ", resultado1);
			throw utilException.getById(13).crearExcepcion();
		}
	}

	@Override
	public ConfiguracionDTO actualizar(ConfiguracionDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("ConfiguracionServiceImpl: Se inicia la actualizacion, parametros | ConfiguracionDTO: {} | id: {} ",
				dto, id);
		ConfiguracionModel resultado1 = configuracionrepo.findByRepetido(dto.getCodigo(), dto.getPadre());
		boolean flag = false;
		if (resultado1 == null) {
			flag = true;
		}
		if (flag || (resultado1.getConf().equals(id) && (!resultado1.getLlave().equals(dto.getCodigo())
				|| !resultado1.getValor().equals(dto.getNombre())))) {
			try {
				ConfiguracionModel resultado = configuracionrepo.getOne(id);
				resultado.setLlave(dto.getCodigo());
				resultado.setValor(dto.getNombre());
				resultado.setDescripcion(dto.getDescripcion());
				resultado.setUsuarioAct(dto.getUactualiza());
				resultado.setFechaAct(new Date());
				resultado.setEstado(dto.getEstado());
				configuracionrepo.save(resultado);
				resultado = configuracionrepo.getOne(id);
				return new ConfiguracionDTO().modeloAdto(resultado);
			} catch (Exception e) {
				LOGGER.error("Error en metodo actualizar, error: {} | {} ", e.getMessage(), e.getStackTrace());
				throw utilException.getById(8).crearExcepcion();
			}
		} else {
			LOGGER.error("Error en metodo actualizar, no entro a ningun if");
			throw utilException.getById(13).crearExcepcion();
		}
	}

	@Override
	public ConfiguracionDTO eliminar(Integer llave) {
		LOGGER.info("ConfiguracionServiceImpl: Ingreso a metodo eliminar, parametro - llave: {} ", llave);
		return null;
	}

	@Override
	public List<ConfiguracionDTO> buscarTodos() {
		LOGGER.info("ConfiguracionServiceImpl: Ingreso a metodo buscarTodos");
		return new ArrayList<>();
	}

	@Override
	public ConfiguracionDTO buscarPorID(Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("ConfiguracionServiceImpl: Ingreso a metodo buscarPorID, parametro - id: {} ", id);
		ConfiguracionModel resultado = new ConfiguracionModel();
		try {
			resultado = configuracionrepo.getOne(id);
			return new ConfiguracionDTO().modeloAdto(resultado);
		} catch (Exception ex) {
			LOGGER.error("Error en metodo buscarPorId, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			throw utilException.getById(5).crearExcepcion();
		}
	}

}
