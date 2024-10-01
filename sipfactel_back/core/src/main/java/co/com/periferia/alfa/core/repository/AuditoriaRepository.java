package co.com.periferia.alfa.core.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.admin.Auditoria;


public interface AuditoriaRepository extends JpaRepository <Auditoria, Integer>{
	
	@Query(value = "select a from Auditoria a where audiUsuario = ?1 order by audiFecha")
	public List<Auditoria> findByTipo(String audiusuario);
	
	@Query(value = "select a from Auditoria a where audiFecha between ?1 and ?2 order by audiFecha")
	public List<Auditoria> findByfecha(Timestamp fechaIni, Timestamp fechaFin);
	
	@Query(value = "select a from Auditoria a where audiFecha between ?1 and ?2 and audiUsuario = ?3 order by audiFecha")
	public List<Auditoria> findByUsuYFecha(Timestamp fechaIni, Timestamp fechaFin, String audiUsuario);
	
	@Query(value = "select a from Auditoria a where audiFecha between ?1 and ?2 order by audiFecha")
	public List<List<Auditoria>> findByfechaExcel(Timestamp fechaIni, Timestamp fechaFin);
	
	@Query(value = "select a from Auditoria a where audiUsuario = ?1 order by audiFecha")
	public List<List<Auditoria>> findByUsuarioExcel(String audiusuario);

	@Query(value = "select a from Auditoria a where audiFecha between ?1 and ?2 and audiUsuario = ?3 order by audiFecha")
	public List<List<Auditoria>> findByUsuYFechaExcel(Timestamp fechaIni, Timestamp fechaFin, String audiUsuario);
	
	@Query(value = "select a from Auditoria a order by audiFecha")
	public List<List<Auditoria>> findByAll();
	
	@Query(value = "select a from Auditoria a order by audiFecha")
	public List<Auditoria> findByGroup();
	
}