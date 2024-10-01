package co.com.periferia.alfa.core.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.periferia.alfa.core.dto.DatosNotasDTO;
import co.com.periferia.alfa.core.dto.DetalleCreditoDTO;
import co.com.periferia.alfa.core.dto.DetalleDebitoDTO;
import co.com.periferia.alfa.core.dto.EmisorAndDireccionDto;
import co.com.periferia.alfa.core.dto.EncabezadoNotaCreDTO;
import co.com.periferia.alfa.core.dto.EncabezadoNotaDebDTO;
import co.com.periferia.alfa.core.dto.ExtensionesDTO;
import co.com.periferia.alfa.core.dto.ImpuestosDTO;
import co.com.periferia.alfa.core.dto.ImpuestosLineasDTO;
import co.com.periferia.alfa.core.dto.PagoDTO;
import co.com.periferia.alfa.core.dto.ReceptoresDireccionDto;
import co.com.periferia.alfa.core.model.DatosNotasModel;
import co.com.periferia.alfa.core.model.DetalleCreditoModel;
import co.com.periferia.alfa.core.model.DetalleDebitoModel;
import co.com.periferia.alfa.core.model.EmisorAndDireccionModel;
import co.com.periferia.alfa.core.model.EncabezadoNotaCreModel;
import co.com.periferia.alfa.core.model.EncabezadoNotaDebModel;
import co.com.periferia.alfa.core.model.ExtensionesModel;
import co.com.periferia.alfa.core.model.ImpuestosLineasModel;
import co.com.periferia.alfa.core.model.ImpuestosModel;
import co.com.periferia.alfa.core.model.PagoModel;
import co.com.periferia.alfa.core.model.ReceptoresDireccionModel;

