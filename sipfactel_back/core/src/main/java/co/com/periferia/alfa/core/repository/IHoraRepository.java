package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.HorasModel;

public interface IHoraRepository extends JpaRepository<HorasModel, Integer> {

	@Query(value = "select h from HorasModel h order by h.dni")
	List<HorasModel> consultar();
	
	@Query(value = "select h from HorasModel h where h.dni in :lista")
	List<HorasModel> consultarByDni(@Param("lista") List<Integer> lista);
	
	@Modifying
	@Transactional
	@Query(value = "update tbl_hora set estado = 'INA' where dni not in :lista", nativeQuery = true)
	void updateEstadoIna(@Param("lista") List<Integer> lista);
	
	@Modifying
	@Transactional
	@Query(value = "update tbl_hora set estado = 'ACT' where dni in :lista", nativeQuery = true)
	void updateEstadoAct(@Param("lista") List<Integer> lista);
	
	
}
