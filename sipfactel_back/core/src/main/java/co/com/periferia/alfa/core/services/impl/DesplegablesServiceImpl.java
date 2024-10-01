package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import co.com.periferia.alfa.core.dto.DesplegableCategoriaDTO;
import co.com.periferia.alfa.core.dto.DesplegableSucursalesDTO;
import co.com.periferia.alfa.core.dto.DesplegablesDTO;
import co.com.periferia.alfa.core.dto.FuenteDTO;
import co.com.periferia.alfa.core.dto.PorcentajesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.DesplegableCategoriaModel;
import co.com.periferia.alfa.core.model.DesplegableSucursalesModel;
import co.com.periferia.alfa.core.model.DesplegablesModel;
import co.com.periferia.alfa.core.model.FuenteModel;
import co.com.periferia.alfa.core.model.ParametrosModel;
import co.com.periferia.alfa.core.model.PorcentajesModel;
import co.com.periferia.alfa.core.repository.DesplegableCategoriaRepository;
import co.com.periferia.alfa.core.repository.DesplegableSucursalesRepository;
import co.com.periferia.alfa.core.repository.DesplegablesRepository;
import co.com.periferia.alfa.core.repository.FuenteRepository;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.repository.PorcentajesRepository;
import co.com.periferia.alfa.core.services.DesplegablesService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

@Service(value = "DesplegablesService")
public class DesplegablesServiceImpl implements DesplegablesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesplegablesServiceImpl.class);
	
	@Autowired
	public JwtTokenData jtd;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	DesplegablesRepository opcConfiguracionRepository;

	@Autowired
	DesplegableSucursalesRepository desplegableSucursalesRepo;

	@Autowired
	PorcentajesRepository porcentajesRepository;

	@Autowired
	DesplegablesRepository desplegablePorcentajeRepository;

	@Autowired
	DesplegableCategoriaRepository desplegableCategoriaRepo;
	
	@Autowired
	FuenteRepository fuenteRepository;
	
	@Autowired
	IParametrosRepository parametrosRepository;

	@Override
	public List<DesplegablesDTO> listaFormaPago() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaFormaPago");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByFormaPago();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaFormaPago, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaTipoPersona() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaTipoPersona");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByTipoPersona();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaTipoPersona, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaTipoIdentificacion() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaTipoIdentificacion");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByTipoIdentificacion();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaTipoIdentificacion, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaOblFiscales() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaOblFiscales");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByOblFiscales();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaOblFiscales, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaPaises() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaPaises");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByPaises();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaPaises, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaCiudades() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaCiudades");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByCiudades();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaCiudades, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaDepartamentos() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaDepartamentos");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByDepartamentos();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaDepartamentos, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaCodTributo() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaCodTributo");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByCodTributo();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaCodTributo, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaRegFiscal() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaRegFiscal");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByRegfiscal();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaRegFiscal, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaUmedidas() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaUmedidas");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByUmedidas();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaUmedidas, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaProductos() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaProductos");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByProductos();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaProductos, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaEnvioFE() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaEnvioFE");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByEnvioFE();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaEnvioFE, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaAmbDestino() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaAmbDestino");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByAmbDestino();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaAmbDestino, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaMonedas() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaMonedas");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByMonedas();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaMonedas, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaMediosPago() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaMediosPago");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByMediosPago();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaMediosPago, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<PorcentajesDTO> listaPorcentajes() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaPorcentajes");
		try {
			List<PorcentajesModel> result = porcentajesRepository.findByPorc();
			return result.stream().map(e -> new PorcentajesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaPorcentajes, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaTiposDocumento() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaTiposDocumento");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByTiposDoc();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaTiposDocumento, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegableSucursalesDTO> listaSucursales() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaSucursales");
		try {
			List<DesplegableSucursalesModel> result = desplegableSucursalesRepo.findBySucursales();
			return result.stream().map(e -> new DesplegableSucursalesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaSucursales, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaEstadoFac() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaEstadoFac");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByEstadoFac();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaEstadoFac, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegablesDTO> listaTipoOperacion() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaTipoOperacion");
		try {
			List<DesplegablesModel> result = opcConfiguracionRepository.findByTipoOperacion();
			return result.stream().map(e -> new DesplegablesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaTipoOperacion, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<DesplegableCategoriaDTO> listaCategorias() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaCategorias");
		try {
			List<DesplegableCategoriaModel> result = desplegableCategoriaRepo.findAll();
			return result.stream().map(e -> new DesplegableCategoriaDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaCategorias, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}
	
	@Override
	public List<FuenteDTO> listaFuente() throws ExcepcionSegAlfa {
		LOGGER.info("DesplegablesServiceImpl: Ingreso al metodo listaFuente");
		try {
			List<FuenteModel> result = fuenteRepository.findByFuente();
			return result.stream().map(e -> new FuenteDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaFuente, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

	/**
	 * Metodo que arma una lsita con los datos de los tipos de polizas para resoluciones
	 */
	
	@Override
	public List<DesplegablesDTO> listaTipoPolizas() throws ExcepcionSegAlfa {
		LOGGER.info("Ingreso a bsucar lista de polizas de resolucion");
		List<String> valoresParametros = new ArrayList<>();
		valoresParametros.add(FacEnum.GENERAL.getValor());
		valoresParametros.add(FacEnum.ARL.getValor());
		valoresParametros.add(FacEnum.PREVISIONAL.getValor());
		valoresParametros.add(FacEnum.VITALICIA.getValor());
		List<ParametrosModel> parametros = parametrosRepository.buscarListaNombres(valoresParametros);
		List<DesplegablesDTO> desplegableDto = new ArrayList<>();
		parametros.stream().forEach(p -> {
			DesplegablesDTO dto = new DesplegablesDTO();
			dto.setIdStr(p.getValor());
			dto.setNombre(p.getNombre());
			desplegableDto.add(dto);
		});
		return desplegableDto;
	}

	@Override
	public List<PorcentajesDTO> listaPorcentajesByImpuesto(String impuesto) throws ExcepcionSegAlfa {
		LOGGER.info("Ingreso a buscar porcentaje por impuesto");
		try {
			List<PorcentajesModel> result = porcentajesRepository.findByImpuesto(impuesto);
			return result.stream().map(e -> new PorcentajesDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en listaPorcentajes, error: {} | {}  ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}
}
