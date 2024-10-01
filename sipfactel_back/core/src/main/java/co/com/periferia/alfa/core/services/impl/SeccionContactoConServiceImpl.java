package co.com.periferia.alfa.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConsorciadosDto;
import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;
import co.com.periferia.alfa.core.dto.ReceptorNotasDTO;
import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.services.ISeccionContactoConService;
import co.com.periferia.alfa.core.services.IValidarPersonaJuridicaService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase para la seccion del contacto de la persona juridica con
 * @author Duvan Rodriguez
 */

@Service
public class SeccionContactoConServiceImpl implements ISeccionContactoConService{

	@Autowired
	IParametrosRepository parametrosRepository;
	
	@Autowired
	IValidarPersonaJuridicaService validacionService;
	
	private static final Logger LOG = LoggerFactory.getLogger(SeccionContactoConServiceImpl.class);
	
	
	@Override
	public JSONArray seccionContactoCon(EmisorAndDireccionDto emi, Integer tipoPersona, Object rec, List<ConsorciadosDto> consorcio){
		LOG.info("Entra getSeccionCON ---> contacto compania");
		List<Map<String, Object>> array = new ArrayList<>();
		Map<String, Object> con = new HashMap<>();
		
		con.put(FacEnum.ID.getValor(), emi.getContactId());
		con.put(FacEnum.CONTACT_NAME.getValor(), Utilitario.datoNulo(emi.getNombreContacto()));		
		con.put(FacEnum.CONTACT_EMAIL.getValor(), Utilitario.datoNulo(emi.getElectronicMail()));
		
		array.add(con);
		
		if (validacionService.validaPersonaJuridica(tipoPersona)) {
			con = seccionPersonaJuridicaCon(rec);
			array.add(con);
		}
		
		if(!consorcio.isEmpty()) {
			seccionAssConsorcioCon(consorcio, array);
		}
		
		return UtilJson.armarJsonArray(array);
	}
	
	private void seccionAssConsorcioCon(List<ConsorciadosDto> consorcio, List<Map<String, Object>> array) {
		LOG.info("Entra seccionAssConsorcioCon ---> nombre de contacto consorciado");
		
		consorcio.stream().forEach(contacto -> {
			Map<String, Object> seccionCon = new HashMap<>();
			seccionCon.put(FacEnum.ID.getValor(), contacto.getContactID());
			seccionCon.put(FacEnum.CONTACT_NAME.getValor(), Utilitario.datoNulo(contacto.getNombreContactId()));
			array.add(seccionCon);
		});
	}
	
	private Map<String, Object> seccionPersonaJuridicaCon(Object receptorDatosAdquiriente) {
		LOG.info("Entra seccionPersonaJuridicaCon ---> nombre de contacto persona juridica");
		
		Map<String, Object> seccionCon = new HashMap<>();
		if(receptorDatosAdquiriente != null) {
			if (receptorDatosAdquiriente.getClass() == ReceptoresDireccionDto.class) {
				seccionCon.put(FacEnum.ID.getValor(), ((ReceptoresDireccionDto) receptorDatosAdquiriente).getContactID());
				seccionCon.put(FacEnum.CONTACT_NAME.getValor(), Utilitario.datoNulo(((ReceptoresDireccionDto) receptorDatosAdquiriente).getContactName()));
			} else {
				seccionCon.put(FacEnum.ID.getValor(), ((ReceptorNotasDTO) receptorDatosAdquiriente).getContactID());
				seccionCon.put(FacEnum.CONTACT_NAME.getValor(), Utilitario.datoNulo(((ReceptorNotasDTO) receptorDatosAdquiriente).getContactName()));
			}	
		}
		
		return seccionCon;
	}

}
