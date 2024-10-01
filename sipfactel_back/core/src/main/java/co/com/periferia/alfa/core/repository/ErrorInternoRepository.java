package co.com.periferia.alfa.core.repository;

import java.math.BigDecimal;
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

import co.com.periferia.alfa.core.model.EmitirDelcopModel;
import co.com.periferia.alfa.core.model.ErrorInternoModel;

@Transactional(propagation = Propagation.NESTED)
@Repository
public class ErrorInternoRepository {
	
	private static final Logger LOG = LoggerFactory.getLogger(ErrorInternoRepository.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	private static final String CURSOR = "c_Resp";

	public List<ErrorInternoModel> getErrorInterno() {
		return executeProcedure("SP_ERRORINTERNO");
	}
	
	public List<ErrorInternoModel> getErrorInternoNotas() {
		return executeProcedure("SP_ERRORINTERNO_NOTAS");
	}
	
	@SuppressWarnings("unchecked")
	private List<ErrorInternoModel> executeProcedure(String strQuery) {
		List<ErrorInternoModel> result = new ArrayList<>();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(strQuery);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.execute();
			List<Object[]> results = procedureQuery.getResultList();
			result = results.stream().map(e -> new ErrorInternoModel((String) e[1], (String) e[10], (String) e[11],
					(BigDecimal) e[12], (BigDecimal) e[13], (String) e[14], ((BigDecimal) e[15]).intValue())).collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<>();
		}
		return result;
	}

	public EmitirDelcopModel getInfoEnvio(String numdoc) {
		EmitirDelcopModel result = new EmitirDelcopModel();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_INFO_ENVIO");
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter("NUMDOC", String.class, ParameterMode.IN);
			procedureQuery.setParameter("NUMDOC", numdoc);
			procedureQuery.execute();
			Object[] results = (Object[]) procedureQuery.getSingleResult();
			result = new EmitirDelcopModel((BigDecimal) results[0], (String) results[1], (String) results[10],
					(String) results[11], (BigDecimal) results[12], (BigDecimal) results[13]);
		} catch (Exception e) {
			return new EmitirDelcopModel();
		}
		return result;
	}
	public void getReAsignar() {
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_REASIGNAR");
			procedureQuery.execute();
		} catch (Exception e) {
			LOG.error("(ErrorInternoRepository) Excepcion en getReAsignar ----> {} | {}", 
					e.getMessage(), e.getStackTrace());
		}
	}
}
