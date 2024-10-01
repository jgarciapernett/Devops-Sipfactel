package co.com.periferia.alfa.core.services;

import java.util.Map;

import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;
import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;

/**
 * Interface para la seccion de direcciones add
 * @author Duvan Rodriguez
 */

public interface ISeccionDireccionesAddService {

	public Map<String, Object> seccionDireccionAdd (boolean tipo, EmisorAndDireccionDto dir1, ReceptoresDireccionDto dir2);
	
}
