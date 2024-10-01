package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.OblFiscalesValDefModel;

public interface OblFiscalesValDefRepository extends JpaRepository<OblFiscalesValDefModel, Integer> {

	@Query(value = "select * from tbl_obl_fiscales_vd where obl_vd_vd = ?1", nativeQuery = true)
	public List<OblFiscalesValDefModel> findByValorPorDefecto(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from tbl_obl_fiscales_vd where obl_vd_vd = ?1", nativeQuery = true)
	public void eliminar(Integer id);
}
