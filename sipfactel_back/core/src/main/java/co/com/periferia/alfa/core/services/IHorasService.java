package co.com.periferia.alfa.core.services;

import java.util.List;

import co.com.periferia.alfa.core.dto.HorarioEdicionDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.HorasModel;

public interface IHorasService {

	List<HorasModel> consultarHoras() throws ExcepcionSegAlfa;	
	
	String editarHorario(List<HorarioEdicionDto> dto) throws ExcepcionSegAlfa;
}
