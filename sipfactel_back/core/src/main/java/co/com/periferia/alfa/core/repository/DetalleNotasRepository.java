package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.DetalleNotasModel;

public interface DetalleNotasRepository extends JpaRepository<DetalleNotasModel, Integer>{

	@Query(value = "select n from DetalleNotasModel n where n.nota = :nota and n.codRamo = :ramo")
	public List<DetalleNotasModel> findByRamoAndNota(@Param("nota") Integer nota, @Param("ramo") Integer ramo);
	
	@Query(value = "select n from DetalleNotasModel n where n.id = :detalle")
	public DetalleNotasModel findDetalleById(@Param("detalle") Integer detalle);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_detalle_notas_debitocredito set DETNOT_CALIDADCOD = 1 where DETNOT_NOT_NOT = :dni", nativeQuery = true)
    public void calidad1Detalle(@Param("dni") Integer dni);
	
}
