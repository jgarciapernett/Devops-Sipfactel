package co.com.periferia.alfa.core.services;

import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

@Service
public interface IEliminarTransaccionRestClientService {

	public boolean eliminarTransaccion (Integer transaccion, String numDoc, Integer facturaNota) throws ExcepcionSegAlfa;
	
}
