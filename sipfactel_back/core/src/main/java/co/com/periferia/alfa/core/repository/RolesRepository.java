package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.admin.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
	
	@Query(value = "select r from Roles r where r.roleRole = ?1")
	public List<Roles> findRolesByUser(Integer id);
	
	@Query(value = "select r from Roles r where UPPER(r.roleNombre) LIKE UPPER (concat('%', ?1,'%'))")
	public List<Roles> findByRoleNombre(String roleNombre); 
	
	@Query(value = "select r from Roles r where r.roleRole = ?1 ")
	public Roles roleNombre(Integer id); 
	
	@Query(value = "select r from Roles r where UPPER(r.roleNombre) = UPPER(?1) ")
	public Roles findrole(String nombre); 
	
}
