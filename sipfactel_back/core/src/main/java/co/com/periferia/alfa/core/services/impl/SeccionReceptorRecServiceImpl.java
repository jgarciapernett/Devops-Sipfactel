package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;
import co.com.periferia.alfa.core.services.ISeccionReceptorRecService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase para la seccion receptor rec
 * @author Duvan Rodriguez Penagos
 */

@Service
public class SeccionReceptorRecServiceImpl implements ISeccionReceptorRecService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionReceptorRecServiceImpl.class);
	
	/**
	 * Metodo que realiza la logica de la seccion rec (receptor)
	 * @param recep - ReceptoresDTO con la informacion de la seccion
	 * @return JSONArray arreglo con la informacion
	 */
	
	@Override
	public JSONArray seccionReceptorRec(ReceptoresDireccionDto recep) {
		LOG.info("Ingreso a seccion de receptor (rec)");

		List<Map<String, Object>> listaMap = new ArrayList<>();
		Map<String, Object> seccionRec = new HashedMap<>();

		if(recep != null) {
			seccionRec.put(FacEnum.NOMBRE.getValor(), recep.getNombre());
			seccionRec.put(FacEnum.EMAIL.getValor(), recep.getEmail());
			seccionRec.put(FacEnum.ENVIAR_EMAIL.getValor(), recep.isEnviarEmail());
			seccionRec.put(FacEnum.INCLUIR_ANEXOS.getValor(), recep.isIncluirAnexos());
			seccionRec.put(FacEnum.INCLUIR_PDF.getValor(), recep.isIncluirPDF());
			seccionRec.put(FacEnum.INCLUIR_XML.getValor(), recep.isIncluirXML());
			listaMap.add(seccionRec);	
		}
	
		return UtilJson.armarJsonArray(listaMap);
	}

	
	
}
