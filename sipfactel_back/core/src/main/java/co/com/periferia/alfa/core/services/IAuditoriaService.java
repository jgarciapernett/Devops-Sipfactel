package co.com.periferia.alfa.core.services;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.AuditoriaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;


@Component
public interface IAuditoriaService extends IServicio<AuditoriaDTO, Integer> {
	
	List<AuditoriaDTO> buscarPorUsuario(String audiUsuario) throws ExcepcionSegAlfa;
	
	List<AuditoriaDTO> buscarPorFecha(String fechaIni, String fechaFin) throws ExcepcionSegAlfa;
	
	List<AuditoriaDTO> buscarPorFechaYUsuario(String fechaIni, String fechaFin, String audiUsuario) throws ExcepcionSegAlfa;

	ResponseEntity<InputStreamResource> generarExcelUsuario(String audiUsuario) throws ExcepcionSegAlfa;

	ResponseEntity<InputStreamResource> generarExcelUsuaFecha(String fechaIni, String fechaFin, String audiUsuario)
			throws ExcepcionSegAlfa;
	
	ResponseEntity<InputStreamResource> buscarTodosExcel() throws ExcepcionSegAlfa;


}
