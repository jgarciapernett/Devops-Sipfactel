package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.DatosGeneralesDTO;
import co.com.periferia.alfa.core.dto.DatosProveedorDTO;
import co.com.periferia.alfa.core.dto.ParametrosDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.DatosGeneralesModel;
import co.com.periferia.alfa.core.model.DatosProveedorModel;
import co.com.periferia.alfa.core.model.ParametrosModel;
import co.com.periferia.alfa.core.repository.DatosGeneralesRepository;
import co.com.periferia.alfa.core.repository.DatosProveedoresRepository;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.services.ParametrosService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

@Component
public class ParametrosServiceImpl implements ParametrosService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ParametrosServiceImpl.class);

	@Autowired
	DatosGeneralesRepository datosGeneralesRepo;

	@Autowired
	DatosProveedoresRepository datosProveedoresRepoRepo;
	
	@Autowired
	IParametrosRepository parametrosRepository;

	@Autowired
	UtilExcecion utilException;

	@Override
	public String agregarGenerales(DatosGeneralesDTO dto) throws ExcepcionSegAlfa {
		DatosGeneralesModel resultado = new DatosGeneralesModel();
		try {
			LOGGER.info("Ejecutando agregarGenerales - Parametros |dto ");
			resultado = datosGeneralesRepo.findByLast();
			if (resultado.getEstado().equals(FacEnum.TRUE.getValor())) {
				resultado.setEstado(FacEnum.FALSE.getValor());
				datosGeneralesRepo.save(resultado);
			}
			resultado = dto.dtoAModelo(dto);
			resultado.setEstado(FacEnum.TRUE.getValor());
			datosGeneralesRepo.save(resultado);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar agregarGenerales");
			throw utilException.getById(7).crearExcepcion();
		}
		return FacEnum.REGISTRO_GUARDADO.getValor();
	}

	@Override
	public DatosGeneralesDTO buscarGenerales() throws ExcepcionSegAlfa {
		DatosGeneralesModel resultado = new DatosGeneralesModel();
		try {
			LOGGER.info("Ejecutando buscarGenerales ");
			resultado = datosGeneralesRepo.findByLast();
			return new DatosGeneralesDTO().modeloAdto(resultado);
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar buscarGenerales");
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public String agregarProveedor(DatosProveedorDTO dto) throws ExcepcionSegAlfa {
		DatosProveedorModel resultado = new DatosProveedorModel();
		try {
			LOGGER.info("Ejecutando agregarProveedor - Parametros |dto ");
			resultado = datosProveedoresRepoRepo.findByLast();
			if (resultado.getEstado().equals(FacEnum.TRUE.getValor())) {
				resultado.setEstado(FacEnum.FALSE.getValor());
				datosProveedoresRepoRepo.save(resultado);
			}
			resultado = dto.dtoAModelo(dto);
			resultado.setEstado(FacEnum.TRUE.getValor());
			datosProveedoresRepoRepo.save(resultado);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar agregarProveedor");
			throw utilException.getById(7).crearExcepcion();
		}
		return FacEnum.REGISTRO_GUARDADO.getValor();
	}

	@Override
	public DatosProveedorDTO buscarProveedor() throws ExcepcionSegAlfa {
		DatosProveedorModel resultado = new DatosProveedorModel();
		try {
			LOGGER.info("Ejecutando buscarProveedor ");
			resultado = datosProveedoresRepoRepo.findByLast();
			return new DatosProveedorDTO().modeloAdto(resultado);
		} catch (PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar buscarProveedor");
			throw utilException.getById(6).crearExcepcion();
		}
	}

	@Override
	public List<ParametrosDto> consultar() throws ExcepcionSegAlfa {
		List<ParametrosModel> listModel = parametrosRepository.findAll();
		List<ParametrosDto> listDto = new ArrayList<>();
		listModel.stream().forEach(d -> listDto.add(modelToDto(d)));
		return listDto;
	}

	@Override
	public ParametrosDto consultarDni(Integer dni) throws ExcepcionSegAlfa {
		ParametrosModel model = parametrosRepository.buscarDni(dni);
		return modelToDto(model);
	}

	@Override
	public String actualizar(ParametrosDto dto) throws ExcepcionSegAlfa {
		ParametrosModel model = dtoToModel(dto);
		parametrosRepository.save(model);
		return "OK";
	}
	
	private ParametrosDto modelToDto(ParametrosModel model) {
		ParametrosDto dto = new ParametrosDto();
		dto.setDni(model.getDni());
		dto.setDescripcion(model.getDescripcion());
		dto.setNombre(model.getNombre());
		dto.setValor(model.getValor());
		return dto;
	}
	
	private ParametrosModel dtoToModel(ParametrosDto dto) {
		ParametrosModel model = parametrosRepository.findById(dto.getDni()).orElse(null);
		if(model != null) {
			model.setDni(dto.getDni());
			model.setDescripcion(dto.getDescripcion());
			model.setValor(dto.getValor());
		}
		return model;
	}
	
}
