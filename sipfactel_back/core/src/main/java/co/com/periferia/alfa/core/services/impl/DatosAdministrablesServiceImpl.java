package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.DatosAdministrablesDTO;
import co.com.periferia.alfa.core.dto.DesplegablesDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.DatosAdministrablesModel;
import co.com.periferia.alfa.core.model.DesplegablesModel;
import co.com.periferia.alfa.core.model.OblFiscalesValDefModel;
import co.com.periferia.alfa.core.repository.DatosAdministrablesRepository;
import co.com.periferia.alfa.core.repository.DesplegablesRepository;
import co.com.periferia.alfa.core.repository.OblFiscalesValDefRepository;
import co.com.periferia.alfa.core.services.DatosAdministrablesService;

@Component
public class DatosAdministrablesServiceImpl implements DatosAdministrablesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatosAdministrablesServiceImpl.class);

	@Autowired
	UtilExcecion utilException;

	@Autowired
	OblFiscalesValDefRepository oblFiscalesValDefRepo;

	@Autowired
	private DatosAdministrablesRepository datosAdministrablesRepo;

	@Autowired
	DesplegablesRepository desplegablesRepository;

	@Override
	public String editar(DatosAdministrablesDTO dto, Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("DatosAdministrablesServiceImpl: Se agrega cambios a la tabla y crea nuevo registro con los cambios, parametros | DatosAdministrablesDTO: {} || id: {} ", dto, id);
		boolean resp;
		try {
		DatosAdministrablesDTO resultado2 = new DatosAdministrablesDTO();
		DatosAdministrablesModel resultado = datosAdministrablesRepo.getOne(id);
		resultado2 = resultado2.modeloAdto(resultado);
		resp = dto.comparar(resultado2);
		if (!resp) {
				resultado.setEstado("FALSE");
				datosAdministrablesRepo.save(resultado);
				resultado = dto.dtoAModelo(dto);
				resultado.setEstado("TRUE");
				resultado.setUcreacion(resultado2.getUcreacion());
				resultado.setFcreacion(resultado2.getFcreacion());
				resultado.setFactualizacion(new Date());
				datosAdministrablesRepo.save(resultado);
				oblFiscalesValDefRepo.eliminar(id);
				for (DesplegablesDTO obl : dto.getObligacionesfiscales()) {
					OblFiscalesValDefModel result = new OblFiscalesValDefModel();
					result.setIdValorPordefecto(id);
					result.setCodFiscales(obl.getId());
					oblFiscalesValDefRepo.save(result);
				}
				return "El registro se edito correctamente";
		} else {
			return "No se guardo el registro porque no se hizo ningun cambio";
		}
		} catch (IllegalArgumentException | EntityNotFoundException ex) {
			LOGGER.error("Error en editar, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
			throw utilException.getById(8).crearExcepcion();
		}
	}

	@Override
	public DatosAdministrablesDTO buscarPorID(Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("DatosAdministrablesServiceImpl: Ingreso al metodo buscarPorID, parametro - id: {} ", id);
		DatosAdministrablesModel resultado = new DatosAdministrablesModel();
		List<DesplegablesDTO> desple = new ArrayList<>();
		try {
			resultado = datosAdministrablesRepo.getOne(id);
			List<OblFiscalesValDefModel> obl = oblFiscalesValDefRepo.findByValorPorDefecto(id);
			for (OblFiscalesValDefModel res : obl) {
				DesplegablesModel model = desplegablesRepository.findByobl(res.getCodFiscales());
				desple.add(new DesplegablesDTO().modeloAdto(model));
			}
			return new DatosAdministrablesDTO().modeloAdtolist(resultado, desple);
		} catch (Exception ex) {
			LOGGER.error("Error en buscarPorID, error: {} | {}", ex.getMessage(), ex.getStackTrace());
			throw utilException.getById(5).crearExcepcion();
		}
	}

	@Override
	public List<DatosAdministrablesDTO> buscarTodos() throws ExcepcionSegAlfa {
		LOGGER.info("DatosAdministrablesServiceImpl: Se inicia la busqueda de todos los datos de la tabla");
		List<DesplegablesDTO> desple = new ArrayList<>();
		List<DatosAdministrablesDTO> resp = new ArrayList<>();
		try {
			List<DatosAdministrablesModel> resultado = datosAdministrablesRepo.findTrue();
			for (DatosAdministrablesModel datos : resultado) {
				List<OblFiscalesValDefModel> obl = oblFiscalesValDefRepo.findByValorPorDefecto(datos.getVad());
				for (OblFiscalesValDefModel res : obl) {
					DesplegablesModel model = desplegablesRepository.findByobl(res.getCodFiscales());
					desple.add(new DesplegablesDTO().modeloAdto(model));
				}
				resp.add(new DatosAdministrablesDTO().modeloAdtolist(datos,desple));
			}
			return resp;
		} catch (Exception e) {
			LOGGER.error("Erroe en buscarTodos, Error: {} | {} ", e.getMessage(), e.getStackTrace());
			throw utilException.getById(6).crearExcepcion();
		}
	}

}
