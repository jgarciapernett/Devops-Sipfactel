package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.HomologacionModel;

public interface HomologacionRepository extends JpaRepository<HomologacionModel, Integer> {
	
	@Query(value = "select * from tbl_homologacion where homo_conf_conf = ?1 order by homo_fuente", nativeQuery = true)
	public List<HomologacionModel> findByTipo(Integer id);
	
	@Query(value = "select * from tbl_homologacion where homo_fuente = ?1 and homo_origen = ?2", nativeQuery = true)
	public HomologacionModel findByRepetido(Integer fuente, String origen);
	
	@Modifying
	@Transactional
	@Query(value = "delete from tbl_homologacion where homo_homo = ?1", nativeQuery = true)
	public void eliminar(Integer id);
	
}