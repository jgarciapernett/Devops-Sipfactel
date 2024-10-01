package co.com.periferia.alfa.core.services;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface EnviarEmailService {

	String sendmail(String mensaje, String subject) throws ExcepcionSegAlfa;

}
