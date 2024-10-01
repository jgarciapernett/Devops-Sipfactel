package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.periferia.alfa.core.model.ErroresDelcopModel;

@Repository
public interface IErroresDelcopRepository extends JpaRepository<ErroresDelcopModel, Integer> {

	@Query(value = "select e from ErroresDelcopModel e where estado = 'ACT' and id != 1")
	public List<ErroresDelcopModel> consultaEstadosActivos();
	
}
