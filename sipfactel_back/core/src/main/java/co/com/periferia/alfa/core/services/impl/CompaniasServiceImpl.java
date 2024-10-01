package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.CompaniasDTO;
import co.com.periferia.alfa.core.dto.DesplegablesDTO;
import co.com.periferia.alfa.core.dto.OpcCompaniasDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.CompaniasModel;
import co.com.periferia.alfa.core.model.DesplegablesModel;
import co.com.periferia.alfa.core.model.OblFiscalesModel;
import co.com.periferia.alfa.core.model.OpcCompaniasModel;
import co.com.periferia.alfa.core.repository.CompaniasRepository;
import co.com.periferia.alfa.core.repository.DesplegablesRepository;
import co.com.periferia.alfa.core.repository.OblFiscalesRepository;
import co.com.periferia.alfa.core.repository.OpcCompaniasRepository;
import co.com.periferia.alfa.core.services.CompaniasService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

@Service(value = "CompaniasService")
public class CompaniasServiceImpl implements CompaniasService {

	@Autowired
	OpcCompaniasRepository companiasrepo;

	@Autowired
	CompaniasRepository companiasrepository;

	@Autowired
	DesplegablesRepository opcConfiguracionRepository;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	UtilExcecion utilException;

	@Autowired
	OblFiscalesRepository oblFiscalesRepository;

	@Autowired
	DesplegablesRepository desplegablesRepository;

	public static final Logger LOGGER = LoggerFactory.getLogger(CompaniasServiceImpl.class);

	@Override
	public String editar(CompaniasDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOGGER.info(
				"CompaniasServiceImpl: Se agrega cambios a la tabla y crea nuevo registro con los cambios, parametros | CompaniasDTO: {} | id: {} ",
				dto, id);
		boolean resp;
		CompaniasDTO resultado2 = new CompaniasDTO();
		List<CompaniasModel> existe;
		boolean flag = true;
		resp = dto.comparar(resultado2);
		if (!resp) {
			existe = companiasrepository.findOthers(id);
			for (CompaniasModel comp : existe) {
				if (comp.getNombre().equals(dto.getNombre()) || comp.getCodpoint().equals(dto.getCodpoint())
						|| comp.getCodascele().equals(dto.getCodascele())) {
					flag = false;
				}
			}
			return complementoEditar(flag, id, dto);
		} else {
			LOGGER.warn("Advertencia en metodo editar, resp = {} ", resp);
			return "No se guardo el registro porque no se hizo ningun cambio";
		}
	}

