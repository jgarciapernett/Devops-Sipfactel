package co.com.periferia.alfa.core.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.periferia.alfa.core.dto.CoaseguradorasDto;
import co.com.periferia.alfa.core.dto.ConsorciadosDto;
import co.com.periferia.alfa.core.dto.ConsultaEstadoConNumdocDto;
import co.com.periferia.alfa.core.dto.DetalleDTO;
import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;
import co.com.periferia.alfa.core.dto.EncabezadoDTO;
import co.com.periferia.alfa.core.dto.ExtensionesDTO;
import co.com.periferia.alfa.core.dto.ImpuestosDTO;
import co.com.periferia.alfa.core.dto.ImpuestosLineasDTO;
import co.com.periferia.alfa.core.dto.PagoDTO;
import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;
import co.com.periferia.alfa.core.dto.TotalesDTO;
import co.com.periferia.alfa.core.model.CoaseguradorasModel;
import co.com.periferia.alfa.core.model.ConsorciadosModel;
import co.com.periferia.alfa.core.model.DetalleModel;
import co.com.periferia.alfa.core.model.EmisorAndDireccionModel;
import co.com.periferia.alfa.core.model.EncabezadoModel;
import co.com.periferia.alfa.core.model.ExtensionesModel;
import co.com.periferia.alfa.core.model.ImpuestosLineasModel;
import co.com.periferia.alfa.core.model.ImpuestosModel;
import co.com.periferia.alfa.core.model.PagoModel;
import co.com.periferia.alfa.core.model.ReceptoresDireccionModel;

