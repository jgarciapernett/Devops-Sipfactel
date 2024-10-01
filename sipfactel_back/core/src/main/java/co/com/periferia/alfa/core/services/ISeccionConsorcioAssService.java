package co.com.periferia.alfa.core.services;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConsorciadosDto;

/**
 * Interface para la seccion de consorcios ass
 * @author Duvan Rodriguez
 */

@Service
public interface ISeccionConsorcioAssService {

	public JSONArray seccionConsorciosAss(List<ConsorciadosDto> listaConsorciados);
	
}
