package co.com.periferia.alfa.core.services.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ImpuestosDTO;
import co.com.periferia.alfa.core.services.ISeccionImpuestosTxtService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase para la seccion Impuestos TXT
 * 
 * @author Duvan Rodriguez
 */

@Service
public class SeccionImpuestosTxtServiceImpl implements ISeccionImpuestosTxtService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionImpuestosTxtServiceImpl.class);

	/**
	 * Metodo que realiza la logica de la seccion txt (impuestos)
	 * 
	 * @param impuestos - List<ImpuestosDTO> con la informacion para la seccion
	 * @return JSONArray arreglo con la informacion
	 */

	@Override
	public JSONArray seccionImpuestosTxt(List<ImpuestosDTO> impuestos) {
		LOG.info("Ingreso a aseccion de impuestos (txt)");

		List<Map<String, Object>> listaMap = new ArrayList<>();
		if (impuestos != null) {
			for (ImpuestosDTO list2 : impuestos) {
				if (list2.getTaxAmount().compareTo(BigInteger.ZERO) > 0 
						&& list2.getTaxSubTotal1TaxableAmount().compareTo(BigInteger.ZERO) > 0) {
					Map<String, Object> seccionTxt = new HashMap<>();
					seccionTxt.put(FacEnum.TAXAMOUNT.getValor(), list2.getTaxAmount());
					seccionTxt.put(FacEnum.SCHEMEID.getValor(), list2.getSchemeID());
					seccionTxt.put(FacEnum.SCHEMENAME.getValor(), list2.getSchemeName());
					seccionTxt.put(FacEnum.TAXSUBTOTAL1_TAXABLEAMOUNT.getValor(), list2.getTaxSubTotal1TaxableAmount());
					seccionTxt.put(FacEnum.TAXSUBTOTAL1_TAXAMOUNT.getValor(), list2.getTaxSubTotal1TaxAmount());
					seccionTxt.put(FacEnum.TAXSUBTOTAL1_PERCENT.getValor(), list2.getTaxSubTotal1Percent());
					listaMap.add(seccionTxt);
				}
			}
		}

		return UtilJson.armarJsonArray(listaMap);

	}

}
