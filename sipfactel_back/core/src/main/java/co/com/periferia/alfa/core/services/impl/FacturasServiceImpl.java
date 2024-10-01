package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.AdquirientesDTO;
import co.com.periferia.alfa.core.dto.FacturasDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.AdquirientesModel;
import co.com.periferia.alfa.core.model.FacturasModel;
import co.com.periferia.alfa.core.repository.AdquirientesRepository;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.services.IFacturasService;

@Component
public class FacturasServiceImpl implements IFacturasService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FacturasServiceImpl.class);

	@Autowired
	private FacturasRepository facturasrepositorio;

	@Autowired
	private AdquirientesRepository adquirientesRepositorio;

	@Autowired
	UtilExcecion utilException;

	@Override
	public FacturasDTO buscarPorFacturas(String numdoc) throws ExcepcionSegAlfa {
		LOGGER.info("Se inicia la busqueda de los datos de usuario de la tabla");
		try {
			LOGGER.info("Ejecutando buscar por facturas - Parametros |numdoc: {}", numdoc);
			FacturasModel resultado = facturasrepositorio.findByFacturas(numdoc);
			if (resultado == null) {
				throw utilException.getById(5).crearExcepcion();
			}
			return new FacturasDTO().modeloAdto(resultado);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar buscarPorFacturas");
			throw utilException.getById(5).crearExcepcion();
		}
	}


	@Override
	public FacturasDTO traerTransaccion(Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("Se editan registros de la tabla");
		FacturasModel resultado = new FacturasModel();
		try {
			LOGGER.info("Ejecutando traer transaccion - Parametros |id: {}", id );
			resultado = facturasrepositorio.getOne(id);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar traerTransaccion");
			throw utilException.getById(5).crearExcepcion();
		}
		return new FacturasDTO().modeloAdto(resultado);
	}

	@Override
	public AdquirientesDTO traerAdquiriente(Integer id) throws ExcepcionSegAlfa {
		LOGGER.info("Se editan registros de la tabla");
		AdquirientesModel resultado = new AdquirientesModel();
		try {
			LOGGER.info("Ejecutando traer adquirientes - Parametros |id: {}", id);
			resultado = adquirientesRepositorio.getOne(id);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException ex) {
			LOGGER.error("Error al ejecutar traerAdquiriente");
			throw utilException.getById(5).crearExcepcion();
		}
		return new AdquirientesDTO().modeloAdto(resultado);
	}


	@Override
	public FacturasDTO guardar(FacturasDTO clase) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public FacturasDTO actualizar(FacturasDTO clase, Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public FacturasDTO eliminar(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public List<FacturasDTO> buscarTodos() throws ExcepcionSegAlfa {
		return new ArrayList<>();
	}

	@Override
	public FacturasDTO buscarPorID(Integer llave) throws ExcepcionSegAlfa {
		return null;
	}

	@Override
	public String editarAdquiriente(AdquirientesDTO dto, Integer id) throws ExcepcionSegAlfa {
		return null;
	}

}
