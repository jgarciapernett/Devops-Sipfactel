package co.com.periferia.alfa.core.services;

import org.springframework.http.ResponseEntity;

public interface IJsonService {

	public ResponseEntity<Object> consultar(Integer dni);
	
}
