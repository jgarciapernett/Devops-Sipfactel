package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConsultaEjecucionEtlDto;
import co.com.periferia.alfa.core.model.EjecucionEtlModel;
import co.com.periferia.alfa.core.repository.IEjecucionEtlRepository;
import co.com.periferia.alfa.core.services.IConsultarEjecucionEtlService;

@Service
public class ConsultarEjecucionEtlServiceImpl implements IConsultarEjecucionEtlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultarEjecucionEtlServiceImpl.class);
	
	@Autowired
	private IEjecucionEtlRepository repository;
	
	@Override
	public List<ConsultaEjecucionEtlDto> consultarEtl() {
		LOGGER.info("Ingreso a consulta de ejecucion de etl");
		List<EjecucionEtlModel> consulta = repository.consultarEjecucion();
		return modelToDto(consulta);
	}
	
	private List<ConsultaEjecucionEtlDto> modelToDto (List<EjecucionEtlModel> model){
		LOGGER.info("Inicio conversion de model a dto");
		List<ConsultaEjecucionEtlDto> listaDto = new ArrayList<>();
		model.stream().forEach(data -> {
			ConsultaEjecucionEtlDto dto = new ConsultaEjecucionEtlDto();
			dto.setEjecutado(data.getProcesado().equals(1) ? "SI": "NO");
			dto.setFechaEjecutada(data.getFechaEjecucion());
			dto.setFechaProgramada(data.getFecha());
			listaDto.add(dto);
		});
		return listaDto;
	}

}