	private String complementoEditar(boolean flag, Integer id, CompaniasDTO dto)
			throws ExcepcionSegAlfa {
		LOGGER.info("Ingreso a metodo complementoEditar, parametros: flag {} | id: {} ", flag, id);
		CompaniasModel resultado;
		if (flag) {
			try {
				resultado = companiasrepository.findById(id).orElse(null);
				if (resultado != null) {
					resultado.setEstado(FacEnum.FALSE.getValor());
					companiasrepository.save(generarCopiaModel(resultado));
					resultado = dto.dtoAModelo(dto);
					resultado.setFcreacion(new Date());
					resultado.setEstado(FacEnum.TRUE.getValor());
					resultado = companiasrepository.save(resultado);
					oblFiscalesRepository.eliminar(id);
					for (DesplegablesDTO obl : dto.getObligacionesfiscales()) {
						OblFiscalesModel result = new OblFiscalesModel();
						result.setIdComp(resultado.getComp());
						result.setCodFiscales(obl.getId());
						oblFiscalesRepository.save(result);
					}
					return "El registro se edito correctamente";
				} else {
					return "Ha ocurrdio un error";
				}

			} catch (IllegalArgumentException | EntityNotFoundException ex) {
				LOGGER.error("Error en metodo editar, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
				throw utilException.getById(8).crearExcepcion();
			}
		} else {
			LOGGER.error("Error en metodo editar, flag = {} ", flag);
			throw utilException.getById(15).crearExcepcion();
		}
	}

	public CompaniasModel generarCopiaModel(CompaniasModel model) {

		CompaniasModel modelo = new CompaniasModel();
		modelo.setNombre(model.getNombre());
		modelo.setCodascele(model.getCodascele());
		modelo.setTipodocumento(model.getTipodocumento());
		modelo.setCodigopais(model.getCodigopais());
		modelo.setRazonsocial(model.getRazonsocial());
		modelo.setCodpoint(model.getCodpoint());
		modelo.setDocumento(model.getDocumento());
		modelo.setNombrepais(model.getNombrepais());
		modelo.setTipopersona(model.getTipopersona());
		modelo.setFacelectronica(model.getFacelectronica());
		modelo.setNotacredito(model.getNotacredito());
		modelo.setNotadebito(model.getNotadebito());
		modelo.setCodempresa(model.getCodempresa());
		modelo.setCodestandar(model.getCodestandar());
		modelo.setCodatributo(model.getCodatributo());
		modelo.setNomatributo(model.getNomatributo());
		modelo.setPorcatributo(model.getPorcatributo());
		modelo.setCantidad(model.getCantidad());
		modelo.setFormapago(model.getFormapago());
		modelo.setMetodopago(model.getMetodopago());
		modelo.setUndmedida(model.getUndmedida());
		modelo.setEstado(model.getEstado());
		modelo.setFcreacion(model.getFcreacion());
		modelo.setUcreacion(model.getUcreacion());
		modelo.setNotadebh(model.getNotadebh());
		modelo.setNotacreh(model.getNotacreh());
		modelo.setRegimen(model.getRegimen());
		return modelo;
	}

	@Override
	public CompaniasDTO guardar(CompaniasDTO clase) throws ExcepcionSegAlfa {
		LOGGER.info("CompaniasServiceImpl: Se crea un nuevo registro, parametro - CompaniasDTO: {} ", clase);
		CompaniasModel modelo;
		boolean flag = true;
		List<CompaniasModel> existe = companiasrepository.findTrue();
		for (CompaniasModel comp : existe) {
			if (comp.getNombre().equals(clase.getNombre()) || comp.getCodpoint().equals(clase.getCodpoint())
					|| comp.getCodascele().equals(clase.getCodascele())) {
				flag = false;
			}
		}
		if (flag) {
			try {
				modelo = clase.dtoAModelo(clase);
				modelo = companiasrepository.save(modelo);
				for (DesplegablesDTO obl : clase.getObligacionesfiscales()) {
					OblFiscalesModel result = new OblFiscalesModel();
					result.setIdComp(modelo.getComp());
					result.setCodFiscales(obl.getId());
					oblFiscalesRepository.save(result);
				}
				return new CompaniasDTO().modeloAdto(modelo);
			} catch (Exception e) {
				throw utilException.getById(7).crearExcepcion(e.getCause());
			}
		} else {
			LOGGER.error("Error en metodo guardar, flag = {} ", flag);
			throw utilException.getById(15).crearExcepcion();
		}
	}

	@Override
	public CompaniasDTO actualizar(CompaniasDTO clase, Integer llave) throws ExcepcionSegAlfa {
		LOGGER.info("CompaniasServiceImpl: Ingreso a metodo actualizar, parametros | CompaniasDTO: {} | llave: {} ",
				clase, llave);
		return null;
	}

	@Override
	public CompaniasDTO eliminar(Integer llave) throws ExcepcionSegAlfa {
		LOGGER.info("CompaniasServiceImpl: Ingreso a metodo eliminar, parametros- llave: {} ", llave);
		return null;
	}

	@Override
	public List<CompaniasDTO> buscarTodos() throws ExcepcionSegAlfa {
		LOGGER.info("CompaniasServiceImpl: Ingreso a metodo buscarTodos");
		return new ArrayList<>();
	}

	@Override
	public CompaniasDTO buscarPorID(Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("CompaniasServiceImpl: Ingreso a metodo buscarPorID, parametro - id: {} ", id);
		CompaniasModel resultado = new CompaniasModel();
		List<DesplegablesDTO> desple = new ArrayList<>();
		try {
			resultado = companiasrepository.getOne(id);
			List<OblFiscalesModel> obl = oblFiscalesRepository.findByComp(id);
			for (OblFiscalesModel res : obl) {
				DesplegablesModel model = desplegablesRepository.findByobl(res.getCodFiscales());
				desple.add(new DesplegablesDTO().modeloAdto(model));
			}
			return new CompaniasDTO().modeloAdtolist(resultado, desple);
		} catch (EntityNotFoundException | UnsupportedOperationException | ClassCastException | NullPointerException
				| IllegalArgumentException ex) {
			LOGGER.error("Error en metodo buscarPorId, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			throw utilException.getById(5).crearExcepcion();
		}
	}

	@Override
	public List<OpcCompaniasDTO> listaOpciones() throws ExcepcionSegAlfa {
		LOGGER.info("CompaniasServiceImpl: Se inicia la busqueda de la lista de opciones");
		try {
			List<OpcCompaniasModel> result = companiasrepo.findByTipo();
			LOGGER.info("Se encontro la siguiente cantidad de opciones {}", result.size());
			return result.stream().map(e -> new OpcCompaniasDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error en metodo listaOpciones, error: {} | {} ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

}
