package co.com.periferia.alfa.core.utilitarios;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.periferia.alfa.core.dto.CoaseguradorasDto;
import co.com.periferia.alfa.core.dto.ConsorciadosDto;
import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;
import co.com.periferia.alfa.core.dto.EncabezadoDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaCreDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaDebDTO;
import co.com.periferia.alfa.core.dto.ExtensionesDTO;
import co.com.periferia.alfa.core.dto.ImpuestosDTO;
import co.com.periferia.alfa.core.dto.PagoDTO;
import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;
import co.com.periferia.alfa.core.repository.FacturaDelcopRepository;
import co.com.periferia.alfa.core.repository.IParametrosRepository;
import co.com.periferia.alfa.core.repository.NotasCreDebRepository;
import co.com.periferia.alfa.core.services.ISeccionCoaseguradorasNotService;
import co.com.periferia.alfa.core.services.ISeccionConsorcioAssService;
import co.com.periferia.alfa.core.services.ISeccionContactoConService;
import co.com.periferia.alfa.core.services.ISeccionDetalleIvlService;
import co.com.periferia.alfa.core.services.ISeccionDireccionesAddService;
import co.com.periferia.alfa.core.services.ISeccionDocumentosBrfService;
import co.com.periferia.alfa.core.services.ISeccionEmisorAspService;
import co.com.periferia.alfa.core.services.ISeccionEncabezadoFacService;
import co.com.periferia.alfa.core.services.ISeccionExtensionExtService;
import co.com.periferia.alfa.core.services.ISeccionImpuestosTxtService;
import co.com.periferia.alfa.core.services.ISeccionPagoPymService;
import co.com.periferia.alfa.core.services.ISeccionReceptorAcpService;
import co.com.periferia.alfa.core.services.ISeccionReceptorRecService;
import co.com.periferia.alfa.core.services.ISeccionTotalesTotService;
import co.com.periferia.alfa.core.services.IUtilSeccionService;

/**
 * Clase utilitaria para hacer uso de las secciones en la creacion de json y
 * notas deb y cred
 * 
 * @author Duvan Rodriguez
 */
@Service
public class UtilSeccion implements IUtilSeccionService {

	private static final Logger LOG = LoggerFactory.getLogger(UtilSeccion.class);
	
	//posiciones de un arreglo boolean
	private static final int FACTURA_NOTA = 0;
	private static final int DEBITO_CREDITO = 1;
	
	//posiciones de las variables integer
	private static final int DNI = 0;
	private static final int TIPO_PERSONA = 1;
	private static final int NUM_REFERENCIA = 2;
	
	//posiciones de un arreglo de DTO
	private static final Integer PAGO = 0;
	private static final Integer EXTENSION = 1;
	private static final Integer EMISOR_DIRECCION = 2;
	private static final Integer RECEPTOR_DIRECCION = 3;
	private static final Integer ENCABEZADOS = 4;
	

	@Autowired
	FacturaDelcopRepository facturaDelcopRepository;

	@Autowired
	NotasCreDebRepository notasCreDebRepository;

	@Autowired
	IParametrosRepository parametrosRepository;
	
	@Autowired
	ISeccionExtensionExtService extensionExtService;
	
	@Autowired
	ISeccionEncabezadoFacService encabezadoService;

    @Autowired
    ISeccionTotalesTotService totalesTotService;
    
    @Autowired
    ISeccionDocumentosBrfService documentosBrfService;
    
    @Autowired
    ISeccionEmisorAspService emisorAspService;
    
    @Autowired
    ISeccionReceptorAcpService receptorAcpService;
    
    @Autowired
    ISeccionContactoConService contactoPersonaService;
    
    @Autowired
    ISeccionPagoPymService pagoPymService;
    
    @Autowired
    ISeccionImpuestosTxtService impuestosTxtService;
    
    @Autowired
    ISeccionDetalleIvlService detallesIvlService;
    
    @Autowired
    ISeccionReceptorRecService receptorRecService;
    
    @Autowired
    ISeccionDireccionesAddService direccionAddService;
    
    @Autowired
    ISeccionConsorcioAssService consorcioAssService;
    
    @Autowired
    ISeccionCoaseguradorasNotService coaseguradorasNotService;

    /**
	 * Metodo que se encarag de armar toda la estructura json tanto de notas como de
	 * facturas
	 * 
	 * @param encabezadosDto       - Objeto que puede ser EncabezadoDTO, EncabezadoNotaDebDTO
	 *                   o EncabezadoNotaCreDTO
	 * @param jsonObject - JSONObject que se reescribira y etse mismo sera el que
	 *                   retorna
	 * @return JSONObject con el json armado
	 */

