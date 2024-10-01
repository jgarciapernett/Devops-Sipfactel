package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ReceptorDTO;
import co.com.periferia.alfa.core.dto.ReceptorNotasDTO;
import co.com.periferia.alfa.core.services.ISeccionContactoPersonaJuridicaConService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase para la seccion del contacto de la persona juridica con
 * @author Duvan Rodriguez
 */

@Service
public class SeccionContactoPersonaJuridicaConServiceImpl implements ISeccionContactoPersonaJuridicaConService{

	private static final Logger LOG = LoggerFactory.getLogger(SeccionContactoPersonaJuridicaConServiceImpl.class);
	
	/**
	 * Metodo que realiza la logica de la seccion CON (nombre contacto persona juridica)
	 * @param receptorDatosAdquiriente - Objeto que puede ser ReceptorDTO o ReceptorNotasDTO
	 * @return JSONObject con el json armado
	 */
	
	@Override
	public JSONArray seccionPersonaJuridicaCon(Object receptorDatosAdquiriente) {
		LOG.info("Entra seccionPersonaJuridicaCon ---> nombre de contacto persona juridica");
		List<Map<String, Object>> listaSeccionCon = new ArrayList<>();
		Map<String, Object> seccionCon = new HashMap<>();
		if(receptorDatosAdquiriente != null) {
			if (receptorDatosAdquiriente.getClass() == ReceptorDTO.class) {
				seccionCon.put(FacEnum.ID.getValor(), ((ReceptorDTO) receptorDatosAdquiriente).getContactID());
				seccionCon.put(FacEnum.CONTACT_NAME.getValor(), Utilitario.datoNulo(((ReceptorDTO) receptorDatosAdquiriente).getContactName()));
			} else {
				seccionCon.put(FacEnum.ID.getValor(), ((ReceptorNotasDTO) receptorDatosAdquiriente).getContactID());
				seccionCon.put(FacEnum.CONTACT_NAME.getValor(), Utilitario.datoNulo(((ReceptorNotasDTO) receptorDatosAdquiriente).getContactName()));
			}	
			listaSeccionCon.add(seccionCon);
		}
		listaSeccionCon.add(seccionCon);
		
		return UtilJson.armarJsonArray(listaSeccionCon);
	}

}
