package co.com.periferia.alfa.core.services;

import java.util.Map;

/**
 * Interface para la seccion de desattalle ivl
 * @author Duvan Rodriguez
 */

public interface ISeccionDetalleIvlService {

	public Map<String, Object> seccionDetalleIvl(boolean tipo, Integer id, Map<String, Object> json, boolean debCred);
	
}
