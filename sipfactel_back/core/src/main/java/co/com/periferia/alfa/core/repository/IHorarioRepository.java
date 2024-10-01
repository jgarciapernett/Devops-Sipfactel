package co.com.periferia.alfa.core.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.HorarioModel;

public interface IHorarioRepository extends JpaRepository<HorarioModel, Long> {

	@Modifying
	@Transactional
	@Query(value = "insert into tbl_horario "
			+ "values (TBL_EJECUCION_DIARIO_ETL_SEQ.nextVal, 'TODOS', TO_TIMESTAMP(:ejecucion, 'DD/MM/YY HH24:MI:SS'), "
			+ "TO_TIMESTAMP(:inicio, 'DD/MM/YY HH24:MI:SS'), TO_TIMESTAMP(:fin, 'DD/MM/YY HH24:MI:SS'), :orden, null)", nativeQuery = true)
	void crearHorario(@Param("ejecucion") String ejecucion,
			@Param("inicio") String inicio,
			@Param("fin") String fin,
			@Param("orden") int orden);

	@Query(value = "SELECT to_char(ejecucion, 'HH24:MI') FROM tbl_horario WHERE to_char(ejecucion, 'HH24:MI:SS') = (" +
			"SELECT to_char(MAX (FECHA_PROCESADO), 'HH24:MI:SS') AS FECHA" +
			" FROM TBL_EJECUCION_DIARIO_ETL)", nativeQuery = true)
	String validacionHorario();
	
}
