package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.admin.UsaRol;

public interface UsuaRolRepository extends JpaRepository<UsaRol, Integer>{
	
	@Query(value = "select u from UsaRol u where u.usuaUsua = ?1")
	public List<UsaRol> findByUserId(Integer id);
	
}
