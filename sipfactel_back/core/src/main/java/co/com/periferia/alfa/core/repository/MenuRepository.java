package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.admin.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{

	@Query(value = "select m from Menu m where m.menuPadre = ?1")
	public List<Menu> findMenuhByRol(Integer id);
	
	@Query(value = "select m from Menu m where m.menuMenu = ?1")
	public List<Menu> findMenuByRol(Integer id);
	
	@Query(value = "select m from Menu m where m.menuPadre is null")
	public List<Menu> findMenuPadres();
	
	@Query(value = "select m from Menu m where m.menuMenu = ?1")
	public Menu findMenuByPadre(Integer id);
	
	@Query(value = "select m from Menu m where m.menuMenu = ?1")
	public Menu findMenuByHijo(Integer id);
}
