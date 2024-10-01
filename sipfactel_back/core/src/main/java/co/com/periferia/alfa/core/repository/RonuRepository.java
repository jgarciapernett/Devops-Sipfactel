package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.admin.Ronu;

public interface RonuRepository extends JpaRepository<Ronu, Integer> {

	@Query(value = "select r from Ronu r where r.rolesRoles = ?1 and r.padre is null order by menuMenu")
	public List<Ronu> findMenuByRol(Integer id);
	
	@Query(value = "select r from Ronu r where r.padre = ?1 and r.rolesRoles = ?2")
	public List<Ronu> findMenuByhijo(Integer id, Integer rol);
	
	@Modifying
	@Transactional
	@Query(value = "delete from Ronu r where r.rolesRoles = ?1")
	public void deletemenu(Integer id);
	
	@Query(value = "select r from Ronu r where r.rolesRoles = ?1 and r.crear = 0 ")
	public List<Ronu> findMenuByPadre(Integer id);
	
}
