package co.com.periferia.alfa.core.services.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.DetalleDTO;
import co.com.periferia.alfa.core.dto.DetalleDebitoDTO;
import co.com.periferia.alfa.core.dto.ImpuestosLineasDTO;
import co.com.periferia.alfa.core.repository.FacturaDelcopRepository;
import co.com.periferia.alfa.core.repository.NotasCreDebRepository;
import co.com.periferia.alfa.core.services.ISeccionDetalleIvlService;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.UtilJson;

/**
 * Clase para la seccion detalle ivl
 * 
 * @author Duvan Rodriguez
 */
@Service
public class SeccionDetalleIvlServiceImpl implements ISeccionDetalleIvlService {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionDetalleIvlServiceImpl.class);

	@Autowired
	NotasCreDebRepository notasCreDebRepository;

	@Autowired
	FacturaDelcopRepository facturaDelcopRepository;

	/**
	 * Metodo get para la seccion ivl (detalle)
	 * 
	 * @param tipo    - boolean donde true es del metodo json y false del metodo de
	 *                notas
	 * @param id      - identificador que puede salir de EncabezadoDTO o
	 *                EncabezadoNotaCreDTO
	 * @param json    - Objeto Map que se le agregaran mas datos
	 * @param debCred - boolean donde true es notaDebito y false es notaCredito
	 * @return Map<String, Object> - retorna el mismo objeto que recibe como
	 *         parametro
	 */

	@Override
	public Map<String, Object> seccionDetalleIvl(boolean tipo, Integer id, Map<String, Object> json, boolean debCred) {
		LOG.info("Ingreso a seccion de detalle (ivl)");

		List<Map<String, Object>> listaIvl = new ArrayList<>();
		List<Map<String, Object>> listaLit = new ArrayList<>();

		if (tipo) {
			LOG.info("Armando detalle del metodo json");
			try {
				List<DetalleDTO> det = facturaDelcopRepository.getIvl(id);
				for (DetalleDTO list2 : det) {
					Map<String, Object> seccionIvl = new HashedMap<>();
					seccionIvl.put(FacEnum.ID.getValor(), list2.getIdIvl());
					seccionIvl.put(FacEnum.INVOICEDQUANTITY.getValor(), list2.getInvoicedQuantity());
					seccionIvl.put(FacEnum.INVOICEDQUANTITYUNITCODE.getValor(), list2.getInvoicedQuantityUnitCode());
					seccionIvl.put(FacEnum.LINEEXTENSIONAMOUNT.getValor(), list2.getLineExtensionAmount());
					seccionIvl.put(FacEnum.PRICEAMOUNT.getValor(), list2.getPriceAmount());
					seccionIvl.put(FacEnum.BASEQUANTITY.getValor(), list2.getBaseQuantity());
					seccionIvl.put(FacEnum.BASEQUANTITY_UNITCODE.getValor(), list2.getBaseQuantityUnitCode());
					seccionIvl.put(FacEnum.ITEM_DESCRIPTION.getValor(), list2.getItemDescription());
					seccionIvl.put(FacEnum.STANDARD_ITEMID.getValor(), list2.getStandardItemID());
					seccionIvl.put(FacEnum.STANDARD_ITEMID_SCHEMEID.getValor(), list2.getStandardItemIDSchemeID());
					if(list2.getLineExtensionAmount().compareTo(BigInteger.ZERO) > 0) 
						listaIvl.add(seccionIvl);	

					List<ImpuestosLineasDTO> impl = facturaDelcopRepository.getLit(Integer.parseInt(list2.getId()));
					for (ImpuestosLineasDTO list3 : impl) {
						if (list3.getTaxAmount().compareTo(BigInteger.ZERO) > 0 
								&& list3.getTaxSubTotalTaxAmount().compareTo(BigInteger.ZERO) > 0) {
							Map<String, Object> seccionLit = new HashedMap<>();
							seccionLit.put(FacEnum.ID.getValor(), list2.getIdIvl());
							seccionLit.put(FacEnum.TAXAMOUNT.getValor(), list3.getTaxAmount());
							seccionLit.put(FacEnum.SCHEMEID.getValor(), list3.getSchemeID());
							seccionLit.put(FacEnum.SCHEMENAME.getValor(), list3.getSchemeName());
							seccionLit.put(FacEnum.TAXSUBTOTAL_TAXABLEAMOUNT.getValor(),
									list3.getTaxSubTotalTaxableAmount());
							seccionLit.put(FacEnum.TAXSUBTOTAL_TAXAMOUNT.getValor(), list3.getTaxSubTotalTaxAmount());
							seccionLit.put(FacEnum.TAXSUBTOTAL_PERCENT.getValor(), list3.getTaxSubTotalPercent());
							listaLit.add(seccionLit);
						}
					}
					validarDatatSeccionLit(json, listaLit);
				}
			} catch (Exception e) {
				LOG.error("Error de lista: {} ", e.getCause().getMessage());
			}
			json.put(FacEnum.IVL.getValor(), UtilJson.armarJsonArray(listaIvl));
			return json;
		} else {
			LOG.info("Armando detalle del metodo json de notas credito o debito");
			return complementoIvl(listaIvl, id, listaLit, json, debCred);
		}
	}

	/**
	 * Metodo que realiza la logica del jason de notas cred y deb, y complementa el
	 * metodo ivl
	 * 
	 * @param listaIvl - Lista de maps para llenar con la data de la seccion
	 * @param id       - identificador que puede salir de EncabezadoDTO o
	 *                 EncabezadoNotaCreDTO
	 * @param listaLit - Lista de maps para llenar con la data de la seccion
	 * @param json     - Objeto Map que se le agregaran mas datos
	 * @param debCred  - boolean donde true es notaDebito y false es notaCredito
	 * @return Map<String, Object> - retorna el mismo objeto que recibe como
	 *         parametro
	 */
	private Map<String, Object> complementoIvl(List<Map<String, Object>> listaIvl, Integer id,
			List<Map<String, Object>> listaLit, Map<String, Object> json, boolean debCred) {
		LOG.info("Ingreso a metodo ivl para notas");

		List<DetalleDebitoDTO> det = notasCreDebRepository.getDnl(id);
		for (DetalleDebitoDTO list2 : det) {
			Map<String, Object> seccionIvl = new HashedMap<>();
			seccionIvl.put(FacEnum.ID.getValor(), list2.getIdDnl());
			seccionIvl.put(FacEnum.UUID.getValor(), FacEnum.NOTWHITE_SPACE.getValor());
			if (debCred) {
				seccionIvl.put(FacEnum.DEBITEDQUANTITY.getValor(), list2.getDebitedQuantity());
				seccionIvl.put(FacEnum.DEBITEDQUANTITYUNITCODE.getValor(), list2.getDebitedQuantityUnitCode());
			} else {
				seccionIvl.put(FacEnum.CREDITEDQUANTITY.getValor(), list2.getDebitedQuantity());
				seccionIvl.put(FacEnum.CREDITEDQUANTITYUNITCODE.getValor(), list2.getDebitedQuantityUnitCode());
			}
			seccionIvl.put(FacEnum.LINEEXTENSIONAMOUNT.getValor(), list2.getLineExtensionAmount());
			seccionIvl.put(FacEnum.PRICEAMOUNT.getValor(), list2.getPriceAmount());
			seccionIvl.put(FacEnum.BASEQUANTITY.getValor(), list2.getBaseQuantity());
			seccionIvl.put(FacEnum.BASEQUANTITY_UNITCODE.getValor(), list2.getBaseQuantityUnitCode());
			seccionIvl.put(FacEnum.ITEM_DESCRIPTION.getValor(), list2.getItemDescription());
			seccionIvl.put(FacEnum.STANDARD_ITEMID.getValor(), list2.getStandardItemID());
			seccionIvl.put(FacEnum.STANDARD_ITEMID_SCHEMEID.getValor(), list2.getStandardItemIDSchemeID());
			if(list2.getLineExtensionAmount().compareTo(BigInteger.ZERO) > 0) 
				listaIvl.add(seccionIvl);

			List<ImpuestosLineasDTO> impl = notasCreDebRepository.getLit(Integer.parseInt(list2.getId()));
			for (ImpuestosLineasDTO list3 : impl) {
				if (list3.getTaxAmount().compareTo(BigInteger.ZERO) > 0 
						&& list3.getTaxSubTotalTaxAmount().compareTo(BigInteger.ZERO) > 0) {
					Map<String, Object> seccionLit = new HashedMap<>();
					seccionLit.put(FacEnum.ID.getValor(), list2.getIdDnl());
					seccionLit.put(FacEnum.TAXAMOUNT.getValor(), list3.getTaxAmount());
					seccionLit.put(FacEnum.SCHEMEID.getValor(), list3.getSchemeID());
					seccionLit.put(FacEnum.SCHEMENAME.getValor(), list3.getSchemeName());
					seccionLit.put(FacEnum.TAXSUBTOTAL_TAXABLEAMOUNT.getValor(), list3.getTaxSubTotalTaxableAmount());
					seccionLit.put(FacEnum.TAXSUBTOTAL_TAXAMOUNT.getValor(), list3.getTaxSubTotalTaxAmount());
					seccionLit.put(FacEnum.TAXSUBTOTAL_PERCENT.getValor(), list3.getTaxSubTotalPercent());
					listaLit.add(seccionLit);
				}
			}
			validarDatatSeccionLit(json, listaLit);
		}
		if (debCred) {
			json.put(FacEnum.DNL.getValor(), UtilJson.armarJsonArray(listaIvl));
		} else {
			json.put(FacEnum.CNL.getValor(), UtilJson.armarJsonArray(listaIvl));
		}

		return json;
	}

	private void validarDatatSeccionLit(Map<String, Object> json, List<Map<String, Object>> listaLit) {
		if (!listaLit.isEmpty()) {
			json.put(FacEnum.LIT.getValor(), UtilJson.armarJsonArray(listaLit));
		}
	}

}
