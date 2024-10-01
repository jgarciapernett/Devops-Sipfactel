package co.com.periferia.alfa.core.services;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.OpcRolesDTO;
import co.com.periferia.alfa.core.dto.UserDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

@Component
public interface IUserService extends IServicio<UserDTO, Integer> {

	List<UserDTO> buscarPorNombre(String nombre) throws ExcepcionSegAlfa;

	List<OpcRolesDTO> rol() throws ExcepcionSegAlfa;

	ResponseEntity<InputStreamResource> generarExcelUsuarios() throws ExcepcionSegAlfa;
}
