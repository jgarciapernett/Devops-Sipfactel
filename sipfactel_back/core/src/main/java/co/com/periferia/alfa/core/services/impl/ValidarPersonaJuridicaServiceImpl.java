package co.com.periferia.alfa.core.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.services.IValidarPersonaJuridicaService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase para la validacion de persona juridica
 * @author Duvan Rodriguez
 */

@Service
public class ValidarPersonaJuridicaServiceImpl implements IValidarPersonaJuridicaService {

	private static final Logger LOG = LoggerFactory.getLogger(ValidarPersonaJuridicaServiceImpl.class);
	
	@Autowired
	IParametrosRepository parametrosRepository;
	
	@Override
	/**
	 * Metodo que consulta el parametro COD_PERSONA_JURIDICA y lo compara con el
	 * tipo de persona proveniente devuelve true si son iguales
	 * @param int tipoPersona
	 * @return boolean
	 */
	public boolean validaPersonaJuridica(int tipoPersona) {
		LOG.info("Entra a validaPersonaJuridica");
		String codPersonaJuridica = parametrosRepository.buscarNombre(FacEnum.COD_PERSONA_JURIDICA.getValor());
		boolean result = false;
		if (tipoPersona == Integer.parseInt(codPersonaJuridica)) {
			LOG.debug("(validaPersonaJuridica) ----> Es persona Juridica");
			result = true;
		}
		LOG.debug("(validaPersonaJuridica) ----> return: {}", result);
		return result;
	}

	
	
}
