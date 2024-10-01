package co.com.periferia.alfa.core.services;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

@Service
public interface IReenviarFacturaNotaService {

	public void reenviarFacturaNotaCodigosTrid(List<Object[]> codigosTrid) throws ExcepcionSegAlfa, JSONException, IOException;
	
	public void reenviarFacturaNotaGeneral ();
	
}
