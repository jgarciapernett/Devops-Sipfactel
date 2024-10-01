package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.PorcentajesModel;

public interface PorcentajesRepository extends JpaRepository<PorcentajesModel, Integer>{

	@Query(value = "select * from tbl_porcentajes where porc_estado = 'ACT' ORDER BY porc_impuesto", nativeQuery = true)
   	public List<PorcentajesModel> findByPorc();
	
	@Query(value = "select p from PorcentajesModel p where p.idStr = :impuesto and p.estado = 'ACT' ORDER BY p.llave")
   	public List<PorcentajesModel> findByImpuesto(@Param("impuesto") String impuesto);
	
}
