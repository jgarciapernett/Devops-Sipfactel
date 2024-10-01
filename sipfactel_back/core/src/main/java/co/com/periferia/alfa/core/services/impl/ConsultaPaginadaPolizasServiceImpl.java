package co.com.periferia.alfa.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ApiResponseDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.ConsultaPolizasEntity;
import co.com.periferia.alfa.core.repository.IConsultaPolizasRepository;
import co.com.periferia.alfa.core.services.IConsultaPaginadaPolizasService;

@Service
public class ConsultaPaginadaPolizasServiceImpl implements IConsultaPaginadaPolizasService {

	@Autowired
	private IConsultaPolizasRepository repo;
	
	@Override
	public ApiResponseDto facturaFiltro(String numPoliza, String numdoc, String fIniEnvio, String fFinEnvio,
			String fIniEmision, String fFinEmision, int pagina, int tamano) throws ExcepcionSegAlfa {
		Pageable page = PageRequest.of(pagina - 1, tamano);
		Page<ConsultaPolizasEntity> polizas = repo.consulta(numPoliza, numdoc, fIniEnvio, fFinEnvio, fIniEmision, fFinEmision, page);
		return new ApiResponseDto(polizas.getContent(), polizas.getTotalElements());
	}

}
