package co.com.periferia.alfa.core.utilitarios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Clase utilitaria para cualquier metodo que se repita
 * 
 * @author Duvan Rodriguez
 */

public class Utilitario {

	private Utilitario() {
	}

	/**
	 * Metodo que retornja la fecha actual
	 * 
	 * @return String con la fecha actual
	 */

	public static String fechaActual() {
		Calendar fech = new GregorianCalendar();

		Locale idioma = new Locale("es", "Es");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh: mm: ss: a", idioma);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		return simpleDateFormat.format(fech.getTime());
	}

	/**
	 * Metodo que invierte el orden de los campos de una fecha string
	 * 
	 * @param fecha - String con los campos a invertir
	 * @return String con la fecha invertida
	 */

	public static String fechaInvertida(String fecha) {
		String fech = "";
		if(fecha != null && !fecha.equals("")) {
			String f = fecha.split(FacEnum.WHITE_SPACE.getValor())[0].replace(FacEnum.LINE.getValor(),
					FacEnum.SLASH.getValor());
			
			fech = f.split(FacEnum.SLASH.getValor())[2] + FacEnum.SLASH.getValor() + f.split(FacEnum.SLASH.getValor())[1]
					+ FacEnum.SLASH.getValor() + f.split(FacEnum.SLASH.getValor())[0]; 
		}
		return fech;

	}
	
	/**
	 * Metodo que valida si un string es nulo o no
	 * @param comprobar - String a validar
	 * @return String validado
	 */
	
	public static String datoNulo (String comprobar) {
		if(comprobar == null) {
			comprobar = FacEnum.NOTWHITE_SPACE.getValor();
		}
		return comprobar;
	}
	
	/**
	 * Metodo que valida si un Integer es nulo o no
	 * @param comprobar - Integer a validar
	 * @return Integer validado
	 */
	
	public static Integer intNulo (Integer comprobar) {
		if(comprobar == null) {
			comprobar = 0;
		}
		return comprobar;
	}
	
	/**
	 * Obtiene los estados de titanio
	 */
	
	public static Map<Integer, String> estadosTitanio(){
		Map<Integer, String> estadosTitanio = new HashMap<>();
		estadosTitanio.put(0, "Transacción Recibida");
		estadosTitanio.put(1, "Transacción Rechazada al llegar");
		estadosTitanio.put(2, "Transacción Validada");
		estadosTitanio.put(3, "Transacción Inválida");
		estadosTitanio.put(4, "Enviado a la DIAN");
		estadosTitanio.put(5, "Rechazado por la DIAN al llegar");
		estadosTitanio.put(6, "Validado por la DIAN");
		estadosTitanio.put(7, "Rechazado por la DIAN");
		estadosTitanio.put(8, "Error de envío al adquiriente por email");
		estadosTitanio.put(9, "Error de envío al adquiriente por interoperabilidad");
		estadosTitanio.put(10, "Enviado exitoso al adquiriente por Email");
		estadosTitanio.put(11, "Enviado exitoso al adquiriente por Interoperabilidad");
		estadosTitanio.put(12, "Radicado por el adquiriente en el portal");
		estadosTitanio.put(13, "Radicado por el adquiriente por interoperabilidad");
		estadosTitanio.put(14, "Aceptado por el adquiriente por el portal");
		estadosTitanio.put(15, "Aceptado por el adquiriente por interoperabilidad");
		estadosTitanio.put(16, "Rechazado por el adquiriente en el portal");
		estadosTitanio.put(17, "Rechazado por el adquiriente por interoperabilidad");
		return estadosTitanio;
	}

}
