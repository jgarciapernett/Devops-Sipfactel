package co.com.periferia.alfa.core.services;

import org.json.JSONObject;

import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;

/**
 * Interface para la seccion receptor acp
 * @author Duvan Rodriguez
 */

public interface ISeccionReceptorAcpService {

	public JSONObject seccionReceptorAcp(ReceptoresDireccionDto rec);
	
}
