package co.com.periferia.alfa.core.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.repository.MensajesErrosRepository;

@Component
public class UtilExcecion {
	
	private List<TipoErrorSegAlfa> excepciones;
	
	@Autowired
	MensajesErrosRepository mensajesErrosRepository;
	
	public void inicializar() {
		excepciones = new ArrayList<>();
		mensajesErrosRepository.findAll().stream().forEach(e -> excepciones.add(
				new TipoErrorSegAlfa(e.getCodigo(), e.getMensaje(),e.getId())));
		if( excepciones.isEmpty()) {
			throw new NullPointerException("Lista de excepciones vacia");
		}
	}
	
	public TipoErrorSegAlfa getById(long id) {
		Optional<TipoErrorSegAlfa> opt =  excepciones.stream().filter(e ->e.getId().equals(id)).findFirst();
		if(opt.isPresent()) {
			return opt.get();
		}else {
			throw new NullPointerException("No se encontro excepci\u00f3n");
		}
	}

}
