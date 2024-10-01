package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.CoaseguradorasDto;
import co.com.periferia.alfa.core.services.ISeccionCoaseguradorasNotService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase para la seccion de Coaseguradoras 
 * @author Alejandra Hernandez
 */

@Service
public class SeccionCoaseguradorasNotServiceImpl implements ISeccionCoaseguradorasNotService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionCoaseguradorasNotServiceImpl.class);
	
	/**
	 * Metodo que realiza la logica de la seccion ass (informacion miembros consorcio)
	 * @param listaConsorciados - List<ConsorciadosDto> lista con la informacion de los miembros del consorcio.
	 * @return JSONArray - arreglo con los datos de los miembros.
	 */
	@Override
	public JSONArray seccionCoaseguradorasNot(List<CoaseguradorasDto> listaCoaseguradoras) {
		LOG.info("Entra a armar seccion not");
        List<Map<String, Object>> listaAss = new ArrayList<>();
        
		for (CoaseguradorasDto coaseguradora : listaCoaseguradoras) {
			Map<String, Object> seccionNot = new HashedMap<>();
			String coaseguradoras = FacEnum.COASEGURADORA.getValor() + ": " + coaseguradora.getCoaseguradora() + "; " + FacEnum.TDOC.getValor() + ": " + Utilitario.datoNulo(coaseguradora.getTdoc()) + "; " + FacEnum.NDOC.getValor() + ": " +  Utilitario.datoNulo(coaseguradora.getNdoc()) + "; " +  FacEnum.PORC_PART.getValor() + ": " +  Utilitario.datoNulo(coaseguradora.getPorcPart()) + "; " + FacEnum.NUMPOL.getValor() + ": " + Utilitario.datoNulo(coaseguradora.getNumpol()) + "; " + FacEnum.RAMO.getValor() + ": " + Utilitario.datoNulo(coaseguradora.getRamo());
			seccionNot.put(FacEnum.NOTE.getValor(), coaseguradoras);
			listaAss.add(seccionNot);
		}
		
		return UtilJson.armarJsonArray(listaAss);
	}

}
