package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.OpcionesModel;


public interface OpcionesRepository extends JpaRepository<OpcionesModel, Integer> {
	
    @Query(value = "select conf_conf, conf_sistema from tbl_configuracion where conf_conf in (1,2,3,4,5,7,8,10,17) ", nativeQuery = true)
	public List<OpcionesModel> findByTipo();
    
}
