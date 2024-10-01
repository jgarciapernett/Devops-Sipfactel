package co.com.periferia.alfa.core.utilitarios;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitaria para el armado de obejtos o arreglos json
 * 
 * @author Duvan Rodriguez
 */

public class UtilJson {

	private static final Logger LOG = LoggerFactory.getLogger(UtilJson.class);

	private UtilJson() {
	}

	/**
	 * Metodo que retorna un jsonObject armado
	 */

	public static JSONObject armarJson(Map<String, Object> informacion) {

		JSONObject json = new JSONObject();
		try {
			for (Map.Entry<String, Object> info : informacion.entrySet()) {
				json.put(info.getKey(), info.getValue());
			}
		} catch (JSONException e) {
			LOG.error("Ha ocurrido un error al armar el json, error: {} ", e.getMessage());
			json = new JSONObject();
		}

		return json;
	}

	/**
	 * Metodo que retorna un JSONArray armado
	 */

	public static JSONArray armarJsonArray(List<Map<String, Object>> informacion) {

		JSONArray array = new JSONArray();
		for(Map<String, Object> dato : informacion) {
			array.put(dato);
		}

		return array;
	}

}
