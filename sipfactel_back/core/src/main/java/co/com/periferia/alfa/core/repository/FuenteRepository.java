package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.FuenteModel;

public interface FuenteRepository extends JpaRepository<FuenteModel, Integer>{

	@Query(value = "select conf_conf, conf_llave, conf_valor from tbl_configuracion where conf_sistema = 'Fuente' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
   	public List<FuenteModel> findByFuente();
}
