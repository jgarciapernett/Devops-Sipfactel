package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.DetalleFacModel;

public interface DetalleFacturasRepository extends JpaRepository<DetalleFacModel, Integer> {

	@Query(value = "select d from DetalleFacModel d where d.factura = :factura and d.ramo = :ramo")
	public List<DetalleFacModel> findByRamoAndFactura(@Param("factura") Integer factura, @Param("ramo") Integer ramo);
	
	@Query(value = "select d from DetalleFacModel d where d.id = :detalle")
	public DetalleFacModel findDetalleById(@Param("detalle") Integer detalle);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_detalle_factura set DETFAC_CALIDADCOD = 1 where DETFAC_FACT_FACT = :dni", nativeQuery = true)
    public void calidad1Detalle(@Param("dni") Integer dni);
	
}
