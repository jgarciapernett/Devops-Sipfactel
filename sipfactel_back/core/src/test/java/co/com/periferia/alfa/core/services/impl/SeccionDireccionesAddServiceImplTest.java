package co.com.periferia.alfa.core.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.com.periferia.alfa.core.dto.DireccionesDTO;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase que testea la seccion de direcciones
 * @author Duvan Rodriguez
 */

class SeccionDireccionesAddServiceImplTest {

	private SeccionDireccionesAddServiceImpl service = new SeccionDireccionesAddServiceImpl();

	private DireccionesDTO direccion = new DireccionesDTO();

	/**
	 * Metodo que setea el parametro que recibira el servicio que se esta testeando
	 */
	
	@BeforeEach
	void setUo() {

		direccion.setId(FacEnum.STRING_PRUEBA.getValor());
		direccion.setCityID(FacEnum.STRING_PRUEBA.getValor());
		direccion.setCityName(FacEnum.STRING_PRUEBA.getValor());
		direccion.setCountryCode(FacEnum.CODIGO_COLOMBIA.getValor());
		direccion.setCountryName(FacEnum.STRING_PRUEBA.getValor());
		direccion.setCountrySubentity(FacEnum.STRING_PRUEBA.getValor());
		direccion.setCountrySubentityCode(FacEnum.STRING_PRUEBA.getValor());
		direccion.setPostalZone(FacEnum.STRING_PRUEBA.getValor());
		direccion.setAddressLine(FacEnum.STRING_PRUEBA.getValor());

	}

	/**
	 * Metodo que testea el flujo normal sel servicio
	 */
	
	/*@Test
	void testSeccionDireccionAdd() {

		/*Map<String, Object> json = new HashMap<>();
		json = service.seccionDireccionAdd(true, direccion);

		assertEquals(FacEnum.STRING_PRUEBA.getValor(), json.get(FacEnum.CITYID.getValor()));
		assertEquals(FacEnum.STRING_PRUEBA.getValor(), json.get(FacEnum.CITYNAME.getValor()));
		assertEquals(FacEnum.STRING_PRUEBA.getValor(), json.get(FacEnum.COUNTRYSUBENTITY.getValor()));
		assertEquals(FacEnum.STRING_PRUEBA.getValor(), json.get(FacEnum.COUNTRYSUBENTITYCODE.getValor()));

	}*/
	
	/**
	 * Metodo que testea el servicio cuando este recibe un pais diferente a colombia (CO)
	 */
	
	/*@Test
	void testSeccionDireccionAddPaisDiferenteColombia (){
		/*Map<String, Object> json = new HashMap<>();
		direccion.setCountryCode(FacEnum.CODIGO_ARGENTINA.getValor());
		json = service.seccionDireccionAdd(false, direccion);

		assertEquals(FacEnum.NOTWHITE_SPACE.getValor(), json.get(FacEnum.CITYID.getValor()));
		assertEquals(FacEnum.NOTWHITE_SPACE.getValor(), json.get(FacEnum.CITYNAME.getValor()));
		assertEquals(FacEnum.NOTWHITE_SPACE.getValor(), json.get(FacEnum.COUNTRYSUBENTITY.getValor()));
		assertEquals(FacEnum.NOTWHITE_SPACE.getValor(), json.get(FacEnum.COUNTRYSUBENTITYCODE.getValor()));
	}*/
	
	/**
	 * Metodo que testea el servicio cuando este recibe un null
	 */
	
	/*@Test
	void testSeccionDireccionAddRecibeNull (){
		Map<String, Object> json = service.seccionDireccionAdd(false, null);
		assertEquals(new HashMap<>(), json);
	}*/
}
