package co.com.periferia.alfa.core.services.impl;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;
import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;
import co.com.periferia.alfa.core.services.ISeccionDireccionesAddService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.Utilitario;

/**
 * Clase para la seccion de direcciones add
 * 
 * @author Duvan Rodriguez
 */

@Service
public class SeccionDireccionesAddServiceImpl implements ISeccionDireccionesAddService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionDireccionesAddServiceImpl.class);

	/**
	 * Metodo que realiza la logica de la seccion add (direcciones)
	 * 
	 * @param direccion - DireccionesDTO con la informacion para la seccion
	 * @return JSONObject con el json armado
	 */

	@Override
	public Map<String, Object> seccionDireccionAdd(boolean tipo, EmisorAndDireccionDto direccion1, ReceptoresDireccionDto direccion2) {
		
        Map<String, Object> seccionAdd = new HashedMap<>();
		String cityId = FacEnum.NOTWHITE_SPACE.getValor();
		String cityName = FacEnum.NOTWHITE_SPACE.getValor();
		String countrySubEntity = FacEnum.NOTWHITE_SPACE.getValor();
		String countrySubEntityCode = FacEnum.NOTWHITE_SPACE.getValor();
		
		if (tipo) {
			LOG.info("Ingreso a seccion direcciones (add)");
			if(direccion1 != null) {
				if (direccion1.getCountryCode() != null && direccion1.getCountryCode().equals(FacEnum.CODIGO_COLOMBIA.getValor())) {
					cityId = direccion1.getCityID();
					cityName = direccion1.getCityName();
					countrySubEntityCode = direccion1.getCountrySubentityCode();
					countrySubEntity = direccion1.getCountrySubentity();
				}

				seccionAdd.put(FacEnum.ID.getValor(), Utilitario.datoNulo(direccion1.getId()));
				seccionAdd.put(FacEnum.CITYID.getValor(), cityId);
				seccionAdd.put(FacEnum.CITYNAME.getValor(), cityName);
				seccionAdd.put(FacEnum.POSTALZONE.getValor(), Utilitario.datoNulo(direccion1.getPostalZone()));
				seccionAdd.put(FacEnum.COUNTRYSUBENTITY.getValor(), countrySubEntity);
				seccionAdd.put(FacEnum.COUNTRYSUBENTITYCODE.getValor(), countrySubEntityCode);
				seccionAdd.put(FacEnum.ADDRESSLINE.getValor(), Utilitario.datoNulo(direccion1.getAddressLine()));
				seccionAdd.put(FacEnum.COUNTRYNAME.getValor(), Utilitario.datoNulo(direccion1.getCountryName()));
				seccionAdd.put(FacEnum.COUNTRYCODE.getValor(), Utilitario.datoNulo(direccion1.getCountryCode()));	
			}
		} else {
			LOG.info("Ingreso a seccion direcciones (add2)");
			if(direccion2 != null) {
				if (direccion2.getCountryCode() != null && direccion2.getCountryCode().equals(FacEnum.CODIGO_COLOMBIA.getValor())) {
					cityId = direccion2.getCityID();
					cityName = direccion2.getCityName();
					countrySubEntityCode = direccion2.getCountrySubentityCode();
					countrySubEntity = direccion2.getCountrySubentity();
				}

				seccionAdd.put(FacEnum.ID.getValor(), Utilitario.datoNulo(direccion2.getId()));
				seccionAdd.put(FacEnum.CITYID.getValor(), cityId);
				seccionAdd.put(FacEnum.CITYNAME.getValor(), cityName);
				seccionAdd.put(FacEnum.POSTALZONE.getValor(), Utilitario.datoNulo(direccion2.getPostalZone()));
				seccionAdd.put(FacEnum.COUNTRYSUBENTITY.getValor(), countrySubEntity);
				seccionAdd.put(FacEnum.COUNTRYSUBENTITYCODE.getValor(), countrySubEntityCode);
				seccionAdd.put(FacEnum.ADDRESSLINE.getValor(), Utilitario.datoNulo(direccion2.getAddressLine()));
				seccionAdd.put(FacEnum.COUNTRYNAME.getValor(), Utilitario.datoNulo(direccion2.getCountryName()));
				seccionAdd.put(FacEnum.COUNTRYCODE.getValor(), Utilitario.datoNulo(direccion2.getCountryCode()));
			}
		}

		return seccionAdd;
	}

}
