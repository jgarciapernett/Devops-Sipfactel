package co.com.periferia.alfa.core.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase reporistorio para la consulta de los codigos tris a reenviar
 * @author Duvan Rodriguez
 */

@Transactional(propagation = Propagation.NESTED)
@Repository
public class ReenvioFacturasNotasRepository {

	public static final Logger LOG = LoggerFactory.getLogger(ReenvioFacturasNotasRepository.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String CURSOR = "c_Resp";
	private static final String SPCONSULTARTRIDREENVIO = "SP_CONSULTAR_TRID_REENVIO";
	
	/**
	 * Metodo que realiza la ocnsulta al sp de SP_CONSULTAR_TRID_REENVIO
	 * @return List<Object[]> lista con los codigos y sus llaves primarias de la tabla json_envio
	 */
	
	@SuppressWarnings("unchecked")
	public List<Object[]> codigosTrid() {
		LOG.info("Ingreso a consulta de factura/nota reenvio");
		List<Object[]> listaTrid = new ArrayList<>();
		try {
			listaTrid = spConsultaTridEstado(CURSOR, void.class, ParameterMode.REF_CURSOR).getResultList();
		} catch (Exception e) {
			LOG.error("Error al ejecutar el sp SP_CONSULTAR_TRID_REENVIO {} | {}", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		} finally {
			entityManager.clear();
		}
		return listaTrid;
	}
	
	/**
	 * Metodo que ejecuta los procedure query
	 * @param cursor - String, define el tipo de cursor a usar
	 * @param type - clase que mapea el cursos enviado
	 * @param mode - ParameterMode, tipo de parametro a enviar
	 * @return StoredProcedureQuery procedimiento ejecutado
	 */
	
	private StoredProcedureQuery spConsultaTridEstado(String cursor, Class<?> type, ParameterMode mode) {
		LOG.info("Llamando el sp de factura: SP_CONSULTAR_TRID_REENVIO");
		StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(SPCONSULTARTRIDREENVIO);
		procedureQuery.registerStoredProcedureParameter(cursor, type, mode);
		procedureQuery.execute();
		return procedureQuery;
	}
	
}
