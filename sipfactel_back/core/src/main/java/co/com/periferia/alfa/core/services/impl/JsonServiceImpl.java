package co.com.periferia.alfa.core.services.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.controller.AuthenticationController;
import co.com.periferia.alfa.core.repository.EmitirDelcopRepository;
import co.com.periferia.alfa.core.services.IJsonService;

@Service
public class JsonServiceImpl implements IJsonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private EmitirDelcopRepository repository;
	
	@Override
	public ResponseEntity<Object> consultar(Integer dni) {
		LOGGER.info("Ingreso a reporte uiaf de clientes");	
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			baos.write("{".getBytes());
			baos.write('\n');
			baos.write("\"Documents\": [".getBytes());
			baos.write('\n');
			List<String> lista = repository.lista(dni);
			for(String data : lista) {
				baos.write((data + ",").getBytes());
				baos.write('\n');
			}
			
			baos.write("]".getBytes());
			baos.write('\n');
			baos.write("}".getBytes());
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=documento_" + dni + ".json")
					.body(baos.toByteArray());
		} catch (Exception e) {
			LOGGER.error("Error al generar el reporte uiaf {} ", e.getMessage());
			return null;
		}
	}

}
