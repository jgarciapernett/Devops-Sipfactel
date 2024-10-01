package co.com.periferia.alfa.core.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.DatosGeneralesModel;

public interface DatosGeneralesRepository extends JpaRepository<DatosGeneralesModel, Integer>{

	@Query(value = "select * from (select * from tbl_datos_generales order by dato_dato desc ) where rownum = 1 ", nativeQuery = true)
   	public DatosGeneralesModel findByLast();
}
