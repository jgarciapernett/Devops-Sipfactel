package co.com.periferia.alfa.core.services;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interfaz para desacoplar los metodo utilitario0s de seccion
 * @author Duvan Rodriguez
 */

public interface IUtilSeccionService {
	
	public JSONObject getJsonObject (Object list, JSONObject jsonObject) throws JSONException;
	
}
