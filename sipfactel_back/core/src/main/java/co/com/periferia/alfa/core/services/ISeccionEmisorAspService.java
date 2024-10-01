package co.com.periferia.alfa.core.services;

import org.json.JSONObject;

import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;

/**
 * Interface para la seccion emisor asp
 * @author Duvan Rodriguez
 */

public interface ISeccionEmisorAspService {

	public JSONObject seccionEmisorAsp(EmisorAndDireccionDto emi);
	
}
