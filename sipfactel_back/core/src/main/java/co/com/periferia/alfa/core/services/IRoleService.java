package co.com.periferia.alfa.core.services;

import java.util.List;

import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.dto.RoleDTO;
import co.com.periferia.alfa.core.dto.RonuNuevoDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

@Component
public interface IRoleService extends IServicio<RoleDTO, Integer> {

	List<RoleDTO> buscarPorNombre(String nombre) throws ExcepcionSegAlfa;

	List<RonuNuevoDTO> buscarTodosMenu() throws ExcepcionSegAlfa;
}
