package co.com.periferia.alfa.core.services;

import org.json.JSONArray;

import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;

/**
 * Interface para la seccion receptor rec
 * @author Duvan Rodriguez
 */

public interface ISeccionReceptorRecService {

	public JSONArray seccionReceptorRec (ReceptoresDireccionDto recep);
	
}
