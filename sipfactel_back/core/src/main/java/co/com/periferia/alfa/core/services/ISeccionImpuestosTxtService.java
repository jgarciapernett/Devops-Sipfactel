package co.com.periferia.alfa.core.services;

import java.util.List;

import org.json.JSONArray;

import co.com.periferia.alfa.core.dto.ImpuestosDTO;

/**
 * Interface para la seccion de impuestos TXT
 * @author Duvan Rodriguez
 */

public interface ISeccionImpuestosTxtService {

	public JSONArray seccionImpuestosTxt(List<ImpuestosDTO> imp);
	
}
