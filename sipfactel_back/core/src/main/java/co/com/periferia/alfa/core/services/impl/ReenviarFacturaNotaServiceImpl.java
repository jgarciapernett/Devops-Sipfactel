package co.com.periferia.alfa.core.services.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.ConsultaEstadoConNumdocDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.model.ErroresDelcopModel;
import co.com.periferia.alfa.core.model.JsonEnvioModel;
import co.com.periferia.alfa.core.repository.FacturasRepository;
import co.com.periferia.alfa.core.repository.IErroresDelcopRepository;
import co.com.periferia.alfa.core.repository.JsonEnvioRepository;
import co.com.periferia.alfa.core.repository.NotaDebitoCreditoRepository;
import co.com.periferia.alfa.core.repository.ValidacionRepetidosRepository;
import co.com.periferia.alfa.core.services.IConsultaEstadosRestClientService;
import co.com.periferia.alfa.core.services.IReenviarFacturaNotaService;

/**
 * Clase que realiza la logica para el reenvio de facturas y notas por medio del
 * codigo trid
 * 
 * @author Duvan Rodriguez
 */

@Service
public class ReenviarFacturaNotaServiceImpl implements IReenviarFacturaNotaService {

	private static final Logger LOG = LoggerFactory.getLogger(ReenviarFacturaNotaServiceImpl.class);

	private static final Integer FACTURA = 1198;

	@Autowired
	JsonEnvioRepository jsonRepository;

	@Autowired
	FacturasRepository facturaRepository;

	@Autowired
	NotaDebitoCreditoRepository notaRepository;

	@Autowired
	ValidacionRepetidosRepository repetidosRepository;

	@Autowired
	IConsultaEstadosRestClientService consultaEstadosRevice;

	@Autowired
	IErroresDelcopRepository erroresRepository;

	/**
	 * Metodo que recibe los codigos trid y los valida para saber si deben ser
	 * reenviados
	 * 
	 * @param codigosTrid - List<Object[]>, es una lista de arreglos de objetso
	 *                    debido a tambienj trae la llave primaria de la tabla
	 *                    json_envio
	 * @throws ExcepcionSegAlfa, JSONException, IOException la exceptionalfa es
	 *                           importante para la validacion del login delcop
	 */

	@Override
	public void reenviarFacturaNotaCodigosTrid(List<Object[]> codigosTrid)
			throws ExcepcionSegAlfa, JSONException, IOException {
		LOG.info("Ingreso a reenvio de facturas y notas, casos duplicados");
		List<ConsultaEstadoConNumdocDto> enviarCodigosTrId = new ArrayList<>();
		List<JsonEnvioModel> listaDocumentos = new ArrayList<>();
		for (int i = 0; i < codigosTrid.size(); i++) {
			JsonEnvioModel datos = new JsonEnvioModel();
			//codigosTrid.get(i)[1] = 965406;
			//LOG.info("TRID::{}",codigosTrid.get(i)[1]);
			List<Object[]> datosExistentes = jsonRepository
					.busquedaPorTrId(Integer.parseInt(String.valueOf(codigosTrid.get(i)[1])));
			if (datosExistentes != null && !datosExistentes.isEmpty()) {
				datos.setTrId(((BigDecimal) datosExistentes.get(0)[1]).intValue());
				datos.setNumdocumento(((String) datosExistentes.get(0)[2]));
				datos.setTipodocumento(((BigDecimal) datosExistentes.get(0)[3]).intValue());
				
				int existeDocumento = jsonRepository
						.busquedaPorTrIdAndDocumento(Integer.parseInt(String.valueOf(codigosTrid.get(i)[1])), Integer.parseInt(String.valueOf(codigosTrid.get(i)[2])));
				
				if (existeDocumento > 0) {
					asignarTrid(enviarCodigosTrId, listaDocumentos, codigosTrid, i);
				} else {
					eliminarNumeracion(datos.getTipodocumento(), datos.getNumdocumento());
				}
			} else {
				asignarTrid(enviarCodigosTrId, listaDocumentos, codigosTrid, i);
			}
			
		}

		if (!listaDocumentos.isEmpty())
			
			consultaEstadosRevice.consultarEstadoFacturas(enviarCodigosTrId, listaDocumentos, false);

	}
	
	private void asignarTrid (List<ConsultaEstadoConNumdocDto> enviarCodigosTrId, List<JsonEnvioModel> listaDocumentos, List<Object[]> codigosTrid, int i) {
		JsonEnvioModel buscarJson = jsonRepository
				.busquedaPorId(Integer.parseInt(String.valueOf(codigosTrid.get(i)[0])));
		ConsultaEstadoConNumdocDto dto = new ConsultaEstadoConNumdocDto();
		dto.setTrid(Integer.parseInt(String.valueOf(codigosTrid.get(i)[1])));
		dto.setNumdoc(buscarJson.getNumdocumento());
		enviarCodigosTrId.add(dto);
		buscarJson.setTrId(Integer.parseInt(String.valueOf(codigosTrid.get(i)[1])));
		jsonRepository.save(buscarJson);
		listaDocumentos.add(buscarJson);
	} 

	/**
	 * Metodo que reaqliza el reenvio de facturas y notas con mensajes de error
	 * generales
	 * 
	 * @return List<Object[]> - en caso de existir errores que tengan codigo tri_id
	 *         estos seran retornados para realoizar otro proceso
	 */

	@Override
	public void reenviarFacturaNotaGeneral() {
		LOG.info("Ingreso a reenvio de facturas y notas generales");
		List<ErroresDelcopModel> erroresDelcop = erroresRepository.consultaEstadosActivos();
		List<Object[]> facturaNotaReenviar = new ArrayList<>();

		erroresDelcop.stream().forEach(
				error -> facturaNotaReenviar.addAll(jsonRepository.consultaReenvioFacturasNotas(error.getId())));

		facturaNotaReenviar.stream().forEach(trid -> 
			encolarNumeracion(Integer.parseInt(String.valueOf(trid[0])), String.valueOf(trid[1]))
		);
	}

	/**
	 * Metodo que realiza la eliminacion de la enumeracion de notas o facturas
	 * 
	 * @param tipoDocumento - Integer, tipo de documento que puede ser 1198 factura,
	 *                      102 credito, 1203 debito
	 */

	private void eliminarNumeracion(Integer tipoDocumento, String numDocumento) {
		LOG.warn("elimina numeracion del documento: {} ", numDocumento);
		if (tipoDocumento.equals(FACTURA))
			facturaRepository.encolarNumeracion(numDocumento);
		else
			notaRepository.encolarNumeracion(numDocumento);
	}

	/**
	 * Metodo que realiza la re-encolacion de la enumeracion de notas o facturas
	 * 
	 * @param tipoDocumento - Integer, tipo de documento que puede ser 1198 factura,
	 *                      102 credito, 1203 debito
	 */

	private void encolarNumeracion(Integer tipoDocumento, String numDocumento) {
		LOG.warn("encola numeracion del documento: {} ", numDocumento);
		if (tipoDocumento.equals(FACTURA))
			facturaRepository.encolarFactura(numDocumento);
		else
			notaRepository.encolarNota(numDocumento);
	}

}
