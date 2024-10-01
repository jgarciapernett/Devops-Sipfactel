package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.DatosNotasDTO;
import co.com.periferia.alfa.core.repository.NotasCreDebRepository;
import co.com.periferia.alfa.core.services.ISeccionDocumentosBrfService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase que realiza la logica de la seccionnde documentos BRF
 * @author Duvan Rodriguez
 */

@Service
public class SeccionDocumentosBrfServiceImpl implements ISeccionDocumentosBrfService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionDocumentosBrfServiceImpl.class);
	
	@Autowired
	NotasCreDebRepository notasCreDebRepository;
	
	/**
	 * Metodo que realiza la logica de la seccion documentos brf}
	 * @param numReferencia - Integer para buscar la informacion de la seccion
	 * @return JSONArray - arreglo con la informacion
	 */
	
	@Override
	public JSONArray seccionDocumentosBrf(Integer numReferencia) {
		LOG.info("Ingreso a seccion brf (documentos)");
		DatosNotasDTO datos = notasCreDebRepository.getBrf(numReferencia);
		List<Map<String, Object>> listaMap = new ArrayList<>();
		Map<String, Object> seccionBrf = new HashedMap<>();
		if(datos != null) {
			seccionBrf.put(FacEnum.INVOICE_ID.getValor(), Utilitario.datoNulo(datos.getInvoiceID()));
			seccionBrf.put(FacEnum.INVOICE_UUID.getValor(), Utilitario.datoNulo(datos.getInvoiceUUID()));
			seccionBrf.put(FacEnum.INVOICE_ISSUEDATE.getValor(), Utilitario.datoNulo(datos.getInvoiceIssueDate()));	
		}

		listaMap.add(seccionBrf);
		
		return UtilJson.armarJsonArray(listaMap);
	}

}
