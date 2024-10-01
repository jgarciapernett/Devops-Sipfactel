package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.CodigoPostalDTO;
import co.com.periferia.alfa.core.dto.SucursalesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.CodigoPostalModel;
import co.com.periferia.alfa.core.model.ConfiguracionModel;
import co.com.periferia.alfa.core.model.SucursalesModel;
import co.com.periferia.alfa.core.repository.CodigoPostalRepository;
import co.com.periferia.alfa.core.repository.ConfiguracionRepository;
import co.com.periferia.alfa.core.repository.ResolucionRepository;
import co.com.periferia.alfa.core.repository.SucursalesRepository;
import co.com.periferia.alfa.core.services.ISucursalesService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

@Service(value = "SucursalesService")
public class SucursalesServiceImpl implements ISucursalesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SucursalesServiceImpl.class);

	private List<CodigoPostalModel> list = new ArrayList<>();

	@Autowired
	SucursalesRepository sucursalesRepository;

	@Autowired
	CodigoPostalRepository codigoPostalRepository;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	ResolucionRepository resolucionRepository;

	@Autowired
	ConfiguracionRepository config;

	@Override
	public SucursalesDTO guardar(SucursalesDTO dto) throws ExcepcionSegAlfa {
		SucursalesModel resultado;
		List<SucursalesModel> existe;
		boolean flag = true;
		existe = sucursalesRepository.findByTipo();
		for (SucursalesModel sucu : existe) {
			if (sucu.getNomsuc().equals(dto.getNomsuc()) || sucu.getCodascele().equals(dto.getCodascele()) || sucu.getCodpoint().equals(dto.getCodpoint())) {
				flag = false;
			}
		}
		if (flag) {
			try {
				LOGGER.info("Ejecutando guardar- parametros | dto ");
				resultado = dto.dtoAModelo(dto);
				resultado = sucursalesRepository.save(resultado);
				return new SucursalesDTO().modeloAdto(resultado);
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				throw utilException.getById(7).crearExcepcion();
			}
		} else {
			throw utilException.getById(16).crearExcepcion();
		}
	}

	@Override
	public SucursalesDTO actualizar(SucursalesDTO dto, Integer id) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public SucursalesDTO eliminar(Integer llave) {
		return null;
	}

	@Override
	public List<SucursalesDTO> buscarTodos() {
		return new ArrayList<>();
	}

	@Override
	public SucursalesDTO buscarPorID(Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("Se inicia la busqueda de todos los datos de la tabla");
		SucursalesModel resultado = new SucursalesModel();
		try {
			LOGGER.info("Ejecutando buscarPorID- parametros | id: {} ", id);
			resultado = sucursalesRepository.getOne(id);
			return new SucursalesDTO().modeloAdto(resultado);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("ERROR al ejecutar buscarPorID, error: {} ", e.getMessage());
			throw utilException.getById(5).crearExcepcion();
		}
	}

	@Override
	public String editar(SucursalesDTO dto, Integer id) throws ExcepcionSegAlfa {
		SucursalesModel resultado;
		SucursalesDTO resultado2 = new SucursalesDTO();
		List<SucursalesModel> existe;
		boolean resp;
		existe = sucursalesRepository.findOthers(id);
		if (validaFlag(existe, dto)) {
			LOGGER.info("Validacion cambio de estado en sucursales");
			resultado = sucursalesRepository.getOne(id);
			resultado2 = resultado2.modeloAdto(resultado);
			resp = dto.comparar(resultado2);
			if (!resp) {
				resultado.setEstado(FacEnum.FALSE.getValor());
				sucursalesRepository.save(resultado);
				resultado = dto.dtoAModelo(dto);
				resultado.setEstado(FacEnum.TRUE.getValor());
				sucursalesRepository.save(resultado);
			}
			try {
				LOGGER.info("Ejecutando editar- parametros | id: {} ", id);
				return "Registro guardado correctamente";
			} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
				LOGGER.error("ERROR al ejecutar editar, error: {} ", e.getMessage());
				throw utilException.getById(8).crearExcepcion();
			}
		} else {
			throw utilException.getById(16).crearExcepcion();
		}
	}
	
	/**
	 * Metodo que valida el flag se sucursales
	 * @param existe ---> Lista de sucursales
	 * @param dto ---> Objeto SucursalesDto
	 * @return
	 */
	private boolean validaFlag(List<SucursalesModel> existe, SucursalesDTO dto) {
		boolean flag = true;
		for (SucursalesModel sucu : existe) {
			if (sucu.getNomsuc().equals(dto.getNomsuc()) || sucu.getCodascele().equals(dto.getCodascele()) || sucu.getCodpoint().equals(dto.getCodpoint())) {
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public List<SucursalesDTO> consultarDatos() throws ExcepcionSegAlfa {
		List<SucursalesDTO> resp = new ArrayList<>();
		SucursalesDTO resp1 = new SucursalesDTO();
		ConfiguracionModel ciudad = null;
		ConfiguracionModel depart = null;
		LOGGER.info("Se hace la consulta de todos los datos de la tabla");
		try {
			List<SucursalesModel> resultado = sucursalesRepository.findByTipo();
			for (SucursalesModel sucursalesModel : resultado) {
				ciudad = config.findByNombre(sucursalesModel.getCodciudad());
				depart = config.findByNombre(sucursalesModel.getCoddepart());
				resp1 = resp1.modeloAdto(sucursalesModel);
				resp1.setNomciudad(ciudad.getValor());
				resp1.setNomdepart(depart.getValor());
				resp.add(resp1);
			}
			return resp;
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("ERROR al ejecutar consultarDatos, error: {} ", e.getMessage());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<CodigoPostalDTO> consCodigoPostal() throws ExcepcionSegAlfa {
		LOGGER.info("Se hace la consulta de todos los datos de la tabla");
		List<CodigoPostalDTO> resp = new ArrayList<>();
		try {
			if (list.isEmpty()) {
				list = codigoPostalRepository.findAll();
				resp = list.stream().map(e -> new CodigoPostalDTO().modeloAdto(e)).collect(Collectors.toList());
			} else {
				resp = list.stream().map(e -> new CodigoPostalDTO().modeloAdto(e)).collect(Collectors.toList());
			}
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("ERROR al ejecutar consCodigoPostal, error: {} ", e.getMessage());
			throw utilException.getById(6).crearExcepcion();
		}
		return resp;
	}

}
