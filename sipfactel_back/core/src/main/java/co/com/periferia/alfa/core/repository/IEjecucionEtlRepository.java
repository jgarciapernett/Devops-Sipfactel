package co.com.periferia.alfa.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.EjecucionEtlModel;

public interface IEjecucionEtlRepository extends JpaRepository<EjecucionEtlModel, Integer> {

	@Query(value = "select ejec_ejec, to_char(ejec_fecha, 'DD/MM/YYYY') as ejec_fecha, ejec_procesado, to_char(ejec_ejecucion, 'DD/MM/YYYY HH24:MI:SS') as ejec_ejecucion "
			+ "from (select * from tbl_ejecucion_etl order by ejec_ejecucion desc)", countQuery = "select count(1)"
					+ "from (select * from tbl_ejecucion_etl order by ejec_ejecucion desc)", nativeQuery = true)
    public List<EjecucionEtlModel> consultarEjecucion();
	
}
