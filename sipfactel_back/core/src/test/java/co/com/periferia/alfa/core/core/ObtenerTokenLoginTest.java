package co.com.periferia.alfa.core.core;

import co.com.periferia.alfa.core.dto.LoginDelcopDTO;
import co.com.periferia.alfa.core.model.DatosDelcopModel;
import co.com.periferia.alfa.core.services.impl.ObtenerTokenLoginImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ObtenerTokenLoginTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(ObtenerTokenLoginTest.class);
	
	private LoginDelcopDTO loginDelcopDTO;
	private DatosDelcopModel delcop;
	private String nit = "860503617";
	
	@BeforeEach
	void init() {
		this.delcop = new DatosDelcopModel();
		this.delcop.setPassword("MTIzNFF3ZXI=");
		this.delcop.setUsuario("JHUERTAS");
		this.delcop.setUrlSignin("https://www-prueba.titanio.com.co/PDE/public/api/auth/signin");
	}
	
	/**
	 * Este metodo prueba que la primera instancia 
	 * invocada de la clase ObtenerTokenLoginImpl
	 * devuelva la informaci�n correcta
	 */
	@Test
	void testFirstSingletonInstance() {
		ObtenerTokenLoginImpl.deleteInstance();
		LOG.info("JCB llama al primer getInstance ----> {} | {}", this.nit, this.delcop.getUsuario());
		LoginDelcopDTO loginDelcopDTO2 = ObtenerTokenLoginImpl.getInstance(this.nit, this.delcop).getLoginDelcopDTO();
		assertNotNull(loginDelcopDTO2);
		assertEquals(true, loginDelcopDTO2.isSuccess());
		assertEquals(null, loginDelcopDTO2.getError());
		assertNotNull(loginDelcopDTO2.getToken());
		assertNotNull(loginDelcopDTO2.getVen());
		this.setLoginDelcopDTO(loginDelcopDTO2);
		LOG.info("JCB llama a la segunda instancia");
		LoginDelcopDTO loginDelcopDTO3 = ObtenerTokenLoginImpl.getInstance().getLoginDelcopDTO();
		assertNotNull(loginDelcopDTO3);
		assertEquals(this.getLoginDelcopDTO().isSuccess(), loginDelcopDTO3.isSuccess());
		assertEquals(null, loginDelcopDTO.getError());
		assertEquals(this.getLoginDelcopDTO().getToken(), loginDelcopDTO2.getToken());
		assertEquals(this.getLoginDelcopDTO().getVen(), loginDelcopDTO2.getVen());
	}
	
	/**
	 * Este metodo prueba que al fallar el servicio de Titanio
	 * se ejecute 3 veces como maximo, almacene el error en cache
	 * y devuelva una variable boolean en true para frenar el flujo.
	 */
	@Test
	void testIntentosServicioFallido() {
		ObtenerTokenLoginImpl.deleteInstance();
		DatosDelcopModel delcopFallido = new DatosDelcopModel();
		delcopFallido.setPassword("MTIzNFF3ZXI=");
		delcopFallido.setUsuario("JHUERTA");
		delcopFallido.setUrlSignin("https://www-prueba.titanio.com.co/PDE/public/api/auth/signin");
		String nitFallido = "86050361";
		LoginDelcopDTO loginDelcopDTO2 = ObtenerTokenLoginImpl.getInstance(nitFallido, delcopFallido).getLoginDelcopDTO();
		assertEquals(true, loginDelcopDTO2.isSuperoIntentosErroneos());
		assertNotNull(loginDelcopDTO2.getError());
	}
	
	@Test
	void testCabecerasXRateLimit() {
		ObtenerTokenLoginImpl.deleteInstance();
		LOG.info("testCabecerasXRateLimit ----> {} | {}", this.nit, this.delcop.getUsuario());
		LoginDelcopDTO loginDelcopDTO = ObtenerTokenLoginImpl.getInstance(this.nit, this.delcop).getLoginDelcopDTO();
		assertNotNull(loginDelcopDTO);
		assertNotEquals(0, loginDelcopDTO.getXRateLimitLimit());
		assertNotEquals(0, loginDelcopDTO.getXRateLimitRemaining());
	}

	public LoginDelcopDTO getLoginDelcopDTO() {
		return loginDelcopDTO;
	}

	public void setLoginDelcopDTO(LoginDelcopDTO loginDelcopDTO) {
		this.loginDelcopDTO = loginDelcopDTO;
	}
}
