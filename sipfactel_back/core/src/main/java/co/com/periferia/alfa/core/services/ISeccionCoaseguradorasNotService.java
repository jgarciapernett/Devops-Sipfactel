package co.com.periferia.alfa.core.services;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.CoaseguradorasDto;

/**
 * Interface para la seccion de consorcios ass
 * @author Duvan Rodriguez
 */

@Service
public interface ISeccionCoaseguradorasNotService {

	public JSONArray seccionCoaseguradorasNot(List<CoaseguradorasDto> listaCoaseguradoras);
	
}