@Transactional(propagation = Propagation.NESTED)
@Repository
public class FacturaDelcopRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String CURSOR = "c_Resp";
	private static final String IDFAC = "id_FAC";
	private static final String IDNOT = "id_NOT";
	private static final String ACP = "SP_CONSULTAR_ACP";
	private static final String ASP = "SP_CONSULTAR_ASP";
	private static final String ASS = "SP_CONSULTAR_ASS";
	private static final String NOT = "SP_CONSULTAR_NOT";

	public static final Logger LOGGER = LoggerFactory.getLogger(FacturaDelcopRepository.class);

	/**
	 * Metodo que envia el nombre ddel sp de nuemracion general para su ejecucion
	 */
	
	public String numeracionGeneral() {
		return executeProcedure("SP_ETL_ASG_NUMERACION");
	}


	/**
	 * Metodo que hace uso del sp SP_ACTUESTADO
	 * @return String con la respuesta de los documentos actualizados
	 */

	public String actualizarEstados() {
		return executeProcedure("SP_ACTUESTADO");
	}
	
	/**
	 * Metood que llama al sp de asignar nuemracion general tanto para notas como facturas
	 */
	
	private String executeProcedure(String strQuery) {
		String mensaje = "";
		try {
			LOGGER.info("Llamando el sp : {} ", strQuery);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(strQuery);
			procedureQuery.registerStoredProcedureParameter("msj", String.class, ParameterMode.OUT);
			procedureQuery.execute();
			mensaje = (String) procedureQuery.getOutputParameterValue("msj");
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp {} | error = {} | {}", strQuery, e.getMessage(), e.getStackTrace());
		}	
		return mensaje;
	}

	/**
	 * Metodo que ejecuta el procedimiento de distintas consultas
	 * @param sor[] - String nombre del parametro
	 * @param type[] - Class tipo de clase
	 * @param mode[] - ParameterMode tipo de parametro para el procedimiento (IN, INOUT, OUT)
	 * @param id - Integer codigo para buscar
	 * @param model - Class clase modleo enviada al procedimiento
	 * @param sp - String nombre procedimiento
	 * @return StoredProcedureQuery
	 */
	@SuppressWarnings("rawtypes")
	private StoredProcedureQuery spConsultas(String[] sor, Class[] type, ParameterMode[] mode, Integer id, Class<?> model, String sp) {
		LOGGER.info("Llamando el sp de factura: {} ", sp);
		LOGGER.info("Nombre parametro {} | {}", sor[0], sor[1]);
		LOGGER.info("Tipos de clases {} | {}", type[0], type[1]);
		LOGGER.info("parametros de procedimiento {} | {}", mode[0], mode[1]);
		LOGGER.info("Parametro id_fac {} ", id);
		LOGGER.info("clase modelo {} ", model);
		StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(sp,
				model);
		procedureQuery.registerStoredProcedureParameter(sor[0], type[0], mode[0]);
		procedureQuery.registerStoredProcedureParameter(sor[1], type[1], mode[1]);
		procedureQuery.setParameter(sor[1], id);
		if(sp.equals("SP_CONSULTAR_PYM") || sp.equals(ACP) || sp.equals(ASP) || sp.equals(ASS)) {
			procedureQuery.execute();	
		}
		return procedureQuery;
	}

	@SuppressWarnings("rawtypes")
	public ExtensionesDTO getExt(Integer id) {
		ExtensionesModel resp = new ExtensionesModel();
		ExtensionesDTO ext = new ExtensionesDTO();
		try {
			String[] parameterName = {CURSOR, IDFAC};
			Class[] clases = {void.class, int.class};
			ParameterMode[] params = {ParameterMode.REF_CURSOR, ParameterMode.IN};
			resp = (ExtensionesModel) spConsultas(parameterName, clases, params, id, ExtensionesModel.class, "SP_CONSULTAR_EXT").getSingleResult();
			ext = ext.modeloAdto(resp);
		} catch (NoResultException e) {
			LOGGER.error("Error en SP_CONSULTAR_EXT: {} | {}", e.getMessage(), e.getStackTrace());
			return new ExtensionesDTO();
		} finally {
			entityManager.clear();
		}
		return ext;
	}
	
	/**
	 * Metodo que ejecuta el procedimiento ass para traer la informacion de los consorciados
	 * @param factura - id de la factura
	 * @param nota - id de la nota puede ser debito o credito
	 * @return List<ConsorciadosDto> - lista con la informacion de los consorciados
	 */
	
	@SuppressWarnings("unchecked")
	public List<ConsorciadosDto> getAss(Integer factura, Integer nota) {
		List<ConsorciadosModel> listaModel = new ArrayList<>();
		List<ConsorciadosDto> listaDto = new ArrayList<>();
		try {
			
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(ASS, ConsorciadosModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(IDFAC, int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(IDNOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(IDFAC, factura);
			procedureQuery.setParameter(IDNOT, nota);
			procedureQuery.execute();
			
			listaModel = procedureQuery.getResultList();
			for(ConsorciadosModel model: listaModel) {
				listaDto.add(new ConsorciadosDto().modeloAdto(model));
			}
		} catch (NoResultException e) {
			LOGGER.error("Error en SP_CONSULTAR_ASS: {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return listaDto;
	}

	/**
	 * Metodo que se encarga de ejecutar el procedimiento SP_TRICONSULTAR_ESTADOS
	 * 
	 * @param sor  - String nombre del parametro
	 * @param type - Class tipo de clase
	 * @param mode - ParameterMode tipo de parametro para el procedimiento (IN,
	 *             INOUT, OUT)
	 * @return StoredProcedureQuery
	 **/

	private StoredProcedureQuery spConsultaTridEstado(String sor, Class<?> type, ParameterMode mode) {
		LOGGER.info("Llamando el sp de factura: SP_TRICONSULTAR_ESTADOS");
		LOGGER.info("Nombre parametro {} ", sor);
		LOGGER.info("Tipos de clases {} ", type);
		LOGGER.info("parametros de procedimiento {} ", mode);
		StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_TRICONSULTAR_ESTADOS");
		procedureQuery.registerStoredProcedureParameter(sor, type, mode);
		procedureQuery.execute();
		return procedureQuery;
	}

	/**
	 * Metodo que se encarga de ejecutar el procedimiento sp_consultar_fac
	 * 
	 * @param sor  - String nombre del parametro
	 * @param type - Class tipo de clase
	 * @param mode - ParameterMode tipo de parametro para el procedimiento (IN,
	 *             INOUT, OUT)
	 * @return StoredProcedureQuery
	 **/

	private StoredProcedureQuery spConsultaFac(String sor, Class<?> type, ParameterMode mode) {
		LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_FAC");
		LOGGER.info("Nombre parametro {} ", sor);
		LOGGER.info("Tipos de clases {} ", type);
		LOGGER.info("parametros de procedimiento {} ", mode);
		StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_FAC",
				EncabezadoModel.class);
		procedureQuery.registerStoredProcedureParameter(sor, type, mode);
		procedureQuery.execute();
		return procedureQuery;
	}

	@SuppressWarnings("unchecked")
	public List<EncabezadoDTO> getFac() {
		List<EncabezadoModel> result = new ArrayList<>();
		List<EncabezadoDTO> resp = new ArrayList<>();
		try {
			result = spConsultaFac(CURSOR, void.class, ParameterMode.REF_CURSOR).getResultList();
			for (EncabezadoModel model : result) {
				resp.add(new EncabezadoDTO().modeloAdto(model));
			}
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_FAC {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	/**
	 * Metodo que llama al procedimiento SP_TRICONSULTAR_ESTADOS
	 * @return List<ConsultaEstadoDto>
	 */
	
	@SuppressWarnings("unchecked")
	public List<ConsultaEstadoConNumdocDto> getTridEstados() {
		LOGGER.info("Ingreso a metodo de tridEstados");
		List<Object[]> listaTrid = new ArrayList<>();
		List<ConsultaEstadoConNumdocDto> documents = new ArrayList<>();
		try {
			listaTrid = spConsultaTridEstado(CURSOR, void.class, ParameterMode.REF_CURSOR).getResultList();
			
			listaTrid.stream().forEach(d -> {
				ConsultaEstadoConNumdocDto dto = new ConsultaEstadoConNumdocDto();
				dto.setTrid(Integer.parseInt(d[0].toString()));
				dto.setNumdoc(d[1].toString());
				documents.add(dto);
			});
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_TRICONSULTAR_ESTADOS {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return documents;
	}

	@SuppressWarnings("unchecked")
	public List<TotalesDTO> getTot() {
		List<EncabezadoModel> result = new ArrayList<>();
		List<TotalesDTO> resp = new ArrayList<>();
		try {
			result = spConsultaFac(CURSOR, void.class, ParameterMode.REF_CURSOR).getResultList();
			for (EncabezadoModel model : result) {
				TotalesDTO dto = new TotalesDTO();
				dto.setLineExtensionAmount(model.getLineExtensionAmount());
				dto.setTaxExclusiveAmount(model.getTaxExclusiveAmount());
				dto.setTaxInclusiveAmount(model.getTaxInclusiveAmount());
				dto.setPayableAmount(model.getPayableAmount());
				resp.add(dto);
			}
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_FAC {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}
	
	@SuppressWarnings("rawtypes")
	public EmisorAndDireccionDto getAspAndAdd(Integer id) {
		EmisorAndDireccionModel result = new EmisorAndDireccionModel();
		EmisorAndDireccionDto resp = new EmisorAndDireccionDto();
		try {
			String[] parameterName = {CURSOR, IDFAC};
			Class[] clases = {void.class, int.class};
			ParameterMode[] params = {ParameterMode.REF_CURSOR, ParameterMode.IN};
			result = (EmisorAndDireccionModel) spConsultas(parameterName, clases, params, id, EmisorAndDireccionModel.class, ASP).getSingleResult();
			resp = resp.modeloAdto(result);
		} catch (NoResultException e) {
			LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_ASP- parametros, ERROR: {} | {}", e.getMessage(), e.getStackTrace());
			return new EmisorAndDireccionDto();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("rawtypes")
	public ReceptoresDireccionDto getRececptoresDireccion(Integer id) {
		ReceptoresDireccionModel result = new ReceptoresDireccionModel();
		ReceptoresDireccionDto resp = new ReceptoresDireccionDto();
		try {
			String[] parameterName = { CURSOR, IDFAC };
			Class[] clases = { void.class, int.class };
			ParameterMode[] params = { ParameterMode.REF_CURSOR, ParameterMode.IN };
			result = (ReceptoresDireccionModel) spConsultas(parameterName, clases, params, id, ReceptoresDireccionModel.class, ACP)
					.getSingleResult();
			resp = resp.modeloAdto(result);
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_ACP- parametros, ERROR:  {} | {}", e.getMessage(),
					e.getStackTrace());
			return new ReceptoresDireccionDto();
		} finally {
			entityManager.clear();
		}
		return resp;
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DetalleDTO> getIvl(Integer id) {
		LOGGER.info("Inicio consulta SP_CONSULTAR_IVL");
		List<DetalleModel> result = new ArrayList<>();
		List<DetalleDTO> resp = new ArrayList<>();
		try {
			String[] parameterName = { CURSOR, IDFAC };
			Class[] clases = { void.class, int.class };
			ParameterMode[] params = { ParameterMode.REF_CURSOR, ParameterMode.IN };
			result = spConsultas(parameterName, clases, params, id, DetalleModel.class, "SP_CONSULTAR_IVL")
					.getResultList();
			resp = result.stream().map(e -> new DetalleDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_IVL- parametros, ERROR: {} | {}", e.getMessage(),
					e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		LOGGER.info("Fin consulta SP_CONSULTAR_IVL");
		return resp;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ImpuestosLineasDTO> getLit(Integer id) {
		List<ImpuestosLineasDTO> resp = new ArrayList<>();
		if(id > 0) {
			List<ImpuestosLineasModel> result = new ArrayList<>();
			try {
				String[] parameterName = { CURSOR, "id_DETFAC" };
				Class[] clases = { void.class, int.class };
				ParameterMode[] params = { ParameterMode.REF_CURSOR, ParameterMode.IN };
				result = spConsultas(parameterName, clases, params, id, ImpuestosLineasModel.class, "SP_CONSULTAR_LIT")
						.getResultList();
				resp = result.stream().map(e -> new ImpuestosLineasDTO().modeloAdto(e)).collect(Collectors.toList());
			} catch (Exception e) {
				LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_LIT- parametros, ERROR: {} | {}", e.getMessage(),
						e.getStackTrace());
				return new ArrayList<>();
			} finally {
				entityManager.clear();
			}	
		}
		return resp;
	}

	@SuppressWarnings("rawtypes")
	public PagoDTO getPym(Integer id) {
		PagoModel result = new PagoModel();
		PagoDTO resp = new PagoDTO();
		try {
			String[] parameterName = { CURSOR, IDFAC };
			Class[] clases = { void.class, int.class };
			ParameterMode[] params = { ParameterMode.REF_CURSOR, ParameterMode.IN };
			result = (PagoModel) spConsultas(parameterName, clases, params, id, PagoModel.class, "SP_CONSULTAR_PYM")
					.getSingleResult();
			resp = resp.modeloAdto(result);
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_PYM- parametros, ERROR: {} | {}", e.getMessage(),
					e.getStackTrace());
			return new PagoDTO();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ImpuestosDTO> getTxt(Integer id) {
		List<ImpuestosModel> result = new ArrayList<>();
		List<ImpuestosDTO> resp = new ArrayList<>();
		try {
			String[] parameterName = { CURSOR, IDFAC };
			Class[] clases = { void.class, int.class };
			ParameterMode[] params = { ParameterMode.REF_CURSOR, ParameterMode.IN };
			result = spConsultas(parameterName, clases, params, id, ImpuestosModel.class, "SP_CONSULTAR_TXT")
					.getResultList();
			resp = result.stream().map(e -> new ImpuestosDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_TXT- parametros, ERROR: {} | {}", e.getMessage(),
					e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<EncabezadoDTO> getFacRemediado(String numdoc) {
		List<EncabezadoModel> result = new ArrayList<>();
		List<EncabezadoDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_FAC_REMEDIADOS - Parametro numdoc {} ", numdoc);
			StoredProcedureQuery procedureQuery = entityManager
					.createStoredProcedureQuery("SP_CONSULTAR_FAC_REMEDIADOS", EncabezadoModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter("NUMDOC", String.class, ParameterMode.IN);
			procedureQuery.setParameter("NUMDOC", numdoc);
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new EncabezadoDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar sp: SP_CONSULTAR_FAC_REMEDIADOS, ERROR: {} | {}", e.getMessage(),
					e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	public Integer trTipoId(Integer idPoliza, Integer opc) {
		Integer trtipoId = 0;
		LOGGER.info("parametros: idPoliza: {} ", idPoliza);
		LOGGER.info("parametros: Opc: {} ", opc);
		try {
			LOGGER.info("Llamando sp: SP_TRTIPOID");
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_TRTIPOID");
			procedureQuery.registerStoredProcedureParameter("IDPOLIZA", int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter("OPC", int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter("TRID", int.class, ParameterMode.OUT);
			procedureQuery.setParameter("IDPOLIZA", idPoliza);
			procedureQuery.setParameter("OPC", opc);
			procedureQuery.execute();
			trtipoId = (Integer) procedureQuery.getOutputParameterValue("TRID");
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar sp: SP_TRTIPOID, ERROR: {} | {}", e.getMessage(), e.getStackTrace());
		}
		return trtipoId;
	}
	
	/**
	 * Metodo que ejecuta el procedimiento ass para traer la informacion de las coaseguradoras
	 * @param factura - id de la factura
	 * @param nota - id de la nota puede ser debito o credito
	 * @return List<ConsorciadosDto> - lista con la informacion de los consorciados
	 */
	
	@SuppressWarnings("unchecked")
	public List<CoaseguradorasDto> getNot(Integer factura, Integer nota) {
		List<CoaseguradorasModel> listaModel = new ArrayList<>();
		List<CoaseguradorasDto> listaDto = new ArrayList<>();
		
		try {
			
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(NOT, CoaseguradorasModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(IDFAC, int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(IDNOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(IDFAC, factura);
			procedureQuery.setParameter(IDNOT, nota);
			procedureQuery.execute();
			
			listaModel = procedureQuery.getResultList();
			for(CoaseguradorasModel model: listaModel) {
				listaDto.add(new CoaseguradorasDto().modeloAdto(model));
			}
		} catch (Exception e) {
			LOGGER.error("Error en SP_CONSULTAR_NOT: {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return listaDto;
	}
}
