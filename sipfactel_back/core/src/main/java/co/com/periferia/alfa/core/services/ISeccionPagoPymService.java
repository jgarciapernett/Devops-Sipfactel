package co.com.periferia.alfa.core.services;

import org.json.JSONArray;

import co.com.periferia.alfa.core.dto.PagoDTO;

/**
 * Interface para la seccion pago pym
 * @author Duvan Rodriguez
 */

public interface ISeccionPagoPymService {

	public JSONArray seccionPagoPym(PagoDTO pago);
	
}
