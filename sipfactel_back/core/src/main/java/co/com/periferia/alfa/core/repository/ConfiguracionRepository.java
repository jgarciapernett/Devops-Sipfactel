package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.ConfiguracionModel;

public interface ConfiguracionRepository extends JpaRepository<ConfiguracionModel, Integer> {

	@Query(value = "select * from tbl_configuracion where conf_padre = ?1", nativeQuery = true)
	public List<ConfiguracionModel> findByTipo(Integer id);

	@Query(value = "select * from tbl_configuracion where conf_llave = ?1 and conf_padre = ?2", nativeQuery = true)
	public ConfiguracionModel findByRepetido(String llave, Integer id);

	@Query(value = "select * from tbl_configuracion where conf_conf = ?1", nativeQuery = true)
	public ConfiguracionModel findByNombre(Integer id);

	@Query(value = "select * from tbl_configuracion where conf_sistema = 'Fuente' and conf_llave = ?1", nativeQuery = true)
	public ConfiguracionModel findByNombreHomo(Integer id);
}
