package co.com.periferia.alfa.core.services;

import java.util.List;

import org.json.JSONArray;

import co.com.periferia.alfa.core.dto.ConsorciadosDto;
import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;

/**
 * Interface para la seccion de la persona juridica Con
 * @author Duvan Rodriguez
 */

public interface ISeccionContactoConService {

	public JSONArray seccionContactoCon(EmisorAndDireccionDto emi, Integer tipoPersona, Object rec, List<ConsorciadosDto> consorcio);
	
}
