package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.DesplegableSucursalesModel;

public interface DesplegableSucursalesRepository extends JpaRepository<DesplegableSucursalesModel, Integer>{

	@Query(value = "select suc_suc, suc_nomsuc from tbl_sucursales where suc_estado = 'TRUE'", nativeQuery = true)
   	public List<DesplegableSucursalesModel> findBySucursales();
}
