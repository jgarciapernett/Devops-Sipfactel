package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.OpcCompaniasModel;

public interface OpcCompaniasRepository extends JpaRepository <OpcCompaniasModel, Integer>{

	@Query(value = "select comp_comp, comp_nombre from tbl_companias where comp_estado = 'TRUE'", nativeQuery = true)
	public List<OpcCompaniasModel> findByTipo();
	
}
