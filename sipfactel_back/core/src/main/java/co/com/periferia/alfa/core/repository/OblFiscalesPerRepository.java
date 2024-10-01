package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.OblFiscalesPerModel;

public interface OblFiscalesPerRepository extends JpaRepository<OblFiscalesPerModel, Integer>{

	@Query(value = "select * from tbl_obl_fiscales_per where obl_per_per = ?1", nativeQuery = true)
	public List<OblFiscalesPerModel> findByPer(Integer id);
	
	@Query(value = "select obl_cod_fiscales from tbl_obl_fiscales_per where obl_per_per = ?1", nativeQuery = true)
	public List<Integer> findCodigosOblByPer(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from tbl_obl_fiscales_per where obl_per_per = ?1", nativeQuery = true)
	public void eliminar(Integer id);
}
