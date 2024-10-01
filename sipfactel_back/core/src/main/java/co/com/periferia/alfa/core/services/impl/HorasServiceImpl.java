package co.com.periferia.alfa.core.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.HorarioEdicionDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.HorasModel;
import co.com.periferia.alfa.core.repository.IHoraRepository;
import co.com.periferia.alfa.core.repository.IHorarioRepository;
import co.com.periferia.alfa.core.services.IHorasService;

@Service
public class HorasServiceImpl implements IHorasService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HorasServiceImpl.class);
	
	@Autowired
	private IHoraRepository daoHora;
	
	@Autowired
	private IHorarioRepository daoHorario;
	
	@Autowired
	UtilExcecion utilException;
	
	@Override
	public List<HorasModel> consultarHoras() {
		LOGGER.info("Ingreso a consultar horas");
		return daoHora.consultar();
	}

	@Override
	public String editarHorario(List<HorarioEdicionDto> dto) throws ExcepcionSegAlfa {
		try {
			SimpleDateFormat formatoEntrada = new SimpleDateFormat("HH:mm");
			SimpleDateFormat formatoSalida = new SimpleDateFormat("hh:mm a");

			String valid= daoHorario.validacionHorario();
			if(valid!=null && !valid.equals("00:00") && (dto.stream().anyMatch(hora -> hora.getHora()!=null && hora.getHora().equals(valid) && hora.getEstado().equals("INA")) ||
					(dto.stream().anyMatch(hora -> hora.getHora()!=null && hora.getHora().equals(valid) && hora.getEstado().equals("ACT"))==false))){
				Date hora = formatoEntrada.parse(valid);
				String horaFormateada = formatoSalida.format(hora);
				return horaFormateada;
			}
			daoHorario.deleteAll();
			dto.stream().filter(d -> d.getEstado().equals("ACT"))
			.forEach(data -> daoHorario.crearHorario(data.getEjecucion(), data.getInicio(), data.getFin(), data.getOrden()));
			updateHoras(dto);
			return null;
		} catch (Exception e) {
			LOGGER.error("error guardadon horarios {} ", e.getMessage());
			throw utilException.getById(7).crearExcepcion();
		}
	}

	private void updateHoras(List<HorarioEdicionDto> dto) {
		List<Integer> dnisHora = new ArrayList<>();
		
		for(HorarioEdicionDto data : dto) {
			if (data.getDni() !=null){
			dnisHora.add(data.getDni());}
		}
		
		List<HorasModel> listaModel = daoHora.consultarByDni(dnisHora);
		
		dto.stream().filter(x -> x.getDni() != null).forEach(d -> {
			for(HorasModel model : listaModel) {
				if(d.getDni().equals(model.getDni())) {
					model.setHora(d.getHora());
					model.setEstado(d.getEstado());
				}
			}
		});
		daoHora.saveAll(listaModel);
	}

}
