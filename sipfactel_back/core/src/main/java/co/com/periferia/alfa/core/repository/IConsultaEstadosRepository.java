package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.ConsEstadosTransitoriosModel;

public interface IConsultaEstadosRepository extends JpaRepository<ConsEstadosTransitoriosModel, Integer> {

	/**
	 * Consulta que retorna el numero de documentos y notas que se encuentran en
	 * estados transitorios ((1256,1258,2531)
	 */
	@Query(value = "SELECT * FROM (SELECT FACT_COD_TIPODOCUMENTO, FACT_NUMDOC, FACT_COD_ESTADO AS fact_fact_estado FROM TBL_FACTURAS "
			+ "WHERE FACT_COD_ESTADO IN (1256, 1258, 2531, 1260) AND fact_numdoc IS NOT NULL AND fact_error_1260 IS NULL UNION ALL "
			+ "SELECT NOT_COD_TIPODOCUMENTO, NOT_NUMDOC, NOT_COD_ESTADO AS not_not_estado FROM TBL_NOTAS_DEBITOCREDITO "
			+ "WHERE NOT_COD_ESTADO IN (1256, 1258, 2531, 1260) AND NOT_NUMDOC IS NOT NULL AND not_error_1260 IS NULL) WHERE  ROWNUM < 95", nativeQuery = true)
	public List<ConsEstadosTransitoriosModel> estadosTransitoriosAConsultar();
}
