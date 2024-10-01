package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.PagoDTO;
import co.com.periferia.alfa.core.services.ISeccionPagoPymService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase para la seccion pago pym
 * @author Duvan Rodriguez
 */

@Service
public class SeccionPagoPymServiceImpl implements ISeccionPagoPymService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionPagoPymServiceImpl.class);
	
	/**
	 * Metodo que realiza la logoca de la seccion pym (pago)
	 * @param pago - PagoDTO con la informacion para el pago
	 * @return JSONArray - arreglo con los datops de la seccion
	 */
	
	@Override
	public JSONArray seccionPagoPym(PagoDTO pago) {
		LOG.info("Ingreso a aseccion de pagos (pym)");

		List<Map<String, Object>> listaMap = new ArrayList<>();
		Map<String, Object> seccionPym = new HashedMap<>();

		if(pago != null) {
			seccionPym.put(FacEnum.ID.getValor(), pago.getId());
			seccionPym.put(FacEnum.PAYMENTMEANSCODE.getValor(), pago.getPaymentMeansCode());
			seccionPym.put(FacEnum.PAYMENTDUEDATE.getValor(), pago.getPaymentDueDate());
			seccionPym.put(FacEnum.INSTRUCTIONNOTE.getValor(), pago.getInstructionNote());
			seccionPym.put(FacEnum.PAYMENTID.getValor(), pago.getPaymentID());
			
			listaMap.add(seccionPym);	
		}

		return UtilJson.armarJsonArray(listaMap);
	}

}
