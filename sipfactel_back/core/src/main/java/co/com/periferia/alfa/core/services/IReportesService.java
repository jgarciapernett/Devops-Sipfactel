package co.com.periferia.alfa.core.services;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import co.com.periferia.alfa.core.dto.TablaReporteContableDTO;
import co.com.periferia.alfa.core.dto.TablaReporteEstadoDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

@Component
public interface IReportesService {
	
	List<TablaReporteContableDTO> filtroReporteContable(String fechaini, String fechafin, Integer tipodoc, String numdoc, String numpoliza) throws ExcepcionSegAlfa;

	List<TablaReporteEstadoDTO> filtroReporteEstado(Integer estado, String fechaini, String fechafin, String numpoliza, String numdoc, String compania, String sucursal) throws ExcepcionSegAlfa;

	ResponseEntity<InputStreamResource> reporteAdquirientesExcel() throws ExcepcionSegAlfa;
	
	Integer reporteAdquirientesCantidad();

	ResponseEntity<InputStreamResource> detalleExcel(String fechaini, String fechafin, Integer tipodoc, String numdoc,
			String numpoliza, String compania) throws ExcepcionSegAlfa;

	ResponseEntity<InputStreamResource> estadoExcel(Integer estado, String fechaini, String fechafin, String numpoliza,
			String numtransaccion, String compania, String sucursal) throws ExcepcionSegAlfa;

	
}
