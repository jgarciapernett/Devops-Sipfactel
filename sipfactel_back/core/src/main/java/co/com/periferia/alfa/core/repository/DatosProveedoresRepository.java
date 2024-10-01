package co.com.periferia.alfa.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.DatosProveedorModel;

public interface DatosProveedoresRepository extends JpaRepository<DatosProveedorModel, Integer> {
	
	@Query(value = "select * from (select * from tbl_datos_proveedor order by prove_prove desc ) where rownum = 1", nativeQuery = true)
   	public DatosProveedorModel findByLast();

}