@Transactional(propagation = Propagation.NESTED)
@Repository
public class NotasCreDebRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotasCreDebRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final String CURSOR = "c_Resp";
	private static final String ID_NOT = "id_NOT";
	private static final String NUMDOC = "NUMDOC";
	private static final String SP_CONSULTAR_NOTAS_ACP = "SP_CONSULTAR_NOTAS_ACP";
	private static final String CONSULTA_SP_ACP_ERROR = "Error al ejecutar el sp SP_CONSULTAR_NOTAS_ACP {} | {}";
	private static final String PARAMETERSTOPASS = " {} | {}";

	public ExtensionesDTO getExtDB(Integer id) {
		return executeProcedureExtensiones("SP_CONSULTAR_NOTASDB_EXT", id);
	}

	public ExtensionesDTO getExtCR(Integer id) {
		return executeProcedureExtensiones("SP_CONSULTAR_NOTASCR_EXT", id);
	}

	private ExtensionesDTO executeProcedureExtensiones(String strQuery, Integer id) {
		ExtensionesModel resp = new ExtensionesModel();
		ExtensionesDTO ext = new ExtensionesDTO();
		try {
			LOGGER.info("Llamando el sp de factura: {} con parametro idFac: {} ", strQuery, id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(strQuery,
					ExtensionesModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(ID_NOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(ID_NOT, id);
			procedureQuery.execute();
			resp = (ExtensionesModel) procedureQuery.getSingleResult();
			ext = ext.modeloAdto(resp);
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp {}, error {} | {} ", strQuery, e.getMessage(), e.getStackTrace());
			return new ExtensionesDTO();
		} finally {
			entityManager.clear();
		}
		return ext;
	}

	@SuppressWarnings("unchecked")
	public List<EncabezadoNotaDebDTO> getConsultaDeb() {
		List<EncabezadoNotaDebModel> result = new ArrayList<>();
		List<EncabezadoNotaDebDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_DEB");
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_DEB",
					EncabezadoNotaDebModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.execute();
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new EncabezadoNotaDebDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_DEB {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<EncabezadoNotaCreDTO> getConsultaCre() {
		List<EncabezadoNotaCreModel> result = new ArrayList<>();
		List<EncabezadoNotaCreDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_CRE");
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_CRE",
					EncabezadoNotaCreModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.execute();
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new EncabezadoNotaCreDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_DEB {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	public DatosNotasDTO getBrf(Integer id) {
		DatosNotasModel result = new DatosNotasModel();
		DatosNotasDTO resp = new DatosNotasDTO();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_BRF con parametro numReferencia: {} ", id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_BRF",
					DatosNotasModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter("id_FAC", int.class, ParameterMode.IN);
			procedureQuery.setParameter("id_FAC", id);
			boolean res = procedureQuery.execute();
			if (res) {
				result = (DatosNotasModel) procedureQuery.getSingleResult();
				resp = resp.modeloAdto(result);
			}
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_BRF {} | {}", e.getMessage(), e.getStackTrace());
			return null;
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	public EmisorAndDireccionDto getAspAndAdd(Integer id) {
		EmisorAndDireccionModel result = new EmisorAndDireccionModel();
		EmisorAndDireccionDto resp = new EmisorAndDireccionDto();
		try {
			LOGGER.info("Llamando el sp de Notas: SP_CONSULTAR_NOTAS_ASP con parametro idDeb: {} ", id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_NOTAS_ASP",
					EmisorAndDireccionModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(ID_NOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(ID_NOT, id);
			procedureQuery.execute();
			result = (EmisorAndDireccionModel) procedureQuery.getSingleResult();
			resp = resp.modeloAdto(result);
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_NOTAS_ASP {} | {}", e.getMessage(), e.getStackTrace());
			return new EmisorAndDireccionDto();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	public ReceptoresDireccionDto getRececptoresDireccion(Integer id) {
		ReceptoresDireccionModel result = new ReceptoresDireccionModel();
		ReceptoresDireccionDto resp = new ReceptoresDireccionDto();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_NOTAS_ACP con parametro idDeb: {} ", id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(SP_CONSULTAR_NOTAS_ACP,
					ReceptoresDireccionModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(ID_NOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(ID_NOT, id);
			procedureQuery.execute();
			result = (ReceptoresDireccionModel) procedureQuery.getSingleResult();
			resp = resp.modeloAdto(result);
		} catch (Exception e) {
			LOGGER.error(CONSULTA_SP_ACP_ERROR + PARAMETERSTOPASS, e.getMessage(), e.getStackTrace());
			return new ReceptoresDireccionDto();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<DetalleDebitoDTO> getDnl(Integer id) {
		List<DetalleDebitoModel> result = new ArrayList<>();
		List<DetalleDebitoDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_DNL con parametro idDeb: {} ", id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_DNL",
					DetalleDebitoModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(ID_NOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(ID_NOT, id);
			procedureQuery.execute();
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new DetalleDebitoDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_DNL {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<DetalleCreditoDTO> getCnl(Integer id) {
		List<DetalleCreditoModel> result = new ArrayList<>();
		List<DetalleCreditoDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_DNL con parametro idFac: {} ", id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_DNL",
					DetalleCreditoModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(ID_NOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(ID_NOT, id);
			procedureQuery.execute();
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new DetalleCreditoDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_DNL {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<ImpuestosLineasDTO> getLit(Integer id) {
		List<ImpuestosLineasDTO> resp = new ArrayList<>();
		if (id > 0) {
			List<ImpuestosLineasModel> result = new ArrayList<>();
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_NOTAS_LIT",
					ImpuestosLineasModel.class);
			try {
				LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_NOTAS_LIT con parametro idFac: {} ", id);
				procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
				procedureQuery.registerStoredProcedureParameter("id_DETNOT", int.class, ParameterMode.IN);
				procedureQuery.setParameter("id_DETNOT", id);
				result = procedureQuery.getResultList();
				if (result == null) {
					return new ArrayList<>();
				}
				resp = result.stream().map(e -> new ImpuestosLineasDTO().modeloAdto(e)).collect(Collectors.toList());
			} catch (Exception e) {
				LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_NOTAS_LIT {} | {}", e.getMessage(),
						e.getStackTrace());
				return new ArrayList<>();
			} finally {
				entityManager.clear();
			}
		}
		return resp;
	}

	public PagoDTO getPym(Integer id) {
		PagoModel result = new PagoModel();
		PagoDTO resp = new PagoDTO();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_NOTAS_PYM con parametro idDeb: {} ", id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_NOTAS_PYM",
					PagoModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(ID_NOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(ID_NOT, id);
			procedureQuery.execute();
			result = (PagoModel) procedureQuery.getSingleResult();
			resp = resp.modeloAdto(result);
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_NOTAS_PYM {} | {}", e.getMessage(), e.getStackTrace());
			return new PagoDTO();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<ImpuestosDTO> getTxt(Integer id) {
		List<ImpuestosModel> result = new ArrayList<>();
		List<ImpuestosDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_NOTAS_TXT con parametro idDeb: {} ", id);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTAR_NOTAS_TXT",
					ImpuestosModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(ID_NOT, int.class, ParameterMode.IN);
			procedureQuery.setParameter(ID_NOT, id);
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new ImpuestosDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_NOTAS_TXT {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<EncabezadoNotaDebDTO> getConsultaDebRemediado(String numdoc) {
		List<EncabezadoNotaDebModel> result = new ArrayList<>();
		List<EncabezadoNotaDebDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_NOTASDEB_REMEDIADOS");
			StoredProcedureQuery procedureQuery = entityManager
					.createStoredProcedureQuery("SP_CONSULTAR_NOTASDEB_REMEDIADOS", EncabezadoNotaDebModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(NUMDOC, String.class, ParameterMode.IN);
			procedureQuery.setParameter(NUMDOC, numdoc);
			procedureQuery.execute();
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new EncabezadoNotaDebDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_NOTASDEB_REMEDIADOS {} | {}", e.getMessage(),
					e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<EncabezadoNotaCreDTO> getConsultaCreRemediado(String numdoc) {
		List<EncabezadoNotaCreModel> result = new ArrayList<>();
		List<EncabezadoNotaCreDTO> resp = new ArrayList<>();
		try {
			LOGGER.info("Llamando el sp de factura: SP_CONSULTAR_NOTASCRE_REMEDIADOS con parametro numdoc: {} ",
					numdoc);
			StoredProcedureQuery procedureQuery = entityManager
					.createStoredProcedureQuery("SP_CONSULTAR_NOTASCRE_REMEDIADOS", EncabezadoNotaCreModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(NUMDOC, String.class, ParameterMode.IN);
			procedureQuery.setParameter(NUMDOC, numdoc);
			procedureQuery.execute();
			result = procedureQuery.getResultList();
			resp = result.stream().map(e -> new EncabezadoNotaCreDTO().modeloAdto(e)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_CONSULTAR_NOTASCRE_REMEDIADOS {} | {}", e.getMessage(),
					e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return resp;
	}

	public Integer trTipoId(Integer idPoliza, Integer opc) {
		Integer trtipoId = 0;
		try {
			LOGGER.info("Llamando el sp de factura: SP_TRTIPOID con parametro poliza: {} y opc: {} ", idPoliza, opc);
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_TRTIPOID");
			procedureQuery.registerStoredProcedureParameter("IDPOLIZA", int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter("OPC", int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter("TRID", int.class, ParameterMode.OUT);
			procedureQuery.setParameter("IDPOLIZA", idPoliza);
			procedureQuery.setParameter("OPC", opc);
			procedureQuery.execute();
			trtipoId = (Integer) procedureQuery.getOutputParameterValue("TRID");
		} catch (Exception e) {
			LOGGER.error("Error al ejecutar el sp SP_TRTIPOID {} | {}", e.getMessage(), e.getStackTrace());
		} finally {
			entityManager.clear();
		}
		return trtipoId;
	}
}
