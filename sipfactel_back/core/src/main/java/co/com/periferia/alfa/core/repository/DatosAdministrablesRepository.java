package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.DatosAdministrablesModel;

public interface DatosAdministrablesRepository extends JpaRepository <DatosAdministrablesModel, Integer>{
	
	@Query(value = "select * from tbl_valores_defecto where vad_vad != ?1", nativeQuery = true)
	public List<DatosAdministrablesModel> findOthers(Integer id);
	
	@Query(value = "select * from tbl_valores_defecto where vad_suc_estado = 'TRUE'", nativeQuery = true)
	public List<DatosAdministrablesModel> findTrue();

}
