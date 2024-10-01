package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.SucursalesModel;

public interface SucursalesRepository extends JpaRepository <SucursalesModel, Integer>{

	@Query(value = "select * from tbl_sucursales where suc_estado = 'TRUE'", nativeQuery = true)
	public List<SucursalesModel> findByTipo();
	
	@Query(value = "select * from tbl_sucursales where suc_suc != ?1", nativeQuery = true)
	public List<SucursalesModel> findOthers(Integer id);
	
}