	@Override
	public JSONObject getJsonObject(Object encabezadosDto, JSONObject jsonObject) throws JSONException {
		LOG.info("Ingreso a metodo de armado de json con la clase = {} ", encabezadosDto.getClass());
		// dtos que guardan la info para el json
		List<ImpuestosDTO> impuestos = null;
		PagoDTO pago = null;
		ExtensionesDTO extension = new ExtensionesDTO();
		EmisorAndDireccionDto emisorAndDireccion = null;
		ReceptoresDireccionDto receptoresDireccion = null;
		List<ConsorciadosDto> consorcio = new ArrayList<>();
		List<CoaseguradorasDto> not = new ArrayList<>();
		// variables que se modifican dependiendo de si es nota o factura
		Integer dni = null;
		boolean facturaNota = false; // true para factura, false para notas
		boolean debitoCredito = false;// true para debito, false para credito
		String tipoFac = null;// DEB, CRE o FAC
		Integer tipoPersona = null;
		Integer numReferencia = null;

		if (encabezadosDto.getClass() == EncabezadoDTO.class) {
			// if donde se hacen las consultas relacionadas a factura
			dni = ((EncabezadoDTO) encabezadosDto).getIdFac();
			facturaNota = true;
			tipoFac = FacEnum.FAC.getValor();
			extension = facturaDelcopRepository.getExt(((EncabezadoDTO) encabezadosDto).getIdFac());
			emisorAndDireccion = facturaDelcopRepository.getAspAndAdd(((EncabezadoDTO) encabezadosDto).getIdFac());
			pago = facturaDelcopRepository.getPym(((EncabezadoDTO) encabezadosDto).getIdFac());
			impuestos = facturaDelcopRepository.getTxt(((EncabezadoDTO) encabezadosDto).getIdFac());
			receptoresDireccion = facturaDelcopRepository.getRececptoresDireccion(((EncabezadoDTO) encabezadosDto).getIdFac());
			consorcio = facturaDelcopRepository.getAss(((EncabezadoDTO) encabezadosDto).getIdFac(), null);
			tipoPersona = receptoresDireccion.getTipoPersona();
			not = facturaDelcopRepository.getNot(((EncabezadoDTO) encabezadosDto).getIdFac(), null);

		} else if (encabezadosDto.getClass() == EncabezadoNotaDebDTO.class) {
			// if donde se hacen las consultas relacionadas a nota debito
			dni = ((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb();
			debitoCredito = true;
			tipoFac = FacEnum.DEB.getValor();
			numReferencia = ((EncabezadoNotaDebDTO) encabezadosDto).getNumRefencia();
			extension = notasCreDebRepository.getExtDB(((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb());
			emisorAndDireccion = notasCreDebRepository.getAspAndAdd(((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb());
			pago = notasCreDebRepository.getPym(((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb());
			impuestos = notasCreDebRepository.getTxt(((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb());
			receptoresDireccion = notasCreDebRepository.getRececptoresDireccion(((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb());
			consorcio = facturaDelcopRepository.getAss(null, ((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb());
			tipoPersona = receptoresDireccion.getTipoPersona();
			not = facturaDelcopRepository.getNot(null, ((EncabezadoNotaDebDTO) encabezadosDto).getIdDeb());

		} else if (encabezadosDto.getClass() == EncabezadoNotaCreDTO.class) {
			// if donde se hacen las consultas relacionadas a nota credito
			dni = ((EncabezadoNotaCreDTO) encabezadosDto).getIdCre();
			tipoFac = FacEnum.CRE.getValor();
			numReferencia = ((EncabezadoNotaCreDTO) encabezadosDto).getNumRefencia();
			extension = notasCreDebRepository.getExtCR(((EncabezadoNotaCreDTO) encabezadosDto).getIdCre());
			emisorAndDireccion = notasCreDebRepository.getAspAndAdd(((EncabezadoNotaCreDTO) encabezadosDto).getIdCre());
			pago = notasCreDebRepository.getPym(((EncabezadoNotaCreDTO) encabezadosDto).getIdCre());
			impuestos = notasCreDebRepository.getTxt(((EncabezadoNotaCreDTO) encabezadosDto).getIdCre());
			receptoresDireccion = notasCreDebRepository.getRececptoresDireccion(((EncabezadoNotaCreDTO) encabezadosDto).getIdCre());
			consorcio = facturaDelcopRepository.getAss(null, ((EncabezadoNotaCreDTO) encabezadosDto).getIdCre());
			tipoPersona = receptoresDireccion.getTipoPersona();
			not = facturaDelcopRepository.getNot(null, ((EncabezadoNotaCreDTO) encabezadosDto).getIdCre());
		}

		boolean[] facDebCre = {facturaNota, debitoCredito};
		Integer[] dniTipopersonaNumreferencia = {dni, tipoPersona, numReferencia};
		Object[] arregloDto = {pago, extension, emisorAndDireccion, receptoresDireccion, encabezadosDto};
        
        return complementoJson(facDebCre, dniTipopersonaNumreferencia, tipoFac, consorcio, arregloDto, impuestos, not);
	}

	private JSONObject complementoJson (boolean[] facDebCre, Integer[] dniTipopersonaNumreferencia, String tipoFac, List<ConsorciadosDto> consorcio, Object[] arregloDto, List<ImpuestosDTO> impuestos, List<CoaseguradorasDto> not) throws JSONException {

		LOG.info("Ingreso a metodo complementario del json");
        Map<String, Object> json = new HashMap<>();
        List<Map<String, Object>> listaDirecciones = new ArrayList<>();
		
		json.put(FacEnum.EXT.getValor(), extensionExtService.seccionExtensionExt((ExtensionesDTO) arregloDto[EXTENSION])); // seccion extension
		json.put(tipoFac, encabezadoService.seccionEncabezadoFac(arregloDto[ENCABEZADOS])); // Seccion encabezado

		if (!facDebCre[FACTURA_NOTA]) 
			json.put(FacEnum.BRF.getValor(), documentosBrfService.seccionDocumentosBrf(dniTipopersonaNumreferencia[NUM_REFERENCIA])); //seccion documentos
		
		json.put(FacEnum.ASP.getValor(), emisorAspService.seccionEmisorAsp((EmisorAndDireccionDto) arregloDto[EMISOR_DIRECCION]));// Seccion Emisor
		json.put(FacEnum.ACP.getValor(), receptorAcpService.seccionReceptorAcp((ReceptoresDireccionDto) arregloDto[RECEPTOR_DIRECCION]));// Seccion receptor
		if((EmisorAndDireccionDto) arregloDto[EMISOR_DIRECCION] != null && dniTipopersonaNumreferencia[TIPO_PERSONA] != null) 
			json.put(FacEnum.CON.getValor(), contactoPersonaService.seccionContactoCon((EmisorAndDireccionDto) arregloDto[EMISOR_DIRECCION], dniTipopersonaNumreferencia[TIPO_PERSONA], arregloDto[RECEPTOR_DIRECCION], consorcio));//seccion personas juridicas

        json.put(FacEnum.PYM.getValor(), pagoPymService.seccionPagoPym((PagoDTO) arregloDto[PAGO])); // Seccion pago
        JSONArray txt = impuestosTxtService.seccionImpuestosTxt(impuestos);
        json.put(FacEnum.TXT.getValor(), txt); // Seccion impuestos
        json = detallesIvlService.seccionDetalleIvl(facDebCre[FACTURA_NOTA], dniTipopersonaNumreferencia[DNI], json, facDebCre[DEBITO_CREDITO]);// Seccion detalle
        json.put(FacEnum.REC.getValor(), receptorRecService.seccionReceptorRec((ReceptoresDireccionDto) arregloDto[RECEPTOR_DIRECCION])); // Seccion receptor

        listaDirecciones.add(direccionAddService.seccionDireccionAdd(true, (EmisorAndDireccionDto) arregloDto[EMISOR_DIRECCION], null));//objeto direcciones uno
        listaDirecciones.add(direccionAddService.seccionDireccionAdd(false, null, (ReceptoresDireccionDto) arregloDto[RECEPTOR_DIRECCION]));//objeto direcciones dos
       
        JSONArray seccionLit = new JSONArray();
        try {
        	seccionLit = (JSONArray) json.get(FacEnum.LIT.getValor());
		} catch (Exception e){
			LOG.info("No hay seccion LIT, {} ", e.getMessage());
		}
		BigInteger sumaTaxExclusiveAmount = validarTotalTaxableAmount(txt, seccionLit);
        json.put(FacEnum.TOT.getValor(), totalesTotService.seccionTotalesTot(arregloDto[ENCABEZADOS], sumaTaxExclusiveAmount));// Seccion totales
        
        json.put(FacEnum.ADD.getValor(), UtilJson.armarJsonArray(listaDirecciones)); //seccion direcciones ADD

        if(!consorcio.isEmpty()) 			
        	json.put(FacEnum.ASS.getValor(), consorcioAssService.seccionConsorciosAss(consorcio));	
	
        if(!not.isEmpty()) 			
        	json.put(FacEnum.NOT.getValor(), coaseguradorasNotService.seccionCoaseguradorasNot(not));

		return UtilJson.armarJson(json);
		
	}
	
	/**
	 * Metodo que valida si hay datos de lineas de impuestos para sumar
	 * @param txt - JSONArray, arreglo de la seccion txt
	 * @param lit - JSONArray, arreglo de la seccion lit y de donde saldra el dato a sumar 
	 */
	
	private BigInteger validarTotalTaxableAmount(JSONArray txt, JSONArray lit) throws JSONException {
		BigInteger suma = BigInteger.ZERO;
		if(txt.length() > 0 && lit.length() > 0) {
			for(int i = 0; i < lit.length(); i++) {
				suma = suma.add((BigInteger) lit.getJSONObject(i).get("TaxSubTotal_TaxableAmount"));
			}
			return suma;	
		}
		return suma;
	}

}
