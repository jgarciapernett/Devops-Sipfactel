package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.ParametrosModel;

public interface IParametrosRepository extends JpaRepository<ParametrosModel, Integer> {
	
	@Query(value = "select valor from tbl_parametrosbck where nombre = :param", nativeQuery = true)
	public String buscarNombre(@Param("param") String param);
	
	@Query(value = "select p from ParametrosModel p where p.nombre in :params")
	public List<ParametrosModel> buscarListaNombres(@Param("params") List<String> params);
	
	@Query(value = "select p from ParametrosModel p where p.dni = :dni")
	public ParametrosModel buscarDni(@Param("dni") Integer dni);
	
}
