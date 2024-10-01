package co.com.periferia.alfa.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que recibe datos del servicio signin de Titanio
 * @author jeissoncastillo
 *
 */
@Getter
@Setter
public class LoginDelcopDTO {
	
	private boolean success;
	private String token;
	private String ven;
	private String error;
	private int xRateLimitLimit;
	private int xRateLimitRemaining;
	private boolean superoIntentosErroneos;
	
}
