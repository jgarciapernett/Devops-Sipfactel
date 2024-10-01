package co.com.periferia.alfa.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.periferia.alfa.core.utilitarios.FacEnum;

/**
 * Clase respositorio para el sp de validaciones de los trId repetidos
 */

@Transactional(propagation = Propagation.NESTED)
@Repository
public class ValidacionRepetidosRepository {

	public static final Logger LOG = LoggerFactory.getLogger(ValidacionRepetidosRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final String CURSOR = "c_Resp";
	private static final String FACTURANOTA = "facturanota";
	private static final String TRID = "trid";
	private static final String SPVALIDACIONREPETIDOS = "SP_VALIDACION_REPETIDOS";

	/**
	 * Metodo que consulta el sp SP_VALIDACION_REPETIDOS
	 * @param facturaNota - Integer, tipo de documento a validar, 1198 factura, 1202 credito, 1203 debito
	 * @param trid - Integer, codigo de la transaccion
	 * @return String solo es para validar si existen o no registros
	 */
	
	public String busquedaCoincidencias(Integer facturaNota, Integer trid) {
		LOG.info("Ingreso a consulta de factura/nota reenvio");
		String respuestaValidacion;
		try {
			respuestaValidacion = (String) spValidacionRepetidos(facturaNota, trid).getSingleResult();
		} catch (Exception e) {
			LOG.error("Error al ejecutar el sp SP_VALIDACION_REPETIDOS {} | {}", e.getMessage(), e.getStackTrace());
			return FacEnum.NOTWHITE_SPACE.getValor();
		} finally {
			entityManager.clear();
		}
		return respuestaValidacion;
	}

	/**
	 * Metodo que ejecuta el procedimiento
	 * @param facturaNota - Integer, tipo de documento a validar, 1198 factura, 1202 credito, 1203 debito
	 * @param trid - Integer, codigo de la transaccion
	 * @return StoredProcedureQuery procedimiento ejecutado
	 */
	
	private StoredProcedureQuery spValidacionRepetidos(Integer facturaNota, Integer trid) {
		LOG.info("Llamando el sp de factura: SP_VALIDACION_REPETIDOS");
		StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(SPVALIDACIONREPETIDOS);
		procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
		procedureQuery.registerStoredProcedureParameter(FACTURANOTA, int.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter(TRID, int.class, ParameterMode.IN);
		procedureQuery.setParameter(FACTURANOTA, facturaNota);
		procedureQuery.setParameter(TRID, trid);
		procedureQuery.execute();
		return procedureQuery;
	}

}
