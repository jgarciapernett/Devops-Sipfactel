package co.com.periferia.alfa.core.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.DetalleErrorModel;

public interface DetalleErrorRepository extends JpaRepository<DetalleErrorModel, Integer>{

    @Modifying
    @Transactional
    @Query(value = "delete from tbl_detalleerror where derr_idtabla = ?1 and derr_tabla = 'tbl_Personas'", nativeQuery = true)
    public void deleteById(Integer detalleError);

}
