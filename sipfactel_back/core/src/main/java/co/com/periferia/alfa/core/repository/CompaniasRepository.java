package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.CompaniasModel;

public interface CompaniasRepository extends JpaRepository <CompaniasModel, Integer>{

	@Query(value = "select * from tbl_companias where comp_estado = 'TRUE'", nativeQuery = true)
	public List<CompaniasModel> findTrue();
	
	@Query(value = "select * from tbl_companias where comp_comp != ?1 and comp_estado = 'TRUE'", nativeQuery = true)
	public List<CompaniasModel> findOthers(Integer id);
	
}
