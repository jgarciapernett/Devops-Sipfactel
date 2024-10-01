package co.com.periferia.alfa.core.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.com.periferia.alfa.core.dto.DocumentosDto;
import co.com.periferia.alfa.core.dto.EstadoDocumentosDto;
import co.com.periferia.alfa.core.model.RespDelcopModel;

/**
 * Clase que realiza las prueba de conversion de dto a model
 * 
 * @author Duvan Rodriguez
 */

class EstadoDocumentosDtoTest {

	private EstadoDocumentosDto dto;
	
	/**
	 * Metodo que inicializa el dto a conevrtir
	 */

	@BeforeEach
	void init() {
		EstadoDocumentosDto dtoSetUp = new EstadoDocumentosDto();
		List<DocumentosDto> lista = new ArrayList<>();
		
		dtoSetUp.setErrorId(0);
		dtoSetUp.setErrorMsg("Se realizo con exito la operacion.");
		dtoSetUp.setDocumentos(lista);
		this.dto = dtoSetUp;
		
	}
	
}
