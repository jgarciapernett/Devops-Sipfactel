package co.com.periferia.alfa.core.services;

import java.math.BigInteger;

import org.json.JSONObject;

/**
 * Interface para la seccion totales TOT
 * @author Duvan Rodriguez
 */

public interface ISeccionTotalesTotService {

	public JSONObject seccionTotalesTot(Object list, BigInteger taxSubTotalTaxableAmount);
	
}
