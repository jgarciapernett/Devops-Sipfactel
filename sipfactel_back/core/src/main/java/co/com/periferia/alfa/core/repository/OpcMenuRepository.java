package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.OpcMenuModel;

public interface OpcMenuRepository extends JpaRepository<OpcMenuModel, Integer>{

	@Query(value = "select menu_menu, menu_titulo from tbl_menus where menu_padre is null", nativeQuery = true)
	public List<OpcMenuModel> findMenu();
	
	@Query(value = "select menu_menu, menu_titulo from tbl_menus where menu_menu = ?1", nativeQuery = true)
	public List<OpcMenuModel> findNomMenu(Integer id);
	
	@Query(value = "select menu_menu, menu_titulo from tbl_menus where menu_padre = ?1", nativeQuery = true)
	public List<OpcMenuModel> findhijo(Integer id);
	
	@Query(value = "select menu_menu, menu_titulo from tbl_menus where menu_menu = ?1", nativeQuery = true)
	public OpcMenuModel findNombre(Integer id);
}
