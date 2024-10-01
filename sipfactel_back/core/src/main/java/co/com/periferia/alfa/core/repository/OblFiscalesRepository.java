package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.OblFiscalesModel;

public interface OblFiscalesRepository extends JpaRepository<OblFiscalesModel, Integer>{

	@Query(value = "select * from tbl_obl_fiscales_asp where asp_comp_comp = ?1", nativeQuery = true)
	public List<OblFiscalesModel> findByComp(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from tbl_obl_fiscales_asp where asp_comp_comp = ?1", nativeQuery = true)
	public void eliminar(Integer id);
}
