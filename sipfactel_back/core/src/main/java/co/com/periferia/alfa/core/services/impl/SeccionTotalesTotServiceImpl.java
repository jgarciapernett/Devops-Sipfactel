package co.com.periferia.alfa.core.services.impl;

import java.math.BigInteger;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.EncabezadoDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaCreDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaDebDTO;
import co.com.periferia.alfa.core.services.ISeccionTotalesTotService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase que realiza la logica de la seccion de totales TOT
 * @author Duvan Rodriguez
 */

@Service
public class SeccionTotalesTotServiceImpl implements ISeccionTotalesTotService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SeccionTotalesTotServiceImpl.class);

	/**
	 * Metodo que recopliza los datos de la seccion totales ya sea para facturas, notas debito o credito
	 * @param encabezadosDto - objeto que puede ser EncabezadoDto o EncabezadoNotaDebDTO o EncabezadoNotaCreDTO
	 * @return JSONObject con el json ya armado
	 */
	
	@Override
	public JSONObject seccionTotalesTot(Object encabezadosDto, BigInteger taxSubTotalTaxableAmount) {
		LOG.info("Ingreso a seccion totales (tot)");
		Map<String, Object> seccionTot = new HashedMap<>();
		if (encabezadosDto.getClass() == EncabezadoDTO.class) {
			LOG.debug("Entro la clase EncabezadoDTO = {} ", encabezadosDto.getClass());
			seccionTot.put(FacEnum.LINEEXTENSIONAMOUNT.getValor(), ((EncabezadoDTO) encabezadosDto).getLineExtensionAmount());
			seccionTot.put(FacEnum.TAXEXCLUSIVEAMOUNT.getValor(), taxSubTotalTaxableAmount);
			seccionTot.put(FacEnum.TAXINCLUSIVEAMOUNT.getValor(), ((EncabezadoDTO) encabezadosDto).getTaxInclusiveAmount());
			seccionTot.put(FacEnum.PAYABLEAMOUNT.getValor(), ((EncabezadoDTO) encabezadosDto).getPayableAmount());
		} else if (encabezadosDto.getClass() == EncabezadoNotaDebDTO.class) {
			LOG.debug("Entro la clase EncabezadoNotaDebDTO = {} ", encabezadosDto.getClass());
			seccionTot.put(FacEnum.LINEEXTENSIONAMOUNT.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getLineExtensionAmount());
			seccionTot.put(FacEnum.TAXEXCLUSIVEAMOUNT.getValor(), taxSubTotalTaxableAmount);
			seccionTot.put(FacEnum.TAXINCLUSIVEAMOUNT.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getTaxInclusiveAmount());
			seccionTot.put(FacEnum.PAYABLEAMOUNT.getValor(), ((EncabezadoNotaDebDTO) encabezadosDto).getPayableAmount());
		} else if (encabezadosDto.getClass() == EncabezadoNotaCreDTO.class) {
			LOG.debug("Entro la clase EncabezadoNotaCreDTO = {} ", encabezadosDto.getClass());
			seccionTot.put(FacEnum.LINEEXTENSIONAMOUNT.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getLineExtensionAmount());
			seccionTot.put(FacEnum.TAXEXCLUSIVEAMOUNT.getValor(), taxSubTotalTaxableAmount);
			seccionTot.put(FacEnum.TAXINCLUSIVEAMOUNT.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getTaxInclusiveAmount());
			seccionTot.put(FacEnum.PAYABLEAMOUNT.getValor(), ((EncabezadoNotaCreDTO) encabezadosDto).getPayableAmount());
		} else {
			LOG.warn("no es un dto de encabezado");
		}
		return UtilJson.armarJson(seccionTot);
	}

	
	
}
